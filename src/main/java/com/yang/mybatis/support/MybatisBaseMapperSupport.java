/**
 * 
 */
package com.yang.mybatis.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.yang.mybatis.support.injector.MySqlInjector;
import com.yang.mybatis.support.injector.SqlInjector;
import com.yang.mybatis.support.sqlbuilder.annotation.Entity;
import com.yang.mybatis.support.sqlbuilder.annotation.Table;

/**
 * @Title MybatisBaseMapperSupport
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月15日
 */
@Slf4j
public class MybatisBaseMapperSupport implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	private static final int DEFAULT_ENTITY_NUM = 10;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;

		// get configuration bean
		Configuration cfg = applicationContext.getBean(org.apache.ibatis.session.Configuration.class);
		// scan entity
		Map<String, Class<?>> typeAliases = cfg.getTypeAliasRegistry().getTypeAliases();
		Map<String, Class<?>> entities = new HashMap<>(DEFAULT_ENTITY_NUM);
		for(Entry<String, Class<?>> type : typeAliases.entrySet()) {
			if(isCustomEntityClass(type.getValue())) {
				entities.put(type.getKey(), type.getValue());
			}
		}
		
		if(!entities.isEmpty()) {
			SqlInjector injector = new MySqlInjector(cfg);
			for(Class<?> e : entities.values()) {
				injector.injectSqls(e);
			}
		}else {
			log.debug("No custom entity found, skip sql injection.");
		}
		
		
		// inject sql
		/**
		 * Configuration configuration, 
		 * String id, 
		 * SqlSource sqlSource, 
		 * SqlCommandType sqlCommandType
		 */
//		String id = "com.yang.dao.T1Mapper.selectById";
//		String sql = "select id, name, age from t1 where id=#{id}";
//		SqlSource  sqlSource = new RawSqlSource(cfg, sql, Long.class);
//		MappedStatement ms = new MappedStatement.Builder(cfg, id, sqlSource, 
//				SqlCommandType.SELECT).build();	
//		cfg.addMappedStatement(ms);

	}


	/**
	 * whether the custom entity class
	 */
	private boolean isCustomEntityClass(Class<?> cl) {
		return cl.isAnnotationPresent(Table.class) || cl.isAnnotationPresent(Entity.class);
	}


}
