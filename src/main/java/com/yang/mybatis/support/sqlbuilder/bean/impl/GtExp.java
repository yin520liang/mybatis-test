package com.yang.mybatis.support.sqlbuilder.bean.impl;

import com.yang.mybatis.support.sqlbuilder.bean.SQLOperator;

/**
 * @title GtExp
 * @desc TODO
 * @author cxm
 * @date 2015年12月1日
 * @version 1.0
 */
public class GtExp extends SimpleExp {

    /**
     * @param propertyName
     * @param value
     * @param operator
     */
    public GtExp(String propertyName, Object value, SQLOperator operator) {
        super(propertyName, value, SQLOperator.GT);
    }

}
