package com.indocosmo.mrp.web.masters.datatables;

public class DataTableColumns {
    private String data;
    private String name;
    private Boolean searchable;
    private Boolean orderable;
    private String searchValue;
    private Boolean searchRegex;
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
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
	 * @return the searchable
	 */
	public Boolean getSearchable() {
		return searchable;
	}
	/**
	 * @param searchable the searchable to set
	 */
	public void setSearchable(Boolean searchable) {
		this.searchable = searchable;
	}
	/**
	 * @return the orderable
	 */
	public Boolean getOrderable() {
		return orderable;
	}
	/**
	 * @param orderable the orderable to set
	 */
	public void setOrderable(Boolean orderable) {
		this.orderable = orderable;
	}
	/**
	 * @return the searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}
	/**
	 * @param searchValue the searchValue to set
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	/**
	 * @return the searchRegex
	 */
	public Boolean getSearchRegex() {
		return searchRegex;
	}
	/**
	 * @param searchRegex the searchRegex to set
	 */
	public void setSearchRegex(Boolean searchRegex) {
		this.searchRegex = searchRegex;
	}
    
}
