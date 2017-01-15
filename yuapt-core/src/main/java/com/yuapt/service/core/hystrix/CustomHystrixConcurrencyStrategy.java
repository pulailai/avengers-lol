package com.yuapt.service.core.hystrix;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;


/**
 * Created by jimmy on 17/1/6.
 *  CustomHystrixConcurrencyStrategy 实现 参考:
 *      http://stackoverflow.com/questions/34062900/forward-a-request-header-with-a-feign-client-requestinterceptor
 */
@Component
public class CustomHystrixConcurrencyStrategy extends HystrixConcurrencyStrategy {

    public CustomHystrixConcurrencyStrategy() {
        HystrixPlugins.getInstance().registerConcurrencyStrategy(this);
    }

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        return new HystrixContextWrapper<T>(callable);
    }

    public static class HystrixContextWrapper<V> implements Callable<V> {

        /**
         * hystrixRequestContext 上下文信息
         */
        private HystrixRequestContext hystrixRequestContext;

        private Callable<V> callable;

        public HystrixContextWrapper(Callable<V> callable) {
            this.hystrixRequestContext = HystrixRequestContext.getContextForCurrentThread();
            this.callable = callable;
        }



        /**
         * 将自己创建的上下文，传递到Hystrix子线程;finally 还原上下文
         * @return
         * @throws Exception
         */
        @Override
        public V call() throws Exception {
            HystrixRequestContext existingState = HystrixRequestContext.getContextForCurrentThread();
            try {
                HystrixRequestContext.setContextOnCurrentThread(this.hystrixRequestContext);
                return this.callable.call();
            } finally {
                HystrixRequestContext.setContextOnCurrentThread(existingState);
            }
        }
    }


}



