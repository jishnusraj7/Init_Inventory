package com.indocosmo.mrp.web.masters.customertypes.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.MasterModelBase;

@Entity
@Table(name = "customer_types")
public class CustomerType extends MasterModelBase {
	
	@Column(name = "default_price_variance_pc")
	private Double default_price_variance_pc;
	
	@Column(name = "created_by")
	private Integer created_by;
	
	@Column(name = "created_at")
	private String created_at;
	
	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "updated_at")
	private String updated_at;
	
	@Transient
	private List<CustomerType> custExcelData;
	
	@Override
	@Id(generationType = GenerationType.COUNTER)
	@Counter(module = "customer_types", key = "customer_types")
	public void setId(Integer id) {
	
		super.setId(id);
	}
	
	/**
	 * @return the default_price_variance_pc
	 */
	public Double getDefault_price_variance_pc() {
	
		return default_price_variance_pc;
	}
	
	/**
	 * @param default_price_variance_pc
	 *            the default_price_variance_pc to set
	 */
	public void setDefault_price_variance_pc(Double default_price_variance_pc) {
	
		this.default_price_variance_pc = default_price_variance_pc;
	}
	
	/**
	 * @return the created_by
	 */
	public Integer getCreated_by() {
	
		return created_by;
	}
	
	/**
	 * @param created_by
	 *            the created_by to set
	 */
	public void setCreated_by(Integer created_by) {
	
		this.created_by = created_by;
	}
	
	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
	
		return created_at;
	}
	
	/**
	 * @param created_at
	 *            the created_at to set
	 */
	public void setCreated_at(String created_at) {
	
		this.created_at = created_at;
	}
	
	/**
	 * @return the updated_by
	 */
	public Integer getUpdated_by() {
	
		return updated_by;
	}
	
	/**
	 * @param updated_by
	 *            the updated_by to set
	 */
	public void setUpdated_by(Integer updated_by) {
	
		this.updated_by = updated_by;
	}
	
	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
	
		return updated_at;
	}
	
	/**
	 * @param updated_at
	 *            the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
	
		this.updated_at = updated_at;
	}
	
	/**
	 * @return the custExcelData
	 */
	public List<CustomerType> getCustExcelData() {
	
		return custExcelData;
	}
	
	/**
	 * @param custExcelData
	 *            the custExcelData to set
	 */
	public void setCustExcelData(List<CustomerType> custExcelData) {
	
		this.custExcelData = custExcelData;
	}
	
}
