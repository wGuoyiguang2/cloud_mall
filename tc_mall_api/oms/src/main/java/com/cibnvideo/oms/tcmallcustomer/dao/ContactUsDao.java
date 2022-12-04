package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.ContactUsBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/10 21:09
 */
@Repository
@Mapper
public interface ContactUsDao {
    List<ContactUsBean> getByVenderId(@Param("venderId") Long venderId);

    ContactUsBean getById(@Param("id")Integer id);

    Integer update(ContactUsBean contactUsBean);

    int add(ContactUsBean contactUsBean);
}
