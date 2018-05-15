/**
 * 
 */
package com.yang.mybatis.support;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Title MybatisBaseMapperSupport
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月15日
 */
public class MybatisBaseMapperSupport implements ApplicationContextAware{
	
	private Logger log = LoggerFactory.getLogger(MybatisBaseMapperSupport.class);
	
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		// get configuration bean
		Configuration cfg = applicationContext.getBean(org.apache.ibatis.session.Configuration.class);
		// scan entity
		Map<String, Class<?>> typeAliases = cfg.getTypeAliasRegistry().getTypeAliases();
		for(Entry<String, Class<?>> type : typeAliases.entrySet()) {
			if(isCustomEntityClass(type.getValue())) {
				// create sql builder
				// inject sql: select, insert, delete, update
				injectAllSqls(type.getValue());
			}
		}
		
		// inject sql
		/**
		 * Configuration configuration, 
		 * String id, 
		 * SqlSource sqlSource, 
		 * SqlCommandType sqlCommandType
		 */
		String id = "com.yang.dao.T1Mapper.selectById";
		String sql = "select id, name, age from t1 where id=#{id}";
		SqlSource  sqlSource = new RawSqlSource(cfg, sql, Long.class);
		MappedStatement ms = new MappedStatement.Builder(cfg, id, sqlSource, 
				SqlCommandType.SELECT).build();	
		cfg.addMappedStatement(ms);
		log.info("size:" + cfg.getTypeAliasRegistry().getTypeAliases().size());
	}

	/**
	 * @Description 
	 */
	private void injectAllSqls(Class<?> entity) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * whether the custom entity class
	 */
	private boolean isCustomEntityClass(Class<?> value) {
		return value.getName().startsWith("com.yang.");
	}


}
