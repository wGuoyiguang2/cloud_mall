package com.cibnvideo.oms.recommend.dao;

import com.cibnvideo.oms.recommend.model.bean.LayoutBean;
import com.cibnvideo.oms.recommend.model.bean.LayoutRecommendBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LayoutDao {
    List<LayoutBean> list(Map<String, Object> params);

    List<LayoutRecommendBean> getLayoutRecommendList(Map<String, Object> params);

    LayoutBean get(int id);

    int count(Map<String, Object> params);

    int save(LayoutBean layoutBean);

    int update(LayoutBean layoutBean);

    int remove(Integer id);
}
