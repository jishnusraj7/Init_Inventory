package com.indocosmo.mrp.web.stock.stockin.model;

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
import com.indocosmo.mrp.web.stock.stockin.stockindetail.model.StockInDetail;

@Entity
@Table(name ="mrp_stock_in_hdr")
public class StockIn extends GeneralModelBase{

	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column( name ="department_id")
	private Integer departmentId;
	
	@Column( name ="stock_in_type")
	private Integer stockInType;
	
	@Column( name ="supplier_id")
	private Integer supplierId;
	
	@Column( name ="supplier_code")
	private String supplierCode;
	
	@Column( name ="supplier_name")
	private String supplierName;

	@Column(name="payment_mode")
	private String stockInPaymentType;

	@Column( name ="supplier_doc_no")
	private String supplierRefNo;
	
	@Column( name ="received_date")
	private String receivedDate;
	
	@Column( name ="grn_no")
	private String grnNo;
	
	
	@Column( name ="finalized_by")
	private Integer finalizedBy;
	
	@Column( name ="finalized_date")
	private String finalizedDate;
	
	@Column( name ="remarks")
	private String remarks;
	
	@Column( name ="status")
	private Integer status=0;
	
	@Column( name ="source_company_id")
	private Integer companyId;
	
	@Column( name ="source_company_code")
	private String companyCode;
	
	@Column( name ="source_company_name")
	private String companyName;
	
	@Column(name ="received_by")
	private Integer receivedBy;
	
	@Column(name ="net_total")
	private Double netTotal;
	
	@Column(name ="tax_total")
	private Double taxTotal;

	@Column(name ="grand_total")
	private Integer grandTotal;
	
	@Transient
	private ArrayList<StockInDetail> stockInDetails;
	
	@Transient
	private String supplierAdress1;
	
	@Transient
	private String supplierAdress2;
	
	
	@Transient
	private String pdfExcel;
	
	@Transient
	private String reportName;

	@Transient
	private String stockDetailLists;

	@Transient
	private String stockUomData;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="stock_in_hdr", key="stock_in_hdr")
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the departmentId
	 */
	public Integer getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the stockInType
	 */
	public Integer getStockInType() {
		return stockInType;
	}

	/**
	 * @param stockInType the stockInType to set
	 */
	public void setStockInType(Integer stockInType) {
		this.stockInType = stockInType;
	}

	/**
	 * @return the supplierId
	 */
	public Integer getSupplierId() {
		return supplierId;
	}

	/**
	 * @param supplierId the supplierId to set
	 */
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * @return the supplierCode
	 */
	public String getSupplierCode() {
		return supplierCode;
	}

	/**
	 * @param supplierCode the supplierCode to set
	 */
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	/**
	 * @return the supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * @param supplierName the supplierName to set
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * @return the supplierRefNo
	 */
	public String getSupplierRefNo() {
		return supplierRefNo;
	}

	/**
	 * @param supplierRefNo the supplierRefNo to set
	 */
	public void setSupplierRefNo(String supplierRefNo) {
		this.supplierRefNo = supplierRefNo;
	}

	

	/**
	 * @return the receivedDate
	 */
	public String getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}


	/**
	 * @return the grnNo
	 */
	public String getGrnNo() {
		return grnNo;
	}

	/**
	 * @param grnNo the grnNo to set
	 */
	public void setGrnNo(String grnNo) {
		this.grnNo = grnNo;
	}

	/**
	 * @return the stockInDetails
	 */
	public ArrayList<StockInDetail> getStockInDetails() {
		return stockInDetails;
	}

	/**
	 * @param stockInDetails the stockInDetails to set
	 */
	public void setStockInDetails(ArrayList<StockInDetail> stockInDetails) {
		this.stockInDetails = stockInDetails;
	}

	/**
	 * @return the finalizedBy
	 */
	public Integer getFinalizedBy() {
		return finalizedBy;
	}

	/**
	 * @param finalizedBy the finalizedBy to set
	 */
	public void setFinalizedBy(Integer finalizedBy) {
		this.finalizedBy = finalizedBy;
	}

	/**
	 * @return the finalizedDate
	 */
	public String getFinalizedDate() {
		return finalizedDate;
	}

	/**
	 * @param finalizedDate the finalizedDate to set
	 */
	public void setFinalizedDate(String finalizedDate) {
		this.finalizedDate = finalizedDate;
	}

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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the receivedBy
	 */
	public Integer getReceivedBy() {
		return receivedBy;
	}

	/**
	 * @param receivedBy the receivedBy to set
	 */
	public void setReceivedBy(Integer receivedBy) {
		this.receivedBy = receivedBy;
	}

	/**
	 * @return the supplierAdress1
	 */
	public String getSupplierAdress1() {
		return supplierAdress1;
	}

	/**
	 * @param supplierAdress1 the supplierAdress1 to set
	 */
	public void setSupplierAdress1(String supplierAdress1) {
		this.supplierAdress1 = supplierAdress1;
	}

	/**
	 * @return the supplierAdress2
	 */
	public String getSupplierAdress2() {
		return supplierAdress2;
	}

	/**
	 * @param supplierAdress2 the supplierAdress2 to set
	 */
	public void setSupplierAdress2(String supplierAdress2) {
		this.supplierAdress2 = supplierAdress2;
	}

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

	public String getStockInPaymentType() {
		return stockInPaymentType;
	}

	public void setStockInPaymentType(String stockInPaymentType) {
		this.stockInPaymentType = stockInPaymentType;
	}

	public String getStockUomData() {
		return stockUomData;
	}

	public void setStockUomData(String stockUomData) {
		this.stockUomData = stockUomData;
	}

	public Double getNetTotal() {
		return netTotal;
	}

	public void setNetTotal(Double netTotal) {
		this.netTotal = netTotal;
	}

	public Double getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(Double taxTotal) {
		this.taxTotal = taxTotal;
	}

	public Integer getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Integer grandTotal) {
		this.grandTotal = grandTotal;
	}





}
