package com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.model;

import java.sql.Timestamp;
import java.util.List;

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
@Table(name = "sale_item_discounts")
public class SaleItemDiscount extends GeneralModelBase{




	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="sale_item_discounts", key="sale_item_discounts")
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="discount_id")
	private Integer discount_id; 
	
	@Column(name="sale_item_id")
	private Integer sale_item_id; 
	
	@Column(name="price")
	private Double price;
	
	@Transient
	private String discnt_code;
	
	@Transient
	private String saleItem_name;

	/**
	 * @return the discount_id
	 */
	public Integer getDiscount_id() {
		return discount_id;
	}

	/**
	 * @param discount_id the discount_id to set
	 */
	public void setDiscount_id(Integer discount_id) {
		this.discount_id = discount_id;
	}

	/**
	 * @return the sale_item_id
	 */
	public Integer getSale_item_id() {
		return sale_item_id;
	}

	/**
	 * @param sale_item_id the sale_item_id to set
	 */
	public void setSale_item_id(Integer sale_item_id) {
		this.sale_item_id = sale_item_id;
	}

	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the discnt_code
	 */
	public String getDiscnt_code() {
		return discnt_code;
	}

	/**
	 * @param discnt_code the discnt_code to set
	 */
	public void setDiscnt_code(String discnt_code) {
		this.discnt_code = discnt_code;
	}

	/**
	 * @return the saleItem_name
	 */
	public String getSaleItem_name() {
		return saleItem_name;
	}

	/**
	 * @param saleItem_name the saleItem_name to set
	 */
	public void setSaleItem_name(String saleItem_name) {
		this.saleItem_name = saleItem_name;
	} 
	
	
	
	
}