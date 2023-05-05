package com.indocosmo.mrp.web.core.base.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.BaseDao;
import com.indocosmo.mrp.web.core.base.model.ModelBase;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;

/**
 * Base class for all services
 * @author jojesh-13.2
 *
 */
public abstract class BaseService<T extends ModelBase, D extends BaseDao<T>> implements IBaseService<T,D>{
	
	private ApplicationContext context;
	
	/**
	 * @return
	 */
	public ApplicationContext getContext() {
		
		return context;
	}

	/**
	 * @param context
	 */
	public BaseService(ApplicationContext context){
		
		this.context=context;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getJsonArray()
	 */
	public JsonArray getJsonArray() throws Exception {

		return getDao().getTableRowsAsJson();
	}
	
	public JsonArray getDropdownArray() throws Exception {

		return getDao().getDropdownArray();
	}
	
	

	public JsonObject getTableJsonArray(DataTableCriterias datatableParameters) throws Exception{
		return getDao().getDataTableRowsAsJson(datatableParameters);
	}
	
	
	public JsonObject getExportData() throws Exception{
		return getDao().getExportColoumnName();
	}
	
	public JsonArray getExcelJsonListData(String ColoumnList,String OrdeList) throws Exception{
		return getDao().getExcelJsonListData(ColoumnList,OrdeList);
	}
	
}
