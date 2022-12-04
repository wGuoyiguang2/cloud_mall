package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.FreeFreightBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/9/7 18:11
 */
@Mapper
@Repository
public interface FreeFreightDao {
    List<FreeFreightBean> listManagerFreeFreight(Map<String, Object> params);

    int countManagerFreeFreight(Map<String, Object> params);

    int addFreeFreight(Map<String, Object> params);

    FreeFreightBean getById(Integer id);

    int updateManagerFreeFreight(Map<String, Object> params);

    Double getByVenderId(Integer venderId);
}
