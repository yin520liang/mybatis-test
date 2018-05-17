/**
 * 
 */
package com.yang.dao;

import java.util.Collection;
import java.util.List;

/**
 * @Title BaseMapper
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月15日
 */
public interface BaseMapper<T> {
	
	T getById(Long id);
	
	// a query has not been injected sql, will throw exception
	List<T> listByIds(Collection ids);
	
	boolean save(T e);
	
	boolean update(T e);
	
	boolean remove(T e);

}
