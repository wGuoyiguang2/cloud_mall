package com.hqtc.search.controller;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import com.hqtc.search.config.Router;
import com.hqtc.search.model.bean.BrandParamsBean;
import com.hqtc.search.model.bean.CateParamsBean;
import com.hqtc.search.service.impl.CateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by makuan on 18-8-16.
 */
@RestController
public class CateController {

    @Autowired
    private CateServiceImpl cateServiceImpl;

    @RequestMapping(value = Router.ROUTE_SEARCH_CATE_LIST, method = RequestMethod.POST)
    public ResultData getCategory(CateParamsBean cateParams){
        ResultData resultData = Tools.getThreadResultData();
        if (cateParams.getVenderId() == null || "".equals(cateParams.getVenderId())){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("缺少参数");
            return resultData;
        }
        List<Integer> cateList = cateServiceImpl.getCategory(cateParams);
        resultData.setData(cateList);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTE_SEARCH_BRANDNAME_BY_CATE, method = RequestMethod.POST)
    public ResultData getBrandList(BrandParamsBean brandParams){
        ResultData resultData = Tools.getThreadResultData();
        if (brandParams.getVenderId() == null || "".equals(brandParams.getVenderId())){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("缺少参数");
            return resultData;
        }
        List<String> brandList = cateServiceImpl.getBrandList(brandParams);
        resultData.setData(brandList);
        return resultData;
    }
}
