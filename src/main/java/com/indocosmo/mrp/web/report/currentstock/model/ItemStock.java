package com.indocosmo.mrp.web.report.currentstock.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
public class ItemStock extends GeneralModelBase{
	@Column(name = "stock_item_id")
	private String stock_item_id;
	
	@Column(name = "unit")
	private String unit;
	
	
	@Transient
	private Integer item_category_id;
	
	
	public Integer getItem_category_id() {
		return item_category_id;
	}

	public void setItem_category_id(Integer item_category_id) {
		this.item_category_id = item_category_id;
	}

	@Transient
	private String department_name;
	
	
	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	@Column(name = "department_id")	
	private String department_id;
	
	public String getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	@Column(name = "code")	
	private String code;
	@Column(name = "name")
	private String name;
	
	@Column(name = "current_stock")
	private String current_stock;
	private String categoryName;
	
	
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

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
	
	/**
	 * @return the std_purchase_qty
	 */
	public String getStd_purchase_qty() {
		return std_purchase_qty;
	}

	/**
	 * @param std_purchase_qty the std_purchase_qty to set
	 */
	public void setStd_purchase_qty(String std_purchase_qty) {
		this.std_purchase_qty = std_purchase_qty;
	}

	@Transient
	Boolean  itemstockReportType;
	
	/**
	 * @return the itemstockReportType
	 */
	public Boolean getItemstockReportType() {
		return itemstockReportType;
	}

	
	
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @param itemstockReportType the itemstockReportType to set
	 */
	public void setItemstockReportType(Boolean itemstockReportType) {
		this.itemstockReportType = itemstockReportType;
	}

	@Transient
	private List<ItemStock> itemstock;

	
	/**
	 * @return the itemstock
	 */
	public List<ItemStock> getItemstock() {
		return itemstock;
	}

	/**
	 * @param itemstock the itemstock to set
	 */
	public void setItemstock(List<ItemStock> itemstock) {
		this.itemstock = itemstock;
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

	@Transient
	List<Integer> itemid=new ArrayList<Integer>();
	
	/*@Transient
	private Integer item_category_id;*/

	/**
	 * @return the pdfExcel
	 */
	public String getPdfExcel() {
		return pdfExcel;
	}

	/**
	 * @param pdfExcel the pdfExcel to set
	 */
	public void setPdfExcel(String pdfExcel) {
		this.pdfExcel = pdfExcel;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @return the itemid
	 */
	public List<Integer> getItemid() {
		return itemid;
	}

	/**
	 * @param itemid the itemid to set
	 */
	public void setItemid(List<Integer> itemid) {
		this.itemid = itemid;
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
