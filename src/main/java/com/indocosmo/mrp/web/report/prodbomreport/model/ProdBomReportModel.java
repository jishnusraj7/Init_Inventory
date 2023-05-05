package  com.indocosmo.mrp.web.report.prodbomreport.model;

import javax.persistence.Entity;

import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;


@Entity
public class ProdBomReportModel extends GeneralModelBase{

	private String pdfExcel;
	private String Month;
	private String monthNo;
	private String year;
	
	
	
	
	public String getPdfExcel() {
		return pdfExcel;
	}



	public void setPdfExcel(String pdfExcel) {
		this.pdfExcel = pdfExcel;
	}



	public String getMonthNo() {
	
		return monthNo;
	}


	
	public void setMonthNo(String monthNo) {
	
		this.monthNo = monthNo;
	}


	
	public String getYear() {
	
		return year;
	}


	
	public void setYear(String year) {
	
		this.year = year;
	}


	public String getMonth() {
	
		return Month;
	}

	
	public void setMonth(String month) {
	
		Month = month;
	}

	private String startdate;
	
	private String enddate;
	
	private Integer department_id;

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
	 * @return the department_id
	 */
	public Integer getDepartment_id() {
		return department_id;
	}

	/**
	 * @param department_id the department_id to set
	 */
	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}

	
}
