package com.cibnvideo.tcmalladmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.jdsyncapi.CategoryApi;
import com.cibnvideo.tcmalladmin.model.bean.CategoryResultInfo;
import com.cibnvideo.tcmalladmin.model.bean.ProductInfo;
import com.cibnvideo.tcmalladmin.omsapi.VenderSettlementApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.tcmalladmin.jdsyncapi.ProductApi;

import javax.servlet.http.HttpServletResponse;

@Controller
public class WaresManagerController extends BaseController {
	
	@Autowired
	ProductApi productApi;

	@Autowired
	CategoryApi categoryApi;

	@Autowired
	ProductDetailSearch productDetailSearch;

	@Autowired
	VenderSettlementApi venderSettlementApi;


	@GetMapping("/v1/tcmalladmin/dashboard")
	public String dashboard(Model model){
		return "tcmalladmin/waresmanager/dashboard";
	}

	@Log("请求访问商品列表页面")
	@RequiresPermissions("mall:wares:list")
	@GetMapping("/v1/tcmalladmin/wares")
	public String wares(Model model){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("catClass", 0);
		ResultData<DataList<List<CategoryResultInfo>>> resultData = categoryApi.categoryList(params);
		if(resultData.getError() == ErrorCode.SUCCESS){
			DataList<List<CategoryResultInfo>> resultList = resultData.getData();
			model.addAttribute("cat0list", resultList.getData());
		}
		return "tcmalladmin/waresmanager/wares";
	}


	@GetMapping("/v1/tcmalladmin/cat")
	@ResponseBody()
	public List<CategoryResultInfo> cat(@RequestParam String parentCategorys, @RequestParam Integer catClass) {
		List<CategoryResultInfo> result = new ArrayList<CategoryResultInfo>();
		if(!StringUtils.isEmpty(parentCategorys)){
			String[] categroys = parentCategorys.split(",");
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("catClass", catClass);
			for (String cat:categroys) {
				params.put("parentId", Integer.valueOf(cat));
				ResultData<DataList<List<CategoryResultInfo>>> resultData = categoryApi.categoryList(params);
				if(resultData.getError() == ErrorCode.SUCCESS){
					DataList<List<CategoryResultInfo>> categoryList = resultData.getData();
					if(categoryList != null){
						result.addAll(categoryList.getData());
					}
				}
			}
		}
		return result;
	}
	
	@GetMapping("/v1/tcmalladmin/productinfo/list")
	@RequiresPermissions("mall:wares:list")
	@ResponseBody()
	Result<ProductInfo> list(@RequestParam Map<String, Object> params) {
		Long venderId = this.getVenderId();
		return productDetailSearch.getProductList(venderId.intValue(), params, false);
	}

	@GetMapping("/v1/tcmalladmin/productinfo/video")
	String video(Model model, @RequestParam Long sku) {
		ResultData<ProductInfo> resultData = productApi.detailInfo(sku);
		if(resultData.getError() == ErrorCode.SUCCESS){
			ProductInfo info = resultData.getData();
			if(info != null) {
				model.addAttribute("videoPath", info.getVideoPath());
			}else {
				model.addAttribute("videoPath", "");
			}
		}else {
			model.addAttribute("videoPath", "");
		}
		model.addAttribute("sku", sku);
		return "tcmalladmin/waresmanager/video";
	}

	@GetMapping("/v1/tcmalladmin/productinfo/video/add")
	@RequiresPermissions("mall:wares:video:edit")
	@ResponseBody
	R videoAdd(@RequestParam("sku") Long sku, @RequestParam("videoPath") String videoPath) {
		ResultData<Integer> resultData = productApi.productVideoAdd(sku, videoPath);
		if(resultData.getError() == ErrorCode.SUCCESS){
			Integer result = resultData.getData();
			if(result != null && result > 0) {
				return R.ok();
			}else {
				return R.error("保存失败!");
			}
		}else {
			return R.error(resultData.getMsg());
		}
	}

	@GetMapping("/v1/tcmalladmin/productinfo/export")
	@RequiresPermissions("mall:wares:list")
	public void export(HttpServletResponse response, @RequestParam Map<String, Object> params) {
		productDetailSearch.exportExcel(response, params, getVenderId().intValue(), false);
	}

}
