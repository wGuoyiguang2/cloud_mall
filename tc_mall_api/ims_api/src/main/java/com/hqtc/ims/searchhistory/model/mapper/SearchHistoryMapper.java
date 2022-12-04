package com.hqtc.ims.searchhistory.model.mapper;

import com.hqtc.ims.searchhistory.model.bean.SearchHistoryBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/6 10:18
 */
@Repository
public interface SearchHistoryMapper {
    @Insert("insert into t_search_history set user_id=#{searchHistoryBean.userId},mac=#{searchHistoryBean.mac},keyword=#{searchHistoryBean.keyword},ctime=#{searchHistoryBean.ctime}")
    Integer createSearchHistory(@Param("searchHistoryBean") SearchHistoryBean searchHistoryBean);
    @Select("select DISTINCT t.keyword from (select keyword from t_search_history where user_id=#{userId} order by ctime desc limit 50)t limit #{limit}")
    List<String> listKeywordsByUserId(@Param("userId") int userId, @Param("limit") int limit);
    @Select("select DISTINCT t.keyword from (select keyword from t_search_history where mac=#{mac} order by ctime desc limit 50)t limit #{limit}")
    List<String> listKeywordsByMac(@Param("mac") String mac, @Param("limit") int limit);
    @Delete("delete from t_search_history where user_id=#{userId}")
    int deleteKeywordsByUserId(@Param("userId") Integer userId);
    @Delete("delete from t_search_history where mac=#{mac}")
    int deleteKeywordsByMac(@Param("mac") String mac);
}
