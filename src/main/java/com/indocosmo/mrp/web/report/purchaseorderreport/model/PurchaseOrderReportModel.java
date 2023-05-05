package com.indocosmo.mrp.web.report.purchaseorderreport.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
public class PurchaseOrderReportModel extends GeneralModelBase{

	
	@Column(name = "po_date")
	private String po_date;
	
	@Column(name = "supplier_name")
	private String supplier_name;
	
	
	@Column(name = "po_number")
	private String po_number;
	

	@Column(name = "po_status")
	private Integer po_status;
	
	@Column(name = "total_amount")
	private Double total_amount;
	
	@Column(name = "stock_item_name")
	private String stock_item_name;

	@Column(name = "qty")
	private Double received_qty;
	
	@Column(name = "unit_price")
	private Double unit_price;
	
	
	@Column(name = "itm_cat_name")
	private String itm_cat_name;
	
	@Transient
	private String startdate;

	@Transient
	private String enddate;

	@Transient
	private Integer supplier_id;

	@Transient
	private String stock_item_id;

	@Transient
	private List<PurchaseOrderReportModel> purchasreportData;
	
	@Transient
	private String pdfExcel;

	

	public String getPdfExcel() {
		return pdfExcel;
	}

	public void setPdfExcel(String pdfExcel) {
		this.pdfExcel = pdfExcel;
	}

	/**
	 * @return the enddate
	 */
	public String getEnddate() {
		return enddate;
	}

	/**
	 * @return the itm_cat_name
	 */
	public String getItm_cat_name() {
		return itm_cat_name;
	}

	/**
	 * @return the po_date
	 */
	public String getPo_date() {
		return po_date;
	}
	
	/**
	 * @return the po_number
	 */
	public String getPo_number() {
		return po_number;
	}
	
	/**
	 * @return the po_status
	 */
	public Integer getPo_status() {
		return po_status;
	}
	

	/**
	 * @return the purchasreportData
	 */
	public List<PurchaseOrderReportModel> getPurchasreportData() {
		return purchasreportData;
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
	 * @return the total_amount
	 */
	public Double getTotal_amount() {
		return total_amount;
	}

	/**
	 * @return the unit_price
	 */
	public Double getUnit_price() {
		return unit_price;
	}

	/**
	 * @param enddate the enddate to set
	 */
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	/**
	 * @param itm_cat_name the itm_cat_name to set
	 */
	public void setItm_cat_name(String itm_cat_name) {
		this.itm_cat_name = itm_cat_name;
	}

	/**
	 * @param po_date the po_date to set
	 */
	public void setPo_date(String po_date) {
		this.po_date = po_date;
	}

	/**
	 * @param po_number the po_number to set
	 */
	public void setPo_number(String po_number) {
		this.po_number = po_number;
	}

	/**
	 * @param po_status the po_status to set
	 */
	public void setPo_status(Integer po_status) {
		this.po_status = po_status;
	}

	/**
	 * @param purchasreportData the purchasreportData to set
	 */
	public void setPurchasreportData(
			List<PurchaseOrderReportModel> purchasreportData) {
		this.purchasreportData = purchasreportData;
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
	 * @param total_amount the total_amount to set
	 */
	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}

	/**
	 * @param unit_price the unit_price to set
	 */
	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
	}

	
	
	
	
	
}
