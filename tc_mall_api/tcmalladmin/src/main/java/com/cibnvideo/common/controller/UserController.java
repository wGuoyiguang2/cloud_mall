package com.cibnvideo.common.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.entity.Role;
import com.cibnvideo.common.entity.Tree;
import com.cibnvideo.common.entity.User;
import com.cibnvideo.common.entity.UserVO;
import com.cibnvideo.common.entity.Vender;
import com.cibnvideo.common.service.RoleService;
import com.cibnvideo.common.service.UserService;
import com.cibnvideo.common.utils.MD5Utils;
import com.cibnvideo.common.utils.PageUtils;
import com.cibnvideo.common.utils.R;

@RequestMapping("/sys/user")
@Controller
public class UserController extends BaseController {
	private String prefix="system/user"  ;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@Log("访问/sys/user")
	@RequiresPermissions("sys:user:user")
	@GetMapping("")
	String user(Model model) {
		return prefix + "/user";
	}

	@Log("访问/sys/user/list")
	@GetMapping("/list")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		List<User> sysUserList = userService.list(params);
		int total = userService.count(params);
		PageUtils pageUtil = new PageUtils(sysUserList, total);
		return pageUtil;
	}

	@Log("访问/sys/user/add 用户添加界面")
	@RequiresPermissions("sys:user:add")
	@GetMapping("/add")
	String add(Model model) {
		List<Role> roles = roleService.list();
		model.addAttribute("roles", roles);
		return prefix + "/add";
	}

	@Log("访问/sys/user/edit")
	@RequiresPermissions("sys:user:edit")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		User user = userService.get(id);
		model.addAttribute("user", user);
		List<Role> roles = roleService.list(id);
		model.addAttribute("roles", roles);
		return prefix+"/edit";
	}

	@Log("访问/sys/user/add 用户添加接口")
	@RequiresPermissions("sys:user:add")
	@PostMapping("/save")
	@ResponseBody
	R save(User user) {
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		if (userService.save(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@Log("访问/sys/user/update 编辑界面")
	@RequiresPermissions("sys:user:edit")
	@PostMapping("/update")
	@ResponseBody
	R update(User user) {
		if (userService.update(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@Log("访问/sys/user 编辑接口")
	@RequiresPermissions("sys:user:edit")
	@PostMapping("/updatePeronal")
	@ResponseBody
	R updatePeronal(User user) {
		if (userService.updatePersonal(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@Log("访问/sys/user/remove 删除用户")
	@RequiresPermissions("sys:user:remove")
	@PostMapping("/remove")
	@ResponseBody
	R remove(Long id) {
		if (userService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@Log("访问/sys/user/batchRemove 批量删除")
	@RequiresPermissions("sys:user:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Long[] userIds) {
		for(Long id:userIds) {
			userService.remove(id);
		}
		return R.ok();
	}

	@Log("访问/sys/user/exit 用户是否存在")
	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !userService.exit(params);
	}

	@Log("访问/sys/user/resetPwd 重置密码界面")
	@RequiresPermissions("sys:user:resetPwd")
	@GetMapping("/resetPwd/{id}")
	String resetPwd(@PathVariable("id") Long userId, Model model) {

		User user = new User();
		user.setUserId(userId);
		model.addAttribute("user", user);
		return prefix + "/reset_pwd";
	}

	@Log("访问/sys/user/resetPwd 重置密码 接口")
	@PostMapping("/resetPwd")
	@ResponseBody
	R resetPwd(UserVO userVO) {
		try{
			userService.resetPwd(userVO,getUser());
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}
	@Log("访问/sys/user/adminResetPwd")
	@RequiresPermissions("sys:user:resetPwd")
	@PostMapping("/adminResetPwd")
	@ResponseBody
	R adminResetPwd(UserVO userVO) {
		try{
			userService.adminResetPwd(userVO);
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}
	@Log("访问/sys/user/tree")
	@GetMapping("/tree")
	@ResponseBody
	public Tree<Vender> tree() {
		Tree<Vender> tree = new Tree<Vender>();
		tree = userService.getTree();
		return tree;
	}
	@Log("访问/sys/user/treeView")
	@GetMapping("/treeView")
	String treeView() {
		return  prefix + "/userTree";
	}

	@Log("访问/sys/user/personal")
	@GetMapping("/personal")
	String personal(Model model) {
		User user  = userService.get(getUserId());
		model.addAttribute("user",user);
		return prefix + "/personal";
	}
}
