package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.model.bean.PriceRate;
import com.cibnvideo.tcmalladmin.omsapi.PricePolicyApi;
import com.cibnvideo.tcmalladmin.omsapi.RatePricePolicyApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RatePricePolicyController extends BaseController {

    @Autowired
    PricePolicyApi pricePolicyApi;

    @Autowired
    RatePricePolicyApi ratePricePolicyApi;

    @RequiresPermissions("mall:pricepolicy:pricepolicies")
    @GetMapping("/v1/tcmalladmin/pricepolicymanager/rate/list")
    @ResponseBody()
    Result<PriceRate> list(@RequestParam Map<String, Object> params) {
        Integer venderId = this.getVenderId().intValue();
        params.put("venderId", venderId);
        Result<PriceRate> result = new Result<PriceRate>();
        result.setTotal(0);
        ResultData<DataList<List<PriceRate>>> resultData = ratePricePolicyApi.list(params);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            DataList<List<PriceRate>> dataList = resultData.getData();
            if (dataList != null) {
                result.setRows(dataList.getData());
                result.setTotal(dataList.getTotalRows());
            }
        }
        return result;
    }

    @RequiresPermissions("mall:pricepolicy:edit")
    @GetMapping("/v1/tcmalladmin/pricepolicymanager/rate/add")
    public String add(Model model, @RequestParam("policyid") Integer policyid) {
        Integer venderId = this.getVenderId().intValue();
        model.addAttribute("policyid", policyid);
        return "tcmalladmin/pricepolicymanager/addrate";
    }

    @RequiresPermissions("mall:pricepolicy:edit")
    @PostMapping("/v1/tcmalladmin/pricepolicymanager/rate/save")
    @ResponseBody
    public R saveCategoryPricePolicy(PriceRate priceRate) {
        BigDecimal startRate = priceRate.getStartRate();
        BigDecimal endRate = priceRate.getEndRate();
        if(startRate == null && endRate == null) {
            return R.error("区间开始结束不能全部为空");
        }
        Integer venderId = this.getVenderId().intValue();
        priceRate.setVenderid(venderId);
        ResultData<Integer> resultData = ratePricePolicyApi.add(priceRate);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            Integer result = resultData.getData();
            if (result != null && result > 0) {
                return R.ok();
            } else {
                return R.error("添加失败");
            }
        } else {
            return R.error(resultData.getMsg());
        }
    }

    @RequiresPermissions("mall:pricepolicy:edit")
    @PostMapping("/v1/tcmalladmin/pricepolicymanager/rate/remove/{policyid}")
    @ResponseBody
    public R removeCollectionPricePolicy(@PathVariable("policyid") Integer policyid, @RequestBody List<Integer> ids) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("venderid", this.getVenderId().intValue());
        params.put("policyid", policyid);
        params.put("ids", ids);
        ResultData<Integer> resultData = ratePricePolicyApi.batchRemove(params);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            Integer r = resultData.getData();
            if (r > 0) {
                return R.ok();
            } else {
                return R.error("删除失败");
            }
        } else {
            return R.error(resultData.getMsg());
        }
    }
}