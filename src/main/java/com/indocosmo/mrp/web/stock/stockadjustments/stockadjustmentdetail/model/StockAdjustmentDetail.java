package com.indocosmo.mrp.web.stock.stockadjustments.stockadjustmentdetail.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="mrp_stock_adjust_dtl")

public class StockAdjustmentDetail extends GeneralModelBase {





	@Column(name = "actual_qty")
	private Double actual_qty ;


	@Column(name = "adjust_qty")
	private Double adjust_qty ;


	@Column(name = "batch_no")
	private String batch_no ;



	@Column(name = "cost_price")
	private Double cost_price ;



	@Column(name = "diff_qty")
	private Double diff_qty ;



	@Column(name = "expiry_date")
	private String 	expiry_date ;	




	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;


	@Column(name = "stock_adjust_hdr_id")
	private Integer stock_adjust_hdr_id;	

	@Column(name = "stock_item_code")
	private String stock_item_code;

	@Column(name = "stock_item_id")
	private Integer stock_item_id;

	@Column(name = "stock_item_name")
	private String stock_item_name;

	@Column(name = "system_qty")
	private Double 	system_qty ;



	public Double getActual_qty() {
		return actual_qty;
	}


	public Double getAdjust_qty() {
		return adjust_qty;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public Double getCost_price() {
		return cost_price;
	}

	public Double getDiff_qty() {
		return diff_qty;
	}

	public String getExpiry_date() {
		return expiry_date;
	}

	public Integer getId() {
		return id;
	}

	public Integer getStock_adjust_hdr_id() {
		return stock_adjust_hdr_id;
	}

	

	public String getStock_item_code() {
		return stock_item_code;
	}





	public Integer getStock_item_id() {
		return stock_item_id;
	}


	public String getStock_item_name() {
		return stock_item_name;
	}


	public Double getSystem_qty() {
		return system_qty;
	}


	public void setActual_qty(Double actual_qty) {
		this.actual_qty = actual_qty;
	}


	public void setAdjust_qty(Double adjust_qty) {
		this.adjust_qty = adjust_qty;
	}


	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}


	public void setCost_price(Double cost_price) {
		this.cost_price = cost_price;
	}


	public void setDiff_qty(Double diff_qty) {
		this.diff_qty = diff_qty;
	}


	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}

	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="stock_adjust_dtl", key="stock_adjust_dtl")
	public void setId(Integer id) {
		this.id = id;
	}

	public void setStock_adjust_hdr_id(Integer stock_adjust_hdr_id) {
		this.stock_adjust_hdr_id = stock_adjust_hdr_id;
	}

	public void setStock_item_code(String stock_item_code) {
		this.stock_item_code = stock_item_code;
	}	

	public void setStock_item_id(Integer stock_item_id) {
		this.stock_item_id = stock_item_id;
	}	

	public void setStock_item_name(String stock_item_name) {
		this.stock_item_name = stock_item_name;
	}	

	public void setSystem_qty(Double system_qty) {
		this.system_qty = system_qty;
	}	

}
