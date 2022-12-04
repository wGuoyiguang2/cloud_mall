package com.hqtc.bms.model.mapper;

import com.hqtc.bms.model.database.TOrderVenderRefundBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by wanghaoyang on 18-8-28.
 */
@Repository
public interface TOrderVenderRefundMapper {

    @Insert("INSERT INTO t_order_vender_refund (venderid, order_sn, refund_price, refund_status, unq_id, ctime) " +
            " VALUES " +
            "(#{venderid}, #{order_sn}, #{refund_price}, #{refund_status}, #{unq_id}, #{ctime})")
    int addVenderRefund(TOrderVenderRefundBean tOrderVenderRefundBean);

    @Select("SELECT * FROM t_order_vender_refund WHERE order_sn = #{orderSn} AND unq_id = #{eventId}")
    TOrderVenderRefundBean getVenderRefund(@Param("orderSn")String orderSn, @Param("eventId")String eventId);

    @Update("UPDATE t_order_vender_refund SET refund_status = 1 WHERE order_sn = #{orderSn} AND unq_id = #{eventId}")
    int updateVenderRefundStatus(@Param("orderSn")String orderSn, @Param("eventId")String eventId);
}
