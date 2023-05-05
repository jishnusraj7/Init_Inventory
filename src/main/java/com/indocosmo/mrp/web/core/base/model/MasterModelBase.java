package com.indocosmo.mrp.web.core.base.model;


import javax.persistence.Column;
import javax.persistence.Entity;

import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;


/**
 * Model base for all masters
 * @author jojesh-13.2
 *
 */
@Entity
public abstract class MasterModelBase extends GeneralModelBase {

	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;	

	@Column(name = "description")
	private String description;
	
	@Column(name = "is_deleted")
	private Integer isDeleted=0;
	
	/*@Column(name="last_sync_at")
	private Timestamp lastSyncAt;
*/
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
	 * @return the isDeleted
	 */
	public Integer getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

/*	*//**
	 * @return the lastSyncAt
	 *//*
	public Timestamp getLastSyncAt() {
		return lastSyncAt;
	}

	*//**
	 * @param lastSyncAt the lastSyncAt to set
	 *//*
	public void setLastSyncAt(Timestamp lastSyncAt) {
		this.lastSyncAt = lastSyncAt;
	}*/

	



}
