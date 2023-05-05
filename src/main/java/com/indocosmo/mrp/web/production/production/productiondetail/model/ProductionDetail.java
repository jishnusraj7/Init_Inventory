package com.indocosmo.mrp.web.production.production.productiondetail.model;

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
@Table(name="mrp_prod_dtl")
public class ProductionDetail extends GeneralModelBase{

	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Pk
	@Column(name = "prod_hdr_id")
	private Integer prodHdrId;

	@Column(name = "stock_item_id")
	private Integer stockItemId;


	@Column(name = "department_id")
	private Integer department_id;



	public Integer getDepartment_id() {

		return department_id;
	}




	public void setDepartment_id(Integer department_id) {

		this.department_id = department_id;
	}

	@Column( name ="stock_item_code")
	private String stockItemCode;

	@Column( name ="schedule_qty")
	private Double scheduleQty;

	@Column( name ="order_qty")
	private Double orderQty;

	@Column( name ="prod_qty")
	private Double prodQty;

	@Column( name ="remarks")
	private String remarks;

	@Column(name = "user_id")
	private Integer user_id;

	@Column(name = "last_sync_at")
	private Integer last_sync_at;

	/*@Column(name="cost_price")
	private Double itemRate;*/

	@Column(name="material_cost")
	private Double material_cost;

	@Column(name="other_cost")
	private Double other_cost;

	@Column(name="total_cost")
	private Double total_cost;

	@Column(name="sales_price")
	private Double sales_price;





	@Column(name="damage_qty")
	private Double damage_qty;

	/*@Column( name ="production_date")
	private String production_date;

*/
	
	/**
	 * @return the production_date
	 */
	/*public String getProduction_date() {
	
		return production_date;
	}*/




	
	/**
	 * @param production_date the production_date to set
	 */
	/*public void setProduction_date(String production_date) {
	
		this.production_date = production_date;
	}*/

	@Transient
	private Integer prodnHdrId;
	
	
	

	@Transient
	private Integer stockRegDtl_id;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}




	/**
	 * @return the last_sync_at
	 */
	public Integer getLast_sync_at() {
		return last_sync_at;
	}

	/**
	 * @return the orderQty
	 */
	public Double getOrderQty() {
		return orderQty;
	}

	/**
	 * @return the prodHdrId
	 */
	public Integer getProdHdrId() {
		return prodHdrId;
	}

	/**
	 * @return the prodnHdrId
	 */
	public Integer getProdnHdrId() {
		return prodnHdrId;
	}

	/**
	 * @return the prodQty
	 */
	public Double getProdQty() {
		return prodQty;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}


	/**
	 * @return the scheduleQty
	 */
	public Double getScheduleQty() {
		return scheduleQty;
	}

	/**
	 * @return the stockItemCode
	 */
	public String getStockItemCode() {
		return stockItemCode;
	}

	/**
	 * @return the stockItemId
	 */
	public Integer getStockItemId() {
		return stockItemId;
	}

	/**
	 * @return the stockRegDtl_id
	 */
	public Integer getStockRegDtl_id() {
		return stockRegDtl_id;
	}



	/**
	 * @return the user_id
	 */
	public Integer getUser_id() {
		return user_id;
	}



	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="prod_dtl", key="prod_dtl")
	public void setId(Integer id) {
		this.id = id;
	}





	/**
	 * @param last_sync_at the last_sync_at to set
	 */
	public void setLast_sync_at(Integer last_sync_at) {
		this.last_sync_at = last_sync_at;
	}



	/**
	 * @param orderQty the orderQty to set
	 */
	public void setOrderQty(Double orderQty) {
		this.orderQty = orderQty;
	}

	/**
	 * @param prodHdrId the prodHdrId to set
	 */
	public void setProdHdrId(Integer prodHdrId) {
		this.prodHdrId = prodHdrId;
	}

	/**
	 * @param prodnHdrId the prodnHdrId to set
	 */
	public void setProdnHdrId(Integer prodnHdrId) {
		this.prodnHdrId = prodnHdrId;
	}

	/**
	 * @param prodQty the prodQty to set
	 */
	public void setProdQty(Double prodQty) {
		this.prodQty = prodQty;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	/**
	 * @param scheduleQty the scheduleQty to set
	 */
	public void setScheduleQty(Double scheduleQty) {
		this.scheduleQty = scheduleQty;
	}

	/**
	 * @param stockItemCode the stockItemCode to set
	 */
	public void setStockItemCode(String stockItemCode) {
		this.stockItemCode = stockItemCode;
	}

	/**
	 * @param stockItemId the stockItemId to set
	 */
	public void setStockItemId(Integer stockItemId) {
		this.stockItemId = stockItemId;
	}

	/**
	 * @param stockRegDtl_id the stockRegDtl_id to set
	 */
	public void setStockRegDtl_id(Integer stockRegDtl_id) {
		this.stockRegDtl_id = stockRegDtl_id;
	}



	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}




	/**
	 * @return the material_cost
	 */
	public Double getMaterial_cost() {
		return material_cost;
	}




	/**
	 * @param material_cost the material_cost to set
	 */
	public void setMaterial_cost(Double material_cost) {
		this.material_cost = material_cost;
	}




	/**
	 * @return the other_cost
	 */
	public Double getOther_cost() {
		return other_cost;
	}




	/**
	 * @param other_cost the other_cost to set
	 */
	public void setOther_cost(Double other_cost) {
		this.other_cost = other_cost;
	}




	/**
	 * @return the total_cost
	 */
	public Double getTotal_cost() {
		return total_cost;
	}




	/**
	 * @param total_cost the total_cost to set
	 */
	public void setTotal_cost(Double total_cost) {
		this.total_cost = total_cost;
	}




	/**
	 * @return the sales_price
	 */
	public Double getSales_price() {
		return sales_price;
	}




	/**
	 * @param sales_price the sales_price to set
	 */
	public void setSales_price(Double sales_price) {
		this.sales_price = sales_price;
	}








	/**
	 * @return the damage_qty
	 */
	public Double getDamage_qty() {
		return damage_qty;
	}




	/**
	 * @param damage_qty the damage_qty to set
	 */
	public void setDamage_qty(Double damage_qty) {
		this.damage_qty = damage_qty;
	}











}