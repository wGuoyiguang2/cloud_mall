package com.hqtc.bms.model.mapper;

import com.hqtc.bms.model.response.AfterSaleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/2/17 14:20
 */
@Mapper
@Repository
public interface AfterSaleMapper {
    @Select("<script> SELECT count(*) FROM t_order_aftersale " +
            "<where> venderid=#{venderid}" +
            "<if test=\"userId != null and userId!=''\"> and user_id=#{userId} </if>"+
            "<if test=\"orderSn != null and orderSn!=''\"> and order_sn=#{orderSn} </if>"+
            "<if test=\"childTradeNo != null and childTradeNo!=''\"> and child_trade_no=#{childTradeNo} </if>"+
            "</where> "+
            "</script>")
    int countAfterSaleManager(Map<String, Object> params);
    @Select("<script> SELECT id,user_id as userId,order_sn as orderSn,child_trade_no as childTradeNo,seq,service_no as serviceNo,name,phone,product_id as productId,product_name as productName,price,count,service_type as serviceType,reason,back_type as backType,return_type as returnType,back_addr as backAddr,return_addr as returnAddr,status,remarks,ctime,utime FROM t_order_aftersale " +
            "<where> venderid=#{venderid}" +
            "<if test=\"userId != null and userId!=''\"> and user_id=#{userId} </if>"+
            "<if test=\"orderSn != null and orderSn!=''\"> and order_sn=#{orderSn} </if>"+
            "<if test=\"childTradeNo != null and childTradeNo!=''\"> and child_trade_no=#{childTradeNo} </if>"+
            "</where> order by ctime desc "+
            "<if test='offset != null and limit != null'> limit ${offset}, ${limit} </if>"+
            "</script>")
    List<AfterSaleVo> afterSaleManagerList(Map<String, Object> params);
}
