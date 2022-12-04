package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.ConfigManagerVo;
import com.cibnvideo.oms.tcmallcustomer.dao.ConfigDao;
import com.cibnvideo.oms.tcmallcustomer.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/8 14:59
 */
@Service
public class ConfigServiceImpl implements ConfigService{
    @Autowired
    private ConfigDao configDao;
    @Override
    public List<ConfigManagerVo> listManagerConfig(Map<String, Object> params) {
        return configDao.listManagerConfig(params);
    }

    @Override
    public int countManagerConfig(Map<String, Object> params) {
        return configDao.countManagerConfig(params);
    }

    @Override
    public int addConfig(Map<String, Object> params) {
        return configDao.addConfig(params);
    }

    @Override
    public ConfigManagerVo getById(Integer id) {
        return configDao.getById(id);
    }

    @Override
    public int updateManagerConfig(Map<String, Object> params) {
        return configDao.updateManagerConfig(params);
    }

    @Override
    public int deleteById(Integer id) {
        return configDao.deleteById(id);
    }
}
