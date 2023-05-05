package com.indocosmo.mrp.web.masters.saleitemchoices.model;

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
@Table(name = "sale_item_choices")
public class SaleItemChoices extends GeneralModelBase{

	
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	
	
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="sale_item_choices", key="sale_item_choices")
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Transient
	private String saleItemName;
	
	@Transient
	private String choiceName;
	
	
	/**
	 * @return the saleItemName
	 */
	public String getSaleItemName() {
	
		return saleItemName;
	}


	
	/**
	 * @return the choiceName
	 */
	public String getChoiceName() {
	
		return choiceName;
	}


	
	/**
	 * @param saleItemName the saleItemName to set
	 */
	public void setSaleItemName(String saleItemName) {
	
		this.saleItemName = saleItemName;
	}


	
	/**
	 * @param choiceName the choiceName to set
	 */
	public void setChoiceName(String choiceName) {
	
		this.choiceName = choiceName;
	}

	@Column(name = "is_deleted")
	private Integer is_delete=0;
	
	
	@Column(name = "sale_item_id")
	private Integer sale_item_id;
	
	@Column(name = "choice_id")
	private Integer choice_id;
	
	
	@Column(name = "free_items")
	private Integer free_items;
	
	@Column(name = "created_by")
	private Integer created_by = 0;

	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "updated_at")
	private String updated_at;
	
	@Column(name = "max_items")
	private Integer max_items;

	@Transient
	private List<SaleItemChoices> saleItemChoiceExcelData;

	
	/**
	 * @return the saleItemChoiceExcelData
	 */
	public List<SaleItemChoices> getSaleItemChoiceExcelData() {
	
		return saleItemChoiceExcelData;
	}

	
	/**
	 * @param saleItemChoiceExcelData the saleItemChoiceExcelData to set
	 */
	public void setSaleItemChoiceExcelData(List<SaleItemChoices> saleItemChoiceExcelData) {
	
		this.saleItemChoiceExcelData = saleItemChoiceExcelData;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the is_delete
	 */
	public Integer getIs_delete() {
		return is_delete;
	}

	/**
	 * @return the sale_item_id
	 */
	public Integer getSale_item_id() {
		return sale_item_id;
	}

	/**
	 * @return the choice_id
	 */
	public Integer getChoice_id() {
		return choice_id;
	}

	/**
	 * @return the free_items
	 */
	public Integer getFree_items() {
		return free_items;
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
	 * @return the max_items
	 */
	public Integer getMax_items() {
		return max_items;
	}

	/**
	 * @param is_delete the is_delete to set
	 */
	public void setIs_delete(Integer is_delete) {
		this.is_delete = is_delete;
	}

	/**
	 * @param sale_item_id the sale_item_id to set
	 */
	public void setSale_item_id(Integer sale_item_id) {
		this.sale_item_id = sale_item_id;
	}

	/**
	 * @param choice_id the choice_id to set
	 */
	public void setChoice_id(Integer choice_id) {
		this.choice_id = choice_id;
	}

	/**
	 * @param free_items the free_items to set
	 */
	public void setFree_items(Integer free_items) {
		this.free_items = free_items;
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

	/**
	 * @param max_items the max_items to set
	 */
	public void setMax_items(Integer max_items) {
		this.max_items = max_items;
	}
	
	
}
