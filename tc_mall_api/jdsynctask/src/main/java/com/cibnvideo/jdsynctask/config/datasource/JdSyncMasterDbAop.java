package com.cibnvideo.jdsynctask.config.datasource;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by wanghaoyang on 18-8-10.
 * 在此类中添加Mapper文件中关于数据库增,删,改的操作
 */
@Aspect
@Component
public class JdSyncMasterDbAop {

    private Logger logger = LoggerFactory.getLogger("JdSyncTaskMasterDbAop");

    @Pointcut("execution(public * com.cibnvideo.jdsynctask.dao.*.save*(..)) || " +
            "execution(public * com.cibnvideo.jdsynctask.dao.*.update*(..)) || " +
            "execution(public * com.cibnvideo.jdsynctask.dao.*.remove*(..)) || " +
            "execution(public * com.cibnvideo.jdsynctask.dao.*.batchRemove*(..))")
    private void masterCut(){}

    @Before("masterCut()")
    public void beforeMasterCall(){
//        logger.info("====切换到主库=====");
        DatabaseContextHolder.setDatabaseType(DatabaseType.master);
    }

    @After("masterCut()")
    public void afterMasterCall(){
//        logger.info("local-thread-clear-DB");
        DatabaseContextHolder.clearDataSource();
    }
}
