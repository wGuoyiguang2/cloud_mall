package com.cibnvideo.common.controller;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.entity.UserOnline;
import com.cibnvideo.common.service.SessionService;
import com.cibnvideo.common.utils.R;


@RequestMapping("/sys/online")
@Controller
public class SessionController {
	@Autowired
	SessionService sessionService;

	@RequiresPermissions("admin:online:list")
	@GetMapping()
	public String online() {
		return "system/online/online";
	}

	@Log("列出在线用户")
	@ResponseBody
	@RequiresPermissions("admin:online:list")
	@RequestMapping("/list")
	public List<UserOnline> list() {
		return sessionService.list();
	}

	@Log("强制退出用户")
	@ResponseBody
	@RequestMapping("/forceLogout/{sessionId}")
	public R forceLogout(@PathVariable("sessionId") String sessionId, RedirectAttributes redirectAttributes) {
		try {
			sessionService.forceLogout(sessionId);
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}

	}

	@Log("获取会话列表")
	@ResponseBody
	@RequestMapping("/sessionList")
	public Collection<Session> sessionList() {
		return sessionService.sessionList();
	}


}
