package com.hqtc.searchtask;

import com.cibnvideo.job.annotation.JobClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableAsync
@JobClient
public class SearchtaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchtaskApplication.class, args);
    }
}
