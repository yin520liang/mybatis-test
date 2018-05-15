/**
 * 
 */
package com.yang.mybatis.support.sql;


/**
 * 
 */
public enum SqlMethod {
	
	GET_BY_ID("getById", "", "select %s from %s where id=#{id}"),
	LIST("list", "", "SELECT %s FROM %s"),
	SAVE("save", "", "insert into %s(%s) values(%s)"),
	DELETE("delete", "default delete by id", "delete from %s where id=#{id}"),
	UPDATE("update", "", "update %s set %s where id=#{id}");
	
    private final String methodName;
    private final String note;
    private final String rawSql;

    SqlMethod(final String methodName, final String note, final String rawSql) {
        this.methodName = methodName;
        this.note = note;
        this.rawSql = rawSql;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public String getNote() {
        return this.note;
    }

    public String getRawSql() {
        return this.rawSql;
    }

}