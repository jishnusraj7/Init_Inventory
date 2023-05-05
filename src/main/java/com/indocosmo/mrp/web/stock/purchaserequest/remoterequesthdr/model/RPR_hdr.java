package com.indocosmo.mrp.web.stock.purchaserequest.remoterequesthdr.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.model.RPR_dtl;

@Entity
@Table(name = "mrp_hq_remote_request_hdr")
public class RPR_hdr extends  GeneralModelBase{


	
	
	


	
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "request_number")
	private String request_number;
	
	@Column(name = "submit_date")
	private String submit_date;
	@Transient
	private String stockDetailLists;
	
	/**
	 * @return the stockDetailLists
	 */
	public String getStockDetailLists() {
		return stockDetailLists;
	}


	/**
	 * @param stockDetailLists the stockDetailLists to set
	 */
	public void setStockDetailLists(String stockDetailLists) {
		this.stockDetailLists = stockDetailLists;
	}


	public String getSubmit_date() {
		return submit_date;
	}

	
	public void setSubmit_date(String submit_date) {
		this.submit_date = submit_date;
	}

	@Column(name = "request_company_id")
	private Integer request_company_id;
	
	/**
	 * @return the request_company_id
	 */
	public Integer getRequest_company_id() {
		return request_company_id;
	}

	/**
	 * @param request_company_id the request_company_id to set
	 */
	public void setRequest_company_id(Integer request_company_id) {
		this.request_company_id = request_company_id;
	}

	@Column(name = "request_company_code")
	private String request_company_code;
	
	@Column(name = "request_company_name")
	private String request_company_name;
	

	@Column(name = "request_by")
	private String request_by;
	

	
	@Column(name = "request_status")
	private Integer request_status;
	
	@Column(name = "remarks")
	private String remarks;
	
	
	
	
	
	
	
	
	
	/**
	 * @return the request_number
	 */
	public String getRequest_number() {
		return request_number;
	}

	/**
	 * @param request_number the request_number to set
	 */
	public void setRequest_number(String request_number) {
		this.request_number = request_number;
	}

	/**
	 * @return the request_company_code
	 */
	public String getRequest_company_code() {
		return request_company_code;
	}

	/**
	 * @param request_company_code the request_company_code to set
	 */
	public void setRequest_company_code(String request_company_code) {
		this.request_company_code = request_company_code;
	}

	/**
	 * @return the request_company_name
	 */
	public String getRequest_company_name() {
		return request_company_name;
	}

	/**
	 * @param request_company_name the request_company_name to set
	 */
	public void setRequest_company_name(String request_company_name) {
		this.request_company_name = request_company_name;
	}


	/**
	 * @return the request_by
	 */
	public String getRequest_by() {
		return request_by;
	}

	/**
	 * @param request_by the request_by to set
	 */
	public void setRequest_by(String request_by) {
		this.request_by = request_by;
	}


	/**
	 * @return the request_status
	 */
	public Integer getRequest_status() {
		return request_status;
	}

	/**
	 * @param request_status the request_status to set
	 */
	public void setRequest_status(Integer request_status) {
		this.request_status = request_status;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	
	
	

	

	@Transient
	private ArrayList<RPR_dtl> pr_dtl;



	/**
	 * @return the pr_dtl
	 */
	public ArrayList<RPR_dtl> getPr_dtl() {
		return pr_dtl;
	}

	/**
	 * @param pr_dtl the pr_dtl to set
	 */
	public void setPr_dtl(ArrayList<RPR_dtl> pr_dtl) {
		this.pr_dtl = pr_dtl;
	}

	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="remote_request_hdr", key="remote_request_hdr")
	public void setId(Integer id) {
		this.id = id;
	}

	




	


	
}
