package com.hqtc.ims.address.service;

import com.hqtc.ims.address.model.bean.AddressBean;
import java.util.List;

/**
 * description:地址管理sevice
 * Created by laiqingchuang on 18-6-26 .
 */
public interface AddressService {

    /**
     * 新增地址
     * @return
     */
    int saveAddress(Integer userId,String name,String phone,Integer provinceId,Integer cityId,Integer countyId,Integer townId,String detail,String intro,Integer isDefault);

    /**
     * 地址详情
     * @param id
     * @return
     */
    AddressBean getAddressById(Integer id);

    /**
     * 修改地址
     * @return
     */
    int updateAddress(Integer id,Integer userId,String name,String phone,Integer provinceId,Integer cityId,Integer countyId,Integer townId,String detail,String intro,Integer isDefault);

    /**
     * 删除地址
     * @return
     */
    int deleteAddress(Integer id);

    /**
     * 获取地址列表
     * @param userId
     * @return
     */
    List<AddressBean> getAddressList(Integer userId);

    /**
     * 设置为默认地址
     * @param id
     * @return
     */
    int setDefault(Integer id,Integer userId);
}
