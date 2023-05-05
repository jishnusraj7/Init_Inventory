package com.indocosmo.mrp.web.report.productionreport.model;

import javax.persistence.Entity;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
public class ProductionReportModel extends GeneralModelBase{

	private String pdfExcel;

	public String getPdfExcel() {
		return pdfExcel;
	}

	public void setPdfExcel(String pdfExcel) {
		this.pdfExcel = pdfExcel;
	}

	private String startdate;
	

	private String enddate;

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

	
}
