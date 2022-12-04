package com.cibnvideo.tcmalladmin.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.jdsyncapi.CategoryApi;
import com.cibnvideo.tcmalladmin.jdsyncapi.ProductApi;
import com.cibnvideo.tcmalladmin.model.bean.CategoryResultInfo;
import com.cibnvideo.tcmalladmin.model.bean.ProductCollection;
import com.cibnvideo.tcmalladmin.model.bean.ProductInfo;
import com.cibnvideo.tcmalladmin.model.bean.RecommendBean;
import com.cibnvideo.tcmalladmin.omsapi.ProductCollectionApi;
import com.cibnvideo.tcmalladmin.omsapi.RecommendApi;
import com.cibnvideo.tcmalladmin.omsapi.RecommendHistoryApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class RecommendManagerController extends BaseController {

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


    @Log("列出所有推荐位")
    @RequiresPermissions("mall:layout:list")
    @GetMapping("/v1/tcmalladmin/recommendmanager/recommendlist")
    @ResponseBody()
    Result<RecommendBean> list(@RequestParam Map<String, Object> params) {
        Result<RecommendBean> result = new Result<RecommendBean>();
        result.setTotal(0);
        params.put("venderId", this.getVenderId().intValue());
        params.put("sort", "position");
        params.put("order", "asc");
        ResultData<DataList<List<RecommendBean>>> resultData = recommendApi.list(params);
        if(resultData.getError() == ErrorCode.SUCCESS){
            DataList<List<RecommendBean>> dataList = resultData.getData();
            result.setRows(dataList.getData());
            result.setTotal(dataList.getTotalRows());
            return result;
        }else {
            return result;
        }
    }

    @Log("访问推荐位添加页面")
    @RequiresPermissions("mall:recommend:add")
    @GetMapping("/v1/tcmalladmin/recommendmanager/add")
    public String addRecommend(Model model, @RequestParam("layoutId") Integer layoutId,@RequestParam("layout") String layout,@RequestParam("position") Integer position){
        Integer venderId = this.getVenderId().intValue();
        HashMap<String, Object> params = new HashMap<String, Object>(1);
        params.put("venderId", venderId);
        params.put("layoutId", layoutId);
        params.put("position", position);
        RecommendBean recommendBean = null;
        ResultData<RecommendBean> resultData = recommendApi.getbyposition(venderId, layoutId, position);
        if(resultData.getError() == ErrorCode.SUCCESS){
            recommendBean = resultData.getData();
        }
        if(recommendBean == null){
            recommendBean = new RecommendBean();
        }
        model.addAttribute("recommend", recommendBean);
        model.addAttribute("layoutId", layoutId);
        model.addAttribute("layout", layout);
        model.addAttribute("position", position);

        Map paramsRecommend = new HashMap<String, Object>();
        if(!StringUtils.isEmpty(recommendBean.getActionParam())){
            paramsRecommend = JSON.parseObject(recommendBean.getActionParam(), new TypeReference<HashMap<String,Object>>() {});
        }
        Integer cat0 = 0;
        Integer cat1 = 0;
        Integer cat2 = 0;
        String sku = "";
        Integer collection = 0;
        if(paramsRecommend.containsKey("sku")){
            sku = sku + paramsRecommend.getOrDefault("sku", "");
        }
        if(paramsRecommend.containsKey("cat0")){
            JSONObject cat0Info = (JSONObject) paramsRecommend.getOrDefault("cat0", null);
            if(cat0Info != null){
                cat0 = cat0Info.getInteger("catId");
            }
        }
        if(paramsRecommend.containsKey("cat1")){
            JSONObject cat1Info = (JSONObject) paramsRecommend.getOrDefault("cat1", null);
            if(cat1Info != null){
                cat1 = cat1Info.getInteger("catId");
            }
        }
        if(paramsRecommend.containsKey("cat2")){
            JSONObject cat2Info = (JSONObject) paramsRecommend.getOrDefault("cat2", null);
            if(cat2Info != null){
                cat2 = cat2Info.getInteger("catId");
            }
        }
        if(paramsRecommend.containsKey("collectionId")){
            collection = (Integer)paramsRecommend.getOrDefault("collectionId", 0);
        }
        model.addAttribute("sku", sku);
        model.addAttribute("cat0", cat0);
        model.addAttribute("cat1", cat1);
        model.addAttribute("cat2", cat2);
        model.addAttribute("collectionid", collection);

        HashMap<String, Object> paramsCat = new HashMap<String, Object>();
        paramsCat.put("catClass", 0);
        ResultData<DataList<List<CategoryResultInfo>>> resultDataCat0 = categoryApi.categoryList(paramsCat);
        if(resultDataCat0.getError() == ErrorCode.SUCCESS){
            DataList<List<CategoryResultInfo>> resultList = resultDataCat0.getData();
            model.addAttribute("cat0list", resultList.getData());
        }
        if(cat0 != 0){
            paramsCat.put("parentId", cat0);
            paramsCat.put("catClass", 1);
            ResultData<DataList<List<CategoryResultInfo>>> resultDataCat1 = categoryApi.categoryList(paramsCat);
            if(resultDataCat1.getError() == ErrorCode.SUCCESS){
                DataList<List<CategoryResultInfo>> resultList = resultDataCat1.getData();
                model.addAttribute("cat1list", resultList.getData());
            }else {
                model.addAttribute("cat1list", new ArrayList<CategoryResultInfo>());
            }
        }

        if(cat1 != 0){
            paramsCat.put("parentId", cat1);
            paramsCat.put("catClass", 2);
            ResultData<DataList<List<CategoryResultInfo>>> resultDataCat1 = categoryApi.categoryList(paramsCat);
            if(resultDataCat1.getError() == ErrorCode.SUCCESS){
                DataList<List<CategoryResultInfo>> resultList = resultDataCat1.getData();
                model.addAttribute("cat2list", resultList.getData());
            }else {
                model.addAttribute("cat2list", new ArrayList<CategoryResultInfo>());
            }
        }

        HashMap<String, Object> paramsCollection = new HashMap<String, Object>();
        paramsCollection.put("venderId", venderId);
        paramsCollection.put("status" , 1);
        ResultData<DataList<List<ProductCollection>>> resultDataCollection = productCollectionApi.list(paramsCollection);
        if(resultDataCollection.getError() == ErrorCode.SUCCESS){
            DataList<List<ProductCollection>> dataList = resultDataCollection.getData();
            if(dataList.getData() != null){
                model.addAttribute("collectionlist", dataList.getData());
            }else {
                model.addAttribute("collectionlist", new ArrayList<ProductCollection>());
            }
        }else {
            model.addAttribute("collectionlist", new ArrayList<ProductCollection>());
        }
        return "tcmalladmin/recommendmanager/add";
    }

    @Log("添加或编辑推荐位")
    @PostMapping("/v1/tcmalladmin/recommendmanager/add")
    @RequiresPermissions("mall:recommend:add")
    @ResponseBody
    public R saveRecommend(RecommendBean recommendBean){
        Integer venderId = this.getVenderId().intValue();
        recommendBean.setVenderId(this.getVenderId().intValue());
        String action = recommendBean.getAction();
        if(!StringUtils.isEmpty(action) && action.equals("OPEN_DETAIL")){
            HashMap<String, Object> actionParamSku = new HashMap<String, Object>();
            actionParamSku.put("sku", recommendBean.getActionParamDetail());
            recommendBean.setActionParam(new JSONObject(actionParamSku).toString());
            //往期推荐
            if(recommendBean.getStatus() == 1) {//启用时才记录
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("venderId", recommendBean.getVenderId());
                params.put("ctype", recommendBean.getCtype());
                params.put("action", recommendBean.getAction());
                params.put("actionParam", recommendBean.getActionParam());
                ResultData<Integer> countTody = recommendHistoryApi.countToday(params);
                if (countTody.getError() == ErrorCode.SUCCESS) {
                    Integer count = countTody.getData();
                    if (count == null || count <= 0) {
                        recommendBean.setCtime(new Date());
                        recommendBean.setUtime(new Date());
                        ResultData<Integer> result = recommendHistoryApi.add(recommendBean);
                        if (result.getError() == ErrorCode.SUCCESS) {
                            Integer r = result.getData();
                            if (r == null || r <= 0) {
                                return R.error("商品详情添加失败,往期推荐添加失败");
                            }
                        } else {
                            return R.error(result.getMsg());
                        }
                    } else {
                        recommendBean.setUtime(new Date());
                        ResultData<Integer> result = recommendHistoryApi.update(recommendBean);
                        if (result.getError() != ErrorCode.SUCCESS) {
                            return R.error(result.getMsg());
                        }
                    }
                } else {
                    return R.error("商品详情添加失败,往期推荐查询失败");
                }
            }
        } else if(!StringUtils.isEmpty(action) && action.equals("OPEN_CATEGORY")){
            HashMap<String, Object> actionParamCategory = new HashMap<String, Object>();
            Integer[] cats = {recommendBean.getActionParamCat0(), recommendBean.getActionParamCat1(), recommendBean.getActionParamCat2()};
            ResultData<List<CategoryResultInfo>> resultDataCats = categoryApi.categoryListByCatIds(cats);
            if(resultDataCats.getError() == ErrorCode.SUCCESS){
                List<CategoryResultInfo> categoryResultInfos = resultDataCats.getData();
                if(categoryResultInfos != null){
                    CategoryResultInfo cat0 = categoryResultInfos.size()>0? categoryResultInfos.get(0):null;
                    CategoryResultInfo cat1 = categoryResultInfos.size()>1? categoryResultInfos.get(1):null;
                    CategoryResultInfo cat2 = categoryResultInfos.size()>2? categoryResultInfos.get(2):null;
                    if(cat0 != null){
                        cat0.setParentId(null);
                        cat0.setCatClass(null);
                        cat0.setState(null);
                        actionParamCategory.put("cat0", cat0);
                    }
                    if(cat1 != null){
                        cat1.setParentId(null);
                        cat1.setCatClass(null);
                        cat1.setState(null);
                        actionParamCategory.put("cat1", cat1);
                    }
                    if(cat2 != null){
                        cat2.setParentId(null);
                        cat2.setCatClass(null);
                        cat2.setState(null);
                        actionParamCategory.put("cat2", cat2);
                    }
                }
            }
            recommendBean.setActionParam(new JSONObject(actionParamCategory).toString());
        }else if(!StringUtils.isEmpty(action) && action.equals("OPEN_COLLECTION")){
            HashMap<String, Object> actionParamCollection = new HashMap<String, Object>();
            actionParamCollection.put("collectionId", recommendBean.getActionParamCollection());
            recommendBean.setActionParam(new JSONObject(actionParamCollection).toString());
        }
        ResultData<RecommendBean> resultDataRecommend = recommendApi.getbyposition(venderId, recommendBean.getLayoutId(), recommendBean.getPosition());
        if(resultDataRecommend.getError() == ErrorCode.SUCCESS){
            if(resultDataRecommend.getData() != null){//推荐位存在，更新
                RecommendBean rb = resultDataRecommend.getData();
                if(rb == null){
                    return R.error("未查到推荐位信息");
                }
                recommendBean.setId(rb.getId());
                recommendBean.setUtime(new Date());
                ResultData<Integer> resultDataUpdate = recommendApi.update(recommendBean);
                if(resultDataUpdate.getError() == ErrorCode.SUCCESS){
                    Integer result = resultDataUpdate.getData();
                    if(resultDataUpdate.getData() > 0){
                        return R.ok();
                    }else {
                        return R.error("推荐位更新失败");
                    }
                }else {
                    return R.error(resultDataUpdate.getMsg());
                }
            } else {//推荐位不存在，添加
                recommendBean.setCtime(new Date());
                recommendBean.setUtime(new Date());
                ResultData<Integer> resultData = recommendApi.add(recommendBean);
                if(resultData.getError() == ErrorCode.SUCCESS){
                    Integer code = resultData.getData();
                    if(code > 0){
                        return R.ok();
                    }else {
                        return R.error("推荐位添加失败");
                    }
                }else {
                    return R.error("推荐位添加失败");
                }
            }
        }else {
            return R.error(resultDataRecommend.getMsg());
        }
    }

    @GetMapping("/v1/tcmalladmin/recommendmanager/getimagepath")
    @ResponseBody
    public ProductInfo getImagePath(@RequestParam Long sku){
        ResultData<ProductInfo> resultData = productApi.detailInfo(sku);
        return resultData.getData();

    }
}
