package com.cibnvideo.jdsynctask.service.impl;

import com.cibnvideo.jdsynctask.model.GetMessageResponseResult;
import com.cibnvideo.jdsynctask.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class JdGetMessageServiceImpl implements JdGetMessageService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JdProductSyncService jdProductSync;

    @Autowired
    JdPriceSyncService jdPriceSync;

    @Autowired
    JdAddressSyncService jdAddressSync;

    @Autowired
    JdGoodsService jdGoodsService;

    @Value("${spring.profiles.active}")
    private String profiles;

    @Autowired
    SendMessageService sendMessageService;

    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void getMessage() throws Exception{
        if(lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                logger.info("======getMessage=======");
                String types = "2,4,6,16,50";
                List<GetMessageResponseResult> getMessageResponseResultList = jdGoodsService.getMessage(types);
                if (getMessageResponseResultList == null) {
                    logger.warn("getMessageResponseResultList is null");
                    return;
                }
                for (GetMessageResponseResult r : getMessageResponseResultList) {
                    if (r.getType() == null) {
                        continue;
                    }
                    Map<String, Object> result = r.getResult();
                    switch (r.getType()) {
                        //价格变动
                        case 2:
                            if (updatePrice(result)) {
                                if (!deleteMessage(r.getId())) {
                                    logger.error("message delete failed, id = " + r.getId());
                                }
                            } else {
                                logger.error("state update failed");
                            }
                            break;
                        //上下架变动
                        case 4:
                            if (updateProductState(result)) {
                                logger.info("state update success");
                                if (!deleteMessage(r.getId())) {
                                    logger.error("message delete failed, id = " + r.getId());
                                }
                            } else {
                                logger.error("state update failed");
                            }
                            break;
                        //商品池内商品添加删除
                        case 6:
                            if (productAddOrRemove(result)) {
                                if (!deleteMessage(r.getId())) {
                                    logger.error("message delete failed, id = " + r.getId());
                                }
                            } else {
                                logger.error("state update failed");
                            }
                            break;
                        //商品详情变动
                        case 16:
                            if (updateProductDetail(result)) {
                                logger.info("product detail update success");
                                if (!deleteMessage(r.getId())) {
                                    logger.error("message delete failed, id = " + r.getId());
                                }
                            } else {
                                logger.error("state update failed");
                            }
                            break;
                        //京东地址变动
                        case 50:
                            if (updateJdAddress(result)) {
                                logger.info("jd address update success");
                                if (!deleteMessage(r.getId())) {
                                    logger.error("message delete failed, id = " + r.getId());
                                }
                            } else {
                                logger.error("state update failed");
                            }
                            break;
                        default:
                            logger.error("unknow message type");
                            break;
                    }
                }

            } catch (Exception e) {
                logger.error("getMessage Exception", e);
            } finally {
                lock.unlock();
            }
        } else {
            logger.error("getMessage tryLock failed");
        }
    }

    private boolean updateProductState(Map<String, Object> result) {
        if (result.containsKey("skuId") && result.containsKey("state")) {
            String skuId = (String) result.get("skuId");
            String state = (String) result.get("state");
            int r = jdProductSync.updateState(Long.valueOf(skuId), Integer.valueOf(state));
            if (r == 0) {
                logger.error("update product state failed");
                return false;
            } else {
                if (sendMessageService.productDetailChange(Long.valueOf(skuId))) {
                    logger.info("updateProductState, kafka send message success");
                } else {
                    logger.info("updateProductState, kafka send message failed");
                }
                return true;
            }
        } else {
            logger.error("result not contains skuid");
            return false;
        }
    }

    private boolean updatePrice(Map<String, Object> result) {
        if (result.containsKey("skuId")) {
            String skuId = (String) result.get("skuId");
            return jdPriceSync.updatePrice(Arrays.asList(Long.valueOf(skuId)));
        } else {
            logger.error("updatePrice result not contains skuid");
            return false;
        }
    }

    private boolean updateProductDetail(Map<String, Object> result) {
        if (result.containsKey("skuId")) {
            String skuId = (String) result.get("skuId");
            int r = jdProductSync.updateProductDetail(skuId);
            if (r == 0) {
                logger.error("update product detail failed");
                return false;
            } else {
                if (r == 1) {
                    if (sendMessageService.productDetailChange(Long.valueOf(skuId))) {
                        logger.info("updateProductDetail, kafka send message success");
                    } else {
                        logger.error("updateProductDetail, kafka send message failed");
                    }
                } else {
                    logger.info("updateProductDetail, product info is same, do not sendMessage to es");
                }
                return true;
            }
        } else {
            logger.error("updateProductDetail, result not contains skuid");
            return false;
        }
    }

    private boolean productAddOrRemove(Map<String, Object> result) {
        if (result.containsKey("skuId") && result.containsKey("state")) {
            String skuId = (String) result.get("skuId");
            String state = (String) result.get("state");
            if (!StringUtils.isEmpty(state)) {
                if ("1".equals(state)) {//添加商品
                    int r = jdProductSync.updateProductDetail(skuId);
                    if (r == 0) {
                        logger.error("update product detail failed");
                        return false;
                    } else {
                        return jdPriceSync.updatePrice(Arrays.asList(Long.valueOf(skuId)));
                    }
                } else if ("2".equals(state)) {
                    int r = jdProductSync.productRemove(Long.valueOf(skuId));
                    if (r == 0) {
                        logger.error("remove product detail failed");
                        return false;
                    } else {
                        if (sendMessageService.productDetailChange(Long.valueOf(skuId))) {
                            logger.info("productAddOrRemove,remove product, kafka send message success");
                        } else {
                            logger.error("productAddOrRemove,remove product, kafka send message failed");
                        }
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } else {
            logger.error("updateProductDetail, result not contains skuid");
            return false;
        }
    }

    private boolean updateJdAddress(Map<String, Object> result) {
        if (result.containsKey("areaId")
                && result.containsKey("areaName")
                && result.containsKey("parentId")
                && result.containsKey("areaLevel")
                && result.containsKey("operateType")) {
            int operateType = Integer.valueOf((String) result.get("operateType"));
            int areaId = Integer.valueOf((String) result.get("areaId"));
            int areaLevel = Integer.valueOf((String) result.get("areaLevel"));
            int resultCode = 0;
            if (operateType == 3) {//地址删除时做删除
                switch (areaLevel) {
                    case 5://镇
                        resultCode = jdAddressSync.removeByTownId(areaId);
                        break;
                    case 4://县
                        resultCode = jdAddressSync.removeByCountyId(areaId);
                        break;
                    case 3://市
                        resultCode = jdAddressSync.removeByCityId(areaId);
                        break;
                    case 2://省
                        resultCode = jdAddressSync.removeByProvinceId(areaId);
                        break;
                    default:
                        resultCode = 0;
                        break;
                }
            } else {//其他情况全量更新
                try {
                    jdAddressSync.syncAddressProvinces();
                    resultCode = 1;
                } catch (Exception e) {
                    logger.error("updatejdAddress failed " + e.toString());
                }

            }
            return resultCode == 1 ? true : false;
        } else {
            return false;
        }
    }

    private boolean deleteMessage(String id) {
        if (StringUtils.isEmpty(profiles) || !profiles.contains("prod")) {
            return true;
        }
        return jdGoodsService.deleteMessage(id);
    }
}
