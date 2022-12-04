package com.hqtc.bms.model.mapper;

import com.hqtc.bms.model.database.TCardUserRecordBean;
import com.hqtc.bms.model.params.CardUseLogBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wanghaoyang on 18-9-20.
 */
@Repository
public interface TCardUserRecordMapper {

    @Insert("INSERT INTO t_card_user_record (card_no, operate_type, user_id, order_sn, refund_no, use_fee, ctime) " +
            " VALUES " +
            " (#{card_no}, #{operate_type}, #{user_id}, #{order_sn}, #{refund_no}, #{use_fee}, #{ctime})")
    int addRecord(TCardUserRecordBean tCardUserRecordBean);

    @Select("SELECT * FROM t_card_user_record WHERE card_no = #{cardNo}")
    List<TCardUserRecordBean> getCardUseLog(@Param("cardNo") String cardNo);

    @Select("SELECT card_no as cardNo, operate_type as operateType, order_sn as orderSn, refund_no as refundNo, " +
            " use_fee as useFee, DATE_FORMAT(ctime,'%Y-%m-%d %H:%i:%s') as ctime FROM t_card_user_record WHERE card_no = #{cardNo} " +
            " ORDER BY ctime DESC LIMIT ${start}, ${size}")
    List<CardUseLogBean> getCardUseLogLimit(@Param("cardNo")String cardNo,
                                            @Param("start")int start,
                                            @Param("size")int size);

    @Insert("<script>" +
            " INSERT INTO t_card_user_record (card_no, operate_type, user_id, order_sn, refund_no, use_fee, ctime) " +
            " VALUES " +
            " <foreach collection = \"list\" item=\"cardRecord\" index=\"index\" separator=\",\"> " +
            " (#{cardRecord.card_no}, #{cardRecord.operate_type}, #{cardRecord.user_id}" +
            ", #{cardRecord.order_sn}, #{cardRecord.refund_no}, #{cardRecord.use_fee}, now()) " +
            " </foreach>" +
            " </script>")
    int batchAddUserCardRecord(List<TCardUserRecordBean> tCardUserRecordBeans);

    @Select("SELECT IFNULL(SUM(use_fee), 0) FROM t_card_user_record WHERE card_no = #{cardNo} AND order_sn=#{orderSn} AND operate_type=3")
    BigDecimal getCardRefundFee(@Param("cardNo")String cardNo, @Param("orderSn")String orderSn);

    @Select("SELECT IFNULL(SUM(use_fee), 0) FROM t_card_user_record WHERE order_sn=#{orderSn} AND operate_type=3")
    BigDecimal getOrderCardRefundFee(@Param("orderSn")String orderSn);
}
