/**
 * 
 */
package com.yang.mybatis.support.sql;

import org.apache.ibatis.jdbc.SQL;

import com.yang.mybatis.support.sqlbuilder.TableContext;

/**
 * @Title BaseUpdateProvider
 * @Description
 * @Author lvzhaoyang
 * @Date 2018年5月21日
 */
public class BaseUpdateProvider<T> {

	private Class<T> entityClass;
	
	private TableContext tableContext;
	
	public BaseUpdateProvider(Class<T> ec) {
		this.entityClass = ec;
		this.tableContext = (entityClass != null)? 
				new TableContext(entityClass): null;
	}
	
	public String save(T obj) {
		SQL sql = new SQL()
			.INSERT_INTO(tableContext.getTableName())
			.VALUES("", "");
		return sql.toString();
	}
	
	public String update(T obj) {
		SQL sql = new SQL()
			.UPDATE("")
			.SET();
		return sql.toString();
	}
	
	
	public String remove(T obj) {
		SQL sql = new SQL()
			.DELETE_FROM(tableContext.getTableName())
			.WHERE("");
		return sql.toString();
	}

}
