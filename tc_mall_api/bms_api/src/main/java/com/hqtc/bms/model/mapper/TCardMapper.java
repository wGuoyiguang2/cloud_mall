package com.hqtc.bms.model.mapper;

import com.hqtc.bms.model.database.TCardBean;
import com.hqtc.bms.model.params.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-9-20.
 */
@Repository
public interface TCardMapper {

    @Select("SELECT * FROM t_card WHERE card_no = #{cardNo}")
    TCardBean getCardInfoByCardNo(@Param("cardNo")String cardNo);

    @Select("SELECT * FROM t_card WHERE card_no = #{cardNo} FOR UPDATE")
    TCardBean getCardInfoByCardNoLock(@Param("cardNo")String cardNo);

    @Select("SELECT * FROM t_card WHERE card_no IN (${cardNo})")
    List<TCardBean> getCardInfoByCardNos(@Param("cardNo")String cardNo);

    @Select("SELECT * FROM t_card WHERE passwd = #{passWord} FOR UPDATE")
    TCardBean getCardInfoByPassWord(@Param("passWord")String passWord);

    @Update("UPDATE t_card SET bind_user = #{userId}, bind_type=2 WHERE id = #{id}")
    int bindCard(@Param("userId")int userId, @Param("id")int id);

    @Select("SELECT card_no as cardNo, face_value as faceValue, balance, DATE_FORMAT(etime,'%Y-%m-%d %H:%i:%s') as expireTime, status as cardState " +
            " FROM t_card WHERE bind_user = #{userId} ORDER BY balance DESC ")
    List<CardSimpleInfoBean> getAllMyCards(@Param("userId")int userId);

    @Update("UPDATE t_card SET balance = balance - #{currentFee} WHERE id =#{id}")
    int updateBalance(@Param("currentFee")BigDecimal currentFee, @Param("id")int id);

    @Select("SELECT SUM(balance) FROM t_card WHERE card_no IN (${cardNos})")
    BigDecimal getTotalBalance(@Param("cardNos")String cardNos);

    @Select("SELECT * FROM t_card WHERE passwd = #{passWord}")
    TCardBean getSimpleCardInfoByPassWord(@Param("passWord")String passWord);

