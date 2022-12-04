package com.hqtc.search.service;

import com.hqtc.search.model.bean.BrandParamsBean;
import com.hqtc.search.model.bean.CateParamsBean;

import java.util.List;

/**
 * Created by makuan on 18-8-16.
 */
public interface CateService {
    /**
     * 获取分类id
     * @param cateParams
     * @return
     */
    List<Integer> getCategory(CateParamsBean cateParams);

    List<String> getBrandList(BrandParamsBean brandParams);
}
