package com.hqtc.searchtask.service.impl;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.searchtask.bmsapi.ProductVolumeApi;
import com.hqtc.searchtask.jdsyncapi.ProductApi;
import com.hqtc.searchtask.model.bean.ProductDo;
import com.hqtc.searchtask.model.bean.ProductOfCollection;
import com.hqtc.searchtask.model.bean.SellPriceResult;
import com.hqtc.searchtask.omsapi.ProductOfCollectionApi;
import com.hqtc.searchtask.omsapi.ProductRemoveApi;
import com.hqtc.searchtask.omsapi.VenderSettlementApi;
import com.hqtc.searchtask.service.EsProductSyncService;
import com.hqtc.searchtask.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EsProductSyncServiceImpl implements EsProductSyncService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ProductApi productApi;

    @Autowired
    SearchService searchService;

    @Autowired
    VenderSettlementApi venderSettlementApi;

    @Autowired
    ProductVolumeApi productVolumeApi;

    @Autowired
    ProductOfCollectionApi productOfCollectionApi;

    @Autowired
    ProductRemoveApi productRemoveApi;

    @Override
    public void productSync(Integer venderId, ProductDo productDo) {
        ResultData<Map<String, Integer>> productVolumeResultData = productVolumeApi.getProductSalesVolume(productDo.getSku().toString(), venderId, 1);
        if (productVolumeResultData.getError() == ErrorCode.SUCCESS) {
            Map<String, Integer> productVolume = productVolumeResultData.getData();
            if (productVolume != null) {
                productDo.setSales(productVolume.get(productDo.getSku().toString()));
            }
        } else {
            logger.error(productVolumeResultData.getMsg());
        }
        ResultData<Integer> collectionIdResultData = productOfCollectionApi.getCollectionIdBySku(venderId, productDo.getSku());
        if(collectionIdResultData.getError() == ErrorCode.SUCCESS) {
            Integer collectionId = collectionIdResultData.getData();
            if(collectionId != null) {
                productDo.setCollectionids(collectionId.toString());
            }
        }
        logger.info("sku=" + productDo.getSku() + " sales = " + productDo.getSales() + " collectionids = " + productDo.getCollectionids());
        ResultData<SellPriceResult> sellPriceResultData = venderSettlementApi.getPrice(venderId, productDo.getSku());
        if (sellPriceResultData.getError() == ErrorCode.SUCCESS) {
            SellPriceResult sellPriceResult = sellPriceResultData.getData();
            if (sellPriceResult != null) {
                productDo.setPrice(sellPriceResult.getPrice());
                productDo.setAgreeprice(sellPriceResult.getTradePrice());
                Map<String, Object> params = new HashMap<String, Object>(2);
                params.put("venderId", venderId);
                params.put("sku", productDo.getSku());
                ResultData<Integer> countResult = productRemoveApi.count(params);
                if(countResult.getError() == ErrorCode.SUCCESS) {
                    Integer count = countResult.getData();
                    if(count > 0) {
                        productDo.setState(0);
                        logger.info("product is removed, sku = " + productDo.getSku());
                    }
                    ProductDo productDoSaved = searchService.getProduct(venderId, productDo.getSku());
                    if (productDoSaved != null && productDoSaved.equals(productDo)) {
                        logger.info("es product saved is same, do not update");
                    } else {
                        boolean result = searchService.insertOrUpdateProduct(venderId, productDo);
                        logger.info("es product insert or update " + result);
                    }
                } else {
                    logger.error(countResult.getMsg());
                }

            }
        } else {
            logger.error(sellPriceResultData.getMsg());
        }

    }

    @Override
    public void productSyncByVenderId(Integer venderId) {
        int pageSize = 100;
        int index = 0;
        int totalRow = 1;
        HashMap<String, Object> params = new HashMap<String, Object>();
        for (index = 0; index < totalRow; ) {
            params.put("offset", index);
            params.put("limit", pageSize);
            logger.info("offset=" + index + " pagesize = " + pageSize);
            ResultData<DataList<List<ProductDo>>> resultData = productApi.listProductForEs(params);
            if (resultData.getError() == ErrorCode.SUCCESS) {
                DataList<List<ProductDo>> dataList = resultData.getData();
                if (dataList != null) {
                    totalRow = dataList.getTotalRows();
                    for (ProductDo p : dataList.getData()) {
                        productSync(venderId, p);
                    }
                } else {
                    totalRow = 0;
                }
            } else {
                totalRow = 0;
            }
            index = index + pageSize;
        }

    }

    @Override
    public void productRemove(Integer venderId, Long sku) {
        //searchService.productRemove(venderId, sku);
        ProductDo productDo = searchService.getProduct(venderId, sku);
        if(productDo != null) {
            productDo.setState(0);
            searchService.insertOrUpdateProduct(venderId, productDo);
        }
    }

    @Override
    public void venderProductRemove(Integer venderId) {
        searchService.venderRemove(venderId);
    }

    @Override
    public void productSyncBySku(Integer venderId, Long sku) {
        ResultData<ProductDo> resultData = productApi.getProductForEs(sku);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            ProductDo productDo = resultData.getData();
            if (productDo != null) {
                productSync(venderId, productDo);
            }
        }
    }

    @Override
    public void productSyncByCollectionId(Integer venderId, Integer collectionId) {
        int pageSize = 100;
        int index = 0;
        int totalRow = 1;
        HashMap<String, Object> params = new HashMap<String, Object>();
        for (index = 0; index < totalRow; ) {
            params.put("venderId", venderId);
            params.put("collectionId", collectionId);
            params.put("offset", index);
            params.put("limit", pageSize);
            logger.info("offset=" + index + " pagesize = " + pageSize);
            ResultData<DataList<List<ProductOfCollection>>> resultData = productOfCollectionApi.listProductOfCollection(params);
            if (resultData.getError() == ErrorCode.SUCCESS) {
                DataList<List<ProductOfCollection>> dataList = resultData.getData();
                if (dataList != null) {
                    totalRow = dataList.getTotalRows();
                    for (ProductOfCollection p : dataList.getData()) {
                        productSyncBySku(venderId, p.getSku());
                    }
                } else {
                    totalRow = 0;
                }
            } else {
                totalRow = 0;
            }
            index = index + pageSize;
        }
    }

    @Override
    public void productSyncByCatId(Integer venderId, Integer catId, Integer catType) {
        int pageSize = 100;
        int index = 0;
        int totalRow = 1;
        HashMap<String, Object> params = new HashMap<String, Object>();
        if (catType == -1) {
            return;
        }
        switch (catType) {
            case 0:
                params.put("cat0", catId);
                break;
            case 1:
                params.put("cat1", catId);
                break;
            case 2:
                params.put("cat2", catId);
                break;
            default:
                return;
        }
        for (index = 0; index < totalRow; ) {
            params.put("offset", index);
            params.put("limit", pageSize);
            logger.info("offset=" + index + " pagesize = " + pageSize);
            ResultData<DataList<List<ProductDo>>> resultData = productApi.listProductForEs(params);
            if (resultData.getError() == ErrorCode.SUCCESS) {
                DataList<List<ProductDo>> dataList = resultData.getData();
                if (dataList != null) {
                    totalRow = dataList.getTotalRows();
                    for (ProductDo p : dataList.getData()) {
                        productSync(venderId, p);
                    }
                } else {
                    totalRow = 0;
                }
            } else {
                totalRow = 0;
            }
            index = index + pageSize;
        }
    }
}
