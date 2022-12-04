package com.hqtc.bms.task;

import com.hqtc.bms.model.rpc.MessageRepVo;
import com.hqtc.bms.service.MessageService;
import com.hqtc.common.response.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-9-13.
 * 处理京东消息的定时任务
 */
@Component
public class JdMessageTask {

    private Logger logger = LoggerFactory.getLogger("JdMessageTask");

    @Autowired
    @Resource(name = "MessageServiceImpl")
    private MessageService messageService;

    /**
     * 此定时任务用来处理jd消息中的拒签的消息(处理退款事宜)
     * add by wanghaoyang at 20180814
     * */
    @Scheduled(fixedRate = 1000 * 60 * 60 * 2)
    public void handleDeliveryMessage() {
        ResultData<List<MessageRepVo>> resultData = messageService.getMessage("5");
        List<MessageRepVo> messageRepVos = resultData.getData();
        ResultData resultData1 = messageService.handleDeliveryMessage(messageRepVos);
        logger.info("开始处理拒签的退款事宜:{}", String.valueOf(resultData1.getMsg()));
    }

    /**
     * 次定时任务用来处理jd消息中的拆单的消息
     * add by wanghaoyang at 20180815
     * */
    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void handlerSplitOrderMessage(){
        ResultData<List<MessageRepVo>> resultData = messageService.getMessage("1");
        List<MessageRepVo> messageRepVos = resultData.getData();
        ResultData resultData1 = messageService.handleSplitOrderMessage(messageRepVos);
        logger.info("开始处理拆单事宜:{}", String.valueOf(resultData1.getMsg()));
    }
}
