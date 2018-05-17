/**
 * 
 */
package com.yang.mybatis.support.sql;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.mapping.SqlCommandType;

import com.yang.mybatis.support.sql.DefaultSqlProcessor.GetByIdSqlProcessor;


/**
 * 
 */
public enum SqlMethod {
	
	GET_BY_ID("getById", SqlCommandType.SELECT, new GetByIdSqlProcessor()),
	LIST_BY_IDS("listByIds", SqlCommandType.SELECT, null),
	SAVE("save", SqlCommandType.INSERT, null),
	REMOVE("delete", SqlCommandType.DELETE, null),
	UPDATE("update", SqlCommandType.UPDATE, null);
	
    private final String methodName;
    
    private final SqlCommandType type;
    
    private SqlProcessor sqlProcessor;
    
    // mapping between method name and SqlMethod instance
    private static Map<String, SqlMethod> idMap = new HashMap<>(5);
    
    static {
    	for(SqlMethod m : SqlMethod.values()) {
    		idMap.put(m.getMethodName(), m);
    	}
    }

    SqlMethod(final String methodName, final SqlCommandType type, final SqlProcessor sqlProcessor) {
        this.methodName = methodName;
        this.type = type;
        this.sqlProcessor = sqlProcessor;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public SqlCommandType getType() {
        return this.type;
    }
    
    public SqlProcessor getSqlProcessor() {
    	return this.sqlProcessor;
    }
    
    public static SqlMethod findMethodByName(String name) {
    	if(idMap.containsKey(name))
    		return idMap.get(name);
    	return null;
    }

}