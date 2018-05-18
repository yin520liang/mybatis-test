/**
 * 
 */
package com.yang.mybatis.support.sql;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Title BaseSelectProvider
 * @Description 'select' sqls provider
 */
public class BaseSelectProvider {
	
	
	public String mockSql() {
		return "aiyowei";
	}
	
	
	public String selectById(final Long id) {
		SQL sql = new SQL()
			.SELECT("id, name, age")
			.FROM("t1")
			.WHERE("id = #{id}");
		return sql.toString();
	}
	
	public String selectByIds(final Collection<Long> ids) {
		if(CollectionUtils.isEmpty(ids)) {
			throw new RuntimeException("Empty id set.");
		}
		
		if(ids.size() == 1) {
			return selectById(ids.iterator().next());
		}
		// default setting: a number has 2 digital character
		StringBuilder idSet = new StringBuilder(ids.size() * 2);
		for(Long id : ids) {
			idSet.append(',').append(id);
		}
		SQL sql = new SQL()
			.SELECT("id, name, age")
			.FROM("t1")
			.WHERE(String.format("id in ( %s )", idSet.substring(1)));
		return sql.toString();
	}

}
