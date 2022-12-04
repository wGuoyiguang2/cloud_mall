package com.cibnvideo.jdsync.controller;

import com.cibnvideo.jdsync.config.Router;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cibnvideo.jdsync.bean.PictureOfPcResponse;
import com.cibnvideo.jdsync.dao.JdPictureOfPcDao;;

@RestController
public class JdPictureOfPcController {

	@Autowired
	private JdPictureOfPcDao jdPictureOfPcDao;

	@ApiOperation(value = "获取PC端商品图片", notes = "根据sku获取PC端商品图片")
	@GetMapping(Router.V1_JDSYNC_PICTURE_PC)
	public ResultData getPictureOfPc(@RequestParam Long sku) {
        ResultData<PictureOfPcResponse> resultData = Tools.getThreadResultData();
        resultData.setData(jdPictureOfPcDao.get(sku));
	    return resultData;
	}
}
