package com.hqtc.cms.controller;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.model.service.CenterService;
import com.hqtc.common.response.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by makuan on 18-6-26.
 */
@RestController
public class CenterController {
    @Autowired
    CenterService centerService;

    @RequestMapping(value = Router.ROUTE_GET_CONTACT, method = RequestMethod.GET)
    @Cacheable(value = "cms", key = "'contact:venderId:' + #venderId",
                cacheManager = "CmsCacheManager")
    public ResultData getContact(@RequestParam int venderId){
        ResultData resultData = centerService.getContact(venderId);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTE_GET_ASSISTANCE, method = RequestMethod.GET)
    @Cacheable(value = "cms", key = "'assit:venderId:' + #venderId",
                cacheManager = "CmsCacheManager")
    public ResultData getAssistance(@RequestParam int venderId){
        ResultData resultData = centerService.getAssistance(venderId);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTE_GET_ASSISTANCE_DETAIL, method = RequestMethod.GET)
    @Cacheable(value = "cms", key = "'assit_detail:assistId:' +#assistId",
                cacheManager = "CmsCacheManager")
    public ResultData getAssistDetail(@RequestParam int assistId){
        ResultData resultData = centerService.getAssistDetail(assistId);
        return resultData;
    }

}
