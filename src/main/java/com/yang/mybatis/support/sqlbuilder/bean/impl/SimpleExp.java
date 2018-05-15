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
 * @title SimpleCondition
 * @desc TODO
 * @author cxm
 * @date 2015年8月13日
 * @version 1.0
 */
public class SimpleExp implements Expression {

    private final String propertyName;
    private final String alias;
    private final Object value;
    private final SQLOperator operator;
    private Map<String, Object> valueMap = new HashMap<String, Object>(1);

    public SimpleExp(String propertyName, Object value, SQLOperator operator) {
        this.propertyName = propertyName;
        this.alias = "";
        this.value = value;
        this.operator = operator;
    }

    public SimpleExp(String propertyName, String alias, Object value, SQLOperator operator) {
        this.propertyName = propertyName;
        this.alias = alias;
        this.value = value;
        this.operator = operator;
    }

    @Override
    public String toSql(SqlBuilderContext context) {
        StringBuilder sql = new StringBuilder();
        String columnName =
            checkAndGetColumn(context.getFieldMapColumn(), context.getColumnMapField(), this.propertyName);
        String varName = StringUtils.isNoneBlank(alias) ? alias : context.getVarName(propertyName, operator);

        sql.append(columnName).append(operator.getOperator()).append(" :").append(varName).append(" ");
        valueMap.put(varName, value);

        return sql.toString();
    }

    @Override
    public Map<String, Object> getParamNameValueMap() {
        return valueMap;

    }

    @Override
    public String checkAndGetColumn(Map<String, String> fieldMapColumn, Map<String, String> columnMapField,
        String property) {
        Preconditions.checkArgument(StringUtils.isNoneBlank(property),
            "can not to in properties because in properties is empty");
        // Preconditions.checkArgument(value != null, this.propertyName + " value is null");
        return ColumnUtil.getColumnName(property, fieldMapColumn, columnMapField);
    }

}
