package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.ExplainBean;
import com.cibnvideo.oms.tcmallcustomer.bean.HelpCenterInfo;
import com.cibnvideo.oms.tcmallcustomer.bean.HelpCenterInfoVo;
import com.hqtc.common.response.ResultData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HelpCenterInfoDao {
    List<ExplainBean> get(int id);
    int add(Map<String,Object> params);
    int update(Map<String,Object> params);
    int delete(int id);
    List<HelpCenterInfoVo> list(Map<String, Object> params);
    Integer count(Map<String, Object> params);
    HelpCenterInfoVo getQAAndTypeById(@Param("id") int id);
    int updateQA(Map<String, Object> params);
    int deleteQAAndTypeByTypeId(@Param("id") Integer id);
    int addQA(Map<String,Object> params);
    int addType(Map<String, Object> params);
    List<HelpCenterInfoVo> listAllType(@Param("venderId") Long venderId);
    HelpCenterInfoVo getTypeById(@Param("typeId") Integer typeId);
    int deleteTypeById(@Param("id") Integer id);
}

