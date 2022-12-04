package com.cibnvideo.jdsynctask.service.impl;

import com.cibnvideo.jdsynctask.dao.JdProductDetailDao;
import com.cibnvideo.jdsynctask.dao.JdProductPriceDao;
import com.cibnvideo.jdsynctask.model.SellPriceResult;
import com.cibnvideo.jdsynctask.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class JdPriceSyncServiceImpl implements JdPriceSyncService {
    //操作成功
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdProductDetailDao jdProductDetailDao;

    @Autowired
    private JdProductPriceDao jdProductPriceDao;

    @Autowired
    private JdProductSyncService jdProductSync;

    @Autowired
    private JdGoodsService jdGoodsService;

    @Autowired
    private ThirdPartService thirdPartService;

    @Autowired
    SendMessageService sendMessageService;

    private ReentrantLock lock = new ReentrantLock();

    private int limit = 50;

    @Override
    public void syncProductPrice() throws Exception{
        if(lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                Map<String, Object> map = new HashMap<String, Object>();
                int totalRows = jdProductDetailDao.count(map);
                for (int i = 0; i < totalRows; ) {
                    List<Long> skuList = jdProductDetailDao.listSkus(i, limit);
                    updatePrice(skuList);
                    i = i + limit;
                }
            } catch (Exception e) {
                logger.error("syncProductPrice Exception", e);
            } finally {
                lock.unlock();
            }
        } else {
            logger.error("syncProductPrice tryLock failed");
        }


    }

    @Override
    public boolean updatePrice(List<Long> skuList) {
        Map<Long, SellPriceResult> sellPriceResultMap = jdGoodsService.getSellPrice(skuList);
        Map<Long , BigDecimal> jdSitePriceMap = thirdPartService.listJDSitePrice(skuList);
        if (sellPriceResultMap != null) {
            skuList.forEach(sku -> {
                int result = 0;
                SellPriceResult sellPriceResult = sellPriceResultMap.get(sku);
                if(jdSitePriceMap != null) {
                    BigDecimal jdSitePrice = jdSitePriceMap.get(sku);
                    if(jdSitePrice != null && sellPriceResult != null && jdSitePrice.compareTo(BigDecimal.ZERO) == 1) {
                        sellPriceResult.setJdPrice(jdSitePrice);
                    }
                }
                if (sellPriceResult != null) {
                    SellPriceResult sellPriceSave = jdProductPriceDao.get(sellPriceResult.getSkuId());
                    if (sellPriceSave != null) {
                        if (!sellPriceSave.equals(sellPriceResult)) {
                            result = jdProductPriceDao.update(sellPriceResult);
                        }
                    } else {
                        result = jdProductPriceDao.save(sellPriceResult);
                    }
                } else {
                    logger.error("sellPriceResult is null , sku = " + sku);
                    result = jdProductSync.productRemove(sku);
                    if (result == 0) {
                        logger.error("sellPrice sync, remove sku failed");
                    }
                }
                if (result > 0) {
                    if (sendMessageService.productDetailChange(sku)) {
                        logger.info("syncProductPrice, sendMessage success,sku = " + sku);
                    } else {
                        logger.warn("syncProductPrice, sendMessage failed, sku = " + sku);
                    }
                }
            });
        } else {
            logger.error("update price failed, sellPriceResultMap is null");
            return false;
        }
        return true;

    }
}