    //购物卡后台操作 wangbin
    @Select("<script> SELECT b.id as id,b.count * b.face_value AS sumMoney,b.bind_vender as bindVender,sum(c.balance) AS balance,sum(c.face_value-c.balance) AS usedMoney,count(c.bind_user) as bindCount,b.count-count(c.bind_user) as unBindCount,count(IF(c.status=1,1,NULL)) as activedCount,b.venderid as venderid,b.batch_no as batchNo,b.card_name as cardName,b.card_type as cardType,b.count as count,b.face_value as faceValue,b.company as company,b.create_type as createType,DATE_FORMAT(b.ctime,'%Y-%m-%d %H:%i:%s') AS cTime,DATE_FORMAT(b.stime,'%Y-%m-%d %H:%i:%s') AS sTime,DATE_FORMAT(b.etime,'%Y-%m-%d %H:%i:%s') AS eTime " +
            " FROM t_card_batch b left join t_card c on b.batch_no=c.batch_no " +
            "<where>" +
            "<if test=\"venderId != null and venderId!=''\"> and b.venderid=#{venderId} </if>"+
            "<if test=\"batchNo != null and batchNo!=''\"> and b.batch_no=#{batchNo} </if>"+
            "<if test=\"cardName != null and cardName!=''\"> and b.card_name like CONCAT('%',#{cardName},'%') </if>"+
            "<if test=\"cardType != null and cardType!=''\"> and b.card_type=#{cardType} </if>"+
            "<if test=\"createType != null and createType!=''\"> and b.create_type=#{createType} </if>"+
            "<if test=\"bindVender != null and bindVender!=''\"> and b.bind_vender=#{bindVender} </if>"+
            "<if test=\"stime != null and stime!=''\"> and b.stime &gt;= #{stime} </if>"+
            "<if test=\"etime != null and etime!=''\"> and b.etime &lt;= #{etime} </if>"+
            "<if test=\"startTime != null and startTime != ''\"> and b.ctime &gt;= #{startTime} </if>"+
            "<if test=\"endTime != null and endTime != ''\"> and b.ctime &lt;= #{endTime} </if>"+
            "<if test=\"faceValue != null and faceValue!=''\"> and  b.face_value= #{faceValue} </if>"+
            "</where> group by b.batch_no order by b.ctime desc"+
            "<if test='offset != null and limit != null'> limit ${offset}, ${limit} </if>"+
            "</script>")
    List<CardBatchVo> listCardBatch(Map<String, Object> map);
    @Select("<script> SELECT count(1) FROM t_card_batch " +
            "<where>" +
            "<if test=\"venderId != null and venderId!=''\"> and venderid=#{venderId} </if>"+
            "<if test=\"batchNo != null and batchNo!=''\"> and batch_no=#{batchNo} </if>"+
            "<if test=\"cardName != null and cardName!=''\"> and card_name like CONCAT('%',#{cardName},'%') </if>"+
            "<if test=\"cardType != null and cardType!=''\"> and card_type=#{cardType} </if>"+
            "<if test=\"createType != null and createType!=''\"> and create_type=#{createType} </if>"+
            "<if test=\"cardType != null and cardType!=''\"> and card_type=#{cardType} </if>"+
            "<if test=\"bindVender != null and bindVender!=''\"> and bind_vender=#{bindVender} </if>"+
            "<if test=\"stime != null and stime!=''\"> and stime &gt;= #{stime} </if>"+
            "<if test=\"etime != null and etime!=''\"> and etime &lt;= #{etime} </if>"+
            "<if test=\"startTime != null and startTime != ''\"> and ctime &gt;= #{startTime} </if>"+
            "<if test=\"endTime != null and endTime != ''\"> and ctime &lt;= #{endTime} </if>"+
            "<if test=\"sfaceValue != null and sfaceValue!=''\"> and face_value &gt;= #{sfaceValue} </if>"+
            "<if test=\"efaceValue != null and efaceValue!=''\"> and face_value &lt;= #{efaceValue} </if>"+
            "</where> "+
            "</script>")
    int countCardBatch(Map<String, Object> map);
    @Select("<script> SELECT id,batch_no as batchNo,venderid,card_no as cardNo,passwd,face_value as faceValue,balance,bind_user as bindUser,case when bind_vender=0 then '否' when bind_vender=1 then '是' end as bindVender,case when bind_type=1 then '手动绑定' when bind_type=2 then '自动绑定' end as bindType,case when status=0 then '未激活' when status=1 then '已激活' when status=2 then '已暂停' when status=3 then '已废弃' end as status ,DATE_FORMAT(ctime,'%Y-%m-%d %H:%i:%s') AS cTime,DATE_FORMAT(stime,'%Y-%m-%d %H:%i:%s') AS sTime,DATE_FORMAT(etime,'%Y-%m-%d %H:%i:%s') AS eTime FROM t_card " +
            "<where>" +
            "<if test=\"status != null and status!=''\"> and status=#{status} </if>"+
            "<if test=\"cardNo != null and cardNo!=''\"> and card_no=#{cardNo} </if>"+
            "<if test=\"cardNoStart != null and cardNoStart!=''\"> and CAST(card_no AS Decimal(24)) &gt;= CAST(#{cardNoStart} AS Decimal(24)) </if>"+
            "<if test=\"cardNoEnd != null and cardNoEnd!=''\"> and CAST(card_no AS Decimal(24)) &lt;= CAST(#{cardNoEnd} AS Decimal(24)) </if>"+
            "<if test=\"batchNo != null and batchNo!=''\"> and batch_no=#{batchNo} </if>"+
            "<if test=\"bindVender != null and bindVender!=''\"> and bind_vender=#{bindVender} </if>"+
            "<if test=\"bindUser != null and bindUser!=''\"> and bind_user=#{bindUser} </if>"+
            "<if test=\"startTime != null and startTime != ''\"> and ctime &gt;= #{startTime} </if>"+
            "<if test=\"endTime != null and endTime != ''\"> and ctime &lt;= #{endTime} </if>"+
            "</where> order by cardNo asc "+
            "<if test='offset != null and limit != null'> limit ${offset}, ${limit} </if>"+
            "</script>")
    List<CardVo> listCard(Map<String, Object> map);
    @Select("<script> SELECT count(1) FROM t_card " +
            "<where>" +
            "<if test=\"status != null and status!=''\"> and status=#{status} </if>"+
            "<if test=\"cardNo != null and cardNo!=''\"> and card_no=#{cardNo} </if>"+
            "<if test=\"cardNoStart != null and cardNoStart!=''\"> and CAST(card_no AS Decimal(24)) &gt;= CAST(#{cardNoStart} AS Decimal(24)) </if>"+
            "<if test=\"cardNoEnd != null and cardNoEnd!=''\"> and CAST(card_no AS Decimal(24)) &lt;= CAST(#{cardNoEnd} AS Decimal(24)) </if>"+
            "<if test=\"batchNo != null and batchNo!=''\"> and batch_no=#{batchNo} </if>"+
            "<if test=\"bindVender != null and bindVender!=''\"> and bind_vender=#{bindVender} </if>"+
            "<if test=\"bindUser != null and bindUser!=''\"> and bind_user=#{bindUser} </if>"+
            "<if test=\"startTime != null and startTime != ''\"> and ctime &gt;= #{startTime} </if>"+
            "<if test=\"endTime != null and endTime != ''\"> and ctime &lt;= #{endTime} </if>"+
            "</where> " +
            "</script>")
    int countCard(Map<String, Object> map);
    @Insert("insert into t_card_batch set venderid=#{venderId},batch_no=#{batchNo},card_name=#{cardName},card_type=#{cardType},count=#{count},bind_vender=#{bindVender},face_value=#{faceValue},company=#{company},create_type=#{createType},ctime=#{ctime},stime=#{stime},etime=#{etime}")
    int addCardBatch(Map<String, Object> map);
    @Insert("<script>" +
            " insert into t_card(venderid,batch_no,card_no,passwd,face_value,balance,bind_user,bind_type,bind_vender,status,ctime,stime,etime) " +
            " VALUES " +
            " <foreach collection = \"list\" item=\"c\" index=\"index\" separator=\",\"> " +
            " (#{c.venderid}, #{c.batchNo}, #{c.cardNo}, #{c.passwd}, " +
            " #{c.faceValue}, #{c.balance}, #{c.bindUser}, #{c.bindType},#{c.bindVender}, " +
            " #{c.status}, #{c.ctime}, #{c.stime}, #{c.etime}) " +
            " </foreach>" +
            " </script>")
    int addCard(List<CardVo> cardList);
    @Insert("insert into t_card_admin_record set batch_no=#{batchNo},operator=#{operator},operate_type=#{operateType},intro=#{intro},ctime=#{ctime}")
    int addRecord(Map<String, Object> map);
    @Select("<script>" +
            "select id,operator,operate_type as operateType,DATE_FORMAT(ctime,'%Y-%m-%d %H:%i:%s') AS cTime from t_card_admin_record " +
            "<where>" +
            "<if test=\"operateType != null and operateType!=''\"> and operate_type=#{operateType} </if>"+
            "<if test=\"batchFlag==1\"> and batch_no is not null </if>"+
            "<if test=\"batchFlag==2\"> and batch_no is null </if>"+
            "<if test=\"batchNo != null and batchNo!=''\"> and batch_no=#{batchNo} </if>"+
            "<if test=\"startTime != null and startTime != ''\"> and ctime &gt;= #{startTime} </if>"+
            "<if test=\"endTime != null and endTime != ''\"> and ctime &lt;= #{endTime} </if>"+
            "</where>  order by ctime desc "+
            "<if test='offset != null and limit != null'> limit ${offset}, ${limit} </if>"+
            " </script>")
    List<CardAdminRecord> listCardBatchAdminRecord(Map<String, Object> map);
    @Select("<script>" +
            "select count(*) from t_card_admin_record " +
            "<where>" +
            "<if test=\"operateType != null and operateType!=''\"> and operate_type=#{operateType} </if>"+
            "<if test=\"batchFlag==1\"> and batch_no is not null </if>"+
            "<if test=\"batchFlag==2\"> and batch_no is null </if>"+
            "<if test=\"batchNo != null and batchNo!=''\"> and batch_no=#{batchNo} </if>"+
            "<if test=\"startTime != null and startTime != ''\"> and ctime &gt;= #{startTime} </if>"+
            "<if test=\"endTime != null and endTime != ''\"> and ctime &lt;= #{endTime} </if>"+
            "</where> "+
            " </script>")
    int countCardBatchAdminRecord(Map<String, Object> map);
    @Select("select (select operator from t_card_admin_record r inner join t_card_batch b on b.batch_no=r.batch_no where b.id = #{id} and r.operate_type=1) as operator,id as id,venderid as venderid,batch_no as batchNo,card_name as cardName,card_type as cardType,count as count,face_value as faceValue,company as company,create_type as createType,DATE_FORMAT(ctime,'%Y-%m-%d %H:%i:%s') AS cTime,DATE_FORMAT(stime,'%Y-%m-%d %H:%i:%s') AS sTime,DATE_FORMAT(etime,'%Y-%m-%d %H:%i:%s') AS eTime  from t_card_batch where id=#{id}")
    CardBatchVo getBatchById(@Param("id") Integer id);
    @Insert("insert into t_card_admin_record(batch_no,operator,intro,operate_type,ctime) values (#{batchNo},#{operator},#{intro},#{operateType},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addCardAdminRecord(CardAdminRecord cardAdminRecord);
    @Insert("<script>" +
            " insert into t_card_admin_record_detail(record_id,card_no) " +
            " VALUES " +
            " <foreach collection = \"list\" item=\"c\" index=\"index\" separator=\",\"> " +
            " (#{c.recordId}, #{c.cardNo})" +
            " </foreach>" +
            " </script>")
    int addCardAdminRecordDetail(List<CardAdminRecordDetail> list);

    @Update("<script> update t_card set status=#{status} where card_no in " +
            "<foreach item=\"cardNo\" collection=\"list\" open=\"(\" separator=\",\" close=\")\"> #{cardNo} </foreach>" +
            "</script>")
    int updateCardStatus(@Param("list")List<String> cardNos, @Param("status")int status);
    @Select("<script> select card_no from t_card where batch_no=#{batchNo} and status in " +
            "<foreach item=\"status\" collection=\"list\" open=\"(\" separator=\",\" close=\")\"> #{status} </foreach>" +
            " for update </script>")
    List<String> selectCardByBatchForUpdate(@Param("batchNo")String batchNo,@Param("list") List<Integer> statusList);
    @Update("<script> update t_card set bind_user=#{bindUser},bind_type=1 where card_no in " +
            "<foreach item=\"cardNo\" collection=\"list\" open=\"(\" separator=\",\" close=\")\"> #{cardNo} </foreach>" +
            "</script>")
    int bindUser(@Param("list")List<String> cardNoList, @Param("bindUser") Integer bindUser);
    @Select("<script> select card_no from t_card where card_no in " +
            "<foreach item=\"cardNo\" collection=\"cardNoList\" open=\"(\" separator=\",\" close=\")\"> #{cardNo} </foreach>" +
            " and status in " +
            "<foreach item=\"status\" collection=\"statusList\" open=\"(\" separator=\",\" close=\")\"> #{status} </foreach>" +
            " for update </script>")
    List<String> selectCardByCardNosForUpdate(@Param("cardNoList")List<String> cardNoList, @Param("statusList")List<Integer> statusList);
    //操作历史详情
    @Select("select (select GROUP_CONCAT(card_no) from t_card_admin_record_detail d where record_id=#{id}) as cardNos,b.batch_no as batchNo,DATE_FORMAT(b.ctime,'%Y-%m-%d %H:%i:%s') as createTime,case when b.create_type=1 then '手动创建' when b.create_type=2 then '自动创建' end as createType,b.face_value as faceValue,DATE_FORMAT(b.stime,'%Y-%m-%d %H:%i:%s') as startTime,DATE_FORMAT(b.etime,'%Y-%m-%d %H:%i:%s') as endTime,b.count as count,b.count*b.face_value as sumMoney,b.company as company,case when b.card_type=1 then '实物卡' when b.card_type=2 then '虚拟卡' end as cardType,r1.operator as createUser,r2.operator as operator,DATE_FORMAT(r2.ctime,'%Y-%m-%d %H:%i:%s') as operateTime," +
            " case when r2.operate_type=1 then '创建' when r2.operate_type=2 then '激活' when r2.operate_type=3 then '绑定' when r2.operate_type=4 then '暂停' when r2.operate_type=5 then '废弃' when r2.operate_type=6 then '导出' end  as operateType," +
            "r2.intro as intro from t_card_admin_record r1 inner join t_card_admin_record r2  on r1.batch_no=r2.batch_no inner join t_card_batch b on b.batch_no=r1.batch_no where r2.id=#{id} and r1.operate_type=1")
    CardBatchAdminRecordDetailVo cardBatchAdminRecordDetail(@Param("id") Integer id);
    @Select("<script> select sum(balance) from t_card where card_no in " +
            "<foreach item=\"cardNo\" collection=\"list\" open=\"(\" separator=\",\" close=\")\"> #{cardNo} </foreach>" +
            "</script>")
    BigDecimal getBalanceByCardNos(@Param("list") List<String> cardNoList);
    @Select("SELECT b.id as id,b.count * b.face_value AS sumMoney,sum(c.balance) AS balance,sum(c.face_value-c.balance) AS usedMoney,count(c.bind_user) as bindCount,b.count-count(c.bind_user) as unBindCount,count(IF(c.status=1,1,NULL)) as activedCount,b.venderid as venderid,b.batch_no as batchNo,b.card_name as cardName,b.card_type as cardType,b.count as count,b.face_value as faceValue,b.company as company,b.create_type as createType,DATE_FORMAT(b.ctime,'%Y-%m-%d %H:%i:%s') AS cTime,DATE_FORMAT(b.stime,'%Y-%m-%d %H:%i:%s') AS sTime,DATE_FORMAT(b.etime,'%Y-%m-%d %H:%i:%s') AS eTime " +
            " FROM t_card_batch b left join t_card c on b.batch_no=c.batch_no  where b.batch_no=#{batchNo}")
    CardBatchVo getBatchDetail(@Param("batchNo") String batchNo);
    @Select("select id,venderid,batch_no as batchNo,card_no as cardNo,passwd,face_value as faceValue,balance,bind_user as bindUser,case when bind_type=1 then '手动绑定' when bind_type=2 then '自动绑定' end as bindType,case when status=0 then '未激活' when status=1 then '已激活' when status=2 then '已暂停' when status=3 then '已废弃' end as status,DATE_FORMAT(ctime,'%Y-%m-%d %H:%i:%s') AS cTime,DATE_FORMAT(stime,'%Y-%m-%d %H:%i:%s') AS sTime,DATE_FORMAT(etime,'%Y-%m-%d %H:%i:%s') AS eTime from t_card where card_no=#{cardNo}")
    CardVo getCardByCardNo(@Param("cardNo") String cardNo);
    @Select("<script> select bind_user from t_card where card_no in " +
            "<foreach item=\"cardNo\" collection=\"list\" open=\"(\" separator=\",\" close=\")\"> #{cardNo} </foreach>" +
            " and isnull(bind_user)=0 limit 1</script>")
    String isExistBindedUser(@Param("list") List<String> cardNoList);
    @Select("<script> SELECT id,case when bind_vender=0 then '否' when bind_vender=1 then '是' end as bindVender,venderid,batch_no as batchNo,card_no as cardNo,passwd,face_value as faceValue,balance,bind_user as bindUser,case when bind_type=1 then '手动绑定' when bind_type=2 then '自动绑定' end as bindType,case when status=0 then '未激活' when status=1 then '已激活' when status=2 then '已暂停' when status=3 then '已废弃' end as status ,DATE_FORMAT(ctime,'%Y-%m-%d %H:%i:%s') AS cTime,DATE_FORMAT(stime,'%Y-%m-%d %H:%i:%s') AS sTime,DATE_FORMAT(etime,'%Y-%m-%d %H:%i:%s') AS eTime FROM t_card  where card_no in " +
            "<foreach item=\"cardNo\" collection=\"list\" open=\"(\" separator=\",\" close=\")\"> #{cardNo} </foreach>" +
            "</script>")
    List<CardVo> listCardByCardNos(@Param("list") List<String> cardNoList);

//    @Update("<script> " +
//            " UPDATE t_card SET balance = balance + " +
//            "<foreach collection = \"list\" item=\"card\" index=\"index\" separator=\",\"> " +
//            " WHEN card_no=#{card.card_no} THEN #{card.balance} " +
//            "</foreach>" +
//            " WHERE card_no IN " +
//            "<foreach collection = \"list\" item=\"card\" index=\"index\" open=\"(\" separator=\",\" close=\")\">" +
//            "  #{card.card_no} " +
//            "</foreach>" +
//            "</script>")
//    int batchUpdateCardBalance(List<TCardBean> cardBeans);

    @Update("UPDATE t_card SET balance = balance + #{balance} WHERE card_no = #{cardNo}")
    int updateCardBalance(@Param("cardNo")String cardNo, @Param("balance")BigDecimal balance);
    @Select("<script> SELECT r.id AS id,CASE WHEN r.operate_type = 1 THEN '绑定' WHEN r.operate_type = 2 THEN '购物' WHEN r.operate_type = 3 THEN '退款' END AS operateType,c.batch_no AS batchNo,r.card_no AS cardNo,b.card_name AS cardName,b.card_type AS cardType,b.face_value AS faceValue,b.face_value - c.balance AS usedFee,c.balance AS balance,c.bind_user AS bindUser,o.order_sn AS orderNo, " +
            "  DATE_FORMAT(min(r.ctime),'%Y-%m-%d %H:%i:%s') AS useTime, CASE WHEN c. STATUS = 0 THEN '未激活' WHEN c. STATUS = 1 THEN '已激活' WHEN c. STATUS = 2 THEN '已暂停' WHEN c. STATUS = 3 THEN '已废弃' END AS STATUS " +
            " FROM t_card_user_record r LEFT JOIN t_card c ON c.card_no = r.card_no LEFT JOIN t_card_batch b ON c.batch_no = b.batch_no LEFT JOIN t_order_card o ON o.card_no = r.card_no " +
            "<where> " +
            "<if test=\"batchNo != null and batchNo!=''\"> and c.batch_no=#{batchNo} </if>"+
            "<if test=\"cardNo != null and cardNo!=''\"> and r.card_no=#{cardNo} </if>"+
            "<if test=\"cardName != null and cardName!=''\"> and b.card_name like CONCAT('%',#{cardName},'%') </if>"+
            "<if test=\"cardType != null and cardType!=''\"> and b.card_type=#{cardType} </if>"+
            "<if test=\"status != null and status!=''\"> and c.status=#{status} </if>"+
            "<if test=\"createType != null and createType!=''\"> and b.create_type=#{createType} </if>"+
            "<if test=\"startTime != null and startTime != ''\"> and r.ctime &gt;= #{startTime} </if>"+
            "<if test=\"endTime != null and endTime != ''\"> and r.ctime &lt;= #{endTime} </if>"+
            "<if test=\"sTime != null and sTime != ''\"> and c.stime &gt;= #{sTime} </if>"+
            "<if test=\"eTime != null and eTime != ''\"> and c.etime &lt;= #{eTime} </if>"+
            "</where>" +
            " GROUP BY r.card_no ORDER BY min(r.ctime) DESC " +
            "<if test='offset != null and limit != null'> limit ${offset}, ${limit} </if>"+
            "</script>")
    List<CardUserRecordManagerVo> listCardUserRecord(Map<String, Object> map);
    @Select("<script> select count(*) from (SELECT r.id " +
            " FROM t_card_user_record r LEFT JOIN t_card c ON c.card_no = r.card_no LEFT JOIN t_card_batch b ON c.batch_no = b.batch_no LEFT JOIN t_order_card o ON o.card_no = r.card_no " +
            "<where> " +
            "<if test=\"batchNo != null and batchNo!=''\"> and c.batch_no=#{batchNo} </if>"+
            "<if test=\"cardNo != null and cardNo!=''\"> and r.card_no=#{cardNo} </if>"+
            "<if test=\"cardName != null and cardName!=''\"> and b.card_name like CONCAT('%',#{cardName},'%') </if>"+
            "<if test=\"cardType != null and cardType!=''\"> and b.card_type=#{cardType} </if>"+
            "<if test=\"status != null and status!=''\"> and c.status=#{status} </if>"+
            "<if test=\"createType != null and createType!=''\"> and b.create_type=#{createType} </if>"+
            "<if test=\"startTime != null and startTime != ''\"> and r.ctime &gt;= #{startTime} </if>"+
            "<if test=\"endTime != null and endTime != ''\"> and r.ctime &lt;= #{endTime} </if>"+
            "<if test=\"sTime != null and sTime != ''\"> and c.stime &gt;= #{sTime} </if>"+
            "<if test=\"eTime != null and eTime != ''\"> and c.etime &lt;= #{eTime} </if>"+
            "</where>" +
            " GROUP BY r.card_no ) a </script>")
    int countCardUserRecord(Map<String, Object> map);
    @Select("<script> select count(*) from (select r.id from t_card_user_record r left join t_order o on o.order_sn=r.order_sn LEFT JOIN t_order_refund ro on ro.order_sn=r.order_sn " +
            "<where> "+
            "<if test=\"cardNo != null and cardNo!=''\"> and r.card_no=#{cardNo} </if>"+
            " </where>"+
            "  group by r.id )a</script>")
    int countCardDetail(Map<String,Object> map);
    @Select("<script> select r.id as id,case when r.operate_type=1 then '绑卡' when r.operate_type=2 then '购物' when r.operate_type=3 then '退款' end as operateType,IFNULL(r.order_sn,'') as orderNo,IFNULL(o.pay_price,0)+IFNULL(o.card_price,0) as orderMoney,IFNULL(case when r.operate_type=2 then o.pay_price when r.operate_type=3 then sum(ro.refund_price) end,0) as cashMoney,IFNULL(r.use_fee,0) as cardMoney,DATE_FORMAT(r.ctime,'%Y-%m-%d %H:%i:%s') as ctime from t_card_user_record r left join t_order o on o.order_sn=r.order_sn LEFT JOIN t_order_refund ro on ro.order_sn=r.order_sn " +
            "<where> "+
            "<if test=\"cardNo != null and cardNo!=''\"> and r.card_no=#{cardNo} </if>"+
            "</where>"+
            " group by r.id order by r.ctime desc limit ${offset}, ${limit} " +
            " </script>")
    List<CardDetailVo> listCardDetail(Map<String,Object> map);

    @Select("select a.card_no as cardNo, a.face_value as faceValue, a.balance, DATE_FORMAT(a.etime,'%Y-%m-%d %H:%i:%s') as expireTime, a.status as cardState " +
            " from t_card a join t_card_user_record b " +
            " on a.card_no = b.card_no " +
            " where bind_user = #{userId} and b.operate_type = 1 " +
            " order by a.status asc, a.etime desc, a.balance desc, b.ctime desc")
    List<CardSimpleInfoBean> getAllMyCards2(@Param("userId")int userId);

    @Select("SELECT a.card_no as cardNo, a.face_value as faceValue, a.balance, DATE_FORMAT(a.etime,'%Y-%m-%d %H:%i:%s') as expireTime, a.status as cardState " +
            " FROM t_card a join t_card_user_record b " +
            " ON a.card_no = b.card_no " +
            " WHERE a.bind_user = #{userId} AND b.operate_type = 1 " +
            "       AND (a.balance = 0 OR a.status != 1 OR a.etime < now()) " +
            " GROUP BY a.card_no " +
            " ORDER BY b.ctime DESC ")
    List<CardSimpleInfoBean> getUserInvalidCard(@Param("userId")int userId);

    @Select("SELECT a.card_no as cardNo, a.face_value as faceValue, a.balance, DATE_FORMAT(a.etime,'%Y-%m-%d %H:%i:%s') as expireTime, a.status as cardState " +
            " FROM t_card a join t_card_user_record b " +
            " ON a.card_no = b.card_no " +
            " WHERE a.bind_user = #{userId} AND b.operate_type = 1 AND a.status = 1 AND a.etime >= now() AND a.balance>0 " +
            " GROUP BY a.card_no " +
            " ORDER BY b.ctime DESC ")
    List<CardSimpleInfoBean> getUserValidCard(@Param("userId")int userId);
    @Insert("<script>" +
            " insert into t_card_user_record(card_no,operate_type,user_id,ctime) " +
            " VALUES " +
            " <foreach collection = \"list\" item=\"c\" index=\"index\" separator=\",\"> " +
            " ( #{c.cardNo},#{c.operateType},#{c.userId},#{c.ctime})" +
            " </foreach>" +
            " </script>")
    void addCardUserRecord(List<CardUserRecord> cardUserRecords);
}
