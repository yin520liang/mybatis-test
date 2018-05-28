package com.yang.service;

import java.util.Collection;
import java.util.List;

import com.yang.entity.T1;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yang123
 * @since 2018-05-14
 */
public interface T1Service {
	
	T1 getById(Long id);
	
	List<T1> listByIds(Collection<Long> ids);

}
