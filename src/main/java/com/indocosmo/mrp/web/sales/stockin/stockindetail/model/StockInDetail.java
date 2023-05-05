package com.indocosmo.mrp.web.sales.stockin.stockindetail.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="stock_in_dtl")
public class StockInDetail extends GeneralModelBase{

	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Pk
	@Column(name = "stock_in_hdr_id")
	private Integer stockInHdrId;
		
	@Column(name = "stock_item_id")
	private Integer stockItemId;
	
	@Column( name ="description")
	private String description;
	

	@Column( name ="qty_received")
	private Double receivedQty=0.00;
	
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
	 * @return the stockInHdrId
	 */
	public Integer getStockInHdrId() {
		return stockInHdrId;
	}

	/**
	 * @param stockInHdrId the stockInHdrId to set
	 */
	public void setStockInHdrId(Integer stockInHdrId) {
		this.stockInHdrId = stockInHdrId;
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
	 * @return the receivedQty
	 */
	public Double getReceivedQty() {
		return receivedQty;
	}

	/**
	 * @param receivedQty the receivedQty to set
	 */
	public void setReceivedQty(Double receivedQty) {
		this.receivedQty = receivedQty;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}