package com.mayikt.spike.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import feign.RequestLine;

/**
 * 秒杀访问相关请求
 * 
 * 
 * @description:
 * @author: 97后互联网架构师-余胜军
 * @contact: QQ644064779、微信yushengjun644 www.mayikt.com
 * @date: 2019年1月3日 下午3:03:17
 * @version V1.0
 * @Copyright 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@Controller
public class SpikeController {
	/**
	 * 秒杀商品详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/details/{id}")
	public String details(@PathVariable("id") Long id) {
		return "details";
	}
}