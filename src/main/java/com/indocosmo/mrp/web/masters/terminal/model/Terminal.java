package com.indocosmo.mrp.web.masters.terminal.model;

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
@Table(name = "stations")
public class Terminal extends MasterModelBase {
	
	@Column(name = "created_at")
	private String created_at;
	
	@Column(name = "created_by")
	private Integer created_by;
	
	@Transient
	private List<Terminal> termExcelData;
	
	
	
	public List<Terminal> getTermExcelData() {
		return termExcelData;
	}

	public void setTermExcelData(List<Terminal> termExcelData) {
		this.termExcelData = termExcelData;
	}

	@Column(name = "is_synchable")
	private Integer is_synchable=1;
	@Column(name = "publish_status")
	private Integer publish_status=0;
	
	
	@Column(name = "type")
	private Integer type;

	@Column(name = "updated_at")
	private String updated_at;

	@Column(name = "updated_by")
	private Integer updated_by;

	public String getCreated_at() {
		return created_at;
	}

	public Integer getCreated_by() {
		return created_by;
	}


	
	@Override
	@Id(generationType = GenerationType.COUNTER)
	@Counter(module = "stations", key = "stations")
	public void setId(Integer id) {
		super.setId(id);
	}
	

	public Integer getIs_synchable() {
		return is_synchable;
	}

	public Integer getPublish_status() {
		return publish_status;
	}

	public Integer getType() {
		return type;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public Integer getUpdated_by() {
		return updated_by;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}



	public void setIs_synchable(Integer is_synchable) {
		this.is_synchable = is_synchable;
	}

	public void setPublish_status(Integer publish_status) {
		this.publish_status = publish_status;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	
	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}
	
	
	

	
}
