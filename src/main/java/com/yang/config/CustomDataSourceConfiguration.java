/**
 * 
 */
package com.yang.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yang.config.properties.CustomDataSourceProperties;
import com.yang.mybatis.support.MybatisBaseMapperSupport;

/**
 * @Title CustomDataSourceConfiguration
 * @Description refer to babel:src/main/resource/base/config-jdbc.xml
 * @Author lvzhaoyang
 * @Date 2018年5月4日
 */
@Configuration
@EnableConfigurationProperties(CustomDataSourceProperties.class)
public class CustomDataSourceConfiguration {
	
	private static Logger log = LoggerFactory.getLogger(CustomDataSourceConfiguration.class);
	
	@Autowired
	private CustomDataSourceProperties properties;

	
	@Bean
	@Qualifier("dataSource")
	public DataSource dataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		// properties of pool
		try {
			dataSource.setDriverClass(properties.getDriverClass());
		} catch (PropertyVetoException e) {
			e.printStackTrace();
			log.error("Wrong driver class type: [{}]", properties.getDriverClass());
			return null;
		}	
		dataSource.setJdbcUrl(properties.getUrl());
		dataSource.setUser(properties.getUsername());
		dataSource.setPassword(properties.getPassword());
		dataSource.setMaxPoolSize(properties.getMaxPoolSize());
		dataSource.setAcquireIncrement(properties.getAcquireIncrement());
		dataSource.setAcquireRetryAttempts(properties.getAcquireRetryAttempts());
		dataSource.setMaxIdleTime(properties.getMaxIdleTime());
		dataSource.setIdleConnectionTestPeriod(properties.getIdleConnectionTestPeriod());
		return dataSource;
	}
	
	@Bean
	@Qualifier("jdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);	
		return jdbcTemplate;
	}

	@Bean
	@Qualifier("transactionManager")
	public DataSourceTransactionManager transactionManager(@Autowired DataSource dataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		transactionManager.setNestedTransactionAllowed(true);	
		return transactionManager;
	}
	
	
	/**
	 * register mybatisBaseMapperSupport
	 */
	@Bean
	public MybatisBaseMapperSupport mybatisBaseMapperSupport() {
		return new MybatisBaseMapperSupport();
	}
	
}
