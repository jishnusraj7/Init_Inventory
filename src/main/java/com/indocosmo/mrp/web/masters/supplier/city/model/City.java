package com.indocosmo.mrp.web.masters.supplier.city.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "cities")
public class City extends GeneralModelBase{

	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;



	@Column(name = "state_id")
	private Integer state_id;
	

	@Column(name = "is_active")
	private Integer is_active;
	
	
	/**
	 * @return the is_active
	 */
	public Integer getIs_active() {
		return is_active;
	}





	/**
	 * @param is_active the is_active to set
	 */
	public void setIs_active(Integer is_active) {
		this.is_active = is_active;
	}





	/**
	 * @return the state_id
	 */
	public Integer getState_id() {
		return state_id;
	}





	/**
	 * @param state_id the state_id to set
	 */
	public void setState_id(Integer state_id) {
		this.state_id = state_id;
	}





	@Column(name = "name")
	private String name;

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
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}	
	
	
  
}
