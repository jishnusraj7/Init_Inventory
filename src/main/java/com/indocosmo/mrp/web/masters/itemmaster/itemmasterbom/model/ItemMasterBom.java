package com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model;

import javax.persistence.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="stock_item_bom")
public class ItemMasterBom extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name="stock_item_id")
	private Integer stockItemId;
	
	@Column(name="bom_item_id")
	private Integer bomItemId;
	
	@Column(name="bom_item_qty")
	private Double qty;
	
	@Column(name="stock_item_qty")
	private Double stock_item_qty;
	
	@Column(name="cost_price")
	private Double cost_price=0.0;
	
	@Column(name="base_item_id")
	private Integer baseItemId;
	
    
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	@Transient
	private Integer stockBomItemId;
	
	@Transient
	private Integer stockValue;
	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="stockitem_bom", key="stockitem_bom")
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the stockBomItemId
	 */
	public Integer getStockBomItemId() {
		return stockBomItemId;
	}

	/**
	 * @param stockBomItemId the stockBomItemId to set
	 */
	public void setStockBomItemId(Integer stockBomItemId) {
		this.stockBomItemId = stockBomItemId;
	}

	/**
	 * @return the stockValue
	 */
	public Integer getStockValue() {
		return stockValue;
	}

	/**
	 * @param stockValue the stockValue to set
	 */
	public void setStockValue(Integer stockValue) {
		this.stockValue = stockValue;
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
	 * @return the bomItemId
	 */
	public Integer getBomItemId() {
		return bomItemId;
	}

	/**
	 * @param bomItemId the bomItemId to set
	 */
	public void setBomItemId(Integer bomItemId) {
		this.bomItemId = bomItemId;
	}

	/**
	 * @return the qty
	 */
	public Double getQty() {
		return qty;
	}


	public Double getCost_price() {
		return cost_price;
	}

	public void setCost_price(Double cost_price) {
		this.cost_price = cost_price;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(Double qty) {
		this.qty = qty;
	}

	/**
	 * @return the stock_item_qty
	 */
	public Double getStock_item_qty() {
		return stock_item_qty;
	}

	/**
	 * @param stock_item_qty the stock_item_qty to set
	 */
	public void setStock_item_qty(Double stock_item_qty) {
		this.stock_item_qty = stock_item_qty;
	}

	/**
	 * @return the baseItemId
	 */
	public Integer getBaseItemId() {
		return baseItemId;
	}

	/**
	 * @param baseItemId the baseItemId to set
	 */
	public void setBaseItemId(Integer baseItemId) {
		this.baseItemId = baseItemId;
	}



	
	

}
