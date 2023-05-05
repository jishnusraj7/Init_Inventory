package com.indocosmo.mrp.web.report.stockexcisereport.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

/*
 * 
 *  Done by anandu on 21-01-2020 
 *  
 */

@Entity
public class StockExciseReport extends GeneralModelBase {

	@Transient
	private String pdfExcel;

	@Transient
	private String reportName;

	@Transient
	private Integer option;

	@Transient
	private String startdate;

	@Transient
	private String enddate;
	
	@Transient
	private String superClass;

	@Column(name = "uomcode")
	private String uomcode;

	@Column(name = "stock_item_name")
	private String stockitemName;
	
	@Column(name = "btl_size")
	private String btlSize;

	@Column(name = "opening")
	private String opening;

	public String getBtlSize() {
		return btlSize;
	}

	public void setBtlSize(String btlSize) {
		this.btlSize = btlSize;
	}



	@Column(name = "inqty")
	private String inqty;

	@Column(name = "outqty")
	private String outqty;

	@Column(name = "inqty")
	private String inQty;

	@Column(name = "outqty")
	private String outQty;

	@Column(name = "cost_price")
	private String cost_price;
	
	@Column(name = "opening_unit")
	private String opening_unit;
	
	@Column(name = "inqty_unit")
	private String inqty_unit;
	
	@Column(name = "outqty_unit")
	private String outqty_unit;

	@Transient
	Boolean stockExciseReportType;

	@Transient
	private int category;
	
	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	
	
	public String getSuperClass() {
		return superClass;
	}

	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}



	@Transient
	private List<StockExciseReport> stockExcise;

	public String getCost_price() {
		return cost_price;
	}

	public void setCost_price(String cost_price) {
		this.cost_price = cost_price;
	}

	public String getOpening() {
		return opening;
	}

	public void setOpening(String opening) {
		this.opening = opening;
	}

	public String getInqty() {
		return inqty;
	}

	public void setInqty(String inqty) {
		this.inqty = inqty;
	}

	public String getOutqty() {
		return outqty;
	}

	public void setOutqty(String outqty) {
		this.outqty = outqty;
	}

	
	public String getOpening_unit() {
		return opening_unit;
	}

	public void setOpening_unit(String opening_unit) {
		this.opening_unit = opening_unit;
	}

	public String getInqty_unit() {
		return inqty_unit;
	}

	public void setInqty_unit(String inqty_unit) {
		this.inqty_unit = inqty_unit;
	}

	public String getOutqty_unit() {
		return outqty_unit;
	}

	public void setOutqty_unit(String outqty_unit) {
		this.outqty_unit = outqty_unit;
	}

	/**
	 * @return the inQty
	 */
	public String getInQty() {

		return inQty;
	}

	/**
	 * @return the option
	 */
	public Integer getOption() {

		return option;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	/**
	 * @return the outQty
	 */
	public String getOutQty() {

		return outQty;
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
	 * @return the stockitemName
	 */
	public String getStockitemName() {

		return stockitemName;
	}

	
	public List<StockExciseReport> getStockExcise() {

		return stockExcise;
	}

	/**
	 * @return the stockExciseReportType
	 */
	public Boolean getStockExciseReportType() {

		return stockExciseReportType;
	}

	/**
	 * @return the uomcode
	 */
	public String getUomcode() {

		return uomcode;
	}

	/**
	 * @param inQty
	 *            the inQty to set
	 */
	public void setInQty(String inQty) {

		this.inQty = inQty;
	}

	/**
	 * @param option
	 *            the option to set
	 */
	public void setOption(Integer option) {

		this.option = option;
	}

	/**
	 * @param outQty
	 *            the outQty to set
	 */
	public void setOutQty(String outQty) {

		this.outQty = outQty;
	}

	/**
	 * @param pdfExcel
	 *            the pdfExcel to set
	 */
	public void setPdfExcel(String pdfExcel) {

		this.pdfExcel = pdfExcel;
	}

	/**
	 * @param reportName
	 *            the reportName to set
	 */
	public void setReportName(String reportName) {

		this.reportName = reportName;
	}

	/**
	 * @param startdate
	 *            the startdate to set
	 */
	public void setStartdate(String startdate) {

		this.startdate = startdate;
	}

	/**
	 * @param stockitemName
	 *            the stockitemName to set
	 */
	public void setStockitemName(String stockitemName) {

		this.stockitemName = stockitemName;
	}

	
	public List<StockExciseReport> setStockExcise(List<StockExciseReport> stockExcise) {

		return this.stockExcise = stockExcise;
	}

	/**
	 * @param stockExciseReportType
	 *            the stockExciseReportType to set
	 */
	public void setStockExciseReportType(Boolean stockExciseReportType) {

		this.stockExciseReportType = stockExciseReportType;
	}

	/**
	 * @param uomcode
	 *            the uomcode to set
	 */
	public void setUomcode(String uomcode) {

		this.uomcode = uomcode;
	}

}