package com.cibnvideo.job.model;

import java.io.Serializable;

/**
 * @Author: likai
 * @description description
 * @Date: 19-1-15 上午11:50
 */
public class JobResponse implements Serializable {
    private Long jobId;
    private String serviceName;
    private String jobName;
    private int status;
    private String message;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
