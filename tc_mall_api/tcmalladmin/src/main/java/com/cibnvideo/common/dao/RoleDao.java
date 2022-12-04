package com.cibnvideo.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cibnvideo.common.entity.Role;

@Mapper
public interface RoleDao {

	Role get(Long roleId);
	
	List<Role> list(Map<String, Object> param);
	
	int count(Map<String, Object> param);
	
	int save(Role role);
	
	int update(Role role);
	
	int remove(Long roleId);
	
}
