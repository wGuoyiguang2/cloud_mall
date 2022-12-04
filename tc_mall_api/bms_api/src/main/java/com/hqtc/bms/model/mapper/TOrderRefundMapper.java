package com.hqtc.bms.model.mapper;

import com.hqtc.bms.model.database.TOrderRefundBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-26.
 */
@Repository
public interface TOrderRefundMapper {

    @Insert("INSERT into t_order_refund (venderid, user_id, order_sn, product_id, name, count, refund_price, pay_type," +
            " pay_order_sn, refund_no, refund_status, ctime, utime) VALUES " +
            " (#{venderid}, #{user_id}, #{order_sn}, #{product_id}, #{name}, #{count}, #{refund_price}, #{pay_type}, " +
            " #{pay_order_sn}, #{refund_no}, #{refund_status}, now(), now()) ")
    int addRefundOrder(TOrderRefundBean tOrderRefundBean);

//    @Insert("<script>" +
//            " INSERT INTO t_order_refund (venderid, user_id, order_sn, product_id, name, count, refund_price, pay_type," +
//            " pay_order_sn, refund_no, refund_status, ctime, utime, unq_id) " +
//            " VALUES " +
//            " <foreach collection = \"list\" item=\"refundOrder\" index=\"index\" separator=\",\"> " +
//            " (#{refundOrder.venderid}, #{refundOrder.user_id}, #{refundOrder.order_sn}, #{refundOrder.product_id}, " +
//            " #{refundOrder.name}, #{refundOrder.count}, #{refundOrder.refund_price}, #{refundOrder.pay_type}, " +
//            " #{refundOrder.pay_order_sn}, #{refundOrder.refund_no}, #{refundOrder.refund_status}, now(), now()" +
//            " , #{refundOrder.unq_id}) " +
//            " </foreach>" +
//            " </script>")
//    int batchAddRefundOrder(List<TOrderRefundBean> orderRefundBeans);// TODO 删掉

    @Insert("<script>" +
            " INSERT INTO t_order_refund (venderid, user_id, order_sn, product_id, name, count, refund_price," +
            " card_refund_price, pay_type," +
            " pay_order_sn, refund_no, refund_status, ctime, utime, unq_id) " +
            " VALUES " +
            " <foreach collection = \"list\" item=\"refundOrder\" index=\"index\" separator=\",\"> " +
            " (#{refundOrder.venderid}, #{refundOrder.user_id}, #{refundOrder.order_sn}, #{refundOrder.product_id}, " +
            " #{refundOrder.name}, #{refundOrder.count}, #{refundOrder.refund_price}, #{refundOrder.card_refund_price}" +
            " ,#{refundOrder.pay_type}, " +
            " #{refundOrder.pay_order_sn}, #{refundOrder.refund_no}, #{refundOrder.refund_status}, now(), now()" +
            " , #{refundOrder.unq_id}) " +
            " </foreach>" +
            " </script>")
    int batchAddRefundOrder(List<TOrderRefundBean> orderRefundBeans);

    @Select("SELECT * FROM t_order_refund WHERE order_sn=#{orderSn} AND product_id=#{productId} AND refund_status=1")
    List<TOrderRefundBean> findreFundByOrderSnAndProductId(@Param("orderSn")String orderSn, @Param("productId")long productId);

    @Update("UPDATE t_order_refund SET refund_order_sn= #{refundTradeNo}, refund_status=1 WHERE refund_no " +
            " =#{refundNo} AND refund_status=0")
    int updateRefundState(@Param("refundTradeNo")String refundTradeNo, @Param("refundNo")String refundNo);

    @Select("SELECT SUM(`count`) FROM t_order_refund WHERE order_sn=#{orderSn} AND product_id=#{productId} AND refund_status=1")
    Integer getRefundedProductSize(@Param("orderSn")String orderSn, @Param("productId")long productId);

    @Select("SELECT * FROM t_order_refund WHERE refund_no =#{refundNo} AND refund_order_sn= #{refundTradeNo}")
    List<TOrderRefundBean> getRefundInfo(@Param("refundTradeNo")String refundTradeNo, @Param("refundNo")String refundNo);

    @Select("SELECT * FROM t_order_refund WHERE order_sn = #{orderSn} AND unq_id = #{eventId}")
    List<TOrderRefundBean> getEventRefundInfo(@Param("orderSn")String orderSn, @Param("eventId")String eventId);

    @Select("SELECT " +
            "   product_id as productId ,SUM(`count`) as total " +
            "FROM " +
            "   t_order_refund  " +
            "WHERE " +
            "   product_id IN (${products}) " +
            "AND " +
            "   venderId=#{venderId} " +
            "AND " +
            "   refund_status = 1  group by product_id")
    List<Map<String, Object>> getRefundProductSalesVolumn(@Param("products")String products, @Param("venderId")int venderId);

    @Select("SELECT * FROM t_order_refund WHERE order_sn = #{orderSn} AND refund_no = #{refundNo}")
    List<TOrderRefundBean> getOrderRefundByOrderSnAndRefundNo(@Param("orderSn")String orderSn, @Param("refundNo")String refundNo);

    @Select("SELECT * FROM t_order_refund WHERE refund_no = #{refundNo}")
    List<TOrderRefundBean> getOrderRefundByRefundNo(@Param("refundNo")String refundNo);
}
