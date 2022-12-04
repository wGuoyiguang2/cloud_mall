package com.cibnvideo.oms.recommend.service.impl;

import com.cibnvideo.oms.recommend.dao.RecommendHistoryDao;
import com.cibnvideo.oms.recommend.model.bean.RecommendBean;
import com.cibnvideo.oms.recommend.model.bean.RecommendHistoryRequest;
import com.cibnvideo.oms.recommend.service.RecommendHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RecommendHistoryServiceImpl implements RecommendHistoryService {

    @Autowired
    RecommendHistoryDao recommendHistoryDao;

    @Override
    public List<RecommendBean> list(Map<String, Object> params) {
        return recommendHistoryDao.list(params);
    }

    @Override
    public List<RecommendBean> listByDay(RecommendHistoryRequest recommendHistoryRequest) {
        return recommendHistoryDao.listByDay(recommendHistoryRequest);
    }

    @Override
    public RecommendBean get(int id) {
        return recommendHistoryDao.get(id);
    }

    @Override
    public int count(Map<String, Object> params) {
        return recommendHistoryDao.count(params);
    }

    @Override
    public int countToday(Map<String, Object> params) {
        return recommendHistoryDao.countToday(params);
    }

    @Override
    public int save(RecommendBean recommendBean) {
        return recommendHistoryDao.save(recommendBean);
    }

    @Override
    public int update(RecommendBean recommendBean) {
        return recommendHistoryDao.update(recommendBean);
    }

    @Override
    public int updateStatus(int id, int status) {
        return recommendHistoryDao.updateStatus(id, status);
    }

    @Override
    public int remove(Integer id) {
        return recommendHistoryDao.remove(id);
    }
}
