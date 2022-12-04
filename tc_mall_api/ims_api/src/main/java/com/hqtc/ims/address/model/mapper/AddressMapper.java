package com.hqtc.ims.address.model.mapper;

import com.hqtc.ims.address.model.bean.AddressBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * description:地址管理mapper
 * Created by laiqingchuang on 18-6-26 .
 */
@Repository
public interface AddressMapper {

    /**
     * 用户地址数量不能超过10 判断
     * @param
     */
    @Select("select count(1) from t_address where user_id=#{userId}")
    int getAddressCount(@Param("userId") Integer userId);

    /**
     * 设置非默认地址
     * @param userId
     */
    @Update("update t_address set is_default=0 where user_id=#{userId}")
    int updateAllnotDefault(@Param("userId") Integer userId);

    /**
     * 保存地址
     * @param
     */
    @Insert("insert into t_address set user_id=#{userId},name=#{name},phone=#{phone},province_id=#{provinceId},city_id=#{cityId},county_id=#{countyId}," +
            "town_id=#{townId},detail=#{detail},is_default=#{isDefault},intro=#{intro},ctime=NOW(),utime=NOW()")
    int saveAddress(@Param("userId") Integer userId,
                     @Param("name") String name,
                     @Param("phone") String phone,
                     @Param("provinceId") Integer provinceId,
                     @Param("cityId") Integer cityId,
                     @Param("countyId") Integer countyId,
                     @Param("townId") Integer townId,
                     @Param("detail") String detail,
                     @Param("intro") String intro,
                     @Param("isDefault") Integer isDefault);

    /**
     * 地址详情
     * @param id
     * @return
     */
    @Select("select id,user_id as userId,name,phone,province_id as provinceId,city_id as cityId,county_id as countyId,town_id as townId,detail,is_default as isDefault,intro,ctime,utime from t_address where id=#{id}")
    AddressBean getAddressById(@Param("id") Integer id);

    /**
     * 修改地址
     * @param
     */
    @Update("update t_address set user_id=#{userId},name=#{name},phone=#{phone},province_id=#{provinceId},city_id=#{cityId},county_id=#{countyId},town_id=#{townId}," +
            "detail=#{detail},intro=#{intro},is_default=#{isDefault},utime=NOW() where id=#{id}")
    int updateAddress(@Param("id") Integer id,
                       @Param("userId") Integer userId,
                       @Param("name") String name,
                       @Param("phone") String phone,
                       @Param("provinceId") Integer provinceId,
                       @Param("cityId") Integer cityId,
                       @Param("countyId") Integer countyId,
                       @Param("townId") Integer townId,
                       @Param("detail") String detail,
                       @Param("intro") String intro,
                       @Param("isDefault") Integer isDefault);

    /**
     * 删除地址
     * @param id
     */
    @Delete("delete from t_address where id=#{id}")
    int deleteAddress(@Param("id") Integer id);

    /**
     * 获取地址列表
     * @param userId
     * @return
     */
    @Select("select id,user_id as userId,name,phone,province_id as provinceId,city_id as cityId,county_id as countyId,town_id as townId,detail," +
            "is_default as isDefault,intro,ctime,utime from t_address where user_id=#{userId} order by is_default desc,utime desc")
    List<AddressBean> getAddressList(@Param("userId") Integer userId);

    /**
     * 设置为默认地址
     * @param id
     */
    @Update("update t_address set is_default=1 where id=#{id}")
    int setDefault(@Param("id") Integer id);
}