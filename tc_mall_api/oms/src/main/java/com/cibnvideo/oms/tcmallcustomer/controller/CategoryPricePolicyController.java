package com.cibnvideo.oms.tcmallcustomer.controller;

import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.PriceCategory;
import com.cibnvideo.oms.tcmallcustomer.service.CategoryPricePolicyService;
import com.cibnvideo.oms.tcmallcustomer.service.SendMessageService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CategoryPricePolicyController {

    @Autowired
    CategoryPricePolicyService categoryPricePolicyService;

    @Autowired
    SendMessageService sendMessageService;

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_CATEGORY_LIST, method = RequestMethod.GET)
    ResultData list(@RequestParam Map<String, Object> params) {
        ResultData resultData = Tools.getThreadResultData();
        List<PriceCategory> priceCategoryList = categoryPricePolicyService.list(params);
        int count = categoryPricePolicyService.count(params);
        DataList<List<PriceCategory>> result = Tools.getThreadDataList();
        result.setData(priceCategoryList);
        result.setTotalRows(count);
        resultData.setData(result);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_CATEGORY_COUNT, method = RequestMethod.GET)
    ResultData count(@RequestParam Map<String, Object> params) {
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int count = categoryPricePolicyService.count(params);
        resultData.setData(count);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_CATEGORY_GET, method = RequestMethod.GET)
    ResultData get(@RequestParam Integer id) {
        ResultData<PriceCategory> resultData = Tools.getThreadResultData();
        resultData.setData(categoryPricePolicyService.get(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_CATEGORY_ADD, method = RequestMethod.POST)
    ResultData add(@RequestBody PriceCategory priceCategory) {
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = categoryPricePolicyService.save(priceCategory);
        resultData.setData(result);
        if (result > 0) {
            int catId = 0;
            int catType = -1;
            if (priceCategory.getCat2() != null && priceCategory.getCat2() != 0) {
                catId = priceCategory.getCat2();
                catType = 2;
            } else if (priceCategory.getCat1() != null && priceCategory.getCat1() != 0) {
                catId = priceCategory.getCat1();
                catType = 1;
            } else if (priceCategory.getCat0() != null && priceCategory.getCat0() != 0) {
                catId = priceCategory.getCat0();
                catType = 0;
            }
            sendMessageService.priceProductCategoryChange(priceCategory.getVenderid(), catId, catType);
        }

        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_CATEGORY_REMOVE, method = RequestMethod.GET)
    ResultData remove(@RequestParam Integer id) {
        ResultData<Integer> resultData = Tools.getThreadResultData();
        PriceCategory priceCategory = categoryPricePolicyService.get(id);
        if (priceCategory != null) {
            int result = categoryPricePolicyService.remove(id);
            resultData.setData(result);
            if (result > 0) {
                if (priceCategory != null) {
                    int catId = 0;
                    int catType = -1;
                    if (priceCategory.getCat2() != null && priceCategory.getCat2() != 0) {
                        catId = priceCategory.getCat2();
                        catType = 2;
                    } else if (priceCategory.getCat1() != null && priceCategory.getCat1() != 0) {
                        catId = priceCategory.getCat1();
                        catType = 1;
                    } else if (priceCategory.getCat0() != null && priceCategory.getCat0() != 0) {
                        catId = priceCategory.getCat0();
                        catType = 0;
                    }
                    sendMessageService.priceProductCategoryChange(priceCategory.getVenderid(), catId, catType);
                }
            }
        } else {
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("price policy category not exist");
        }
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_CATEGORY_BATCHREMOVE, method = RequestMethod.POST)
    ResultData batchRemove(@RequestBody Map<String, Object> params) {
        ResultData<Integer> resultData = Tools.getThreadResultData();
        if (params.containsKey("venderid") && params.containsKey("ids")) {
            int venderId = (int) params.get("venderid");
            List<Integer> ids = (List<Integer>) params.get("ids");
            for (Integer id : ids) {
                PriceCategory priceCategory = categoryPricePolicyService.get(id);
                if (priceCategory != null) {
                    resultData.setData(categoryPricePolicyService.remove(id));
                    int catId = 0;
                    int catType = -1;
                    if (priceCategory.getCat2() != null && priceCategory.getCat2() != 0) {
                        catId = priceCategory.getCat2();
                        catType = 2;
                    } else if (priceCategory.getCat1() != null && priceCategory.getCat1() != 0) {
                        catId = priceCategory.getCat1();
                        catType = 1;
                    } else if (priceCategory.getCat0() != null && priceCategory.getCat0() != 0) {
                        catId = priceCategory.getCat0();
                        catType = 0;
                    }
                    sendMessageService.priceProductCategoryChange(venderId, catId, catType);
                }
            }
        }else {
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("venderid or ids not exist");
        }

        return resultData;
    }
}
