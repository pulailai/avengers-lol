package com.yuapt.service.core.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;

/**
 * Created by jimmy on 17/1/9.
 */
public class YuaptHystrixRequestContext {


    private static class Singleton {
        private static  HystrixRequestVariableDefault<String> userContextVariable = new HystrixRequestVariableDefault<String>();
    }

    private YuaptHystrixRequestContext() {
    }

    public static HystrixRequestVariableDefault<String> getInstance() {
        return Singleton.userContextVariable;
    }

}
