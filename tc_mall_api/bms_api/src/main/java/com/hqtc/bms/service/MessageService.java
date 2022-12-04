package com.hqtc.bms.service;

import com.hqtc.bms.model.params.JdDeliveryMessageBean;
import com.hqtc.bms.model.rpc.MessageRepVo;
import com.hqtc.common.response.ResultData;

import java.util.List;

/**
 * Created by wanghaoyang on 18-8-10.
 */
public interface MessageService {
    /**
     * 获取京东消息接口
     * add by wanghaoyang at 20180810
     * @param type 消息类型
     * @return 消息内容
     * */
    ResultData getMessage(String type);

    /**
     * 删除京东消息接口
     * add by wanghaoyang at 20180810
     * @param messageId 京东消息id
     * @return 成功失败
     * */
    ResultData delMessage(String messageId);

    /**
     * 处理京东(投递)消息(状态为-5)
     * add by wanghaoyang at 2018010
     * */
    ResultData handleDeliveryMessage(List<MessageRepVo> jdMessages);

    /**
     * 退款完全成功过后的工作
     * add by wanghaoyang at 20180813
     * @param jdOrderId 京东订单号
     * */
    ResultData successRefundFinally(String jdOrderId);

    /**
     * 处理拆单消息(状态为1)
     * add by wanghaoyang at 2018015
     * */
    ResultData handleSplitOrderMessage(List<MessageRepVo> jdMessages);
}
