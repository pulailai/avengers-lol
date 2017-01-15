package com.yuapt.zipkin.mysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

@EnableDiscoveryClient
@SpringBootApplication
@EnableZipkinStreamServer
public class ZipkinMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinMysqlApplication.class, args);
    }


}
