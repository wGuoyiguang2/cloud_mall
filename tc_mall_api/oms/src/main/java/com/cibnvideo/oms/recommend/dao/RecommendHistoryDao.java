package com.cibnvideo.oms.recommend.dao;

import com.cibnvideo.oms.recommend.model.bean.RecommendBean;
import com.cibnvideo.oms.recommend.model.bean.RecommendHistoryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface RecommendHistoryDao {
    List<RecommendBean> list(Map<String, Object> params);

    List<RecommendBean> listByDay(RecommendHistoryRequest recommendHistoryRequest);

    RecommendBean get(int id);

    int count(Map<String, Object> params);

    int countToday(Map<String, Object> params);

    int save(RecommendBean recommendBean);

    int update(RecommendBean recommendBean);

    int updateStatus(@Param("id") int id, @Param("status") int status);

    int remove(Integer id);
}
