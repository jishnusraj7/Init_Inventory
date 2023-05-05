package com.indocosmo.mrp.web.report.stockadjustmentsummary.model;

import javax.persistence.Transient;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

public class StockAdjustmentReport extends GeneralModelBase{

	@Transient
	private String startdate;

	@Transient
	private String enddate;
	
	@Transient
	private String pdfexcel;

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

	public String getPdfexcel() {
		return pdfexcel;
	}

	public void setPdfexcel(String pdfexcel) {
		this.pdfexcel = pdfexcel;
	}	
}
