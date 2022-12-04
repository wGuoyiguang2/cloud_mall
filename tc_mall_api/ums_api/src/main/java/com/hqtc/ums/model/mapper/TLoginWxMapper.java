package com.hqtc.ums.model.mapper;

import com.hqtc.ums.model.databasebean.TLoginWxBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by wanghaoyang on 18-10-11.
 */
@Repository
public interface TLoginWxMapper {

    @Insert("INSERT INTO t_login_wx (userid, appid, openid, unionid, ctime) " +
            " VALUES " +
            " (#{userid}, #{appid}, #{openid}, #{unionid}, now() )")
    int addWxInfo(TLoginWxBean tLoginWxBean);

    @Select("SELECT * FROM t_login_wx WHERE appid = #{appId} AND openid = #{openId}")
    TLoginWxBean getByAppIdAndOpenId(@Param("appId")String appId, @Param("openId")String openId);

    @Update("UPDATE t_login_wx SET unionid = #{unionId} WHERE id =#{id}")
    int updateUnionIdById(@Param("unionId")String unionId, @Param("id")int id);

    @Select("SELECT * FROM t_login_wx WHERE openid = #{openId}")
    TLoginWxBean findByOpenId(@Param("openId")String openId);
}
