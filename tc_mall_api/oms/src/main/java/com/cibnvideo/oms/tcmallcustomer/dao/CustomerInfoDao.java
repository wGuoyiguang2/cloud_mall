package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.CustomerInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CustomerInfoDao {

    CustomerInfo get(int venderId);
    int update(Map<String,Object> params);
    int add(Map<String,Object> params);
    int delete(int venderId);
}
