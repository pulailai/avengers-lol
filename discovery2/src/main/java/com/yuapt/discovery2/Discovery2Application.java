package com.yuapt.discovery2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Discovery2Application {

	public static void main(String[] args) {
		SpringApplication.run(Discovery2Application.class, args);
	}
}
