package com.cibnvideo.oms.recommend.service.impl;

import com.cibnvideo.oms.recommend.dao.RecommendHistoryPeriodDao;
import com.cibnvideo.oms.recommend.model.bean.RecommendHistoryPeriod;
import com.cibnvideo.oms.recommend.service.RecommendHistoryPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class RecommendHistoryPeriodServiceImpl implements RecommendHistoryPeriodService {

    @Autowired
    RecommendHistoryPeriodDao recommendHistoryPeriodDao;

    @Override
    public int getRecommendHistoryPeroid(Integer venderId) {
        int period = 30;
        RecommendHistoryPeriod recommendHistoryPeriod = recommendHistoryPeriodDao.getByVenderId(venderId);
        if(recommendHistoryPeriod != null){
            period = recommendHistoryPeriod.getPeriod();
        }
        return period;
    }

    @Override
    public int updatePeriod(Integer venderId, Integer period) {
        RecommendHistoryPeriod recommendHistoryPeriod = new RecommendHistoryPeriod();
        recommendHistoryPeriod.setVenderId(venderId);
        recommendHistoryPeriod.setPeriod(period);
        recommendHistoryPeriod.setCtime(new Date());
        recommendHistoryPeriod.setUtime(new Date());
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("venderId", venderId);
        int count = recommendHistoryPeriodDao.count(params);
        if(count == 0){
            return recommendHistoryPeriodDao.save(recommendHistoryPeriod);
        }else {
            return recommendHistoryPeriodDao.update(recommendHistoryPeriod);
        }
    }
}
