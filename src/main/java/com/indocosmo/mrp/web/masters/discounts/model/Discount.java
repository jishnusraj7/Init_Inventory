package com.indocosmo.mrp.web.masters.discounts.model;


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
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.model.SaleItemDiscount;
import com.indocosmo.mrp.web.masters.reasons.model.Reasons;


@Entity
@Table(name="discounts")
public class Discount extends GeneralModelBase{
	


	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;	
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "is_percentage")
	private Integer is_percentage;
	
	@Column(name = "is_overridable")
	private Integer is_overridable;
	
	@Column(name = "is_item_specific")
	private Integer is_item_specific;
	
	@Column(name = "permitted_for")
	private Integer permitted_for;
	
	@Column(name = "is_promotion")
	private Integer is_promotion;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "account_code")
	private String account_code;
	
	@Column(name = "grouping_quantity")
	private Double grouping_quantity;
	
	@Column(name = "allow_editing")
	private Integer allow_editing;
	
	@Column(name = "is_valid")
	private Integer is_valid;
	
	@Column(name = "date_from")
	private String date_from;
	
	@Column(name = "date_to")
	private String date_to;
	
	@Column(name = "time_from")
	private String time_from;
	
	@Column(name = "time_to")
	private String time_to;
	
	@Column(name = "week_days")
	private String week_days;
	
	@Column(name = "is_deleted")
	private Integer isDeleted=0;
	
	@Column(name="created_by")
	private Integer created_by;
	
	@Column(name="created_at")
	private String created_at;
	
	@Column(name="updated_by")
	private Integer update_by;
	
	@Column(name="updated_at")
	private String update_at;
	
	@Column(name="disc_password")
	private String disc_password=" ";
	
	@Transient
	private List<Discount> discountExcelData;
	
	@Transient
	private List<SaleItemDiscount> saleItemExcelData;
	

	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="discounts", key="discounts")
	public void setId(Integer id) {
	
		this.id = id;
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
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
	 * @param name the name to set
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
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the is_percentage
	 */
	public Integer getIs_percentage() {
		return is_percentage;
	}


	/**
	 * @param is_percentage the is_percentage to set
	 */
	public void setIs_percentage(Integer is_percentage) {
		this.is_percentage = is_percentage;
	}


	/**
	 * @return the is_overridable
	 */
	public Integer getIs_overridable() {
		return is_overridable;
	}


	/**
	 * @param is_overridable the is_overridable to set
	 */
	public void setIs_overridable(Integer is_overridable) {
		this.is_overridable = is_overridable;
	}


	/**
	 * @return the is_item_specific
	 */
	public Integer getIs_item_specific() {
		return is_item_specific;
	}


	/**
	 * @param is_item_specific the is_item_specific to set
	 */
	public void setIs_item_specific(Integer is_item_specific) {
		this.is_item_specific = is_item_specific;
	}


	/**
	 * @return the permitted_for
	 */
	public Integer getPermitted_for() {
		return permitted_for;
	}


	/**
	 * @param permitted_for the permitted_for to set
	 */
	public void setPermitted_for(Integer permitted_for) {
		this.permitted_for = permitted_for;
	}


	/**
	 * @return the is_promotion
	 */
	public Integer getIs_promotion() {
		return is_promotion;
	}


	/**
	 * @param is_promotion the is_promotion to set
	 */
	public void setIs_promotion(Integer is_promotion) {
		this.is_promotion = is_promotion;
	}


	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}


	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}


	/**
	 * @return the account_code
	 */
	public String getAccount_code() {
		return account_code;
	}


	/**
	 * @param account_code the account_code to set
	 */
	public void setAccount_code(String account_code) {
		this.account_code = account_code;
	}


	


	/**
	 * @return the allow_editing
	 */
	public Integer getAllow_editing() {
		return allow_editing;
	}


	/**
	 * @param allow_editing the allow_editing to set
	 */
	public void setAllow_editing(Integer allow_editing) {
		this.allow_editing = allow_editing;
	}


	/**
	 * @return the is_valid
	 */
	public Integer getIs_valid() {
		return is_valid;
	}


	/**
	 * @param is_valid the is_valid to set
	 */
	public void setIs_valid(Integer is_valid) {
		this.is_valid = is_valid;
	}


	/**
	 * @return the date_from
	 */
	public String getDate_from() {
		return date_from;
	}


	/**
	 * @param date_from the date_from to set
	 */
	public void setDate_from(String date_from) {
		this.date_from = date_from;
	}


	/**
	 * @return the date_to
	 */
	public String getDate_to() {
		return date_to;
	}


	/**
	 * @param date_to the date_to to set
	 */
	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}


	/**
	 * @return the time_from
	 */
	public String getTime_from() {
		return time_from;
	}


	/**
	 * @param time_from the time_from to set
	 */
	public void setTime_from(String time_from) {
		this.time_from = time_from;
	}


	/**
	 * @return the time_to
	 */
	public String getTime_to() {
		return time_to;
	}


	/**
	 * @param time_to the time_to to set
	 */
	public void setTime_to(String time_to) {
		this.time_to = time_to;
	}


	/**
	 * @return the week_days
	 */
	public String getWeek_days() {
		return week_days;
	}


	/**
	 * @param week_days the week_days to set
	 */
	public void setWeek_days(String week_days) {
		this.week_days = week_days;
	}


	/**
	 * @return the isDeleted
	 */
	public Integer getIsDeleted() {
		return isDeleted;
	}


	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}


	/**
	 * @return the created_by
	 */
	public Integer getCreated_by() {
		return created_by;
	}


	/**
	 * @param created_by the created_by to set
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
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}


	/**
	 * @return the update_by
	 */
	public Integer getUpdate_by() {
		return update_by;
	}


	/**
	 * @param update_by the update_by to set
	 */
	public void setUpdate_by(Integer update_by) {
		this.update_by = update_by;
	}


	/**
	 * @return the update_at
	 */
	public String getUpdate_at() {
		return update_at;
	}


	/**
	 * @param update_at the update_at to set
	 */
	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @return the disc_password
	 */
	public String getDisc_password() {
		return disc_password;
	}


	/**
	 * @param disc_password the disc_password to set
	 */
	public void setDisc_password(String disc_password) {
		this.disc_password = disc_password;
	}


	/**
	 * @return the discountExcelData
	 */
	public List<Discount> getDiscountExcelData() {
		return discountExcelData;
	}


	/**
	 * @param discountExcelData the discountExcelData to set
	 */
	public void setDiscountExcelData(List<Discount> discountExcelData) {
		this.discountExcelData = discountExcelData;
	}


	/**
	 * @return the grouping_quantity
	 */
	public Double getGrouping_quantity() {
		return grouping_quantity;
	}


	/**
	 * @param grouping_quantity the grouping_quantity to set
	 */
	public void setGrouping_quantity(Double grouping_quantity) {
		this.grouping_quantity = grouping_quantity;
	}


	/**
	 * @return the saleItemExcelData
	 */
	public List<SaleItemDiscount> getSaleItemExcelData() {
		return saleItemExcelData;
	}


	/**
	 * @param saleItemExcelData the saleItemExcelData to set
	 */
	public void setSaleItemExcelData(List<SaleItemDiscount> saleItemExcelData) {
		this.saleItemExcelData = saleItemExcelData;
	}

	
	
	

	
	
	
	
}
