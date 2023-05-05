package com.indocosmo.mrp.web.core.base.model;

import javax.persistence.Entity;

@Entity
public class RefereneceModelBase{

	private String refrenceTable;
	
	private String refrenceKey;

	/**
	 * @return the refrenceTable
	 */
	public String getRefrenceTable() {
		return refrenceTable;
	}

	/**
	 * @param refrenceTable the refrenceTable to set
	 */
	public void setRefrenceTable(String refrenceTable) {
		this.refrenceTable = refrenceTable;
	}

	/**
	 * @return the refrenceKey
	 */
	public String getRefrenceKey() {
		return refrenceKey;
	}

	/**
	 * @param refrenceKey the refrenceKey to set
	 */
	public void setRefrenceKey(String refrenceKey) {
		this.refrenceKey = refrenceKey;
	}

	
	
	
}
