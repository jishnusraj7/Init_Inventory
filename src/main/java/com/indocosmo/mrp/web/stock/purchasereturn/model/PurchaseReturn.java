package com.indocosmo.mrp.web.stock.purchasereturn.model;

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
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.controller.PurchaseReturnDetailController;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.model.PurchaseReturnDetail;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.model.StockInDetail;

@Entity
@Table(name = "mrp_stock_return_hdr")
public class PurchaseReturn extends GeneralModelBase{

	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "return_no")
	private String return_no;
	
	@Column(name = "return_date")
	private String return_date;
	
	@Column(name = "return_status")
	private Integer return_status;
	
	@Column(name = "department_id")
	private Integer department_id;
	
	@Column(name = "supplier_id")
	private Integer supplier_id;
	
	@Column(name = "supplier_code")
	private String supplier_code;
	
	@Column(name = "supplier_name")
	private String supplier_name;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "created_by")
	private Integer created_by;
	
	@Column(name = "created_date")
	private String created_date;
		
	public Integer getId() {
		return id;
	}
	
	@Id(generationType = GenerationType.COUNTER)
	@Counter(module="stock_return_hdr", key="stock_return_hdr")
	
	@Transient
	private ArrayList<PurchaseReturnDetail> returnStockDetails;
	
	@Transient
	private String returnDetailLists;
	
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="stock_return_hdr", key="stock_return_hdr")
	public void setId(Integer id) {
		this.id = id;
	}

	public String getReturn_no() {
		return return_no;
	}

	public void setReturn_no(String return_no) {
		this.return_no = return_no;
	}

	public String getReturn_date() {
		return return_date;
	}

	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}

	public Integer getReturn_status() {
		return return_status;
	}

	public void setReturn_status(Integer return_status) {
		this.return_status = return_status;
	}

	public Integer getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getSupplier_code() {
		return supplier_code;
	}

	public void setSupplier_code(String supplier_code) {
		this.supplier_code = supplier_code;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getCreated_by() {
		return created_by;
	}

	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public ArrayList<PurchaseReturnDetail> getReturnStockDetails() {
		return returnStockDetails;
	}

	public void setReturnStockDetails(ArrayList<PurchaseReturnDetail> returnStockDetails) {
		this.returnStockDetails = returnStockDetails;
	}

	public String getReturnDetailLists() {
		return returnDetailLists;
	}

	public void setReturnDetailLists(String returnDetailLists) {
		this.returnDetailLists = returnDetailLists;
	}

	public Integer getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}
	
}
