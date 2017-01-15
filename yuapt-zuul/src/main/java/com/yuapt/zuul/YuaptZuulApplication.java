package com.yuapt.zuul;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringCloudApplication
public class YuaptZuulApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(YuaptZuulApplication.class).web(true).run(args);
    }

}
