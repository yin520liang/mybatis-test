package com.yang.mybatis.support.sqlbuilder.schema;

import com.yang.mybatis.support.sqlbuilder.util.VariableChangeUtils;

import lombok.Data;

/**
 * @title ColumnDefine
 * @desc TODO
 * @author cxm
 * @date 2015年8月10日
 */
@Data
public class ColumnDefine {

    private String fieldName;

    private String columnName;

    private boolean isIdColumn;

    private boolean isAuto;

    private String sqlType;

    private String defaultVal;

    private String comment;

    public ColumnDefine(String fieldName, String columnName, boolean isIdColumn) {
        this.fieldName = fieldName;
        this.columnName = columnName;
        this.isIdColumn = isIdColumn;
    }

    public ColumnDefine(String fieldName, String columnName) {
        this(fieldName, columnName, false);
    }

    public ColumnDefine(String fieldName) {
        this(fieldName, VariableChangeUtils.camelToUnderline(fieldName));
    }

    public ColumnDefine(String fieldName, boolean isIdColumn) {
        this(fieldName, fieldName, isIdColumn);
    }

}
