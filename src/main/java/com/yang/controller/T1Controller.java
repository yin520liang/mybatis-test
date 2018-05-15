package com.yang.controller;


import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${jdbc.custom.url}")
	private String jdbcUrl;
	
	@Autowired
	private T1Service service;
	
	@Autowired
	private SqlSessionFactory factory;
	
	@RequestMapping(value = "/jdbc")
	public String getUrl() {
		return jdbcUrl;
	}
	
	
	@RequestMapping(value = "/{id}")
	public String queryT1(@PathVariable(value="id") Long id) {
		return service.getById(id).toString();
	}
	
	@RequestMapping(value = "/list")
	public String list() {
		return service.list().toString();
	}

	@RequestMapping(value = "/type")
	public String size() {
		Map<String, Class<?>> map = factory.getConfiguration().getTypeAliasRegistry().getTypeAliases();
		StringBuilder builder = new StringBuilder();
		for(Entry<String, Class<?>> entry : map.entrySet()) {
			builder.append(entry.getKey()).append(':').append(entry.getValue().getName()).append(",\n");
		}
		return builder.toString();
	}
}
