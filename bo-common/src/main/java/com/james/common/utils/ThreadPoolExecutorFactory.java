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
package com.james.common.utils;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 创建单例线程池
 * @author james
 */
public final class ThreadPoolExecutorFactory {

    /**
     * 机器的CPU核数: Runtime.getRuntime().availableProcessors()
     * corePoolSize: 线程池维护线程的最少数量,包括空闲线程
     * CPU密集型: 核心线程数 = CPU核数*2
     * IO密集型: 核心线程数 = CPU核数+1
     */
    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 100;

    /**
     * 线程存活时间
     */
    private static final int KEEP_ALIVE_TIME = 1000;

    /**
     * 阻塞队列
     */
    private static final int QUEUE_SIZE = 200;


    private static volatile ThreadPoolExecutor threadPoolExecutor = null;

    private ThreadPoolExecutorFactory()
    {
        if (null == threadPoolExecutor) {
            threadPoolExecutor = ThreadPoolExecutorFactory.getThreadPoolExecutor();
        }
    }

    /**
     * 重写readResolve方法
     * @return
     */
    private Object readResolve() {
        return ThreadPoolExecutorFactory.getThreadPoolExecutor();
    }
    


    public static ThreadPoolExecutor getThreadPoolExecutor()
    {
        if (null == threadPoolExecutor) {
            synchronized (ThreadPoolExecutorFactory.class) {
                if (null == threadPoolExecutor) {
                    threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(QUEUE_SIZE),
                            new ThreadPoolExecutor.CallerRunsPolicy());
                }
            }
        }

        return threadPoolExecutor;
    }
    

    /**
     * 关闭线程池
     */
    public static void shutdown()
    {
        if (null != threadPoolExecutor) {
            threadPoolExecutor.shutdown();
        }
    }

    public void execute(Runnable runnable) {
        if (null == runnable) {
            return;
        }
        threadPoolExecutor.execute(runnable);
    }


}
