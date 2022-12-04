package com.cibnvideo.common.controller;


import org.springframework.stereotype.Controller;

import com.cibnvideo.common.entity.User;
import com.cibnvideo.common.utils.ShiroUtils;

@Controller
public class BaseController {
	public User getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}

	public Long getVenderId(){ return getUser().getVenderId();}
}