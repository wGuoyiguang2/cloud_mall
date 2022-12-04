package com.hqtc.bms.model.base;

import java.util.List;

/**
 * 批处理接口
 * @author laiqingchuang
 */
public interface BaseDao {

	/**
	 * 插入 接口 (返回 主键ID int型)
	 * @param sql			//sql语句
	 * @param valueList		//参数集合
	 */
	int insert(String sql, List<Object> valueList);

	/**
	 * 修改 接口(返回 影响行数)
	 * @param sql			//sql语句
	 * @param valueList		//参数集合
	 */
	int update(String sql, List<Object> valueList);

	/**
	 * 批量执行  接口
	 */
	int[] batchExecute(List<String> sqlList);

}