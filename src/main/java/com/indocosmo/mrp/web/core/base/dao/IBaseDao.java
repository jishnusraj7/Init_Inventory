package com.indocosmo.mrp.web.core.base.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.core.base.model.ModelBase;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;

/**
 * Interface for Baase DAO
 * @author jojesh-13.2
 *
 */
public interface IBaseDao<T extends ModelBase> {
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getTableRowsAsJson() throws Exception;
	public JsonArray getDropdownArray() throws Exception;
	
	public JsonObject getDataTableRowsAsJson(DataTableCriterias datatableParameters) throws Exception;
	/**
	 * @throws Exception
	 */
	public void beginTrans() throws Exception;
	
	
	/**
	 * @throws Exception
	 */
	public void endTrans() throws Exception;
	
	/**
	 * @throws Exception
	 */
	public void rollbackTrans() throws Exception;
	
	/**
	 * 
	 */
	public void beginTrans(String transactionPoint) throws Exception;
	
	/**
	 * @throws Exception
	 */
	public void endTrans(String transactionPoint) throws Exception;
	
	/**
	 * @throws Exception
	 */
	public void rollbackTrans(String transactionPoint) throws Exception;
	
	
}
