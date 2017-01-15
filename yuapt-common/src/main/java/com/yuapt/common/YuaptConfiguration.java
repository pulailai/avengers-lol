package com.yuapt.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;

/**
 * Created by jimmy on 17/1/9.
 */
@Configuration("yuapt1")
//@ConditionalOnProperty(value = "spring.sleuth.web.enabled", matchIfMissing = true)
@ConditionalOnWebApplication
public class YuaptConfiguration extends  WebMvcConfigurerAdapter{

    private static Logger log = LoggerFactory.getLogger(YuaptConfiguration.class);





    /**
     * 请求 filter
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistration() {
        log.info("大叔大婶自动装配filter......");
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(someFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("requestFilter121");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    @Bean(name = "requestFilter121")
    public Filter someFilter() {
        return new  RequestFilter121();
    }


}
