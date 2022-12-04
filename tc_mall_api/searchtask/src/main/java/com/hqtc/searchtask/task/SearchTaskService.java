package com.hqtc.searchtask.task;

import com.cibnvideo.job.model.JobResult;
import com.cibnvideo.job.service.JobService;
import com.hqtc.searchtask.service.EsProductSyncService;
import com.hqtc.searchtask.service.ProductSyncService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchTaskService implements JobService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    ProductSyncService productSync;
    @Autowired
    EsProductSyncService esProductSync;

    @Override
    public JobResult execute(String param) {
        JobResult result = new JobResult();
        if (StringUtils.isNotBlank(param)) {
            switch (param) {
                case "search_product_sync":
                    try {
                        logger.info("search product sync job start");
                        productSync.productSyncStart();
                        logger.info("search product sync job end");
                        result.setStatus(1);
                        result.setMessage("执行成功");
                    } catch (Exception e) {
                        logger.error("search product sync exception", e);
                        result.setStatus(0);
                        result.setMessage(e.getMessage());
                    }
                    break;
                default:
                    result.setStatus(0);
                    result.setMessage("未知任务参数:" + param);
                    logger.error("unknow message type");
                    break;
            }
        } else {
            result.setStatus(0);
            result.setMessage("任务参数为空");
        }
        return result;
    }
}
