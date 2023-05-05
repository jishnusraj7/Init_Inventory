package com.indocosmo.mrp.web.report.stockoutreport.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
public class StockoutReportModel extends GeneralModelBase{

	@Column(name="stock_transfer_date")
	private String stock_transfer_date;
	
	@Column(name="source_department_name")
	private String source_department_name;
	
	@Column(name="dest_department_name")
	private String dest_department_name;
	
	@Column(name="stock_item_name")
	private String stock_item_name;
	
	@Transient
	private String startdate;

	@Transient
	private String enddate;
	
	@Transient
	private Integer sourceDepartmentId;
	
	@Transient
	private Integer destDepartmentId;
	
	@Transient
	private String stockItemId;
	
	
	@Column(name = "itm_cat_name")
	private String itm_cat_name;
	
	@Transient
	private Integer option;
	
	@Column(name="issued_qty")
	private Double issued_qty;

	@Column(name="cost_price")
	private Double cost_price;

	@Column(name="amount")
	private Double amount;

	@Column(name="total_amount")
	private Double total_amount;

	@Column(name="uomname")
	private String uomname;
	
	@Column(name="stock_transfer_no")
	private String stock_transfer_no;
	
	@Column(name="war_rate")
	private Double war_rate;
	
	
	@Transient
	private List<StockoutReportModel> stockoutreportData;
	
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	
	/**
	 * @return the cost_price
	 */
	public Double getCost_price() {
		return cost_price;
	}
	
	/**
	 * @return the dest_department_name
	 */
	public String getDest_department_name() {
		return dest_department_name;
	}

	/**
	 * @return the destDepartmentId
	 */
	public Integer getDestDepartmentId() {
		return destDepartmentId;
	}

	/**
	 * @return the enddate
	 */
	public String getEnddate() {
		return enddate;
	}

	/**
	 * @return the issued_qty
	 */
	public Double getIssued_qty() {
		return issued_qty;
	}

	/**
	 * @return the itm_cat_name
	 */
	public String getItm_cat_name() {
		return itm_cat_name;
	}
	
	

	/**
	 * @return the option
	 */
	public Integer getOption() {
		return option;
	}

	/**
	 * @return the source_department_name
	 */
	public String getSource_department_name() {
		return source_department_name;
	}

	/**
	 * @return the sourceDepartmentId
	 */
	public Integer getSourceDepartmentId() {
		return sourceDepartmentId;
	}
	
	
	/**
	 * @return the startdate
	 */
	public String getStartdate() {
		return startdate;
	}

	/**
	 * @return the stock_item_name
	 */
	public String getStock_item_name() {
		return stock_item_name;
	}

	/**
	 * @return the stock_transfer_date
	 */
	public String getStock_transfer_date() {
		return stock_transfer_date;
	}

	/**
	 * @return the stock_transfer_no
	 */
	public String getStock_transfer_no() {
		return stock_transfer_no;
	}

	/**
	 * @return the stockItemId
	 */
	public String getStockItemId() {
		return stockItemId;
	}

	/**
	 * @return the stockoutreportData
	 */
	public List<StockoutReportModel> getStockoutreportData() {
		return stockoutreportData;
	}

	/**
	 * @return the total_amount
	 */
	public Double getTotal_amount() {
		return total_amount;
	}

	/**
	 * @return the uomname
	 */
	public String getUomname() {
		return uomname;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @param cost_price the cost_price to set
	 */
	public void setCost_price(Double cost_price) {
		this.cost_price = cost_price;
	}

	/**
	 * @param dest_department_name the dest_department_name to set
	 */
	public void setDest_department_name(String dest_department_name) {
		this.dest_department_name = dest_department_name;
	}

	/**
	 * @param destDepartmentId the destDepartmentId to set
	 */
	public void setDestDepartmentId(Integer destDepartmentId) {
		this.destDepartmentId = destDepartmentId;
	}



	/**
	 * @param enddate the enddate to set
	 */
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	/**
	 * @param issued_qty the issued_qty to set
	 */
	public void setIssued_qty(Double issued_qty) {
		this.issued_qty = issued_qty;
	}

	/**
	 * @param itm_cat_name the itm_cat_name to set
	 */
	public void setItm_cat_name(String itm_cat_name) {
		this.itm_cat_name = itm_cat_name;
	}

	/**
	 * @param option the option to set
	 */
	public void setOption(Integer option) {
		this.option = option;
	}

	public Double getWar_rate() {
		return war_rate;
	}

	public void setWar_rate(Double war_rate) {
		this.war_rate = war_rate;
	}

	/**
	 * @param source_department_name the source_department_name to set
	 */
	public void setSource_department_name(String source_department_name) {
		this.source_department_name = source_department_name;
	}

	/**
	 * @param sourceDepartmentId the sourceDepartmentId to set
	 */
	public void setSourceDepartmentId(Integer sourceDepartmentId) {
		this.sourceDepartmentId = sourceDepartmentId;
	}

	/**
	 * @param startdate the startdate to set
	 */
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	/**
	 * @param stock_item_name the stock_item_name to set
	 */
	public void setStock_item_name(String stock_item_name) {
		this.stock_item_name = stock_item_name;
	}

	/**
	 * @param stock_transfer_date the stock_transfer_date to set
	 */
	public void setStock_transfer_date(String stock_transfer_date) {
		this.stock_transfer_date = stock_transfer_date;
	}

	/**
	 * @param stock_transfer_no the stock_transfer_no to set
	 */
	public void setStock_transfer_no(String stock_transfer_no) {
		this.stock_transfer_no = stock_transfer_no;
	}

	/**
	 * @param stockItemId the stockItemId to set
	 */
	public void setStockItemId(String stockItemId) {
		this.stockItemId = stockItemId;
	}

	/**
	 * @param stockoutreportData the stockoutreportData to set
	 */
	public void setStockoutreportData(List<StockoutReportModel> stockoutreportData) {
		this.stockoutreportData = stockoutreportData;
	}

	/**
	 * @param total_amount the total_amount to set
	 */
	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}

	/**
	 * @param uomname the uomname to set
	 */
	public void setUomname(String uomname) {
		this.uomname = uomname;
	}

	

	
	
	
	
}
