package com.cibnvideo.oms.recommend.dao;

import com.cibnvideo.oms.recommend.model.bean.RecommendHistoryPeriod;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface RecommendHistoryPeriodDao {
    List<RecommendHistoryPeriod> list(Map<String, Object> params);

    RecommendHistoryPeriod get(Integer id);

    RecommendHistoryPeriod getByVenderId(Integer venderId);

    int count(Map<String, Object> params);

    int save(RecommendHistoryPeriod recommendHistoryPeriod);

    int update(RecommendHistoryPeriod recommendHistoryPeriod);

    int remove(Integer id);
}
