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
package com.james.client.task;

import java.util.concurrent.ExecutorService;

import org.springframework.boot.CommandLineRunner;

import com.james.common.utils.ThreadPoolExecutorFactory;

import jakarta.annotation.PreDestroy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author james
 */
@Slf4j
public abstract class AbstractMessageResultTask implements CommandLineRunner {

    private static final ExecutorService EXECUTOR_SERVICE = ThreadPoolExecutorFactory.getThreadPoolExecutor();

    @Override
    public void run(String... args) {
        // 初始化定时器
        EXECUTOR_SERVICE.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                try {
                    pullMessage();
                } catch (Exception e) {
                    log.error("任务调度异常", e);
                }
                if (!EXECUTOR_SERVICE.isShutdown()) {
                    Thread.sleep(100);
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
