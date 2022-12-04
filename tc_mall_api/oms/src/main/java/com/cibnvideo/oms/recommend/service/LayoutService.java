package com.cibnvideo.oms.recommend.service;

import com.cibnvideo.oms.recommend.model.bean.LayoutBean;
import com.cibnvideo.oms.recommend.model.bean.LayoutRecommendBean;

import java.util.List;
import java.util.Map;

public interface LayoutService {

    List<LayoutBean> list(Map<String, Object> params);

    List<LayoutRecommendBean> getLayoutRecommendList(Map<String, Object> params);

    LayoutBean get(int id);

    int count(Map<String, Object> params);

    int save(LayoutBean layoutBean);

    int update(LayoutBean layoutBean);

    int remove(Integer id) throws Exception;

    int copyByLayoutId(Integer venderId, Integer parentId) throws Exception;
}
