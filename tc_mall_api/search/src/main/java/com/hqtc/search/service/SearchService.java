package com.hqtc.search.service;

import com.hqtc.search.model.bean.ProductListBean;
import com.hqtc.search.model.bean.SearchParamsBean;
import com.hqtc.search.model.bean.SkuSearchParamsBean;

/**
 * Created by makuan on 18-8-9.
 */
public interface SearchService {
    /**
     * 搜索
     * @param searchParams
     * @return
     */
    ProductListBean fuzzySearch(SearchParamsBean searchParams);

    /**
     * 根据指定sku搜索商品,多个sku以逗号分隔
     * @param skuSearchParams
     * @return
     */
    ProductListBean idsQuery(SkuSearchParamsBean skuSearchParams);
}
