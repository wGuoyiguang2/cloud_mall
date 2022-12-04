package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.CustomerInfo;
import com.cibnvideo.oms.tcmallcustomer.dao.CustomerInfoDao;
import com.cibnvideo.oms.tcmallcustomer.service.CustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {

    @Autowired
    CustomerInfoDao customerInfoDao;
    @Override
    public CustomerInfo get(int venderId) {
        return customerInfoDao.get(venderId);
    }

    @Override
    public int update(Map<String, Object> params) {
        return customerInfoDao.update(params);
    }

    @Override
    public int add(Map<String, Object> params) {
        return customerInfoDao.add(params);
    }

    @Override
    public int delete(int venderId) {
        return customerInfoDao.delete(venderId);
    }
}
