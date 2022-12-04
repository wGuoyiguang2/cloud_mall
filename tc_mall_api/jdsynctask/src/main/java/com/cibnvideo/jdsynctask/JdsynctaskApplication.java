package com.cibnvideo.jdsynctask;

import com.cibnvideo.job.annotation.JobClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableTransactionManagement
@EnableAsync
@JobClient
public class JdsynctaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdsynctaskApplication.class, args);
    }
}
