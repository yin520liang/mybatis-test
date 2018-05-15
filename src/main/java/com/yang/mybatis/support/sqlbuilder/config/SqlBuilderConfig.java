
package com.yang.mybatis.support.sqlbuilder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 导入数据源配置文件
 * 
 * @title SqlBuilderConfig
 * @desc TODO
 * @author cxm
 * @date 2015年12月17日
 * @version 1.0
 */
@Configuration
@PropertySource("classpath:db_config.properties")
public class SqlBuilderConfig {
    
    public final static String DEFAULT_JDBC_TEMPLATE_BEAN_KEY  = "sqlbuilder.jdbctemplate.default.name";
    public final static String DEFAULT_DATASOURCE_BEAN_KEY     = "sqlbuilder.datasource.default.name";

    public final static String JDBC_TEMPLATE_BEAN_KEY_TEMPLATE = "sqlbuilder.jdbctemplate.%s.name";
    public final static String DATASOURCE_BEAN_KEY_TEMPLATE    = "sqlbuilder.datasource.%s.name";

}
