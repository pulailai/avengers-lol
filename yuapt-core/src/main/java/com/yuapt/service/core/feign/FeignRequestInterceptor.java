package com.yuapt.service.core.feign;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.yuapt.service.core.Constants;
import com.yuapt.service.core.hystrix.YuaptHystrixRequestContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        LOGGER.info("Start set header information");
        HystrixRequestContext.getContextForCurrentThread();

        HystrixRequestVariableDefault<String> hrvd = YuaptHystrixRequestContext.getInstance();
        final String serverHeader = hrvd.get();
        try {
            if (null != serverHeader && StringUtils.isNotEmpty(serverHeader)) {
                requestTemplate.header(Constants.SERVICE_HEADER, serverHeader);
                LOGGER.info("Service header value is [{}]", serverHeader);
            }
            else {
                LOGGER.warn("Service header is null ");
            }

        } catch (Exception e) {
            LOGGER.error("Feign requestTemplate set header failure;exception information[{}]", e.getMessage());
        }

    }
}
