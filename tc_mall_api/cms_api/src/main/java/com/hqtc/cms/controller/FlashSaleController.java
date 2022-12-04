package com.hqtc.cms.controller;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.model.bean.flashsale.OrderParams;
import com.hqtc.cms.model.service.FlashSaleService;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.CookieOperate;
import com.hqtc.common.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 秒杀controller
 * @Author: WangBin
 * @Date: 2018/8/15 18:37
 */
@RestController
public class FlashSaleController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FlashSaleService flashSaleService;

    /**
     * 活动开始时,调用此接口加载秒杀配置数据
     * @return
     */
    @PostMapping(Router.V1_FLASHSALE_START)
    public ResultData flashSaleStart(@RequestParam("venderId") String venderId,@RequestParam("activityId") String activityId){
        ResultData resultData=Tools.getThreadResultData();
        resultData=flashSaleService.flashSaleStart(venderId,activityId);
        return resultData;
    }

    /**
     * 关闭秒杀接口
     * @param venderId
     * @return
     */
    @PostMapping(Router.V1_FLASHSALE_END)
    public ResultData flashSaleEnd(@RequestParam("venderId") String venderId,@RequestParam("activityId") String activityId){
        ResultData resultData=Tools.getThreadResultData();
        resultData = flashSaleService.flashSaleEnd(venderId,activityId);
        return resultData;
    }
    /**
     * 轮询查看下单状态
     * @param requestId
     * @return
     */
    @PostMapping(Router.V1_FLASHSALE_ORDER_STATE_GET)
    public ResultData getOrderState(@RequestParam("venderId") String venderId,@RequestParam("activityId") String activityId,@RequestParam("requestId") String requestId){
        ResultData resultData=Tools.getThreadResultData();
        resultData=flashSaleService.getOrderState(venderId,activityId,requestId);
        return resultData;
    }
    /**
     * 秒杀
     * @param vo
     * @param request
     * @param userId
     * @return
     */
    @PostMapping(Router.V1_FLASHSALE)
    public ResultData flashSale(@RequestBody @Valid OrderParams vo, HttpServletRequest request,@RequestAttribute("userId") Integer userId) {
        ResultData resultData=Tools.getThreadResultData();
        //封装参数
        String cookie=CookieOperate.getCookie(request);
        vo.setCookie(cookie);
        vo.setUserId(userId);
        Map<String, Integer> map = new HashMap<>();
        map.put(vo.getSku(), vo.getCount());
        vo.setProducts(map);
        resultData=flashSaleService.flashSale(vo);
        return resultData;
    }
}
