/**
 * 
 */
package com.yang.entity;

import java.io.Serializable;

import com.yang.mybatis.support.sqlbuilder.annotation.GeneratedValue;
import com.yang.mybatis.support.sqlbuilder.annotation.Id;
import com.yang.mybatis.support.sqlbuilder.annotation.Table;

/**
 * @Title T2
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月14日
 */
@Table(name = "t2")
public class T2 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2581812450650246310L;

	@Id
	@GeneratedValue
	Long id;
	
	String name;
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
