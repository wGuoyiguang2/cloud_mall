package com.hqtc.searchtask.service.impl;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.searchtask.jdsyncapi.ProductApi;
import com.hqtc.searchtask.model.bean.ProductDo;
import com.hqtc.searchtask.omsapi.VenderSettlementApi;
import com.hqtc.searchtask.service.EsProductSyncService;
import com.hqtc.searchtask.service.ProductSyncService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProductSyncServiceImpl implements ProductSyncService {
    private Logger logger  = LoggerFactory.getLogger(getClass());

    @Autowired
    ProductApi productApi;

    @Autowired
    EsProductSyncService esProductSync;

    @Autowired
    VenderSettlementApi venderSettlementApi;

    int pageSize = 100;
    int index = 0;
    int totalRow = 1;

    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void productSyncStart() throws Exception{
        if(lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                logger.info("productSync start...");
                HashMap<String, Object> params = new HashMap<String, Object>();
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
                for(index=0;index<totalRow;){
                    params.put("offset", index);
                    params.put("limit", pageSize);
                    logger.info("offset=" + index + " pagesize = " + pageSize);
                    ResultData<DataList<List<ProductDo>>> resultData = productApi.listProductForEs(params);
                    if(resultData.getError() == ErrorCode.SUCCESS){
                        DataList<List<ProductDo>> dataList = resultData.getData();
                        if(dataList != null){
                            totalRow = dataList.getTotalRows();
                            for(ProductDo p:dataList.getData()){
                                for(Integer venderId:venderIds){
                                    esProductSync.productSync(venderId, (ProductDo) BeanUtils.cloneBean(p));
                                }
                            }
                        }else {
                            totalRow = 0;
                        }
                    }else {
                        totalRow = 0;
                    }
                    index = index + pageSize;
                }
            }catch (Exception e){
                logger.error("productSyncStart Exception", e);
            } finally {
                lock.unlock();
            }
        }else {
            logger.error("productSync tryLock failed");
            throw new Exception("此任务正在执行");
        }
    }
}
