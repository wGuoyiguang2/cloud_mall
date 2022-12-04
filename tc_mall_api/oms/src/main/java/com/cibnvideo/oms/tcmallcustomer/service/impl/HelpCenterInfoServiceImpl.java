package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.ExplainBean;
import com.cibnvideo.oms.tcmallcustomer.bean.HelpCenterInfoVo;
import com.cibnvideo.oms.tcmallcustomer.dao.HelpCenterInfoDao;
import com.cibnvideo.oms.tcmallcustomer.service.HelpCenterInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HelpCenterInfoServiceImpl implements HelpCenterInfoService {

    @Autowired
    HelpCenterInfoDao helpCenterInfoDao;
    @Override
    public List<ExplainBean> get(int id) {
        return helpCenterInfoDao.get(id);
    }

    @Override
    public int add(Map<String, Object> params) {
        return helpCenterInfoDao.add(params);
    }

    @Override
    public int update(Map<String, Object> params) {
        return helpCenterInfoDao.update(params);
    }

    @Override
    public int delete(int id) {
        return helpCenterInfoDao.delete(id);
    }

    @Override
    public List<HelpCenterInfoVo> list(Map<String, Object> params) {
        return helpCenterInfoDao.list(params);
    }

    @Override
    public Integer count(Map<String, Object> params) {
        return helpCenterInfoDao.count(params);
    }

    @Override
    public HelpCenterInfoVo getQAAndTypeById(int id) {
        return helpCenterInfoDao.getQAAndTypeById(id);
    }

    @Override
    public int updateQA(Map<String, Object> params) {
        return helpCenterInfoDao.updateQA(params);
    }

    @Override
    public int deleteQAAndTypeByTypeId(Integer id) {
        return helpCenterInfoDao.deleteQAAndTypeByTypeId(id);
    }

    @Override
    public int addQA(Map<String, Object> params) {
        return helpCenterInfoDao.addQA(params);
    }

    @Override
    public int addType(Map<String, Object> params) {
        return helpCenterInfoDao.addType(params);
    }

    @Override
    public List<HelpCenterInfoVo> listAllType(Long venderId) {
        return helpCenterInfoDao.listAllType(venderId);
    }

    @Override
    public HelpCenterInfoVo getTypeById(Integer typeId) {
        return helpCenterInfoDao.getTypeById(typeId);
    }

    @Override
    public int deleteTypeById(Integer id) {
        return helpCenterInfoDao.deleteTypeById(id);
    }
}
