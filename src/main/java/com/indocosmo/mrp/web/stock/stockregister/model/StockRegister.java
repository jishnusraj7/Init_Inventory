package com.indocosmo.mrp.web.stock.stockregister.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "mrp_stock_register_hdr")
public class StockRegister extends GeneralModelBase{

	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	@Transient
	private Integer departmentId;
	
	@Pk
	@Column(name = "ext_ref_no")
	private String extRefNo;
	
	@Column( name ="txn_date")
	private String txnDate;
	
	
	@Column(name="trans_type")
	private Integer transType;
	
	@Column(name = "ext_ref_id")
	private Integer extRefId;
	
	
	@Column(name = "created_by")
	private Integer created_by = 0;

	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "source_shop_id")
	private Integer source_shop_id;
	
	@Column(name = "destination_shop_id")
	private Integer destination_shop_id = 0;
	
	
	
	/**
	 * @return the source_shop_id
	 */
	public Integer getSource_shop_id() {
	
		return source_shop_id;
	}


	
	/**
	 * @return the destination_shop_id
	 */
	public Integer getDestination_shop_id() {
	
		return destination_shop_id;
	}


	
	/**
	 * @param source_shop_id the source_shop_id to set
	 */
	public void setSource_shop_id(Integer source_shop_id) {
	
		this.source_shop_id = source_shop_id;
	}


	
	/**
	 * @param destination_shop_id the destination_shop_id to set
	 */
	public void setDestination_shop_id(Integer destination_shop_id) {
	
		this.destination_shop_id = destination_shop_id;
	}


	/**
	 * @return the created_by
	 */
	public Integer getCreated_by() {
	
		return created_by;
	}

	
	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
	
		return created_at;
	}

	
	/**
	 * @return the updated_by
	 */
	public Integer getUpdated_by() {
	
		return updated_by;
	}

	
	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
	
		return updated_at;
	}

	
	/**
	 * @param created_by the created_by to set
	 */
	public void setCreated_by(Integer created_by) {
	
		this.created_by = created_by;
	}

	
	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
	
		this.created_at = created_at;
	}

	
	/**
	 * @param updated_by the updated_by to set
	 */
	public void setUpdated_by(Integer updated_by) {
	
		this.updated_by = updated_by;
	}

	
	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
	
		this.updated_at = updated_at;
	}

	@Column(name = "updated_at")
	private String updated_at;
	
	
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
	@Counter(module="stock_register_hdr", key="stock_register_hdr")
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the departmentId
	 */
	public Integer getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the extRefNo
	 */
	public String getExtRefNo() {
		return extRefNo;
	}

	/**
	 * @param extRefNo the extRefNo to set
	 */
	public void setExtRefNo(String extRefNo) {
		this.extRefNo = extRefNo;
	}

	/**
	 * @return the txnDate
	 */
	public String getTxnDate() {
		return txnDate;
	}

	/**
	 * @param txnDate the txnDate to set
	 */
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	
	/**
	 * @return the trensType
	 */
	public Integer getTransType() {
		return transType;
	}

	/**
	 * @param trensType the trensType to set
	 */
	public void setTransType(Integer trensType) {
		this.transType = trensType;
	}

	/**
	 * @return the extRefId
	 */
	public Integer getExtRefId() {
		return extRefId;
	}

	/**
	 * @param extRefId the extRefId to set
	 */
	public void setExtRefId(Integer extRefId) {
		this.extRefId = extRefId;
	}
	
	
	
}
