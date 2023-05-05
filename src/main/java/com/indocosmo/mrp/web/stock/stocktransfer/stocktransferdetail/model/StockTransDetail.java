package com.indocosmo.mrp.web.stock.stocktransfer.stocktransferdetail.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name="mrp_stock_transfer_dtl")
public class StockTransDetail extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Pk
	@Column(name = "stock_transfer_hdr_id")
	private Integer stock_transfer_hdr_id;
	
	@Column(name = "stock_item_id")
	private Integer stock_item_id;
	
	@Column( name ="stock_item_code")
	private String stock_item_code;
	
	@Column( name ="stock_item_name")
	private String stock_item_name;
	
	@Column( name ="request_qty")
	private Double request_qty=0.00;
	
	@Column( name ="issued_qty")
	private Double issued_qty=0.00;
	
	@Column( name ="cost_price")
	private Double cost_price=0.00;
	
	@Column( name ="amount")
	private Double amount=0.00;
	
	
	
	@Column( name ="tax_pc")
	private Double tax_pc=0.00;
	
	@Column(name="uom_code")
	private String uomCode;
	
	@Column( name ="base_uom_code")
	private String baseUomCode;
	
	@Column( name ="compound_unit")
	private Double compoundUnit;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
	
		return id;
	}

	
	/**
	 * @return the stock_transfer_hdr_id
	 */
	public Integer getStock_transfer_hdr_id() {
	
		return stock_transfer_hdr_id;
	}

	
	/**
	 * @return the stock_item_id
	 */
	public Integer getStock_item_id() {
	
		return stock_item_id;
	}

	
	/**
	 * @return the stock_item_code
	 */
	public String getStock_item_code() {
	
		return stock_item_code;
	}

	
	/**
	 * @return the stock_item_name
	 */
	public String getStock_item_name() {
	
		return stock_item_name;
	}

	
	/**
	 * @return the request_qty
	 */
	public Double getRequest_qty() {
	
		return request_qty;
	}

	
	/**
	 * @return the issued_qty
	 */
	public Double getIssued_qty() {
	
		return issued_qty;
	}

	
	/**
	 * @return the cost_price
	 */
	public Double getCost_price() {
	
		return cost_price;
	}

	
	/**
	 * @return the amount
	 */
	public Double getAmount() {
	
		return amount;
	}

	
	/**
	 * @return the tax_pc
	 */
	public Double getTax_pc() {
	
		return tax_pc;
	}

	
	

	
	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="stock_transfer_dtl", key="stock_transfer_dtl")
	public void setId(Integer id) {
	
		this.id = id;
	}

	
	/**
	 * @param stock_transfer_hdr_id the stock_transfer_hdr_id to set
	 */
	public void setStock_transfer_hdr_id(Integer stock_transfer_hdr_id) {
	
		this.stock_transfer_hdr_id = stock_transfer_hdr_id;
	}

	
	/**
	 * @param stock_item_id the stock_item_id to set
	 */
	public void setStock_item_id(Integer stock_item_id) {
	
		this.stock_item_id = stock_item_id;
	}

	
	/**
	 * @param stock_item_code the stock_item_code to set
	 */
	public void setStock_item_code(String stock_item_code) {
	
		this.stock_item_code = stock_item_code;
	}

	
	/**
	 * @param stock_item_name the stock_item_name to set
	 */
	public void setStock_item_name(String stock_item_name) {
	
		this.stock_item_name = stock_item_name;
	}

	
	/**
	 * @param request_qty the request_qty to set
	 */
	public void setRequest_qty(Double request_qty) {
	
		this.request_qty = request_qty;
	}

	
	/**
	 * @param issued_qty the issued_qty to set
	 */
	public void setIssued_qty(Double issued_qty) {
	
		this.issued_qty = issued_qty;
	}

	
	/**
	 * @param cost_price the cost_price to set
	 */
	public void setCost_price(Double cost_price) {
	
		this.cost_price = cost_price;
	}

	
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
	
		this.amount = amount;
	}

	
	/**
	 * @param tax_pc the tax_pc to set
	 */
	public void setTax_pc(Double tax_pc) {
	
		this.tax_pc = tax_pc;
	}


	public String getBaseUomCode() {
		return baseUomCode;
	}


	public void setBaseUomCode(String baseUomCode) {
		this.baseUomCode = baseUomCode;
	}


	public Double getCompoundUnit() {
		return compoundUnit;
	}


	public void setCompoundUnit(Double compoundUnit) {
		this.compoundUnit = compoundUnit;
	}


	public String getUomCode() {
		return uomCode;
	}


	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}

	
	

	
	
}
