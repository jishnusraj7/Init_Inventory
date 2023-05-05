package com.indocosmo.mrp.web.masters.reasons.model;

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
@Table(name = "reasons")
public class Reasons extends GeneralModelBase {
	
	@Pk
	@Id(generationType = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "is_deleted")
	private Integer isDeleted = 0;
	
	@Column(name = "context")
	private Integer context;
	
	@Column(name = "created_by")
	private Integer created_by;
	
	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "created_at")
	private String created_at;
	
	@Column(name = "updated_at")
	private String updated_at;
	
	@Transient
	private List<Reasons> reasonExcelData;
	
	@Id(generationType = GenerationType.COUNTER)
	@Counter(module = "reasons", key = "reasons")
	public void setId(Integer id) {
	
		this.id = id;
	}
	
	/**
	 * @return the context
	 */
	public Integer getContext() {
	
		return context;
	}
	
	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(Integer context) {
	
		this.context = context;
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
	 * @return the code
	 */
	public String getCode() {
	
		return code;
	}
	
	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
	
		this.code = code;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
	
		return name;
	}
	
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
	
		this.name = name;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
	
		return description;
	}
	
	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
	
		this.description = description;
	}
	
	/**
	 * @return the isDeleted
	 */
	public Integer getIsDeleted() {
	
		return isDeleted;
	}
	
	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(Integer isDeleted) {
	
		this.isDeleted = isDeleted;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
	
		return id;
	}
	
	/**
	 * @return the reasonExcelData
	 */
	public List<Reasons> getReasonExcelData() {
	
		return reasonExcelData;
	}
	
	/**
	 * @param reasonExcelData
	 *            the reasonExcelData to set
	 */
	public void setReasonExcelData(List<Reasons> reasonExcelData) {
	
		this.reasonExcelData = reasonExcelData;
	}
	
}
