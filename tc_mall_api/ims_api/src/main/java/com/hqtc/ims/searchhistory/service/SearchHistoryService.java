package com.hqtc.ims.searchhistory.service;

import com.hqtc.ims.searchhistory.model.bean.SearchHistoryBean;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/6 10:17
 */
public interface SearchHistoryService {
    Integer createSearchHistory(SearchHistoryBean searchHistoryBean);

    List<String> listKeywordsByUserId(int userId, int limit);

    List<String> listKeywordsByMac(String mac, int limit);

    int deleteKeywordsByUserId(Integer userId);

    int deleteKeywordsByMac(String mac);
}
