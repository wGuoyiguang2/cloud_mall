package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.FreeFreightBean;
import com.cibnvideo.oms.tcmallcustomer.dao.FreeFreightDao;
import com.cibnvideo.oms.tcmallcustomer.service.FreeFreightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/9/7 17:58
 */
@Service
public class FreeFreightServiceImpl implements FreeFreightService{
    @Autowired
    private FreeFreightDao dao;
    @Override
    public List<FreeFreightBean> listManagerFreeFreight(Map<String, Object> params) {
        return dao.listManagerFreeFreight(params);
    }

    @Override
    public int countManagerFreeFreight(Map<String, Object> params) {
        return dao.countManagerFreeFreight(params);
    }

    @Override
    public int addFreeFreight(Map<String, Object> params) {
        return dao.addFreeFreight(params);
    }

    @Override
    public FreeFreightBean getById(Integer id) {
        return dao.getById(id);
    }

    @Override
    public int updateManagerFreeFreight(Map<String, Object> params) {
        return dao.updateManagerFreeFreight(params);
    }

    @Override
    public Double getByVenderId(Integer venderId) {
        return dao.getByVenderId(venderId);
    }
}
