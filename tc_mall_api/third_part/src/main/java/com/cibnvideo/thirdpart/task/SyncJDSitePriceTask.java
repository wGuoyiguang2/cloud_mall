package com.cibnvideo.thirdpart.task;

import com.cibnvideo.thirdpart.config.BaseConfig;
import com.cibnvideo.thirdpart.feign.JdSyncFeign;
import com.cibnvideo.thirdpart.feign.ThirdPartFeign;
import com.cibnvideo.thirdpart.message.MessageSource;
import com.cibnvideo.thirdpart.model.bean.JDSitePriceBean;
import com.cibnvideo.thirdpart.model.bean.SyncJDSitePriceResponse;
import com.cibnvideo.thirdpart.service.JDSitePriceService;
import com.google.gson.Gson;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 同步京东网站商品价格
 * @Author: WangBin
 * @Date: 2019/1/21 17:23
 */
@EnableBinding(MessageSource.class)
@Component
public class SyncJDSitePriceTask {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Gson gson=new Gson();
    @Autowired
    private ThirdPartFeign thirdPartFeign;
    @Autowired
    private JdSyncFeign jdSyncFeign;
    @Autowired
    private JDSitePriceService jdSitePriceService;
    @Autowired
    private BaseConfig baseConfig;
    @Autowired
    private MessageSource messageSource;
    /**
     * 同步京东网站价格，对指定大客户的商品进行上下架操作
     */
    @Scheduled(cron ="${service.config.syncJDSitePriceCron}")
    public void syncJDSitePrice() {
        //从jdSync获取sku总数
        int total = this.getTotal();
        logger.warn("从jdsync获取sku总数："+total);
        int pageSize = baseConfig.getPerCount(); //每次执行条数
        int sumPage = (total - 1) / pageSize + 1;
        for (int i = 0; i < sumPage; i++) {
            try {
                String skus = this.getSkus(i*pageSize);
                //从第三方服务获取sku对应的价格
                String param = "J_" + skus;
                List<SyncJDSitePriceResponse> jdSitePriceList = thirdPartFeign.getJDSitePrice(param);
                if (jdSitePriceList == null || jdSitePriceList.isEmpty()) {
                    throw new Exception("获取京东网站商品价格失败！");
                }
                if(jdSitePriceList.size()<baseConfig.getPerCount()) {
                    logger.warn("从第三方爬取商品价格数量小于"+baseConfig.getPerCount()+",从jdsync查询的skus:"+skus+",从第三方通过skus查询的结果:"+gson.toJson(jdSitePriceList));
                }
                //保存到数据库(京东商品价格)
                List<JDSitePriceBean> jdSitePriceBeanList = jdSitePriceService.saveJDSitePrice(jdSitePriceList);
                //向kafka发送消息
                String json=gson.toJson(jdSitePriceBeanList);
                Message<String> msg = MessageBuilder.withPayload(json).build();
                boolean flag = messageSource.outputJDSitePrice().send(msg);
                if(!flag){
                    logger.error("同步京东网站价格后发送消息失败！");
                }
            } catch (Exception e) {
                logger.error("同步京东网站商品价格异常：" + e.getMessage());
            }
        }
    }


    /**
     * 获取京东商品状态为上架的sku总数
     *
     * @return
     * @throws Exception
     */
    private int getTotal(){
        Map<String, Object> map = new HashMap<>();
        map.put("state", 1);
        ResultData<Integer> totalResultData = jdSyncFeign.getCount(map);
        if (totalResultData.getError() != ErrorCode.SUCCESS || totalResultData.getData() == 0) {
            logger.error("从jdsync获取sku总数失败！");
        }
        return totalResultData.getData();
    }

    /**
     * 从jdsync分页获取skus
     * @param offset
     * @return
     * @throws Exception
     */
    private String getSkus(Integer offset) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("state", 1);
        map.put("offset", offset);
        map.put("limit", baseConfig.getPerCount());
        ResultData<String> resultData = jdSyncFeign.getSkus(map);
        if (resultData.getError() != ErrorCode.SUCCESS || StringUtils.isEmpty(resultData.getData())) {
            throw new Exception("从jdsync获取sku总数失败！");
        }
        return resultData.getData();
    }
}
