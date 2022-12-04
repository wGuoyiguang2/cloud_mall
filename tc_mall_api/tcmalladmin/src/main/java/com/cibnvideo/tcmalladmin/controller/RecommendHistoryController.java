package com.cibnvideo.tcmalladmin.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.bmsapi.OrderApi;
import com.cibnvideo.tcmalladmin.jdsyncapi.CategoryApi;
import com.cibnvideo.tcmalladmin.jdsyncapi.ProductApi;
import com.cibnvideo.tcmalladmin.model.bean.ProductInfo;
import com.cibnvideo.tcmalladmin.model.bean.RecommendBean;
import com.cibnvideo.tcmalladmin.model.bean.RecommendHistoryBean;
import com.cibnvideo.tcmalladmin.model.bean.SellPriceResult;
import com.cibnvideo.tcmalladmin.omsapi.ProductCollectionApi;
import com.cibnvideo.tcmalladmin.omsapi.RecommendApi;
import com.cibnvideo.tcmalladmin.omsapi.RecommendHistoryApi;
import com.cibnvideo.tcmalladmin.omsapi.VenderSettlementApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class RecommendHistoryController extends BaseController {

    @Autowired
    RecommendApi recommendApi;

    @Autowired
    RecommendHistoryApi recommendHistoryApi;

    @Autowired
    ProductApi productApi;

    @Autowired
    CategoryApi categoryApi;

    @Autowired
    ProductCollectionApi productCollectionApi;

    @Autowired
    ProductDetailSearch productDetailSearch;

    @Autowired
    VenderSettlementApi venderSettlementApi;

    @Autowired
    OrderApi orderApi;

    @Log("列出所有往期推荐位")
    @GetMapping("/v1/tcmalladmin/recommendhistory/recommendhistory")
    @RequiresPermissions("mall:recommendhistory:list")
    String list(Model model) {
        ResultData<Integer> resultData = recommendHistoryApi.getPeriod(getVenderId().intValue());
        if(resultData.getError() == ErrorCode.SUCCESS){
            if(resultData.getData() != null){
                model.addAttribute("period", resultData.getData());
            }else {
                model.addAttribute("period", 30);
            }
        }else {
            model.addAttribute("period", 30);
        }
        return "tcmalladmin/recommendhistory/recommendhistory";
    }


    @Log("列出所有往期推荐位")
    @RequiresPermissions("mall:recommendhistory:list")
    @GetMapping("/v1/tcmalladmin/recommendhistory/list")
    @ResponseBody()
    Result<RecommendHistoryBean> list(@RequestParam Map<String, Object> params) {
        Result<RecommendHistoryBean> result = new Result<RecommendHistoryBean>();
        List<String> actionParams = new ArrayList<String>();
        Map<String, Object> skus = new HashMap<String, Object>();
        if(params.containsKey("sku")){
            skus.put("sku", Long.valueOf((String)params.get("sku")));
            actionParams.add(new JSONObject( skus).toString());
        }else if(params.containsKey("name")){
            Map<String, Object> productParams = new HashMap<String, Object>();
            productParams.put("name", params.get("name"));
            ResultData<DataList<List<ProductInfo>>> resultData = productApi.detailSearchList(productParams);
            if(resultData.getError() == ErrorCode.SUCCESS){
                DataList<List<ProductInfo>> dataList = resultData.getData();
                if(dataList != null && dataList.getTotalRows() > 0){
                    List<ProductInfo> productInfoList = dataList.getData();
                    for(ProductInfo p:productInfoList){
                        if(p.getSku() != null){
                            skus.put("sku", p.getSku());
                            actionParams.add(new JSONObject(skus).toString());
                        }
                    }
                }else {
                    return result;
                }
            }else {
                return result;
            }
            params.remove("name");
        }
        if(actionParams.size() > 0){
            params.put("isin", actionParams);
        }
        Long venderId = getVenderId();
        List<RecommendHistoryBean> recommendHistoryBeanList = new ArrayList<RecommendHistoryBean>();
        params.put("venderId", venderId.intValue());
        ResultData<DataList<List<RecommendBean>>> resultData = recommendHistoryApi.list(params);
        if(resultData.getError() == ErrorCode.SUCCESS){
            DataList<List<RecommendBean>> dataList = resultData.getData();
            if(dataList != null){
                List<RecommendBean> recommendBeanList = dataList.getData();
                for(RecommendBean r:recommendBeanList){
                    Long sku = 0L;
                    RecommendHistoryBean recommendHistoryBean = new RecommendHistoryBean();
                    Map<String, Long> paramsRecommend = new HashMap<String, Long>();
                    if(!StringUtils.isEmpty(r.getActionParam())){
                        paramsRecommend = JSON.parseObject(r.getActionParam(), new TypeReference<HashMap<String,Long>>() {});
                    }else {
                        continue;
                    }
                    if(paramsRecommend.containsKey("sku")){
                        sku = paramsRecommend.get("sku");
                    }else {
                        continue;
                    }
                    ResultData<ProductInfo> resultDataProductInfo = productApi.detailInfo(sku);
                    if(resultDataProductInfo.getError() == ErrorCode.SUCCESS){
                        if(resultDataProductInfo.getData() != null){
                            ProductInfo productInfo = resultDataProductInfo.getData();
                            recommendHistoryBean.setState(productInfo.getState());
                            recommendHistoryBean.setName(productInfo.getName());
                            recommendHistoryBean.setBrandName(productInfo.getBrandName());
                            recommendHistoryBean.setCategory(productDetailSearch.getCategoryNameByIds(productInfo.getCategory()));
                            ResultData<SellPriceResult> sellPriceResultResultData = venderSettlementApi.getPrice(venderId.intValue(), productInfo.getSku());
                            if(sellPriceResultResultData.getError() == ErrorCode.SUCCESS){
                                if(sellPriceResultResultData.getData() != null){
                                    SellPriceResult sellPriceResult = sellPriceResultResultData.getData();
                                    recommendHistoryBean.setPrice(sellPriceResult.getPrice());
                                }
                            }
                        }
                    }else {
                        continue;
                    }
                    ResultData<List<Map<String, Long>>> resultDataVolume = orderApi.getProductSalesVolume(sku.toString());
                    if(resultDataVolume.getError() == ErrorCode.SUCCESS){
                        if(resultDataVolume.getData() != null && resultDataVolume.getData().size() > 0){
                            List<Map<String, Long>> volumes = resultDataVolume.getData();
                            for(Map<String, Long> m:volumes){
                                if(m.containsKey("productId")){
                                    recommendHistoryBean.setSales(m.get("total").intValue());
                                    break;
                                }
                            }
                        }
                    }
                    recommendHistoryBean.setId(r.getId());
                    recommendHistoryBean.setSku(sku);
                    recommendHistoryBean.setStatus(r.getStatus());
                    recommendHistoryBean.setCtime(r.getCtime());
                    recommendHistoryBean.setUtime(r.getUtime());
                    recommendHistoryBeanList.add(recommendHistoryBean);
                }
            }
            result.setRows(recommendHistoryBeanList);
            result.setTotal(dataList.getTotalRows());
        }else {
            result.setTotal(0);
            result.setRows(recommendHistoryBeanList);
        }
        return result;
    }

    @RequiresPermissions("mall:recommendhistory:edit")
    @PostMapping("/v1/tcmalladmin/recommendhistory/edit")
    @ResponseBody()
    R recommendHistoryEdit(@RequestBody RecommendHistoryBean recommendHistoryBean) {
        ResultData<Integer> resultData = recommendHistoryApi.updateStatus(recommendHistoryBean.getId(), recommendHistoryBean.getStatus());
        if(resultData.getError() == ErrorCode.SUCCESS){
            if(resultData.getData() != null && resultData.getData() > 0){
                return R.ok();
            }else {
                return R.error("状态更新失败");
            }
        }else {
            return R.error(resultData.getMsg());
        }
    }

    @RequiresPermissions("mall:recommendhistory:edit")
    @GetMapping("/v1/tcmalladmin/recommendhistory/period/update")
    @ResponseBody()
    R periodEdit(@RequestParam("period") Integer period) {
        ResultData<Integer> resultData = recommendHistoryApi.updatePeriod(getVenderId().intValue(), period);
        if(resultData.getError() == ErrorCode.SUCCESS){
            if(resultData.getData() != null && resultData.getData()>0){
                return R.ok();
            }else {
                return R.error("更新失败");
            }
        }else {
            return R.error(resultData.getMsg());
        }
    }
}
