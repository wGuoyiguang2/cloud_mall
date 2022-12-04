package com.cibnvideo.jobservice.quartz.utils;


import com.cibnvideo.jobservice.model.ScheduleJob;
import com.cibnvideo.jobservice.model.TaskDO;
import org.quartz.JobDataMap;

public class ScheduleJobUtils {
	public static ScheduleJob entityToData(TaskDO taskDO) {
		ScheduleJob scheduleJob = new ScheduleJob();
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("jobId", taskDO.getId());
		jobDataMap.put("serviceName", taskDO.getServiceName());
		jobDataMap.put("jobName", taskDO.getJobName());
		jobDataMap.put("param", taskDO.getJobParam());
		scheduleJob.setJobName(taskDO.getJobName());
		scheduleJob.setJobGroup(taskDO.getJobGroup());
		scheduleJob.setCronExpression(taskDO.getCronExpression());
		scheduleJob.setJobDataMap(jobDataMap);
		return scheduleJob;
	}
}