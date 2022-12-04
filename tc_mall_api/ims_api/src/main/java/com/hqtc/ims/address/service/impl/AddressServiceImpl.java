package com.hqtc.ims.address.service.impl;

import com.hqtc.ims.address.model.bean.AddressBean;
import com.hqtc.ims.address.model.mapper.AddressMapper;
import com.hqtc.ims.address.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * description:地址管理impl
 * Created by laiqingchuang on 18-6-26 .
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressMapper addressMapper;

    /**
     * 新增地址
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveAddress(Integer userId, String name, String phone,Integer provinceId,Integer cityId,Integer countyId,Integer townId, String detail, String intro,Integer isDefault) {
        //用户地址数量不能超过10 判断
        int count= addressMapper.getAddressCount(userId);
        if(count >=10){
            return count;
        }
        if(isDefault.equals(1)){
            int num=addressMapper.updateAllnotDefault(userId);
        }
        int row=addressMapper.saveAddress(userId,name,phone,provinceId,cityId,countyId,townId,detail,intro,isDefault);
        return row;
    }

    /**
     * 地址详情
     * @param id
     * @return
     */
    @Override
    public AddressBean getAddressById(Integer id) {
        return addressMapper.getAddressById(id);
    }

    /**
     * 修改地址
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAddress(Integer id, Integer userId, String name, String phone,Integer provinceId,Integer cityId,Integer countyId,Integer townId, String detail, String intro,Integer isDefault) {
        if(isDefault.equals(1)){
            int num=addressMapper.updateAllnotDefault(userId);
            if(num==0){
                return num;
            }
        }
        int row=addressMapper.updateAddress(id,userId,name,phone,provinceId,cityId,countyId,townId,detail,intro,isDefault);
        return row;
    }

    /**
     * 删除地址
     * @param id
     * @return
     */
    @Override
    public int deleteAddress(Integer id) {
        int row=addressMapper.deleteAddress(id);
        return row;
    }

    /**
     * 获取地址列表
     * @param userId
     * @return
     */
    @Override
    public List<AddressBean> getAddressList(Integer userId) {
        return addressMapper.getAddressList(userId);
    }

    /**
     * 设置为默认地址
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int setDefault(Integer id,Integer userId) {
        int a=addressMapper.updateAllnotDefault(userId);
        int row=addressMapper.setDefault(id);
        return row;
    }
}
