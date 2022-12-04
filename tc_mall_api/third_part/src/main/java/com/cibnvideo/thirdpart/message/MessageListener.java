package com.cibnvideo.thirdpart.message;

import com.cibnvideo.thirdpart.model.bean.JDSitePriceBean;
import com.cibnvideo.thirdpart.service.JDSitePriceService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/24 13:48
 */
@EnableBinding({MessageSink.class})
public class MessageListener {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Gson gson=new Gson();
    @Autowired
    private JDSitePriceService jdSitePriceService;
    @StreamListener(MessageSink.JD_SITE_PRICE_SINK)
    public void syncProductStatus(String msg) {
        logger.info("同步京东网站价格消息处理内容："+msg);
        List<JDSitePriceBean> list=gson.fromJson(msg,new TypeToken<List<JDSitePriceBean>>(){}.getType());
        jdSitePriceService.syncProductStatus(list);
    }
}
