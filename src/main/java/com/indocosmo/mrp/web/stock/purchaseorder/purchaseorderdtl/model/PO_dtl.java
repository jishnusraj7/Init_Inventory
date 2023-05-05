package com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.model;

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

@Entity
@Table(name = "mrp_po_dtl")
public class PO_dtl  extends  GeneralModelBase  {





	
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;


	@Column(name = "po_hdr_id")
	private Integer po_hdr_id;
	
	
	
	

	@Transient
	private ArrayList deleteremotedtlid;

	@Transient
	private String deleteremotedtlid1;

	
	
	@Column(name = "po_dtl_status")
	private Integer po_dtl_status;
	
	



	/**
	 * @return the po_dtl_status
	 */
	public Integer getPo_dtl_status() {
		return po_dtl_status;
	}

	/**
	 * @param po_dtl_status the po_dtl_status to set
	 */
	public void setPo_dtl_status(Integer po_dtl_status) {
		this.po_dtl_status = po_dtl_status;
	}

	/**
	 * @return the deleteremotedtlid1
	 */
	public String getDeleteremotedtlid1() {
		return deleteremotedtlid1;
	}

	/**
	 * @param deleteremotedtlid1 the deleteremotedtlid1 to set
	 */
	public void setDeleteremotedtlid1(String deleteremotedtlid1) {
		this.deleteremotedtlid1 = deleteremotedtlid1;
	}

	/**
	 * @return the deleteremotedtlid
	 */
	public ArrayList getDeleteremotedtlid() {
		return deleteremotedtlid;
	}

	/**
	 * @param deleteremotedtlid the deleteremotedtlid to set
	 */
	public void setDeleteremotedtlid(ArrayList deleteremotedtlid) {
		this.deleteremotedtlid = deleteremotedtlid;
	}

	@Column(name = "company_id")
	private Integer company_id;
	
	@Column(name = "company_code")
	private String company_code;
		
	
	@Column(name = "company_name")
	private String company_name;
	
	
	
	
	@Column(name = "request_dtl_id")
	private Integer request_dtl_id;
	
	
	@Transient
	private Integer remote_request_status;
	
	
	public Integer getRemote_request_status() {
		return remote_request_status;
	}

	public void setRemote_request_status(Integer remote_request_status) {
		this.remote_request_status = remote_request_status;
	}

	@Transient
	private Integer remote_request_hdr_id;

	/**
	 * @return the remote_request_hdr_id
	 */
	public Integer getRemote_request_hdr_id() {
		return remote_request_hdr_id;
	}

	/**
	 * @param remote_request_hdr_id the remote_request_hdr_id to set
	 */
	public void setRemote_request_hdr_id(Integer remote_request_hdr_id) {
		this.remote_request_hdr_id = remote_request_hdr_id;
	}

	/**
	 * @return the company_id
	 */
	public Integer getCompany_id() {
		return company_id;
	}

	/**
	 * @param company_id the company_id to set
	 */
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	/**
	 * @return the company_code
	 */
	public String getCompany_code() {
		return company_code;
	}

	/**
	 * @param company_code the company_code to set
	 */
	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	/**
	 * @return the company_name
	 */
	public String getCompany_name() {
		return company_name;
	}

	/**
	 * @param company_name the company_name to set
	 */
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}


	/**
	 * @return the request_dtl_id
	 */
	public Integer getRequest_dtl_id() {
		return request_dtl_id;
	}

	/**
	 * @param request_dtl_id the request_dtl_id to set
	 */
	public void setRequest_dtl_id(Integer request_dtl_id) {
		this.request_dtl_id = request_dtl_id;
	}

	@Column(name = "stock_item_id")
	private Integer stock_item_id;
	
	@Column(name = "stock_item_code")
	private String stock_item_code;
		
	
	@Column(name = "stock_item_name")
	private String stock_item_name;
	
	
	@Column(name = "qty")
	private  Double qty;

	
	@Column(name = "unit_price")
	private  Double unit_price;
	
	@Column(name = "amount")
	private  Double amount;
	
	@Column(name = "expected_date")
	private String expected_date;
	
	
	/**
	 * @return the expected_date
	 */
	public String getExpected_date() {
		return expected_date;
	}

	/**
	 * @param expected_date the expected_date to set
	 */
	public void setExpected_date(String expected_date) {
		this.expected_date = expected_date;
	}




	
	@Column(name = "is_tax_inclusive")
	private  Integer is_tax_inclusive;
		
	/**
	 * @return the is_tax_inclusive
	 */
	public Integer getIs_tax_inclusive() {
		return is_tax_inclusive;
	}

	/**
	 * @param is_tax_inclusive the is_tax_inclusive to set
	 */
	public void setIs_tax_inclusive(Integer is_tax_inclusive) {
		this.is_tax_inclusive = is_tax_inclusive;
	}

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
	@Counter(module="po_dtl", key="po_dtl")
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the po_hdr_id
	 */
	public Integer getPo_hdr_id() {
		return po_hdr_id;
	}

	/**
	 * @param po_hdr_id the po_hdr_id to set
	 */
	public void setPo_hdr_id(Integer po_hdr_id) {
		this.po_hdr_id = po_hdr_id;
	}

	/**
	 * @return the stock_item_id
	 */
	public Integer getStock_item_id() {
		return stock_item_id;
	}

	/**
	 * @param stock_item_id the stock_item_id to set
	 */
	public void setStock_item_id(Integer stock_item_id) {
		this.stock_item_id = stock_item_id;
	}

	/**
	 * @return the stock_item_code
	 */
	public String getStock_item_code() {
		return stock_item_code;
	}

	/**
	 * @param stock_item_code the stock_item_code to set
	 */
	public void setStock_item_code(String stock_item_code) {
		this.stock_item_code = stock_item_code;
	}

	/**
	 * @return the stock_item_name
	 */
	public String getStock_item_name() {
		return stock_item_name;
	}

	/**
	 * @param stock_item_name the stock_item_name to set
	 */
	public void setStock_item_name(String stock_item_name) {
		this.stock_item_name = stock_item_name;
	}

	/**
	 * @return the qty
	 */
	public Double getQty() {
		return qty;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(Double qty) {
		this.qty = qty;
	}

	/**
	 * @return the unit_price
	 */
	public Double getUnit_price() {
		return unit_price;
	}

	/**
	 * @param unit_price the unit_price to set
	 */
	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}







	
	
	


	
	

			
	
}
