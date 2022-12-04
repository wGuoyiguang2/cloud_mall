package com.cibnvideo.jdsynctask.task;

import com.cibnvideo.jdsynctask.service.*;
import com.cibnvideo.job.model.JobResult;
import com.cibnvideo.job.service.JobService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JdSyncTaskService implements JobService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JdProductSyncService jdProductSync;

    @Autowired
    JdAddressSyncService jdAddressSync;

    @Autowired
    JdCategorySyncService jdCategorySync;

    @Autowired
    JdPriceSyncService jdPriceSync;

    @Autowired
    JdGetMessageService jdGetMessage;

    @Override
    public JobResult execute(String param) {
        JobResult result = new JobResult();
        if (StringUtils.isNotBlank(param)) {
            switch (param) {
                case "getmessage":
                    try {
                        logger.info("getMessage job start");
                        jdGetMessage.getMessage();
                        logger.info("getMessage job end");
                        result.setStatus(1);
                        result.setMessage("执行成功");
                    } catch (Exception e) {
                        logger.error("getMessage failed", e);
                        result.setStatus(0);
                        result.setMessage(e.getMessage());
                    }
                    break;
                case "product_detail_sync":
                    try {
                        logger.info("product detail sync job start");
                        jdProductSync.syncProductDetailStart();
                        logger.info("product detail sync job end");
                        result.setStatus(1);
                        result.setMessage("执行成功");
                    } catch (Exception e) {
                        logger.error("search product sync exception", e);
                        result.setStatus(0);
                        result.setMessage(e.getMessage());
                    }
                    break;
                case "address_sync":
                    try {
                        logger.info("address_sync job start");
                        jdAddressSync.syncAddressProvinces();
                        logger.info("address_sync job end");
                        result.setStatus(1);
                        result.setMessage("执行成功");
                    } catch (Exception e) {
                        logger.error("address_sync exception", e);
                        result.setStatus(0);
                        result.setMessage(e.getMessage());
                    }
                    break;
                case "category_sync":
                    try {
                        logger.info("category_sync job start");
                        jdCategorySync.syncCategorys();
                        logger.info("category_sync job end");
                        result.setStatus(1);
                        result.setMessage("执行成功");
                    } catch (Exception e) {
                        logger.error("category_sync exception", e);
                        result.setStatus(0);
                        result.setMessage(e.getMessage());
                    }
                    break;
                case "price_sync":
                    try {
                        logger.info("price_sync job start");
                        jdPriceSync.syncProductPrice();
                        logger.info("price_sync job end");
                        result.setStatus(1);
                        result.setMessage("执行成功");
                    } catch (Exception e) {
                        logger.error("price_sync exception", e);
                        result.setStatus(0);
                        result.setMessage(e.getMessage());
                    }
                    break;
                default:
                    logger.error("未知任务参数:" + param);
                    break;
            }
        } else {
            result.setStatus(0);
            result.setMessage("任务参数为空");
        }
        return result;
    }
}
