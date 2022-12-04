package com.hqtc.ims.searchhistory.service.impl;

import com.hqtc.ims.searchhistory.model.bean.SearchHistoryBean;
import com.hqtc.ims.searchhistory.model.mapper.SearchHistoryMapper;
import com.hqtc.ims.searchhistory.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/6 10:17
 */
@Service
public class SearchHistoryServiceImpl implements SearchHistoryService{
    @Autowired
    private SearchHistoryMapper searchHistoryMapper;
    @Override
    public Integer createSearchHistory(SearchHistoryBean searchHistoryBean) {
        return searchHistoryMapper.createSearchHistory(searchHistoryBean);
    }

    @Override
    public List<String> listKeywordsByUserId(int userId, int limit) {
        return searchHistoryMapper.listKeywordsByUserId(userId, limit);
    }

    @Override
    public List<String> listKeywordsByMac(String mac, int limit) {
        return searchHistoryMapper.listKeywordsByMac(mac, limit);
    }

    @Override
    public int deleteKeywordsByUserId(Integer userId) {
        return searchHistoryMapper.deleteKeywordsByUserId(userId);
    }

    @Override
    public int deleteKeywordsByMac(String mac) {
        return searchHistoryMapper.deleteKeywordsByMac(mac);
    }
}
