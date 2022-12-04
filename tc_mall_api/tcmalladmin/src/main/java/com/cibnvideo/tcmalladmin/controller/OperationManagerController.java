package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.jdsyncapi.CategoryApi;
import com.cibnvideo.tcmalladmin.jdsyncapi.ProductApi;
import com.cibnvideo.tcmalladmin.model.bean.CategoryResultInfo;
import com.cibnvideo.tcmalladmin.model.bean.ProductInfo;
import com.cibnvideo.tcmalladmin.model.bean.SellPriceResult;
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

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OperationManagerController extends BaseController {

    @Autowired
    ProductApi productApi;

    @Autowired
    CategoryApi categoryApi;

    @Autowired
    VenderSettlementApi venderSettlementApi;

    @Autowired
    ProductDetailSearch productDetailSearch;

    @Autowired
    ProductRemoveApi productRemoveApi;

    @Log("请求访问运营管理商品管理页面")
    @RequiresPermissions("mall:operation:products")
    @GetMapping("/v1/tcmalladmin/operation/products")
    public String operationManager(Model model) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("catClass", 0);
        ResultData<DataList<List<CategoryResultInfo>>> resultData = categoryApi.categoryList(params);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            DataList<List<CategoryResultInfo>> resultList = resultData.getData();
            model.addAttribute("cat0list", resultList.getData());
        }
        return "tcmalladmin/operationmanager/products";
    }

    @RequiresPermissions("mall:operation:products")
    @GetMapping("/v1/tcmalladmin/operation/productlist")
    @ResponseBody()
    Result<ProductInfo> list(@RequestParam Map<String, Object> params) {
        Integer venderId = this.getVenderId().intValue();
        params.put("state", 1);
        Result<ProductInfo> result = productDetailSearch.getProductList(venderId, params, true);
        return result;
    }

    @Log("编辑单品价格")
    @RequiresPermissions("mall:operation:editprice")
    @PostMapping("/v1/tcmalladmin/operation/productedit")
    @ResponseBody()
    R productEdit(@RequestBody ProductInfo productInfo) {
        Long venderId = this.getVenderId();
        ResultData<SellPriceResult> sellPriceResult = venderSettlementApi.getPrice(venderId.intValue(), productInfo.getSku());
        if (sellPriceResult.getError() == ErrorCode.SUCCESS) {
            SellPriceResult s = sellPriceResult.getData();
            if (s != null) {
                if (s.getPrice().compareTo(productInfo.getPrice()) == 1) {
                    return R.error("价格不能小于底价");
                }
            } else {
                return R.error("查询底价失败");
            }
        }
        return R.ok();
    }

    @RequiresPermissions("mall:operation:products")
    @GetMapping("/v1/tcmalladmin/operation/video")
    String video(Model model, @RequestParam Long sku) {
        ResultData<ProductInfo> resultData = productApi.detailInfo(sku);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            ProductInfo info = resultData.getData();
            if (info != null) {
                model.addAttribute("videoPath", info.getVideoPath());
            } else {
                model.addAttribute("videoPath", "");
            }
        } else {
            model.addAttribute("videoPath", "");
        }
        model.addAttribute("sku", sku);
        return "tcmalladmin/operationmanager/video";
    }

    @GetMapping("/v1/tcmalladmin/operation/export")
    @RequiresPermissions("mall:operation:products")
    public void export(HttpServletResponse response, @RequestParam Map<String, Object> params) {
        productDetailSearch.exportExcel(response, params, getVenderId().intValue(), true);
    }
}
