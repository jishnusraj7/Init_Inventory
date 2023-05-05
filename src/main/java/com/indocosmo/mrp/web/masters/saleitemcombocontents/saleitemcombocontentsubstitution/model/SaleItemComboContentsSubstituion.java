package com.indocosmo.mrp.web.masters.saleitemcombocontents.saleitemcombocontentsubstitution.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "sale_item_combo_substitutions")
public class SaleItemComboContentsSubstituion extends GeneralModelBase{
	
	
	
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	
	public Integer getId() {
		return id;
	}

	
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="sale_item_combo_contents_subtitution", key="sale_item_combo_contents_subtitution")
	public void setId(Integer id) {
	
		this.id = id;
		
		
	}
	
	
	@Column(name = "sale_item_combo_content_id")
	private Integer sale_item_combo_content_id;

	
	
	public Integer getSale_item_combo_content_id() {
	
		return sale_item_combo_content_id;
	}


	
	public void setSale_item_combo_content_id(Integer sale_item_combo_content_id) {
	
		this.sale_item_combo_content_id = sale_item_combo_content_id;
	}


	
	public Integer getSubstitution_sale_item_id() {
	
		return substitution_sale_item_id;
	}


	
	public void setSubstitution_sale_item_id(Integer substitution_sale_item_id) {
	
		this.substitution_sale_item_id = substitution_sale_item_id;
	}


	
	public Double getQuantity() {
	
		return quantity;
	}


	
	public void setQuantity(Double quantity) {
	
		this.quantity = quantity;
	}


	
	public Double getPrice_difference() {
	
		return price_difference;
	}


	
	public void setPrice_difference(Double price_difference) {
	
		this.price_difference = price_difference;
	}


	
	public Integer getIs_default() {
	
		return is_default;
	}


	
	public void setIs_default(Integer is_default) {
	
		this.is_default = is_default;
	}


	
	public Integer getCreated_by() {
	
		return created_by;
	}


	
	public void setCreated_by(Integer created_by) {
	
		this.created_by = created_by;
	}


	
	public Integer getCreated_at() {
	
		return created_at;
	}


	
	public void setCreated_at(Integer created_at) {
	
		this.created_at = created_at;
	}


	
	public Integer getUpdated_by() {
	
		return updated_by;
	}


	
	public void setUpdated_by(Integer updated_by) {
	
		this.updated_by = updated_by;
	}


	
	public String getUpdated_at() {
	
		return updated_at;
	}


	
	public void setUpdated_at(String updated_at) {
	
		this.updated_at = updated_at;
	}


	
	public Integer getPublish_status() {
	
		return publish_status;
	}


	
	public void setPublish_status(Integer publish_status) {
	
		this.publish_status = publish_status;
	}


	
	public Integer getIs_deleted() {
	
		return is_deleted;
	}


	
	public void setIs_deleted(Integer is_deleted) {
	
		this.is_deleted = is_deleted;
	}


	
	public Integer getIs_synchable() {
	
		return is_synchable;
	}


	
	public void setIs_synchable(Integer is_synchable) {
	
		this.is_synchable = is_synchable;
	}


	@Column(name = "substitution_sale_item_id")
	private Integer substitution_sale_item_id;
	
	
	@Column(name = "quantity")
	private Double quantity;
	
	
	@Column(name = "price_difference")
	private Double price_difference;
	
	@Column(name = "is_default")
	private Integer is_default;
	
	@Column(name = "created_by")
	private Integer created_by;
	
	
	@Column(name = "created_at")
	private Integer created_at;
	
	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "updated_at")
	private String updated_at;
	
	
	@Column(name = "publish_status")
	private Integer publish_status=1;
	
	
	@Column(name = "is_deleted")
	private Integer is_deleted=0;
	
	
	@Column(name = "is_synchable")
	private Integer is_synchable=1;
	
	

	

}
