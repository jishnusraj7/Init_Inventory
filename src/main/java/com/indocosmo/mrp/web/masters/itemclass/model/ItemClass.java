package com.indocosmo.mrp.web.masters.itemclass.model;

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
@Table(name ="item_classes")
public class ItemClass extends MasterModelBase{
	
	
	@Column(name = "alternative_name")
	private String alternative_name;


	@Column(name = "department_id")
	private Integer departmentId;

	
	@Column(name = "hsn_code")
	private String hsn_code;

	@Column(name = "print_order")
	private Integer print_order;

	
	public String getHsn_code() {
	
		return hsn_code;
	}
	
	public void setHsn_code(String hsn_code) {
	
		this.hsn_code = hsn_code;
	}
	
	public Integer getPrint_order() {
	
		return print_order;
	}
	
	public void setPrint_order(Integer print_order) {
	
		this.print_order = print_order;
	}

	@Column(name = "menu_id")
	private Integer menu_id;

	@Column(name = "super_class_id")
	private Integer super_class_id;
	
	
	
	@Column(name = "display_order")
	private Integer display_order;


	@Column(name = "account_code")
	private String account_code;


	@Column(name = "fg_color")
	private String textColor;


	@Column(name = "bg_color")
	private String bgColor;


	@Column(name = "item_thumb")
	private String itemThumb;


	@Column(name = "created_by")
	private Integer created_by;


	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;


	@Column(name = "updated_at")
	private String updated_at;

	
	@Transient
	private List<ItemClass> itemClassExcelData;

	/**
	 * @return the itemClassExcelData
	 */
	public List<ItemClass> getItemClassExcelData() {
		return itemClassExcelData;
	}
	/**
	 * @param itemClassExcelData the itemClassExcelData to set
	 */
	public void setItemClassExcelData(List<ItemClass> itemClassExcelData) {
		this.itemClassExcelData = itemClassExcelData;
	}

	@Transient
	private Boolean is_subclass;

	@Transient
	private String superClassName;
	
	/**
	 * @return the superClassName
	 */
	public String getSuperClassName() {
		return superClassName;
	}
	/**
	 * @param superClassName the superClassName to set
	 */
	public void setSuperClassName(String superClassName) {
		this.superClassName = superClassName;
	}

	@Transient
	private String department_code;

	@Transient
	private String department_name;
	
	/**
	 * @return the account_code
	 */
	public String getAccount_code() {
		return account_code;
	}
	/**
	 * @return the alternative_name
	 */
	public String getAlternative_name() {
		return alternative_name;
	}
	/**
	 * @return the bgColor
	 */
	public String getBgColor() {
		return bgColor;
	}

	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}

	/**
	 * @return the created_by
	 */
	public Integer getCreated_by() {
		return created_by;
	}

	/**
	 * @return the department_code
	 */
	public String getDepartment_code() {
		return department_code;
	}

	
	/**
	 * @return the department_name
	 */
	public String getDepartment_name() {
		return department_name;
	}


	/**
	 * @return the departmentId
	 */
	public Integer getDepartmentId() {
		return departmentId;
	}


	/**
	 * @return the display_order
	 */
	public Integer getDisplay_order() {
		return display_order;
	}


	


	/**
	 * @return the is_subclass
	 */
	public Boolean getIs_subclass() {
		return is_subclass;
	}


	



	


	


	/**
	 * @return the menu_id
	 */
	public Integer getMenu_id() {
		return menu_id;
	}


	/**
	 * @return the super_class_id
	 */
	public Integer getSuper_class_id() {
		return super_class_id;
	}


	/**
	 * @return the textColor
	 */
	public String getTextColor() {
		return textColor;
	}


	


	/**
	 * @param account_code the account_code to set
	 */
	public void setAccount_code(String account_code) {
		this.account_code = account_code;
	}


	/**
	 * @param alternative_name the alternative_name to set
	 */
	public void setAlternative_name(String alternative_name) {
		this.alternative_name = alternative_name;
	}


	/**
	 * @param bgColor the bgColor to set
	 */
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
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
	 * @param department_code the department_code to set
	 */
	public void setDepartment_code(String department_code) {
		this.department_code = department_code;
	}
	
	/**
	 * @param department_name the department_name to set
	 */
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	
	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	/**
	 * @param display_order the display_order to set
	 */
	public void setDisplay_order(Integer display_order) {
		this.display_order = display_order;
	}
	
	

	@Override
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="item_classes", key="item_classes")
	public void setId(Integer id) {
	
		super.setId(id);
	}

	
	/**
	 * @param is_subclass the is_subclass to set
	 */
	public void setIs_subclass(Boolean is_subclass) {
		this.is_subclass = is_subclass;
	}

	/**
	 * @return the itemThumb
	 */
	public String getItemThumb() {
		return itemThumb;
	}
	/**
	 * @param itemThumb the itemThumb to set
	 */
	public void setItemThumb(String itemThumb) {
		this.itemThumb = itemThumb;
	}
	/**
	 * @param menu_id the menu_id to set
	 */
	public void setMenu_id(Integer menu_id) {
		this.menu_id = menu_id;
	}

	/**
	 * @param super_class_id the super_class_id to set
	 */
	public void setSuper_class_id(Integer super_class_id) {
		this.super_class_id = super_class_id;
	}

	/**
	 * @param textColor the textColor to set
	 */
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	/**
	 * @return the updated_by
	 */
	public Integer getUpdated_by() {
		return updated_by;
	}
	/**
	 * @param updated_by the updated_by to set
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
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	

}