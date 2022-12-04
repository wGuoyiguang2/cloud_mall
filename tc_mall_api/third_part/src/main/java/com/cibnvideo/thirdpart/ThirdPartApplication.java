package com.cibnvideo.thirdpart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/21 17:23
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableScheduling
public class ThirdPartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThirdPartApplication.class, args);
    }
}
