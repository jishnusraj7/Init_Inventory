package com.indocosmo.mrp.web.masters.tablelocation.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="serving_table_location")
public class TableLocation extends GeneralModelBase{
	
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
	
	@Column(name = "sc_amount")
	private Double sc_amount;
	
	@Column(name = "is_auto_layout")
	private Integer is_auto_layout;
	
	@Column(name = "num_rows")
	private Integer num_rows=5;
	
	@Column(name = "num_columns")
	private Integer num_columns=5;
	
	@Column(name = "apply_service_charge")
	private Integer apply_service_charge;
	
	@Column(name = "is_sc_percentage")
	private Integer is_sc_percentage;
	
	@Column(name="last_sync_at")
	private Timestamp lastSyncAt;
	
	@Column(name = "bg_image")
	private String bg_image;
	
	@Column(name = "created_by")
	private Integer created_by;

	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;


	@Column(name = "updated_at")
	private String updated_at;
	
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="serving_table_location", key="serving_table_location")
	public void setId(Integer id) {
	
		this.id=id;
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

	/**
	 * @return the is_auto_layout
	 */
	public Integer getIs_auto_layout() {
		return is_auto_layout;
	}

	/**
	 * @param is_auto_layout the is_auto_layout to set
	 */
	public void setIs_auto_layout(Integer is_auto_layout) {
		this.is_auto_layout = is_auto_layout;
	}

	/**
	 * @return the apply_service_charge
	 */
	public Integer getApply_service_charge() {
		return apply_service_charge;
	}

	/**
	 * @param apply_service_charge the apply_service_charge to set
	 */
	public void setApply_service_charge(Integer apply_service_charge) {
		this.apply_service_charge = apply_service_charge;
	}

	/**
	 * @return the lastSyncAt
	 */
	public Timestamp getLastSyncAt() {
		return lastSyncAt;
	}

	/**
	 * @param lastSyncAt the lastSyncAt to set
	 */
	public void setLastSyncAt(Timestamp lastSyncAt) {
		this.lastSyncAt = lastSyncAt;
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

	/**
	 * @return the sc_amount
	 */
	public Double getSc_amount() {
		return sc_amount;
	}

	/**
	 * @param sc_amount the sc_amount to set
	 */
	public void setSc_amount(Double sc_amount) {
		this.sc_amount = sc_amount;
	}

	/**
	 * @return the num_rows
	 */
	public Integer getNum_rows() {
		return num_rows;
	}

	/**
	 * @param num_rows the num_rows to set
	 */
	public void setNum_rows(Integer num_rows) {
		this.num_rows = num_rows;
	}

	/**
	 * @return the num_columns
	 */
	public Integer getNum_columns() {
		return num_columns;
	}

	/**
	 * @param num_columns the num_columns to set
	 */
	public void setNum_columns(Integer num_columns) {
		this.num_columns = num_columns;
	}

	/**
	 * @return the is_sc_percentage
	 */
	public Integer getIs_sc_percentage() {
		return is_sc_percentage;
	}

	/**
	 * @param is_sc_percentage the is_sc_percentage to set
	 */
	public void setIs_sc_percentage(Integer is_sc_percentage) {
		this.is_sc_percentage = is_sc_percentage;
	}

	/**
	 * @return the bg_image
	 */
	public String getBg_image() {
		return bg_image;
	}

	/**
	 * @param bg_image the bg_image to set
	 */
	public void setBg_image(String bg_image) {
		this.bg_image = bg_image;
	}
	
}
