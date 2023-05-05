package com.indocosmo.mrp.web.stock.purchaseorder.viewstockdetails.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "vw_itemstock")
public class ViewStockDetails  extends  GeneralModelBase{

	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "stock_item_id")
	private String stock_item_id;
	
	@Column(name = "current_stock")
	private String current_stock;
	
	/**
	 * @return the current_stock
	 */
	public String getCurrent_stock() {
		return current_stock;
	}

	/**
	 * @param current_stock the current_stock to set
	 */
	public void setCurrent_stock(String current_stock) {
		this.current_stock = current_stock;
	}

	@Column(name = "max_stock")
	private  String max_stock;
	
	@Column(name = "min_stock")
	private  String min_stock;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;

	/**
	 * @return the stock_item_id
	 */
	public String getStock_item_id() {
		return stock_item_id;
	}

	/**
	 * @param stock_item_id the stock_item_id to set
	 */
	public void setStock_item_id(String stock_item_id) {
		this.stock_item_id = stock_item_id;
	}

	

	/**
	 * @return the max_stock
	 */
	public String getMax_stock() {
		return max_stock;
	}

	/**
	 * @param max_stock the max_stock to set
	 */
	public void setMax_stock(String max_stock) {
		this.max_stock = max_stock;
	}

	/**
	 * @return the min_stock
	 */
	public String getMin_stock() {
		return min_stock;
	}

	/**
	 * @param min_stock the min_stock to set
	 */
	public void setMin_stock(String min_stock) {
		this.min_stock = min_stock;
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

	
	
	
}
