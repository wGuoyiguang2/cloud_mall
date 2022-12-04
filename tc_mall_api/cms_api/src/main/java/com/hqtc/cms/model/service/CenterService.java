package com.hqtc.cms.model.service;

import com.hqtc.cms.fallback.CenterFallbackFactory;
import com.hqtc.cms.config.Router;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by makuan on 18-6-26.
 */
@FeignClient(name= FeignClientService.OMSAPI, fallbackFactory = CenterFallbackFactory.class)
public interface CenterService {

    @RequestMapping(value = Router.ROUTE_GET_OMS_CONTACT, method = RequestMethod.GET)
    ResultData getContact(@RequestParam("venderId") int venderId);

    @RequestMapping(value = Router.ROUTE_GET_OMS_ASSISTANCE, method = RequestMethod.GET)
    ResultData getAssistance(@RequestParam("venderId") int venderId);

    @RequestMapping(value = Router.ROUTE_GET_OMS_ASSISTANCE_DETAIL, method = RequestMethod.GET)
    ResultData getAssistDetail(@RequestParam("id") int id);
}
