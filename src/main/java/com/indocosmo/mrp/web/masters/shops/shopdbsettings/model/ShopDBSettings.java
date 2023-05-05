package com.indocosmo.mrp.web.masters.shops.shopdbsettings.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="shop_db_settings")
public class ShopDBSettings extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="shop_db_settings", key="shop_db_settings")
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "shop_id")
	private Integer shop_id;
	
	@Column(name = "db_server")
	private String db_server;
	
	@Column(name = "db_database")
	private String db_database;
	
	@Column(name = "db_user")
	private String db_user;
	
	@Column(name = "db_password")
	private String db_password;

	@Column(name = "is_hq")
	private Integer is_hq;
	
	@Column(name = "description")
	private String description;
	
	/**
	 * @return the shop_id
	 */
	public Integer getShop_id() {
		return shop_id;
	}

	/**
	 * @param shop_id the shop_id to set
	 */
	public void setShop_id(Integer shop_id) {
		this.shop_id = shop_id;
	}

	/**
	 * @return the db_server
	 */
	public String getDb_server() {
		return db_server;
	}

	/**
	 * @param db_server the db_server to set
	 */
	public void setDb_server(String db_server) {
		this.db_server = db_server;
	}

	/**
	 * @return the db_database
	 */
	public String getDb_database() {
		return db_database;
	}

	/**
	 * @param db_database the db_database to set
	 */
	public void setDb_database(String db_database) {
		this.db_database = db_database;
	}

	/**
	 * @return the db_user
	 */
	public String getDb_user() {
		return db_user;
	}

	/**
	 * @param db_user the db_user to set
	 */
	public void setDb_user(String db_user) {
		this.db_user = db_user;
	}

	/**
	 * @return the db_password
	 */
	public String getDb_password() {
		return db_password;
	}

	/**
	 * @param db_password the db_password to set
	 */
	public void setDb_password(String db_password) {
		this.db_password = db_password;
	}

	/**
	 * @return the is_hq
	 */
	public Integer getIs_hq() {
		return is_hq;
	}

	/**
	 * @param is_hq the is_hq to set
	 */
	public void setIs_hq(Integer is_hq) {
		this.is_hq = is_hq;
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
	
	
	

}
