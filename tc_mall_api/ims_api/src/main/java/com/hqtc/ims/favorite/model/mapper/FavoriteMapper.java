package com.hqtc.ims.favorite.model.mapper;

import com.hqtc.ims.favorite.model.bean.FavoriteBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/25 19:14
 */
@Repository
public interface FavoriteMapper {
    @Insert("insert into t_favorite set user_id=#{userId},ctime=NOW(),source_id=#{sku}")
    int addFavorite(@Param("userId") int userId,
                     @Param("sku") Long sku);
    @Delete("delete from t_favorite where user_id=#{userId} and source_id=#{sku}")
    int deleteFavorite(@Param("userId") int userId,
                        @Param("sku") Long sku);
    @Select("select id,user_id as userId,source_id as sourceId,ctime from t_favorite where user_id=#{userId} order by ctime desc limit #{startIndex},#{pageSize}")
    List<FavoriteBean> listByUserId(@Param("userId") Integer userId,@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);
    @Select("select id,user_id as userId,source_id as sourceId,ctime from t_favorite where user_id=#{userId} and source_id=#{sku}")
    FavoriteBean getByUserIdAndSku(@Param("userId") Integer userId, @Param("sku") Long sku);
    @Select("select count(1) from t_favorite where user_id=#{userId}")
    int countFavorite(@Param("userId") Integer userId);
}
