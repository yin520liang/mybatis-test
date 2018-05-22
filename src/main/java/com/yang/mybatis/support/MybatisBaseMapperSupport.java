/**
 * 
 */
package com.yang.mybatis.support;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.yang.mybatis.support.injector.MySqlInjector;
import com.yang.mybatis.support.injector.SqlInjector;

/**
 * @Title MybatisBaseMapperSupport
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月15日
 */
@Slf4j
public class MybatisBaseMapperSupport implements ApplicationContextAware{


	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		System.out.println("----setApplicationContext----");
		SqlSessionFactory factory = applicationContext.getBean(SqlSessionFactory.class);
		Configuration cfg = factory.getConfiguration();
		
		
		Collection<Class<?>> mapperClasses = cfg.getMapperRegistry().getMappers();
		if(!mapperClasses.isEmpty()) {
			SqlInjector sqlInjector = new MySqlInjector(cfg);
			for(Class<?> mp : mapperClasses) {
				sqlInjector.injectSqls(mp);
			}	
		}else {
			log.debug("No custom mapper class found, skip sql injection.");
		}
		
		
		
//		String id = "com.yang.dao.T1Mapper.getById";
//		// current
//		MappedStatement ms = cfg.getMappedStatement(id);
//		ProviderSqlSource source = (ProviderSqlSource) ms.getSqlSource();
//		System.out.println(source.getBoundSql(null).getSql());
//		
//		/**
//		 * Configuration configuration, 
//		 * String id, 
//		 * SqlSource sqlSource, 
//		 * SqlCommandType sqlCommandType
//		 */
//		String id = "com.yang.dao.T1Mapper.getById";
//		String sql = "select id, name, age from t1 where id=#{id}";
//		SqlSource  sqlSource = new RawSqlSource(cfg, sql, Long.class);
//		MappedStatement ms = new MappedStatement.Builder(cfg, id, sqlSource, 
//				SqlCommandType.SELECT).build();	
//		cfg.addMappedStatement(ms);
		
	}


}
