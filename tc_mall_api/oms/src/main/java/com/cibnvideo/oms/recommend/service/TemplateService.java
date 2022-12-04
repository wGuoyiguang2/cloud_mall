package com.cibnvideo.oms.recommend.service;

import com.cibnvideo.oms.recommend.model.bean.TemplateBean;

import java.util.List;
import java.util.Map;

public interface TemplateService {
    List<TemplateBean> list(Map<String, Object> params);

    TemplateBean get(int templateId);

    int count(Map<String, Object> params);

    int save(TemplateBean templateBean);

    int update(TemplateBean templateBean);

    int remove(Integer templateId);
}
