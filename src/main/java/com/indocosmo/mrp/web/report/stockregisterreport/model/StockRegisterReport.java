package com.indocosmo.mrp.web.report.stockregisterreport.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
public class StockRegisterReport extends GeneralModelBase {
	
	@Transient
	private String pdfExcel;
	
	@Transient
	private String reportName;
	
	@Transient
	private Integer option;
	
	@Transient
	private String startdate;
	
	@Column(name = "uomcode")
	private String uomcode;
	
	@Transient
	private String enddate;
	
	@Transient
	private String startmonth;
	
	@Transient
	private String endmonth;
	
	@Column(name = "stock_item_id")
	private String stock_item_id;
	
	@Column(name = "stock_item_code")
	private String stock_item_code;
	
	@Column(name = "stock_item_name")
	private String stockitemName;
	
	@Transient
	private Integer department_id;
	
	@Transient
	private String department_name;
	
	@Transient
	private Integer item_category_id;
	
	@Transient
	private String item_category_name;
	
	public String getItem_category_name() {
	
		return item_category_name;
	}
	
	public void setItem_category_name(String item_category_name) {
	
		this.item_category_name = item_category_name;
	}
	
	@Transient
	private String itemid;
	
	@Transient
	private Integer trans_type;
	
	@Column(name = "txn_date")
	private String txnDate;
	
	@Column(name = "opening")
	private String opening;
	
	@Column(name = "inqty")
	private String inQty;
	
	@Column(name = "outqty")
	private String outQty;
	
	@Column(name = "cost_price")
	private String cost_price;
	
	@Transient
	Boolean stockregisterReportType;
	
	@Transient
	private List<StockRegisterReport> stockRegister;
	
	public String getCost_price() {
	
		return cost_price;
	}
	
	/**
	 * @return the department_id
	 */
	public Integer getDepartment_id() {
	
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
	 * @return the endmonth
	 */
	public String getEndmonth() {
	
		return endmonth;
	}
	
	/**
	 * @return the inQty
	 */
	public String getInQty() {
	
		return inQty;
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
	public String getItemid() {
	
		return itemid;
	}
	
	/**
	 * @return the opening
	 */
	public String getOpening() {
	
		return opening;
	}
	
	/**
	 * @return the option
	 */
	public Integer getOption() {
	
		return option;
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
	 * @return the startmonth
	 */
	public String getStartmonth() {
	
		return startmonth;
	}
	
	/**
	 * @return the stock_item_code
	 */
	public String getStock_item_code() {
	
		return stock_item_code;
	}
	
	/**
	 * @return the stock_item_id
	 */
	public String getStock_item_id() {
	
		return stock_item_id;
	}
	
	/**
	 * @return the stockitemName
	 */
	public String getStockitemName() {
	
		return stockitemName;
	}
	
	/**
	 * @return the stockRegister
	 */
	public List<StockRegisterReport> getStockRegister() {
	
		return stockRegister;
	}
	
	/**
	 * @return the stockregisterReportType
	 */
	public Boolean getStockregisterReportType() {
	
		return stockregisterReportType;
	}
	
	/**
	 * @return the trans_type
	 */
	public Integer getTrans_type() {
	
		return trans_type;
	}
	
	/**
	 * @return the txnDate
	 */
	public String getTxnDate() {
	
		return txnDate;
	}
	
	/**
	 * @return the uomcode
	 */
	public String getUomcode() {
	
		return uomcode;
	}
	
	public void setCost_price(String cost_price) {
	
		this.cost_price = cost_price;
	}
	
	/**
	 * @param department_id
	 *            the department_id to set
	 */
	public void setDepartment_id(Integer department_id) {
	
		this.department_id = department_id;
	}
	
	public void setDepartment_name(String department_name) {
	
		this.department_name = department_name;
	}
	
	/**
	 * @param enddate
	 *            the enddate to set
	 */
	public void setEnddate(String enddate) {
	
		this.enddate = enddate;
	}
	
	/**
	 * @param endmonth
	 *            the endmonth to set
	 */
	public void setEndmonth(String endmonth) {
	
		this.endmonth = endmonth;
	}
	
	/**
	 * @param inQty
	 *            the inQty to set
	 */
	public void setInQty(String inQty) {
	
		this.inQty = inQty;
	}
	
	/**
	 * @param item_category_id
	 *            the item_category_id to set
	 */
	public void setItem_category_id(Integer item_category_id) {
	
		this.item_category_id = item_category_id;
	}
	
	/**
	 * @param itemid
	 *            the itemid to set
	 */
	public void setItemid(String itemid) {
	
		this.itemid = itemid;
	}
	
	/**
	 * @param opening
	 *            the opening to set
	 */
	public void setOpening(String opening) {
	
		this.opening = opening;
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
	 * @param startmonth
	 *            the startmonth to set
	 */
	public void setStartmonth(String startmonth) {
	
		this.startmonth = startmonth;
	}
	
	/**
	 * @param stock_item_code
	 *            the stock_item_code to set
	 */
	public void setStock_item_code(String stock_item_code) {
	
		this.stock_item_code = stock_item_code;
	}
	
	/**
	 * @param stock_item_id
	 *            the stock_item_id to set
	 */
	public void setStock_item_id(String stock_item_id) {
	
		this.stock_item_id = stock_item_id;
	}
	
	/**
	 * @param stockitemName
	 *            the stockitemName to set
	 */
	public void setStockitemName(String stockitemName) {
	
		this.stockitemName = stockitemName;
	}
	
	/**
	 * @param stockRegister
	 *            the stockRegister to set
	 * @return
	 */
	public List<StockRegisterReport> setStockRegister(List<StockRegisterReport> stockRegister) {
	
		return this.stockRegister = stockRegister;
	}
	
	/**
	 * @param stockregisterReportType
	 *            the stockregisterReportType to set
	 */
	public void setStockregisterReportType(Boolean stockregisterReportType) {
	
		this.stockregisterReportType = stockregisterReportType;
	}
	
	/**
	 * @param trans_type
	 *            the trans_type to set
	 */
	public void setTrans_type(Integer trans_type) {
	
		this.trans_type = trans_type;
	}
	
	/**
	 * @param txnDate
	 *            the txnDate to set
	 */
	public void setTxnDate(String txnDate) {
	
		this.txnDate = txnDate;
	}
	
	/**
	 * @param uomcode
	 *            the uomcode to set
	 */
	public void setUomcode(String uomcode) {
	
		this.uomcode = uomcode;
	}
	
}