package com.indocosmo.mrp.web.masters.combocontents.ComboContentSubstitution.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "combo_content_substitutions")
public class ComboContentsSubstituion extends GeneralModelBase{
	
	
	
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	
	public Integer getId() {
		return id;
	}

	
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="combo_contents_subtitution", key="combo_contents_subtitution")
	public void setId(Integer id) {
	
		this.id = id;
		
		
	}
	
	
	
	
	
	
	

	
	
	public Integer getCombo_content_id() {
	
		return combo_content_id;
	}

	
	public void setCombo_content_id(Integer combo_content_id) {
	
		this.combo_content_id = combo_content_id;
	}

	
	public Integer getSubstitution_sale_item_id() {
	
		return substitution_sale_item_id;
	}

	
	public void setSubstitution_sale_item_id(Integer substitution_sale_item_id) {
	
		this.substitution_sale_item_id = substitution_sale_item_id;
	}

	
	public Double getPrice_diff() {
	
		return price_diff;
	}

	
	public void setPrice_diff(Double price_diff) {
	
		this.price_diff = price_diff;
	}

	
	public Double getQty() {
	
		return qty;
	}

	
	public void setQty(Double qty) {
	
		this.qty = qty;
	}

	
	public Integer getIs_default() {
	
		return is_default;
	}

	
	public void setIs_default(Integer is_default) {
	
		this.is_default = is_default;
	}









	@Column(name = "combo_content_id")
	private Integer combo_content_id;
	
	@Column(name = "substitution_sale_item_id")
	private Integer substitution_sale_item_id;
	
	
	@Column(name = "price_diff")
	private Double price_diff;
	
	@Column(name = "qty")
	private Double qty;
	
	@Column(name = "is_default")
	private Integer is_default;
	
	@Column(name = "created_by")
	private Integer created_by = 0;

	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "updated_at")
	private String updated_at;

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
}
