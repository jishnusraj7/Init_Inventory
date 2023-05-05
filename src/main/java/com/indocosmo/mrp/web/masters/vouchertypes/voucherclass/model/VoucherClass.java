package com.indocosmo.mrp.web.masters.vouchertypes.voucherclass.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;
import com.indocosmo.mrp.web.core.base.model.MasterModelBase;

@Entity
@Table(name ="voucher_class")
public class VoucherClass extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;	
	
	@Column(name = "is_deleted")
	private Integer isDeleted=0;
	
	@Column(name = "is_synchable")
	private Integer isSynchable=1;
	
	@Column(name = "publish_status")
	private Integer publish_status=0;
	
	
	@Column(name = "created_by")
	private Integer created_by;

	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;


	@Column(name = "updated_at")
	private String updated_at;
	
	
	
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="voucher_types", key="voucher_types")
	public void setId(Integer id) {
	
		this.id=id;
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



	/**
	 * @return the isSynchable
	 */
	public Integer getIsSynchable() {
		return isSynchable;
	}



	/**
	 * @param isSynchable the isSynchable to set
	 */
	public void setIsSynchable(Integer isSynchable) {
		this.isSynchable = isSynchable;
	}



	/**
	 * @return the publish_status
	 */
	public Integer getPublish_status() {
		return publish_status;
	}



	/**
	 * @param publish_status the publish_status to set
	 */
	public void setPublish_status(Integer publish_status) {
		this.publish_status = publish_status;
	}



	/**
	 * @return the created_by
	 */
	public Integer getCreated_by() {
		return created_by;
	}



	/**
	 * @param created_by the created_by to set
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
	 * @param created_at the created_at to set
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
	 * @param updated_by the updated_by to set
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
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}



	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	
	
	
}
