

package com.yang.mybatis.support.sqlbuilder.util;

import org.apache.commons.lang3.StringUtils;

import com.yang.mybatis.support.sqlbuilder.schema.ColumnDefine;
import com.yang.mybatis.support.sqlbuilder.schema.TableDefine;

/**
 * @title TableCreateUtil
 * @desc TODO
 * @author shanyu
 * @date 2017年1月5日
 * @version 1.0
 */

public class TableCreateUtil {

    public static String createSql(Class<?> poCls) {
        TableDefine tableDefine = ClassFieldUtil.readFieldColumnFromEntityCalss(poCls);
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE ").append(tableDefine.getName()).append(" (\n");
        String idName = null;
        for (ColumnDefine columnDefine : tableDefine.getColumnDefines()) {
            sb.append(columnDefine.getColumnName()).append(" ").append(columnDefine.getSqlType()).append(" ");
            if (columnDefine.isIdColumn()) {
                idName = columnDefine.getColumnName();
            }
            sb.append("NOT NULL ");
            if (columnDefine.isAuto()) {
                sb.append("AUTO_INCREMENT ");
            } else {
                if (columnDefine.getSqlType().equals("timestamp")) {
                    sb.append("DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ");
                } else {
                    sb.append("DEFAULT '");
                    if (StringUtils.isNoneBlank(columnDefine.getDefaultVal())) {
                        sb.append(columnDefine.getDefaultVal());
                    }
                    sb.append("' ");
                }
            }
            sb.append("COMMENT '");
            if (columnDefine.isIdColumn()) {
                sb.append(columnDefine.getColumnName());
            } else {
                sb.append(columnDefine.getComment());
            }
            sb.append("'");
            sb.append(",\n");
        }
        sb.append(" PRIMARY KEY (").append(idName).append(")\n");
        sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='").append(tableDefine.getComment()).append("';");
        return sb.toString();
    }

}
