/**
 * 
 */
package com.yang.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @Title CustomDataSourceProperties
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月4日
 */
@ConfigurationProperties(prefix="jdbc.custom")
public class CustomDataSourceProperties {
	/**
	 * <property name="driverClass" value="${jdbc.driverClassName}" />
		<property name="jdbcUrl" value="${jdbc.babel.url}" />
		<property name="user" value="${jdbc.babel.username}" />
		<property name="password" value="${jdbc.babel.password}" />
		<property name="maxPoolSize" value="${jdbc.master.maxPoolSize:10}" />
		<property name="acquireIncrement" value="${jdbc.master.acquireIncrement:3}" />
		<property name="acquireRetryAttempts" value="${jdbc.master.acquireRetryAttempts:3}" />
		<property name="maxIdleTime" value="${jdbc.master.maxIdleTime:300}" />
		<property name="idleConnectionTestPeriod" value="${jdbc.master.idleConnectionTestPeriod:300}" />
		<property name="debugUnreturnedConnectionStackTraces" value="true" />
		<property name="unreturnedConnectionTimeout" value="300" />
	 */
	private String url;
	
	private String driverClass;
	
	private String username;
	
	private String password;
	
	private int maxPoolSize;
	
	private int acquireIncrement;
	
	private int acquireRetryAttempts;
	
	private int maxIdleTime;
	
	private int idleConnectionTestPeriod;
	
	private boolean debugUnreturnedConnectionStackTraces;
	
	private int unreturnedConnectionTimeout;

	
	public String getUrl() {
		return url;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public int getAcquireIncrement() {
		return acquireIncrement;
	}

	public int getAcquireRetryAttempts() {
		return acquireRetryAttempts;
	}

	public int getMaxIdleTime() {
		return maxIdleTime;
	}

	public int getIdleConnectionTestPeriod() {
		return idleConnectionTestPeriod;
	}

	public boolean isDebugUnreturnedConnectionStackTraces() {
		return debugUnreturnedConnectionStackTraces;
	}

	public int getUnreturnedConnectionTimeout() {
		return unreturnedConnectionTimeout;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public void setAcquireIncrement(int acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

	public void setAcquireRetryAttempts(int acquireRetryAttempts) {
		this.acquireRetryAttempts = acquireRetryAttempts;
	}

	public void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
		this.idleConnectionTestPeriod = idleConnectionTestPeriod;
	}

	public void setDebugUnreturnedConnectionStackTraces(
			boolean debugUnreturnedConnectionStackTraces) {
		this.debugUnreturnedConnectionStackTraces = debugUnreturnedConnectionStackTraces;
	}

	public void setUnreturnedConnectionTimeout(int unreturnedConnectionTimeout) {
		this.unreturnedConnectionTimeout = unreturnedConnectionTimeout;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("url").append(':').append('[').append(this.url).append(']')
			.append("maxPoolSize").append(':').append('[').append(this.maxPoolSize).append(']');
		return builder.toString();
	}
}
