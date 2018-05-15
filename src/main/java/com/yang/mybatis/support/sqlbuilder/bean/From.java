package com.yang.mybatis.support.sqlbuilder.bean;

import org.apache.commons.lang3.StringUtils;

import com.yang.mybatis.support.sqlbuilder.SqlBuilderContext;

/**
 * @title From
 * @desc TODO
 * @author cxm
 * @date 2015年8月13日
 * @version 1.0
 */
public class From implements SqlElement {

    private String catalog;

    private String tableName;

    public From(String catalog, String tableName) {
        super();
        this.catalog = catalog;
        this.tableName = tableName;
    }

    public From(String tableName) {
        this.catalog = "";
        this.tableName = tableName;
    }

    public String toSql(SqlBuilderContext context) {
        if (StringUtils.isBlank(tableName)) {
            throw new IllegalArgumentException("table name is null");
        }
        if (StringUtils.isNoneBlank(catalog)) {
            return catalog + "." + tableName;
        } else {
            return tableName;
        }
    }

}
