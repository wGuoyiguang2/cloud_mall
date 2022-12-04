package com.cibnvideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableTransactionManagement
public class JdSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdSyncApplication.class, args);
    }
}
