package com.yang.mybatis.support.sqlbuilder.bean.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.yang.mybatis.support.sqlbuilder.SqlBuilderContext;
import com.yang.mybatis.support.sqlbuilder.bean.Expression;
import com.yang.mybatis.support.sqlbuilder.bean.SQLOperator;
import com.yang.mybatis.support.sqlbuilder.util.ColumnUtil;

/**
 * 
 * @title DateFormatExp
 * @desc TODO
 * @author zhangbing
 * @date 2016年1月11日
 * @version 1.0
 */
public class DateFormatExp implements Expression {

    private String fieldName;

    private String format;

    private String value;

    private Map<String, Object> paramNameMap = new HashMap<String, Object>();

    public static final SQLOperator OPERATOR = SQLOperator.DATEFORMAT;

    public DateFormatExp(String fieldName, String format, String value) {
        this.fieldName = fieldName;
        this.format = format;
        this.value = value;
    }

    @Override
    public String toSql(SqlBuilderContext context) {
        StringBuilder sql = new StringBuilder();
        sql.append(" date_format(").append(fieldName).append(",'").append(format).append("')").append("='")
            .append(value).append("'");
        return sql.toString();
    }

    @Override
    public Map<String, Object> getParamNameValueMap() {
        return paramNameMap;
    }

    @Override
    public String checkAndGetColumn(Map<String, String> fieldMapColumn, Map<String, String> columnMapField,
        String property) {
        Preconditions.checkArgument(StringUtils.isNoneBlank(property),
            "can not to in properties because in properties is empty");
        Preconditions.checkArgument(format != null && value != null, "format: %s or value: %s is null", format, value);
        return ColumnUtil.getColumnName(property, fieldMapColumn, columnMapField);

    }

}
