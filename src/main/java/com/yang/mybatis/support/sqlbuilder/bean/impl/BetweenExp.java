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
 * @title Between
 * @desc TODO
 * @author cxm
 * @date 2015年8月13日
 * @version 1.0
 */
public class BetweenExp implements Expression {

    private String properties;

    private Comparable<?> start;

    private Comparable<?> end;

    private Map<String, Object> paramNameMap = new HashMap<String, Object>();

    public static final SQLOperator OPERATOR = SQLOperator.BETWEEN;

    public BetweenExp(String properties, Comparable<?> start, Comparable<?> end) {
        this.properties = properties;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toSql(SqlBuilderContext context) {
        StringBuilder sql = new StringBuilder();
        String columnName =
            checkAndGetColumn(context.getFieldMapColumn(), context.getColumnMapField(), this.properties);
        String startName = context.getVarName("start_" + columnName, OPERATOR);
        String endName = context.getVarName("end_" + columnName, OPERATOR);
        sql.append(columnName).append(" between :").append(startName).append(" and :").append(endName);
        paramNameMap.put(startName, start);
        paramNameMap.put(endName, end);
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
        Preconditions.checkArgument(start != null && end != null, "start: %s or end: %s is null", start, end);
        return ColumnUtil.getColumnName(property, fieldMapColumn, columnMapField);

    }

}
