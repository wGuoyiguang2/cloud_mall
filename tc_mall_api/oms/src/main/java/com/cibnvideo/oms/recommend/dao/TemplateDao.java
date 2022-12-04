package com.cibnvideo.oms.recommend.dao;

import com.cibnvideo.oms.recommend.model.bean.TemplateBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TemplateDao {
    List<TemplateBean> list(Map<String, Object> params);

    TemplateBean get(int templateId);

    int count(Map<String, Object> params);

    int save(TemplateBean templateBean);

    int update(TemplateBean templateBean);

    int remove(Integer templateId);
}
