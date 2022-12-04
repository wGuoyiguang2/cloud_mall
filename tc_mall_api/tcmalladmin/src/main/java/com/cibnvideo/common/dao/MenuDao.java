package com.cibnvideo.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cibnvideo.common.entity.Menu;

@Mapper
public interface MenuDao {

	Menu get(Long menuId);
	
	List<Menu> list(Map<String, Object> params);
	
	int save(Menu menu);
	
	int update(Menu menu);
	
	int remove(Long menuId);
	
	int countChildren(Long parentId);

	
	List<Menu> listMenuByUserId(Long id);
	
	List<String> listUserPerms(Long id);
}
