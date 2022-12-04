package com.hqtc.bms.model.mapper;

import com.hqtc.bms.model.params.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * description:售后物流
 * Created by laiqingchuang on 18-7-25 .
 */
@Repository
public interface AfterSaleTaskMapper {
    /**
     * 获取待收货子订单
     */
    @Select("SELECT order_sn orderSn,child_trade_no childTradeNo,order_state orderState FROM t_order_product where order_state=3 and child_trade_no is not null" +
            " and DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= ctime order by ctime desc")
    List<OrderProductBean> getOrderProducBeanList();

    /**
     * 获取订单信息
     */
    @Select("SELECT b.child_trade_no jdOrderId,b.product_id skuId,c.name,c.phone,c.province_id provinceId,c.city_id cityId,c.county_id countyId," +
            "c.town_id townId,c.detail FROM t_order a LEFT JOIN t_order_product b ON a.order_sn = b.order_sn LEFT JOIN t_order_address c ON a.id = c.order_id" +
            " where b.child_trade_no=#{jdOrderId} limit 1")
    OrderInfoResponseParams getOrderInfo(@Param("jdOrderId") Long jdOrderId);

    /**
     * 获取商品信息
     */
    @Select("SELECT a.order_sn orderSn,a.name productName,a.price,b.venderid,b.user_id userId FROM t_order_product a LEFT JOIN t_order b ON a.order_sn = b.order_sn " +
            " where a.child_trade_no=#{bean.childTradeNo} and a.product_id=#{bean.productId} limit 1")
    OrderAftersaleBean getProductInfo(@Param("bean") OrderAftersaleBean bean);

    /**
     * 添加售后流水信息
     */
    @Insert("insert into t_order_aftersale set venderid=#{bean.venderid},user_id=#{bean.userId},order_sn=#{bean.orderSn},child_trade_no=#{bean.childTradeNo},service_no=#{bean.serviceNo},name=#{bean.name}," +
            "phone=#{bean.phone},product_id=#{bean.productId},product_name=#{bean.productName},price=#{bean.price},count=#{bean.count},service_type=#{bean.serviceType}," +
            "reason=#{bean.reason},back_type=#{bean.backType},return_type=#{bean.returnType},back_addr=#{bean.backAddr},return_addr=#{bean.returnAddr},status=#{bean.status}"+
            ",remarks=#{bean.remarks},ctime=now(),utime=now()")
    int addOrderAftersale(@Param("bean") OrderAftersaleBean bean);

    @Update("update t_order_aftersale set status=50 where status not in(50,60) and DATEDIFF(date_format(now(), '%Y-%m-%d'),date_format(utime, '%Y-%m-%d'))>20")
    int updateCompleteServices();

    /**
     * 获取需要更新的记录
     */
    @Select("select service_no serviceNo from t_order_aftersale where service_no is not null and status not in (20,40,50,60)")
    List<Integer> getNeedUpdateList();

    /**
     * 根据订单号获取售后列表
     */
    @Select("<script> select order_sn orderSn,child_trade_no childTradeNo,service_no serviceNo,name,phone,product_id productId,product_name productName,price,count,service_type " +
            "serviceType,reason,back_type backType,return_type returnType,back_addr backAddr,return_addr returnAddr,status,remarks,ctime,utime from t_order_aftersale " +
            "where order_sn=#{orderSn} " +
            "<if test=\"type != null and type ==1\"> and status <![CDATA[ < ]]> 40 and status !=20 </if>"+
            "<if test=\"type != null and type ==2\"> and status in (40,50) </if>"+
            "<if test=\"type != null and type ==3\"> and status in (20) </if>"+
            " and status !=60"+
            " order by ctime desc" +
            "</script>")
    List<OrderAftersaleBean> getAftersaleListByOrderSn(@Param("orderSn") String orderSn,@Param("type") Integer type);

    /**
     * 根据用户id获取售后数量
     */
    @Select("select count(1) from t_order_aftersale where user_id=#{userId}")
    int getAftersaleCountByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户id获取售后列表
     */
    @Select("<script> SELECT a.order_sn orderSn,a.child_trade_no childTradeNo,a.service_no serviceNo,a.name,a.product_id productId,a.product_name productName," +
            "a.price,a.count,a.service_type serviceType,a.status,b.ctime FROM t_order_aftersale a inner join t_order b on a.order_sn=b.order_sn" +
            " where a.user_id=#{userId} order by a.ctime desc " +
            "<if test='offset != null and limit != null'> limit ${offset}, ${limit} </if>" +
            "</script> ")
    List<AftersaleBean> getAftersaleListByUserId(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 获取退款服务单
     */
    @Select("select order_sn orderSn,venderid,reason,product_id productId,count,service_no serviceNo from t_order_aftersale where service_no in (${serviceNos}) and service_type=10")
    List<OrderAftersaleBean> getServices(@Param("serviceNos") String serviceNos);

    /**
     * 查询需要开发票的订单(通过子订单号)
     */
    @Select("select distinct b.order_sn orderSn,b.user_id userId from t_order_product a inner join t_order b on a.order_sn=b.order_sn where a.child_trade_no in(${childTradeNos})" +
            " and b.invoice=1")
    List<OrderMainBean> getMainBeanList(@Param("childTradeNos") String childTradeNos);

    /**
     * 查询需要开发票的订单(通过子服务单号)
     */
    @Select("select distinct b.order_sn orderSn, b.user_id userId from t_order_aftersale a inner join t_order b on a.order_sn=b.order_sn where a.service_no in(${serviceNos}) and " +
            " a.service_type=10 and a.status=40 and b.invoice=1")
    List<OrderMainBean> getMainBeanList2(@Param("serviceNos") String serviceNos);

    /**
     * 获取订单售后最大序号
     */
    @Select("select ifnull(max(seq),0) maxSeq from t_order_aftersale where child_trade_no=#{childTradeNo}")
    int getTbMaxSeq(@Param("childTradeNo") String childTradeNo);

    /**
     * 获取需要更新的服务单
     */
    @Select("select child_trade_no childTradeNo,seq from t_order_aftersale where service_no is null and seq>0 and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= ctime order by ctime desc")
    List<OrderAftersaleBean> getUpdateAftersaleList();

    /**
     * 根据JD订单号获取售后列表
     */
    @Select("<script> select child_trade_no jdOrderId,service_no serviceNo,name,phone,product_id productId,product_name productName," +
            "price,count,service_type serviceType,reason,back_type backType,return_type returnType,back_addr backAddr," +
            "return_addr returnAddr,status,remarks,ctime,utime from t_order_aftersale " +
            "where child_trade_no=#{jdOrderId} " +
            "<if test=\"type != null and type ==1\"> and status <![CDATA[ < ]]> 40 and status !=20 </if>"+
            "<if test=\"type != null and type ==2\"> and status in (40,50) </if>"+
            "<if test=\"type != null and type ==3\"> and status in (20,60) </if>"+
            " order by ctime desc" +
            "</script>")
    List<AftersaleForeignBean> getAftersaleListByJdOrderId(@Param("jdOrderId") Long jdOrderId,@Param("type") Integer type);

    /**
     * 校验是否是该用户下的订单
     */
    @Select("select count(1) from t_order a left join t_order_product b on a.order_sn=b.order_sn where a.user_id=#{userId} and b.child_trade_no=#{jdOrderId}")
    int getJdOrderIdCount(@Param("userId") Integer userId,@Param("jdOrderId") Long jdOrderId);

}
