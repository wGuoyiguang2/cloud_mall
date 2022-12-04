package com.cibnvideo.thirdpart.controller;

import com.cibnvideo.thirdpart.config.Router;
import com.cibnvideo.thirdpart.model.bean.JDSitePriceBean;
import com.cibnvideo.thirdpart.service.JDSitePriceService;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/22 16:44
 */
@RestController
public class JDSitePriceController {

    @Autowired
    private JDSitePriceService jdSitePriceService;
    @PostMapping(Router.V1_3RD_PART_JD_SITE_PRICE_LIST)
    public ResultData<List<JDSitePriceBean>> listJDSitePrice(@RequestBody List<Long> skuList){
        ResultData<List<JDSitePriceBean>> resultData= Tools.getThreadResultData();
        List<JDSitePriceBean> list=jdSitePriceService.listJDSitePrice(skuList);
        resultData.setData(list);
        return resultData;
    }
}
