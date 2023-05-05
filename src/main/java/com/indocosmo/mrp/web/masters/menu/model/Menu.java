package com.indocosmo.mrp.web.masters.menu.model;

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
@Table(name = "menus")
public class Menu extends MasterModelBase{
	@Override
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="menu", key="menu")
	public void setId(Integer id) {
	
		super.setId(id);
		
		
	}
	@Transient
	private String deptName;
	
	
	/**
	 * @return the deptName
	 */
	public String getDeptName() {
	
		return deptName;
	}


	
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
	
		this.deptName = deptName;
	}
	@Transient
	private List<Menu> menuExcelData;
	
	
	/**
	 * @return the menuExcelData
	 */
	public List<Menu> getMenuExcelData() {
	
		return menuExcelData;
	}

	
	/**
	 * @param menuExcelData the menuExcelData to set
	 */
	public void setMenuExcelData(List<Menu> menuExcelData) {
	
		this.menuExcelData = menuExcelData;
	}

	@Column(name = "is_default_menu")
	private Integer is_default_menu;
	
	/**
	 * @return the is_default_menu
	 */
	public Integer getIs_default_menu() {
		return is_default_menu;
	}

	/**
	 * @return the enable_h1_button
	 */
	public Integer getEnable_h1_button() {
		return enable_h1_button;
	}

	/**
	 * @return the enable_h2_button
	 */
	public Integer getEnable_h2_button() {
		return enable_h2_button;
	}

	/**
	 * @return the enable_h3_button
	 */
	public Integer getEnable_h3_button() {
		return enable_h3_button;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @return the is_active
	 */
	public Integer getIs_active() {
		return is_active;
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
	 * @param is_default_menu the is_default_menu to set
	 */
	public void setIs_default_menu(Integer is_default_menu) {
		this.is_default_menu = is_default_menu;
	}

	/**
	 * @param enable_h1_button the enable_h1_button to set
	 */
	public void setEnable_h1_button(Integer enable_h1_button) {
		this.enable_h1_button = enable_h1_button;
	}

	/**
	 * @param enable_h2_button the enable_h2_button to set
	 */
	public void setEnable_h2_button(Integer enable_h2_button) {
		this.enable_h2_button = enable_h2_button;
	}

	/**
	 * @param enable_h3_button the enable_h3_button to set
	 */
	public void setEnable_h3_button(Integer enable_h3_button) {
		this.enable_h3_button = enable_h3_button;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @param is_active the is_active to set
	 */
	public void setIs_active(Integer is_active) {
		this.is_active = is_active;
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

	@Column(name = "enable_h1_button")
	private Integer enable_h1_button;
	
	
	@Column(name = "enable_h2_button")
	private Integer enable_h2_button;
	
	
	
	@Transient
	private Integer[] depList;
	

	/**
	 * @return the depList
	 */
	public Integer[] getDepList() {
		return depList;
	}

	/**
	 * @param depList the depList to set
	 */
	public void setDepList(Integer[] depList) {
		this.depList = depList;
	}

	@Column(name = "enable_h3_button")
	private Integer enable_h3_button;
	
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "is_active")
	private Integer is_active;
	
	
	@Column(name = "created_by")
	private Integer created_by = 0;

	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "updated_at")
	private String updated_at;

	 
}
