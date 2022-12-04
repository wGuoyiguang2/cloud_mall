package com.hqtc.bms.model.base;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 批处理接口实现类
 * @author laiqingchuang
 */
@Repository
public class BaseDaoImpl implements BaseDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate; // 主库连接对象

	@Override
	public int insert(final String sql, final List<Object> valueList) {
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement pst = connection.prepareStatement(sql, new String[] { "id" });
				if (valueList != null && valueList.size() > 0) {
					int i = 0;
					for (Object obj : valueList) {
						pst.setObject(++i, obj);
					}
				}
				return pst;
			}
		}, key);
		return key.getKey().intValue();
	}

	@Override
	public int update(String sql, List<Object> valueList) {
		return jdbcTemplate.update(sql, valueList.toArray());
	}

	@Override
	public int[] batchExecute(List<String> sqlList) {
		return jdbcTemplate.batchUpdate(sqlList.toArray(new String[sqlList.size()]));
	}


}
