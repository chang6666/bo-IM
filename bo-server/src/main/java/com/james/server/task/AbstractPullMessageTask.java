/**
 * Copyright [2024] [bo-IM]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.james.server.task;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.james.common.utils.ThreadPoolExecutorFactor;
import com.james.server.netty.IMServerGroup;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * @author james
 */
@Slf4j
public abstract class AbstractPullMessageTask implements CommandLineRunner {
    
    private static final ExecutorService EXECUTOR_SERVICE = ThreadPoolExecutorFactor.getThreadPoolExecutor();

    @Autowired
    private IMServerGroup serverGroup;

    @Override
    public void run(String... args) throws Exception {
        EXECUTOR_SERVICE.execute(new Runnable() {
            public void run() {
                try {
                    if (serverGroup.isReady()) {
                        pullMessage();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!EXECUTOR_SERVICE.isShutdown()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    EXECUTOR_SERVICE.execute(this);
                }
            }
        });
    }
    
    @PreDestroy
    public void destroy() {
        log.info("{}线程任务关闭", this.getClass().getSimpleName());
        EXECUTOR_SERVICE.shutdown();
    }
    


    public abstract void pullMessage() throws InterruptedException;

}
