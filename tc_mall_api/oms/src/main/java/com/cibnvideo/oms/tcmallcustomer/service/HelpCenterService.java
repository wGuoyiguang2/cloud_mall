package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.HelpCenter;

import java.util.List;
import java.util.Map;

public interface HelpCenterService {
    List<HelpCenter> get(int venderId);
    int update(Map<String,Object> params);
    int add(Map<String,Object> params);
    int delete(int id);
    int deleteCustomer(int venderId);
}
