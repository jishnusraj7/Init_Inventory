package com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="mrp_stock_item_prod_costcalc")
public class ItemProdCost extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name="stock_item_id")
	private Integer stockItemId;
	
	@Column(name="costcalc_param_id")
	private Integer costcalc_param_id;
	
	
	@Column(name="is_percentage")
	private Integer is_percentage;
	
	@Column(name="rate")
	private Double rate=0.0;
	
	@Column(name="stock_item_qty")
	private Double stock_item_qty=0.0;

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
	@Counter(module="item_prod_costcalc", key="item_prod_costcalc")
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the costcalc_param_id
	 */
	public Integer getCostcalc_param_id() {
		return costcalc_param_id;
	}

	/**
	 * @param costcalc_param_id the costcalc_param_id to set
	 */
	public void setCostcalc_param_id(Integer costcalc_param_id) {
		this.costcalc_param_id = costcalc_param_id;
	}

	/**
	 * @return the is_percentage
	 */
	public Integer getIs_percentage() {
		return is_percentage;
	}

	/**
	 * @param is_percentage the is_percentage to set
	 */
	public void setIs_percentage(Integer is_percentage) {
		this.is_percentage = is_percentage;
	}

	/**
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
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

	



	
	

}
