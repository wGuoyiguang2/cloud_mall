package com.hqtc.cms.model.service;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.fallback.JdSearchFallbackFactory;
import com.hqtc.cms.fallback.JdServiceFallbackFactory;
import com.hqtc.cms.model.bean.SimilarGoodsResponseBean;
import com.hqtc.cms.model.bean.SimilarRequestBean;
import com.hqtc.cms.model.bean.WarrantyRequestBean;
import com.hqtc.cms.model.bean.WarrantyResponseBean;
import com.hqtc.common.config.FeignClientService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Map;

/**
 * description:京东接口
 * Created by laiqingchuang on 18-7-11 .
 */
@FeignClient(name=FeignClientService.JDSERVICE/*,fallbackFactory = JdServiceFallbackFactory.class*/)
public interface JdService {
    /**
     * 查询同类商品
     */
    @RequestMapping(method = RequestMethod.POST, value = Router.GOODS_GETSIMILARGOODS)
    Map<String,SimilarGoodsResponseBean> getSimilarGoods(@RequestBody SimilarRequestBean requestBean);

    /**
     * 查询商品延保
     */
    @RequestMapping(method = RequestMethod.POST, value = Router.GOODS_GETWARRANTYLIST)
    Map<String,WarrantyResponseBean> getWarrantyList(@RequestBody WarrantyRequestBean requestBean);
}
