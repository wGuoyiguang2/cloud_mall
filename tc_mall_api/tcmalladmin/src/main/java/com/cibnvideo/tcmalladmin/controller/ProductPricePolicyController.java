package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.entity.VenderSettlement;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.jdsyncapi.CategoryApi;
import com.cibnvideo.tcmalladmin.jdsyncapi.ProductApi;
import com.cibnvideo.tcmalladmin.model.bean.*;
import com.cibnvideo.tcmalladmin.omsapi.*;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class ProductPricePolicyController extends BaseController {

    @Autowired
    ProductPricePolicyApi productPricePolicyApi;

    @Autowired
    ProductApi productApi;

    @Autowired
    VenderSettlementApi venderSettlementApi;

    @Autowired
    CategoryApi categoryApi;

    @Autowired
    ProductDetailSearch productDetailSearch;

    @RequiresPermissions("mall:pricepolicy:product")
    @GetMapping("/v1/tcmalladmin/pricepolicymanager/product")
    public String product(Model model){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("catClass", 0);
        ResultData<DataList<List<CategoryResultInfo>>> resultData = categoryApi.categoryList(params);
        if(resultData.getError() == ErrorCode.SUCCESS){
            DataList<List<CategoryResultInfo>> resultList = resultData.getData();
            model.addAttribute("cat0list", resultList.getData());
        }
        return "tcmalladmin/pricepolicymanager/productpricepolicies";
    }

    @RequiresPermissions("mall:pricepolicy:product")
    @GetMapping("/v1/tcmalladmin/pricepolicymanager/product/list")
    @ResponseBody()
    Result<ProductInfo> list(@RequestParam Map<String, Object> params) {
        Result<ProductInfo> productInfos= new Result<ProductInfo>();
        productInfos.setTotal(0);
        productInfos.setRows(new ArrayList<ProductInfo>());
        Long venderId = this.getVenderId();
        ResultData<VenderSettlement> venderSettlementResultData = venderSettlementApi.getVenderSettlement(venderId);
        if(venderSettlementResultData.getError() != ErrorCode.SUCCESS || venderSettlementResultData.getData() == null){
            return productInfos;
        }
        VenderSettlement venderSettlement = venderSettlementResultData.getData();
        params.put("venderId", venderId.intValue());
        Result<PriceCollection> result = new Result<PriceCollection>();
        result.setTotal(0);
        ResultData<DataList<List<PriceProduct>>> resultData = productPricePolicyApi.list(params);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            DataList<List<PriceProduct>> dataList = resultData.getData();
            if (dataList != null && dataList.getData().size() > 0) {
                List<PriceProduct> priceProducts = dataList.getData();
                List<Long> skus = new ArrayList<Long>();
                for(PriceProduct p:priceProducts){
                    skus.add(p.getSku());
                }
                Map<String, Object> productParams = new HashMap<String, Object>();
                productParams.put("isin", skus);
                for(String key:params.keySet()){
                    if(!"limit".equals(key) && !"offset".equals(key)){
                        productParams.put(key, params.get(key));
                    }
                }
                ResultData<DataList<List<ProductInfo>>> resultDataProducts = productApi.detailSearchList(productParams);
                if(resultDataProducts.getError() == ErrorCode.SUCCESS){
                    DataList<List<ProductInfo>> dataList1 = resultDataProducts.getData();
                    if(dataList1 != null){
                        List<ProductInfo> productInfoList = dataList1.getData();
                        for(ProductInfo p:productInfoList){
                            PriceProduct priceProduct = priceProducts.get(skus.indexOf(p.getSku()));
                            if(priceProduct != null){
                                BigDecimal price = priceProduct.getPrice();
                                p.setPrice(price.setScale(1, BigDecimal.ROUND_HALF_UP));
                            }
                            String categorys = productDetailSearch.getCategoryNameByIds(p.getCategory());
                            if(!StringUtils.isEmpty(categorys)){
                                p.setCategory(categorys);
                            }
                        }
                        productInfos.setTotal(dataList.getTotalRows());
                        productInfos.setRows(productInfoList);
                    }
                }
            }
        }
        return productInfos;
    }

    @RequiresPermissions("mall:operation:editprice")
    @PostMapping("/v1/tcmalladmin/pricepolicymanager/product/save")
    @ResponseBody
    public R saveProductPricePolicy(@RequestBody ProductInfo productInfo) {
        Long venderId = this.getVenderId();
        PriceProduct priceProduct = new PriceProduct();
        priceProduct.setSku(productInfo.getSku());
        priceProduct.setVenderId(venderId.intValue());
        priceProduct.setCtime(new Date());
        priceProduct.setUtime(new Date());
        ResultData<SellPriceResult> sellPriceResultResultData= productApi.getPrice(productInfo.getSku());
        if(sellPriceResultResultData.getError() != ErrorCode.SUCCESS || sellPriceResultResultData.getData() == null){
            return R.error("商品原价查询失败");
        }
        SellPriceResult sellPriceResult = sellPriceResultResultData.getData();

        ResultData<VenderSettlement> venderSettlementResultData = venderSettlementApi.getVenderSettlement(venderId);
        if(venderSettlementResultData.getError() != ErrorCode.SUCCESS || venderSettlementResultData.getData() == null){
            return R.error("原始商品价格系数获取失败");
        }
        if(BigDecimal.ZERO.compareTo(productInfo.getPrice()) == 1){
            return R.error("价格不能低于0");
        }
//        VenderSettlement venderSettlement = venderSettlementResultData.getData();
//        BigDecimal oldPrice = sellPriceResult.getPrice().multiply(venderSettlement.getPricePercent());
//        if(oldPrice.compareTo(productInfo.getPrice()) == 1){
//            return R.error("价格不能低于底价");
//        }
        priceProduct.setPrice(productInfo.getPrice());
        HashMap<String, Object> paramPrice = new HashMap<String, Object>();
        paramPrice.put("venderId", venderId);
        paramPrice.put("sku", priceProduct.getSku());
        ResultData<DataList<List<PriceProduct>>> resultDataPriceProduct = productPricePolicyApi.list(paramPrice);
        if(resultDataPriceProduct.getError() != ErrorCode.SUCCESS){
            return R.error("单品价格策略查询失败");
        }
        if(resultDataPriceProduct.getData() != null && resultDataPriceProduct.getData().getTotalRows() > 0){
            DataList<List<PriceProduct>> dataList = resultDataPriceProduct.getData();
            List<PriceProduct> priceProducts = dataList.getData();
            for(PriceProduct p:priceProducts){
                p.setPrice(priceProduct.getPrice());
                p.setUtime(new Date());
                productPricePolicyApi.update(p);
            }
            return R.ok();
        }else {
            ResultData<Integer> resultData = productPricePolicyApi.add(priceProduct);
            if (resultData.getError() == ErrorCode.SUCCESS) {
                Integer result = resultData.getData();
                if (result != null && result > 0) {
                    return R.ok();
                } else {
                    return R.error("商品价格修改失败");
                }
            } else {
                return R.error(resultData.getMsg());
            }
        }
    }

    @RequiresPermissions("mall:productprice:remove")
    @PostMapping("/v1/tcmalladmin/pricepolicymanager/product/remove")
    @ResponseBody
    public R removeProductPricePolicy(@RequestBody List<Long> skus) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("venderId", this.getVenderId().intValue());
        params.put("skus", skus);
        ResultData<Integer> resultData = productPricePolicyApi.batchRemove(params);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            Integer r = resultData.getData();
            if (r > 0) {
                return R.ok();
            } else {
                return R.error("单品定价删除失败");
            }
        } else {
            return R.error(resultData.getMsg());
        }
    }
}