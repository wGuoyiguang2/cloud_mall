package com.cibnvideo.thirdpart.model.mapper;

import com.cibnvideo.thirdpart.model.bean.JDSitePriceBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/21 17:23
 */
@Repository
@Mapper
public interface JDSitePriceMapper {
    @Insert("<script> replace into t_jdsite_price(sku,price,ctime) values" +
            " <foreach collection = \"list\" item=\"bean\" index=\"index\" separator=\",\"> " +
            " (#{bean.sku},#{bean.price},now()) " +
            " </foreach>" +
            "</script>")
    int add(@Param("list") List<JDSitePriceBean> jdSitePriceBeanList);
    @Select("<script> select sku,price from t_jdsite_price where sku in " +
            "<foreach item=\"sku\" collection=\"list\" open=\"(\" separator=\",\" close=\")\"> #{sku} </foreach>" +
            "</script>")
    List<JDSitePriceBean> listJDSitePrice(@Param("list") List<Long> skuList);
}
