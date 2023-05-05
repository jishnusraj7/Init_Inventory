package com.indocosmo.mrp.web.production.production.model;

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
@Table(name = "mrp_prod_hdr")
public class Production extends GeneralModelBase {


	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column( name ="prod_date")
	private String schedDate;
	
	@Column( name ="prod_upto")
	private String prodUpTo;
	

	
	/**
	 * @return the prodUpTo
	 */
	public String getProdUpTo() {
	
		return prodUpTo;
	}



	
	/**
	 * @param prodUpTo the prodUpTo to set
	 */
	public void setProdUpTo(String prodUpTo) {
	
		this.prodUpTo = prodUpTo;
	}


	@Column(name = "prod_time")
	private String prod_time;

	@Transient
	private Integer department_id;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "last_sync_at")
	private String last_sync_at;



	@Column(name = "prod_no")
	private String prod_no;

	@Column(name = "shop_id")
	private Integer shop_id;



	@Column(name = "status")
	private Integer status;

	/*@Column(name="total_stock_amount")
	private Double total_stock_amount;

	@Column(name="total_sales_amount")
	private Double total_sales_amount;*/

	
	/**
	 * @return the shop_id
	 */
	public Integer getShop_id() {
	
		return shop_id;
	}


	
	/**
	 * @param shop_id the shop_id to set
	 */
	public void setShop_id(Integer shop_id) {
	
		this.shop_id = shop_id;
	}


	@Transient
	private String dailyPlanningDetailLists;

	@Transient
	private String orderHdrsIdsList;



	@Transient
	private String remarksProd;



	@Transient
	private String stockreg_id;

	/**
	 * @return the dailyPlanningDetailLists
	 */
	public String getDailyPlanningDetailLists() {
		return dailyPlanningDetailLists;
	}


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}



	/**
	 * @return the last_sync_at
	 */
	public String getLast_sync_at() {
		return last_sync_at;
	}



	/**
	 * @return the prod_no
	 */
	public String getProd_no() {
		return prod_no;
	}



	/**
	 * @return the prod_time
	 */
	public String getProd_time() {
		return prod_time;
	}
	/**
	 * @return the prodDate
	 */

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return the remarksProd
	 */
	public String getRemarksProd() {
		return remarksProd;
	}



	/**
	 * @return the schedDate
	 */
	public String getSchedDate() {
		return schedDate;
	}



	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}



	/**
	 * @return the stockreg_id
	 */
	public String getStockreg_id() {
		return stockreg_id;
	}







	/**
	 * @param dailyPlanningDetailLists the dailyPlanningDetailLists to set
	 */
	public void setDailyPlanningDetailLists(String dailyPlanningDetailLists) {
		this.dailyPlanningDetailLists = dailyPlanningDetailLists;
	}






	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="prod_hdr", key="prod_hdr")
	public void setId(Integer id) {
		this.id = id;
	}



	/**
	 * @param last_sync_at the last_sync_at to set
	 */
	public void setLast_sync_at(String last_sync_at) {
		this.last_sync_at = last_sync_at;
	}



	/**
	 * @param prod_no the prod_no to set
	 */
	public void setProd_no(String prod_no) {
		this.prod_no = prod_no;
	}



	/**
	 * @param prod_time the prod_time to set
	 */
	public void setProd_time(String prod_time) {
		this.prod_time = prod_time;
	}



	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	/**
	 * @param remarksProd the remarksProd to set
	 */
	public void setRemarksProd(String remarksProd) {
		this.remarksProd = remarksProd;
	}






	/**
	 * @param schedDate the schedDate to set
	 */
	public void setSchedDate(String schedDate) {
		this.schedDate = schedDate;
	}



	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}



	/**
	 * @param stockreg_id the stockreg_id to set
	 */
	public void setStockreg_id(String stockreg_id) {
		this.stockreg_id = stockreg_id;
	}


	/**
	 * @return the orderHdrsIdsList
	 */
	public String getOrderHdrsIdsList() {
		return orderHdrsIdsList;
	}


	/**
	 * @param orderHdrsIdsList the orderHdrsIdsList to set
	 */
	public void setOrderHdrsIdsList(String orderHdrsIdsList) {
		this.orderHdrsIdsList = orderHdrsIdsList;
	}


	/**
	 * @return the total_stock_amount
	 *//*
	public Double getTotal_stock_amount() {
		return total_stock_amount;
	}


	  *//**
	  * @param total_stock_amount the total_stock_amount to set
	  *//*
	public void setTotal_stock_amount(Double total_stock_amount) {
		this.total_stock_amount = total_stock_amount;
	}


	   *//**
	   * @return the total_sales_amount
	   *//*
	public Double getTotal_sales_amount() {
		return total_sales_amount;
	}


	    *//**
	    * @param total_sales_amount the total_sales_amount to set
	    *//*
	public void setTotal_sales_amount(Double total_sales_amount) {
		this.total_sales_amount = total_sales_amount;
	}*/
	
	/*
	 * @return the source_department_id
	 */
	public Integer getDepartment_id() {
		return department_id;
	}

	/*
	 * @param source_department_id the source_department_id to set
	 */
	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}
}
