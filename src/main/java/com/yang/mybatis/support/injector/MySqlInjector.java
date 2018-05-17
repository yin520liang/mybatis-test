/**
 * 
 */
package com.yang.mybatis.support.injector;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import com.yang.mybatis.support.sql.SqlMethod;
import com.yang.mybatis.support.sqlbuilder.TableContext;

/**
 * @Title MySqlInjector
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月17日
 */
@Slf4j
public class MySqlInjector implements SqlInjector{

	private Configuration cfg;
	
	private static final String CLASS_METHOD_SPLITTER = ".";
	
	public MySqlInjector(Configuration cfg) {
		this.cfg = cfg;
	}

	
	/**
	 * String id = "com.yang.dao.T1Mapper.selectById";
	 * String sql = "select id, name, age from t1 where id=#{id}";
	 * SqlSource  sqlSource = new RawSqlSource(cfg, sql, Long.class);
	 * MappedStatement ms = new MappedStatement.Builder(cfg, id, sqlSource, 
				SqlCommandType.SELECT).build();	
	 * cfg.addMappedStatement(ms);
	 * 
	 * @see com.yang.mybatis.support.injector.SqlInjector#injectSqls(java.lang.Class)
	 */
	@Override
	public void injectSqls(Class<?> entityClass) {
		TableContext tableContext = new TableContext(entityClass);
		for(Method method : entityClass.getMethods()) {
			// skip Object methods
			if(Object.class.equals(method.getDeclaringClass())) {
				continue;
			}
			// get corresponding sqlMethod
			SqlMethod sqlMethod = SqlMethod.findMethodByName(method.getName());
			if(sqlMethod != null) {
				String id = generateId(entityClass, method);
				String sql = processSql(tableContext, method, sqlMethod);
				log.info("sql: {}" + sql);
				//  process parameters
				SqlSource sqlSource = new StaticSqlSource(cfg, sql, null);
				MappedStatement ms = new MappedStatement.Builder(cfg, id, sqlSource, 
						sqlMethod.getType()).build();
				// add statement to cfg
				cfg.addMappedStatement(ms);
			}
		}
		
	}
	
	
	/**
	 *
	 */
	private String processSql(final TableContext tableContext, final Method method, final SqlMethod sqlMethod) {
		SQL sql = sqlMethod.getSqlProcessor().processSql(tableContext, method, sqlMethod);
		return (sql == null)? null: sql.toString();
	}


	private String generateId(Class<?> cl, Method m) {
		return cl.getName() + CLASS_METHOD_SPLITTER + m.getName();
	}

}
