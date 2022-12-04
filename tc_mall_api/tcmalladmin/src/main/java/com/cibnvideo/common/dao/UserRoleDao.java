package com.cibnvideo.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cibnvideo.common.entity.UserRole;

@Mapper
public interface UserRoleDao {
	
	UserRole get(Long id);

	List<UserRole> list(Map<String, Object> params);

	int count(Map<String, Object> params);

	int save(UserRole userRole);

	int update(UserRole userRole);

	int remove(Long id);

	List<Long> listRoleId(Long userId);

	int removeByUserId(Long userId);

}
