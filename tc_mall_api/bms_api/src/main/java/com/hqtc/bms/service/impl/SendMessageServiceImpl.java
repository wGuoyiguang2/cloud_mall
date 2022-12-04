package com.hqtc.bms.service.impl;

import com.hqtc.bms.model.base.SendMessageDao;
import com.hqtc.bms.model.mapper.AfterSaleTaskBatchMapper;
import com.hqtc.bms.model.params.MessageInfo;
import com.hqtc.bms.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description:发送消息实现类
 * Created by laiqingchuang on 18-9-5 .
 */
@Service
public class SendMessageServiceImpl implements SendMessageService {

    private Logger logger  = LoggerFactory.getLogger(getClass());

    @Autowired
    SendMessageDao sendMessageDao;
    @Autowired
    AfterSaleTaskBatchMapper asBatchMapper;
    @Value("${service.config.timeout}")
    private int timeout;

    @Override
    public boolean sendAftersaleMessage(MessageInfo messageInfo) {
        if((int)((new Date().getTime() - messageInfo.getStartDate().getTime()) / 1000) >= timeout){
            int num=asBatchMapper.updateAftersaleFailinfo(messageInfo);
            logger.info("售后申请失败:时间超过"+timeout+"秒不再发送消息,更新数据数量"+num);
            return true;
        }
        return sendMessageDao.sendAftersaleMessage(messageInfo);
    }
}
