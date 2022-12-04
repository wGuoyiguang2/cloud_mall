package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.HotSearchBean;
import com.cibnvideo.oms.tcmallcustomer.dao.HotSearchDao;
import com.cibnvideo.oms.tcmallcustomer.service.HotSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/13 14:59
 */
@Service
public class HotSearchServiceImpl implements HotSearchService{
    @Autowired
    private HotSearchDao hotSearchDao;
    @Override
    public List<HotSearchBean> listManagerHotSearch(Map<String, Object> params) {
        return hotSearchDao.listManagerHotSearch(params);
    }

    @Override
    public int countManagerHotSearch(Map<String, Object> params) {
        return hotSearchDao.countManagerHotSearch(params);
    }

    @Override
    public int addHotSearch(Map<String, Object> params) {
        return hotSearchDao.addHotSearch(params);
    }

    @Override
    public HotSearchBean getById(Integer id) {
        return hotSearchDao.getById(id);
    }

    @Override
    public int updateManagerHotSearch(Map<String, Object> params) {
        return hotSearchDao.updateManagerHotSearch(params);
    }

    @Override
    public int deleteById(Integer id) {
        return hotSearchDao.deleteById(id);
    }

    @Override
    public List<String> listByVenderId(Long venderId, Integer limit) {
        return hotSearchDao.listByVenderId(venderId,limit);
    }
}
