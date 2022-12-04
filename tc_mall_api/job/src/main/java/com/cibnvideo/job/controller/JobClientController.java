package com.cibnvideo.job.controller;

import com.cibnvideo.job.model.JobRequest;
import com.cibnvideo.job.model.JobResponse;
import com.cibnvideo.job.service.JobExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: likai
 * @description description
 * @Date: 19-1-15 上午9:57
 */
@RestController
public class JobClientController {

    @Autowired
    JobExecutorService jobExecutorService;

    @GetMapping("/v1/job/request")
    JobResponse job(JobRequest jobRequest) {
        Long jobId = jobRequest.getJobId();
        String serviceName = jobRequest.getJobServiceName();
        String jobName = jobRequest.getJobName();
        JobResponse jobResponse = new JobResponse();
        jobExecutorService.execute(jobRequest);
        jobResponse.setJobId(jobId);
        jobResponse.setServiceName(serviceName);
        jobResponse.setJobName(jobName);
        jobResponse.setMessage("正在执行");
        jobResponse.setStatus(1);
        return jobResponse;
    }
}
