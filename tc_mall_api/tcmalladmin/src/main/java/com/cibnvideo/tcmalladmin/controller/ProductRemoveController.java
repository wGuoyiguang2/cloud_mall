package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.jdsyncapi.CategoryApi;
import com.cibnvideo.tcmalladmin.jdsyncapi.ProductApi;
import com.cibnvideo.tcmalladmin.model.bean.CategoryResultInfo;
import com.cibnvideo.tcmalladmin.model.bean.ProductInfo;
import com.cibnvideo.tcmalladmin.model.bean.ProductRemove;
import com.cibnvideo.tcmalladmin.omsapi.ProductRemoveApi;
import com.cibnvideo.tcmalladmin.omsapi.VenderSettlementApi;
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
public class ProductRemoveController extends BaseController {

    @Autowired
    ProductRemoveApi productRemoveApi;

    @Autowired
    ProductApi productApi;

    @Autowired
    VenderSettlementApi venderSettlementApi;

    @Autowired
    CategoryApi categoryApi;

    @Log("访问删除商品页面")
    @RequiresPermissions("mall:productremove:list")
    @GetMapping("/v1/tcmalladmin/productremove/products")
    public String products(Model model){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("catClass", 0);
        ResultData<DataList<List<CategoryResultInfo>>> resultData = categoryApi.categoryList(params);
        if(resultData.getError() == ErrorCode.SUCCESS){
            DataList<List<CategoryResultInfo>> resultList = resultData.getData();
            model.addAttribute("cat0list", resultList.getData());
        }
        return "tcmalladmin/pricepolicymanager/productremoves";
    }

    @RequiresPermissions("mall:productremove:list")
    @GetMapping("/v1/tcmalladmin/productremove/list")
    @ResponseBody()
    Result<ProductRemove> list(@RequestParam Map<String, Object> params) {
        Long venderId = this.getVenderId();
        Result<ProductRemove> productRemoveResult = new Result<ProductRemove>();
        productRemoveResult.setTotal(0);
        productRemoveResult.setRows(new ArrayList<ProductRemove>());
        params.put("venderId", venderId.intValue());
        ResultData<DataList<List<ProductRemove>>> resultData = productRemoveApi.list(params);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            DataList<List<ProductRemove>> dataList = resultData.getData();
            if (dataList != null && dataList.getData().size() > 0) {
                List<ProductRemove> productRemoves = dataList.getData();
                List<Long> skus = new ArrayList<Long>();
                for(ProductRemove p:productRemoves){
                    skus.add(p.getSku());
                }
                Map<String, Object> productParams = new HashMap<String, Object>();
                productParams.put("isin", skus);
                for(String key:params.keySet()){
                    if(!"offset".equals(key) && !"limit".equals(key)) {
                        productParams.put(key, params.get(key));
                    }
                }
                ResultData<DataList<List<ProductInfo>>> resultDataProducts = productApi.detailSearchList(productParams);
                if(resultDataProducts.getError() == ErrorCode.SUCCESS){
                    DataList<List<ProductInfo>> dataList1 = resultDataProducts.getData();
                    if(dataList1 != null){
                        List<ProductInfo> productInfoList = dataList1.getData();
                        List<ProductRemove> productRemoveList = new ArrayList<ProductRemove>();
                        for(ProductInfo p:productInfoList){
                            ProductRemove productRemove = productRemoves.get(skus.indexOf(p.getSku()));
                            if(productRemove != null){
                                productRemove.setName(p.getName());
                                productRemoveList.add(productRemove);
                            }
                        }
                        productRemoveResult.setRows(productRemoveList);
                        productRemoveResult.setTotal(dataList.getTotalRows());
                    }
                }
            }
        }
        return productRemoveResult;
    }

    @RequiresPermissions("mall:operation:batchremove")
    @PostMapping("/v1/tcmalladmin/productremove/save")
    @ResponseBody
    public R saveProductRemove(@RequestBody List<Long> skus) {
        Long venderId = this.getVenderId();
        ProductRemove productRemove = new ProductRemove();
        for(Long sku:skus){
            productRemove.setSku(sku);
            productRemove.setVenderId(venderId.intValue());
            productRemove.setCtime(new Date());
            productRemove.setUtime(new Date());
            ResultData<Integer> resultData = productRemoveApi.add(productRemove);
            if(resultData.getError() != ErrorCode.SUCCESS){
                if(resultData.getData() == null || resultData.getData() == 0){
                    return R.error("删除失败");
                }

            }
        }
        return R.ok();
    }

    @Log("删除商品批量还原")
    @RequiresPermissions("mall:productremove:remove")
    @PostMapping("/v1/tcmalladmin/productremove/remove")
    @ResponseBody
    public R removeProductRemove(@RequestBody List<Integer> ids) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("venderId", this.getVenderId().intValue());
        params.put("ids", ids);
        ResultData<Integer> resultData = productRemoveApi.batchRemove(params);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            Integer r = resultData.getData();
            if (r > 0) {
                return R.ok();
            } else {
                return R.error("删除取消失败");
            }
        } else {
            return R.error(resultData.getMsg());
        }
    }
}