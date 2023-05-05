package com.indocosmo.mrp.web.masters.saleitemcombocontents.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "sale_item_combo_contents")
public class SaleItemComboContents extends GeneralModelBase{

	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "combo_sale_item_id")
	private Integer combo_sale_item_id;
	
	
	private String salecomboListItem;
	
	
	
	
	public String getSalecomboListItem() {
	
		return salecomboListItem;
	}


	
	public void setSalecomboListItem(String salecomboListItem) {
	
		this.salecomboListItem = salecomboListItem;
	}

	@Column(name = "combo_content_item_id")
	private Integer combo_content_item_id;

	
	@Column(name = "max_items")
	private Double max_items;

	
	@Column(name = "created_by")
	private Integer created_by;

	
	@Column(name = "created_at")
	private String created_at;

	
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

	
	public Integer getCombo_content_item_id() {
	
		return combo_content_item_id;
	}

	
	public Integer getCombo_sale_item_id() {
	
		return combo_sale_item_id;
	}

	
	public String getCreated_at() {
	
		return created_at;
	}

	
	public Integer getCreated_by() {
	
		return created_by;
	}

	
	public Integer getId() {
	
		return id;
	}

	
	public Integer getIs_deleted() {
	
		return is_deleted;
	}

	
	public Integer getIs_synchable() {
	
		return is_synchable;
	}

	
	public Double getMax_items() {
	
		return max_items;
	}

	
	public Integer getPublish_status() {
	
		return publish_status;
	}

	
	public String getUpdated_at() {
	
		return updated_at;
	}

	
	public Integer getUpdated_by() {
	
		return updated_by;
	}

	
	public void setCombo_content_item_id(Integer combo_content_item_id) {
	
		this.combo_content_item_id = combo_content_item_id;
	}

	
	public void setCombo_sale_item_id(Integer combo_sale_item_id) {
	
		this.combo_sale_item_id = combo_sale_item_id;
	}

	public void setCreated_at(String created_at) {
	
		this.created_at = created_at;
	}
	
	public void setCreated_by(Integer created_by) {
	
		this.created_by = created_by;
	}
	
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="sale_combo_contents", key="sale_combo_contents")
	public void setId(Integer id) {
	
		this.id = id;
		
		
	}
	
	

	public void setIs_deleted(Integer is_deleted) {
	
		this.is_deleted = is_deleted;
	}
	
	
	public void setIs_synchable(Integer is_synchable) {
	
		this.is_synchable = is_synchable;
	}
	
	public void setMax_items(Double max_items) {
	
		this.max_items = max_items;
	}
	
	public void setPublish_status(Integer publish_status) {
	
		this.publish_status = publish_status;
	}
	
	public void setUpdated_at(String updated_at) {
	
		this.updated_at = updated_at;
	}
	
	public void setUpdated_by(Integer updated_by) {
	
		this.updated_by = updated_by;
	}
	
	
	
	
	

}
