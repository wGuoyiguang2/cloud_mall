package com.cibnvideo.common.serviceImple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cibnvideo.common.dao.LogDao;
import com.cibnvideo.common.entity.LogDO;
import com.cibnvideo.common.service.LogService;


@Service
public class LogServiceImpl implements LogService {
	@Autowired
	LogDao logDao;

	@Async
	@Override
	public void save(LogDO logDO) {
		logDao.save(logDO);
	}

	@Override
	public List<LogDO> queryList(LogDO log) {
		List<LogDO> logs = logDao.list(log);
		return logs;
	}

	@Override
	public int remove(Long id) {
		int count = logDao.remove(id);
		return count;
	}

	@Override
	public int batchRemove(Long[] ids){
		int r = 0;
		for(Long l:ids) {
			r = logDao.remove(l);
		}
		return r;
	}
}
