package com.indocosmo.mrp.web.report.stockdisposalreport.model;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
public class DisposalReportModel extends GeneralModelBase{

	

	private String startdate;
	private String enddate;
	private String monthNo;
	private String year;
	private String stockId;
	private String deptId;
	private String transType;
	private String deptName;
	
	@Transient
	private String pdfExcel;

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

	/**
	 * @return the monthNo
	 */
	public String getMonthNo() {
		return monthNo;
	}

	/**
	 * @param monthNo the monthNo to set
	 */
	public void setMonthNo(String monthNo) {
		this.monthNo = monthNo;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the stockId
	 */
	public String getStockId() {
		return stockId;
	}

	/**
	 * @param stockId the stockId to set
	 */
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	/**
	 * @return the deptId
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return the transType
	 */
	public String getTransType() {
		return transType;
	}

	/**
	 * @param transType the transType to set
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPdfExcel() {
		return pdfExcel;
	}

	public void setPdfExcel(String pdfExcel) {
		this.pdfExcel = pdfExcel;
	}

}
