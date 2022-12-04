package com.cibnvideo.oms.tcmallcustomer.controller;

import com.cibnvideo.oms.bean.Result;
import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.FreeFreightBean;
import com.cibnvideo.oms.tcmallcustomer.service.FreeFreightService;
import com.cibnvideo.oms.tcmallcustomer.service.PricePolicyService;
import com.cibnvideo.oms.tcmallcustomer.service.VenderSettlementService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/8 14:55
 */
@RestController
public class FreeFreightController {

    @Value("${spring.config.jdFreeFreightPrice}")
    private Double jdFreeFreightPrice;
    @Value("${spring.config.defaultPricePercent}")
    private Double defaultPricePercent;
    @Autowired
    private FreeFreightService freeFreightService;
    @Autowired
    private VenderSettlementService venderSettlementService;
    @Autowired
    private PricePolicyService pricePolicyService;
    @PostMapping(Router.V1_OMS_FREEFREIGHT_MANAGER_LIST)
    public ResultData<Result<FreeFreightBean>> listManagerFreeFreight(@RequestParam Map<String, Object> params){
        ResultData resultData= Tools.getThreadResultData();
        Result<FreeFreightBean> result=new Result<>();
        List<FreeFreightBean> list=freeFreightService.listManagerFreeFreight(params);
        int total=freeFreightService.countManagerFreeFreight(params);
        result.setTotal(total);
        result.setRows(list);
        resultData.setData(result);
        return resultData;
    }
    @PostMapping(Router.V1_OMS_FREEFREIGHT_MANAGER_ADD)
    public ResultData addFreeFreight(@RequestParam Map<String, Object> params){
        ResultData resultData= Tools.getThreadResultData();
        int count=0;
        count = freeFreightService.addFreeFreight(params);
        if(count>0){
            resultData.setMsg("添加成功！");
        }else{
            resultData.setMsg("添加失败！");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        return resultData;
    }
    @PostMapping(Router.V1_OMS_FREEFREIGHT_MANAGER_GETBYID)
    public ResultData getById(@RequestParam("id") Integer id){
        ResultData resultData=Tools.getThreadResultData();
        FreeFreightBean freeFreightManagerVo=freeFreightService.getById(id);
        resultData.setData(freeFreightManagerVo);
        return resultData;
    }
    @PostMapping(Router.V1_OMS_FREEFREIGHT_MANAGER_UPDATE)
    public ResultData updateManagerFreeFreight(@RequestParam Map<String,Object> params){
        ResultData resultData=Tools.getThreadResultData();
        int count=freeFreightService.updateManagerFreeFreight(params);
        if(count>0){
            resultData.setMsg("更新成功！");
        }else{
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
            resultData.setMsg("更新失败！");
        }
        return  resultData;
    }

    /**
     * 获取包邮价格
     * @param venderId
     * @return
     */
    @PostMapping(Router.V1_OMS_FREEFREIGHT_GETBY_VENDERID)
    public ResultData getByVenderId(@RequestParam("venderId") Integer venderId){
        ResultData resultData=Tools.getThreadResultData();
        Double price=freeFreightService.getByVenderId(venderId);
        if(price==null){
            price=this.getSuggestPrice(venderId).getData().doubleValue();
        }
        resultData.setData(price);
        return resultData;
    }
    @PostMapping(Router.V1_OMS_FREEFREIGHT_SUGGEST_PRICE)
    public ResultData<BigDecimal> getSuggestPrice(@RequestParam("venderId") Integer venderId){
        ResultData<BigDecimal> resultData = Tools.getThreadResultData();
        BigDecimal percent = pricePolicyService.getMaxPricePolicy(venderId);
        BigDecimal tradePercent = venderSettlementService.pricePercentGet(venderId);
        if(tradePercent == null) {
            percent=new BigDecimal(defaultPricePercent);
        }else {
            if (percent == null) {
                percent = tradePercent;
            } else {
                percent = percent.multiply(tradePercent);
            }
        }
        resultData.setData(percent==null? BigDecimal.ONE:percent);
        double suggestPrice=Math.ceil(jdFreeFreightPrice*percent.doubleValue());
        resultData.setData(new BigDecimal(suggestPrice));
        return resultData;
    }
}
