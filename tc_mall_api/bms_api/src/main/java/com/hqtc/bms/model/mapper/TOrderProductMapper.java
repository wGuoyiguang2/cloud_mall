package com.hqtc.bms.model.mapper;

import com.hqtc.bms.model.database.TOrderProductBean;
import com.hqtc.bms.model.params.SalesAmountVo;
import com.hqtc.bms.model.params.SalesManagerVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-5.
 */
@Repository
public interface TOrderProductMapper {

//    @Insert("INSERT INTO t_order_product (order_sn, product_id, name, agree_price, price, ctime, taxrate, utime) " +
//            " VALUES " +
//            " (#{order_sn}, #{product_id}, #{name}, #{agree_price}, #{price}, #{ctime}, #{taxrate}, now())")
//    int add(TOrderProductBean orderProductBean);

    @Insert("INSERT INTO t_order_product (order_sn, child_trade_no, product_id, name, agree_price, price, pay_price, count, taxrate, order_state, ctime, utime) " +
            " VALUES " +
            " (#{order_sn}, #{child_trade_no}, #{product_id}, #{name}, #{agree_price}, #{price}, #{pay_price}, #{count}, #{taxrate}, #{order_state}, #{ctime}, now())")
    int add(TOrderProductBean orderProductBean);

    @Select("SELECT * FROM t_order_product WHERE order_sn = #{orderSn}")
    List<TOrderProductBean> getOrderDetailByOrderSn(@Param("orderSn")String orderSn);

    @Select("SELECT * FROM t_order_product WHERE order_sn IN (${orderSn})")
    List<TOrderProductBean> getOrderDetails(@Param("orderSn")String orderSn);

    @Select("select product_id, name, price, agree_price, order_state, child_trade_no, SUM(`count`) as `count` from t_order_product where order_sn = #{orderSn} group by product_id")
    List<Map<String, Object>> getProductCount(@Param("orderSn")String orderSn);


    @Select("SELECT * FROM t_order_product WHERE order_sn =#{orderSn} AND product_id=#{sku}")
    List<TOrderProductBean> getOrderProductDetails(@Param("orderSn")String orderSn, @Param("sku")long sku);

    @Select("select *,count(product_id) as total from t_order_product where order_sn = #{orderSn} group by product_id")
    List<Map<String, Object>> getProductCount2(@Param("orderSn")String orderSn);

    @Update("UPDATE t_order_product SET child_trade_no=#{childTradeNo}, order_state = 3 WHERE order_sn=#{orderSn} AND product_id IN (${products})")
    int updateChildTradeNo(@Param("orderSn")String orderSn, @Param("childTradeNo")String childTradeNo, @Param("products")String products);

    @Select("SELECT " +
            "   product_id as productId ,SUM(`count`) as total " +
            "FROM " +
            "   t_order_product as p " +
            "INNER JOIN " +
            "   t_order as o " +
            "ON " +
            "   p.order_sn = o.order_sn " +
            "WHERE " +
            "   p.product_id IN (${products}) " +
            "AND " +
            "   o.pay_status = 1  group by product_id")
    List<Map<String, Object>> getProductSalesVolumn(@Param("products")String products);

    @Select("SELECT " +
            "   product_id as productId ,SUM(`count`) as total " +
            "FROM " +
            "   t_order_product as p " +
            "INNER JOIN " +
            "   t_order as o " +
            "ON " +
            "   p.order_sn = o.order_sn " +
            "WHERE " +
            "   p.product_id IN (${products}) " +
            "AND " +
            "   o.venderId=#{venderId} " +
            "AND " +
            "   o.pay_status = 1  group by product_id")
    List<Map<String, Object>> getVenderProductSalesVolumn(@Param("products")String products, @Param("venderId")int venderId);
    @Select("SELECT CONVERT(sum(a.payPrice - IFNULL(b.refundPrice, 0)),decimal(10,2)) AS salesAmount,CONVERT(sum(a.payPrice - IFNULL(b.refundPrice, 0)-a.agreePrice * (a.count - IFNULL(b.count, 0))),decimal(10,2))     AS gainsAmount " +
            "FROM (SELECT p.product_id AS productId,p.`name` AS productName,IFNULL(sum((IFNULL(o.pay_price,0)+IFNULL(o.card_price,0)) * p.price * p.count / o.price),0) AS payPrice,IFNULL(p.agree_price, 0) AS agreePrice,sum(p.count) AS count " +
            " FROM t_order_product p LEFT JOIN t_order o ON p.order_sn = o.order_sn WHERE o.pay_status = 1 AND o.venderid = #{venderId} GROUP BY p.product_id) a " +
            " LEFT JOIN (SELECT product_id AS productId,sum(count) AS count,sum(refund_price) AS refundPrice FROM t_order_refund r WHERE refund_status = 1 GROUP BY product_id) b ON a.productId = b.productId where (a.count - IFNULL(b.count, 0))>0")
    SalesAmountVo getSalesAmount(@Param("venderId") Long venderId);

