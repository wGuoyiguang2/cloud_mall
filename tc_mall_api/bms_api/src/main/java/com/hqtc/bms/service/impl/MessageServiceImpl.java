package com.hqtc.bms.service.impl;

import com.hqtc.bms.model.params.JdDeliveryMessageBean;
import com.hqtc.bms.model.response.Result;
import com.hqtc.bms.model.rpc.*;
import com.hqtc.bms.service.MessageService;
import com.hqtc.bms.service.OrderService;
import com.hqtc.bms.service.RefundService;
import com.hqtc.bms.service.rpc.RPCJDService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanghaoyang on 18-8-10.
 */
@Service("MessageServiceImpl")
public class MessageServiceImpl implements MessageService {

    private Logger logger = LoggerFactory.getLogger("MessageServiceImpl");

    //redis中投递信息集合的名字前缀+京东订单id
    private static String REDIS_DELIVERY_MSG_KEY_PREFIX = "tcmall:bms:jdmessage:delivery:";
    //redis中拆单信息
    private static String REDIS_SPLIT_ORDER_MSG_KEY_PREFIX = "tcmall:bms:jdmessage:splitOrder:";
    //key保存的时长(24小时)
    private static int REDIS_DELIVERY_MSG_KEY_EXPIRE = 86400;

    @Autowired
    private RPCJDService rpcjdService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    @Resource(name = "RefundServiceImpl")
    private RefundService refundService;

    @Autowired
    @Resource(name = "OrderServiceImpl")
    private OrderService orderService;

    @Override
    public ResultData getMessage(String type){
        ResultData resultData = new ResultData();
        GetMessageRequestParams messageRequestParams = new GetMessageRequestParams();
        messageRequestParams.setType(type);
        GetMessageResponseParams res = rpcjdService.getMessage(messageRequestParams);
        if(null == res || null == res.getBiz_message_get_response()){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("Jdservice 服务异常");
            return resultData;
        }
        MessageVo messageVo = res.getBiz_message_get_response();
        if(!"0000".equals(messageVo.getResultCode()) || "false".equals(messageVo.getResultCode())){
            resultData.setError(ErrorCode.FALI);
            resultData.setMsg(messageVo.getResultMessage());
            return resultData;
        }
        List<MessageRepVo> messageRepVos = messageVo.getResult();
        resultData.setData(messageRepVos);
        return resultData;
    }

    @Override
    public ResultData delMessage(String messageId){
        ResultData resultData = new ResultData();
        DeleteMessageRequestParams deleteMessageRequestParams = new DeleteMessageRequestParams();
        deleteMessageRequestParams.setId(messageId);
        DeleteMessageResponseParams res = rpcjdService.deleteMessage(deleteMessageRequestParams);
        if(null == res){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("Jdservice 服务异常");
            return resultData;
        }
        DeleteMessageVo messageVo = res.getBiz_message_del_response();
        if(null == messageVo || !"0000".equals(messageVo.getResultCode()) || "false".equals(messageVo.getResultCode())){
            resultData.setError(ErrorCode.FALI);
            resultData.setMsg("Jdservice 服务异常");
            return resultData;
        }
        boolean result = messageVo.getResult();
        resultData.setData(result);
        return resultData;
    }

    /**
     * 处理拒收的消息
     * 1:循环将拒收消息写入redis并调用退款流程
     * 2:如果退款成功,删除redis,并删除京东推送的词条消息
     * */
    @Override
    public ResultData handleDeliveryMessage(List<MessageRepVo> jdMessages){
        ResultData resultData = new ResultData();
        if(null == jdMessages || jdMessages.isEmpty()){
            return resultData;
        }
        for(MessageRepVo deliveryMessageBean: jdMessages){
            if("5".equals(deliveryMessageBean.getType()) && "2".equals(deliveryMessageBean.getResult().get("state"))){
                this.writeMessageToRedis(deliveryMessageBean);
                this.refund(deliveryMessageBean);
            }else if("5".equals(deliveryMessageBean.getType()) && "1".equals(deliveryMessageBean.getResult().get("state"))){
                logger.info("订单:"+deliveryMessageBean.getResult().getOrDefault("orderId", "").toLowerCase()+"签收成功,删除消息");
                //判断当前环境是否有此订单
                //是否需要更新订单状态
                this.delMessage(deliveryMessageBean.getId());
            }
        }
        return resultData;
    }

    @Override
    public ResultData successRefundFinally(String jdOrderId){
        String key = REDIS_DELIVERY_MSG_KEY_PREFIX+jdOrderId;
        String messageId = redisTemplate.opsForValue().get(key);
        this.deleteMessageFromRedis(jdOrderId);
        return this.delMessage(messageId);
    }

