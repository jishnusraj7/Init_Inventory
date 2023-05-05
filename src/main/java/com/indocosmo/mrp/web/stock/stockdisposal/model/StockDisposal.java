package com.indocosmo.mrp.web.stock.stockdisposal.model;

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
import com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.model.StockDisposalDetail;

@Entity
@Table(name="mrp_stock_disposal_hdr")
public class StockDisposal extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	@Column( name ="department_id")
	private Integer departmentId;
	
	@Column( name ="ref_no")
	private String refNo;
	
	@Column( name ="disposal_date")
	private String disposalDate;
	
	@Column( name ="disposal_by")
	private String disposalBy;
	
	@Column( name ="total_amount")
	private Double totalAmount;
	
	@Transient
	private ArrayList<StockDisposalDetail> stockDisposalDetails;

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
	@Counter(module="stock_disposal_hdr", key="stock_disposal_hdr")
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
	 * @return the refNo
	 */
	public String getRefNo() {
		return refNo;
	}

	/**
	 * @param refNo the refNo to set
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	/**
	 * @return the disposalDate
	 */
	public String getDisposalDate() {
		return disposalDate;
	}

	/**
	 * @param disposalDate the disposalDate to set
	 */
	public void setDisposalDate(String disposalDate) {
		this.disposalDate = disposalDate;
	}

	/**
	 * @return the disposalBy
	 */
	public String getDisposalBy() {
		return disposalBy;
	}

	/**
	 * @param disposalBy the disposalBy to set
	 */
	public void setDisposalBy(String disposalBy) {
		this.disposalBy = disposalBy;
	}

	/**
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the stockDisposalDetails
	 */
	public ArrayList<StockDisposalDetail> getStockDisposalDetails() {
		return stockDisposalDetails;
	}

	/**
	 * @param stockDisposalDetails the stockDisposalDetails to set
	 */
	public void setStockDisposalDetails(
			ArrayList<StockDisposalDetail> stockDisposalDetails) {
		this.stockDisposalDetails = stockDisposalDetails;
	}

	
	
	
}
