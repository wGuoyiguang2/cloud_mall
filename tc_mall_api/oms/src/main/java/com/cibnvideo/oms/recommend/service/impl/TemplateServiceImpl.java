package com.cibnvideo.oms.recommend.service.impl;

import com.cibnvideo.oms.recommend.dao.TemplateDao;
import com.cibnvideo.oms.recommend.model.bean.TemplateBean;
import com.cibnvideo.oms.recommend.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    TemplateDao templateDao;
    @Override
    public List<TemplateBean> list(Map<String, Object> params) {
        return templateDao.list(params);
    }

    @Override
    public TemplateBean get(int templateId) {
        return templateDao.get(templateId);
    }

    @Override
    public int count(Map<String, Object> params) {
        return templateDao.count(params);
    }

    @Override
    public int save(TemplateBean templateBean) {
        return templateDao.save(templateBean);
    }

    @Override
    public int update(TemplateBean templateBean) {
        return templateDao.update(templateBean);
    }

    @Override
    public int remove(Integer templateId) {
        return templateDao.remove(templateId);
    }
}
