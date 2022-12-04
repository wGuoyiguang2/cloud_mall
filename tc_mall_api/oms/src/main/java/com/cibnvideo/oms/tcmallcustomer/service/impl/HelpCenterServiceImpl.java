package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.HelpCenter;
import com.cibnvideo.oms.tcmallcustomer.dao.HelpCenterDao;
import com.cibnvideo.oms.tcmallcustomer.service.HelpCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HelpCenterServiceImpl implements HelpCenterService {

    @Autowired
    HelpCenterDao helpCenterDao;
    @Override
    public List<HelpCenter> get(int venderId) {
        return helpCenterDao.get(venderId);
    }

    @Override
    public int update(Map<String, Object> params) {
        return helpCenterDao.update(params);
    }

    @Override
    public int add(Map<String, Object> params) {
        return helpCenterDao.add(params);
    }

    @Override
    public int delete(int id) {
        return helpCenterDao.delete(id);
    }

    @Override
    public int deleteCustomer(int venderId) {
        return helpCenterDao.deleteCustomer(venderId);
    }
}
