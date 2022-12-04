package com.cibnvideo.common.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibnvideo.common.entity.LogDO;
import com.cibnvideo.common.entity.PageDO;
import com.cibnvideo.common.service.LogService;
import com.cibnvideo.common.utils.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@RequestMapping("/common/log")
@Controller
public class LogController {
	@Autowired
	LogService logService;
	String prefix = "common/log";

	@RequiresPermissions("common:log")
	@GetMapping()
	String log() {
		return prefix + "/log";
	}

	@RequiresPermissions("common:log")
	@ResponseBody
	@GetMapping("/list")
	PageDO<LogDO> list(Integer pageSize, Integer pageNumber, String sortOrder, String username, String operation) {
		PageHelper.startPage(pageNumber, pageSize);
		LogDO log = new LogDO();
		log.setUsername(username);
		log.setOperation(operation);
		PageDO<LogDO> pages = new PageDO<LogDO>();
		List<LogDO> logs = logService.queryList(log);
		PageInfo<LogDO> pageInfo = new PageInfo<LogDO>(logs);
		pages.setRows(pageInfo.getList());
		pages.setTotal((int)pageInfo.getTotal());
		return pages;
	}

	@RequiresPermissions("sys:log:remove")
	@ResponseBody
	@PostMapping("/remove")
	R remove(Long id) {
		if (logService.remove(id)>0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:log:clear")
	@ResponseBody
	@PostMapping("/batchRemove")
	R batchRemove(@RequestParam("ids[]") Long[] ids) {
		int r = logService.batchRemove(ids);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}
}
