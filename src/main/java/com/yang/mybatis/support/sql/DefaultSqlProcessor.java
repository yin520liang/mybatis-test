/**
 * 
 */
package com.yang.mybatis.support.sql;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.apache.ibatis.jdbc.SQL;

import com.yang.mybatis.support.sqlbuilder.TableContext;
import com.yang.mybatis.support.sqlbuilder.schema.ColumnDefine;
import com.yang.mybatis.support.sqlbuilder.schema.TableDefine;
import com.yang.mybatis.support.sqlbuilder.util.ColumnUtil;

/**
 * @Title DefaultSqlProcessor
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月17日
 */
public class DefaultSqlProcessor {
	
	
	static class GetByIdSqlProcessor implements SqlProcessor{

		@Override
		public SQL processSql(TableContext tableContext, Method method, SqlMethod sqlMethod) {
			final TableDefine tableDef = tableContext.getTableDefine();
			final ColumnDefine idColumn = tableContext.getIdColumn();
			final String tableName = tableContext.getFrom();
			final Parameter[] params = method.getParameters();
			SQL sql = new SQL() {{
				SELECT(ColumnUtil.getColumnNames(tableDef));
				FROM(tableName);
				WHERE( 
					String.format("#{%s} = %s", 
							idColumn.getColumnName(), 
							params[0].getName())
					);
			}};
			return sql;
		}
		
	}
	
	

}
