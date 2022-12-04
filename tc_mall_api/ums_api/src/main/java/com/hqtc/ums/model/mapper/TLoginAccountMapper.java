package com.hqtc.ums.model.mapper;

import com.hqtc.ums.model.databasebean.TLoginAccountBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by wanghaoyang on 19-1-9.
 */
@Repository
public interface TLoginAccountMapper {

    @Select("SELECT * FROM t_login_account WHERE username = #{userName} AND password = #{passWord}")
    TLoginAccountBean getUserByNameAndPassWord(@Param("userName")String userName, @Param("passWord")String passWord);

    @Select("SELECT * FROM t_login_account WHERE username = #{userName} AND password = #{passWord} AND account_type = 1")
    TLoginAccountBean getVenderByNameAndPassWord(@Param("userName")String userName, @Param("passWord")String passWord);
}
