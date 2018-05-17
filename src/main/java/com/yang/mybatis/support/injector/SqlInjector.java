/**
 * 
 */
package com.yang.mybatis.support.injector;

/**
 * @Title SqlInjector
 * @Description
 * @Author lvzhaoyang
 * @Date 2018年5月17日
 */
public interface SqlInjector {
	
	void injectSqls(Class<?> entityClass);
}
