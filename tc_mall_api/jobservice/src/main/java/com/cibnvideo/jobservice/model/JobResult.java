package com.cibnvideo.jobservice.model;

/**
 * @Author: likai
 * @description description
 * @Date: 19-1-15 上午11:50
 */
public class JobResult {
    private long jobId;
    private int status;
    private String message;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
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

    @Override
    public String toString() {
        return "JobResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
