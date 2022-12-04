package com.hqtc.bms.model.mapper;

import com.hqtc.bms.model.database.TOrderBean;
import com.hqtc.bms.model.params.OrderManagerVo;
import com.hqtc.bms.model.response.OrderRefundVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-4.
 */
@Repository
public interface TOrderMapper {

    @Insert("INSERT INTO `t_order`" +
            " (venderid, user_id, order_sn, agree_price, price, pay_price, pay_status, order_state, ctime, utime" +
            ", pay_type, pay_order_sn, trade_no, freight, jd_freight, invoice) " +
            " VALUES " +
            " (#{venderid}, #{user_id}, #{order_sn}, #{agree_price}, #{price}, #{pay_price}, #{pay_status}, #{order_state}," +
            " #{ctime}, #{utime}, #{pay_type}, #{pay_order_sn}, #{trade_no}, #{freight}, #{jd_freight}, #{invoice})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addOrder(TOrderBean tOrderBean);

    @Select("SELECT * FROM t_order WHERE order_sn = #{orderSn}")
    TOrderBean getOrderInfoByOrderSn(@Param("orderSn")String orderSn);

    @Select("SELECT * FROM t_order WHERE order_sn IN (${orderSn})")
    List<TOrderBean> getOrderInfo(@Param("orderSn")String orderSn);

    @Update("UPDATE `t_order` SET " +
            " pay_price= #{pay_price}, pay_status = #{pay_status}, utime=#{utime}, pay_type= #{pay_type}" +
            ", pay_order_sn = #{pay_order_sn} , order_state = #{order_state}, card_price = #{card_price}" +
            " WHERE order_sn = #{order_sn}")
    int updateOrderStatus(TOrderBean tOrderBean);

    @Update("UPDATE `t_order` SET " +
            " trade_no = #{trade_no}, order_state=#{order_state}, price=#{price} " +
            " WHERE order_sn = #{order_sn} ")
    int updateJdOrderStatus(TOrderBean tOrderBean);

    @Select("SELECT * FROM t_order WHERE trade_no = #{tradeNo} limit 1")
    TOrderBean getOrderInfoByTradeNo(@Param("tradeNo")String tradeNo);

    @Select("SELECT * FROM t_order WHERE user_id = #{userId} AND status =1 AND order_state IN (${orderState}) ORDER BY id DESC LIMIT #{page}, #{size}")
    List<TOrderBean> findStateOrderByUserId(@Param("userId")int userId, @Param("orderState")String orderState, @Param("page")int page, @Param("size")int size);

    @Select("SELECT * FROM t_order WHERE user_id = #{userId} AND status = 1 ORDER BY id DESC LIMIT #{page}, #{size}")
    List<TOrderBean> findAllOrderByUserId(@Param("userId")int userId, @Param("page")int page, @Param("size")int size);

    @Update("UPDATE t_order SET freight = #{freight} WHERE order_sn = #{orderSn}")
    int updateFreight(@Param("freight")BigDecimal freight, @Param("orderSn")String orderSn);
    @Select("<script> SELECT t.orderId as orderId,t.agreePrice as agreePrice,t.venderFreight as venderFreight,t.address as address,t.venderId AS venderId,t.tradeNo AS tradeNo,t.parentId AS parentId,t.orderNo AS orderNo,t.payNo AS payNo,t.productId AS productId,t.productName AS productName,t.productPrice AS productPrice,t.payPrice AS payPrice,t.cardPrice as cardPrice,t.payStatus AS payStatus,t.orderState AS orderState,t.createTime AS createTime " +
            "FROM (" +
            "(SELECT null as orderId,sum(p.agree_price*p.count) as agreePrice,NULL as venderFreight,o.venderid AS venderId,o.trade_no AS tradeNo,p.order_sn AS parentId,p.child_trade_no AS orderNo,o.pay_order_sn AS payNo,GROUP_CONCAT(p.product_id SEPARATOR '; ') AS productId,GROUP_CONCAT(CONCAT(p.`name`,' × ',p.count) SEPARATOR '; ') AS productName,sum(p.price*p.count) AS productPrice,p.pay_price AS payPrice,null as cardPrice,o.pay_status AS payStatus,p.order_state AS orderState,CONCAT(CONCAT('收货人：',a. NAME,'; '),CONCAT('联系电话：',a.phone,'; '),CONCAT('收货地址：',a.detail,'; ')) AS address,DATE_FORMAT(o.ctime,'%Y-%m-%d %H:%i:%s') AS createTime "+
            "FROM ("+
            "SELECT o.order_sn AS orderNo "+
            "FROM t_order o LEFT JOIN t_order_address a ON o.id = a.order_id LEFT JOIN t_order_product p ON p.order_sn = o.order_sn "+
            "<where>"+
            "<if test=\"venderid != null and venderid!=''\"> and o.venderid=#{venderid} </if>"+
            "<if test=\"orderState != null and orderState!=''\"> and o.order_state=#{orderState} </if>"+
            "<if test=\"orderNo != null and orderNo!=''\"> and o.order_sn=#{orderNo} </if>"+
            "<if test=\"payNo != null and payNo!=''\"> and o.pay_order_sn=#{payNo} </if>"+
            "<if test=\"productId != null and productId!=''\"> and p.product_id=#{productId} </if>"+
            "<if test=\"payStatus != null and payStatus!=''\"> and o.pay_status=#{payStatus} </if>"+
            "</where> "+
            " GROUP BY o.order_sn ORDER BY o.ctime desc,o.id desc "+
            "<if test='offset != null and limit != null'> limit ${offset}, ${limit} </if>"+
            ")temp "+
            "LEFT JOIN t_order_product p ON p.order_sn = temp.orderNo LEFT JOIN t_order o ON o.order_sn = p.order_sn LEFT JOIN t_order_address a ON o.id = a.order_id where p.order_sn is not null GROUP BY p.child_trade_no,p.order_sn) "+
            " UNION ALL "+
            " (SELECT o.id as orderId,o.agree_price as agreePrice,o.freight as venderFreight,o.venderid AS venderId,o.trade_no AS tradeNo,'0' AS parentId,o.order_sn AS orderNo,o.pay_order_sn AS payNo,'-' AS productName,'-' AS productId,o.price AS productPrice,IFNULL(o.pay_price,0) AS payPrice,IFNULL(o.card_price,0) as cardPrice,o.pay_status AS payStatus,o.order_state AS orderState,CONCAT(CONCAT('收货人：',a. NAME,'; '),CONCAT('联系电话：',a.phone,'; '),CONCAT('收货地址：',a.detail,'; ')) AS address,DATE_FORMAT(o.ctime,'%Y-%m-%d %H:%i:%s') AS createTime "+
            "FROM t_order o LEFT JOIN t_order_address a ON o.id = a.order_id LEFT JOIN t_order_product p ON p.order_sn = o.order_sn "+
            "<where>"+
            "<if test=\"venderid != null and venderid!=''\"> and o.venderid=#{venderid} </if>"+
            "<if test=\"orderState != null and orderState!=''\"> and o.order_state=#{orderState} </if>"+
            "<if test=\"orderNo != null and orderNo!=''\"> and o.order_sn=#{orderNo} </if>"+
            "<if test=\"payNo != null and payNo!=''\"> and o.pay_order_sn=#{payNo} </if>"+
            "<if test=\"productId != null and productId!=''\"> and p.product_id=#{productId} </if>"+
            "<if test=\"payStatus != null and payStatus!=''\"> and o.pay_status=#{payStatus} </if>"+
            "</where> "+
            " GROUP BY o.order_sn ORDER BY o.ctime desc,o.id desc "+
            "<if test='offset != null and limit != null'> limit ${offset}, ${limit} </if>)"+
            ") t order by t.createTime desc</script>")
    List<OrderManagerVo> orderManagerList(Map<String, Object> params);
    @Select("<script> select count(1) from (SELECT count(1)  " +
            "FROM t_order o LEFT JOIN t_order_address a ON o.id = a.order_id LEFT JOIN t_order_product p ON p.order_sn = o.order_sn "+
            "<where>"+
            "<if test=\"venderid != null and venderid!=''\"> and o.venderid=#{venderid} </if>"+
            "<if test=\"orderState != null and orderState!=''\"> and o.order_state=#{orderState} </if>"+
            "<if test=\"orderNo != null and orderNo!=''\"> and o.order_sn=#{orderNo} </if>"+
            "<if test=\"payNo != null and payNo!=''\"> and o.pay_order_sn=#{payNo} </if>"+
            "<if test=\"productId != null and productId!=''\"> and p.product_id=#{productId} </if>"+
            "<if test=\"payStatus != null and payStatus!=''\"> and o.pay_status=#{payStatus} </if>"+
            "</where> "+
            "  GROUP BY o.order_sn )a </script>")
    int countManagerOrderList(Map<String, Object> params);

    @Update("UPDATE t_order SET order_state = #{orderState} WHERE trade_no = #{tradeNo}")
    int updateOrderStateByTradeNo(@Param("orderState")int orderState, @Param("tradeNo")String tradeNo);

    @Update("UPDATE t_order SET order_state = #{orderState} WHERE order_sn = #{orderSn}")
    int updateOrderStateByOrderSn(@Param("orderState")int orderState, @Param("orderSn")String orderSn);

    @Select("select count(1) from t_order where venderid=#{venderId}")
    int countOrderByVenderId(@Param("venderId") Long venderId);

    @Select("<script> SELECT o.order_sn " +
            " FROM t_order o LEFT JOIN t_order_product p ON o.order_sn = p.order_sn " +
            "<where> o.order_state=4" +
            "<if test=\"venderid != null and venderid!=''\"> and o.venderid=#{venderid} </if>"+
            "<if test=\"productId != null and productId!=''\"> and p.name like CONCAT('%',#{productName},'%') </if>"+
            "<if test=\"productId != null and productId!=''\"> and p.product_id=#{productId} </if>"+
            "</where> "+
            " GROUP BY o.order_sn order by o.ctime desc "+
            "<if test='offset != null and limit != null'> limit ${offset}, ${limit} </if>"+
            "</script>")
    List<String> getProductOrderNo(Map<String, Object> params);
    @Select("<script> select count(1) from (select count(1) from t_order o left join t_order_product p on p.order_sn=o.order_sn " +
            "<where> o.order_state=4 " +
            "<if test=\"venderid != null and venderid!=''\"> and o.venderid=#{venderid} </if>"+
            "<if test=\"productId != null and productId!=''\"> and p.name like CONCAT('%',#{productName},'%') </if>"+
            "<if test=\"productId != null and productId!=''\"> and p.product_id=#{productId} </if>"+
            "</where> "+
            " GROUP BY o.order_sn ) a </script>")
    int countProductOrderNos(Map<String, Object> params);

    @Select("SELECT order_sn FROM t_order WHERE pay_status = 0 AND order_state = 1 AND ctime < #{ctime} LIMIT ${count}")
    List<String> getUnPayedOrderSnLimit(@Param("ctime")String ctime, @Param("count")int count);

    @Update("UPDATE t_order SET status = 0 WHERE user_id = #{userId} AND order_sn IN (${orderSn})")
    int deleteOrderByUserIdAndOrderSn(@Param("userId")int userId, @Param("orderSn")String orderSn);

    @Select("SELECT * FROM t_order WHERE user_id = #{userId} AND order_sn = #{orderSn}")
    TOrderBean getUserOrderDetail(@Param("orderSn")String orderSn,@Param("userId")int userId);
    @Select("<script> " +
            "select count(*) from (SELECT count(*) FROM t_order_refund v left join t_order_product p on p.order_sn=v.order_sn LEFT JOIN t_order o ON o.order_sn = p.order_sn " +
            "<where> o.venderid = #{venderId} " +
            "<if test=\"orderSn != null and orderSn!=''\"> and p.order_sn=#{orderSn} </if>"+
            "<if test=\"productId != null and productId!=''\"> and v.product_id=#{productId} </if>"+
            "</where>" +
            "GROUP BY o.order_sn)a" +
            "</script>")
    int countOrderRefund(Map<String, Object> params);
    @Select("<script>" +
            "SELECT '0' as parentId,p.order_sn AS orderSn,NULL AS unqId,o.trade_no AS tradeNo,o.pay_price AS payPrice,o.card_price AS cardPrice,o.pay_order_sn AS payOrderSn,NULL AS productId,NULL AS productName,o.price AS price,NULL AS refundStatus,null AS failedReason " +
            "FROM t_order_refund v left join t_order_product p on p.order_sn=v.order_sn " +
            "LEFT JOIN t_order o ON o.order_sn = p.order_sn " +
            "<where> o.venderid=#{venderId}" +
            "<if test=\"orderSn != null and orderSn!=''\"> and p.order_sn=#{orderSn} </if>"+
            "<if test=\"productId != null and productId!=''\"> and v.product_id=#{productId} </if>"+
            "</where>" +
            "GROUP BY o.order_sn " +
            "<if test='offset != null and limit != null'> limit ${offset}, ${limit} </if>"+
            "UNION ALL " +
            " SELECT p.order_sn as parentId,null AS orderSn,b.unqId AS unqId,NULL AS tradeNo,NULL AS payPrice,NULL AS cardPrice,NULL AS payOrderSn,group_concat(distinct p.product_id) AS productId,group_concat(distinct p. NAME) AS productName,p.price AS price,min(b.refundStatus) AS refundStatus,max(b.failedReason) AS failedReason " +
            " FROM (SELECT p.order_sn AS orderSn " +
            "FROM t_order_refund v left join t_order_product p on p.order_sn=v.order_sn " +
            "LEFT JOIN t_order o ON o.order_sn = p.order_sn " +
            "<where> o.venderid=#{venderId}" +
            "<if test=\"orderSn != null and orderSn!=''\"> and p.order_sn=#{orderSn} </if>"+
            "<if test=\"productId != null and productId!=''\"> and v.product_id=#{productId} </if>"+
            "</where>" +
            "GROUP BY o.order_sn " +
            "<if test='offset != null and limit != null'> limit ${offset}, ${limit} </if>"+
            "  ) temp " +
            " LEFT JOIN t_order_product p ON p.order_sn = temp.orderSn " +
            " LEFT JOIN (" +
            "SELECT r.order_sn AS orderSn,r.product_id AS productId,v.refund_status &amp; r.refund_status AS refundStatus,r.unq_id AS unqId,CASE WHEN v.refund_status = 0 THEN '退款至大客户失败' WHEN r.refund_status = 0 THEN '退款至用户失败' ELSE '退款成功' END AS failedReason " +
            "FROM t_order_vender_refund v LEFT JOIN t_order_refund r ON v.order_sn = r.order_sn" +
            " WHERE v.venderid =  #{venderId} " +
            "<where>" +
            "<if test=\"productId != null and productId!=''\"> and r.productId=#{productId} </if>"+
            "</where>" +
            " ) b ON p.order_sn = b.orderSn " +
            "WHERE p.product_id = b.productId" +
            " group by b.unqId,b.orderSn</script>")
    List<OrderRefundVo> listOrderRefund(Map<String, Object> params);
}
