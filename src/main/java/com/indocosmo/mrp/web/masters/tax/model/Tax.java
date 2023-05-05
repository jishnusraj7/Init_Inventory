package com.indocosmo.mrp.web.masters.tax.model;

import java.util.List;

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
@Table(name = "taxes")
public class Tax extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;	

	
	
	@Column(name = "is_deleted")
	private Integer isDeleted=0;
	
	
	@Column(name = "created_by")
	private Integer created_by = 0;
	
	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;

	@Column(name = "updated_at")
	private String updated_at;
	
	@Column(name = "is_tax1_applicable")
	private Integer is_tax1_applicable;
	
	@Column(name = "is_tax2_applicable")
	private Integer is_tax2_applicable;
	
	@Column(name = "is_tax3_applicable")
	private Integer is_tax3_applicable;
	
	@Column(name = "tax1_percentage")
	private Double tax1_percentage;
	
	@Column(name = "tax2_percentage")
	private Double tax2_percentage;
	
	@Column(name = "tax3_percentage")
	private Double tax3_percentage;
	
	@Column(name = "tax1_refund_rate")
	private Double tax1_refund_rate;
	
	@Column(name = "tax2_refund_rate")
	private Double tax2_refund_rate;
	
	@Column(name = "tax3_refund_rate")
	private Double tax3_refund_rate;
	
	@Column(name = "is_sc_applicable")
	private Integer is_sc_applicable=0;
	
	@Column(name = "is_define_gst")
	private Integer is_define_gst=0;
	
	@Column(name = "is_tax1_included_in_gst")
	private Integer is_tax1_included_in_gst=0;
	
	@Column(name = "is_tax2_included_in_gst")
	private Integer is_tax2_included_in_gst=0;
	
	@Column(name = "is_tax3_included_in_gst")
	private Integer is_tax3_included_in_gst=0;
	
	@Column(name = "is_sc_included_in_gst")
	private Integer is_sc_included_in_gst=0;
	
	@Transient
	private List<Tax> taxExcelData;
	
	
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="tax", key="tax")
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	/**
	 * @return the id
	 */
	public Integer getId() {
	
		return id;
	}

	
	/**
	 * @return the code
	 */
	public String getCode() {
	
		return code;
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
	
		return name;
	}

	
	/**
	 * @return the isDeleted
	 */
	public Integer getIsDeleted() {
	
		return isDeleted;
	}

	
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
	
		this.code = code;
	}

	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
	
		this.name = name;
	}

	
	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Integer isDeleted) {
	
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the taxExcelData
	 */
	public List<Tax> getTaxExcelData() {
		return taxExcelData;
	}

	/**
	 * @param taxExcelData the taxExcelData to set
	 */
	public void setTaxExcelData(List<Tax> taxExcelData) {
		this.taxExcelData = taxExcelData;
	}

	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}

	/**
	 * @return the tax1_refund_rate
	 */
	public Double getTax1_refund_rate() {
		return tax1_refund_rate;
	}

	/**
	 * @return the tax2_refund_rate
	 */
	public Double getTax2_refund_rate() {
		return tax2_refund_rate;
	}

	/**
	 * @return the tax3_refund_rate
	 */
	public Double getTax3_refund_rate() {
		return tax3_refund_rate;
	}

	/**
	 * @param tax1_refund_rate the tax1_refund_rate to set
	 */
	public void setTax1_refund_rate(Double tax1_refund_rate) {
		this.tax1_refund_rate = tax1_refund_rate;
	}

	/**
	 * @param tax2_refund_rate the tax2_refund_rate to set
	 */
	public void setTax2_refund_rate(Double tax2_refund_rate) {
		this.tax2_refund_rate = tax2_refund_rate;
	}

	/**
	 * @param tax3_refund_rate the tax3_refund_rate to set
	 */
	public void setTax3_refund_rate(Double tax3_refund_rate) {
		this.tax3_refund_rate = tax3_refund_rate;
	}

	/**
	 * @return the created_by
	 */
	public Integer getCreated_by() {
		return created_by;
	}

	/**
	 * @return the is_tax1_applicable
	 */
	public Integer getIs_tax1_applicable() {
		return is_tax1_applicable;
	}

	/**
	 * @return the is_tax2_applicable
	 */
	public Integer getIs_tax2_applicable() {
		return is_tax2_applicable;
	}

	/**
	 * @return the is_tax3_applicable
	 */
	public Integer getIs_tax3_applicable() {
		return is_tax3_applicable;
	}

	/**
	 * @return the tax1_percentage
	 */
	public Double getTax1_percentage() {
		return tax1_percentage;
	}

	/**
	 * @return the tax2_percentage
	 */
	public Double getTax2_percentage() {
		return tax2_percentage;
	}

	/**
	 * @return the tax3_percentage
	 */
	public Double getTax3_percentage() {
		return tax3_percentage;
	}

	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
		return updated_at;
	}

	/**
	 * @return the updated_by
	 */
	public Integer getUpdated_by() {
		return updated_by;
	}

	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	/**
	 * @param created_by the created_by to set
	 */
	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}

	

	/**
	 * @param is_tax1_applicable the is_tax1_applicable to set
	 */
	public void setIs_tax1_applicable(Integer is_tax1_applicable) {
		this.is_tax1_applicable = is_tax1_applicable;
	}

	/**
	 * @param is_tax2_applicable the is_tax2_applicable to set
	 */
	public void setIs_tax2_applicable(Integer is_tax2_applicable) {
		this.is_tax2_applicable = is_tax2_applicable;
	}

	/**
	 * @param is_tax3_applicable the is_tax3_applicable to set
	 */
	public void setIs_tax3_applicable(Integer is_tax3_applicable) {
		this.is_tax3_applicable = is_tax3_applicable;
	}

	/**
	 * @param tax1_percentage the tax1_percentage to set
	 */
	public void setTax1_percentage(Double tax1_percentage) {
		this.tax1_percentage = tax1_percentage;
	}

	/**
	 * @param tax2_percentage the tax2_percentage to set
	 */
	public void setTax2_percentage(Double tax2_percentage) {
		this.tax2_percentage = tax2_percentage;
	}

	/**
	 * @param tax3_percentage the tax3_percentage to set
	 */
	public void setTax3_percentage(Double tax3_percentage) {
		this.tax3_percentage = tax3_percentage;
	}

	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	/**
	 * @param updated_by the updated_by to set
	 */
	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}

	/**
	 * @return the is_sc_applicable
	 */
	public Integer getIs_sc_applicable() {
		return is_sc_applicable;
	}

	/**
	 * @return the is_define_gst
	 */
	public Integer getIs_define_gst() {
		return is_define_gst;
	}

	/**
	 * @return the is_tax1_included_in_gst
	 */
	public Integer getIs_tax1_included_in_gst() {
		return is_tax1_included_in_gst;
	}

	/**
	 * @return the is_tax2_included_in_gst
	 */
	public Integer getIs_tax2_included_in_gst() {
		return is_tax2_included_in_gst;
	}

	/**
	 * @return the is_tax3_included_in_gst
	 */
	public Integer getIs_tax3_included_in_gst() {
		return is_tax3_included_in_gst;
	}

	/**
	 * @return the is_sc_included_in_gst
	 */
	public Integer getIs_sc_included_in_gst() {
		return is_sc_included_in_gst;
	}

	/**
	 * @param is_sc_applicable the is_sc_applicable to set
	 */
	public void setIs_sc_applicable(Integer is_sc_applicable) {
		this.is_sc_applicable = is_sc_applicable;
	}

	/**
	 * @param is_define_gst the is_define_gst to set
	 */
	public void setIs_define_gst(Integer is_define_gst) {
		this.is_define_gst = is_define_gst;
	}

	/**
	 * @param is_tax1_included_in_gst the is_tax1_included_in_gst to set
	 */
	public void setIs_tax1_included_in_gst(Integer is_tax1_included_in_gst) {
		this.is_tax1_included_in_gst = is_tax1_included_in_gst;
	}

	/**
	 * @param is_tax2_included_in_gst the is_tax2_included_in_gst to set
	 */
	public void setIs_tax2_included_in_gst(Integer is_tax2_included_in_gst) {
		this.is_tax2_included_in_gst = is_tax2_included_in_gst;
	}

	/**
	 * @param is_tax3_included_in_gst the is_tax3_included_in_gst to set
	 */
	public void setIs_tax3_included_in_gst(Integer is_tax3_included_in_gst) {
		this.is_tax3_included_in_gst = is_tax3_included_in_gst;
	}

	/**
	 * @param is_sc_included_in_gst the is_sc_included_in_gst to set
	 */
	public void setIs_sc_included_in_gst(Integer is_sc_included_in_gst) {
		this.is_sc_included_in_gst = is_sc_included_in_gst;
	}

}
