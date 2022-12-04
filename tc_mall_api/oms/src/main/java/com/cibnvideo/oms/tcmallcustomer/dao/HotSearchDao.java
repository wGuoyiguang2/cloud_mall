package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.HotSearchBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/13 14:59
 */
@Repository
@Mapper
public interface HotSearchDao {
    List<HotSearchBean> listManagerHotSearch(Map<String, Object> params);

    int countManagerHotSearch(Map<String, Object> params);

    int addHotSearch(Map<String, Object> params);

    HotSearchBean getById(@Param("id") Integer id);

    int updateManagerHotSearch(Map<String, Object> params);

    int deleteById(@Param("id") Integer id);

    List<String> listByVenderId(@Param("venderId") Long venderId, @Param("limit")Integer limit);
}
