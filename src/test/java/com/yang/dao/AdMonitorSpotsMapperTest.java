/**
 * 
 */
package com.yang.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yang.entity.AdMonitorSpots;

/**
 * @Title AdMonitorSpotsMapperTest
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月18日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdMonitorSpotsMapperTest {

	@Autowired
	private AdMonitorSpotsMapper mapper;
	
	@Test
	public void testList() {
		long start = System.currentTimeMillis();
		List<AdMonitorSpots> l = mapper.list();
		long end = System.currentTimeMillis();
		System.out.println((end - start) * 1.0 / 1000 + "s");
		
		System.out.println(l.get(0).getName());
	}

}
