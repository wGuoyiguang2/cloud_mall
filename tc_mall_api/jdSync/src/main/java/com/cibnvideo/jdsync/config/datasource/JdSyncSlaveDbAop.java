package com.cibnvideo.jdsync.config.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by wanghaoyang on 18-8-16.
 */
@Aspect
@Component
public class JdSyncSlaveDbAop {
    private Logger logger = LoggerFactory.getLogger("JdSyncSlaveDbAop");

    @Pointcut("execution(public * com.cibnvideo.jdsync.dao.*.get*(..)) || " +
            "execution(public * com.cibnvideo.jdsync.dao.*.list*(..)) || " +
            "execution(public * com.cibnvideo.jdsync.dao.*.count*(..)) || " +
            "execution(public * com.cibnvideo.jdsync.dao.*.search*(..))")
    private void masterCut(){}

    @Before("masterCut()")
    public void beforeMasterCall(){
//        logger.info("====切换到从库=====");
        DatabaseContextHolder.setDatabaseType(DatabaseType.slave00);
    }

    @After("masterCut()")
    public void afterMasterCall(){
//        logger.info("local-thread-clear-DB");
        DatabaseContextHolder.clearDataSource();
    }
}
