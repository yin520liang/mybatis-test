package com.yang.mybatis.support.sqlbuilder.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.yang.mybatis.support.sqlbuilder.annotation.Column;
import com.yang.mybatis.support.sqlbuilder.annotation.Entity;
import com.yang.mybatis.support.sqlbuilder.annotation.GeneratedValue;
import com.yang.mybatis.support.sqlbuilder.annotation.Id;
import com.yang.mybatis.support.sqlbuilder.annotation.Table;
import com.yang.mybatis.support.sqlbuilder.schema.ColumnDefine;
import com.yang.mybatis.support.sqlbuilder.schema.TableDefine;

/**
 * @title ClassFieldUtil
 * @desc 根据类的注解读取数据库配置,待实现
 * @author cxm
 * @date 2015年8月7日
 * @version 1.0
 */
public class ClassFieldUtil {

    public static TableDefine readFieldColumnFromEntityCalss(Class<?> clazz) {
        TableDefine table = null;
        if (clazz.isAnnotationPresent(Entity.class)) {
            Entity entity = clazz.getAnnotation(Entity.class);
            table = new TableDefine(clazz);
            if (StringUtils.isNoneBlank(entity.name())) {
                table.setName(entity.name());
            } else {
                table.setName(VariableChangeUtils.camelToUnderline(clazz.getSimpleName()));
            }
            if (StringUtils.isNoneBlank(entity.tableDefine())) {
                table.setComment(entity.tableDefine());
            } else {
                table.setComment(table.getName());
            }
            table.setDynamicInsert(entity.dynamicInsert());
            table.setDynamicUpdate(entity.dynamicUpdate());
            table.setDataSourceBeanName(entity.dataSourceBeanName());
        }
        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnno = clazz.getAnnotation(Table.class);
            if (table == null) {
                table = new TableDefine(clazz);
            }
            if (StringUtils.isNoneBlank(tableAnno.name())) {
                table.setName(tableAnno.name());
            }
            if (StringUtils.isNoneBlank(tableAnno.catalog())) {
                table.setCatalog(tableAnno.catalog());
            }
            if (StringUtils.isNoneBlank(tableAnno.tableDefine())) {
                table.setComment(tableAnno.tableDefine());
            } else {
                table.setComment(table.getName());
            }
        }
        if (table != null && StringUtils.isBlank(table.getName())) {
            char[] nameArr = clazz.getName().toCharArray();
            nameArr[0] = (char) (nameArr[3] + 32);
            table.setName(new String(nameArr));
        }

        getColumnInfoFromMethod(table, clazz.getMethods());
        getColumnInfoFromFields(table, clazz.getDeclaredFields());

