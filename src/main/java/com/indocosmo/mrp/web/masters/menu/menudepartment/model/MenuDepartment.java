package com.indocosmo.mrp.web.masters.menu.menudepartment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "menu_departments")
public class MenuDepartment extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="menu_department", key="menu_department")
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the menu_id
	 */
	public Integer getMenu_id() {
		return menu_id;
	}

	/**
	 * @return the department_id
	 */
	public Integer getDepartment_id() {
		return department_id;
	}

	/**
	 * @param menu_id the menu_id to set
	 */
	public void setMenu_id(Integer menu_id) {
		this.menu_id = menu_id;
	}

	/**
	 * @param department_id the department_id to set
	 */
	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}


	@Column(name = "menu_id")
	private Integer menu_id;
	
	@Column(name = "department_id")
	private Integer department_id;
	
	
	

}
