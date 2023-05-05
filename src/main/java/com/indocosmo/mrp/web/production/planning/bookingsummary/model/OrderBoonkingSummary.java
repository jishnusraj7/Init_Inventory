package com.indocosmo.mrp.web.production.planning.bookingsummary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "order_booking_summary")
public class OrderBoonkingSummary extends GeneralModelBase {
 
	@Column(name = "order_qty")
	private Double order_qty;	

	@Column(name = "issued_qty")
	private Double issued_qty=0.0;
	
	@Column(name = "shop_id")
	private Integer shop_id;
		
	@Column(name = "ext_ref_id")
	private String ext_ref_id;
	
	@Column(name = "ext_ref_no")
	private String ext_ref_no;
		
	@Column(name = "stock_item_id")
	private Integer stock_item_id;	
	
	@Column(name = "trans_date")
	private String trans_date;	
	
	@Column(name = "order_date")
	private String order_date;	
	
	@Column(name = "created_by")
	private Integer created_by;	
	
	@Column(name = "created_at")
	private String created_at;	
	
	@Column(name = "updated_by")
	private Integer updated_by;	
	
	@Column(name = "updated_at")
	private String updated_at;	
	
	@Transient
	private String shop_code;
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "trans_type")
	private Integer trans_type;	
	
	/**
	 * @return the order_qty
	 */
	public Double getOrder_qty() {
	
		return order_qty;
	}

	
	/**
	 * @return the issued_qty
	 */
	public Double getIssued_qty() {
	
		return issued_qty;
	}

	
	/**
	 * @return the shop_id
	 */
	public Integer getShop_id() {
	
		return shop_id;
	}
	
	/**
	 * @return the stock_item_id
	 */
	public Integer getStock_item_id() {
	
		return stock_item_id;
	}
	
	/**
	 * @return the trans_date
	 */
	public String getTrans_date() {
	
		return trans_date;
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
	 * @return the shop_code
	 */
	public String getShop_code() {
	
		return shop_code;
	}
	
	/**
	 * @param order_qty the order_qty to set
	 */
	public void setOrder_qty(Double order_qty) {
	
		this.order_qty = order_qty;
	}

	/**
	 * @param issued_qty the issued_qty to set
	 */
	public void setIssued_qty(Double issued_qty) {
	
		this.issued_qty = issued_qty;
	}
	
	/**
	 * @param shop_id the shop_id to set
	 */
	public void setShop_id(Integer shop_id) {
	
		this.shop_id = shop_id;
	}
	
	/**
	 * @return the ext_ref_id
	 */
	public String getExt_ref_id() {
	
		return ext_ref_id;
	}
	
	/**
	 * @param ext_ref_id the ext_ref_id to set
	 */
	public void setExt_ref_id(String ext_ref_id) {
	
		this.ext_ref_id = ext_ref_id;
	}

	/**
	 * @param stock_item_id the stock_item_id to set
	 */
	public void setStock_item_id(Integer stock_item_id) {
	
		this.stock_item_id = stock_item_id;
	}
	
	/**
	 * @param trans_date the trans_date to set
	 */
	public void setTrans_date(String trans_date) {
	
		this.trans_date = trans_date;
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

	
	/**
	 * @param shop_code the shop_code to set
	 */
	public void setShop_code(String shop_code) {
	
		this.shop_code = shop_code;
	}

	/**
	 * @return the order_date
	 */
	public String getOrder_date() {
	
		return order_date;
	}
	
	/**
	 * @param order_date the order_date to set
	 */
	public void setOrder_date(String order_date) {
	
		this.order_date = order_date;
	}
			
	/**
	 * @return the trans_type
	 */
	public Integer getTrans_type() {
	
		return trans_type;
	}
	
	/**
	 * @param trans_type the trans_type to set
	 */
	public void setTrans_type(Integer trans_type) {
	
		this.trans_type = trans_type;
	}

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
	 * @return
	 */
	public String getExt_ref_no() {
		return ext_ref_no;
	}

	/**
	 * @param ext_ref_no
	 */
	public void setExt_ref_no(String ext_ref_no) {
		this.ext_ref_no = ext_ref_no;
	}
}