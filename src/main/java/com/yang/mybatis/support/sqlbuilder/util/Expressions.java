package com.yang.mybatis.support.sqlbuilder.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.yang.mybatis.support.sqlbuilder.bean.Expression;
import com.yang.mybatis.support.sqlbuilder.bean.SQLOperator;
import com.yang.mybatis.support.sqlbuilder.bean.impl.AndExp;
import com.yang.mybatis.support.sqlbuilder.bean.impl.BetweenExp;
import com.yang.mybatis.support.sqlbuilder.bean.impl.DateFormatExp;
import com.yang.mybatis.support.sqlbuilder.bean.impl.InExp;
import com.yang.mybatis.support.sqlbuilder.bean.impl.LikeExp;
import com.yang.mybatis.support.sqlbuilder.bean.impl.MatchMode;
import com.yang.mybatis.support.sqlbuilder.bean.impl.NotInExp;
import com.yang.mybatis.support.sqlbuilder.bean.impl.NotNullExp;
import com.yang.mybatis.support.sqlbuilder.bean.impl.NullExp;
import com.yang.mybatis.support.sqlbuilder.bean.impl.OrExp;
import com.yang.mybatis.support.sqlbuilder.bean.impl.PropertyExp;
import com.yang.mybatis.support.sqlbuilder.bean.impl.SimpleExp;
import com.yang.mybatis.support.sqlbuilder.bean.impl.TupleInExp;

/**
 * @title SqlConditions
 * @desc 生成查询条件的接口
 * @author cxm
 * @date 2015年12月1日
 * @version 1.0
 */
public class Expressions {

    public static <T extends Serializable> Expression createSimpleExpression(String fieldName, T value,
        SQLOperator operator) {
        return new SimpleExp(fieldName, value, operator);
    }

    public static <T extends Serializable> Expression eq(String fieldName, T value) {
        return new SimpleExp(fieldName, value, SQLOperator.EQ);
    }

    public static Expression eqField(String fieldName, String rightField) {
        return new PropertyExp(fieldName, rightField, SQLOperator.EQ);
    }

    public static <T extends Serializable> Expression ne(String fieldName, T value) {
        return new SimpleExp(fieldName, value, SQLOperator.NE);
    }

    public static Expression neField(String fieldName, String rightField) {
        return new PropertyExp(fieldName, rightField, SQLOperator.NE);
    }

    public static <T extends Serializable> Expression gt(String fieldName, T value) {
        return new SimpleExp(fieldName, value, SQLOperator.GT);
    }

    public static <T extends Serializable> Expression ge(String fieldName, T value) {
        return new SimpleExp(fieldName, value, SQLOperator.GE);
    }

    public static <T extends Serializable> Expression le(String fieldName, T value) {
        return new SimpleExp(fieldName, value, SQLOperator.LE);
    }

    public static <T extends Serializable> Expression lt(String fieldName, T value) {
        return new SimpleExp(fieldName, value, SQLOperator.LT);
    }

    public static <T extends Serializable> Expression like(String fieldName, T value) {
        return new LikeExp(fieldName, value);
    }

    public static <T extends Serializable> Expression like(String fieldName, T value, MatchMode matchMode) {
        return new LikeExp(fieldName, value, matchMode);
    }

    public static <T extends Serializable> Expression in(String fieldName, T[] values) {
        return in(fieldName, Sets.newHashSet(values));
    }

    public static <T extends Serializable> Expression in(String fieldName, Collection<T> values) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(values), " in values is empty");
        if (values.size() == 1) {
            return eq(fieldName, values.iterator().next());
        }
        return new InExp(fieldName, values);
    }

    public static Expression tupleIn(List<String> fieldNames, Collection<List<Object>> values) {
        return new TupleInExp(fieldNames, values);
    }

    public static <T extends Serializable> Expression notin(String fieldName, T[] values) {
        return notin(fieldName, Sets.newHashSet(values));
    }

    public static <T extends Serializable> Expression notin(String fieldName, Collection<T> values) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(values), "not in values is empty");
        if (values.size() == 1) {
            return ne(fieldName, values.iterator().next());
        }
        return new NotInExp(fieldName, values);
    }

    public static <T extends Serializable> Expression isNull(String fieldName) {
        return new NullExp(fieldName);
    }

    public static <T extends Serializable> Expression isNotNull(String fieldName) {
        return new NotNullExp(fieldName);
    }

    public static <T extends Comparable<?>> Expression between(String fieldName, T startTime, T endTime) {
        return new BetweenExp(fieldName, startTime, endTime);
    }

    public static <T extends Serializable> Expression and(Expression leftExp, Expression rightExp) {
        return new AndExp(leftExp, rightExp);
    }

    public static <T extends Serializable> Expression and(Expression...exps) {
        return new AndExp(exps);
    }

    public static <T extends Serializable> Expression or(Expression leftExp, Expression rightExp) {
        return new OrExp(leftExp, rightExp);
    }

    public static <T extends Serializable> Expression or(Expression...exps) {
        return new OrExp(exps);
    }

    public static <T extends Serializable> Expression dateformat(String fieldName, String format, String value) {
        return new DateFormatExp(fieldName, format, value);
    }
}
