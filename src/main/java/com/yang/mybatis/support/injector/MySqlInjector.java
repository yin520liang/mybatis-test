/**
 * 
 */
package com.yang.mybatis.support.injector;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.Configuration;

import com.yang.mybatis.support.exceptions.InvalidMapperClassException;
import com.yang.mybatis.support.sql.BaseSelectProvider;
import com.yang.mybatis.support.sql.SqlMethod;

/**
 * @Title MySqlInjector
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月21日
 */
public class MySqlInjector implements SqlInjector{
	
	private static HashMap<Class<?>, Class<?>> mapperEntityCache;
	
	private Class<?> entityClass;
	
	private Class<?> mapperClass;
	
	private Configuration cfg;

	public MySqlInjector(Configuration cfg) {
		this.cfg = cfg;
	}

	@Override
	public void injectSqls(Class<?> mapperClass) {
		this.mapperClass = mapperClass;
		entityClass = getEntityClass(mapperClass);
		// inject sqls
		injectSelectSqls();		
//		injectUpdateSqls();		
	}
	
	
	
	/**
	 * 
	 */
	private void injectUpdateSqls() {
		
	}



	/**
	 * String id = "com.yang.dao.T1Mapper.getById";
	 * String sql = "select id, name, age from t1 where id=#{id}";
	 * SqlSource  sqlSource = new RawSqlSource(cfg, sql, Long.class);
	 * MappedStatement ms = new MappedStatement.Builder(cfg, id, sqlSource, 
				SqlCommandType.SELECT).build();	
	   cfg.addMappedStatement(ms);
	 *
	 */
	private void injectSelectSqls()  {
		BaseSelectProvider provider = new BaseSelectProvider(entityClass);
		for(Method method : mapperClass.getMethods()) {
			SqlMethod sqlMethod = SqlMethod.getById(method.getName());
			if(sqlMethod == null)
				continue;
			if("getById".equals(method.getName())) {
				String id = generateId(method);
				String sql = "select id, name, age from t1 where id=#{id}";
				SqlSource sqlSource = new RawSqlSource(cfg, sql, Long.class);
				MappedStatement ms = new MappedStatement.Builder(cfg, id, sqlSource, 
						SqlCommandType.SELECT).build();	
				cfg.addMappedStatement(ms);
			   
			}
			 
		}
		
	}




	private String generateId(Method method) {
		return mapperClass.getName() + "." + method.getName();
	}



	private Class<?> getEntityClass(Class<?> mapperClass) {
		Class<?> ec = mapperEntityCache.get(mapperClass);
		if(ec == null) {
			// parse entity class
			Type[] superInterfaces = mapperClass.getGenericInterfaces();
			if(!mapperClass.isInterface() || superInterfaces.length == 0)
				throw new InvalidMapperClassException();
			
			try {
				ParameterizedType ptype = (ParameterizedType) superInterfaces[0];
				ec = (Class<?>) ptype.getActualTypeArguments()[0];
			}catch(ClassCastException e) {
				throw new InvalidMapperClassException();
			}
			mapperEntityCache.put(mapperClass, ec);
		}
		return ec;
	}

}
