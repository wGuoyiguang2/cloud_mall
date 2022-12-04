package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.jdsyncapi.CategoryApi;
import com.cibnvideo.tcmalladmin.model.bean.CategoryResultInfo;
import com.cibnvideo.tcmalladmin.model.bean.PriceCategory;
import com.cibnvideo.tcmalladmin.omsapi.CategoryPricePolicyApi;
import com.cibnvideo.tcmalladmin.omsapi.PricePolicyApi;
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
public class CategoryPricePolicyController extends BaseController {

    @Autowired
    PricePolicyApi pricePolicyApi;

    @Autowired
    CategoryPricePolicyApi categoryPricePolicyApi;

    @Autowired
    CategoryApi categoryApi;

    @RequiresPermissions("mall:pricepolicy:pricepolicies")
    @GetMapping("/v1/tcmalladmin/pricepolicymanager/category/list")
    @ResponseBody()
    Result<PriceCategory> list(@RequestParam Map<String, Object> params) {
        Integer venderId = this.getVenderId().intValue();
        params.put("venderId", venderId);
        Result<PriceCategory> result = new Result<PriceCategory>();
        result.setTotal(0);
        ResultData<DataList<List<PriceCategory>>> resultData = categoryPricePolicyApi.list(params);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            DataList<List<PriceCategory>> dataList = resultData.getData();
            if (dataList != null) {
                for(PriceCategory p: dataList.getData()){
                    Integer[] cats = {p.getCat0(), p.getCat1(), p.getCat2()};
                    ResultData<List<CategoryResultInfo>> resultDataCatNames = categoryApi.categoryListByCatIds(cats);
                    if(resultDataCatNames.getError() == ErrorCode.SUCCESS){
                        List<CategoryResultInfo> categoryResultInfoList = resultDataCatNames.getData();
                        if(categoryResultInfoList != null){
                            for(CategoryResultInfo info:categoryResultInfoList){
                                switch (info.getCatClass()){
                                    case 0:
                                        p.setCat0Name(info.getName());break;
                                    case 1:
                                        p.setCat1Name(info.getName());break;
                                    case 2:
                                        p.setCat2Name(info.getName());break;
                                }
                            }
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
    @GetMapping("/v1/tcmalladmin/pricepolicymanager/category/add")
    public String add(Model model, @RequestParam("policyid") Integer policyid) {
        Integer venderId = this.getVenderId().intValue();
        model.addAttribute("policyid", policyid);
        HashMap<String, Object> paramsCat = new HashMap<String, Object>();
        paramsCat.put("catClass", 0);
        ResultData<DataList<List<CategoryResultInfo>>> resultDataCat0 = categoryApi.categoryList(paramsCat);
        if(resultDataCat0.getError() == ErrorCode.SUCCESS){
            DataList<List<CategoryResultInfo>> resultList = resultDataCat0.getData();
            model.addAttribute("cat0list", resultList.getData());
        }
        return "tcmalladmin/pricepolicymanager/addcategory";
    }

    @RequiresPermissions("mall:pricepolicy:edit")
    @PostMapping("/v1/tcmalladmin/pricepolicymanager/category/save")
    @ResponseBody
    public R saveCategoryPricePolicy(PriceCategory priceCategory) {
        Integer venderId = this.getVenderId().intValue();
        priceCategory.setVenderid(venderId);
        priceCategory.setCtime(new Date());
        priceCategory.setUtime(new Date());
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("venderid", venderId);
        if(priceCategory.getCat0() != null){
            params.put("cat0", priceCategory.getCat0());
        }else {
            priceCategory.setCat0(0);
            params.put("cat0", 0);
        }
        if(priceCategory.getCat1() != null){
            params.put("cat1", priceCategory.getCat1());
        }else {
            priceCategory.setCat1(0);
            params.put("cat1", 0);
        }
        if(priceCategory.getCat2() != null){
            params.put("cat2", priceCategory.getCat2());
        }else {
            priceCategory.setCat2(0);
            params.put("cat2", 0);
        }
        ResultData<Integer> resultDataCount = categoryPricePolicyApi.count(params);
        if(resultDataCount.getError() == ErrorCode.SUCCESS){
            if(resultDataCount.getData() != null){
                if(resultDataCount.getData() > 0){
                    return R.error("此分类已存在分类价格策略");
                }
            }
        }
        ResultData<Integer> resultData = categoryPricePolicyApi.add(priceCategory);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            Integer result = resultData.getData();
            if (result != null && result > 0) {
                return R.ok();
            } else {
                return R.error("分类添加失败");
            }
        } else {
            return R.error(resultData.getMsg());
        }
    }

    @RequiresPermissions("mall:pricepolicy:edit")
    @PostMapping("/v1/tcmalladmin/pricepolicymanager/category/remove/{policyid}")
    @ResponseBody
    public R removeCategoryPricePolicy(@PathVariable("policyid") Integer policyid, @RequestBody List<Integer> ids) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("venderid", this.getVenderId().intValue());
        params.put("policyid", policyid);
        params.put("ids", ids);
        ResultData<Integer> resultData = categoryPricePolicyApi.batchRemove(params);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            Integer r = resultData.getData();
            if (r > 0) {
                return R.ok();
            } else {
                return R.error("分类删除失败");
            }
        } else {
            return R.error(resultData.getMsg());
        }
    }
}