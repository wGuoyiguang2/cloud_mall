package com.cibnvideo.jdsynctask.service.impl;

import ch.qos.logback.core.util.TimeUtil;
import com.cibnvideo.jdsynctask.dao.JdProductCategoryDao;
import com.cibnvideo.jdsynctask.model.CategoryPageRequestParams;
import com.cibnvideo.jdsynctask.model.CategoryResult;
import com.cibnvideo.jdsynctask.model.CategoryResultInfo;
import com.cibnvideo.jdsynctask.service.JdCategorySyncService;
import com.cibnvideo.jdsynctask.service.JdGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class JdCategorySyncServiceImpl implements JdCategorySyncService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdProductCategoryDao jdProductCategoryDao;

    @Autowired
    JdGoodsService jdGoodsService;

    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void syncCategorys() throws Exception{
        if(lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                int pageNo = 1;
                int pageSize = 100;
                CategoryPageRequestParams categoryPageRequestParams = new CategoryPageRequestParams();
                categoryPageRequestParams.setPageNo(pageNo);
                categoryPageRequestParams.setPageSize(pageSize);
                CategoryResult categoryResult = jdGoodsService.getCategorys(categoryPageRequestParams);
                if (categoryResult != null) {
                    categorysSave(categoryResult);
                    Integer totalRows = categoryResult.getTotalRows();
                    if (totalRows > pageSize) {
                        int pageTotalSize = (int) Math.ceil(((double) totalRows) / ((double) pageSize));
                        for (int i = 2; i <= pageTotalSize; i++) {
                            categoryPageRequestParams.setPageNo(i);
                            categoryPageRequestParams.setPageSize(pageSize);
                            categorysSave(jdGoodsService.getCategorys(categoryPageRequestParams));
                        }
                    }
                } else {
                    logger.error("categorys is null");
                }

            } catch (Exception e) {
                logger.error("syncCategorys Exception", e);
            } finally {
                lock.unlock();
            }
        } else {
            logger.error("syncCategorys tryLock failed");
        }
    }

    private void categorysSave(CategoryResult categoryResult) {
        if (categoryResult != null) {
            List<CategoryResultInfo> categoryResultInfoList = categoryResult.getCategorys();
            for (CategoryResultInfo info : categoryResultInfoList) {
                CategoryResultInfo infoSaved = jdProductCategoryDao.get(info.getCatId());
                if (infoSaved != null) {
                    if (!infoSaved.equals(info)) {
                        jdProductCategoryDao.update(info);
                    }
                } else {
                    jdProductCategoryDao.save(info);
                }
            }
        }
    }
}
