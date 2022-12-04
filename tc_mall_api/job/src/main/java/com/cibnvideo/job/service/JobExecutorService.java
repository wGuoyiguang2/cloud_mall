package com.cibnvideo.job.service;

import com.cibnvideo.job.model.JobRequest;

/**
 * @Author: likai
 * @description description
 * @Date: 19-1-15 上午10:08
 */
public interface JobExecutorService {
    void execute(JobRequest jobRequest);
}
