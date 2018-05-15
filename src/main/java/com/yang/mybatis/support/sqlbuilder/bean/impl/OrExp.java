package com.yang.mybatis.support.sqlbuilder.bean.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.yang.mybatis.support.sqlbuilder.SqlBuilderContext;
import com.yang.mybatis.support.sqlbuilder.bean.Expression;
import com.yang.mybatis.support.sqlbuilder.bean.SqlElement;

/**
 * @title OrCondition
 * @desc TODO
 * @author cxm
 * @date 2015年8月12日
 * @version 1.0
 */
public class OrExp implements Expression {

    private Expression[] conditions;

    public OrExp(Expression leftCondition, Expression rightCondition) {
        this.conditions = new Expression[] { leftCondition, rightCondition };
    }

    public OrExp(Expression...conditions) {
        this.conditions = conditions;
    }

    @Override
    public String toSql(SqlBuilderContext context) {

        checkAndGetColumn(context.getFieldMapColumn(), context.getColumnMapField(), null);

        StringBuilder sb = new StringBuilder();
        sb.append(" ( ");
        List<String> expSqlList = Lists.newArrayList();
        for (SqlElement expression : conditions) {
            expSqlList.add(expression.toSql(context));
        }
        if (expSqlList.size() > 1) {
            sb.append(StringUtils.join(expSqlList, " OR "));
        } else {
            sb.append(expSqlList.get(0));
        }
        sb.append(" ) ");

        return sb.toString();
    }

    @Override
    public Map<String, Object> getParamNameValueMap() {
        Map<String, Object> params = new HashMap<String, Object>();
        for (Expression expression : conditions) {
            params.putAll(expression.getParamNameValueMap());
        }
        return params;
    }

    @Override
    public String checkAndGetColumn(Map<String, String> fieldMapColumn, Map<String, String> columnMapField,
        String property) {
        Preconditions.checkArgument(conditions != null && conditions.length > 0, "or conditions is empty");
        for (Expression expression : conditions) {
            Preconditions.checkNotNull(expression, "or condition: %s is null:", expression);
        }
        return null;

    }

}
