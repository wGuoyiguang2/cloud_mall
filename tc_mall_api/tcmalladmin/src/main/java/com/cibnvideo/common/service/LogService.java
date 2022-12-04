package com.cibnvideo.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibnvideo.common.entity.LogDO;


@Service
public interface LogService {
	void save(LogDO logDO);
	List<LogDO> queryList(LogDO log);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
