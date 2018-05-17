package com.yang.mybatis.support.sqlbuilder.bean;

import com.yang.mybatis.support.sqlbuilder.TableContext;

/**
 * @title SqlElement
 * @desc TODO
 * @author cxm
 * @date 2015年12月2日
 * @version 1.0
 */
public interface SqlElement {

    public abstract String toSql(TableContext context);

}
