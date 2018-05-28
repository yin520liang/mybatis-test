package com.yang.controller;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yang.service.T1Service;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yang123
 * @since 2018-05-14
 */
@RestController
@RequestMapping("/t1")
public class T1Controller {
	
	@Autowired
	private T1Service service;
	
	
	@RequestMapping(value = "/{id}")
	public String queryT1(@PathVariable(value="id") Long id) {
		return service.getById(id).toString();
	}

	
	@RequestMapping(value = "/list")
	public String queryList() {
		return service.listByIds(Arrays.asList(1L, 2L)).toString();
	}
}

