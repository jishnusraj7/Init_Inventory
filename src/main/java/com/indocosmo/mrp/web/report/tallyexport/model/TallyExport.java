package com.indocosmo.mrp.web.report.tallyexport.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;
/*
 * @gana 06042020
 */
@Entity
public class TallyExport extends GeneralModelBase{

	@Transient
	private String startDate;
	
	@Transient
	private String endDate;
	
	@Transient
	private String option;
	
	@Column(name="net_total")
	private Double netTotal;
	
	@Column(name="tax_total")
	private Double taxTotal;
	
	@Column(name="grand_total")
	private Double grandTotal;
	
	@Column(name="received_date")
	private Date recievedDate;
	
	@Column(name="supplier_doc_no")
	private String supplierDocNo;
	
	@Column(name="supplier_name")
	private String supplierName;
	
	@Column(name="payment_mode")
	private Integer paymentMode;
	
	private List<TallyExport> tallyExport;


	public List<TallyExport> getTallyExport() {
		return tallyExport;
	}

	public void setTallyExport(List<TallyExport> tallyExport) {
		this.tallyExport = tallyExport;
	}

	public Double getNetTotal() {
		return netTotal;
	}

	public void setNetTotal(Double netTotal) {
		this.netTotal = netTotal;
	}

	public Double getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(Double taxTotal) {
		this.taxTotal = taxTotal;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
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

	public Date getRecievedDate() {
		return recievedDate;
	}

	public void setRecievedDate(Date recievedDate) {
		this.recievedDate = recievedDate;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getSupplierDocNo() {
		return supplierDocNo;
	}

	public void setSupplierDocNo(String supplierDocNo) {
		this.supplierDocNo = supplierDocNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(Integer paymentMode) {
		this.paymentMode = paymentMode;
	}


	
	
}
