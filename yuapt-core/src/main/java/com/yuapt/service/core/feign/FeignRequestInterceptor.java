package com.yuapt.service.core.feign;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.yuapt.service.core.hystrix.AvengersHystrixRequestContext;
import com.yuapt.service.core.utils.AvengersHttpHeader;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by jimmy on 17/1/5.
 */
public class FeignRequestInterceptor implements RequestInterceptor {


    /**
     * The logger instance used by this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignRequestInterceptor.class);


    /**
     * Service header 如果为null,会影响灰度发布
     *
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        LOGGER.info("Start set header information");
        HystrixRequestContext.getContextForCurrentThread();

        HystrixRequestVariableDefault<AvengersHttpHeader> hrvd = AvengersHystrixRequestContext.getInstance();
        final AvengersHttpHeader avengersHttpHeader = hrvd.get();

        try {

            if (avengersHttpHeader != null && !avengersHttpHeader.getHeaders().isEmpty()) {
                Map<String, String> headers = avengersHttpHeader.getHeaders();
                for (String key : headers.keySet()) {
                    requestTemplate.header(key, headers.get(key));
                }
                LOGGER.info("Service headers values is [{}]", JSON.toJSONString(headers));
            }
            else {

                LOGGER.warn("No incoming headers。");
            }

        }
        catch (Exception e) {
            LOGGER.error("Feign requestTemplate set header failure;exception information[{}]", e.getMessage());
        }

    }
}