    @Select("<script> SELECT a.productId AS productId,a.productName AS productName,a.productPrice * (a.count - IFNULL(b.count, 0)) AS productPrice,a.payPrice - IFNULL(b.refundPrice, 0) AS payPrice,a.agreePrice * (a.count - IFNULL(b.count, 0)) AS agreePrice,a.count - IFNULL(b.count, 0) AS count  " +
            "FROM (SELECT p.product_id AS productId,p.`name` AS productName,IFNULL(p.price, 0) AS productPrice,IFNULL(sum((IFNULL(o.pay_price,0)+IFNULL(o.card_price,0)) * p.price * p.count / o.price),0) AS payPrice,IFNULL(p.agree_price, 0) AS agreePrice,sum(p.count) AS count FROM t_order_product p LEFT JOIN t_order o ON p.order_sn = o.order_sn" +
            "<where>  o.pay_status=1 " +
            "<if test=\"venderId != null and venderId!=''\"> and o.venderid=#{venderId} </if>"+
            "<if test=\"productName != null and productName!=''\"> and p.name like CONCAT('%',#{productName},'%') </if>"+
            "<if test=\"productId != null and productId!=''\"> and p.product_id=#{productId} </if>"+
            "</where>" +
            " GROUP BY p.product_id " +
            " )a " +
            "LEFT JOIN ( SELECT product_id AS productId, sum(count) AS count, sum(refund_price) AS refundPrice FROM t_order_refund r WHERE refund_status = 1 GROUP BY product_id ) b ON a.productId = b.productId " +
            " where (a.count - IFNULL(b.count, 0))>0"+
            " ORDER BY (a.count - IFNULL(b.count, 0)) DESC "+
            "<if test='offset != null and limit != null'> limit ${offset}, ${limit} </if> </script>" )
    List<SalesManagerVo> salesManagerList(Map<String, Object> params);

    @Select("<script> SELECT count(1)  " +
            "FROM (SELECT p.product_id AS productId,p.`name` AS productName,IFNULL(p.price, 0) AS productPrice,IFNULL(sum(((o.pay_price+o.card_price) - o.freight) * p.price * p.count / o.price),0) AS payPrice,IFNULL(p.agree_price, 0) AS agreePrice,sum(p.count) AS count FROM t_order_product p LEFT JOIN t_order o ON p.order_sn = o.order_sn" +
            "<where>  o.pay_status=1 " +
            "<if test=\"venderId != null and venderId!=''\"> and o.venderid=#{venderId} </if>"+
            "<if test=\"productName != null and productName!=''\"> and p.name like CONCAT('%',#{productName},'%') </if>"+
            "<if test=\"productId != null and productId!=''\"> and p.product_id=#{productId} </if>"+
            "</where>" +
            " GROUP BY p.product_id " +
            " )a " +
            "LEFT JOIN ( SELECT product_id AS productId, sum(count) AS count, sum(refund_price) AS refundPrice FROM t_order_refund r WHERE refund_status = 1 GROUP BY product_id ) b ON a.productId = b.productId " +
            " where (a.count - IFNULL(b.count, 0))>0 </script>")
    int countSalesManager(Map<String, Object> params);

    @Select("SELECT p.product_id AS productId,p.`name` AS productName,IFNULL(sum(p.price),0) AS productPrice,(IFNULL((o.pay_price+o.card_price),0)-IFNULL(o.freight,0))*p.price/o.price as payPrice,IFNULL(sum(p.agree_price),0) AS agreePrice,count(1) AS count FROM t_order_product p LEFT JOIN t_order o ON p.order_sn = o.order_sn where p.product_id=#{sku}")
    SalesManagerVo getSalesManagerVo(@Param("sku") Long sku);

    @Select("SELECT * FROM t_order_product WHERE child_trade_no = #{jdOrderId} AND order_state>2")
    List<TOrderProductBean> getOrderProductByJdOrderId(@Param("jdOrderId")String jdOrderId);

    @Select("SELECT child_trade_no FROM t_order_product WHERE order_sn = #{orderSn} AND order_state<4")
    List<TOrderProductBean> getUnFinishedOrder(@Param("orderSn")String orderSn);

    @Insert("<script>" +
            " INSERT INTO t_order_product (order_sn, child_trade_no, product_id, name, agree_price, price, pay_price, count, taxrate, order_state, ctime, utime) " +
            " VALUES " +
            " <foreach collection = \"list\" item=\"product\" index=\"index\" separator=\",\"> " +
            " (#{product.order_sn}, #{product.child_trade_no}, #{product.product_id}, #{product.name}, " +
            " #{product.agree_price}, #{product.price}, #{product.pay_price}, #{product.count}, " +
            " #{product.taxrate}, #{product.order_state}, now(), now()) " +
            " </foreach>" +
            " </script>")
    int batchAddProduct(List<TOrderProductBean> orderRefundBeans);

    @Select("SELECT product_id FROM t_order_product WHERE order_sn = #{orderSn}")
    List<String> getOrderProduct(@Param("orderSn")String orderSn);
}
