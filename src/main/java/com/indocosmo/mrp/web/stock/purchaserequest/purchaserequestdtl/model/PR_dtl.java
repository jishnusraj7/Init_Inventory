package com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.model;

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
@Table(name = "mrp_pr_dtl")
public class PR_dtl  extends  GeneralModelBase  {







	
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;


	@Column(name = "pr_hdr_id")
	private Integer pr_hdr_id;
	
	
	
	@Column(name = "stock_item_id")
	private Integer stock_item_id;
	
	@Column(name = "stock_item_code")
	private String stock_item_code;
		
	
	@Column(name = "stock_item_name")
	private String stock_item_name;
	
	@Transient
	private String uomcode;
	
	public String getUomcode() {
		return uomcode;
	}

	public void setUomcode(String uomcode) {
		this.uomcode = uomcode;
	}

	@Column(name = "qty")
	private  Double qty;

	/**
	 * @return the pr_hdr_id
	 */
	public Integer getPr_hdr_id() {
		return pr_hdr_id;
	}

	/**
	 * @param pr_hdr_id the pr_hdr_id to set
	 */
	public void setPr_hdr_id(Integer pr_hdr_id) {
		this.pr_hdr_id = pr_hdr_id;
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
	 * @return the request_status
	 */
	public Integer getRequest_status() {
		return request_status;
	}

	/**
	 * @param request_status the request_status to set
	 */
	public void setRequest_status(Integer request_status) {
		this.request_status = request_status;
	}

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

	@Column(name = "request_status")
	private Integer request_status;
	

	
	@Column(name = "expected_date")
	private String expected_date;
	
	
	
	

	
	

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
	@Counter(module="pr_dtl", key="pr_dtl")
	public void setId(Integer id) {
		this.id = id;
	}

	




	
	
	


	
	

			
	
}
