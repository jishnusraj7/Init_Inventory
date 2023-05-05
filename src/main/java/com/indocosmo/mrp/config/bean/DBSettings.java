package com.indocosmo.mrp.config.bean;

public class DBSettings {
	
	private String databaseType;
	private String dbConnectionString;
	private String driverClassName;
	
	
	/**
	 * @return
	 */
	public String getDatabaseType() {
		return databaseType;
	}
	/**
	 * @param databaseType
	 */
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
	/**
	 * @return
	 */
	public String getDbConnectionString() {
		return dbConnectionString;
	}
	/**
	 * @param dbConnectionString
	 */
	public void setDbConnectionString(String dbConnectionString) {
		this.dbConnectionString = dbConnectionString;
	}
	/**
	 * @return
	 */
	public String getDriverClassName() {
		return driverClassName;
	}
	/**
	 * @param driverClassName
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	

}
