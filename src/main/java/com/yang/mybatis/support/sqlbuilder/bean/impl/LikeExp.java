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
 * @title EqConditon
 * @desc TODO
 * @author cxm
 * @date 2015年8月10日
 * @version 1.0
 */
public class LikeExp implements Expression {

    private final String properties;

    private final Object value;

    private MatchMode matchMode = MatchMode.EXACT;

    private Map<String, Object> valueMap = new HashMap<String, Object>(1);

    public static final SQLOperator OPERATOR = SQLOperator.IN;

    public LikeExp(String properties, Object value) {
        this.properties = properties;
        this.value = value;
    }

    public LikeExp(String properties, Object value, MatchMode matchMode) {
        this.properties = properties;
        this.value = value;
        this.matchMode = matchMode;
    }

    @Override
    public String toSql(SqlBuilderContext context) {
        StringBuilder sql = new StringBuilder();
        String columnName =
            checkAndGetColumn(context.getFieldMapColumn(), context.getColumnMapField(), this.properties);
        String varName = context.getVarName(properties, OPERATOR);
        sql.append(columnName).append(" LIKE :").append(varName).append(" ");
        valueMap.put(varName, matchMode.toMatchString(value.toString()));
        return sql.toString();
    }

    @Override
    public Map<String, Object> getParamNameValueMap() {
        return valueMap;
    }

    /**
     * @return the matchMode
     */
    public MatchMode getMatchMode() {
        return matchMode;
    }

    @Override
    public String checkAndGetColumn(Map<String, String> fieldMapColumn, Map<String, String> columnMapField,
        String property) {
        Preconditions.checkArgument(StringUtils.isNoneBlank(property),
            "can not to in properties because in properties is empty");
        Preconditions.checkArgument(value instanceof String, "value is not string");
        Preconditions.checkArgument(StringUtils.isNoneBlank((String) value), "like value is null.");
        return ColumnUtil.getColumnName(property, fieldMapColumn, columnMapField);
    }

}
