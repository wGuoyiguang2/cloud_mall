package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.omsapi.ContactUsFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.ContactUsBean;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/10 20:38
 */
@FeignClient(value = FeignClientService.OMSAPI,fallbackFactory = ContactUsFallbackFactory.class)
public interface ContactUsApi {
    @GetMapping(value = Router.V1_OMS_CONTACTUS_GET_BY_VENDERID)
    ResultData<List<ContactUsBean>> getContactWithUsByVenderId(@RequestParam("venderId") Long venderId);

    @GetMapping(value = Router.V1_OMS_CONTACTUS_GET_BY_ID)
    ResultData<ContactUsBean> getContactUsById(@RequestParam("id") Integer id);
    @PostMapping(value=Router.V1_OMS_CONTACTUS_UPDATE)
    ResultData<Integer> updateContactUs(@RequestBody ContactUsBean contactUsBean);
    @PostMapping(Router.V1_OMS_CONTACTUS_ADD)
    ResultData addContactUs(@RequestBody ContactUsBean contactUsBean);
}
