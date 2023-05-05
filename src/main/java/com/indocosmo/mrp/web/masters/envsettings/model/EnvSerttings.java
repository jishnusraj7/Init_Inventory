package com.indocosmo.mrp.web.masters.envsettings.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;

@Entity
@Table(name = "env_settings")
public class EnvSerttings extends GeneralModelBase{
	
	@Pk
	@Id(generationType=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	 
	@Column(name = "is_publish")
	private Integer is_publish;
	
	
	/**
	 * @param id the id to set
	 */
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="env_settings", key="env_settings")
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
	 * @return the is_publish
	 */
	public Integer getIs_publish() {
		return is_publish;
	}


	/**
	 * @param is_publish the is_publish to set
	 */
	public void setIs_publish(Integer is_publish) {
		this.is_publish = is_publish;
	}

	
	

	
}
