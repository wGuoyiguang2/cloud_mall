package com.cibnvideo.jobservice.task;

import com.cibnvideo.jobservice.model.JobResponse;
import com.cibnvideo.jobservice.model.JobResult;
import com.cibnvideo.jobservice.model.TaskDO;
import com.cibnvideo.jobservice.service.JobService;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CommonTask implements Job {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.application.name}")
    String jobServiceName;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    JobService jobService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("test task start ...");
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        Long jobId = jobDataMap.getLong("jobId");
        String serviceName = jobDataMap.getString("serviceName");
        String jobName = jobDataMap.getString("jobName");
        String param = jobDataMap.getString("param");
        if(StringUtils.isEmpty(serviceName)) {
            jobService.updateEndTime(jobId, 0, "任务服务名为空");
            return;
        }
        if(StringUtils.isEmpty(jobName)) {
            jobService.updateEndTime(jobId, 0, "任务名为空");
            return;
        }
        String url = "http://" + serviceName + "/v1/job/request?jobId={1}&jobServiceName={2}&jobName={3}&param={4}";
        jobService.updateStartTime(jobId);
        try {
            JobResponse jobResponse = restTemplate.getForObject(url, JobResponse.class, jobId, jobServiceName, jobName, param);
            if (jobResponse != null) {
                jobService.updateEndTime(jobId, jobResponse.getStatus(), jobResponse.getMessage());
            } else {
                jobService.updateEndTime(jobId, 0, "任务服务返回结果为空");
            }
        }catch (Exception e) {
            jobService.updateEndTime(jobId, 0, e.toString());
        }
    }
}
