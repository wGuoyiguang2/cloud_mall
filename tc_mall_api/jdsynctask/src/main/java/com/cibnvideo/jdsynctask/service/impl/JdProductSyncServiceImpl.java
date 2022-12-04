package com.cibnvideo.jdsynctask.service.impl;

import com.cibnvideo.jdsynctask.dao.*;
import com.cibnvideo.jdsynctask.model.*;
import com.cibnvideo.jdsynctask.service.JdGoodsService;
import com.cibnvideo.jdsynctask.service.JdProductSyncService;
import com.cibnvideo.jdsynctask.service.SendMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class JdProductSyncServiceImpl implements JdProductSyncService {

    //操作成功
    private final String JD_ERRORCODE_OK = "0000";
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String imagePathPre = "http://img13.360buyimg.com/n0/";
    @Autowired
    private JdProductDetailDao jdProductDetailDao;

    @Autowired
    private JdStyleOfPcDao jdStyleOfPcDao;

    @Autowired
    private JdStyleOfMobileDao jdStyleOfMobileDao;

    @Autowired
    private JdPictureOfPcDao jdPictureOfPcDao;

    @Autowired
    private JdPictureOfMobileDao jdPictureOfMobileDao;

    @Autowired
    private JdGoodsService goodsService;

    @Autowired
    private SendMessageService sendMessageService;

    private ReentrantLock lock = new ReentrantLock();


    @Override
    public void syncProductDetailStart() throws Exception{
        if(lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                List<PageNumInfo> pageNumInfo = goodsService.getPageNum();
                if (pageNumInfo == null) {
                    return;
                }
                for (PageNumInfo info : pageNumInfo) {
                    String num = info.getPage_num();
                    List<String> skuIds = goodsService.getSkus(num);
                    if (skuIds != null) {
                        skuIds.forEach(sku -> {
                            int re = updateProductDetail(sku);
                            if (re == 0) {
                                logger.error("商品详情更新失败, sku = " + sku);
                            } else if (re == 1) {
                                if (sendMessageService.productDetailChange(Long.valueOf(sku))) {
                                    logger.info("syncProductDetail, send Message success");
                                } else {
                                    logger.error("syncProductDetail, send Message failed");
                                }
                            }
                        });
                    }
                }
            } catch (Exception e) {
                logger.error("syncProductDetailStart Exception", e);
            } finally {
                lock.unlock();
            }
        } else {
            logger.error("syncProductDetail tryLock failed");
        }
    }

    @Override
    public int productRemove(Long sku) {
//        int result = jdProductDetailDao.remove(sku);
//        if (result == 1) {
//            jdStyleOfPcDao.remove(sku);
//            jdStyleOfMobileDao.remove(sku);
//        }
//        return result;
        return updateState(sku, 0);
    }

    @Override
    public int updateState(Long sku, Integer state) {
        ProductResult productResult = jdProductDetailDao.get(sku);
        if (productResult != null) {
            return jdProductDetailDao.updateState(sku, state);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateProductDetail(String sku) {
        ProductResult productResult = goodsService.getProductDetail(Long.valueOf(sku));
        if (productResult != null) {
            productResult.setImagePath(imagePathPre + productResult.getImagePath());

            String[] cats = getCategorys(productResult.getCategory());
            if (cats != null) {
                productResult.setCat0(Integer.valueOf(cats[0]));
                productResult.setCat1(Integer.valueOf(cats[1]));
                productResult.setCat2(Integer.valueOf(cats[2]));
            }
            if (hasPcStyle(productResult.getIntroduction())) {
                int r = updateStyleOfPc(Long.valueOf(sku));
                if (r == 0) {
                    logger.error("style of pc update failed");
                    return 0;
                }
            }
            if (hasMobileStyle(productResult.getAppintroduce())) {
                int r = updateStyleOfMobile(Long.valueOf(sku));
                if (r == 0) {
                    logger.error("style of mobile update failed");
                    return 0;
                }
            }
            if (updatePictureOfPc(Long.valueOf(sku)) == 0) {
                logger.error("picture update failed");
                return 0;
            }
            if (updatePictureOfMobile(Long.valueOf(sku)) == 0) {
                logger.error("picture of mobile update failed");
                return 0;
            }
        } else {
            return 0;
        }
        ProductResult productSaved = jdProductDetailDao.get(productResult.getSku());
        if (productSaved != null) {//商品存在数据库中
            if (!productSaved.equals(productResult)) {
                productResult.setUtime(new Date());
                return jdProductDetailDao.update(productResult);//更新商品详情
            } else {
                return 2;//商品详情无变化，不用更新
            }
        } else {//商品不存在数据库中
            productResult.setCtime(new Date());
            productResult.setUtime(new Date());
            return jdProductDetailDao.save(productResult);
        }
    }

    private boolean hasPcStyle(String introduction) {
        if (StringUtils.isEmpty(introduction)) {
            return false;
        } else {
            if (introduction.contains("skudesign=\"100010\"")) {
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean hasMobileStyle(String introduction) {
        if (StringUtils.isEmpty(introduction)) {
            return false;
        } else {
            if (introduction.contains("skudesign=\"100011\"")) {
                return true;
            } else {
                return false;
            }
        }
    }

    private String[] getCategorys(String categorys) {
        if (StringUtils.isEmpty(categorys)) {
            return null;
        } else {
            String[] cts = categorys.split(";");
            if (cts.length != 3) {
                return null;
            } else {
                return cts;
            }
        }
    }

    //更新pc端样式
    private int updateStyleOfPc(Long sku) {
        StyleOfPcResult styleOfPcResult = goodsService.getStyleOfPc(sku);
        if (styleOfPcResult != null) {
            StyleOfPcResult styleOfPcSave = jdStyleOfPcDao.get(styleOfPcResult.getSku());
            if (styleOfPcSave != null) {
                if (!styleOfPcSave.equals(styleOfPcResult)) {
                    return jdStyleOfPcDao.update(styleOfPcResult);
                } else {
                    return 1;
                }
            } else {
                return jdStyleOfPcDao.save(styleOfPcResult);
            }
        } else {
            logger.error("styleofpc is null , sku = " + sku);
            return 0;
        }
    }

    //更新移动端样式
    private int updateStyleOfMobile(Long sku) {
        StyleOfMobileResult styleOfMobileResult = goodsService.getStyleOfMobile(sku);
        if (styleOfMobileResult != null) {
            StyleOfMobileResult styleOfMobileSave = jdStyleOfMobileDao.get(styleOfMobileResult.getSku());
            if (styleOfMobileSave != null) {
                if (!styleOfMobileSave.equals(styleOfMobileResult)) {
                    return jdStyleOfMobileDao.update(styleOfMobileResult);
                } else {
                    return 1;
                }
            } else {
                return jdStyleOfMobileDao.save(styleOfMobileResult);
            }
        } else {
            logger.error("styleofmobile is null , sku = " + sku);
            return 0;
        }
    }


    @Override
    public int updatePictureOfPc(Long sku) {
        PictureOfPcResponse pictureOfPcResponse = goodsService.getPictureOfPc(sku);
        if (pictureOfPcResponse != null) {
            pictureOfPcResponse.setSku(sku);
            PictureOfPcResponse pictureOfPcSaved = jdPictureOfPcDao.get(sku);
            if (pictureOfPcSaved != null) {
                if (!pictureOfPcSaved.equals(pictureOfPcResponse)) {
                    return jdPictureOfPcDao.update(pictureOfPcResponse);
                } else {
                    return 1;
                }
            } else {
                return jdPictureOfPcDao.save(pictureOfPcResponse);
            }
        } else {
            logger.error("pictureofpc is null , sku = " + sku);
            return 0;
        }
    }

    @Override
    public int updatePictureOfMobile(Long sku) {
        PictureOfMobileResponse pictureOfMobileResponse = goodsService.getPictureOfMobile(sku);
        if (pictureOfMobileResponse != null) {
            pictureOfMobileResponse.setSku(sku);
            PictureOfMobileResponse pictureOfPcSave = jdPictureOfMobileDao.get(pictureOfMobileResponse.getSku());
            if (pictureOfPcSave != null) {
                if (!pictureOfPcSave.equals(pictureOfMobileResponse)) {
                    return jdPictureOfMobileDao.update(pictureOfMobileResponse);
                } else {
                    return 1;
                }
            } else {
                return jdPictureOfMobileDao.save(pictureOfMobileResponse);
            }
        } else {
            logger.error("pictureofpc is null , sku = " + sku);
            return 0;
        }
    }
}
