package com.cibnvideo.oms.recommend.dao;

import com.cibnvideo.oms.recommend.model.bean.RecommendBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecommendDao {
    List<RecommendBean> list(Map<String, Object> params);

    RecommendBean get(int id);

    RecommendBean getbyposition(@Param("venderId") Integer venderId, @Param("layoutId") Integer layoutId, @Param("position") Integer position);

    int count(Map<String, Object> params);

    int save(RecommendBean recommendBean);

    int update(RecommendBean recommendBean);

    int remove(Integer id);

    int removeByLayoutId(Integer layoutId);
}
