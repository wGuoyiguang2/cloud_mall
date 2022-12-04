package com.cibnvideo.job.model;

import java.io.Serializable;

/**
 * @Author: likai
 * @description description
 * @Date: 19-1-15 上午11:50
 */
public class JobRequest implements Serializable {
    private Long jobId;
    private String jobServiceName;
    private String jobName;
    private String param;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobServiceName() {
        return jobServiceName;
    }

    public void setJobServiceName(String jobServiceName) {
        this.jobServiceName = jobServiceName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "JobRequest{" +
                "jobId=" + jobId +
                ", jobServiceName='" + jobServiceName + '\'' +
                ", jobName='" + jobName + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
}
