package com.cibnvideo.job.config;

import com.cibnvideo.job.controller.JobClientController;
import com.cibnvideo.job.service.impl.JobExecutorServiceImpl;
import com.cibnvideo.job.utils.SpringUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author: likai
 * @description description
 * @Date: 19-1-15 上午11:17
 */
@Configuration
@Import({
        JobExecutorServiceImpl.class,
        JobClientController.class,
        SpringUtil.class,
        RestTemplateConfiguration.class
})
public class JobClientConfiguration {
}
