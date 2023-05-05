package com.indocosmo.mrp.web.report.stockinreport.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
public class StockinReportModel extends GeneralModelBase{


	@Column(name = "received_date")
	private String received_date;

	@Column(name = "supplier_name")
	private String supplier_name;

	@Column(name = "grn_no")
	private String grn_no;

	@Column(name = "itm_cat_id")
	private Integer itm_cat_id;

	@Column(name = "itm_cat_name")
	private String itm_cat_name;

	@Column(name="uomname")
	private String uomname;

	@Column(name = "status")
	private Integer status;

	@Column(name = "stock_in_type")
	private Integer stock_in_type;

	@Column(name = "stock_item_name")
	private String stock_item_name;

	@Column(name = "received_qty")
	private Double received_qty;

	@Column(name = "unit_price")
	private Double unit_price;

	@Column(name = "amount")
	private Double amount;

	@Transient
	private Integer option;

	@Transient
	private String startdate;

	@Transient
	private String enddate;

	@Transient
	private Integer supplier_id;

	@Transient
	private String stock_item_id;

	@Transient
	private String pdfExcel;

	@Transient
	private List<StockinReportModel> purchasreportData;

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @return the enddate
	 */
	public String getEnddate() {
		return enddate;
	}

	/**
	 * @return the grn_no
	 */
	public String getGrn_no() {
		return grn_no;
	}
	/**
	 * @return the itm_cat_id
	 */
	public Integer getItm_cat_id() {
		return itm_cat_id;
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
	 * @return the purchasreportData
	 */
	public List<StockinReportModel> getPurchasreportData() {
		return purchasreportData;
	}

	/**
	 * @return the received_date
	 */
	public String getReceived_date() {
		return received_date;
	}

	/**
	 * @return the received_qty
	 */
	public Double getReceived_qty() {
		return received_qty;
	}

	/**
	 * @return the startdate
	 */
	public String getStartdate() {
		return startdate;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @return the stock_in_type
	 */
	public Integer getStock_in_type() {
		return stock_in_type;
	}

	/**
	 * @return the stock_item_id
	 */
	public String getStock_item_id() {
		return stock_item_id;
	}


	/**
	 * @return the stock_item_name
	 */
	public String getStock_item_name() {
		return stock_item_name;
	}

	/**
	 * @return the supplier_id
	 */
	public Integer getSupplier_id() {
		return supplier_id;
	}

	/**
	 * @return the supplier_name
	 */
	public String getSupplier_name() {
		return supplier_name;
	}

	/**
	 * @return the unit_price
	 */
	public Double getUnit_price() {
		return unit_price;
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
	 * @param enddate the enddate to set
	 */
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	/**
	 * @param grn_no the grn_no to set
	 */
	public void setGrn_no(String grn_no) {
		this.grn_no = grn_no;
	}

	/**
	 * @param itm_cat_id the itm_cat_id to set
	 */
	public void setItm_cat_id(Integer itm_cat_id) {
		this.itm_cat_id = itm_cat_id;
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

	/**
	 * @param purchasreportData the purchasreportData to set
	 */
	public void setPurchasreportData(
			List<StockinReportModel> purchasreportData) {
		this.purchasreportData = purchasreportData;
	}

	/**
	 * @param received_date the received_date to set
	 */
	public void setReceived_date(String received_date) {
		this.received_date = received_date;
	}

	/**
	 * @param received_qty the received_qty to set
	 */
	public void setReceived_qty(Double received_qty) {
		this.received_qty = received_qty;
	}

	/**
	 * @param startdate the startdate to set
	 */
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @param stock_in_type the stock_in_type to set
	 */
	public void setStock_in_type(Integer stock_in_type) {
		this.stock_in_type = stock_in_type;
	}

	/**
	 * @param stock_item_id the stock_item_id to set
	 */
	public void setStock_item_id(String stock_item_id) {
		this.stock_item_id = stock_item_id;
	}

	/**
	 * @param stock_item_name the stock_item_name to set
	 */
	public void setStock_item_name(String stock_item_name) {
		this.stock_item_name = stock_item_name;
	}

	/**
	 * @param supplier_id the supplier_id to set
	 */
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}

	/**
	 * @param supplier_name the supplier_name to set
	 */
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	/**
	 * @param unit_price the unit_price to set
	 */
	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
	}

	/**
	 * @param uomname the uomname to set
	 */
	public void setUomname(String uomname) {
		this.uomname = uomname;
	}

	public String getPdfExcel() {
		return pdfExcel;
	}

	public void setPdfExcel(String pdfExcel) {
		this.pdfExcel = pdfExcel;
	}
}
