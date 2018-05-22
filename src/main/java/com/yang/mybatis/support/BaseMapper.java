/**
 * 
 */
package com.yang.mybatis.support;

import java.util.Collection;
import java.util.List;

/**
 * @Title BaseMapper
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月15日
 */
public interface BaseMapper<T> {
	
//	@SelectProvider(type = BaseSelectProvider.class, method = "selectById")
//	@SelectProvider(type = BaseSelectProvider.class, method = "mockSql")
	T getById(Long id);
	
//	@SelectProvider(type = BaseSelectProvider.class, method = "selectByIds")
	List<T> listByIds(Collection<Long> ids);
	
//	@InsertProvider(type = Object.class, method = "")
	boolean save(T e);
//	
//	@UpdateProvider(type = Object.class, method = "")
	boolean update(T e);
//	
//	@DeleteProvider(type = Object.class, method = "")
	boolean remove(T e);

}
