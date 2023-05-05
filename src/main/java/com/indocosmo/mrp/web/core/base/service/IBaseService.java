package com.indocosmo.mrp.web.core.base.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.core.base.dao.BaseDao;
import com.indocosmo.mrp.web.core.base.model.ModelBase;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;

public interface IBaseService<T extends ModelBase, D extends BaseDao<T>> {
	

	/**
	 * @return
	 */
	public D getDao();
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getJsonArray() throws Exception ; 
	public JsonObject getTableJsonArray(DataTableCriterias datatableParameters) throws Exception ; 
	public JsonArray getDropdownArray() throws Exception;
}
