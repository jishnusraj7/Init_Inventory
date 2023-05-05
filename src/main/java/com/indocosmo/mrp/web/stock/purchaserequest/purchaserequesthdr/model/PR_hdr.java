package com.indocosmo.mrp.web.stock.purchaserequest.purchaserequesthdr.model;

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
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.model.PR_dtl;

@Entity
@Table(name = "mrp_pr_hdr")
public class PR_hdr extends  GeneralModelBase{


	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "pr_number")
	private String pr_number;

	@Column(name = "remarks")
	private String remarks;
	
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

	@Column(name = "company_id")
	private Integer company_id;
	
	@Column(name = "status")
	private Integer status;
	
	
	@Column(name = "request_by")
	private String request_by;
	
	@Transient
	private String prDetailLists;
	@Transient
	private String request_company_code;
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

	@Transient
	private String request_company_name;
	
	/**
	 * @return the prDetailLists
	 */
	public String getPrDetailLists() {
		return prDetailLists;
	}

	/**
	 * @param prDetailLists the prDetailLists to set
	 */
	public void setPrDetailLists(String prDetailLists) {
		this.prDetailLists = prDetailLists;
	}

	/**
	 * @return the pr_number
	 */
	public String getPr_number() {
		return pr_number;
	}

	/**
	 * @param pr_number the pr_number to set
	 */
	public void setPr_number(String pr_number) {
		this.pr_number = pr_number;
	}

	/**
	 * @return the company_id
	 */
	public Integer getCompany_id() {
		return company_id;
	}

	/**
	 * @param company_id the company_id to set
	 */
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	 * @return the create_date
	 */
	public String getCreate_date() {
		return create_date;
	}

	/**
	 * @param create_date the create_date to set
	 */
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	/**
	 * @return the submit_date
	 */
	public String getSubmit_date() {
		return submit_date;
	}

	/**
	 * @param submit_date the submit_date to set
	 */
	public void setSubmit_date(String submit_date) {
		this.submit_date = submit_date;
	}

	@Column(name = "create_date")
	private String create_date;
	
	
	@Column(name = "submit_date")
	private String submit_date;
	
	
	
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
	private ArrayList<PR_dtl> pr_dtl;




	/**
	 * @return the pr_dtl
	 */
	public ArrayList<PR_dtl> getPr_dtl() {
		return pr_dtl;
	}

	/**
	 * @param pr_dtl the pr_dtl to set
	 */
	public void setPr_dtl(ArrayList<PR_dtl> pr_dtl) {
		this.pr_dtl = pr_dtl;
	}

	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="pr_hdr", key="pr_hdr")
	public void setId(Integer id) {
		this.id = id;
	}

	




	


	
}
