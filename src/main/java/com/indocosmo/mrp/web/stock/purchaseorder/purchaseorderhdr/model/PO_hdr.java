package com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.model.PO_dtl;

@Entity
@Table(name = "mrp_po_hdr")
public class PO_hdr extends  GeneralModelBase{


	

	
	
	
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "po_number")
	private String po_number;


	@Transient
	private String deleteremoterequestdtlid;
	
	
	public String getDeleteremoterequestdtlid() {
		return deleteremoterequestdtlid;
	}

	public void setDeleteremoterequestdtlid(String deleteremoterequestdtlid) {
		this.deleteremoterequestdtlid = deleteremoterequestdtlid;
	}

	@Transient
	private String commpanyName;
	

	/**
	 * @return the commpanyName
	 */
	public String getCommpanyName() {
		return commpanyName;
	}

	/**
	 * @param commpanyName the commpanyName to set
	 */
	public void setCommpanyName(String commpanyName) {
		this.commpanyName = commpanyName;
	}

	@Column(name = "po_date")
	private String po_date;	
	
	/**
	 * @return the po_date
	 */
	public String getPo_date() {
		return po_date;
	}

	/**
	 * @param po_date the po_date to set
	 */
	public void setPo_date(String po_date) {
		this.po_date = po_date;
	}

	/**
	 * @return the po_print_date
	 */
	public String getPo_print_date() {
		return po_print_date;
	}

	/**
	 * @param po_print_date the po_print_date to set
	 */
	public void setPo_print_date(String po_print_date) {
		this.po_print_date = po_print_date;
	}

	@Column(name = "po_status")
	private Integer po_status;
	
	@Column(name = "po_print_date")
	private String po_print_date;
	
	@Column(name = "po_close_date")
	private String po_close_date;
	
	/**
	 * @return the po_close_date
	 */
	public String getPo_close_date() {
		return po_close_date;
	}

	/**
	 * @param po_close_date the po_close_date to set
	 */
	public void setPo_close_date(String po_close_date) {
		this.po_close_date = po_close_date;
	}

	@Column(name = "supplier_id")
	private Integer supplier_id;

	@Column(name = "supplier_code")
	private String supplier_code;	
	
	@Column(name = "supplier_name")
	private String supplier_name;

	@Column(name = "po_type")
	private Integer po_type=0;
	
	@Column(name = "shipping_address")
	private String shipping_address;
	
	@Column(name = "billing_address")
	private String billing_address;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "terms")
	private String terms;

	@Column(name = "validity_days")
	private Integer validity_days;

	@Column(name = "total_amount")
	private  Double total_amount;
	
	@Transient
	private String poDetailLists;
	
	
	/**
	 * @return the poDetailLists
	 */
	public String getPoDetailLists() {
		return poDetailLists;
	}

	/**
	 * @param poDetailLists the poDetailLists to set
	 */
	public void setPoDetailLists(String poDetailLists) {
		this.poDetailLists = poDetailLists;
	}

	@Transient
	private String pdfExcel;
	
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

	@Transient
	private String reportName;

	
	
	

	/**
	 * @return the total_amount
	 */
	public Double getTotal_amount() {
		return total_amount;
	}

	/**
	 * @param total_amount the total_amount to set
	 */
	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}

	@Transient
	private List<PO_dtl> po_dtl;



	/**
	 * @return the po_dtl
	 */
	public List<PO_dtl> getPo_dtl() {
		return po_dtl;
	}

	/**
	 * @param po_dtl the po_dtl to set
	 */
	public void setPo_dtl(List<PO_dtl> po_dtl) {
		this.po_dtl = po_dtl;
	}

	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="po_hdr", key="po_hdr")
	public void setId(Integer id) {
		this.id = id;
	}


	public String getPo_number() {
		return po_number;
	}

	public void setPo_number(String po_number) {
		this.po_number = po_number;
	}

	



	

	public Integer getPo_status() {
		return po_status;
	}

	public void setPo_status(Integer po_status) {
		this.po_status = po_status;
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

	public Integer getPo_type() {
		return po_type;
	}

	public void setPo_type(Integer po_type) {
		this.po_type = po_type;
	}

	public String getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}

	public String getBilling_address() {
		return billing_address;
	}

	public void setBilling_address(String billing_address) {
		this.billing_address = billing_address;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public Integer getValidity_days() {
		return validity_days;
	}

	public void setValidity_days(Integer validity_days) {
		this.validity_days = validity_days;
	}

	


	
}
