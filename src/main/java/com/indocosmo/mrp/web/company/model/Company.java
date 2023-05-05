package com.indocosmo.mrp.web.company.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "company")
public class Company extends GeneralModelBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Pk
	@Column(name="id")
	private Integer id;
	
	@Column(name="code")
	private String code;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="database_server")
	private String databaseServer;
	
	
	@Column(name="database_name")
	private String databaseName;
	
	@Column(name="database_user")
	private String databaseUser;
	
	@Column(name="database_password")
	private String databasePassword;
	
	@Column(name="is_system")
	private Integer isSystem;
	
	@Column(name="business_type")
	private Integer businessType;
	
	
	
	/**
	 * @return the isSystem
	 */
	public Integer getIsSystem() {
	
		return isSystem;
	}

	
	/**
	 * @param isSystem the isSystem to set
	 */
	public void setIsSystem(Integer isSystem) {
	
		this.isSystem = isSystem;
	}

	@Column(name="database_port")
	private Integer databasePort;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the databaseServer
	 */
	public String getDatabaseServer() {
		return databaseServer;
	}

	/**
	 * @param databaseServer the databaseServer to set
	 */
	public void setDatabaseServer(String databaseServer) {
		this.databaseServer = databaseServer;
	}

	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @param databaseName the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * @return the databaseUser
	 */
	public String getDatabaseUser() {
		return databaseUser;
	}

	/**
	 * @param databaseUser the databaseUser to set
	 */
	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}

	/**
	 * @return the databasePassword
	 */
	public String getDatabasePassword() {
		return databasePassword;
	}

	/**
	 * @param databasePassword the databasePassword to set
	 */
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	
	/**
	 * @return the databasePort
	 */
	public Integer getDatabasePort() {
		return databasePort;
	}

	/**
	 * @param databasePort the databasePort to set
	 */
	public void setDatabasePort(Integer databasePort) {
		this.databasePort = databasePort;
	}

	
	/**
	 * @return the businessType
	 */
	public Integer getBusinessType() {
		return businessType;
	}


	/**
	 * @param businessType the businessType to set
	 */
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}


	public String getConnectionString() {
		
		final String conString= "jdbc:mysql://"
					+databaseServer
					+":3306/"
					+databaseName
					+"?user="+databaseUser
					+"&password="+databasePassword
					+"&tinyInt1isBit=false&useOldAliasMetadataBehavior=true&allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8";
		
		return conString;
	}

		
}
