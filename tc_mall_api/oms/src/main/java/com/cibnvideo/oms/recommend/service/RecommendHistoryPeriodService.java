package com.cibnvideo.oms.recommend.service;

public interface RecommendHistoryPeriodService {
    public int getRecommendHistoryPeroid(Integer venderId);

    public int updatePeriod(Integer venderId, Integer period);
}
