package com.yang.mybatis.support.sqlbuilder.bean.impl;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.yang.mybatis.support.sqlbuilder.SqlBuilderContext;
import com.yang.mybatis.support.sqlbuilder.bean.Expression;
import com.yang.mybatis.support.sqlbuilder.util.ColumnUtil;

/**
 * @title NullExpression
 * @desc TODO
 * @author cxm
 * @date 2015年8月14日
 * @version 1.0
 */
public class NullExp implements Expression {

    private final String properties;

    public NullExp(String properties) {
        this.properties = properties;
    }

    @Override
    public String toSql(SqlBuilderContext context) {
        StringBuilder sql = new StringBuilder();
        String columnName =
            checkAndGetColumn(context.getFieldMapColumn(), context.getColumnMapField(), this.properties);
        return sql.append(columnName).append(" IS NULL ").toString();

    }

    @Override
    public Map<String, Object> getParamNameValueMap() {
        return Collections.emptyMap();
    }

    @Override
    public String checkAndGetColumn(Map<String, String> fieldMapColumn, Map<String, String> columnMapField,
        String property) {
        Preconditions.checkArgument(StringUtils.isNoneBlank(this.properties),
            "can not to in properties because in properties is empty");
        return ColumnUtil.getColumnName(this.properties, fieldMapColumn, columnMapField);
    }

}
