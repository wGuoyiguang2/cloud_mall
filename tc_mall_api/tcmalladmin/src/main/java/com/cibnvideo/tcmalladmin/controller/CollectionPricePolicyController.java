package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.model.bean.PriceCollection;
import com.cibnvideo.tcmalladmin.model.bean.ProductCollection;
import com.cibnvideo.tcmalladmin.omsapi.CollectionPricePolicyApi;
import com.cibnvideo.tcmalladmin.omsapi.PricePolicyApi;
import com.cibnvideo.tcmalladmin.omsapi.ProductCollectionApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CollectionPricePolicyController extends BaseController {

    @Autowired
    PricePolicyApi pricePolicyApi;

    @Autowired
    CollectionPricePolicyApi collectionPricePolicyApi;

    @Autowired
    ProductCollectionApi productCollectionApi;

    @RequiresPermissions("mall:pricepolicy:pricepolicies")
    @GetMapping("/v1/tcmalladmin/pricepolicymanager/collection/list")
    @ResponseBody()
    Result<PriceCollection> list(@RequestParam Map<String, Object> params) {
        Integer venderId = this.getVenderId().intValue();
        params.put("venderId", venderId);
        Result<PriceCollection> result = new Result<PriceCollection>();
        result.setTotal(0);
        ResultData<DataList<List<PriceCollection>>> resultData = collectionPricePolicyApi.list(params);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            DataList<List<PriceCollection>> dataList = resultData.getData();
            if (dataList != null) {
                for(PriceCollection p: dataList.getData()){
                    ResultData<ProductCollection> resultDataCollection = productCollectionApi.get(p.getCollectionid());
                    if(resultDataCollection.getError() == ErrorCode.SUCCESS){
                        ProductCollection productCollection = resultDataCollection.getData();
                        if(productCollection != null){
                            p.setCollectionName(productCollection.getName());
                        }
                    }
                }
                result.setRows(dataList.getData());
                result.setTotal(dataList.getTotalRows());
            }
        }
        return result;
    }

    @RequiresPermissions("mall:pricepolicy:edit")
    @GetMapping("/v1/tcmalladmin/pricepolicymanager/collection/add")
    public String add(Model model, @RequestParam("policyid") Integer policyid) {
        Integer venderId = this.getVenderId().intValue();
        model.addAttribute("policyid", policyid);
        HashMap<String, Object> paramsCollection = new HashMap<String, Object>();
        paramsCollection.put("venderId", venderId);
        paramsCollection.put("status", 1);
        ResultData<DataList<List<ProductCollection>>> resultDataCollection = productCollectionApi.list(paramsCollection);
        if(resultDataCollection.getError() == ErrorCode.SUCCESS){
            DataList<List<ProductCollection>> resultList = resultDataCollection.getData();
            List<ProductCollection> productCollectionList = resultList.getData();
            HashMap<String, Object> paramsPolicy = new HashMap<String, Object>();
            paramsPolicy.put("venderid", venderId);
            ResultData<DataList<List<PriceCollection>>> resultListResultData = collectionPricePolicyApi.list(paramsPolicy);
            List<Integer> collections = new ArrayList<Integer>();
            List<ProductCollection> productCollectionListRemove = new ArrayList<ProductCollection>();
            if(resultListResultData.getError() == ErrorCode.SUCCESS){
                if(resultListResultData.getData() != null && resultListResultData.getData().getTotalRows() > 0){
                    List<PriceCollection> list = resultListResultData.getData().getData();
                    for(PriceCollection p:list){
                        collections.add(p.getCollectionid());
                    }
                    for(int i=0;i<productCollectionList.size();i++){
                        if(collections.indexOf(productCollectionList.get(i).getId()) != -1){
                            productCollectionListRemove.add(productCollectionList.get(i));
                        }
                    }
                    productCollectionList.removeAll(productCollectionListRemove);
                }
            }
            model.addAttribute("collectionlist", productCollectionList);
        }
        return "tcmalladmin/pricepolicymanager/addcollection";
    }

    @RequiresPermissions("mall:pricepolicy:edit")
    @PostMapping("/v1/tcmalladmin/pricepolicymanager/collection/save")
    @ResponseBody
    public R saveCategoryPricePolicy(PriceCollection priceCollection) {
        Integer venderId = this.getVenderId().intValue();
        priceCollection.setVenderid(venderId);
        priceCollection.setCtime(new Date());
        priceCollection.setUtime(new Date());
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("venderid", venderId);
        params.put("policyid", priceCollection.getPolicyid());
        params.put("collectionid", priceCollection.getCollectionid());
        ResultData<Integer> resultDataCount = collectionPricePolicyApi.count(params);
        if(resultDataCount.getError() == ErrorCode.SUCCESS){
            if(resultDataCount.getData() != null && resultDataCount.getData() > 0){
                return R.error("添加失败,此商品集已存在价格策略");
            }
        }else {
            return R.error(resultDataCount.getMsg());
        }

        ResultData<Integer> resultData = collectionPricePolicyApi.add(priceCollection);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            Integer result = resultData.getData();
            if (result != null && result > 0) {
                return R.ok();
            } else {
                return R.error("商品集添加失败");
            }
        } else {
            return R.error(resultData.getMsg());
        }
    }

    @RequiresPermissions("mall:pricepolicy:edit")
    @PostMapping("/v1/tcmalladmin/pricepolicymanager/collection/remove/{policyid}")
    @ResponseBody
    public R removeCollectionPricePolicy(@PathVariable("policyid") Integer policyid, @RequestBody List<Integer> ids) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("venderid", this.getVenderId().intValue());
        params.put("policyid", policyid);
        params.put("ids", ids);
        ResultData<Integer> resultData = collectionPricePolicyApi.batchRemove(params);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            Integer r = resultData.getData();
            if (r > 0) {
                return R.ok();
            } else {
                return R.error("商品集删除失败");
            }
        } else {
            return R.error(resultData.getMsg());
        }
    }
}