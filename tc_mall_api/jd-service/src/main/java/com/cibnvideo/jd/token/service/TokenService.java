package com.cibnvideo.jd.token.service;


/**
 * @Description: token service
 * @Author: WangBin
 * @Date: 2018/6/22 14:49
 */
public interface TokenService {
    String getToken();
    void refreshToken();
}
