package com.yang.mybatis.support.sqlbuilder.bean.impl;

import com.yang.mybatis.support.sqlbuilder.bean.SQLOperator;

/**
 * @title NeExp
 * @desc TODO
 * @author cxm
 * @date 2015年12月1日
 * @version 1.0
 */
public class NeExp extends SimpleExp {

    /**
     * @param propertyName
     * @param value
     * @param operator
     */
    public NeExp(String propertyName, Object value, SQLOperator operator) {
        super(propertyName, value, SQLOperator.NE);
    }

}
