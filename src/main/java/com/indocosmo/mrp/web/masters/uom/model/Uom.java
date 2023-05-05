package com.indocosmo.mrp.web.masters.uom.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.MasterModelBase;
import com.indocosmo.mrp.web.masters.department.model.Department;

@Entity
@Table(name = "uoms")
public class Uom extends MasterModelBase {

	@Column(name = "is_compound")
	private Integer isCompound;
	
	@Column(name = "base_uom_id")
	private Integer base_uom_id;
	
	@Column(name = "compound_unit")
	private Integer compound_unit;
	
	@Column(name = "uom_symbol")
	private String uom_symbol;
	
	@Transient
	private List<Uom> uomExcelData;



	/**
	 * @return the uomExcelData
	 */
	public List<Uom> getUomExcelData() {
		return uomExcelData;
	}






	/**
	 * @param uomExcelData the uomExcelData to set
	 */
	public void setUomExcelData(List<Uom> uomExcelData) {
		this.uomExcelData = uomExcelData;
	}






	/**
	 * @return the isCompound
	 */
	public Integer getIsCompound() {
		return isCompound;
	}






	/**
	 * @param isCompound the isCompound to set
	 */
	public void setIsCompound(Integer isCompound) {
		this.isCompound = isCompound;
	}






	/**
	 * @return the base_uom_id
	 */
	public Integer getBase_uom_id() {
		return base_uom_id;
	}






	/**
	 * @return the compound_unit
	 */
	public Integer getCompound_unit() {
		return compound_unit;
	}






	/**
	 * @return the uom_symbol
	 */
	public String getUom_symbol() {
		return uom_symbol;
	}






	/**
	 * @return the decimal_places
	 */
	public Integer getDecimal_places() {
		return decimal_places;
	}






	/**
	 * @return the created_by
	 */
	public Integer getCreated_by() {
		return created_by;
	}






	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}






	/**
	 * @return the updated_by
	 */
	public Integer getUpdated_by() {
		return updated_by;
	}






	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
		return updated_at;
	}












	/**
	 * @param base_uom_id the base_uom_id to set
	 */
	public void setBase_uom_id(Integer base_uom_id) {
		this.base_uom_id = base_uom_id;
	}






	/**
	 * @param compound_unit the compound_unit to set
	 */
	public void setCompound_unit(Integer compound_unit) {
		this.compound_unit = compound_unit;
	}






	/**
	 * @param uom_symbol the uom_symbol to set
	 */
	public void setUom_symbol(String uom_symbol) {
		this.uom_symbol = uom_symbol;
	}






	/**
	 * @param decimal_places the decimal_places to set
	 */
	public void setDecimal_places(Integer decimal_places) {
		this.decimal_places = decimal_places;
	}






	/**
	 * @param created_by the created_by to set
	 */
	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}






	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}






	/**
	 * @param updated_by the updated_by to set
	 */
	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}






	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}






	@Column(name = "decimal_places")
	private Integer decimal_places;
	
	@Column(name = "created_by")
	private Integer created_by = 0;

	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "updated_at")
	private String updated_at;
	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.model.MasterModelBase#setId(java.lang.Integer)
	 */
	@Override
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="uom", key="uom")
	public void setId(Integer id) {
	
		super.setId(id);
	}
	
	
	
}
