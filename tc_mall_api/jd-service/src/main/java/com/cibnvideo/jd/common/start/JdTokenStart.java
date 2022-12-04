package com.cibnvideo.jd.common.start;

import com.cibnvideo.jd.common.config.BaseConfig;
import com.cibnvideo.jd.token.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/23 18:54
 */
@Component
public class JdTokenStart implements ApplicationRunner{
    Logger logger= LoggerFactory.getLogger(this.getClass());
    private static final long periodDay=20;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private BaseConfig baseConfig;
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        this.token();
    }
    private void token(){
        //如果当前环境为非线上，则不创建定时刷新token任务。
        if(!"prod".equals(baseConfig.getProfiles())){
            logger.error("检测到为非线上环境，不创建定时刷新token任务!");
            return;
        }
        //创建定时任务
        /*查询redis中的过期时间，先在过期时执行刷新token的操作，然后每隔20天刷新一次token
         */
        Long expiresIn=redisTemplate.getExpire(baseConfig.getJdTokenKey(),TimeUnit.SECONDS);
        Long period=periodDay*24*3600;
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable(){
            @Override
            public void run() {
                tokenService.refreshToken();
            }
        },expiresIn,period,TimeUnit.SECONDS);
    }
}
