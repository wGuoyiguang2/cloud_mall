package com.cibnvideo.common.dao;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.cibnvideo.common.entity.LogDO;
import com.cibnvideo.common.sqlprovider.LogProvider;

@Mapper
public interface LogDao {

	@Select("SELECT `id`,`user_id`,`username`,`operation`,`time`,`method`,`params`,`ip`,`gmt_create` FROM sys_log WHERE id = #{id}")
	@Results({ 
	@Result(column="user_id", property="userId"),
	@Result(column="gmt_create", property="gmtCreate"),
	})
	LogDO get(Long id);
	
	@SelectProvider(type=LogProvider.class, method="list")
	@Results({ 
	@Result(column="user_id", property="userId"),
	@Result(column="gmt_create", property="gmtCreate"),
	})
	List<LogDO> list(LogDO log);
	
	@SelectProvider(type=LogProvider.class, method="count")
	int count(LogDO log);
	
	@Insert("INSERT INTO sys_log(`user_id`,`username`, `operation`, `time`, `method`, `params`, `ip`, `gmt_create`) "
			+ "VALUES (#{userId}, #{username}, #{operation}, #{time}, #{method}, #{params}, #{ip}, #{gmtCreate})")
	int save(LogDO log);
	
	@UpdateProvider(type=LogProvider.class, method="update")
	int update(LogDO log);
	
	@Delete("delete from sys_log where id = #{id}")
	int remove(Long id);
	
}
