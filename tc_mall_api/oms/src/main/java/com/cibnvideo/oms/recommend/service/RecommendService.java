package com.cibnvideo.oms.recommend.service;

import com.cibnvideo.oms.recommend.model.bean.RecommendBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RecommendService {
    List<RecommendBean> list(Map<String, Object> params);

    RecommendBean get(int id);

    RecommendBean getbyposition(@Param("venderId") Integer venderId, @Param("layoutId") Integer layoutId, @Param("position") Integer position);

    int count(Map<String, Object> params);

    int save(RecommendBean recommendBean);

    int update(RecommendBean recommendBean);

    int remove(Integer id);

    int removeByLayoutId(Integer layoutId);

    int copyByLayoutId(Integer venderId, Integer parentLayoutId, Integer layoutId) throws Exception;
}
