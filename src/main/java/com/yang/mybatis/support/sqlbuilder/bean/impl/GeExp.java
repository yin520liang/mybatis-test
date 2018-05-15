package com.yang.mybatis.support.sqlbuilder.bean.impl;

import com.yang.mybatis.support.sqlbuilder.bean.SQLOperator;

/**
 * @title GeExp
 * @desc TODO
 * @author cxm
 * @date 2015年12月1日
 * @version 1.0
 */
public class GeExp extends SimpleExp {

    /**
     * @param propertyName
     * @param value
     * @param operator
     */
    public GeExp(String propertyName, Object value, SQLOperator operator) {
        super(propertyName, value, SQLOperator.GE);
    }

}
