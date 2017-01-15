package com.yuapt.zipkin;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.stream.SleuthSink;
import org.springframework.cloud.sleuth.stream.Spans;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import zipkin.server.EnableZipkinServer;

import javax.annotation.PostConstruct;

@EnableDiscoveryClient
@EnableZipkinServer
@EnableBinding(SleuthSink.class)
@SpringBootApplication
@MessageEndpoint
public class ZipkinApplication {


	@Value("${qiniu.url}")
	private String driverClassName;


	@Value("${upload.url}")
	private String url;


	@ServiceActivator(inputChannel = SleuthSink.INPUT)
	public void sink(Spans input) throws Exception {

		System.out.println("消费者=============="+ JSON.toJSONString(input));


	}

	@PostConstruct
	public void show() {
		System.out.println("driverClassName=" + driverClassName);
		System.out.println("url=" + url);
	}
	public static void main(String[] args) {
		SpringApplication.run(ZipkinApplication.class, args);
	}
}
