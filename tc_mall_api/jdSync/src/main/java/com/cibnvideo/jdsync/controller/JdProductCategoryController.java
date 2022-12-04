package com.cibnvideo.jdsync.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cibnvideo.jdsync.config.Router;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cibnvideo.jdsync.bean.CategoryResultInfo;
import com.cibnvideo.jdsync.dao.JdProductCategoryDao;;

@RestController
public class JdProductCategoryController {

	@Autowired
	private JdProductCategoryDao jdProductCategoryDao;

	@ApiOperation(value = "列出所有分类", notes = "列出所有分类信息")
	@GetMapping(Router.V1_JDSYNC_CATEGORY_LIST)
	public ResultData getAllCategory0(@RequestParam Map<String, Object> params) {
		ResultData<DataList<List<CategoryResultInfo>>> resultData = Tools.getThreadResultData();
		DataList<List<CategoryResultInfo>> result = Tools.getThreadDataList();
		result.setTotalRows(jdProductCategoryDao.count(params));
		result.setData(jdProductCategoryDao.list(params));
		resultData.setData(result);
		return resultData;
	}

	@ApiOperation(value = "获取分类信息", notes = "根据分类ID获取分类信息")
	@GetMapping(Router.V1_JDSYNC_CATEGORY_GET)
	public ResultData getAllCategory0(@RequestParam Integer catId) {
		ResultData<CategoryResultInfo> resultData = Tools.getThreadResultData();
		resultData.setData(jdProductCategoryDao.get(catId));
		return resultData;
	}

	@ApiOperation(value = "批量获取分类信息", notes = "根据分类ID列表批量获取分类信息")
	@PostMapping(Router.V1_JDSYNC_CATEGORY_LIST_BY_CATIDS)
	public ResultData getAllCategoryByCatId(@RequestBody Integer[] catIds) {
		ResultData<List<CategoryResultInfo>> resultData = Tools.getThreadResultData();
		if(catIds.length > 0){
			resultData.setData(jdProductCategoryDao.listByCatIds(catIds));
		}else {
			resultData.setData(new ArrayList<CategoryResultInfo>());
		}
		return resultData;
	}

	@ApiOperation(value = "搜索分类信息", notes = "根据条件搜索分类信息")
	@PostMapping(Router.V1_JDSYNC_CATEGORY_SEARCH)
	public ResultData getCategorySearch(@RequestBody Map<String, Object> params) {
		ResultData<DataList<List<CategoryResultInfo>>> resultData = Tools.getThreadResultData();
		DataList<List<CategoryResultInfo>> result = Tools.getThreadDataList();
		result.setTotalRows(jdProductCategoryDao.searchcount(params));
		result.setData(jdProductCategoryDao.searchinfo(params));
		resultData.setData(result);
		return resultData;
	}
}