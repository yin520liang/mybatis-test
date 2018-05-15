package com.yang.mybatis.support.sqlbuilder.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.yang.mybatis.support.sqlbuilder.SqlBuilderContext;
import com.yang.mybatis.support.sqlbuilder.annotation.Column;
import com.yang.mybatis.support.sqlbuilder.annotation.Id;
import com.yang.mybatis.support.sqlbuilder.bean.SaveInfo;
import com.yang.mybatis.support.sqlbuilder.exception.NoColumnFoundException;
import com.yang.mybatis.support.sqlbuilder.schema.ColumnDefine;
import com.yang.mybatis.support.sqlbuilder.schema.TableDefine;

/**
 * @title ColumnUtil
 * @desc TODO
 * @author cxm
 * @date 2015年8月10日
 * @version 1.0
 */
@Slf4j
public class ColumnUtil {

	public static ColumnDefine lookupColumn(TableDefine tableDefine, String properties) {
		ColumnDefine columnFix = null;
		ColumnDefine fieldFix = null;

		for (ColumnDefine column : tableDefine.getColumnDefines()) {
			if (column.getFieldName().equals(properties)) {
				if (fieldFix != null) {
					throw new UnsupportedOperationException(
							"properties is exist in column:" + fieldFix + " and column:" + column);
				}
				fieldFix = column;
			} else if (column.getColumnName().equals(properties)) {
				columnFix = column;
			}
		}
		ColumnDefine column = columnFix;
		if (fieldFix != null) {
			column = fieldFix;
		}
		return column;
	}

	public static String getColumnName(String properties, Map<String, String> fieldMapColumn,
			Map<String, String> columnMapField) {
		String columnName = fieldMapColumn.get(properties);
		if (columnName == null) {
			if (columnMapField.containsKey(properties)) {
				columnName = properties;
			}
		}
		if (StringUtils.isBlank(columnName)) {
			throw new NoColumnFoundException(properties);
		}
		return columnName;
	}

	public static String getColumnName(String properties, SqlBuilderContext context) {
		return getColumnName(properties, context.getFieldMapColumn(), context.getColumnMapField());
	}

	public static SaveInfo getSaveInfoFromObjs(Object obj, boolean saveNull, boolean useDefaultVal) {
		Preconditions.checkNotNull(obj, "save obj is null");
		SaveInfo result = new SaveInfo();
		for (Field field : obj.getClass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				Object value = field.get(obj);
				if (!field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class)) {
					continue;
				}

				if (!saveNull && value == null) {
					Column anno = field.getAnnotation(Column.class);
					if (anno != null && StringUtils.isNoneBlank(anno.defaultVal()) && useDefaultVal) {
						if (field.getType().isAssignableFrom(Date.class) && StringUtils.isNumeric(anno.defaultVal())) {
							value = Integer.parseInt(anno.defaultVal());
						} else {
							value = anno.defaultVal();
						}
					} else {
						continue;
					}
				}

				result.getParamMap().put(field.getName(), value);
				result.getColumns().add(field.getName());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				log.warn("get field:{} from obj :{} catch error:{}", field, obj, e);
			}
		}

		Method[] methods = obj.getClass().getMethods();
		for (Method method : methods) {
			try {
				method.setAccessible(true);
				if (method.isAnnotationPresent(Column.class) || method.isAnnotationPresent(Id.class)) {
					String fieldName = ClassFieldUtil.getFieldNameFromGetOrSetMethodName(method.getName());
					String getMethodName = String.format("get%s%s", fieldName.substring(0, 1).toUpperCase(),
							fieldName.substring(1));

					Object value = obj.getClass().getMethod(getMethodName).invoke(obj);
					if (!saveNull && value == null) {
						Column anno = method.getAnnotation(Column.class);
						if (anno != null && StringUtils.isNoneBlank(anno.defaultVal()) && useDefaultVal) {
							if (method.getReturnType().isAssignableFrom(Date.class)
									&& StringUtils.isNumeric(anno.defaultVal())) {
								value = Integer.parseInt(anno.defaultVal());
							} else {
								value = anno.defaultVal();
							}
						} else {
							continue;
						}
					}
					result.getParamMap().put(fieldName, value);
					result.getColumns().add(fieldName);
				}
			} catch (Exception e) {
				log.warn("get method:{} from obj :{} catch error:{}", method, obj, e);
				log.warn("", e);
			}
		}

		return result;
	}

	public static void main(String[] args) {

	}

	public static <T> Map<String, Object> collectBatchInsertValue(Collection<T> values, boolean isUpdate) {
		if (CollectionUtils.isEmpty(values)) {
			return Collections.emptyMap();
		}
		Map<String, Object> paramsMap = Maps.newHashMap();
		int i = 0;
		for (T value : values) {
			for (Field field : value.getClass().getDeclaredFields()) {
				try {
					field.setAccessible(true);
					Object fieldValue = field.get(value);
					String name = field.getName();
					if (i > 0) {
						name += "_" + i;
					}
					// if (!isUpdate && fieldValue == null) {
					// fieldValue =
					// getDefaultValueFromColumn(field.getAnnotation(Column.class),
					// field);
					// }
					paramsMap.put(name, fieldValue);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					log.warn("get field:{} from obj :{} catch error:{}", field, value, e);
				}
			}

			// methods
			Method[] methods = value.getClass().getMethods();
			for (Method method : methods) {
				try {
					method.setAccessible(true);
					if (method.isAnnotationPresent(Column.class) || method.isAnnotationPresent(Id.class)) {
						String name = ClassFieldUtil.getFieldNameFromGetOrSetMethodName(method.getName());	
						
						String getMethodName = String.format("get%s%s", name.substring(0, 1).toUpperCase(),
								name.substring(1));										
						Object fieldValue = value.getClass().getMethod(getMethodName).invoke(value);

						if (i > 0) {
							name += "_" + i;
						}
						paramsMap.put(name, fieldValue);
					}
				} catch (Exception e) {
					log.warn("get method:{} from obj :{} catch error:{}", method.getName(), value.getClass(), e);
				}
			}

			i++;
		}
		return paramsMap;
	}

	// private static Object getDefaultValueFromColumn(Column column, Field
	// field) {
	// if (column == null) {
	// return null;
	// }
	// if (StringUtils.isNoneBlank(column.defaultVal())) {
	// return column.defaultVal();
	// } else {
	// if (field.getType().equals(String.class)) {
	// return "";
	// } else {
	// try {
	// return field.getType().newInstance();
	// } catch (InstantiationException | IllegalAccessException e) {
	// log.error("new default catch error:", e);
	// throw new UnsupportedOperationException("column is not support type:" +
	// field.getType());
	// }
	// }
	// }
	// }

}
