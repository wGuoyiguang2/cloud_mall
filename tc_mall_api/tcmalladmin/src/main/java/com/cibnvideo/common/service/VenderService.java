package com.cibnvideo.common.service;

import java.util.List;
import java.util.Map;

import com.cibnvideo.common.entity.Vender;
import com.cibnvideo.common.entity.Tree;

public interface VenderService {
	
	Vender get(Long venderId);
	
	List<Vender> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(Vender sysVender);
	
	int update(Vender sysVender);
	
	int remove(Long venderId);
	
	int batchRemove(Long[] venderIds);

	Tree<Vender> getTree();
	
	boolean checkVenderHasUser(Long venderId);
}
