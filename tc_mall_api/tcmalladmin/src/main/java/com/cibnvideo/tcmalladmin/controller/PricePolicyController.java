package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.jdsyncapi.CategoryApi;
import com.cibnvideo.tcmalladmin.model.bean.CategoryResultInfo;
import com.cibnvideo.tcmalladmin.model.bean.PricePolicy;
import com.cibnvideo.tcmalladmin.model.bean.ProductCollection;
import com.cibnvideo.tcmalladmin.omsapi.*;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PricePolicyController extends BaseController {

    @Autowired
    PricePolicyApi pricePolicyApi;

    @Autowired
    CollectionPricePolicyApi collectionPricePolicyApi;

    @Autowired
    CategoryPricePolicyApi categoryPricePolicyApi;

    @Autowired
    RatePricePolicyApi ratePricePolicyApi;

    @Autowired
    CategoryApi categoryApi;
    @Autowired
    ProductCollectionApi productCollectionApi;

    @RequiresPermissions("mall:pricepolicy:pricepolicies")
    @GetMapping("/v1/tcmalladmin/pricepolicymanager/pricepolicies")
    public String productCollectionManager(Model model) {
        return "tcmalladmin/pricepolicymanager/pricepolicies";
    }

    @RequiresPermissions("mall:pricepolicy:pricepolicies")
    @GetMapping("/v1/tcmalladmin/pricepolicymanager/pricepolicylist")
    @ResponseBody()
    Result<PricePolicy> list(@RequestParam Map<String, Object> params) {
        Integer venderId = this.getVenderId().intValue();
        params.put("venderId", venderId);
        Result<PricePolicy> result = new Result<PricePolicy>();
        result.setTotal(0);
        ResultData<DataList<List<PricePolicy>>> resultData = pricePolicyApi.list(params);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            DataList<List<PricePolicy>> dataList = resultData.getData();
            if (dataList != null) {
                result.setRows(dataList.getData());
                result.setTotal(dataList.getTotalRows());
            }
        }
        return result;
    }

    @Log("访问添加价格策略页面")
    @RequiresPermissions("mall:pricepolicy:add")
    @GetMapping("/v1/tcmalladmin/pricepolicymanager/add")
    public String add(Model model) {
        return "tcmalladmin/pricepolicymanager/add";
    }

    @Log("添加价格策略")
    @RequiresPermissions("mall:pricepolicy:add")
    @PostMapping("/v1/tcmalladmin/pricepolicymanager/save")
    @ResponseBody
    public R savePricePolicy(PricePolicy pricePolicy) {
        Integer venderId = this.getVenderId().intValue();
        if (pricePolicy.getType() == 0) {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("venderId", venderId);
            params.put("type", 0);
            ResultData<Integer> resultData = pricePolicyApi.count(params);
            if (resultData.getError() == ErrorCode.SUCCESS) {
                Integer count = resultData.getData();
                if (count == null) {
                    return R.error("全局零售价策略添加失败");
                }else if (count > 0) {
                    return R.error("全局零售价策略已存在");
                }
            } else {
                return R.error(resultData.getMsg());
            }
        }
        pricePolicy.setVenderId(venderId);
        pricePolicy.setCtime(new Date());
        pricePolicy.setUtime(new Date());
        ResultData<Integer> resultData = pricePolicyApi.add(pricePolicy);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            Integer result = resultData.getData();
            if (result != null && result > 0) {
                return R.ok();
            } else {
                return R.error("策略添加失败");
            }
        } else {
            return R.error(resultData.getMsg());
        }
    }

    @Log("价格策略编辑")
    @RequiresPermissions("mall:pricepolicy:edit")
    @PostMapping("/v1/tcmalladmin/pricepolicymanager/edit")
    @ResponseBody
    public R editPricePolicy(@RequestBody PricePolicy pricePolicy) {
        Integer venderId = this.getVenderId().intValue();
        pricePolicy.setUtime(new Date());
        pricePolicy.setVenderId(venderId);
        ResultData<Integer> resultData = pricePolicyApi.update(pricePolicy);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            Integer result = resultData.getData();
            if (result != null && result > 0) {
                return R.ok();
            } else {
                return R.error("策略修改失败");
            }
        } else {
            return R.error(resultData.getMsg());
        }
    }

    @Log("访问价格策略修改页面")
    @RequiresPermissions("mall:pricepolicy:pricepolicies")
    @GetMapping("/v1/tcmalladmin/pricepolicymanager/policyedit")
    public String editPolicy(Model model, Integer id) {
        Integer venderId = this.getVenderId().intValue();
        model.addAttribute("policyid", id);
        ResultData<PricePolicy> resultData = pricePolicyApi.get(id);
        if(resultData.getError() == ErrorCode.SUCCESS){
            PricePolicy pricePolicy = resultData.getData();
            if(pricePolicy != null){
                Integer type = pricePolicy.getType();
                if(type == 1){//分类
                    HashMap<String, Object> paramsCat = new HashMap<String, Object>();
                    paramsCat.put("catClass", 0);
                    ResultData<DataList<List<CategoryResultInfo>>> resultDataCat0 = categoryApi.categoryList(paramsCat);
                    if(resultDataCat0.getError() == ErrorCode.SUCCESS){
                        DataList<List<CategoryResultInfo>> resultList = resultDataCat0.getData();
                        model.addAttribute("cat0list", resultList.getData());
                    }
                    return "tcmalladmin/pricepolicymanager/editcategorypolicy";
                }else if(type == 2){//商品集
                    HashMap<String, Object> paramsCollection = new HashMap<String, Object>();
                    paramsCollection.put("venderId", venderId);
                    paramsCollection.put("status", 1);
                    ResultData<DataList<List<ProductCollection>>> resultDataCollection = productCollectionApi.list(paramsCollection);
                    if(resultDataCollection.getError() == ErrorCode.SUCCESS){
                        DataList<List<ProductCollection>> resultList = resultDataCollection.getData();
                        model.addAttribute("collectionlist", resultList.getData());
                    }
                    return "tcmalladmin/pricepolicymanager/editcollectionpolicy";
                }else if(type == 3){//毛利率分段定价
                    return "tcmalladmin/pricepolicymanager/editratepolicy";
                }else {
                    return "error/500";
                }
            }else {
                return "error/500";
            }
        }else {
            return "error/500";
        }
    }

    @Log("价格策略删除")
    @RequiresPermissions("mall:pricepolicy:remove")
    @PostMapping("/v1/tcmalladmin/pricepolicymanager/remove")
    @ResponseBody
    public R removePricePolicy(Integer id) {
        ResultData<PricePolicy> pricePolicyResultData = pricePolicyApi.get(id);
        if(pricePolicyResultData.getError() == ErrorCode.SUCCESS) {
            PricePolicy pricePolicy = pricePolicyResultData.getData();
            if(pricePolicy != null) {
                Integer type = pricePolicy.getType();
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("venderId", this.getVenderId().intValue());
                params.put("policyid", id);
                if(type == 1) {
                    ResultData<Integer> resultDataCount = categoryPricePolicyApi.count(params);
                    if(resultDataCount.getError() == ErrorCode.SUCCESS){
                        if(resultDataCount.getData() != null && resultDataCount.getData() > 0){
                            return R.error("删除失败,此零售价策略下存在关联分类");
                        }
                    }else {
                        return R.error(resultDataCount.getMsg());
                    }
                } else if(type == 2) {
                    ResultData<Integer> resultDataCount = collectionPricePolicyApi.count(params);
                    if(resultDataCount.getError() == ErrorCode.SUCCESS){
                        if(resultDataCount.getData() != null && resultDataCount.getData() > 0){
                            return R.error("删除失败,此零售价策略下存在关联商品集");
                        }
                    }else {
                        return R.error(resultDataCount.getMsg());
                    }
                } else if(type == 3) {
                    ResultData<Integer> resultDataCount = ratePricePolicyApi.count(params);
                    if(resultDataCount.getError() == ErrorCode.SUCCESS){
                        if(resultDataCount.getData() != null && resultDataCount.getData() > 0){
                            return R.error("删除失败,此零售价策略下存在关联毛利率区间");
                        }
                    }else {
                        return R.error(resultDataCount.getMsg());
                    }
                }
            }else {
                return R.error("此价格策略不存在");
            }
        } else {
            return R.error(pricePolicyResultData.getMsg());
        }



        ResultData<Integer> resultData = pricePolicyApi.remove(id);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            Integer r = resultData.getData();
            if (r > 0) {
                return R.ok();
            } else {
                return R.error("零售价策略删除失败");
            }
        } else {
            return R.error(resultData.getMsg());
        }
    }
}