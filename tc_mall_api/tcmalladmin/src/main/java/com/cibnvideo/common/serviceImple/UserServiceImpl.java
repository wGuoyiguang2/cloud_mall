package com.cibnvideo.common.serviceImple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibnvideo.common.dao.VenderDao;
import com.cibnvideo.common.dao.UserDao;
import com.cibnvideo.common.dao.UserRoleDao;
import com.cibnvideo.common.entity.Vender;
import com.cibnvideo.common.entity.Tree;
import com.cibnvideo.common.entity.User;
import com.cibnvideo.common.entity.UserRole;
import com.cibnvideo.common.entity.UserVO;
import com.cibnvideo.common.service.UserService;
import com.cibnvideo.common.utils.BuildTree;
import com.cibnvideo.common.utils.MD5Utils;


@Transactional
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;
	@Autowired
	UserRoleDao userRoleDao;
	@Autowired
	VenderDao venderDao;



	@Override
	public User get(Long id) {
		List<Long> roleIds = userRoleDao.listRoleId(id);
		User user = userDao.get(id);
		if(user.getVenderId() != null) {
			user.setVenderName(venderDao.get(user.getVenderId()).getName());
		}
		user.setRoleIds(roleIds);
		return user;
	}

	@Override
	public List<User> list(Map<String, Object> param) {
		return userDao.list(param);
	}

	@Override
	public int count(Map<String, Object> param) {
		return userDao.count(param);
	}

	@Transactional
	@Override
	public int save(User user) {
		int count = userDao.save(user);
		Long userId = user.getUserId();
		List<Long> roles = user.getRoleIds();
		userRoleDao.removeByUserId(userId);
		List<UserRole> list = new ArrayList<>();
		for (Long roleId : roles) {
			UserRole ur = new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		if (list.size() > 0) {
			for(UserRole ur:list) {
				userRoleDao.save(ur);
			}
		}
		return count;
	}

	@Override
	public int update(User user) {
		int r = userDao.update(user);
		Long userId = user.getUserId();
		List<Long> roles = user.getRoleIds();
		userRoleDao.removeByUserId(userId);
		List<UserRole> list = new ArrayList<>();
		for (Long roleId : roles) {
			UserRole ur = new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		if (list.size() > 0) {
			for(UserRole ur:list) {
				userRoleDao.save(ur);
			}
		}
		return r;
	}

	@Override
	public int remove(Long userId) {
		userRoleDao.removeByUserId(userId);
		return userDao.remove(userId);
	}

	@Override
	public boolean exit(Map<String, Object> param) {
		boolean exit;
		exit = userDao.list(param).size() > 0;
		return exit;
	}

	@Override
	public Set<String> listRoles(Long userId) {
		return null;
	}

	@Override
	public int resetPwd(UserVO userVO,User user) throws Exception {
		if(Objects.equals(userVO.getUser().getUserId(),user.getUserId())){
			if(Objects.equals(MD5Utils.encrypt(user.getUsername(),userVO.getPwdOld()),user.getPassword())){
				user.setPassword(MD5Utils.encrypt(user.getUsername(),userVO.getPwdNew()));
				return userDao.update(user);
			}else{
				throw new Exception("输入的旧密码有误！");
			}
		}else{
			throw new Exception("你修改的不是你登录的账号！");
		}
	}
	@Override
	public int adminResetPwd(UserVO userVO) throws Exception {
		User user =get(userVO.getUser().getUserId());
		if("admin".equals(user.getUsername())){
			throw new Exception("超级管理员的账号不允许直接重置！");
		}
		user.setPassword(MD5Utils.encrypt(user.getUsername(), userVO.getPwdNew()));
		return userDao.update(user);


	}


	@Override
	public Tree<Vender> getTree() {
		List<Tree<Vender>> trees = new ArrayList<Tree<Vender>>();
		List<Vender> venders = venderDao.list(new HashMap<String, Object>());
		Long[] pVenders = venderDao.listParentVender();
		Long[] uVenders = userDao.listAllVender();
		Long[] allVenders = (Long[]) ArrayUtils.addAll(pVenders, uVenders);
		for (Vender vender : venders) {
			if (!ArrayUtils.contains(allVenders, vender.getVenderId())) {
				continue;
			}
			Tree<Vender> tree = new Tree<Vender>();
			tree.setId(vender.getVenderId().toString());
			tree.setParentId(vender.getParentId().toString());
			tree.setText(vender.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "dept");
			tree.setState(state);
			trees.add(tree);
		}
		List<User> users = userDao.list(new HashMap<String, Object>());
		for (User user : users) {
			Tree<Vender> tree = new Tree<Vender>();
			tree.setId(user.getUserId().toString());
			tree.setParentId(user.getVenderId().toString());
			tree.setText(user.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "user");
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<Vender> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public int updatePersonal(User user) {
		return userDao.update(user);
	}

}
