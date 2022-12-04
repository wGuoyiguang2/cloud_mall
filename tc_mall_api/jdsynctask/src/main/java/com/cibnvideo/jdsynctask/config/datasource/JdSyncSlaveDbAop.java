package com.cibnvideo.jdsynctask.config.datasource;

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
    private Logger logger = LoggerFactory.getLogger("JdSyncTaskSlaveDbAop");

    @Pointcut("execution(public * com.cibnvideo.jdsynctask.dao.*.get*(..)) || " +
            "execution(public * com.cibnvideo.jdsynctask.dao.*.list*(..)) || " +
            "execution(public * com.cibnvideo.jdsynctask.dao.*.count*(..)) || " +
            "execution(public * com.cibnvideo.jdsynctask.dao.*.search*(..))")
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
