package com.indocosmo.mrp.web.report.nonmoving.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
public class NonMovingStock extends GeneralModelBase{
	
	@Column(name = "stock_item_id")
	private String stock_item_id;
	
	@Transient
	private String startdate;
	
	@Transient
	private String enddate;
	
	@Transient
	private String days;
	
	@Transient
	private Integer item_category_id;
	
	@Transient
	private String department_name;

	@Column(name = "department_id")	
	private String department_id;

	@Column(name = "code")	
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "current_stock")
	private Double current_stock;

	private String categoryName;

	@Transient
	private String pdfExcel;
	
	@Transient
	private String reportName;

	@Column(name = "min_stock")
	private String min_stock;

	@Column(name = "max_stock")
	private String max_stock;
	
	@Column(name = "std_purchase_qty")
	private String std_purchase_qty;

	@Transient
	Boolean  itemstockReportType;

	@Transient
	private List<NonMovingStock> itemstock;
	@Transient
	List<Integer> itemid=new ArrayList<Integer>();
	
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the current_stock
	 */
	public Double getCurrent_stock() {
		return current_stock;
	}

	/**
	 * @return the days
	 */
	public String getDays() {
		return days;
	}
	
	
	public String getDepartment_id() {
		return department_id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	/**
	 * @return the enddate
	 */
	public String getEnddate() {
		return enddate;
	}
	
	/**
	 * @return the item_category_id
	 */
	public Integer getItem_category_id() {
		return item_category_id;
	}
	
	/**
	 * @return the itemid
	 */
	public List<Integer> getItemid() {
		return itemid;
	}
	
	/**
	 * @return the itemstock
	 */
	public List<NonMovingStock> getItemstock() {
		return itemstock;
	}

	/**
	 * @return the itemstockReportType
	 */
	public Boolean getItemstockReportType() {
		return itemstockReportType;
	}
	
	/**
	 * @return the max_stock
	 */
	public String getMax_stock() {
		return max_stock;
	}

	/**
	 * @return the min_stock
	 */
	public String getMin_stock() {
		return min_stock;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the pdfExcel
	 */
	public String getPdfExcel() {
		return pdfExcel;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @return the startdate
	 */
	public String getStartdate() {
		return startdate;
	}

	
	/**
	 * @return the std_purchase_qty
	 */
	public String getStd_purchase_qty() {
		return std_purchase_qty;
	}

	/**
	 * @return the stock_item_id
	 */
	public String getStock_item_id() {
		return stock_item_id;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @param current_stock the current_stock to set
	 */
	public void setCurrent_stock(Double current_stock) {
		this.current_stock = current_stock;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(String days) {
		this.days = days;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	/**
	 * @param enddate the enddate to set
	 */
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	/**
	 * @param item_category_id the item_category_id to set
	 */
	public void setItem_category_id(Integer item_category_id) {
		this.item_category_id = item_category_id;
	}

	/**
	 * @param itemid the itemid to set
	 */
	public void setItemid(List<Integer> itemid) {
		this.itemid = itemid;
	}

	/**
	 * @param itemstock the itemstock to set
	 */
	public void setItemstock(List<NonMovingStock> itemstock) {
		this.itemstock = itemstock;
	}

	/**
	 * @param itemstockReportType the itemstockReportType to set
	 */
	public void setItemstockReportType(Boolean itemstockReportType) {
		this.itemstockReportType = itemstockReportType;
	}

	/**
	 * @param max_stock the max_stock to set
	 */
	public void setMax_stock(String max_stock) {
		this.max_stock = max_stock;
	}

	/**
	 * @param min_stock the min_stock to set
	 */
	public void setMin_stock(String min_stock) {
		this.min_stock = min_stock;
	}
	
	/*@Transient
	private Integer item_category_id;*/

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param pdfExcel the pdfExcel to set
	 */
	public void setPdfExcel(String pdfExcel) {
		this.pdfExcel = pdfExcel;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @param startdate the startdate to set
	 */
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	/**
	 * @param std_purchase_qty the std_purchase_qty to set
	 */
	public void setStd_purchase_qty(String std_purchase_qty) {
		this.std_purchase_qty = std_purchase_qty;
	}

	/**
	 * @param stock_item_id the stock_item_id to set
	 */
	public void setStock_item_id(String stock_item_id) {
		this.stock_item_id = stock_item_id;
	}

	/**
	 * @return the item_category_id
	 *//*
	public Integer getItem_category_id() {
		return item_category_id;
	}

	*//**
	 * @param item_category_id the item_category_id to set
	 *//*
	public void setItem_category_id(Integer item_category_id) {
		this.item_category_id = item_category_id;
	}

	*/

}
