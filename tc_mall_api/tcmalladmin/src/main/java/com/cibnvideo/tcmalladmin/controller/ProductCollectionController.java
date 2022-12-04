package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.jdsyncapi.CategoryApi;
import com.cibnvideo.tcmalladmin.jdsyncapi.ProductApi;
import com.cibnvideo.tcmalladmin.model.bean.*;
import com.cibnvideo.tcmalladmin.omsapi.*;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class ProductCollectionController extends BaseController {

    @Autowired
    ProductCollectionApi productCollectionApi;

    @Autowired
    ProductOfCollectionApi productOfCollectionApi;

    @Autowired
    VenderSettlementApi venderSettlementApi;

    @Autowired
    CategoryApi categoryApi;

    @Autowired
    ProductApi productApi;

    @Autowired
    ProductDetailSearch productDetailSearch;

    @Autowired
    PricePolicyApi pricePolicyApi;

    @Autowired
    CollectionPricePolicyApi collectionPricePolicyApi;

    @RequiresPermissions("mall:productcollection:productcollections")
    @GetMapping("/v1/tcmalladmin/productcollectionmanager/productcollections")
    public String productCollectionManager(Model model){
        return "tcmalladmin/productcollectionmanager/productcollections";
    }

    @RequiresPermissions("mall:productcollection:productcollections")
    @GetMapping("/v1/tcmalladmin/productcollectionmanager/productcollectionlist")
    @ResponseBody()
    Result<ProductCollection> list(@RequestParam Map<String, Object> params) {
        BigDecimal percent = null;
        //////全站定价系数
        HashMap<String, Object> paramsPolicy = new HashMap<String, Object>();
        paramsPolicy.put("venderId", this.getVenderId().intValue());
        paramsPolicy.put("type", 0);//全站定价
        ResultData<DataList<List<PricePolicy>>> resultDataPricePolicy = pricePolicyApi.list(paramsPolicy);
        if(resultDataPricePolicy.getError() == ErrorCode.SUCCESS){
            DataList<List<PricePolicy>> pricePolicyList = resultDataPricePolicy.getData();
            if(pricePolicyList!= null && pricePolicyList.getTotalRows() > 0){
                List<PricePolicy> pricePolicies = pricePolicyList.getData();
                percent = pricePolicies.get(0).getPercent();
            }
        }
        Result<ProductCollection> resultCollection = new Result<ProductCollection>();
        resultCollection.setTotal(0);
        params.put("venderId", this.getVenderId().intValue());
        ResultData<DataList<List<ProductCollection>>> resultData = productCollectionApi.list(params);
        if(resultData.getError() == ErrorCode.SUCCESS){
            DataList<List<ProductCollection>> dataList = resultData.getData();
            List<ProductCollection> productCollectionList = dataList.getData();
            int totalRows = dataList.getTotalRows();
            if(productCollectionList != null){
                HashMap<String, Object> collectionParams = new HashMap<String, Object>(1);
                for(ProductCollection p:productCollectionList){
                    p.setPercent(percent);
                    ResultData<List<PricePolicy>> pricePolicyListResult = pricePolicyApi.getPricePolicesByCollectionId(getVenderId().intValue(), p.getId());
                    if(pricePolicyListResult.getError() == ErrorCode.SUCCESS){
                        if(pricePolicyListResult.getData() != null && pricePolicyListResult.getData().size() > 0){
                            p.setCollectionPercent(pricePolicyListResult.getData().get(0).getPercent());//默认一个商品集只能存在一个策略中
                        }
                    }
                    collectionParams.put("collectionId", p.getId());
                    ResultData<Integer> resultDataCount = productOfCollectionApi.count(collectionParams);
                    if(resultDataCount.getError() == ErrorCode.SUCCESS){
                        if(resultDataCount.getData() != null){
                            p.setProductCount(resultDataCount.getData());
                        }else {
                            p.setProductCount(0);
                        }
                    }else {
                        p.setProductCount(0);
                    }
                }
                resultCollection.setRows(productCollectionList);
                resultCollection.setTotal(totalRows);
            }
            return resultCollection;
        }else {
            return resultCollection;
        }
    }

    @RequiresPermissions("mall:productcollection:add")
    @GetMapping("/v1/tcmalladmin/productcollectionmanager/add")
    public String add(Model model){
        return "tcmalladmin/productcollectionmanager/add";
    }

    @RequiresPermissions("mall:productcollection:add")
    @PostMapping("/v1/tcmalladmin/productcollectionmanager/save")
    @ResponseBody
    public R saveCollection(ProductCollection productCollection){
        Integer venderId = this.getVenderId().intValue();
        productCollection.setVenderId(venderId);
        productCollection.setCtime(new Date());
        productCollection.setUtime(new Date());
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("venderId", venderId);
        params.put("name", productCollection.getName());
        ResultData<DataList<List<ProductCollection>>> resultDataCollection = productCollectionApi.list(params);
        if(resultDataCollection.getError() == ErrorCode.SUCCESS){
            DataList<List<ProductCollection>> dataList = resultDataCollection.getData();
            if(dataList !=null && dataList.getTotalRows() > 0){
                return R.error("该商品集名称已存在");
            }
        }else {
            return R.error(resultDataCollection.getMsg());
        }
        ResultData<Integer> resultData = productCollectionApi.add(productCollection);
        if(resultData.getError() == ErrorCode.SUCCESS){
            Integer r = resultData.getData();
            if(r > 0){
                return R.ok();
            }else {
                return R.error("商品集添加失败");
            }
        }else {
            return R.error(resultData.getMsg());
        }
    }

    @RequiresPermissions("mall:productcollection:edit")
    @GetMapping("/v1/tcmalladmin/productcollectionmanager/edit")
    public String edit(Model model, @RequestParam Integer collectionId){
        Integer venderId = this.getVenderId().intValue();
        HashMap<String, Object> countParams = new HashMap<String, Object>();
        countParams.put("collectionId", collectionId);
        int count = 0;
        ResultData<Integer> resultDataCount = productOfCollectionApi.count(countParams);
        if(resultDataCount.getError() == ErrorCode.SUCCESS){
            if(resultDataCount.getData() != null){
                count = resultDataCount.getData();
            }
        }
        ResultData<ProductCollection> resultData = productCollectionApi.get(collectionId);
        if(resultData.getError() == ErrorCode.SUCCESS){
            ProductCollection productCollection = resultData.getData();
            if(productCollection != null){
                model.addAttribute("id", productCollection.getId());
                model.addAttribute("name", productCollection.getName());
                model.addAttribute("desc", productCollection.getDescribe());
                model.addAttribute("imagePath", productCollection.getImagePath());
                model.addAttribute("count", count);
                model.addAttribute("status", productCollection.getStatus());
                return "tcmalladmin/productcollectionmanager/edit";
            }else {
                return "error/500";
            }

        }else {
            return "error/500";
        }
    }

    @RequiresPermissions("mall:productcollection:edit")
    @PostMapping("/v1/tcmalladmin/productcollectionmanager/collectionedit")
    @ResponseBody
    public R editCollection(@RequestBody ProductCollection productCollection){
        Integer venderId = this.getVenderId().intValue();
        productCollection.setVenderId(venderId);
        productCollection.setUtime(new Date());
        ResultData<Integer> resultData = productCollectionApi.update(productCollection);
        if(resultData.getError() == ErrorCode.SUCCESS){
            Integer r = resultData.getData();
            if(r > 0){
                return R.ok();
            }else {
                return R.error("商品集修改失败");
            }
        }else {
            return R.error(resultData.getMsg());
        }
    }

    @RequiresPermissions("mall:productcollection:remove")
    @PostMapping("/v1/tcmalladmin/productcollectionmanager/remove")
    @ResponseBody
    public R removeCollection(Integer id){
        Integer venderId = this.getVenderId().intValue();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("venderid", venderId);
        params.put("collectionid", id);
        ResultData<Integer> resultDataCount = collectionPricePolicyApi.count(params);
        if(resultDataCount.getError() != ErrorCode.SUCCESS){
            return R.error(resultDataCount.getMsg());
        }else {
            if(resultDataCount.getData() != null && resultDataCount.getData() > 0){
                return R.error("删除失败！请先删除商品集定价策略中该商品集");
            }
        }
        params.clear();
        params.put("venderId", venderId);
        params.put("collectionId", id);
        ResultData<Integer> resultDataProducts = productOfCollectionApi.count(params);
        if(resultDataProducts.getError() != ErrorCode.SUCCESS){
            return R.error(resultDataProducts.getMsg());
        }else {
            if(resultDataProducts.getData() != null && resultDataProducts.getData() > 0){
                return R.error("删除失败！请先删除商品集内商品");
            }
        }
        ResultData<Integer> resultDataRemove = productCollectionApi.remove(id);
        if(resultDataRemove.getError() == ErrorCode.SUCCESS){
            if(resultDataRemove.getData()!=null && resultDataRemove.getData()>0){
                return R.ok();
            }else {
                return R.error("删除失败");
            }
        }else {
            return R.error(resultDataRemove.getMsg());
        }

    }

    @RequiresPermissions("mall:productcollection:productcollections")
    @GetMapping("/v1/tcmalladmin/productcollectionmanager/picture")
    public String picture(Model model, @RequestParam("id") Integer id){
        ResultData<ProductCollection> resultData = productCollectionApi.get(id);
        if(resultData.getError() == ErrorCode.SUCCESS){
            ProductCollection productCollection = resultData.getData();
            if(productCollection != null){
                model.addAttribute("imagePath", productCollection.getImagePath());
            }else {
                model.addAttribute("imagePath", "");
            }
        }else {
            model.addAttribute("imagePath", "");
        }
        return "tcmalladmin/productcollectionmanager/picture";
    }

    @RequiresPermissions("mall:productcollection:edit")
    @GetMapping("/v1/tcmalladmin/productcollectionmanager/productadd")
    public String productAdd(Model model, @RequestParam("id") Integer id){
        ResultData<ProductCollection> resultData = productCollectionApi.get(id);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("catClass", 0);
        ResultData<DataList<List<CategoryResultInfo>>> resultDataCate0 = categoryApi.categoryList(params);
        if(resultDataCate0.getError() == ErrorCode.SUCCESS){
            DataList<List<CategoryResultInfo>> resultList = resultDataCate0.getData();
            model.addAttribute("cat0list", resultList.getData());
        }
        model.addAttribute("collectionId", id);
        return "tcmalladmin/productcollectionmanager/addproducts";
    }

    @RequiresPermissions("mall:productcollection:edit")
    @PostMapping("/v1/tcmalladmin/productcollectionmanager/addproduct2collection/{id}")
    @ResponseBody
    public R addProducts2Collection(@PathVariable("id") Integer id, @RequestBody List<Long> skus){
        Integer collectionId = id;
        Integer venderId = this.getVenderId().intValue();
        List<ProductOfCollection> productOfCollections = new ArrayList<ProductOfCollection>();
        for(Long sku:skus){
            ProductOfCollection productOfCollection = new ProductOfCollection();
            productOfCollection.setVenderId(venderId);
            productOfCollection.setCollectionId(id);
            productOfCollection.setSku(sku);
            productOfCollections.add(productOfCollection);
        }
        ResultData<Integer> resultData = productOfCollectionApi.batchAdd(productOfCollections);
        if(resultData.getError() == ErrorCode.SUCCESS){
            if(resultData.getData() != null && resultData.getData() > 0){
                return R.ok();
            }else {
                return R.error("添加失败");
            }
        }else {
            return R.error(resultData.getMsg());
        }
    }

    @RequiresPermissions("mall:productcollection:edit")
    @GetMapping("/v1/tcmalladmin/productcollectionmanager/productlist")
    @ResponseBody()
    Result<ProductInfo> listProduct(@RequestParam Map<String, Object> params) {
        Integer venderId = this.getVenderId().intValue();
        HashMap<String, Object> paramsPro = new HashMap<String, Object>();
        paramsPro.put("venderId", venderId);
        List<Long> skusExport = getProductOfCollection(paramsPro);
        params.put("notin", skusExport);
        params.put("state", 1);
        Result<ProductInfo> result = productDetailSearch.getProductList(venderId, params, true);
        return result;
    }

    @RequiresPermissions("mall:productcollection:productcollections")
    @GetMapping("/v1/tcmalladmin/productcollectionmanager/productofcollection/{id}")
    public String productofCollection(Model model, @PathVariable("id") Integer id){
        model.addAttribute("collectionId", id);
        return "tcmalladmin/productcollectionmanager/productsofcollection";
    }

    @RequiresPermissions("mall:productcollection:productcollections")
    @GetMapping("/v1/tcmalladmin/productcollectionmanager/productofcollectionlist/{id}")
    @ResponseBody()
    Result<ProductInfo> listProductOfCollection(@PathVariable("id") Integer id, @RequestParam Map<String, Object> params) {
        Integer venderId = this.getVenderId().intValue();
        HashMap<String, Object> paramsPro = new HashMap<String, Object>();
        paramsPro.put("venderId", venderId);
        paramsPro.put("collectionId", id);
        List<Long> skusIsIn = getProductOfCollection(paramsPro);
        if(skusIsIn.size() > 0){
            params.put("isin", skusIsIn);
            return productDetailSearch.getProductList(venderId, params, true);
        }else {
            Result<ProductInfo> result = new Result<ProductInfo>();
            result.setTotal(0);
            result.setRows(new ArrayList<ProductInfo>());
            return result;
        }

    }

    @RequiresPermissions("mall:productcollection:edit")
    @GetMapping("/v1/tcmalladmin/productcollectionmanager/brandnamebycat")
    @ResponseBody()
    Result<String> getBrandNameByCat(@RequestParam Map<String, Object> params) {
        Result<String> result = new Result<String>();
        ResultData<List<String>> resultData = productApi.getBrandNamesByCat2(params);
        if(resultData.getError() == ErrorCode.SUCCESS){
            List<String> brandNames = resultData.getData();
            result.setRows(brandNames);
            result.setTotal(brandNames.size());
        }else {
            result.setRows(new ArrayList<String>());
            result.setTotal(0);
        }
        return result;
    }

    @RequiresPermissions("mall:productcollection:edit")
    @PostMapping("/v1/tcmalladmin/productcollectionmanager/productofcollection/remove/{id}")
    @ResponseBody()
    R removeProductOfCollection(@PathVariable("id") Integer id, @RequestBody List<Long> skus) {
        Integer venderId = this.getVenderId().intValue();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("venderId", venderId);
        params.put("collectionId", id);
        params.put("skus", skus);
        ResultData<Integer> resultData = productOfCollectionApi.batchRemove(params);
        if(resultData.getError() == ErrorCode.SUCCESS){
            Integer r = resultData.getData();
            if(r != null && r > 0){
                return R.ok();
            }else {
                return R.error("删除失败");
            }
        }else {
            return R.error(resultData.getMsg());
        }
    }

    private List<Long> getProductOfCollection(Map<String, Object> params){
        List<Long> skus = new ArrayList<Long>();
        ResultData<DataList<List<ProductOfCollection>>> resultDataPro = productOfCollectionApi.list(params);
        if(resultDataPro.getError() == ErrorCode.SUCCESS){
            DataList<List<ProductOfCollection>> dataList = resultDataPro.getData();
            if(dataList != null){
                List<ProductOfCollection> productOfCollections = dataList.getData();
                for(ProductOfCollection p:productOfCollections){
                    skus.add(p.getSku());
                }
            }
        }
        return skus;
    }

}
