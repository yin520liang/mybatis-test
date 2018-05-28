package com.yang.entity;

import java.io.Serializable;

import com.yang.mybatis.support.sqlbuilder.annotation.GeneratedValue;
import com.yang.mybatis.support.sqlbuilder.annotation.Id;
import com.yang.mybatis.support.sqlbuilder.annotation.Table;

/**
 * 
 */
@Table(name = "t1")
public class T1 implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6579226638529496854L;

	/**
     * id
     */
    @Id
	@GeneratedValue
    private Integer id;

    private String name;

    private Integer age;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "T1{" +
        " id=" + id +
        ", name=" + name +
        ", age=" + age +
        "}";
    }
}
