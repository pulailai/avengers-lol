package com.yuapt.service.core;

import com.yuapt.service.core.filters.HttpRequestHeaderFilter;
import com.yuapt.service.core.ribbon.rule.MetadataCustomRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Created by jimmy on 17/1/9.
 */
@Configuration
public class YuaptConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(YuaptConfiguration.class);

    /**
     * metadataCustomRule
     *
     * @return
     */
    @Bean
    public MetadataCustomRule metadataCustomRule() {
        return new MetadataCustomRule();
    }

    /**
     * 请求 filter
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistration() {

        LOGGER.info("init HttpRequestHeaderFilter......");
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpRequestHeaderFilter());
        registration.addUrlPatterns("/*");
        registration.setName("httpRequestHeaderFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        LOGGER.info("HttpRequestHeaderFilter Initialize the end.................");
        return registration;
    }


}
