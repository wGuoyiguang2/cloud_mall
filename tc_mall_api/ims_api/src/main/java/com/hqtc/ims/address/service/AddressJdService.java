package com.hqtc.ims.address.service;

import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import com.hqtc.ims.address.fallback.AddressJdFallbackFactory;
import com.hqtc.ims.address.model.bean.*;
import com.hqtc.ims.common.constant.PathConstants;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description:四级地址
 * Created by laiqingchuang on 18-7-9 .
 */
@FeignClient(name=FeignClientService.JDSYNC,fallbackFactory = AddressJdFallbackFactory.class)
public interface AddressJdService {
    /**
     * 获取所有一级地址
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value =PathConstants.V1_JD_ADDRESS_PROVINCELIST)
    ResultData<Result<ProvinceBean>> getProvinceList(@RequestParam("provinceId") Integer provinceId,
                                                     @RequestParam("name") String name);

    /**
     * 获取指定一级地址
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value =PathConstants.V1_JD_ADDRESS_PROVINCE)
    ResultData<ProvinceBean> getProvince(@RequestParam("provinceId") Integer provinceId);

    /**
     * 获取所有二级地址
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value =PathConstants.V1_JD_ADDRESS_CITYLIST)
    ResultData<Result<CityBean>> getCityList(@RequestParam("provinceId") Integer provinceId,
                                                 @RequestParam("cityId") Integer cityId,
                                                 @RequestParam("name") String name);

    /**
     * 获取指定二级地址
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value =PathConstants.V1_JD_ADDRESS_CITY)
    ResultData<CityBean> getCity(@RequestParam("cityId") Integer cityId);


    /**
     * 获取所有三级地址
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value =PathConstants.V1_JD_ADDRESS_COUNTYLIST)
    ResultData<Result<CountyBean>> getCountyList(@RequestParam("provinceId") Integer provinceId,
                                                 @RequestParam("cityId") Integer cityId,
                                                 @RequestParam("countyId") Integer countyId,
                                                 @RequestParam("name") String name);

    /**
     * 获取指定三级地
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value =PathConstants.V1_JD_ADDRESS_COUNTY)
    ResultData<CountyBean> getCounty(@RequestParam("countyId") Integer countyId);

    /**
     * 获取所有四级地址
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value =PathConstants.V1_JD_ADDRESS_TOWNLIST)
    ResultData<Result<TownBean>> getTownList(@RequestParam("provinceId") Integer provinceId,
                                             @RequestParam("cityId") Integer cityId,
                                             @RequestParam("countyId") Integer countyId,
                                             @RequestParam("townId") Integer townId,
                                             @RequestParam("name") String name);


    /**
     * 获取指定四级地址
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value =PathConstants.V1_JD_ADDRESS_TOWN)
    ResultData<TownBean> getTown(@RequestParam("townId") Integer townId);

    /**
     * 获取级联地址信息
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value =PathConstants.V1_JD_ADDRESS_INFO)
    ResultData<CommonAddressBean> getCommonAddressById(@RequestBody CommonAddressBean bean);

}
