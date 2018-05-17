/**
 * 
 */
package com.yang.controller;

import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yang.dao.T1Mapper;

/**
 * @Title TestController
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月17日
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private SqlSessionFactory factory;
	
	@RequestMapping(value = "/mapper")
	public String mapper() {
		return applicationContext.getBean(T1Mapper.class).getClass().toString();
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
