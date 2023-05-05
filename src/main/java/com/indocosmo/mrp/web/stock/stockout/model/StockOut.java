package com.indocosmo.mrp.web.stock.stockout.model;

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
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.model.StockOutDetail;


@Entity
@Table(name ="mrp_stock_out_hdr")
public class StockOut extends GeneralModelBase{

	@Transient
	private String change_date;
	
	@Transient
	private String change_reason;
	
	@Column( name ="dest_company_code")
	private String destCompanyCode;
	
	@Column( name ="dest_company_id")
	private Integer destCompanyId;
	
	@Column( name ="dest_company_name")
	private String destCompanyName;
	
	@Transient String destDepartmentName;

	@Column( name ="source_department_id")
	private Integer fromDepartmentId;
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Transient
	private String pdfExcel;
	
	
	@Column( name ="remarks")
	private String remarks;

	@Transient
	private String reportName;

	@Column( name ="req_by")
	private Integer reqBy=0;

	@Column( name ="req_status")
	private Integer reqStatus;

	@Transient
	private String srcDepartmentName;
	
	@Transient
	private String stockDetailLists;
	
	@Transient
	private List<StockOutDetail> stockOutDetails;
	
	@Column( name ="stock_out_type")
	private Integer stockOutType=0;
	
	@Column( name ="stock_request_date")
	private String stockRequestDate;
	
	@Column( name ="stock_transfer_no")
	private String stockTransfeNo;
	
	@Column( name ="stock_transfer_date")
	private String stockTransferDate;
	
	@Column( name ="dest_department_id")
	private Integer toDepartmentId;
	
	@Column( name ="total_amount")
	private Double total_amount=0.00;
	
	/**
	 * @return the change_date
	 */
	public String getChange_date() {
		return change_date;
	}
	
	/**
	 * @return the change_reason
	 */
	public String getChange_reason() {
		return change_reason;
	}
	

	/**
	 * @return the destCompanyCode
	 */
	public String getDestCompanyCode() {
		return destCompanyCode;
	}

	/**
	 * @return the destCompanyId
	 */
	public Integer getDestCompanyId() {
		return destCompanyId;
	}

	/**
	 * @return the destCompanyName
	 */
	public String getDestCompanyName() {
		return destCompanyName;
	}
	
	/**
	 * @return the destDepartmentName
	 */
	public String getDestDepartmentName() {
		return destDepartmentName;
	}

	
	/**
	 * @return the fromDepartmentId
	 */
	public Integer getFromDepartmentId() {
		return fromDepartmentId;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the pdfExcel
	 */
	public String getPdfExcel() {
		return pdfExcel;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @return the reqBy
	 */
	public Integer getReqBy() {
		return reqBy;
	}

	/**
	 * @return the reqStatus
	 */
	public Integer getReqStatus() {
		return reqStatus;
	}

	/**
	 * @return the srcDepartmentName
	 */
	public String getSrcDepartmentName() {
		return srcDepartmentName;
	}

	/**
	 * @return the stockDetailLists
	 */
	public String getStockDetailLists() {
		return stockDetailLists;
	}

	/**
	 * @return the stockOutDetails
	 */
	public List<StockOutDetail> getStockOutDetails() {
		return stockOutDetails;
	}

	/**
	 * @return the stockOutType
	 */
	public Integer getStockOutType() {
		return stockOutType;
	}

	/**
	 * @return the stockRequestDate
	 */
	public String getStockRequestDate() {
		return stockRequestDate;
	}

	/**
	 * @return the stockTransfeNo
	 */
	public String getStockTransfeNo() {
		return stockTransfeNo;
	}

	/**
	 * @return the stockTransferDate
	 */
	public String getStockTransferDate() {
		return stockTransferDate;
	}

	/**
	 * @return the toDepartmentId
	 */
	public Integer getToDepartmentId() {
		return toDepartmentId;
	}

	/**
	 * @return the total_amount
	 */
	public Double getTotal_amount() {
		return total_amount;
	}

	/**
	 * @param change_date the change_date to set
	 */
	public void setChange_date(String change_date) {
		this.change_date = change_date;
	}

	/**
	 * @param change_reason the change_reason to set
	 */
	public void setChange_reason(String change_reason) {
		this.change_reason = change_reason;
	}

	/**
	 * @param destCompanyCode the destCompanyCode to set
	 */
	public void setDestCompanyCode(String destCompanyCode) {
		this.destCompanyCode = destCompanyCode;
	}

	/**
	 * @param destCompanyId the destCompanyId to set
	 */
	public void setDestCompanyId(Integer destCompanyId) {
		this.destCompanyId = destCompanyId;
	}

	/**
	 * @param destCompanyName the destCompanyName to set
	 */
	public void setDestCompanyName(String destCompanyName) {
		this.destCompanyName = destCompanyName;
	}

	/**
	 * @param destDepartmentName the destDepartmentName to set
	 */
	public void setDestDepartmentName(String destDepartmentName) {
		this.destDepartmentName = destDepartmentName;
	}

	/**
	 * @param fromDepartmentId the fromDepartmentId to set
	 */
	public void setFromDepartmentId(Integer fromDepartmentId) {
		this.fromDepartmentId = fromDepartmentId;
	}

	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="stock_out_hdr", key="stock_out_hdr")
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param pdfExcel the pdfExcel to set
	 */
	public void setPdfExcel(String pdfExcel) {
		this.pdfExcel = pdfExcel;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @param reqBy the reqBy to set
	 */
	public void setReqBy(Integer reqBy) {
		this.reqBy = reqBy;
	}

	

	/**
	 * @param reqStatus the reqStatus to set
	 */
	public void setReqStatus(Integer reqStatus) {
		this.reqStatus = reqStatus;
	}

	/**
	 * @param srcDepartmentName the srcDepartmentName to set
	 */
	public void setSrcDepartmentName(String srcDepartmentName) {
		this.srcDepartmentName = srcDepartmentName;
	}

	/**
	 * @param stockDetailLists the stockDetailLists to set
	 */
	public void setStockDetailLists(String stockDetailLists) {
		this.stockDetailLists = stockDetailLists;
	}

	/**
	 * @param stockOutDetails the stockOutDetails to set
	 */
	public void setStockOutDetails(List<StockOutDetail> stockOutDetails) {
		this.stockOutDetails = stockOutDetails;
	}

	/**
	 * @param stockOutType the stockOutType to set
	 */
	public void setStockOutType(Integer stockOutType) {
		this.stockOutType = stockOutType;
	}

	/**
	 * @param stockRequestDate the stockRequestDate to set
	 */
	public void setStockRequestDate(String stockRequestDate) {
		this.stockRequestDate = stockRequestDate;
	}

	/**
	 * @param stockTransfeNo the stockTransfeNo to set
	 */
	public void setStockTransfeNo(String stockTransfeNo) {
		this.stockTransfeNo = stockTransfeNo;
	}

	/**
	 * @param stockTransferDate the stockTransferDate to set
	 */
	public void setStockTransferDate(String stockTransferDate) {
		this.stockTransferDate = stockTransferDate;
	}

	/**
	 * @param toDepartmentId the toDepartmentId to set
	 */
	public void setToDepartmentId(Integer toDepartmentId) {
		this.toDepartmentId = toDepartmentId;
	}

	/**
	 * @param total_amount the total_amount to set
	 */
	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}
	
	
	
}
