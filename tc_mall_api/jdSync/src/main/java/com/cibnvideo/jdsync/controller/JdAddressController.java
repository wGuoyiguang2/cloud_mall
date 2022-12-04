package com.cibnvideo.jdsync.controller;

import com.cibnvideo.jdsync.bean.*;
import com.cibnvideo.jdsync.config.Router;
import com.cibnvideo.jdsync.dao.*;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description = "京东地址管理")
@RestController
public class JdAddressController {

	@Autowired
	private ProvinceDao provinceDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private CountyDao countyDao;
	@Autowired
	private TownDao townDao;
	@Autowired
	private AddressDao addressDao;

	@ApiOperation(value = "列出所有省地址", notes = "根据过滤条件获取省地址列表")
	@GetMapping(Router.V1_JDSYNC_ADDRESS_PROVINCELIST)
	public ResultData getProvinceList(@RequestParam Map<String, Object> params) {
		ResultData<DataList<List<Province>>> resultData = Tools.getThreadResultData();
		DataList<List<Province>> result = Tools.getThreadDataList();
		result.setTotalRows(provinceDao.count(params));
		result.setData(provinceDao.list(params));
		resultData.setData(result);
		return resultData;
	}

	@ApiOperation(value = "获取省地址", notes = "根据省ID获取省份信息")
	@ApiImplicitParam(name="provinceId", value = "省ID", required = true, dataType = "Integer")
	@GetMapping(Router.V1_JDSYNC_ADDRESS_PROVINCE)
	public ResultData getProvince(@RequestParam Integer provinceId) {
		ResultData<Province> resultData = Tools.getThreadResultData();
		resultData.setData(provinceDao.get(provinceId));
		return resultData;
	}

	@ApiOperation(value = "列出所有市地址", notes = "根据过滤条件获取市地址列表")
	@GetMapping(Router.V1_JDSYNC_ADDRESS_CITYLIST)
	public ResultData getCityList(@RequestParam Map<String, Object> params) {
		ResultData<DataList<List<City>>> resultData = Tools.getThreadResultData();
		DataList<List<City>> result = Tools.getThreadDataList();
		result.setData(cityDao.list(params));
		result.setTotalRows(cityDao.count(params));
		resultData.setData(result);
		return resultData;
	}

	@ApiOperation(value = "获取市地址", notes = "根据市ID获取市信息")
	@GetMapping(Router.V1_JDSYNC_ADDRESS_CITY)
	public ResultData getCity(@RequestParam Integer cityId) {
		ResultData resultData = Tools.getThreadResultData();
		resultData.setData(cityDao.get(cityId));
		return resultData;
	}

	@ApiOperation(value = "列出所有县地址", notes = "根据过滤条件获取县地址列表")
	@GetMapping(Router.V1_JDSYNC_ADDRESS_COUNTYLIST)
	public ResultData getCountyList(@RequestParam Map<String, Object> params) {
		ResultData<DataList<List<County>>> resultData = Tools.getThreadResultData();
		DataList<List<County>> result = Tools.getThreadDataList();
		result.setTotalRows(countyDao.count(params));
		result.setData(countyDao.list(params));
		resultData.setData(result);
		return resultData;
	}

	@ApiOperation(value = "获取县地址", notes = "根据县ID获取县信息")
	@GetMapping(Router.V1_JDSYNC_ADDRESS_COUNTY)
	public ResultData getCounty(@RequestParam Integer countyId) {
		ResultData<County> resultData = Tools.getThreadResultData();
		resultData.setData(countyDao.get(countyId));
		return resultData;
	}

	@ApiOperation(value = "列出所有乡镇地址", notes = "根据过滤条件获取乡镇地址列表")
	@GetMapping(Router.V1_JDSYNC_ADDRESS_TOWNLIST)
	public ResultData getTownList(@RequestParam Map<String, Object> params) {
		ResultData<DataList<List<Town>>> resultData = Tools.getThreadResultData();
		DataList<List<Town>> result = Tools.getThreadDataList();
		result.setTotalRows(townDao.count(params));
		result.setData(townDao.list(params));
		resultData.setData(result);
		return resultData;
	}

	@ApiOperation(value = "获取乡镇地址", notes = "根据乡镇ID获取乡镇信息")
	@GetMapping(Router.V1_JDSYNC_ADDRESS_TOWN)
	public ResultData getTown(@RequestParam Integer townId) {
		ResultData<Town> resultData = Tools.getThreadResultData();
		resultData.setData(townDao.get(townId));
		return resultData;
	}

	@ApiOperation(value = "获取级联地址信息", notes = "获取级联地址信息，如：传入乡镇ID则获取从乡镇到省的级联信息")
	@PostMapping(Router.V1_JDSYNC_ADDRESS_INFO)
	public ResultData getAddressInfo(@RequestBody AddressBean addressBean) {
		ResultData<AddressBean> resultData = Tools.getThreadResultData();
		if(addressBean.getTownId() != 0){
			resultData.setData(addressDao.getTownInfo(addressBean.getTownId()));
		}else if(addressBean.getCountyId() != 0){
			resultData.setData(addressDao.getCountyInfo(addressBean.getCountyId()));
		}else if(addressBean.getCityId() != 0){
			resultData.setData(addressDao.getCityInfo(addressBean.getCityId()));
		}else if(addressBean.getProvinceId() != 0){
			resultData.setData(addressDao.getProvinceInfo(addressBean.getProvinceId()));
		}
		return resultData;
	}
}
