package com.cibnvideo.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cibnvideo.common.entity.Vender;
import com.cibnvideo.common.entity.Tree;
import com.cibnvideo.common.entity.User;
import com.cibnvideo.common.entity.UserVO;

@Service
public interface UserService {
	User get(Long id);

	List<User> list(Map<String, Object> param);

	int count(Map<String, Object> param);
	
	int save(User user);

	int update(User user);

	int remove(Long userId);


	boolean exit(Map<String, Object> params);

	Set<String> listRoles(Long userId);

	int resetPwd(UserVO userVO,User userDO) throws Exception;
	int adminResetPwd(UserVO userVO) throws Exception;
	Tree<Vender> getTree();

	/**
	 * 更新个人信息
	 * @param userDO
	 * @return
	 */
	int updatePersonal(User user);

}
