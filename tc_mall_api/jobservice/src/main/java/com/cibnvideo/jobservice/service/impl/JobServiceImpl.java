package com.cibnvideo.jobservice.service.impl;


import com.cibnvideo.jobservice.dao.TaskDao;
import com.cibnvideo.jobservice.model.ScheduleJob;
import com.cibnvideo.jobservice.model.TaskDO;
import com.cibnvideo.jobservice.quartz.utils.QuartzManager;
import com.cibnvideo.jobservice.quartz.utils.ScheduleJobUtils;
import com.cibnvideo.jobservice.service.JobService;
import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JobServiceImpl implements JobService {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TaskDao taskMapper;

	@Autowired
	QuartzManager quartzManager;

	@Override
	public TaskDO get(Long id) {
		return taskMapper.get(id);
	}

	@Override
	public List<TaskDO> list(Map<String, Object> map) {
		return taskMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return taskMapper.count(map);
	}

	@Override
	public int save(TaskDO taskDO) {
		taskDO.setCtime(new Date());
		taskDO.setUtime(new Date());
		return taskMapper.save(taskDO);
	}

	@Override
	public int update(TaskDO taskDO) {
		taskDO.setUtime(new Date());
		return taskMapper.update(taskDO);
	}

	@Override
	public int remove(Long id) {
		try {
			TaskDO taskDO = get(id);
			quartzManager.deleteJob(ScheduleJobUtils.entityToData(taskDO));
			return taskMapper.remove(id);
		} catch (SchedulerException e) {
			logger.error(e.toString());
			return 0;
		}

	}

	@Override
	public int batchRemove(Long[] ids) {
		for (Long id : ids) {
			try {
				TaskDO taskDO = get(id);
				quartzManager.deleteJob(ScheduleJobUtils.entityToData(taskDO));
			} catch (SchedulerException e) {
				logger.error(e.toString());
				return 0;
			}
		}
		return taskMapper.batchRemove(ids);
	}

	@Override
	public int changeStatus(Long jobId, String cmd) throws SchedulerException {
		TaskDO taskDO = get(jobId);
		if (taskDO == null) {
			return 0;
		}
		if ("stop".equals(cmd)) {
			quartzManager.deleteJob(ScheduleJobUtils.entityToData(taskDO));
			taskDO.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
		} else {
			if (!"start".equals(cmd)) {
			} else {
				taskDO.setJobStatus(ScheduleJob.STATUS_RUNNING);
                quartzManager.addJob(ScheduleJobUtils.entityToData(taskDO));
            }
		}
		return update(taskDO);
	}

	@Override
	public int run(Long id) {
		TaskDO taskDO = get(id);
		if(taskDO == null) {
			return 0;
		}
		try {
			quartzManager.runAJobNow(ScheduleJobUtils.entityToData(taskDO));
		} catch (Exception e) {
			logger.error(e.toString());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateCron(Long jobId) throws SchedulerException {
		TaskDO taskDO = get(jobId);
		if (taskDO == null) {
			return 0;
		}
		if (ScheduleJob.STATUS_RUNNING.equals(taskDO.getJobStatus())) {
			quartzManager.updateJobCron(ScheduleJobUtils.entityToData(taskDO));
		}
		return update(taskDO);
	}

	@Override
	public int updateStartTime(Long taskId) {
		return taskMapper.updateStartTime(taskId);
	}

	@Override
	public int updateEndTime(Long taskId, int resultStatus, String msg) {
		return taskMapper.updateEndTime(taskId, resultStatus, msg);
	}
}
