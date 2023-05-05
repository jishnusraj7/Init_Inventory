package com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "mrp_stock_return_dtl")
public class PurchaseReturnDetail extends GeneralModelBase{

	@Pk
	@Id(generationType = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "stock_return_hdr_id")
	private Integer stock_return_hdr_id;
	
	@Column(name = "stock_item_id")
	private Integer stock_item_id;
	
	@Column(name = "stock_item_code")
	private String stock_item_code;
	
	@Column(name = "uomcode")
	private String uomcode;
	
	@Column(name = "stock_item_name")
	private String stock_item_name;
	
	@Column(name = "return_qty")
	private Double return_qty;
	
	@Column(name = "base_uom_code")
	private String base_uom_code;
	
	@Column(name = "compound_unit")
	private Double compound_unit;
	
	@Column(name = "issued_qty")
	private Double issued_qty;
	
	public Integer getId() {
		return id;
	}
	
	@Id(generationType = GenerationType.COUNTER)
	@Counter(module = "stock_return_dtl", key = "stock_return_dtl")
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStock_return_hdr_id() {
		return stock_return_hdr_id;
	}

	public void setStock_return_hdr_id(Integer stock_return_hdr_id) {
		this.stock_return_hdr_id = stock_return_hdr_id;
	}

	public Integer getStock_item_id() {
		return stock_item_id;
	}

	public void setStock_item_id(Integer stock_item_id) {
		this.stock_item_id = stock_item_id;
	}

	public String getStock_item_code() {
		return stock_item_code;
	}

	public void setStock_item_code(String stock_item_code) {
		this.stock_item_code = stock_item_code;
	}

	public String getStock_item_name() {
		return stock_item_name;
	}

	public void setStock_item_name(String stock_item_name) {
		this.stock_item_name = stock_item_name;
	}
	
	public String getUomcode() {
		return uomcode;
	}

	public void setUomcode(String uomcode) {
		this.uomcode = uomcode;
	}

	public Double getReturn_qty() {
		return return_qty;
	}

	public void setReturn_qty(Double return_qty) {
		this.return_qty = return_qty;
	}

	public String getBase_uom_code() {
		return base_uom_code;
	}

	public void setBase_uom_code(String base_uom_code) {
		this.base_uom_code = base_uom_code;
	}

	public Double getCompound_unit() {
		return compound_unit;
	}

	public void setCompound_unit(Double compound_unit) {
		this.compound_unit = compound_unit;
	}

	public Double getIssued_qty() {
		return issued_qty;
	}

	public void setIssued_qty(Double issued_qty) {
		this.issued_qty = issued_qty;
	}

	

	
	
	/*public Double getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Double currentStock) {
		this.currentStock = currentStock;
	}
	*/
}
