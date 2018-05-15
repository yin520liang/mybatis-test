package com.yang.mybatis.support.sqlbuilder.bean.impl;

import java.util.Collections;
import java.util.Map;

import lombok.AllArgsConstructor;

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
@AllArgsConstructor
public class PropertyExp implements Expression {

    private final String leftProperty;
    private final String rightProperty;
    private final SQLOperator operator;

    @Override
    public String toSql(SqlBuilderContext context) {
        StringBuilder sql = new StringBuilder();
        String leftColumnName =
            checkAndGetColumn(context.getFieldMapColumn(), context.getColumnMapField(), leftProperty);
        String rightColumnName =
            checkAndGetColumn(context.getFieldMapColumn(), context.getColumnMapField(), rightProperty);

        sql.append(leftColumnName).append(operator.getOperator()).append(rightColumnName);

        return sql.toString();
    }

    @Override
    public Map<String, Object> getParamNameValueMap() {
        return Collections.emptyMap();

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
