package com.indocosmo.mrp.web.report.stocktransferreport.model;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
public class StockTransferReportModel  extends GeneralModelBase{
	
	@Transient
	private Integer option;

	@Transient
	private String startdate;

	@Transient
	private String enddate;
	
	@Transient
	private String pdfExcel;
	
	@Transient
	private Integer companyid;
	
	@Transient
	private Integer departmentid;
	
	

	public Integer getOption() {
		return option;
	}

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public Integer getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(Integer departmentid) {
		this.departmentid = departmentid;
	}

	public void setOption(Integer option) {
		this.option = option;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getPdfExcel() {
		return pdfExcel;
	}

	public void setPdfExcel(String pdfExcel) {
		this.pdfExcel = pdfExcel;
	}
	
}
