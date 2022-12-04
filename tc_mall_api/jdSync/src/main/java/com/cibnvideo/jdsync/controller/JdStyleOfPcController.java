package com.cibnvideo.jdsync.controller;

import com.cibnvideo.jdsync.config.Router;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cibnvideo.jdsync.bean.StyleOfPcResult;
import com.cibnvideo.jdsync.dao.JdStyleOfPcDao;;

@RestController
public class JdStyleOfPcController {

	@Autowired
	private JdStyleOfPcDao jdStyleOfPcDao;

	@ApiOperation(value = "获取PC端商品样式", notes = "根据sku获取PC端商品样式")
	@GetMapping(Router.V1_JDSYNC_STYLE_PC)
	public ResultData getStyleOfPc(@RequestParam Long sku) {
		ResultData<StyleOfPcResult> resultData = Tools.getThreadResultData();
		resultData.setData(jdStyleOfPcDao.get(sku));
		return resultData;
	}
}
