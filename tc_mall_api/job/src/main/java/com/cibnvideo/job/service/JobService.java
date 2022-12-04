package com.cibnvideo.job.service;

import com.cibnvideo.job.model.JobResult;

/**
 * @Author: likai
 * @description description
 * @Date: 19-1-15 上午11:48
 */
public interface JobService {
    JobResult execute(String param);
}
