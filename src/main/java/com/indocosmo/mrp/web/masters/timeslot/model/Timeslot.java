package com.indocosmo.mrp.web.masters.timeslot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.model.MasterModelBase;

@Entity
@Table(name = "mrp_time_slot")
public class Timeslot extends MasterModelBase{
	
	@Column(name = "start_time")
	private String start_time;
	
	
	@Column(name = "end_time")
	private String end_time;

	@Column(name = "created_by")
	private Integer created_by = 0;

	@Column(name = "created_at")
	private String created_at;

	@Column(name = "updated_by")
	private Integer updated_by;
	
	@Column(name = "updated_at")
	private String updated_at;
	
	
	@Override
	@Id(generationType=GenerationType.COUNTER)
	@Counter(module="mrp_time_slot", key="mrp_time_slot")
	public void setId(Integer id) {
	
		super.setId(id);
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
	 * @return the start_time
	 */
	public String getStart_time() {
	
		return start_time;
	}


	
	/**
	 * @return the end_time
	 */
	public String getEnd_time() {
	
		return end_time;
	}


	
	/**
	 * @param start_time the start_time to set
	 */
	public void setStart_time(String start_time) {
	
		this.start_time = start_time;
	}


	
	/**
	 * @param end_time the end_time to set
	 */
	public void setEnd_time(String end_time) {
	
		this.end_time = end_time;
	}
	

}
