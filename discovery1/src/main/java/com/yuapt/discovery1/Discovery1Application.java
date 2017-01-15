package com.yuapt.discovery1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Discovery1Application {

	public static void main(String[] args) {
		SpringApplication.run(Discovery1Application.class, args);
	}
}
