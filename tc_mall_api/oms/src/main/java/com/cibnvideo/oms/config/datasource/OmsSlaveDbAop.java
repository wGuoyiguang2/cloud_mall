package com.cibnvideo.oms.config.datasource;

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
public class OmsSlaveDbAop {
    private Logger logger = LoggerFactory.getLogger("OmsSlaveDbAop");

    @Pointcut("execution(public * com.cibnvideo.oms.*.dao.*.list*(..)) || " +
            "execution(public * com.cibnvideo.oms.*.dao.*.get*(..)) || " +
            "execution(public * com.cibnvideo.oms.*.dao.*.count*(..)) || " +
            "execution(public * com.cibnvideo.oms.*.dao.*.skusByVenderId*(..)) || " +
            "execution(public * com.cibnvideo.oms.*.dao.*.accountManagerList*(..)) || " +
            "execution(public * com.cibnvideo.oms.*.dao.*.balanceGet*(..))")
    private void masterCut(){}

    @Before("masterCut()")
    public void beforeMasterCall(){
        logger.info("====切换到从库=====");
        DatabaseContextHolder.setDatabaseType(DatabaseType.slave00);
    }

    @After("masterCut()")
    public void afterMasterCall(){
        logger.info("local-thread-clear-DB");
        DatabaseContextHolder.clearDataSource();
    }
}
