package com.cibnvideo.jd.token.service.impl;

import com.cibnvideo.jd.common.config.BaseConfig;
import com.cibnvideo.jd.common.utils.HttpClientUtil;
import com.cibnvideo.jd.mail.MailService;
import com.cibnvideo.jd.token.constants.GrantTypeEnum;
import com.cibnvideo.jd.token.params.TokenRequestParams;
import com.cibnvideo.jd.token.params.TokenResponseParams;
import com.cibnvideo.jd.token.service.TokenService;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Description: token service impl
 * @Author: WangBin
 * @Date: 2018/6/22 14:49
 */
@Service
public class TokenServiceImpl implements TokenService{

    private static Logger log  = LoggerFactory.getLogger(TokenServiceImpl.class);
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private BaseConfig baseConfig;
    @Autowired
    private MailService mailService;
    /**
     * 从redis获取token
     * @return
     */
    @Override
    public String getToken() {
        String token=redisTemplate.opsForValue().get(baseConfig.getJdTokenKey());
        log.info("从redis获取token:"+token);
        return token;
    }

    /**
     * 将token放入redis
     * @param responseParams
     */
    private void setToken2Redis(TokenResponseParams responseParams){
        String token = responseParams.getAccess_token();
        Long expiresIn = responseParams.getExpires_in();
        redisTemplate.opsForValue().set(baseConfig.getJdTokenKey(), token, expiresIn, TimeUnit.SECONDS);
    }
    @Override
    public void refreshToken(){
        //如果是非线上环境，则返回
        if(!"prod".equals(baseConfig.getProfiles())){
            log.error("检测到为非线上环境，禁止刷新token!");
            return;
        }
        Gson gson=new Gson();
        TokenRequestParams requestParams=new TokenRequestParams();
        requestParams.setApp_key(baseConfig.getAppKey());
        requestParams.setApp_secret(baseConfig.getAppSecret());
        requestParams.setPassword(baseConfig.getPassword());
        requestParams.setUsername(baseConfig.getUsername());
        requestParams.setGrant_type(GrantTypeEnum.REFRESH_TOKEN.getValue());
        String json=HttpClientUtil.post(baseConfig.getTokenUrl(),requestParams,"UTF-8",true);
        log.error("刷新京东token返回结果:"+json);
        TokenResponseParams responseParams=gson.fromJson(json,TokenResponseParams.class);
        if(responseParams!=null&&StringUtils.isNotEmpty(responseParams.getAccess_token())){
            //设置redis
            setToken2Redis(responseParams);
        }
        //邮件发送
        String title="刷新京东token返回结果";
        String content=json;
        try {
            mailService.send(title, content);
        }catch (Exception e){
            log.error("刷新京东token，邮件发送异常："+e.getMessage());
        }
    }
}
