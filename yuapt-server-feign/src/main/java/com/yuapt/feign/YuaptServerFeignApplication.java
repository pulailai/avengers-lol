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
public class YuaptServerFeignApplication {

    public static void main(String[] args) {
        System.out.println(System.getProperty("host.ip"));
        ConfigurableApplicationContext context = SpringApplication.run(YuaptServerFeignApplication.class, args);
    }


}
