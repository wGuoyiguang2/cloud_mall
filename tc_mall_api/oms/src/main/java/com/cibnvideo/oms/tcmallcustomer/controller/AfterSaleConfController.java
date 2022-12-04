package com.cibnvideo.oms.tcmallcustomer.controller;

import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.AfterSaleConfBean;
import com.cibnvideo.oms.tcmallcustomer.service.AfterSaleConfService;
import com.hqtc.common.response.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * Created by makuan on 18-7-21.
 */
@RestController
public class AfterSaleConfController {

    @Autowired
    AfterSaleConfService afterSaleConfService;

    @Value("${spring.config.afterSaleType}")
    private int afterSaletype;

    @RequestMapping(value = Router.V1_OMS_AFTERSALE_CONFIG, method = RequestMethod.GET)
    ResultData getAfterSaleConfig(@RequestParam("venderId") Integer venderId){
        ResultData resultData = getThreadResultData();
        List<AfterSaleConfBean> afterSaleConfBeanList = afterSaleConfService.getAfterSaleConf(venderId, afterSaletype);
        resultData.setData(afterSaleConfBeanList);
        return resultData;
    }
}
