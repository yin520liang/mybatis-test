///**
// * 
// */
//package com.yang.dao;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.yang.entity.T1;
//import com.yang.service.T1Service;
//
///**
// * @Title T1MapperTest
// * @Description 
// * @Author lvzhaoyang
// * @Date 2018年5月14日
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@EnableAutoConfiguration
//public class T1MapperTest {
//	
//	@Autowired
//	private T1Service service;
//	
////	@Value("${jdbc.custom.url}")
////	private String jdbcUrl;
//
//
//	@Test
//	public void testSelect() {
//		T1 t = service.selectById(2L);
//		System.out.println(t.getName());
//
//	}
//	
////	@Test
////	public void testProperties() {
////		System.out.println(jdbcUrl);
////	}
//	
//	@Test
//	public void testEntityWrapper() {
//		EntityWrapper<T1> ew = new EntityWrapper<T1>();
//		ew.setEntity(new T1());
//		ew.where("id={0}", 1L);
//		System.out.println(service.selectCount(ew));
//	}
//
//}
