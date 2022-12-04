package com.hqtc.search.controller;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import com.hqtc.search.config.Router;
import com.hqtc.search.model.bean.ProductListBean;
import com.hqtc.search.model.bean.SearchParamsBean;
import com.hqtc.search.model.bean.SkuSearchParamsBean;
import com.hqtc.search.service.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by makuan on 18-8-9.
 */
@RestController
public class SearchController {

    @Autowired
    SearchServiceImpl searchServiceImpl;

    @RequestMapping(value = Router.ROUTE_SEARCH_FUZZYSEARCH, method = RequestMethod.POST)
    public ResultData fuzzySearch(SearchParamsBean searchParams){
        ResultData resultData = Tools.getThreadResultData();
        if (searchParams.getVenderId() == null || "".equals(searchParams.getVenderId())){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("缺少参数");
            return resultData;
        }
        ProductListBean productListBean = searchServiceImpl.fuzzySearch(searchParams);
        resultData.setData(productListBean);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTE_SEARCH_PRODUCT_BY_SKU, method = RequestMethod.POST)
    public ResultData idsQuery(SkuSearchParamsBean skuSearchParams){
        ResultData resultData = Tools.getThreadResultData();
        if (skuSearchParams.getVenderId() == null || "".equals(skuSearchParams.getVenderId()) ||
            skuSearchParams.getSkus() == null || "".equals(skuSearchParams.getSkus())){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("缺少参数");
            return resultData;
        }
        ProductListBean productListBean = searchServiceImpl.idsQuery(skuSearchParams);
        resultData.setData(productListBean);
        return resultData;
    }

}
