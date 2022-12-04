package com.cibnvideo.jobadmin.service.impl;


import com.cibnvideo.common.utils.Result;
import com.cibnvideo.jobadmin.jobserviceapi.JobServiceApi;
import com.cibnvideo.jobadmin.model.TaskDO;
import com.cibnvideo.jobadmin.service.JobService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JobServiceImpl implements JobService {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JobServiceApi jobServiceApi;

	@Override
	public TaskDO get(Long id) {
		ResultData<TaskDO> resultData = jobServiceApi.get(id);
		if(resultData.getError() == ErrorCode.SUCCESS) {
			return resultData.getData();
		} else {
			logger.error(resultData.getMsg());
		}
		return null;
	}

	@Override
	public Result<TaskDO> list(Map<String, Object> map) {
		ResultData<Result<TaskDO>> resultData = jobServiceApi.list(map);
		if(resultData.getError() == ErrorCode.SUCCESS) {
			return resultData.getData();
		}else {
			logger.error(resultData.getMsg());
			return null;
		}
	}

	@Override
	public int count(Map<String, Object> map) {
		ResultData<Integer> resultData = jobServiceApi.count(map);
		if(resultData.getError() == ErrorCode.SUCCESS) {
			return resultData.getData();
		} else {
			logger.error(resultData.getMsg());
		}
		return 0;
	}

	@Override
	public int save(TaskDO taskScheduleJob) {
		ResultData<Integer> resultData = jobServiceApi.add(taskScheduleJob);
		if(resultData.getError() == ErrorCode.SUCCESS) {
			Integer result = resultData.getData();
			if(result != null && result == 1) {
				return 1;
			} else {
				logger.error("task save failed");
			}
		} else {
			logger.error(resultData.getMsg());
		}
		return 0;
	}

	@Override
	public int update(TaskDO taskScheduleJob) {
		ResultData<Integer> resultData = jobServiceApi.update(taskScheduleJob);
		if(resultData.getError() == ErrorCode.SUCCESS) {
			Integer result = resultData.getData();
			if(result != null && result == 1) {
				return 1;
			} else {
				logger.error("任务更新失败");
			}
		}else {
			logger.error(resultData.getMsg());
		}
		return 0;
	}

	@Override
	public int remove(Long id) {
		ResultData<Integer> resultData = jobServiceApi.remove(id);
		if(resultData.getError() == ErrorCode.SUCCESS) {
			Integer result = resultData.getData();
			if(result != null && result == 1) {
				return 1;
			} else {
				logger.error("任务删除失败");
			}
		}else {
			logger.error(resultData.getMsg());
		}
		return 0;

	}

	@Override
	public int batchRemove(Long[] ids) {
		ResultData<Integer> resultData = jobServiceApi.batchRemove(ids);
		if(resultData.getError() == ErrorCode.SUCCESS) {
			Integer result = resultData.getData();
			if(result != null && result > 0) {
				return 1;
			} else {
				logger.error("任务批量删除失败");
			}
		}else {
			logger.error(resultData.getMsg());
		}
		return 0;
	}

	@Override
	public int changeStatus(Long jobId, String cmd) {
		ResultData<Integer> resultData = jobServiceApi.changeStatus(jobId, cmd);
		if(resultData.getError() == ErrorCode.SUCCESS) {
			Integer result = resultData.getData();
			if(result != null && result == 1) {
				return 1;
			} else {
				logger.error("任务修改失败");
			}
		}else {
			logger.error(resultData.getMsg());
		}
		return 0;
	}

	@Override
	public int run(Long jobId) {
		ResultData<Integer> resultData = jobServiceApi.run(jobId);
		if(resultData.getError() == ErrorCode.SUCCESS) {
			Integer result = resultData.getData();
			if(result != null && result == 1) {
				return 1;
			} else {
				logger.error("执行失败");
			}
		}else {
			logger.error(resultData.getMsg());
		}
		return 0;
	}
}
