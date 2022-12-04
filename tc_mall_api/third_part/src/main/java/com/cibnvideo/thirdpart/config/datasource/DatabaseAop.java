package com.cibnvideo.thirdpart.config.datasource;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


@Aspect
@Component
public class DatabaseAop {

    private Logger logger = LoggerFactory.getLogger("DatabaseAop");

    @Pointcut("execution(public * com.hqtc.thirdpart.model.mapper.*.*(..))")
    private void masterCut(){}

    @Before("masterCut()")
    public void beforeMasterCall(JoinPoint pjp){
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        Annotation[] annotations = targetMethod.getDeclaredAnnotations();
        if(null == annotations || 0 == annotations.length){
            DatabaseContextHolder.setDatabaseType(DatabaseType.master);
        }else {
            for(Annotation annotation:annotations){
                if (Insert.class.equals(annotation.annotationType()) || Delete.class.equals(annotation.annotationType())
                        || Update.class.equals(annotation.annotationType())){
                    DatabaseContextHolder.setDatabaseType(DatabaseType.master);
                    logger.info("local-thread-change-to-database-master");
                    break;
                }else if (Select.class.equals(annotation.annotationType())){
                    DatabaseContextHolder.setDatabaseType(DatabaseType.slave00);
                    logger.info("local-thread-change-to-database-slave");
                    break;
                }
            }
        }
    }

    @After("masterCut()")
    public void afterMasterCall(){
        logger.info("local-thread-clear-database");
        DatabaseContextHolder.clearDataSource();
    }
}
