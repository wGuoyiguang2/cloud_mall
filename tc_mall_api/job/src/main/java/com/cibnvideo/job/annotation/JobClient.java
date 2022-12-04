package com.cibnvideo.job.annotation;

import com.cibnvideo.job.config.JobClientConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

/**
 * @Author: likai
 * @description 任务服务客户端
 * @Date: 19-1-15 上午11:14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableAsync
@Import(JobClientConfiguration.class)
public @interface JobClient {
}
