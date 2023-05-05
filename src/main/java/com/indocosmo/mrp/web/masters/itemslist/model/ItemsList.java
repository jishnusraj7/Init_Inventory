package com.indocosmo.mrp.web.masters.itemslist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;


@Entity
@Table(name="mrp_stock_item")
public class ItemsList extends GeneralModelBase {
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name="code")
	private String code;

	@Column(name="name")
	private String name;
	
	@Column(name="item_category_id")
	private Integer item_category_id;

	@Column(name="is_active")
	private Integer is_active;

	@Column(name="is_synchable")
	private Integer is_synchable;

	public String getCode() {
		return code;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIs_active() {
		return is_active;
	}

	public Integer getIs_synchable() {
		return is_synchable;
	}

	public Integer getItem_category_id() {
		return item_category_id;
	}

	public String getName() {
		return name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIs_active(Integer is_active) {
		this.is_active = is_active;
	}
	
	public void setIs_synchable(Integer is_synchable) {
		this.is_synchable = is_synchable;
	}
	
	public void setItem_category_id(Integer item_category_id) {
		this.item_category_id = item_category_id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

}
