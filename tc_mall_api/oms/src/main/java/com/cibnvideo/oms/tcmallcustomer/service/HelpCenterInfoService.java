package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.ExplainBean;
import com.cibnvideo.oms.tcmallcustomer.bean.HelpCenterInfoVo;

import java.util.List;
import java.util.Map;

public interface HelpCenterInfoService {
    List<ExplainBean> get(int id);
    int add(Map<String,Object> params);
    int update(Map<String,Object> params);
    int delete(int id);
    List<HelpCenterInfoVo> list(Map<String, Object> params);
    Integer count(Map<String, Object> params);
    HelpCenterInfoVo getQAAndTypeById(int id);
    int updateQA(Map<String, Object> params);
    int deleteQAAndTypeByTypeId(Integer id);
    int addQA(Map<String,Object> params);
    int addType(Map<String, Object> params);
    List<HelpCenterInfoVo> listAllType(Long venderId);
    HelpCenterInfoVo getTypeById(Integer typeId);
    int deleteTypeById(Integer id);
}
