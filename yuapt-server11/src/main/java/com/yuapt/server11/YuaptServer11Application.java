package com.yuapt.server11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class YuaptServer11Application {

	public static void main(String[] args) {
		SpringApplication.run(YuaptServer11Application.class, args);
	}
}
