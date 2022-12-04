package com.cibnvideo.jd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by makuan on 18-6-20.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class JdServiceApplication {
    private static Logger log  = LoggerFactory.getLogger(JdServiceApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(JdServiceApplication.class, args);
        log.info("===JdApiApplication启动成功===");
    }
}
