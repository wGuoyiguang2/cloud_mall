package com.cibnvideo.common.serviceImple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibnvideo.common.dao.VenderDao;
import com.cibnvideo.common.entity.Tree;
import com.cibnvideo.common.entity.Vender;
import com.cibnvideo.common.service.VenderService;
import com.cibnvideo.common.utils.BuildTree;



@Service
public class VenderServiceImpl implements VenderService {
	@Autowired
	private VenderDao sysVenderDao;

	@Override
	public Vender get(Long venderId){
		return sysVenderDao.get(venderId);
	}

	@Override
	public List<Vender> list(Map<String,Object> map){
		return sysVenderDao.list(map);
	}

	@Override
	public int count(Map<String,Object> map){
		return sysVenderDao.count(map);
	}

	@Override
	public int save(Vender sysVender){
		return sysVenderDao.save(sysVender);
	}

	@Override
	public int update(Vender sysVender){
		return sysVenderDao.update(sysVender);
	}

	@Override
	public int remove(Long venderId){
		return sysVenderDao.remove(venderId);
	}

	@Override
	public int batchRemove(Long[] venderIds){
		int r = 0;
		for(Long d:venderIds) {
			r = sysVenderDao.remove(d);
		}
		return r;
	}

	@Override
	public Tree<Vender> getTree(){
		List<Tree<Vender>> trees = new ArrayList<Tree<Vender>>();
		List<Vender> sysVenders = sysVenderDao.list(new HashMap<String,Object>(16));
		for (Vender sysVender : sysVenders) {
			Tree<Vender> tree = new Tree<Vender>();
			tree.setId(sysVender.getVenderId().toString());
			tree.setParentId(sysVender.getParentId().toString());
			tree.setText(sysVender.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<Vender> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public boolean checkVenderHasUser(Long venderId) {
		// TODO Auto-generated method stub
		//查询厂商以及此厂商的下级厂商
		int result = sysVenderDao.getVenderUserNumber(venderId);
		return result==0?true:false;
	}

}
