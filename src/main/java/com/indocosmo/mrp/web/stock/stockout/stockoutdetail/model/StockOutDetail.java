package com.indocosmo.mrp.web.stock.stockout.stockoutdetail.model;

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
@Table(name="mrp_stock_out_dtl")
public class StockOutDetail extends GeneralModelBase{

	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Pk
	@Column(name = "stock_out_hdr_id")
	private Integer stockOutHdrId;
	
	@Column(name = "stock_item_id")
	private Integer stockItemId;
	
	@Column( name ="stock_item_code")
	private String stockItemCode;
	
	@Column( name ="stock_item_name")
	private String stockItemName;
	
	@Column( name ="request_qty")
	private Double requestQty;
	
	@Column( name ="issued_qty")
	private Double deliveredQty;
	
	@Column( name ="cost_price")
	private Double unitPrice;
	
	@Column( name ="amount")
	private Double amount;
	
	@Transient
	private Double total_amount=0.00;
	
	@Column(name="uom_code")
	private String uomCode;
	
	@Column( name ="base_uom_code")
	private String baseUomCode;
	
	@Column( name ="compound_unit")
	private Double compoundUnit;
	
	
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
	@Counter(module="stock_out_dtl", key="stock_out_dtl")
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the stockOutHdrId
	 */
	public Integer getStockOutHdrId() {
		return stockOutHdrId;
	}

	/**
	 * @param stockOutHdrId the stockOutHdrId to set
	 */
	public void setStockOutHdrId(Integer stockOutHdrId) {
		this.stockOutHdrId = stockOutHdrId;
	}

	/**
	 * @return the stockItemId
	 */
	public Integer getStockItemId() {
		return stockItemId;
	}

	/**
	 * @param stockItemId the stockItemId to set
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
	 * @param stockItemCode the stockItemCode to set
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
	 * @param stockItemName the stockItemName to set
	 */
	public void setStockItemName(String stockItemName) {
		this.stockItemName = stockItemName;
	}

	/**
	 * @return the requestQty
	 */
	public Double getRequestQty() {
		return requestQty;
	}

	/**
	 * @param requestQty the requestQty to set
	 */
	public void setRequestQty(Double requestQty) {
		this.requestQty = requestQty;
	}

	/**
	 * @return the deliveredQty
	 */
	public Double getDeliveredQty() {
		return deliveredQty;
	}

	/**
	 * @param deliveredQty the deliveredQty to set
	 */
	public void setDeliveredQty(Double deliveredQty) {
		this.deliveredQty = deliveredQty;
	}

	/**
	 * @return the unitPrice
	 */
	public Double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the total_amount
	 */
	public Double getTotal_amount() {
		return total_amount;
	}

	/**
	 * @param total_amount the total_amount to set
	 */
	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}

	public String getBaseUomCode() {
		return baseUomCode;
	}

	public void setBaseUomCode(String baseUomCode) {
		this.baseUomCode = baseUomCode;
	}

	public Double getCompoundUnit() {
		return compoundUnit;
	}

	public void setCompoundUnit(Double compoundUnit) {
		this.compoundUnit = compoundUnit;
	}

	public String getUomCode() {
		return uomCode;
	}

	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}

	
	
}
