package com.hqtc.ims.address.controller;

import com.alibaba.fastjson.JSON;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import com.hqtc.ims.address.model.bean.AddressBean;
import com.hqtc.ims.address.model.bean.CommonAddressBean;
import com.hqtc.ims.address.model.bean.Result;
import com.hqtc.ims.address.model.bean.TownBean;
import com.hqtc.ims.address.service.AddressJdService;
import com.hqtc.ims.address.service.AddressService;
import com.hqtc.ims.common.constant.PathConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * description:地址管理
 * Created by laiqingchuang on 18-6-26 .
 */
@RestController
public class AddressController {
    @Value("${service.config.addressQrcode}")
    private String addressQrcode;
    @Autowired
    AddressService addressService;
    @Autowired
    AddressJdService addressJdService;

    /**
     * 添加地址
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDADDRESS,method = RequestMethod.POST)
    public ResultData saveAddress(@RequestAttribute("userId") Integer userId,
                                  @RequestParam String name,
                                  @RequestParam String phone,
                                  @RequestParam Integer provinceId,
                                  @RequestParam Integer cityId,
                                  @RequestParam Integer countyId,
                                  @RequestParam(defaultValue = "0") Integer townId,
                                  @RequestParam String detail,
                                  @RequestParam(defaultValue = "") String intro,
                                  @RequestParam(defaultValue = "0") Integer isDefault){
        ResultData result = getThreadResultData();
        //参数校验
        if(name.length() >16){
            result.setError(ErrorCode.PARAM_ERROR);
            result.setMsg("收件人姓名长度不能超过16");
            return result;
        }
        if(phone.length() >32){
            result.setError(ErrorCode.PARAM_ERROR);
            result.setMsg("收件人手机号长度不能超过32");
            return result;
        }
        if(detail.length() >64){
            result.setError(ErrorCode.PARAM_ERROR);
            result.setMsg("详细地址长度不能超过64");
            return result;
        }
        if(intro.length() >32){
            result.setError(ErrorCode.PARAM_ERROR);
            result.setMsg("地址说明长度不能超过32");
            return result;
        }
        if(isDefault >1 || isDefault <0){
            result.setError(ErrorCode.PARAM_ERROR);
            result.setMsg("默认地址参数不合法");
            return result;
        }
        if(townId==0){
            Result<TownBean> data = addressJdService.getTownList(null, null, countyId, null, null).getData();
            if(data!=null && data.getTotalRows() >0){
                result.setError(ErrorCode.PARAM_ERROR);
                result.setMsg("请添加四级地址");
                return result;
            }
        }

        try{
            int row=addressService.saveAddress(userId,name,phone,provinceId,cityId,countyId,townId,detail,intro,isDefault);
            if(row>=10){
                result.setError(ErrorCode.SUCCESS);
                result.setMsg("用户地址数量不能超过10");
                return result;
            }
            if(row==0){
                result.setError(ErrorCode.WRITE_DATA_ERROR);
                result.setMsg("读写数据失败");
                return result;
            }
        }catch(Exception e){
            result.setError(ErrorCode.WRITE_DATA_ERROR);
            result.setMsg("读写数据失败");
        }
        return result;
    }

    /**
     * 地址详情
     * @param
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESSDETAIL,method = RequestMethod.POST)
    public ResultData getAddressById(@RequestParam("venderId") Integer venderId,
                                     @RequestParam Integer id,
                                     @RequestParam("mac1") String mac){
        ResultData result = getThreadResultData();
        AddressBean bean= getAddressDetail(id);
        String url=addressQrcode + "?vender=" + venderId + "&mac=" + mac;
        bean.setUrl(url);
        result.setData(bean);
        return result;
    }

    /**
     * 地址详情（无需登录）
     * @param
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESSDETAIL_NOLOGIN,method = RequestMethod.POST)
    public ResultData getAddressById2(@RequestParam Integer id){
        ResultData result = getThreadResultData();
        AddressBean bean= getAddressDetail(id);
        result.setData(bean);
        return result;
    }

    private AddressBean getAddressDetail(Integer id) {
        AddressBean bean=addressService.getAddressById(id);
        if(bean==null){
            return new AddressBean();
        }
        CommonAddressBean commonBean = new CommonAddressBean();
        commonBean.setCountyId(bean.getCountyId());
        commonBean.setTownId(bean.getTownId());
        commonBean=addressJdService.getCommonAddressById(commonBean).getData();
        if(commonBean !=null){
            bean.setAddre(commonBean.getProvinceName()+commonBean.getCityName()+commonBean.getCountyName()+commonBean.getTownName());
        }
        return bean;
    }

    /**
     * 修改地址
     * @return
     */
    @RequestMapping(value=PathConstants.V1_UPDATEADDRESS,method = RequestMethod.POST)
    public ResultData updateAddress(@RequestParam Integer id,
                                    @RequestAttribute("userId") Integer userId,
                                    @RequestParam String name,
                                    @RequestParam String phone,
                                    @RequestParam Integer provinceId,
                                    @RequestParam Integer cityId,
                                    @RequestParam Integer countyId,
                                    @RequestParam(defaultValue = "0") Integer townId,
                                    @RequestParam String detail,
                                    @RequestParam(defaultValue = "") String intro,
                                    @RequestParam(defaultValue = "0") Integer isDefault){
        ResultData result = getThreadResultData();
        //参数校验
        if(id==0){
            result.setError(ErrorCode.PARAM_ERROR);
            result.setMsg("id应大于0");
            return result;
        }
        if(name.length() >16){
            result.setError(ErrorCode.PARAM_ERROR);
            result.setMsg("收件人姓名长度不能超过16");
            return result;
        }
        if(phone.length() >32){
            result.setError(ErrorCode.PARAM_ERROR);
            result.setMsg("收件人手机号长度不能超过32");
            return result;
        }
        if(detail.length() >64){
            result.setError(ErrorCode.PARAM_ERROR);
            result.setMsg("详细地址长度不能超过64");
            return result;
        }
        if(intro.length() >32){
            result.setError(ErrorCode.PARAM_ERROR);
            result.setMsg("地址说明长度不能超过32");
            return result;
        }
        if(isDefault >1 || isDefault <0){
            result.setError(ErrorCode.PARAM_ERROR);
            result.setMsg("默认地址参数不合法");
            return result;
        }
        if(townId==0){
            Result<TownBean> data = addressJdService.getTownList(null, null, countyId, null, null).getData();
            if(data!=null && data.getTotalRows() >0){
                result.setError(ErrorCode.PARAM_ERROR);
                result.setMsg("请添加四级地址");
                return result;
            }
        }

        try{
            int row=addressService.updateAddress(id,userId,name,phone,provinceId,cityId,countyId,townId,detail,intro,isDefault);
            if(row==0){
                result.setError(ErrorCode.WRITE_DATA_ERROR);
                result.setMsg("读写数据失败");
                return result;
            }
        }catch(Exception e){
            result.setError(ErrorCode.WRITE_DATA_ERROR);
            result.setMsg("读写数据失败");
        }
        return result;
    }

