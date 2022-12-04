package com.hqtc.searchtask.listener;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.searchtask.config.MessageSink;
import com.hqtc.searchtask.jdsyncapi.ProductApi;
import com.hqtc.searchtask.model.bean.JdSyncMessageInfo;
import com.hqtc.searchtask.model.bean.ProductDo;
import com.hqtc.searchtask.omsapi.VenderSettlementApi;
import com.hqtc.searchtask.service.EsProductSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.List;

@EnableBinding({MessageSink.class})
public class JdSyncMessage {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    EsProductSyncService esProductSync;

    @Autowired
    ProductApi productApi;

    @Autowired
    VenderSettlementApi venderSettlementApi;

    @StreamListener(MessageSink.INPUT_JDSYNC)
    public synchronized void listenJdsync(JdSyncMessageInfo messageInfo) {
        logger.info("JdSyncMessageInfo : " + messageInfo);
        List<Integer> venderIds = null;
        ResultData<List<Integer>> venderIdResultData = venderSettlementApi.listVenderId();
        if(venderIdResultData.getError() == ErrorCode.SUCCESS){
            if(venderIdResultData.getData() != null){
                venderIds = venderIdResultData.getData();
            }
        }
        if(venderIds == null || venderIds.size() == 0){
            logger.warn("venderid list is null");
            return;
        }
        int type = messageInfo.getType();
        long sku = messageInfo.getSku();
        if(type == 0 && sku != 0){
            ResultData<ProductDo> resultData = productApi.getProductForEs(sku);
            if(resultData.getError() == ErrorCode.SUCCESS){
                ProductDo productDo = resultData.getData();
                if(productDo != null){
                    for(Integer venderId:venderIds){
                        esProductSync.productSync(venderId, productDo);
                    }
                }
            }
        }
    }
}
