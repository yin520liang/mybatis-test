/**
 * 
 */
package com.yang.mybatis.support.sql;

import java.util.Collection;
import java.util.HashMap;


/**
 * @Title SqlMethod
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月21日
 */
public enum SqlMethod {
	
	GET_BY_ID("getById", BaseSelectProvider.class, new Class[0]),
	;
	
	
	private SqlMethod(String methodName, Class providerClass, Class[] methodParameters) {
		this.methodName = methodName;
		this.providerClass = providerClass;
		this.methodParameters = methodParameters;
	}
	
	private String methodName;
	
	private Class<?> providerClass;
	
	private Class[] methodParameters;

	public String getMethodName() {
		return methodName;
	}

	public Class<?> getProviderClass() {
		return providerClass;
	}

	public Class[] getMethodParameters() {
		return methodParameters;
	}
	
	private static HashMap<String, SqlMethod> map = new HashMap<>(2);
	static {
		for(SqlMethod m : SqlMethod.values()) {
			map.put(m.methodName, m);
		}
	}
	

	public static SqlMethod getById(String name) {
		return (name == null)? null : map.get(name);
	}

}
