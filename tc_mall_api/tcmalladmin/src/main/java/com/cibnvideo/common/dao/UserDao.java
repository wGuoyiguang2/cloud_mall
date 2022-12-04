package com.cibnvideo.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cibnvideo.common.entity.User;

@Mapper
public interface UserDao {

	User get(Long userId);
	
	List<User> list(Map<String, Object> param);
	
	int count(Map<String, Object> param);
	
	int save(User user);
	
	int update(User user);
	
	int remove(Long userId);
	
	Long[] listAllVender();

}
