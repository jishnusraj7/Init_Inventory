package com.indocosmo.mrp.web.masters.usergroup.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.MasterModelBase;

@Entity
@Table(name = "user_groups")
public class UserGroup extends MasterModelBase {

	@Column(name = "created_by")
	private Integer created_by = 0;

	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;

	@Column(name = "updated_at")
	private String updated_at;

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.model.MasterModelBase#setId(java.lang
	 * .Integer)
	 */
	@Override
	@Id(generationType = GenerationType.COUNTER)
	@Counter(module = "usergroup", key = "usergroup")
	public void setId(Integer id) {
		super.setId(id);
	}

	/**
	 * @return the created_by
	 */
	public Integer getCreated_by() {
		return created_by;
	}

	/**
	 * @param created_by
	 *            the created_by to set
	 */
	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}

	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}

	/**
	 * @param created_at
	 *            the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	/**
	 * @return the updated_by
	 */
	public Integer getUpdated_by() {
		return updated_by;
	}

	/**
	 * @param updated_by
	 *            the updated_by to set
	 */
	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}

	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
		return updated_at;
	}

	/**
	 * @param updated_at
	 *            the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	
}
