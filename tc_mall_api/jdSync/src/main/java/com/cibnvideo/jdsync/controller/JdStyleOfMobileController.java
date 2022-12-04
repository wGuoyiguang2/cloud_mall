package com.cibnvideo.jdsync.controller;

import com.cibnvideo.jdsync.config.Router;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cibnvideo.jdsync.bean.StyleOfMobileResult;
import com.cibnvideo.jdsync.dao.JdStyleOfMobileDao;;

@RestController
public class JdStyleOfMobileController {

	@Autowired
	private JdStyleOfMobileDao jdStyleOfMobileDao;

	@ApiOperation(value = "获取移动端商品样式", notes = "根据sku获取移动端商品样式")
	@GetMapping(Router.V1_JDSYNC_STYLE_MOBILE)
	public ResultData getStyleOfPc(@RequestParam Long sku) {
		ResultData<StyleOfMobileResult> resultData = Tools.getThreadResultData();
		resultData.setData(jdStyleOfMobileDao.get(sku));
		return resultData;
	}
}
