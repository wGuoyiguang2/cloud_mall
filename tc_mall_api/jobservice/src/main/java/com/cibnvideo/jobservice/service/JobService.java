package com.cibnvideo.jobservice.service;

import com.cibnvideo.jobservice.model.TaskDO;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.Map;

public interface JobService {
	
	TaskDO get(Long id);
	
	List<TaskDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TaskDO taskScheduleJob);
	
	int update(TaskDO taskScheduleJob);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	int changeStatus(Long jobId, String cmd) throws SchedulerException;

	int run(Long id);

	int updateCron(Long jobId) throws SchedulerException;

	int updateStartTime(Long taskId);

	int updateEndTime(Long taskId, int resultStatus, String msg);
}
