package com.yuapt.service.core.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.yuapt.service.core.utils.AvengersHttpHeader;

/**
 * Created by jimmy on 17/1/9.
 */
public class AvengersHystrixRequestContext {


    private static class Singleton {
        private static HystrixRequestVariableDefault<AvengersHttpHeader> userContextVariable = new HystrixRequestVariableDefault<AvengersHttpHeader>();
    }

    private AvengersHystrixRequestContext() {
    }

    public static HystrixRequestVariableDefault<AvengersHttpHeader> getInstance() {
        return Singleton.userContextVariable;
    }

}
