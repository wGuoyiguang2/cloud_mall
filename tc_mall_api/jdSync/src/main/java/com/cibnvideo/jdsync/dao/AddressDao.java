package com.cibnvideo.jdsync.dao;

import com.cibnvideo.jdsync.bean.AddressBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressDao {

    AddressBean getTownInfo(Integer townId);

    AddressBean getCountyInfo(Integer countyId);

    AddressBean getCityInfo(Integer cityId);

    AddressBean getProvinceInfo(Integer provinceId);
}
