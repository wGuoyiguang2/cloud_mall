package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.ConfigManagerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/8 15:03
 */
@Mapper
@Repository
public interface ConfigDao {

    List<ConfigManagerVo> listManagerConfig(Map<String, Object> params);

    int countManagerConfig(Map<String, Object> params);

    int addConfig(Map<String, Object> params);

    ConfigManagerVo getById(@Param("id") Integer id);

    int updateManagerConfig(Map<String, Object> params);

    int deleteById(Integer id);
}
