package com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.model;

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
@Table(name = "mrp_hq_remote_request_dtl")
public class RPR_dtl  extends  GeneralModelBase  {




	


	
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;


	@Column(name = "remote_request_hdr_id")
	private Integer remote_request_hdr_id;
	
	@Transient
	private String uomcode;
	
	public String getUomcode() {
		return uomcode;
	}

	public void setUomcode(String uomcode) {
		this.uomcode = uomcode;
	}
	
	@Column(name = "stock_item_id")
	private Integer stock_item_id;
	
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

	@Column(name = "stock_item_code")
	private String stock_item_code;
		
	
	@Column(name = "stock_item_name")
	private String stock_item_name;
	
	
	@Column(name = "qty")
	private  Double qty;


	@Column(name = "request_status")
	private Integer request_status;
	

	
	@Column(name = "expected_date")
	private String expected_date;
	
	
	@Column(name = "po_id")
	private Integer po_id;

	
	
	
	

	
	

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

	/**
	 * @return the po_id
	 */
	public Integer getPo_id() {
		return po_id;
	}

	/**
	 * @param po_id the po_id to set
	 */
	public void setPo_id(Integer po_id) {
		this.po_id = po_id;
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
	@Counter(module="remote_request_dtl", key="remote_request_dtl")
	public void setId(Integer id) {
		this.id = id;
	}

	




	
	
	


	
	

			
	
}
