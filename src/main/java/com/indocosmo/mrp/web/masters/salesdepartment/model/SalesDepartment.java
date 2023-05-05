package com.indocosmo.mrp.web.masters.salesdepartment.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.MasterModelBase;

@Entity
@Table(name = "departments")
public class SalesDepartment extends MasterModelBase{

	
	@Override
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="sales_departments", key="sales_departments")
	public void setId(Integer id) {
	
		super.setId(id);
		
		
	}
	
	@Transient
	private List<SalesDepartment> salesExcelData;
	
	public List<SalesDepartment> getSalesExcelData() {
		return salesExcelData;
	}

	public void setSalesExcelData(List<SalesDepartment> salesExcelData) {
		this.salesExcelData = salesExcelData;
	}

	@Column(name = "created_by")
	private Integer created_by = 0;

	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "updated_at")
	private String updated_at;
	
	@Column(name = "sales_account_code")
	private String sales_account_code;
	
	@Column(name = "purchase_account_code")
	private String purchase_account_code;
	
	@Column(name = "stock_account_code")
	private String stock_account_code;
	
	@Column(name = "cogs_account_code")
	private String cogs_account_code;
	
	
	@Column(name = "wages_account_code")
	private String wages_account_code;
	
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
	 * @return the sales_account_code
	 */
	public String getSales_account_code() {
		return sales_account_code;
	}

	/**
	 * @return the purchase_account_code
	 */
	public String getPurchase_account_code() {
		return purchase_account_code;
	}

	/**
	 * @return the stock_account_code
	 */
	public String getStock_account_code() {
		return stock_account_code;
	}

	/**
	 * @return the cogs_account_code
	 */
	public String getCogs_account_code() {
		return cogs_account_code;
	}

	/**
	 * @return the wages_account_code
	 */
	public String getWages_account_code() {
		return wages_account_code;
	}

	/**
	 * @return the gst_collected_account_code
	 */
	public String getGst_collected_account_code() {
		return gst_collected_account_code;
	}

	/**
	 * @return the gst_paid_account_code
	 */
	public String getGst_paid_account_code() {
		return gst_paid_account_code;
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
	 * @param sales_account_code the sales_account_code to set
	 */
	public void setSales_account_code(String sales_account_code) {
		this.sales_account_code = sales_account_code;
	}

	/**
	 * @param purchase_account_code the purchase_account_code to set
	 */
	public void setPurchase_account_code(String purchase_account_code) {
		this.purchase_account_code = purchase_account_code;
	}

	/**
	 * @param stock_account_code the stock_account_code to set
	 */
	public void setStock_account_code(String stock_account_code) {
		this.stock_account_code = stock_account_code;
	}

	/**
	 * @param cogs_account_code the cogs_account_code to set
	 */
	public void setCogs_account_code(String cogs_account_code) {
		this.cogs_account_code = cogs_account_code;
	}

	/**
	 * @param wages_account_code the wages_account_code to set
	 */
	public void setWages_account_code(String wages_account_code) {
		this.wages_account_code = wages_account_code;
	}

	/**
	 * @param gst_collected_account_code the gst_collected_account_code to set
	 */
	public void setGst_collected_account_code(String gst_collected_account_code) {
		this.gst_collected_account_code = gst_collected_account_code;
	}

	/**
	 * @param gst_paid_account_code the gst_paid_account_code to set
	 */
	public void setGst_paid_account_code(String gst_paid_account_code) {
		this.gst_paid_account_code = gst_paid_account_code;
	}
	@Column(name = "gst_collected_account_code")
	private String gst_collected_account_code;
	
	@Column(name = "gst_paid_account_code")
	private String gst_paid_account_code;
	
	
	
}
