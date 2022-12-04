package com.cibnvideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
//import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
//@EnableZipkinStreamServer
public class TcMallAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(TcMallAdminApplication.class, args);
	}
}
