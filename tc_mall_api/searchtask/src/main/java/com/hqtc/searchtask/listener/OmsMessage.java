package com.hqtc.searchtask.listener;

import com.hqtc.searchtask.config.MessageSink;
import com.hqtc.searchtask.model.bean.OmsMessageInfo;
import com.hqtc.searchtask.service.EsProductSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding({MessageSink.class})
public class OmsMessage {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    EsProductSyncService esProductSync;

    @StreamListener(MessageSink.INPUT_OMS)
    public synchronized void listenOms(OmsMessageInfo messageInfo) {
        logger.info("OmsMessageInfo : " + messageInfo);
        Integer type = messageInfo.getType();
        if (type != null) {
            switch (type) {
                case 0://vender create
                    esProductSync.productSyncByVenderId(messageInfo.getVenderId());
                    break;
                case 1://vender remove
                    esProductSync.venderProductRemove(messageInfo.getVenderId());
                    break;
                case 2://vender settlement change
                    esProductSync.productSyncByVenderId(messageInfo.getVenderId());
                    break;
                case 3://单品价格修改
                    esProductSync.productSyncBySku(messageInfo.getVenderId(), messageInfo.getSku());
                    break;
                case 4://商品集价格修改
                    esProductSync.productSyncByCollectionId(messageInfo.getVenderId(), messageInfo.getCollectionId());
                    break;
                case 5://商品分类价格修改
                    esProductSync.productSyncByCatId(messageInfo.getVenderId(), messageInfo.getCatId(), messageInfo.getCatType());
                    break;
                case 6://全站商品价格修改
                    esProductSync.productSyncByVenderId(messageInfo.getVenderId());
                    break;
                case 7://商品删除
                    esProductSync.productRemove(messageInfo.getVenderId(), messageInfo.getSku());
                    break;
                case 8://商品删除恢复
                    esProductSync.productSyncBySku(messageInfo.getVenderId(), messageInfo.getSku());
                    break;
                default:
                    break;

            }
        }
    }
}
