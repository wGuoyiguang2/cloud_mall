package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.CustomerInfo;

import java.util.Map;

public interface CustomerInfoService {
    CustomerInfo get(int venderId);
    int update(Map<String,Object> params);
    int add(Map<String,Object> params);
    int delete(int venderId);
}
