package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.HelpCenter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HelpCenterDao {
    List<HelpCenter> get(int venderId);
    int update(Map<String,Object> params);
    int add(Map<String,Object> params);
    int delete(int id);
    int deleteCustomer(int venderId);
}
