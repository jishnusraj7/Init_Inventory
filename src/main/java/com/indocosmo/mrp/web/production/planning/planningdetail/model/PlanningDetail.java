package com.indocosmo.mrp.web.production.planning.planningdetail.model;

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
@Table(name = "order_dtls_booking")
public class PlanningDetail extends GeneralModelBase {

	
	@Column(name = "qty")
	private Double quantity;	
	
	public Double getQuantity() {
		return quantity;
	}
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "order_date")
	private String order_date;	
	
	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	
	/**
	 * @return the status
	 */
	public Integer getStatus() {
	
		return status;
	}

	
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
	
		this.status = status;
	}

	@Column(name = "description")
	private String description;	
	
	@Column(name = "fixed_price")
	private Double fixed_price;
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Transient
	private Integer del_order_id;
	
	
	public Integer getDel_order_id() {
		return del_order_id;
	}

	public void setDel_order_id(Integer del_order_id) {
		this.del_order_id = del_order_id;
	}

	@Column(name = "is_combo_item")
	private Integer is_combo_item;

	@Column(name = "item_type")
	private Integer item_type;

	@Column(name = "last_sync_at")
	private String last_sync_at;

	@Column(name = "order_id")
	private Integer order_id;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "sale_item_code")
	private String sale_item_code;

	@Column(name = "sale_item_id")
	private Integer sale_item_id;

	@Column(name = "sub_class_code")
	private String sub_class_code;

	@Column(name = "sub_class_id")
	private Integer sub_class_id;

	@Column(name = "sub_class_name")
	private String sub_class_name;

	@Column(name = "uom_code")
	private String uom_code;

	@Column(name = "uom_name")
	private String uom_name;

	@Column(name = "uom_symbol")
	private String uom_symbol;
	
	@Transient
	private Double balanceqty;

	public String getDescription() {
		return description;
	}

	public Double getFixed_price() {
		return fixed_price;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIs_combo_item() {
		return is_combo_item;
	}

	public Integer getItem_type() {
		return item_type;
	}

	public String getLast_sync_at() {
		return last_sync_at;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getSale_item_code() {
		return sale_item_code;
	}

	public Integer getSale_item_id() {
		return sale_item_id;
	}

	public String getSub_class_code() {
		return sub_class_code;
	}

	public Integer getSub_class_id() {
		return sub_class_id;
	}

	public String getSub_class_name() {
		return sub_class_name;
	}

	public String getUom_code() {
		return uom_code;
	}

	public String getUom_name() {
		return uom_name;
	}

	public String getUom_symbol() {
		return uom_symbol;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFixed_price(Double fixed_price) {
		this.fixed_price = fixed_price;
	}
	
	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="order_dtls_booking", key="order_dtls_booking")
	public void setId(Integer id) {
		this.id = id;
	}	
	
	public void setIs_combo_item(Integer is_combo_item) {
		this.is_combo_item = is_combo_item;
	}	
	
	public void setItem_type(Integer item_type) {
		this.item_type = item_type;
	}	
	
	public void setLast_sync_at(String last_sync_at) {
		this.last_sync_at = last_sync_at;
	}	
	
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}	
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public void setSale_item_code(String sale_item_code) {
		this.sale_item_code = sale_item_code;
	}	
	
	public void setSale_item_id(Integer sale_item_id) {
		this.sale_item_id = sale_item_id;
	}
	
	public void setSub_class_code(String sub_class_code) {
		this.sub_class_code = sub_class_code;
	}
	
	public void setSub_class_id(Integer sub_class_id) {
		this.sub_class_id = sub_class_id;
	}
	
	public void setSub_class_name(String sub_class_name) {
		this.sub_class_name = sub_class_name;
	}	
	
	
	public void setUom_code(String uom_code) {
		this.uom_code = uom_code;
	}
	
	public void setUom_name(String uom_name) {
		this.uom_name = uom_name;
	}
	
	public void setUom_symbol(String uom_symbol) {
		this.uom_symbol = uom_symbol;
	}

	/**
	 * @return the balanceqty
	 */
	public Double getBalanceqty() {
		return balanceqty;
	}

	/**
	 * @param balanceqty the balanceqty to set
	 */
	public void setBalanceqty(Double balanceqty) {
		this.balanceqty = balanceqty;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

}
