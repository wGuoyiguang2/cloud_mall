package com.cibnvideo.oms.config.datasource;

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
public class OmsMasterDbAop {

    private Logger logger = LoggerFactory.getLogger("OmsMasterDbAop");

    @Pointcut("execution(public * com.cibnvideo.oms.*.dao.*.save*(..)) || " +
            "execution(public * com.cibnvideo.oms.*.dao.*.update*(..)) || " +
            "execution(public * com.cibnvideo.oms.*.dao.*.remove*(..)) || " +
            "execution(public * com.cibnvideo.oms.*.dao.*.batchRemove*(..)) || " +
            "execution(public * com.cibnvideo.oms.*.dao.*.add*(..)) || " +
            "execution(public * com.cibnvideo.oms.*.dao.*.delete*(..)) || " +
            "execution(public * com.cibnvideo.oms.*.dao.*.settleAccount*(..)) || " +
            "execution(public * com.cibnvideo.oms.*.dao.*.batchSave*(..)) || " +
            "execution(public * com.cibnvideo.oms.*.dao.*.balanceAdd*(..)) || " +
            "execution(public * com.cibnvideo.oms.*.dao.*.balanceReduce*(..))")
    private void masterCut(){}

    @Before("masterCut()")
    public void beforeMasterCall(){
        logger.info("====切换到主库=====");
        DatabaseContextHolder.setDatabaseType(DatabaseType.master);
    }

    @After("masterCut()")
    public void afterMasterCall(){
        logger.info("local-thread-clear-DB");
        DatabaseContextHolder.clearDataSource();
    }
}
