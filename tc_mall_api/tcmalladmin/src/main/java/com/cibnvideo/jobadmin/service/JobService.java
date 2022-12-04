package com.cibnvideo.jobadmin.service;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.jobadmin.model.TaskDO;

import java.util.Map;

public interface JobService {

	TaskDO get(Long id);

	Result<TaskDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(TaskDO taskScheduleJob);

	int update(TaskDO taskScheduleJob);

	int remove(Long id);

	int batchRemove(Long[] ids);

	int changeStatus(Long jobId, String cmd);

	int run(Long jobId);
}
