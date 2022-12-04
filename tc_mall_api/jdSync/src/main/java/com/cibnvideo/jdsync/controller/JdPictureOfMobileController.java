package com.cibnvideo.jdsync.controller;

import com.cibnvideo.jdsync.bean.PictureOfMobileResponse;
import com.cibnvideo.jdsync.config.Router;
import com.cibnvideo.jdsync.dao.JdPictureOfMobileDao;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "获取京东移动端图片")
@RestController
public class JdPictureOfMobileController {

	@Autowired
	private JdPictureOfMobileDao jdPictureOfMobileDao;

	@ApiOperation(value = "根据sku获取移动端商品图片", notes = "根据sku获取移动端商品图片")
	@GetMapping(Router.V1_JDSYNC_PICTURE_MOBILE)
	public ResultData getStyleOfPc(@RequestParam Long sku) {
		ResultData<PictureOfMobileResponse> resultData = Tools.getThreadResultData();
		resultData.setData(jdPictureOfMobileDao.get(sku));
		return resultData;
	}
}
