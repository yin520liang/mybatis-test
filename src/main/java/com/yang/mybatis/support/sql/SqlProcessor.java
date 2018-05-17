/**
 * 
 */
package com.yang.mybatis.support.sql;

import java.lang.reflect.Method;

import org.apache.ibatis.jdbc.SQL;

import com.yang.mybatis.support.sqlbuilder.TableContext;

/**
 * @Title SqlProcessor
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月17日
 */
public interface SqlProcessor {

	SQL processSql(final TableContext tableContext, final Method method, final SqlMethod sqlMethod);
	
	
}
