package com.hqtc.ims.address.fallback;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.ims.address.model.bean.CommonAddressBean;
import com.hqtc.ims.address.model.bean.ProvinceBean;
import com.hqtc.ims.address.model.bean.Result;
import com.hqtc.ims.address.service.AddressJdService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * description:四级地址容错
 * Created by laiqingchuang on 18-7-9 .
 */
@Component
public class AddressJdFallbackFactory implements FallbackFactory<AddressJdService> {
    private Logger logger  = LoggerFactory.getLogger(getClass());

    @Override
    public AddressJdService create(Throwable throwable) {
        return new AddressJdService() {
            /**
             * 获取所有一级地址
             * @param provinceId
             * @param name
             * @return
             */
            @Override
            public ResultData getProvinceList(Integer provinceId, String name) {
                return errorResponse();
            }

            /**
             * 获取指定一级地址
             * @param provinceId@return
             */
            @Override
            public ResultData getProvince(Integer provinceId) {
                return errorResponse();
            }

            /**
             * 获取所有二级地址
             * @param provinceId
             * @param cityId
             * @param name
             * @return
             */
            @Override
            public ResultData getCityList(Integer provinceId, Integer cityId, String name) {
                return errorResponse();
            }

            /**
             * 获取指定二级地址
             * @param cityId@return
             */
            @Override
            public ResultData getCity(Integer cityId) {
                return errorResponse();
            }

            /**
             * 获取所有三级地址
             * @param provinceId
             * @param cityId
             * @param countyId
             * @param name
             * @return
             */
            @Override
            public ResultData getCountyList(Integer provinceId, Integer cityId, Integer countyId, String name) {
                return errorResponse();
            }

            /**
             * 获取指定三级地
             * @param countyId@return
             */
            @Override
            public ResultData getCounty(Integer countyId) {
                return errorResponse();
            }

            /**
             * 获取所有四级地址
             * @param provinceId
             * @param cityId
             * @param countyId
             * @param townId
             * @param name
             * @return
             */
            @Override
            public ResultData getTownList(Integer provinceId, Integer cityId, Integer countyId, Integer townId, String name) {
                return errorResponse();
            }

            /**
             * 获取指定四级地址
             * @param townId@return
             */
            @Override
            public ResultData getTown(Integer townId) {
                return errorResponse();
            }


            /**
             * 获取级联地址信息
             * @return
             */
            @Override
            public ResultData getCommonAddressById(CommonAddressBean bean) {
                return errorResponse();
            }
        };
    }

    private ResultData<Result<ProvinceBean>> errorResponse() {
        ResultData result = getThreadResultData();
        result.setMsg("FeignClient Request JDSYNC Failed");
        result.setError(ErrorCode.SERVER_EXCEPTION);
        return result;
    }
}