    @Override
    public ResultData handleSplitOrderMessage(List<MessageRepVo> jdMessages){
        ResultData resultData = new ResultData();
        if(null == jdMessages || jdMessages.isEmpty()){
            return resultData;
        }
        for(MessageRepVo deliveryMessageBean: jdMessages){
            if("1".equals(deliveryMessageBean.getType())){
                this.splitOrder(deliveryMessageBean);
            }
        }
        return resultData;
    }

    /**
     * 退款,先退大客户,成功后退款给用户
     * add by wanghaoyang at 20180813
     * */
    private boolean refund(MessageRepVo deliveryMessageBean){
        String jdOrderId = deliveryMessageBean.getResult().get("orderId");
        String jdMessageId = deliveryMessageBean.getId();
//        ResultData resultData = refundService.refund(jdOrderId);
        ResultData resultData = refundService.denyReceiveRefund(jdOrderId, jdMessageId);
        logger.info("自动退款 京东订单ID: "+jdOrderId);
        if(ErrorCode.SUCCESS != resultData.getError()){
            logger.error("自动退款失败, 京东订单ID: "+jdOrderId+" ,失败原因: "+resultData.getMsg());
            return false;
        }
        return true;
    }

    /**
     * 拆单
     * add by wanghaoyang at 20180815
     * */
    private void splitOrder(MessageRepVo splitOrderMessageBean){
        String jdOrderId = splitOrderMessageBean.getResult().get("pOrder");
        ResultData resultData = orderService.splitOrder(jdOrderId);
        if(null != resultData && ErrorCode.SUCCESS == resultData.getError()){
            String messageId = splitOrderMessageBean.getId();
            this.delMessage(messageId);
        }
    }

    /**
     * 将投递消息写入redis
     * add by wanghaoyang at 20180813
     * */
    private boolean writeMessageToRedis(MessageRepVo deliveryMessageBean){
        String msgId = deliveryMessageBean.getId();
        String jdOrderId = deliveryMessageBean.getResult().get("orderId");
        String key = REDIS_DELIVERY_MSG_KEY_PREFIX+jdOrderId;
        redisTemplate.opsForValue().set(key, msgId, REDIS_DELIVERY_MSG_KEY_EXPIRE, TimeUnit.SECONDS);
        return true;
    }

    private boolean writeMessageToRedis2(MessageRepVo deliveryMessageBean){
        String prefix = "";
        String jdOrderId = "";
        if("1".equals(deliveryMessageBean.getType())){//拆单
            prefix = REDIS_SPLIT_ORDER_MSG_KEY_PREFIX;
            jdOrderId = deliveryMessageBean.getResult().get("pOrder");
        }else if("5".equals(deliveryMessageBean.getType())){//拒收
            prefix = REDIS_DELIVERY_MSG_KEY_PREFIX;
            jdOrderId = deliveryMessageBean.getResult().get("orderId");
        }
        String msgId = deliveryMessageBean.getId();
        String key = prefix+jdOrderId;
        redisTemplate.opsForValue().set(key, msgId, REDIS_DELIVERY_MSG_KEY_EXPIRE, TimeUnit.SECONDS);
        return true;
    }

    /**
     * 将处理成功的消息从redis中删除
     * add by wanghaoyang at 20180813
     * */
    private boolean deleteMessageFromRedis(MessageRepVo deliveryMessageBean){
        String jdOrderId = deliveryMessageBean.getResult().get("orderId");
        String key = REDIS_DELIVERY_MSG_KEY_PREFIX+jdOrderId;
        redisTemplate.delete(key);
        return true;
    }

    private boolean deleteMessageFromRedis(String jdOrderId){
        String key = REDIS_DELIVERY_MSG_KEY_PREFIX+jdOrderId;
        redisTemplate.delete(key);
        return true;
    }

    /**
     * 删除redis中的消息
     * add by wanghaoyang at 20180815
     * @param msgType 消息(1:拆单|5:拒收)
     * */
    private boolean deleteMessageFromRedis2(int msgType, String jdOrderId){
        String prefix = "";
        if(1 == msgType){
            prefix = REDIS_SPLIT_ORDER_MSG_KEY_PREFIX;
        }else if(5 == msgType){
            prefix = REDIS_DELIVERY_MSG_KEY_PREFIX;
        }
        String key = prefix+jdOrderId;
        redisTemplate.delete(key);
        return true;
    }
}
