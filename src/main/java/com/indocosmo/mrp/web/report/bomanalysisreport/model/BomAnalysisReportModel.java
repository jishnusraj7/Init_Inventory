package com.indocosmo.mrp.web.report.bomanalysisreport.model;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;


public class BomAnalysisReportModel extends GeneralModelBase{
	
	private String pdfExcel;
    private String startdate;
	
	public String getPdfExcel() {
		return pdfExcel;
	}

	public void setPdfExcel(String pdfExcel) {
		this.pdfExcel = pdfExcel;
	}

	/**
	 * @return the startdate
	 */
	public String getStartdate() {
	
		return startdate;
	}
	
	/**
	 * @return the enddate
	 */
	public String getEnddate() {
	
		return enddate;
	}
	
	/**
	 * @param startdate the startdate to set
	 */
	public void setStartdate(String startdate) {
	
		this.startdate = startdate;
	}
	
	/**
	 * @param enddate the enddate to set
	 */
	public void setEnddate(String enddate) {
	
		this.enddate = enddate;
	}
	private String enddate;
 
	
}
