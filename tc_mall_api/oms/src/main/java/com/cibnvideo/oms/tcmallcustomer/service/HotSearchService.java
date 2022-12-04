package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.HotSearchBean;

import java.util.List;
import java.util.Map; /**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/13 14:59
 */
public interface HotSearchService {
    List<HotSearchBean> listManagerHotSearch(Map<String, Object> params);

    int countManagerHotSearch(Map<String, Object> params);

    int addHotSearch(Map<String, Object> params);

    HotSearchBean getById(Integer id);

    int updateManagerHotSearch(Map<String, Object> params);

    int deleteById(Integer id);

    List<String> listByVenderId(Long venderId, Integer limit);
}
