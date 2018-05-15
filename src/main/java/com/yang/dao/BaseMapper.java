/**
 * 
 */
package com.yang.dao;

import java.util.List;

/**
 * @Title BaseMapper
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月15日
 */
public interface BaseMapper<T> {
	
	T selectById(Long id);
	
	List<T> list();

}