    /**
     * 删除地址
     * @param id
     * @return
     */
    @RequestMapping(value=PathConstants.V1_DELETEADDRESS,method = RequestMethod.POST)
    public ResultData deleteAddress(@RequestAttribute("userId") Integer userId,
                                    @RequestParam(required=false) Integer id){
        ResultData result = getThreadResultData();
        //参数校验
        if(id==null){
            result.setError(ErrorCode.MISS_PARAM);
            result.setMsg("缺少参数");
            return result;
        }
        try{
            int row=addressService.deleteAddress(id);
            if(row==0){
                result.setError(ErrorCode.WRITE_DATA_ERROR);
                result.setMsg("读写数据失败");
                return result;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id",id);
            result.setData(map);
            return result;
        }catch(Exception e){
            result.setError(ErrorCode.WRITE_DATA_ERROR);
            result.setMsg("读写数据失败");
        }
        return result;
    }

    /**
     * 获取地址列表
     * @param userId
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESSLIST,method = RequestMethod.POST)
    public ResultData getAddressList(@RequestAttribute("userId") Integer userId){
        ResultData result = getThreadResultData();
        try{
            List<AddressBean> list=addressService.getAddressList(userId);
            if(list !=null && list.size() !=0){
                for(AddressBean bean: list){
                    CommonAddressBean commonBean = new CommonAddressBean();
                    commonBean.setCountyId(bean.getCountyId());
                    commonBean.setTownId(bean.getTownId());
                    commonBean=addressJdService.getCommonAddressById(commonBean).getData();
                    if(commonBean !=null){
                        bean.setAddre(commonBean.getProvinceName()+commonBean.getCityName()+commonBean.getCountyName()+commonBean.getTownName());
                    }
                }
            }
            result.setData(list);
        }catch(Exception e){
            result.setError(ErrorCode.WRITE_DATA_ERROR);
            result.setMsg("读写数据失败");
        }
        return result;
    }

    /**
     * 设置为默认地址
     * @param userId
     * @return
     */
    @RequestMapping(value=PathConstants.V1_SETDEFAULT,method = RequestMethod.POST)
    public ResultData setDefault(@RequestAttribute("userId") Integer userId,
                                 @RequestParam(required=false) Integer id){
        ResultData result = getThreadResultData();
        //参数校验
        if(id==null){
            result.setError(ErrorCode.MISS_PARAM);
            result.setMsg("缺少参数");
            return result;
        }
        try{
            int row=addressService.setDefault(id,userId);
            if(row==0){
                result.setError(ErrorCode.WRITE_DATA_ERROR);
                result.setMsg("读写数据失败");
                return result;
            }
        }catch(Exception e){
            result.setError(ErrorCode.WRITE_DATA_ERROR);
            result.setMsg("读写数据失败");
        }
        return result;
    }

    /**
     * 获取所有一级地址
     * @param
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESS_PROVINCELIST,method = {RequestMethod.GET, RequestMethod.POST})
    @Cacheable(value = "ims", key = "'province_list'", cacheManager = "ImsCacheManager")
    public ResultData getProvinceList(@RequestParam(required=false) Integer provinceId,
                                      @RequestParam(required=false) String name){
        ResultData result = getThreadResultData();
        try{
            return addressJdService.getProvinceList(provinceId,name);
        }catch(Exception e){
            result.setError(ErrorCode.SERVER_EXCEPTION);
            result.setMsg("服务端发生异常");
        }
        return result;
    }

    /**
     * 获取指定一级地址
     * @param
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESS_PROVINCE,method = RequestMethod.GET)
    public ResultData getProvince(@RequestParam("provinceId") Integer provinceId){
        ResultData result = getThreadResultData();
        //参数校验
        if(provinceId==null){
            result.setError(ErrorCode.MISS_PARAM);
            result.setMsg("缺少参数");
            return result;
        }
        try{
            return addressJdService.getProvince(provinceId);
        }catch(Exception e){
            result.setError(ErrorCode.SERVER_EXCEPTION);
            result.setMsg("服务端发生异常");
        }
        return result;
    }

    /**
     * 获取所有二级地址
     * @param
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESS_CITYLIST,method = RequestMethod.GET)
    @Cacheable(value = "ims", key = "'city_list:provinceId:' +#provinceId",
            cacheManager = "ImsCacheManager")
    public ResultData getCityList(@RequestParam(required=false) Integer provinceId,
                                  @RequestParam(required=false) Integer cityId,
                                  @RequestParam(required=false) String name){
        ResultData result = getThreadResultData();
        try{
            return addressJdService.getCityList(provinceId,cityId,name);
        }catch(Exception e){
            result.setError(ErrorCode.SERVER_EXCEPTION);
            result.setMsg("服务端发生异常");
        }
        return result;
    }

    /**
     * 手机扫码根据一级地址获取二级地址
     * @param params
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESS_CITYLIST,method = RequestMethod.POST)
    public ResultData postCityList(@RequestBody String params){
        ResultData result = getThreadResultData();
        Map<String, Integer> map = (Map) JSON.parse(params);
        if (!map.containsKey("provinceId") || "".equals(map.get("provinceId"))){
            result.setError(ErrorCode.PARAM_ERROR);
            result.setMsg("缺少参数");
            return result;
        }
        try{
            return addressJdService.getCityList(map.get("provinceId"),null,null);
        }catch(Exception e){
            result.setError(ErrorCode.SERVER_EXCEPTION);
            result.setMsg("服务端发生异常");
        }
        return result;
    }

    /**
     * 获取指定二级地址
     * @param
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESS_CITY,method = RequestMethod.GET)
    public ResultData getCity(@RequestParam("cityId") Integer cityId){
        ResultData result = getThreadResultData();
        //参数校验
        if(cityId==null){
            result.setError(ErrorCode.MISS_PARAM);
            result.setMsg("缺少参数");
            return result;
        }
        try{
            return addressJdService.getCity(cityId);
        }catch(Exception e){
            result.setError(ErrorCode.SERVER_EXCEPTION);
            result.setMsg("服务端发生异常");
        }
        return result;
    }

    /**
     * 获取所有三级地址
     * @param
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESS_COUNTYLIST,method = RequestMethod.GET)
    public ResultData getCountyList(@RequestParam(required=false) Integer provinceId,
                                    @RequestParam(required=false) Integer cityId,
                                    @RequestParam(required=false) Integer countyId,
                                    @RequestParam(required=false) String name){
        ResultData result = getThreadResultData();
        try{
            return addressJdService.getCountyList(provinceId,cityId,countyId,name);
        }catch(Exception e){
            result.setError(ErrorCode.SERVER_EXCEPTION);
            result.setMsg("服务端发生异常");
        }
        return result;
    }

    /**
     * 手机扫码根据一二级地址获取三级地址
     * @param params
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESS_COUNTYLIST,method = RequestMethod.POST)
    public ResultData postCountyList(@RequestBody String params){
        ResultData result = getThreadResultData();
        Map<String, Integer> map = (Map) JSON.parse(params);
        if (!map.containsKey("provinceId") || "".equals(map.get("provinceId")) ||
                !map.containsKey("cityId") || "".equals(map.get("cityId"))){
            result.setError(ErrorCode.PARAM_ERROR);
            result.setMsg("缺少参数");
            return result;
        }
        try{
            return addressJdService.getCountyList(map.get("provinceId"),map.get("cityId"),null,null);
        }catch(Exception e){
            result.setError(ErrorCode.SERVER_EXCEPTION);
            result.setMsg("服务端发生异常");
        }
        return result;
    }
    /**
     * 获取指定三级地址
     * @param
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESS_COUNTY,method = RequestMethod.GET)
    public ResultData getCounty(@RequestParam("countyId") Integer countyId){
        ResultData result = getThreadResultData();
        //参数校验
        if(countyId==null){
            result.setError(ErrorCode.MISS_PARAM);
            result.setMsg("缺少参数");
            return result;
        }
        try{
            return addressJdService.getCounty(countyId);
        }catch(Exception e){
            result.setError(ErrorCode.SERVER_EXCEPTION);
            result.setMsg("服务端发生异常");
        }
        return result;
    }

    /**
     * 获取所有四级地址
     * @param
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESS_TOWNLIST,method = RequestMethod.GET)
    public ResultData getTownList(@RequestParam(required=false) Integer provinceId,
                                  @RequestParam(required=false) Integer cityId,
                                  @RequestParam(required=false) Integer countyId,
                                  @RequestParam(required=false) Integer townId,
                                  @RequestParam(required=false) String name){
        ResultData result = getThreadResultData();
        try{
            return addressJdService.getTownList(provinceId,cityId,countyId,townId,name);
        }catch(Exception e){
            result.setError(ErrorCode.SERVER_EXCEPTION);
            result.setMsg("服务端发生异常");
        }
        return result;
    }

    /**
     * 手机扫码根据一二三级地址获取四级地址
     * @param params
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESS_TOWNLIST,method = RequestMethod.POST)
    public ResultData getTownList(@RequestBody String params){
        ResultData result = getThreadResultData();
        Map<String, Integer> map = (Map) JSON.parse(params);
        if (!map.containsKey("provinceId") || "".equals(map.get("provinceId")) ||
                !map.containsKey("cityId") || "".equals(map.get("cityId")) ||
                !map.containsKey("countyId") || "".equals(map.get("countyId"))){
            result.setError(ErrorCode.PARAM_ERROR);
            result.setMsg("缺少参数");
            return result;
        }
        try{
            return addressJdService.getTownList(map.get("provinceId"),map.get("cityId"), map.get("countyId"), null, null);
        }catch(Exception e){
            result.setError(ErrorCode.SERVER_EXCEPTION);
            result.setMsg("服务端发生异常");
        }
        return result;
    }
    /**
     * 获取指定四级地址
     * @param
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESS_TOWN,method = RequestMethod.GET)
    public ResultData getTown(@RequestParam("townId") Integer townId){
        ResultData result = getThreadResultData();
        //参数校验
        if(townId==null){
            result.setError(ErrorCode.MISS_PARAM);
            result.setMsg("缺少参数");
            return result;
        }
        try{
            return addressJdService.getTown(townId);
        }catch(Exception e){
            result.setError(ErrorCode.SERVER_EXCEPTION);
            result.setMsg("服务端发生异常");
        }
        return result;
    }

    /**
     * 获取级联地址信息（公共方法）
     * @param
     * @return
     */
    @RequestMapping(value=PathConstants.V1_ADDRESS_INFO,method = RequestMethod.GET)
    public ResultData getCommonAddressById(CommonAddressBean bean){
        ResultData result = getThreadResultData();
        try{
            return addressJdService.getCommonAddressById(bean);
        }catch(Exception e){
            result.setError(ErrorCode.SERVER_EXCEPTION);
            result.setMsg("服务端发生异常");
        }
        return result;
    }

    /**
     * 获取新增地址二维码
     * @param
     * @return
     */
    @GetMapping(PathConstants.ROUTE_GET_ADDRESS_QRCODE)
    public ResultData addressQrcode(@RequestParam("venderId") Integer venderId,
                                    @RequestParam("mac1") String mac){
        ResultData<String> resultData= Tools.getThreadResultData();
        String url=addressQrcode + "?vender=" + venderId + "&mac=" + mac;
        resultData.setData(url);
        return resultData;
    }

}