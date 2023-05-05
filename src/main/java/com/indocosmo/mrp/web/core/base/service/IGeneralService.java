package com.indocosmo.mrp.web.core.base.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;

public interface IGeneralService<T extends GeneralModelBase,D extends GeneralDao<T>> extends IBaseService<T,D>{
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<T> getList() throws Exception;
	/**
	 * @param cond
	 * @return
	 * @throws Exception
	 */
	public List<T> getList(String cond) throws Exception;
	/**
	 * @param item
	 * @throws Exception
	 */
	public void save(T item) throws Exception;
	/**
	 * @param itemList
	 * @throws Exception
	 */
	public void save(List<T> itemList) throws Exception;
	/**
	 * @param itemList
	 * @throws Exception
	 */
	public void saveHqData(List<T> itemList) throws Exception; 
	/**
	 * @param item
	 * @throws Exception
	 */
	public void delete(T item) throws Exception;
	/**
	 * @param item
	 * @return 
	 * @throws Exception
	 */
	public Integer delete(String cond) throws Exception;
	
	/**
	 * @param item
	 * @throws Exception
	 */
	public void delete(List<T> itemList) throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getJsonArray() throws Exception;
	
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
	 * @param transactionPoint
	 * @throws Exception
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
	
	/**
	 * @param datatableParameters
	 * @param tableFields
	 * @return
	 * @throws Exception
	 */
	public JsonArray getDataTable(DataTableCriterias datatableParameters,List<String> tableFields) throws Exception;
	
	
	
}
