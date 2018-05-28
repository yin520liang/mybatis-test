/**
 * 
 */
package com.yang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Title Application
 * @Description
 * @Author lvzhaoyang
 * @Date 2018年5月14日
 */

@SpringBootApplication
@MapperScan("com.yang.dao")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}
