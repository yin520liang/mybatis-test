package com.yang.mybatis.support.sqlbuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yang.mybatis.support.sqlbuilder.exception.NoIdColumnFoundException;
import com.yang.mybatis.support.sqlbuilder.schema.ColumnDefine;
import com.yang.mybatis.support.sqlbuilder.schema.TableDefine;
import com.yang.mybatis.support.sqlbuilder.util.ClassFieldUtil;

/**
 * @title SqlBuilderContext
 * @desc TODO
 * @author cxm
 * @date 2015年12月2日
 * @version 1.0
 */
@Data
public class TableContext {
	
	private Logger log = LoggerFactory.getLogger(TableContext.class);
	
	private TableDefine tableDefine;

	private Class<?> poClass;

	private ColumnDefine idColumn;
	
	private String tableName;

    private AtomicInteger conditionIndex = new AtomicInteger(0);

    private Map<String, String> fieldMapColumn;
    
    private Map<String, String> columnMapField;
    
    
    public TableContext(Class<?> poClass) {
    	this.poClass = poClass;
		this.tableDefine = ClassFieldUtil.readFieldColumnFromEntityCalss(poClass);
		tableName = tableDefine.getName();
		if (this.tableDefine != null) {
			fieldMapColumn = new HashMap<String, String>();
			columnMapField = new HashMap<String, String>();
			for (ColumnDefine column : tableDefine.getColumnDefines()) {
				if (column.isIdColumn()) {
					if (this.idColumn == null) {
						this.idColumn = column;
					} else {
						throw new UnsupportedOperationException(
								"can not support two id column:" + idColumn + " and:" + column);
					}
				}
				fieldMapColumn.put(column.getFieldName(), column.getColumnName());
				columnMapField.put(column.getColumnName(), column.getFieldName());
			}
		}
		if (this.idColumn == null) {
			log.warn("can not found Id column in poClass:{}", poClass);
			throw new NoIdColumnFoundException(poClass);
		}

    }

//    public String getVarName(String properties, SQLOperator operator) {
//        Preconditions.checkArgument(StringUtils.isNoneBlank(properties), "properties can not be empty");
//        Preconditions.checkNotNull(operator, "sql operator can not be null");
//        properties =
//            properties.replace('.', '_').replace(' ', '_').replace('(', '_').replace(')', '_').replace(',', '_');
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(operator.name()).append("_").append(properties).append("_").append(conditionIndex.getAndIncrement());
//        return sb.toString();
//    }

}
