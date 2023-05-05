package com.indocosmo.mrp.web.report.common.model;


public class Report {

	String ColoumnList;
	String OrderList;
	String ReportName;
	String CurrUserName;
	String CompanyName;
	String companyAddress;
	String dateFormat;
	Integer decimalPlace;
	String currency;
	Integer docType;
	String is_customer;
	Integer ReportType;
	Integer combine_mode;

	
	/**
	 * @return the combine_mode
	 */
	public Integer getCombine_mode() {
	
		return combine_mode;
	}

	
	/**
	 * @param combine_mode the combine_mode to set
	 */
	public void setCombine_mode(Integer combine_mode) {
	
		this.combine_mode = combine_mode;
	}

	public String getColoumnList() {
		return ColoumnList;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return CompanyName;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @return the currUserName
	 */
	public String getCurrUserName() {
		return CurrUserName;
	}

	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * @return the decimalPlace
	 */
	public Integer getDecimalPlace() {
		return decimalPlace;
	}

	public Integer getDocType() {
		return docType;
	}

	public String getIs_customer() {
		return is_customer;
	}

	public String getOrderList() {
		return OrderList;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return ReportName;
	}

	/**
	 * @return the reportType
	 */
	public Integer getReportType() {
		return ReportType;
	}

	public void setColoumnList(String coloumnList) {
		ColoumnList = coloumnList;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @param currUserName
	 *            the currUserName to set
	 */
	public void setCurrUserName(String currUserName) {
		CurrUserName = currUserName;
	}

	/**
	 * @param dateFormat
	 *            the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * @param decimalPlace
	 *            the decimalPlace to set
	 */
	public void setDecimalPlace(Integer decimalPlace) {
		this.decimalPlace = decimalPlace;
	}

	public void setDocType(Integer docType) {
		this.docType = docType;
	}

	public void setIs_customer(String is_customer) {
		this.is_customer = is_customer;
	}

	public void setOrderList(String orderList) {
		OrderList = orderList;
	}

	/**
	 * @param reportName
	 *            the reportName to set
	 */
	public void setReportName(String reportName) {
		ReportName = reportName;
	}

	/**
	 * @param reportType
	 *            the reportType to set
	 */
	public void setReportType(Integer reportType) {
		ReportType = reportType;
	}


	public String getCompanyAddress() {
		return companyAddress;
	}


	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

}
