package com.cibnvideo.job.service.impl;

import com.cibnvideo.job.config.Constant;
import com.cibnvideo.job.model.JobRequest;
import com.cibnvideo.job.model.JobResult;
import com.cibnvideo.job.service.JobExecutorService;
import com.cibnvideo.job.service.JobService;
import com.cibnvideo.job.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: likai
 * @description description
 * @Date: 19-1-15 上午10:10
 */
@Service
public class JobExecutorServiceImpl implements JobExecutorService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.application.name}")
    String serviceName;

    @Autowired
    RestTemplate restTemplate;

    @Override
    @Async
    public void execute(JobRequest jobRequest) {
        Long jobId = jobRequest.getJobId();
        String jobServiceName = jobRequest.getJobServiceName();
        String jobName = jobRequest.getJobName();
        String param = jobRequest.getParam();
        int status = Constant.JOB_FAILED;
        String message = "";
        Object object = SpringUtil.getBean(jobName);
        if(object != null) {
            if(object instanceof JobService) {
                JobResult jobResult =  ((JobService) object).execute(param);
                status = jobResult.getStatus();
                message = jobResult.getMessage();
            } else {
                message = "任务" + jobName + "执行方法不存在";
            }
        } else {
            message = "任务" + jobName + "不存在";
        }
        try {
            String url = "http://" + jobServiceName + "/v1/job/response?jobId={1}&serviceName={2}&jobName={3}&status={4}&message={5}";
            Integer result = restTemplate.getForObject(url, Integer.class, jobId, serviceName, jobName, status, message);
        }catch (Exception e) {
            logger.error("job response Exception: " + e.toString());
        }
    }
}
