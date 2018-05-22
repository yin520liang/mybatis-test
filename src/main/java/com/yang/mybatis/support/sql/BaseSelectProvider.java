/**
 * 
 */
package com.yang.mybatis.support.sql;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.tomcat.util.buf.StringUtils;

import com.yang.mybatis.support.sqlbuilder.TableContext;

/**
 * @Title BaseSelectProvider
 * @Description 'select' sqls provider
 */
@Slf4j
public class BaseSelectProvider<T>{
	
	private Class<T> entityClass;
	
	private TableContext tableContext;
	
	public BaseSelectProvider(Class<T> ec) {
		this.entityClass = ec;
		this.tableContext = new TableContext(ec);
		
	}
	
//	public String mockSql() {
//		return "aiyowei";
//	}
	
	
	public String getById() {
		String columnStr = StringUtils.join(tableContext.getColumnMapField().keySet());
		String whereStr = String.format("%s = #{id}", tableContext.getIdColumn().getColumnName());
		SQL sql = new SQL()
			.SELECT(columnStr)
			.FROM(tableContext.getTableName())
			.WHERE(whereStr);
		log.info("getById sql: [{}]", sql.toString());
		return sql.toString();
	}
	
	public String listByIds() {	
		String columnStr = StringUtils.join(tableContext.getColumnMapField().keySet());
//		String whereStr = String.format("id in ( %s )", idSet.substring(1));
		String whereStr = "";
		SQL sql = new SQL()
			.SELECT(columnStr)
			.FROM(tableContext.getTableName())
			.WHERE(whereStr);
		log.info("listByIds sql: [{}]", sql.toString());
		return sql.toString();
	}

}
