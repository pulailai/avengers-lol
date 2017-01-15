package com.yuapt.feign;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan
//@Import({
//        FeignRequestInterceptor.class,
//        YuaptConfiguration.class
//})
//@ComponentScan("com.yuapt.core")
public class YuaptServerFeignApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(YuaptServerFeignApplication.class, args);
    }


}
