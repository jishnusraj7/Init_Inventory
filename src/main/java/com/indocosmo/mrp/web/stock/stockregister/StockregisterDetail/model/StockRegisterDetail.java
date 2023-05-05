package com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model;

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
@Table(name = "mrp_stock_register_dtl")
public class StockRegisterDetail extends GeneralModelBase {
	
	@Pk
	@Id(generationType = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "department_id")
	private Integer department_id;
	
	public Integer getDepartment_id() {
	
		return department_id;
	}
	
	public void setDepartment_id(Integer department_id) {
	
		this.department_id = department_id;
	}
	
	@Column(name = "stock_register_hdr_id")
	private Integer stockRegHdrid;
	
	@Column(name = "stock_item_id")
	private Integer stockItemId;
	
	@Column(name = "stock_item_code")
	private String stockItemCode;
	
	@Column(name = "stock_item_name")
	private String stockItemName;
	
	@Column(name = "in_qty")
	private Double inQty = 0.00;
	
	@Column(name = "out_qty")
	private Double outQty = 0.00;
	
	@Column(name = "approval_qty")
	private Double approval_qty = 0.00;
	
	@Column(name="approval_status")
	Integer approval_status;
	
	@Column(name="batch_no")
	String batch_no;
	
	@Column(name = "expiry_date")
	private String expiry_date;
	
	
	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String exptm) {
		this.expiry_date = exptm;
	}
	
	
	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	
	
	/*@Column(name = "remarks")
	private String remarks;*/
	
	/**
	 * @return the approval_qty
	 */
	public Double getApproval_qty() {
	
		return approval_qty;
	}

	
	/**
	 * @return the approval_status
	 */
	public Integer getApproval_status() {
	
		return approval_status;
	}

	
	/**
	 * @param approval_qty the approval_qty to set
	 */
	public void setApproval_qty(Double approval_qty) {
	
		this.approval_qty = approval_qty;
	}

	
	/**
	 * @param approval_status the approval_status to set
	 */
	public void setApproval_status(Integer approval_status) {
	
		this.approval_status = approval_status;
	}

	@Column(name = "ext_ref_dtl_id")
	private Integer extRefDtlId;
	
	@Column(name = "cost_price")
	private Double costPrice = 0.00;
	
	@Transient
	private Integer poId;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
	
		return id;
	}
	
	/**
	 * @param id
	 *            the id to set
	 */
	@Id(generationType = GenerationType.COUNTER)
	@Counter(module = "stock_register_dtl", key = "stock_register_dtl")
	public void setId(Integer id) {
	
		this.id = id;
	}
	
	/**
	 * @return the stockRegHdrid
	 */
	public Integer getStockRegHdrid() {
	
		return stockRegHdrid;
	}
	
	/**
	 * @param stockRegHdrid
	 *            the stockRegHdrid to set
	 */
	public void setStockRegHdrid(Integer stockRegHdrid) {
	
		this.stockRegHdrid = stockRegHdrid;
	}
	
	/**
	 * @return the stockItemId
	 */
	public Integer getStockItemId() {
	
		return stockItemId;
	}
	
	/**
	 * @param stockItemId
	 *            the stockItemId to set
	 */
	public void setStockItemId(Integer stockItemId) {
	
		this.stockItemId = stockItemId;
	}
	
	/**
	 * @return the stockItemCode
	 */
	public String getStockItemCode() {
	
		return stockItemCode;
	}
	
	/**
	 * @param stockItemCode
	 *            the stockItemCode to set
	 */
	public void setStockItemCode(String stockItemCode) {
	
		this.stockItemCode = stockItemCode;
	}
	
	/**
	 * @return the stockItemName
	 */
	public String getStockItemName() {
	
		return stockItemName;
	}
	
	/**
	 * @param stockItemName
	 *            the stockItemName to set
	 */
	public void setStockItemName(String stockItemName) {
	
		this.stockItemName = stockItemName;
	}
	
	/**
	 * @return the inQty
	 */
	public Double getInQty() {
	
		return inQty;
	}
	
	/**
	 * @param inQty
	 *            the inQty to set
	 */
	public void setInQty(Double inQty) {
	
		this.inQty = inQty;
	}
	
	/**
	 * @return the outQty
	 */
	public Double getOutQty() {
	
		return outQty;
	}
	
	/**
	 * @param outQty
	 *            the outQty to set
	 */
	public void setOutQty(Double outQty) {
	
		this.outQty = outQty;
	}
	
	/**
	 * @return the extRefDtlId
	 */
	public Integer getExtRefDtlId() {
	
		return extRefDtlId;
	}
	
	/**
	 * @param extRefDtlId
	 *            the extRefDtlId to set
	 */
	public void setExtRefDtlId(Integer extRefDtlId) {
	
		this.extRefDtlId = extRefDtlId;
	}
	
	/**
	 * @return the costPrice
	 */
	public Double getCostPrice() {
	
		return costPrice;
	}
	
	/**
	 * @param costPrice
	 *            the costPrice to set
	 */
	public void setCostPrice(Double costPrice) {
	
		this.costPrice = costPrice;
	}
	
	/**
	 * @return the poId
	 */
	public Integer getPoId() {
	
		return poId;
	}
	
	/**
	 * @param poId
	 *            the poId to set
	 */
	public void setPoId(Integer poId) {
	
		this.poId = poId;
	}
	
	
/*	*//**
	 * @return the stockItemName
	 *//*
	public String getRemarks() {
	
		return stockItemName;
	}
	
	*//**
	 * @param stockItemName
	 *            the stockItemName to set
	 *//*
	public void setRemarks(String remarks) {
	
		this.remarks = remarks;
	}*/
}
