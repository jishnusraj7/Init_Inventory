package com.indocosmo.mrp.web.stock.stocktransfer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

/**
 * @author Panasonic
 *
 */
@Entity
@Table(name ="mrp_stock_transfer_hdr")
public class StockTransfer extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column( name ="dest_company_code")
	private String dest_company_code;
	
	@Column( name ="dest_company_id")
	private Integer dest_company_id;
	
	@Column( name ="dest_company_name")
	private String dest_company_name;
	
	@Column( name ="source_department_id")
	private Integer source_department_id;
	
	@Column( name ="dest_department_id")
	private Integer dest_department_id;
	
	@Column( name ="req_by")
	private Integer req_by=0;
	
	@Column( name ="transfer_type")
	private Integer transfer_type=0;

	@Column( name ="order_date")
	private String order_date;
	
	@Column( name ="stock_transfer_no")
	private String stock_transfer_no;
	
	@Column( name ="stock_transfer_date")
	private String stock_transfer_date;
	
	@Column( name ="total_amount")
	private Double total_amount=0.00;
	
	@Column( name ="req_status")
	private Integer req_status;
	
	@Column( name ="order_no")
	private String order_no;
	
	@Column( name ="dest_department_code")
	private String dest_department_code;
	
	@Column( name ="dest_department_name")
	private String dest_department_name;
	
	@Transient
	private String stockDetailLists;

	public Integer getId() {
		return id;
	}

	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="stock_transfer_hdr", key="stock_transfer_hdr")
	public void setId(Integer id) {
	
		this.id = id;
	}

	public String getDest_company_code() {
		return dest_company_code;
	}

	public void setDest_company_code(String dest_company_code) {
		this.dest_company_code = dest_company_code;
	}

	public Integer getDest_company_id() {
		return dest_company_id;
	}

	public void setDest_company_id(Integer dest_company_id) {
		this.dest_company_id = dest_company_id;
	}

	public String getDest_company_name() {
		return dest_company_name;
	}

	public void setDest_company_name(String dest_company_name) {
		this.dest_company_name = dest_company_name;
	}

	public Integer getSource_department_id() {
		return source_department_id;
	}

	public void setSource_department_id(Integer source_department_id) {
		this.source_department_id = source_department_id;
	}

	public Integer getDest_department_id() {
		return dest_department_id;
	}

	public void setDest_department_id(Integer dest_department_id) {
		this.dest_department_id = dest_department_id;
	}

	public Integer getReq_by() {
		return req_by;
	}

	public void setReq_by(Integer req_by) {
		this.req_by = req_by;
	}

	public Integer getTransfer_type() {
		return transfer_type;
	}

	public void setTransfer_type(Integer transfer_type) {
		this.transfer_type = transfer_type;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public String getStock_transfer_no() {
		return stock_transfer_no;
	}

	public void setStock_transfer_no(String stock_transfer_no) {
		this.stock_transfer_no = stock_transfer_no;
	}

	public String getStock_transfer_date() {
		return stock_transfer_date;
	}

	public void setStock_transfer_date(String stock_transfer_date) {
		this.stock_transfer_date = stock_transfer_date;
	}

	public Double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}

	public Integer getReq_status() {
		return req_status;
	}

	public void setReq_status(Integer req_status) {
		this.req_status = req_status;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getDest_department_code() {
		return dest_department_code;
	}

	public void setDest_department_code(String dest_department_code) {
		this.dest_department_code = dest_department_code;
	}

	public String getDest_department_name() {
		return dest_department_name;
	}

	public void setDest_department_name(String dest_department_name) {
		this.dest_department_name = dest_department_name;
	}

	public String getStockDetailLists() {
		return stockDetailLists;
	}

	public void setStockDetailLists(String stockDetailLists) {
		this.stockDetailLists = stockDetailLists;
	}	
}
