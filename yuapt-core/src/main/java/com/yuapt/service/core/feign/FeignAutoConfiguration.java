package com.yuapt.service.core.feign;

import feign.Feign;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by jimmy on 17/1/5.
 */
@Configuration
@ConditionalOnClass({Feign.class})
@ConditionalOnProperty(value = "feign.avengers.enabled", matchIfMissing = true)
public class FeignAutoConfiguration {

    @Bean
    public RequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }
}

