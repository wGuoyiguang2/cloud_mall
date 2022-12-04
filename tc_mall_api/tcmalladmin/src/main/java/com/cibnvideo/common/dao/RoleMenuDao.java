package com.cibnvideo.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cibnvideo.common.entity.RoleMenu;

@Mapper
public interface RoleMenuDao {

	RoleMenu get(Long id);
	
	List<RoleMenu> list(Map<String, Object> params);
	
	int count(Map<String, Object> params);
	
	int save(RoleMenu roleMenu);
	
	int update(RoleMenu roleMenu);
	
	int remove(Long id);
	
	List<Long> listMenuIdByRoleId(Long roleId);
	
	int removeByRoleId(Long roleId);
	
}
