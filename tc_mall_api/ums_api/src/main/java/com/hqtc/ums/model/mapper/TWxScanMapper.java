package com.hqtc.ums.model.mapper;

import com.hqtc.ums.model.databasebean.TWxScanBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by wanghaoyang on 18-11-2.
 */
@Repository
public interface TWxScanMapper {

    @Insert("INSERT INTO t_wx_scan (appid, openid, ticket, type, ctime, scantime, value, valuemd5num, source) " +
            " VALUES " +
            "(#{appid}, #{openid}, #{ticket}, #{type}, #{ctime}, #{scantime}, #{value}, #{valuemd5num}, #{source})")
    @Options(useGeneratedKeys= true, keyProperty="id", keyColumn = "id")
    int addWxScan(TWxScanBean wxScanBean);

    @Update("UPDATE t_wx_scan SET openid='', scantime=NULL WHERE id= #{id}")
    int updateClearScan(@Param("id") int id);

    @Select("SELECT * FROM t_wx_scan WHERE " +
            " valuemd5num = #{md5Value} AND appid = #{appid} AND type =#{type} " +
            " ORDER BY id DESC LIMIT 1")
    TWxScanBean findScanInfo(@Param("md5Value") String md5Value, @Param("appid") String appid, @Param("type") int type);

    @Update("UPDATE t_wx_scan SET ticket= #{ticket} WHERE id = #{id}")
    int updateScanTicket(@Param("ticket") String ticket, @Param("id") int id);

    @Select("SELECT * FROM t_wx_scan WHERE id = #{id}")
    TWxScanBean findScanById(@Param("id") int id);

    @Update("UPDATE t_wx_scan SET openid=#{openId}, scantime=#{scanTime} WHERE id = #{id}")
    int updateScanInfo(@Param("openId") String opneId, @Param("scanTime") Date scanTime, @Param("id") int id);
}
