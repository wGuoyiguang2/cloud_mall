package com.cibnvideo.common.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.entity.Menu;
import com.cibnvideo.common.entity.Tree;
import com.cibnvideo.common.entity.User;
import com.cibnvideo.common.service.MenuService;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.ShiroUtils;

@Controller
public class LoginController extends BaseController{
	
	@Autowired
	MenuService menuService;
	
	@Log("请求登录页面")
	@GetMapping("login")
	public String loginGet(){
		return "login/login";
	}
	@Log("请求登录")
	@PostMapping("login")
	@ResponseBody
	public R loginPost(String username, String password){
		password = com.cibnvideo.common.utils.MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return R.ok();
		} catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}
	
	@Log("请求/")
	@GetMapping("/")
	public String fms(Model model) {
		return "redirect:/index";
	}
	
	@Log("请求访问主页")
	@GetMapping("/index")
	public String index(Model model) {
		User user = ShiroUtils.getUser();
		List<Tree<Menu>> menuTree = menuService.listMenuTree(user.getUserId());
		model.addAttribute("menus", menuTree);
		model.addAttribute("name", user.getName());
		model.addAttribute("username", user.getUsername());
		return "index";
	}
	@Log("退出登录")
	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@Log("Error Code 403")
	@GetMapping("/403")
	String error403() {
		return "403";
	}
	
}
