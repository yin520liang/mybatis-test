package com.yang.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yang.dao.T1Mapper;
import com.yang.entity.T1;
import com.yang.service.T1Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yang123
 * @since 2018-05-14
 */
@Service
public class T1ServiceImpl implements T1Service{
	
	@Autowired
	private T1Mapper dao;


	@Override
	public T1 getById(Long id) {
		return dao.getById(id);
	}



	@Override
	public List<T1> listByIds(Collection<Long> ids) {
		return dao.listByIds(ids);
	}

}
