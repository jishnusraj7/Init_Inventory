package com.indocosmo.mrp.web.report.purchasereturnreport.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;
import com.indocosmo.mrp.web.report.stockoutreport.model.StockoutReportModel;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.model.PurchaseReturnDetail;

@Entity
public class PurchaseReturnReport extends GeneralModelBase{

	@Column(name = "return_no")
	private String return_no;
	
	@Column(name = "return_date")
	private String return_date;
	
	private Integer return_status;
	
	private Integer department_id;
	
	private Integer supplier_id;
	
	private String supplier_code;
	
	@Column(name = "supplier_name")
	private String supplier_name;
	
	private String returnDetailLists;
	
	private String reportName;
	
	private String startDate;
	
	private String endDate;
	
	@Transient
	private Integer option;
	
	@Column(name = "uomcode")
	private String uomcode;
	
	@Column(name = "stock_item_id")
	private String stock_item_id;
	
	@Column(name = "stock_item_name")
	private String stock_item_name;
	
	@Column(name = "return_qty")
	private Double return_qty;
	
	@Transient
	private List<PurchaseReturnReport> purchaseReturnReportData;
	
	public Double getReturn_qty() {
		return return_qty;
	}

	public void setReturn_qty(Double return_qty) {
		this.return_qty = return_qty;
	}

	
	
	public String getReturn_no() {
		return return_no;
	}

	public void setReturn_no(String return_no) {
		this.return_no = return_no;
	}

	public String getReturn_date() {
		return return_date;
	}

	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}

	public Integer getReturn_status() {
		return return_status;
	}

	public void setReturn_status(Integer return_status) {
		this.return_status = return_status;
	}

	public Integer getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}

	public Integer getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getSupplier_code() {
		return supplier_code;
	}

	public void setSupplier_code(String supplier_code) {
		this.supplier_code = supplier_code;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public String getReturnDetailLists() {
		return returnDetailLists;
	}

	public void setReturnDetailLists(String returnDetailLists) {
		this.returnDetailLists = returnDetailLists;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<PurchaseReturnReport> getPurchaseReturnReportData() {
		return purchaseReturnReportData;
	}

	public void setPurchaseReturnReportData(List<PurchaseReturnReport> purchaseReturnReportData) {
		this.purchaseReturnReportData = purchaseReturnReportData;
	}

	public String getUomcode() {
		return uomcode;
	}

	public void setUomcode(String uomcode) {
		this.uomcode = uomcode;
	}

	public String getStock_item_name() {
		return stock_item_name;
	}

	public void setStock_item_name(String stock_item_name) {
		this.stock_item_name = stock_item_name;
	}

	public Integer getOption() {
		return option;
	}

	public void setOption(Integer option) {
		this.option = option;
	}

	public String getStock_item_id() {
		return stock_item_id;
	}

	public void setStock_item_id(String stock_item_id) {
		this.stock_item_id = stock_item_id;
	}

	
	
}
