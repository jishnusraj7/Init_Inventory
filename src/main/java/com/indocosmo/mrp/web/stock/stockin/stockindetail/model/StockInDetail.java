package com.indocosmo.mrp.web.stock.stockin.stockindetail.model;

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
@Table(name="mrp_stock_in_dtl")
public class StockInDetail extends GeneralModelBase{

	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Pk
	@Column(name = "stock_in_hdr_id")
	private Integer stockInHdrId;
		
	@Column(name = "stock_item_id")
	private Integer stockItemId;
	
	@Column( name ="stock_item_code")
	private String stockItemCode;
	
	@Column( name ="stock_item_name")
	private String stockItemName;
	
	@Column(name = "po_id")
	private Integer poId;
	
	@Column( name ="po_qty")
	private Double poQty=0.00;
	
	@Column( name ="received_qty")
	private Double receivedQty=0.00;
	
	@Column( name ="pack_qty")
	private Double pack_qty=0.00;
	
	@Column(name="uom_code")
	private String uomCode;
	
	@Column( name ="base_uom_code")
	private String baseUomCode;
	
	@Column( name ="compound_unit")
	private Double compoundUnit;

	
	
	/**
	 * @return the pack_qty
	 */
	public Double getPack_qty() {
	
		return pack_qty;
	}

	
	/**
	 * @param pack_qty the pack_qty to set
	 */
	public void setPack_qty(Double pack_qty) {
	
		this.pack_qty = pack_qty;
	}

	@Column( name ="unit_price")
	private Double unitPrice=0.00;
	
	@Column( name ="amount")
	private Double amount=0.00;
	
	@Column(name = "tax_id")
	private Integer taxId=0;
	
	@Column( name ="tax_pc")
	private Double taxPc=0.00;
	
	@Column( name ="tax_amount")
	private Double taxAmount=0.00;
	
	/*@Transient
	private Integer deletedPoDtlId;*/
	
	@Transient
	private String deletedPOItems;
	
	
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
	@Counter(module="stock_in_dtl", key="stock_in_dtl")
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the stockInHdrId
	 */
	public Integer getStockInHdrId() {
		return stockInHdrId;
	}

	/**
	 * @param stockInHdrId the stockInHdrId to set
	 */
	public void setStockInHdrId(Integer stockInHdrId) {
		this.stockInHdrId = stockInHdrId;
	}

	/**
	 * @return the stockItemId
	 */
	public Integer getStockItemId() {
		return stockItemId;
	}

	/**
	 * @param stockItemId the stockItemId to set
	 */
	public void setStockItemId(Integer stockItemId) {
		this.stockItemId = stockItemId;
	}

	/**
	 * @return the stockItemCode
	 */
	public String getStockItemCode() {
		return stockItemCode;
	}

	/**
	 * @param stockItemCode the stockItemCode to set
	 */
	public void setStockItemCode(String stockItemCode) {
		this.stockItemCode = stockItemCode;
	}

	/**
	 * @return the stockItemName
	 */
	public String getStockItemName() {
		return stockItemName;
	}

	/**
	 * @param stockItemName the stockItemName to set
	 */
	public void setStockItemName(String stockItemName) {
		this.stockItemName = stockItemName;
	}

	/**
	 * @return the poId
	 */
	public Integer getPoId() {
		return poId;
	}

	/**
	 * @param poId the poId to set
	 */
	public void setPoId(Integer poId) {
		this.poId = poId;
	}

	/**
	 * @return the poQty
	 */
	public Double getPoQty() {
		return poQty;
	}

	/**
	 * @param poQty the poQty to set
	 */
	public void setPoQty(Double poQty) {
		this.poQty = poQty;
	}

	/**
	 * @return the receivedQty
	 */
	public Double getReceivedQty() {
		return receivedQty;
	}

	/**
	 * @param receivedQty the receivedQty to set
	 */
	public void setReceivedQty(Double receivedQty) {
		this.receivedQty = receivedQty;
	}

	/**
	 * @return the unitPrice
	 */
	public Double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
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

	/**
	 * @return the deletedPOItems
	 */
	public String getDeletedPOItems() {
		return deletedPOItems;
	}

	/**
	 * @param deletedPOItems the deletedPOItems to set
	 */
	public void setDeletedPOItems(String deletedPOItems) {
		this.deletedPOItems = deletedPOItems;
	}

	/**
	 * @return the taxId
	 */
	public Integer getTaxId() {
		return taxId;
	}

	/**
	 * @param taxId the taxId to set
	 */
	public void setTaxId(Integer taxId) {
		this.taxId = taxId;
	}

	/**
	 * @return the taxPc
	 */
	public Double getTaxPc() {
		return taxPc;
	}

	/**
	 * @param taxPc the taxPc to set
	 */
	public void setTaxPc(Double taxPc) {
		this.taxPc = taxPc;
	}

	/**
	 * @return the taxAmount
	 */
	public Double getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
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