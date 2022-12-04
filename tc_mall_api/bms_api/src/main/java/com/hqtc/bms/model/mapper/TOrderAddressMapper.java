package com.hqtc.bms.model.mapper;

import com.hqtc.bms.model.database.TOrderAddressBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by wanghaoyang on 18-7-12.
 */
@Repository
public interface TOrderAddressMapper {

    @Insert("INSERT INTO `t_order_address` " +
            " (order_id, province_id, city_id, county_id, town_id, name, phone, detail, ctime) " +
            " VALUES " +
            " (#{order_id}, #{province_id}, #{city_id}, #{county_id}, #{town_id}, #{name}, #{phone}, #{detail}, #{ctime})")
    int insert(TOrderAddressBean tOrderAddressBean);

    @Select("SELECT * FROM `t_order_address` WHERE order_id = #{id} ")
    TOrderAddressBean findById(@Param("id")int id);
}
