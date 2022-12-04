package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.bean.ProductInfo;
import com.cibnvideo.oms.bean.SellPriceResult;
import com.cibnvideo.oms.jdsyncapi.ProductApi;
import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicy;
import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicyCategory;
import com.cibnvideo.oms.tcmallcustomer.bean.PriceProduct;
import com.cibnvideo.oms.tcmallcustomer.dao.PricePolicyDao;
import com.cibnvideo.oms.tcmallcustomer.service.*;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class PricePolicyServiceImpl implements PricePolicyService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    PricePolicyDao pricePolicyDao;

    @Autowired
    ProductApi productApi;

    @Autowired
    ProductOfCollectionService productOfCollectionService;

    @Autowired
    ProductPricePolicyService productPricePolicyService;

    @Autowired
    CollectionPricePolicyService collectionPricePolicyService;

    @Autowired
    CategoryPricePolicyService categoryPricePolicyService;

    @Autowired
    RatePricePolicyService ratePricePolicyService;

    @Override
    public PricePolicy get(Integer id) {
        return pricePolicyDao.get(id);
    }

    @Override
    public List<PricePolicy> list(Map<String, Object> map) {
        return pricePolicyDao.list(map);
    }

    @Override
    public List<PricePolicy> getByType(Integer venderId, Integer type) {
        return pricePolicyDao.getByType(venderId, type);
    }

    @Override
    public List<PricePolicy> getPricePolicyByCollectionId(Integer venderId, Integer collectionId) {
        return pricePolicyDao.getPricePolicyByCollectionId(venderId, collectionId);
    }

    @Override
    public SellPriceResult getSellPriceBySku(BigDecimal venderPercent, Integer venderId, Long sku) {
        logger.info("getSellPriceBySku venderId=" + venderId + " sku=" + sku);
        ResultData<ProductInfo> resultData = productApi.getProductInfo(sku);
        if (resultData.getError() == ErrorCode.SUCCESS && resultData.getData() != null) {
            SellPriceResult sellPriceResult = new SellPriceResult();
            //////合作厂商批发价//////////////
            ProductInfo productInfo = resultData.getData();
            BigDecimal price = productInfo.getPrice();
            BigDecimal priceTrade = price.multiply(venderPercent).setScale(1, BigDecimal.ROUND_HALF_UP);
            BigDecimal priceJd = productInfo.getJdPrice();
            logger.info(sku + " : priceTrade = " + priceTrade);

            sellPriceResult.setPrice(priceTrade.setScale(1, BigDecimal.ROUND_HALF_UP));
            sellPriceResult.setJdPrice(priceJd);
            sellPriceResult.setTradePrice(priceTrade);
            sellPriceResult.setSkuId(productInfo.getSku());
            //////单品定价////////////
            PriceProduct priceProduct = productPricePolicyService.getBySku(venderId.intValue(), sku);
            if (priceProduct != null) {
                BigDecimal priceSell = priceProduct.getPrice();
                sellPriceResult.setPrice(priceSell.setScale(1, BigDecimal.ROUND_HALF_UP));
                logger.info(sku + " : priceProduct = " + priceSell);
                return sellPriceResult;
            } else {
                logger.info(sku + " : priceProduct is null");
            }
            ///////商品集定价////////
            Integer collectionId = productOfCollectionService.getCollectionIdBySku(venderId.intValue(), sku);
            if (collectionId != null) {
                PricePolicy pricePolicy = collectionPricePolicyService.getByCollectionId(venderId.intValue(), collectionId);
                if (pricePolicy != null) {
                    BigDecimal priceSell = priceTrade.multiply(pricePolicy.getPercent());
                    sellPriceResult.setPrice(priceSell.setScale(1, BigDecimal.ROUND_HALF_UP));
                    logger.info(sku + " : collection price is " + priceSell);
                    return sellPriceResult;
                } else {
                    logger.info(sku + " : collection price policy is null");
                }
            } else {
                logger.info(sku + " : product Collection is null");
            }
            //////毛利率定价//////
            if(priceJd != null && priceTrade != null) {
                BigDecimal rate = priceJd.subtract(priceTrade).divide(priceJd, 2, BigDecimal.ROUND_HALF_UP);
                logger.info("rate price policy, rate = " + rate);
                PricePolicy pricePolicy = ratePricePolicyService.getPricePolicyByRate(venderId, rate);
                if(pricePolicy != null) {
                    BigDecimal priceSell = priceTrade.multiply(pricePolicy.getPercent());
                    sellPriceResult.setPrice(priceSell.setScale(1, BigDecimal.ROUND_HALF_UP));
                    return sellPriceResult;
                } else {
                    logger.info("rate price policy, pricePolicy is null");
                }
            }else {
                logger.info("priceJd or priceTrade is null");
            }
            //////分类定价///////
            String categorys = productInfo.getCategory();
            if (!StringUtils.isEmpty(categorys)) {
                String[] catIds = categorys.split(";");
                int cat0 = catIds.length > 0 ? Integer.valueOf(catIds[0]) : 0;
                int cat1 = catIds.length > 1 ? Integer.valueOf(catIds[1]) : 0;
                int cat2 = catIds.length > 2 ? Integer.valueOf(catIds[2]) : 0;
                List<PricePolicyCategory> pricePolicyCategories = categoryPricePolicyService.getByCatId(venderId.intValue(), cat0, cat1, cat2);
                if (pricePolicyCategories != null && pricePolicyCategories.size() > 0) {
                    PricePolicyCategory pricePolicyCategory = new PricePolicyCategory();
                    for (PricePolicyCategory p : pricePolicyCategories) {
                        if (p.getCat0() != 0 && pricePolicyCategory.getCat0() == 0) {
                            pricePolicyCategory.setCat0(p.getCat0());
                            pricePolicyCategory.setPercent(p.getPercent());
                        }
                        if (p.getCat1() != 0 && pricePolicyCategory.getCat1() == 0) {
                            pricePolicyCategory.setCat1(p.getCat1());
                            pricePolicyCategory.setPercent(p.getPercent());
                        }
                        if (p.getCat2() != 0 && pricePolicyCategory.getCat2() == 0) {
                            pricePolicyCategory.setCat2(p.getCat2());
                            pricePolicyCategory.setPercent(p.getPercent());
                        }
                    }
                    if (pricePolicyCategory.getPercent() != null) {
                        BigDecimal priceSell = priceTrade.multiply(pricePolicyCategory.getPercent());
                        sellPriceResult.setPrice(priceSell.setScale(1, BigDecimal.ROUND_HALF_UP));
                        logger.info(sku + " : price category is " + priceSell);
                        return sellPriceResult;
                    } else {
                        logger.info(sku + " : pricePolicyCategory getPercent is null");
                    }
                } else {
                    logger.info(sku + " : pricePolicyCategory is null");
                }
            }
            ////////全站定价///////////
            List<PricePolicy> pricePolicyList = getByType(venderId.intValue(), 0);
            if (pricePolicyList != null && pricePolicyList.size() > 0) {
                PricePolicy pricePolicy = pricePolicyList.get(0);
                BigDecimal priceSell = priceTrade.multiply(pricePolicy.getPercent());
                logger.info(sku + " : all product price is " + priceSell);
                sellPriceResult.setPrice(priceSell.setScale(1, BigDecimal.ROUND_HALF_UP));
                return sellPriceResult;
            } else {
                logger.info(sku + " : all product price is null");
            }
            logger.info(sku + " : end return price = " + sellPriceResult.getPrice() + " tradePrice = " + sellPriceResult.getTradePrice());
            return sellPriceResult;
        } else {
            logger.info(resultData.getMsg() + ": 商品信息获取失败");
            return null;
        }
    }

    @Override
    public int count(Map<String, Object> map) {
        return pricePolicyDao.count(map);
    }

    @Override
    public int save(PricePolicy pricePolicy) {
        return pricePolicyDao.save(pricePolicy);
    }

    @Override
    public int update(PricePolicy pricePolicy) {
        return pricePolicyDao.update(pricePolicy);
    }

    @Override
    public int remove(Integer id) {
        return pricePolicyDao.remove(id);
    }

    @Override
    public BigDecimal getMaxPricePolicy(Integer venderId) {
        return pricePolicyDao.getMaxPricePolicy(venderId);
    }
}
