package com.hqtc.ums.model.mapper;

import com.hqtc.ums.model.databasebean.TUserBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by wanghaoyang on 18-10-11.
 */
@Repository
public interface TUserMapper {

    @Insert("INSERT INTO t_user (nickname, header, source, ctime, utime) " +
            " VALUES " +
            " (#{nickname}, #{header}, #{source}, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addUser(TUserBean tUserBean);

    @Select("SELECT * FROM t_user WHERE id = #{userId}")
    TUserBean getUserById(@Param("userId")int userId);

    @Update("UPDATE t_user SET header= #{header}, nickname = #{nickName}, ctime = now() WHERE id = #{id}")
    int updateUserInfo(@Param("header")String header, @Param("nickName")String nickName, @Param("id")int id);
}
