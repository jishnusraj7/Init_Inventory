package com.indocosmo.mrp.web.masters.taxparam.model;

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
@Table(name = "tax_param")
public class TaxParam extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	 
	@Column(name = "tax1_name")
	private String tax1_name;
	
	@Column(name = "tax2_name")
	private String tax2_name;

	@Column(name = "tax3_name")
	private String tax3_name;

	@Column(name = "default_taxation_method")
	private Integer default_taxation_method;

	@Column(name = "default_purchase_taxation_method")
	private Integer default_purchase_taxation_method;
	
	@Column(name="last_sync_at")
	private Timestamp lastSyncAt;

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
	 * @return the default_purchase_taxation_method
	 */
	public Integer getDefault_purchase_taxation_method() {
		return default_purchase_taxation_method;
	}

	/**
	 * @return the default_taxation_method
	 */
	public Integer getDefault_taxation_method() {
		return default_taxation_method;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the tax1_name
	 */
	public String getTax1_name() {
		return tax1_name;
	}

	/**
	 * @return the tax2_name
	 */
	public String getTax2_name() {
		return tax2_name;
	}

	/**
	 * @return the tax3_name
	 */
	public String getTax3_name() {
		return tax3_name;
	}

	/**
	 * @param default_purchase_taxation_method the default_purchase_taxation_method to set
	 */
	public void setDefault_purchase_taxation_method(
			Integer default_purchase_taxation_method) {
		this.default_purchase_taxation_method = default_purchase_taxation_method;
	}

	/**
	 * @param default_taxation_method the default_taxation_method to set
	 */
	public void setDefault_taxation_method(Integer default_taxation_method) {
		this.default_taxation_method = default_taxation_method;
	}

	/**
	 * @param id the id to set
	 */
	
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="tax_param", key="tax_param")
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * @param tax1_name the tax1_name to set
	 */
	public void setTax1_name(String tax1_name) {
		this.tax1_name = tax1_name;
	}
	
	/**
	 * @param tax2_name the tax2_name to set
	 */
	public void setTax2_name(String tax2_name) {
		this.tax2_name = tax2_name;
	}
	
	/**
	 * @param tax3_name the tax3_name to set
	 */
	public void setTax3_name(String tax3_name) {
		this.tax3_name = tax3_name;
	}
	
	
}
