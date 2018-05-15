package com.yang.mybatis.support.sqlbuilder.bean;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Preconditions;
import com.yang.mybatis.support.sqlbuilder.SqlBuilderContext;
import com.yang.mybatis.support.sqlbuilder.util.ColumnUtil;

/**
 * @title Order
 * @desc TODO
 * @author cxm
 * @date 2015年8月13日
 * @version 1.0
 */
public class Order implements SqlElement {

    private boolean ascending;
    private String[] propertyName;

    private boolean orderGBKFun;

    private Order(boolean ascending, boolean orderGBKFun, String...propertyName) {
        this.ascending = ascending;
        this.propertyName = propertyName;
        this.orderGBKFun = orderGBKFun;
    }

    private Order(boolean ascending, String...propertyName) {
        this(ascending, false, propertyName);
    }

    /**
     * Ascending order
     *
     * @param propertyName
     * @return Order
     */
    public static Order asc(String...propertyName) {
        return asc(false, propertyName);
    }

    public static Order asc(String propertyName, boolean orderGBKFun) {
        return new Order(true, orderGBKFun, propertyName);
    }

    public static Order asc(boolean orderGBKFun, String...propertyName) {
        return new Order(true, orderGBKFun, propertyName);
    }

    /**
     * Descending order
     *
     * @param propertyName
     * @return Order
     */
    public static Order desc(String...propertyName) {
        return desc(false, propertyName);
    }

    public static Order desc(String propertyName, boolean orderGBKFun) {
        return new Order(false, orderGBKFun, propertyName);
    }

    public static Order desc(boolean orderGBKFun, String...propertyName) {
        return new Order(false, orderGBKFun, propertyName);
    }

    @Override
    public String toSql(SqlBuilderContext context) {
        Preconditions.checkArgument(ArrayUtils.isNotEmpty(this.propertyName), "can not order empty property");

        StringBuilder sb = new StringBuilder();
        String order = this.ascending ? " ASC" : " DESC";
        for (String column : this.propertyName) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            if (orderGBKFun) {
                sb.append(" CONVERT ( ");
            }
            sb.append(ColumnUtil.getColumnName(column, context.getFieldMapColumn(), context.getColumnMapField()));
            if (orderGBKFun) {
                sb.append(" USING GBK)");
            }
            sb.append(" ").append(order);
        }

        String prop = sb.toString();
        return " ORDER BY " + prop;
    }

    public static String toSql(List<Order> orders, SqlBuilderContext context) {
        StringBuilder sb = new StringBuilder();
        for (Order order : orders) {
            Preconditions.checkArgument(ArrayUtils.isNotEmpty(order.propertyName), "can not order empty property");

            String ascending = order.ascending ? " ASC" : " DESC";
            for (String column : order.propertyName) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                if (order.orderGBKFun) {
                    sb.append(" CONVERT ( ");
                }
                sb.append(ColumnUtil.getColumnName(column, context.getFieldMapColumn(), context.getColumnMapField()));
                if (order.orderGBKFun) {
                    sb.append(" USING GBK)");
                }
                sb.append(" ").append(ascending);
            }
        }
        return " ORDER BY " + sb.toString();
    }
}
