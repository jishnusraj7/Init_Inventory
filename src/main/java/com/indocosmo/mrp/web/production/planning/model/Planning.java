package com.indocosmo.mrp.web.production.planning.model;

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
@Table(name = "order_hdrs_booking")
public class Planning extends GeneralModelBase {
	
	@Column(name = "closing_date")
	private String closing_date;
	
	@Column(name = "closing_time_slot_id")
	private Integer closing_time_slot_id;
	
	@Column(name = "created_at")
	private String created_at;
	
	@Column(name = "created_by")
	private Integer created_by;
	
	@Column(name = "customer_address")
	private String customer_address;
	
	@Column(name = "customer_contact1")
	private String customer_contact1;
	
	@Column(name = "customer_contact2")
	private String customer_contact2;
	
	@Column(name = "customer_name")
	private String customer_name;
	
	@Column(name = "customer_id")
	private Integer customer_id;
	
	@Column(name = "department_id")
	private Integer department_id;
	
	@Transient
	private String del_dtl_id;
	
	
	@Transient
	private String productionIds;
	
	
	public Integer getDepartment_id() {
		return department_id;
	}


	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}


	/**
	 * @return the productionIds
	 */
	public String getProductionIds() {
	
		return productionIds;
	}

	
	/**
	 * @param productionIds the productionIds to set
	 */
	public void setProductionIds(String productionIds) {
	
		this.productionIds = productionIds;
	}

	@Transient
	private Integer hdrFlag;
	
	@Column(name = "is_ar_customer")
	private Integer is_ar_customer;
	
	@Column(name = "last_sync_at")
	private String last_sync_at;
	
	@Column(name = "order_date")
	private String order_date;
	
	@Pk
	@Id(generationType = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Integer order_id;
	
	@Column(name = "order_no")
	private String order_no;
	
	@Column(name = "order_time")
	private String order_time;
	
	@Transient
	private String planningDetailLists;
	
	@Transient
	private String customerList;

	
	
	/**
	 * @return the customerList
	 */
	public String getCustomerList() {
	
		return customerList;
	}


	
	/**
	 * @param customerList the customerList to set
	 */
	public void setCustomerList(String customerList) {
	
		this.customerList = customerList;
	}

	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "shop_code")
	private String shop_code;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "sync_status")
	private Integer sync_status;
	
	@Column(name = "total_amount")
	private Double total_amount;
	
	@Column(name = "total_amount_paid")
	private Double total_amount_paid;
	
	@Column(name = "total_balance")
	private Double total_balance;
	
	@Column(name = "updated_at")
	private String updated_at;
	
	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "user_id")
	private Integer user_id;
	
	
	@Transient
	private Integer shopId1;
	
	public String getClosing_date() {
	
		return closing_date;
	}
	
	
	
	public String getCreated_at() {
	
		return created_at;
	}
	
	public Integer getCreated_by() {
	
		return created_by;
	}
	
	public String getCustomer_address() {
	
		return customer_address;
	}
	
	public String getCustomer_contact1() {
	
		return customer_contact1;
	}
	
	public String getCustomer_contact2() {
	
		return customer_contact2;
	}
	
	public String getCustomer_name() {
	
		return customer_name;
	}
	
	public String getDel_dtl_id() {
	
		return del_dtl_id;
	}
	
	public Integer getHdrFlag() {
	
		return hdrFlag;
	}
	
	public Integer getIs_ar_customer() {
	
		return is_ar_customer;
	}
	
	public String getLast_sync_at() {
	
		return last_sync_at;
	}
	
	public String getOrder_date() {
	
		return order_date;
	}
	
	public Integer getOrder_id() {
	
		return order_id;
	}
	
	public String getOrder_no() {
	
		return order_no;
	}
	
	public String getOrder_time() {
	
		return order_time;
	}
	
	public String getPlanningDetailLists() {
	
		return planningDetailLists;
	}
	
	public String getRemarks() {
	
		return remarks;
	}
	
	public String getShop_code() {
	
		return shop_code;
	}
	
	public Integer getStatus() {
	
		return status;
	}
	
	public Integer getSync_status() {
	
		return sync_status;
	}
	
	public Double getTotal_amount() {
	
		return total_amount;
	}
	
	public Double getTotal_amount_paid() {
	
		return total_amount_paid;
	}
	
	public Double getTotal_balance() {
	
		return total_balance;
	}
	
	public String getUpdated_at() {
	
		return updated_at;
	}
	
	public Integer getUpdated_by() {
	
		return updated_by;
	}
	
	public Integer getUser_id() {
	
		return user_id;
	}
	
	public void setClosing_date(String closing_date) {
	
		this.closing_date = closing_date;
	}
	
	
	
	
	/**
	 * @return the closing_time_slot_id
	 */
	public Integer getClosing_time_slot_id() {
	
		return closing_time_slot_id;
	}


	
	/**
	 * @param closing_time_slot_id the closing_time_slot_id to set
	 */
	public void setClosing_time_slot_id(Integer closing_time_slot_id) {
	
		this.closing_time_slot_id = closing_time_slot_id;
	}


	public void setCreated_at(String created_at) {
	
		this.created_at = created_at;
	}
	
	public void setCreated_by(Integer created_by) {
	
		this.created_by = created_by;
	}
	
	public void setCustomer_address(String customer_address) {
	
		this.customer_address = customer_address;
	}
	
	public void setCustomer_contact1(String customer_contact1) {
	
		this.customer_contact1 = customer_contact1;
	}
	
	public void setCustomer_contact2(String customer_contact2) {
	
		this.customer_contact2 = customer_contact2;
	}
	
	public void setCustomer_name(String customer_name) {
	
		this.customer_name = customer_name;
	}
	
	public void setDel_dtl_id(String del_dtl_id) {
	
		this.del_dtl_id = del_dtl_id;
	}
	
	public void setHdrFlag(Integer hdrFlag) {
	
		this.hdrFlag = hdrFlag;
	}
	
	public void setIs_ar_customer(Integer is_ar_customer) {
	
		this.is_ar_customer = is_ar_customer;
	}
	
	public void setLast_sync_at(String last_sync_at) {
	
		this.last_sync_at = last_sync_at;
	}
	
	public void setOrder_date(String order_date) {
	
		this.order_date = order_date;
	}
	
	/**
	 * @param id
	 *            the id to set
	 */
	@Id(generationType = GenerationType.COUNTER)
	@Counter(module = "order_hdrs_booking", key = "order_hdrs_booking")
	public void setOrder_id(Integer order_id) {
	
		this.order_id = order_id;
	}
	
	public void setOrder_no(String order_no) {
	
		this.order_no = order_no;
	}
	
	public void setOrder_time(String order_time) {
	
		this.order_time = order_time;
	}
	
	public void setPlanningDetailLists(String planningDetailLists) {
	
		this.planningDetailLists = planningDetailLists;
	}
	
	public void setRemarks(String remarks) {
	
		this.remarks = remarks;
	}
	
	public void setShop_code(String shop_code) {
	
		this.shop_code = shop_code;
	}
	
	public void setStatus(Integer status) {
	
		this.status = status;
	}
	
	public void setSync_status(Integer sync_status) {
	
		this.sync_status = sync_status;
	}
	
	public void setTotal_amount(Double total_amount) {
	
		this.total_amount = total_amount;
	}
	
	public void setTotal_amount_paid(Double total_amount_paid) {
	
		this.total_amount_paid = total_amount_paid;
	}
	
	public void setTotal_balance(Double total_balance) {
	
		this.total_balance = total_balance;
	}
	
	public void setUpdated_at(String updated_at) {
	
		this.updated_at = updated_at;
	}
	
	public void setUpdated_by(Integer updated_by) {
	
		this.updated_by = updated_by;
	}
	
	public void setUser_id(Integer user_id) {
	
		this.user_id = user_id;
	}

	/**
	 * @return the shopId1
	 */
	public Integer getShopId1() {
		return shopId1;
	}

	/**
	 * @param shopId1 the shopId1 to set
	 */
	public void setShopId1(Integer shopId1) {
		this.shopId1 = shopId1;
	}

	/**
	 * @return the customer_id
	 */
	public Integer getCustomer_id() {
		return customer_id;
	}

	/**
	 * @param customer_id the customer_id to set
	 */
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	
}
