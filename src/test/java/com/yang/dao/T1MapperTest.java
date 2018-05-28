/**
 * 
 */
package com.yang.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yang.entity.T1;
import com.yang.service.T1Service;

/**
 * @Title T1MapperTest
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月14日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class T1MapperTest {
	
	@Autowired
	private T1Service service;


	@Test
	public void testSelect() {
		System.out.println(">>>>>>>>> start first query.");
		T1 t1 = service.getById(2L);
		System.out.println(t1.getName());
		System.out.println("<<<<<<<<<end first query");
		

		System.out.println(">>>>>>>>> start second query.");
		T1 t2 = service.getById(2L);
		System.out.println(t2.getName());
		System.out.println("<<<<<<<<< end second query");
	}

}
