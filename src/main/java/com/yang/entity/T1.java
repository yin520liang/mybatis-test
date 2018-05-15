package com.yang.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yang123
 * @since 2018-05-14
 */
public class T1 {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
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
        ", id=" + id +
        ", name=" + name +
        ", age=" + age +
        "}";
    }
}
