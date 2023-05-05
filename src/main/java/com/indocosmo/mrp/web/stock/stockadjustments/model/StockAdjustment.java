package com.indocosmo.mrp.web.stock.stockadjustments.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="mrp_stock_adjust_hdr")
public class StockAdjustment extends GeneralModelBase{
	
	@Column(name = "adjust_by")
	private String adjust_by;

	@Column(name = "adjust_date")
	private String adjust_date;
	
	@Column(name = "approval_by")
	private String approval_by;

	
	@Column(name = "approval_date")
	private String approval_date;
	@Transient
	private Integer approvalStatus;



	@Column( name ="department_id")
	private Integer departmentId;



	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;






	@Column(name = "ref_no")
	private String ref_no;
	@Column(name = "remarks")
	private String remarks ;

	public String getAdjust_by() {
		return adjust_by;
	}






	public String getAdjust_date() {
		return adjust_date;
	}
	
	
	public String getApproval_by() {
		return approval_by;
	}
	
	
	
	public String getApproval_date() {
		return approval_date;
	}
	
	
	
	public Integer getApprovalStatus() {
		return approvalStatus;
	}



	public Integer getDepartmentId() {
		return departmentId;
	}



	
	public Integer getId() {
		return id;
	}
	
	public String getRef_no() {
		return ref_no;
	}



	public String getRemarks() {
		return remarks;
	}



	public void setAdjust_by(String adjust_by) {
		this.adjust_by = adjust_by;
	}



	public void setAdjust_date(String adjust_date) {
		this.adjust_date = adjust_date;
	}



	public void setApproval_by(String approval_by) {
		this.approval_by = approval_by;
	}



	public void setApproval_date(String approval_date) {
		this.approval_date = approval_date;
	}



	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}



	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}



	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="stock_adjust_hdr", key="stock_adjust_hdr")
	public void setId(Integer id) {
		this.id = id;
	}



	public void setRef_no(String ref_no) {
		this.ref_no = ref_no;
	}



	

	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}	
	
}