        return table;
    }

    private static void getColumnInfoFromFields(TableDefine table, Field[] fields) {
        String columnName = null;
        for (Field field : fields) {
            ColumnDefine columnDefine = null;
            boolean hasAnnotation = false;
            columnDefine = new ColumnDefine(field.getName());
            int precision = 0;
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                columnName = column.name();
                if (StringUtils.isNoneBlank(columnName)) {
                    columnDefine.setColumnName(columnName);
                }
                precision = column.precision();
                buildColumnDefine(columnDefine, column);
                hasAnnotation = true;
            }
            if (field.isAnnotationPresent(Id.class)) {
                columnDefine.setIdColumn(true);
                hasAnnotation = true;
                if (field.isAnnotationPresent(GeneratedValue.class)) {
                    columnDefine.setAuto(true);
                }
            }
            if (columnDefine != null && hasAnnotation) {
                buildSqlType(field.getType(), precision, columnDefine);
                table.getColumnDefines().add(columnDefine);
            }
        }
    }

    private static void getColumnInfoFromMethod(TableDefine table, Method[] methods) {
        String columnName = null;
        for (Method method : methods) {
            ColumnDefine columnDefine = null;
            boolean hasAnnotation = false;
            int precision = 0;
            if (method.isAnnotationPresent(Column.class)) {
                columnDefine = new ColumnDefine(getFieldNameFromGetOrSetMethodName(method.getName()));
                Column column = method.getAnnotation(Column.class);
                precision = column.precision();
                if (StringUtils.isNoneBlank(columnName)) {
                    columnDefine.setColumnName(columnName);
                }
                buildColumnDefine(columnDefine, column);
                hasAnnotation = true;
            }
            if (method.isAnnotationPresent(Id.class)) {
                if (columnDefine == null) {
                    columnDefine = new ColumnDefine(getFieldNameFromGetOrSetMethodName(method.getName()), true);
                } else {
                    columnDefine.setIdColumn(true);
                }
                if (method.isAnnotationPresent(GeneratedValue.class)) {
                    columnDefine.setAuto(true);
                }
                hasAnnotation = true;
            }
            if (columnDefine != null && hasAnnotation) {
                buildSqlType(method.getClass(), precision, columnDefine);
                table.getColumnDefines().add(columnDefine);
            }
        }
    }

    public static String getFieldNameFromGetOrSetMethodName(String methodName) {
        if (StringUtils.isBlank(methodName) || (!methodName.startsWith("get") && !methodName.startsWith("set"))
            || methodName.length() <= 3) {
            throw new UnsupportedOperationException("unsupport method:" + methodName);
        }
        char[] nameArr = methodName.toCharArray();
        nameArr[3] = (char) (nameArr[3] + 32);

        return new String(Arrays.copyOfRange(nameArr, 3, nameArr.length));
    }

    public static String lowerIndexString(String source, int index) {
        checkIndex(source, index);
        char[] nameArr = source.toCharArray();
        nameArr[index] = (char) (nameArr[index] + 32);
        return new String(nameArr);
    }

    /**
     * @param source
     * @param index
     */
    private static void checkIndex(String source, int index) {
        if (StringUtils.isBlank(source)) {
            throw new IllegalArgumentException("source string can not be empty");
        }
        if (index < 0) {
            throw new IllegalArgumentException("index can not be Negative.");
        }
        if (source.length() < index + 1) {
            throw new IndexOutOfBoundsException("source length:" + source.length() + " change index:" + index);
        }
    }

    public static String uperIndexString(String source, int index) {
        checkIndex(source, index);
        char[] nameArr = source.toCharArray();
        nameArr[index] = (char) (nameArr[index] - 32);
        return new String(nameArr);
    }

    private static void buildColumnDefine(ColumnDefine define, Column column) {
        define.setComment(column.columnDefinition());
        define.setDefaultVal(column.defaultVal());
    }

    /**
     * @param cls
     * @param precision
     * @return
     */
    @SuppressWarnings("rawtypes")
    private static void buildSqlType(Class cls, int precision, ColumnDefine define) {
        StringBuffer sb = new StringBuffer();
        if (cls == Integer.class) {
            if (precision > 0 && precision <= 4) {
                sb.append("int(").append(precision).append(")");
            } else {
                sb.append("int(10)");
            }
            if (StringUtils.isBlank(define.getDefaultVal())) {
                define.setDefaultVal("0");
            }
        } else if (cls == Boolean.class) {
            sb.append("tinyint(2)");
            if (StringUtils.isBlank(define.getDefaultVal())) {
                define.setDefaultVal("0");
            }
        } else if (cls == Long.class) {
            sb.append("int(11)");
            if (StringUtils.isBlank(define.getDefaultVal())) {
                define.setDefaultVal("0");
            }
        } else if (cls == String.class) {
            if (precision == 0) {
                sb.append("varchar(100)");
            } else {
                sb.append("varchar(").append(precision).append(")");
            }
        } else if (cls == Date.class) {
            sb.append("timestamp");
        }
        define.setSqlType(sb.toString());
    }

    public static boolean isPrimitive(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        return clazz.isPrimitive() | clazz.isAssignableFrom(Integer.class) | clazz.isAssignableFrom(String.class)
            | clazz.isAssignableFrom(Character.class) | clazz.isAssignableFrom(Boolean.class)
            | clazz.isAssignableFrom(Byte.class) | clazz.isAssignableFrom(Long.class)
            | clazz.isAssignableFrom(Float.class) | clazz.isAssignableFrom(Short.class);
    }

    public static void main(String[] args) {
        System.out.println(isPrimitive(Integer.class));
        System.out.println(isPrimitive(int.class));
        System.out.println(isPrimitive(Boolean.class));
    }

}
