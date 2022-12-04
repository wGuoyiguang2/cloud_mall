package com.cibnvideo.common.service;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.cibnvideo.common.entity.User;
import com.cibnvideo.common.entity.UserOnline;


@Service
public interface SessionService {
	List<UserOnline> list();

	List<User> listOnlineUser();

	Collection<Session> sessionList();
	
	boolean forceLogout(String sessionId);
}
