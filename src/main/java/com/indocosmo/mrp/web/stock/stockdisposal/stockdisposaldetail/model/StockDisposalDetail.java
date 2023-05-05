package com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="mrp_stock_disposal_dtl")

public class StockDisposalDetail extends GeneralModelBase {
	
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	@Column(name = "stock_disposal_hdr_id")
	private Integer stockDisposalHdrId;
	
	
	@Column(name = "stock_item_id")
	private Integer stockItemId ;	
	
	@Column(name = "stock_item_code")
	private String stockItemCode;	
	
	@Column(name = "stock_item_name")
	private String stockItemName;	
	
	@Column(name = "dispose_qty")
	private Double disposeQty;	
	
	@Column(name = "cost_price")
	private Double costPrice;	
	
	@Column(name = "reason")
	private Integer reason=0;	

	
	
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
	@Counter(module="stock_disposal_dtl", key="stock_disposal_dtl")
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the stockDisposalHdrId
	 */
	public Integer getStockDisposalHdrId() {
		return stockDisposalHdrId;
	}

	/**
	 * @param stockDisposalHdrId the stockDisposalHdrId to set
	 */
	public void setStockDisposalHdrId(Integer stockDisposalHdrId) {
		this.stockDisposalHdrId = stockDisposalHdrId;
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
	 * @return the disposeQty
	 */
	public Double getDisposeQty() {
		return disposeQty;
	}

	/**
	 * @param disposeQty the disposeQty to set
	 */
	public void setDisposeQty(Double disposeQty) {
		this.disposeQty = disposeQty;
	}

	/**
	 * @return the costPrice
	 */
	public Double getCostPrice() {
		return costPrice;
	}

	/**
	 * @param costPrice the costPrice to set
	 */
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	/**
	 * @return the reason
	 */
	public Integer getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(Integer reason) {
		this.reason = reason;
	}

	
	
}
