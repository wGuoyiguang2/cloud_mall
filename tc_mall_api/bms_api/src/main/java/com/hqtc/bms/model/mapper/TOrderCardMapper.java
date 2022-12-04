package com.hqtc.bms.model.mapper;

import com.hqtc.bms.model.database.TOrderCardBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wanghaoyang on 18-9-26.
 */
@Repository
public interface TOrderCardMapper {
    @Insert("<script>" +
            " INSERT INTO t_order_card (order_sn, card_no, balance, use_fee, ctime) " +
            " VALUES " +
            " <foreach collection = \"list\" item=\"card\" index=\"index\" separator=\",\"> " +
            " (#{card.order_sn}, #{card.card_no}, #{card.balance}, #{card.use_fee}, now())" +
            " </foreach>" +
            " </script>")
    int batchAddOrderCards(List<TOrderCardBean> orderCardBeans);

    @Select("SELECT * FROM t_order_card WHERE order_sn = #{orderSn}")
    List<TOrderCardBean> getOrderCards(@Param("orderSn")String orderSn);

    @Select("SELECT card_no FROM t_order_card WHERE order_sn = #{orderSn}")
    List<String> getOrderCardNos(@Param("orderSn")String orderSn);

    @Update("<script>" +
            " REPLACE INTO t_order_card (order_sn, card_no, balance, use_fee) " +
            " VALUES " +
            " <foreach collection = \"list\" item=\"card\" index=\"index\" separator=\",\"> " +
            " (#{card.order_sn}, #{card.card_no}, #{card.balance}, #{card.use_fee})" +
            " </foreach>" +
            " </script>")
    int batchUpdateOrderCards(List<TOrderCardBean> orderCardBeans);

    @Update("UPDATE t_order_card set balance = #{balance}, use_fee = #{use_fee} WHERE order_sn = #{order_sn} AND card_no = #{card_no}")
    int updateOrderCard(TOrderCardBean tOrderCardBean);
}
