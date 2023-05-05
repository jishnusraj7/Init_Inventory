package com.indocosmo.mrp.web.core.base.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;


/**
 * @author jojesh-13.2
 *
 * @param <T>
 */
/**
 * @author anjutv
 *
 * @param <T>
 */
public interface IGeneralDao <T extends GeneralModelBase> extends IBaseDao<T>{
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<T> getList() throws Exception;
	/**
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public List<T> getList(String where) throws Exception;
	/**
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public T get(String where) throws Exception;
	
	/**
	 * @param item
	 * @throws Exception
	 */
	public void save(T item) throws Exception;
	
	/**
	 * @param item
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
	public void insert(T item) throws Exception;
	/**
	 * @param itemList
	 * @throws Exception
	 */
	public void insert(List<T> itemList) throws Exception;
	
	/**
	 * @param item
	 * @throws Exception
	 */
	public void update(T item)throws Exception;
	/**
	 * @param itemList
	 * @throws Exception
	 */
	public void update(List<T> itemList)throws Exception;
	
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
	public Integer delete(String  where) throws Exception;
	/**
	 * @param item
	 * @throws Exception
	 */
	public void delete(List<T> item) throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getMastersRowJson() throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getHqTableRowJson() throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getUpdatedHqTableRowJson() throws Exception;

	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<T> getHqTableRowListToImport() throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<T> getUpdatedHqTableRowListToImport() throws Exception;
	
	/**
	 * @return
	 */
	public Integer dataTableTotalRecord();
	
	/**
	 * @param datatableParameters
	 * @param tableFields
	 * @return
	 * @throws Exception
	 */
	public JsonArray getDataTableList(DataTableCriterias datatableParameters,List<String> tableFields) throws Exception;
	
	/**
	 * @param id
	 * @return
	 */
	public Object getCustomId(Integer counter);
	

}
