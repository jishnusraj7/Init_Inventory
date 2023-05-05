package com.indocosmo.mrp.web.core.base.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;

/**
 * Base service class for all master services
 * @author jojesh-13.2
 *
 * @param <T>
 */
public abstract class GeneralService<T extends GeneralModelBase, D extends GeneralDao<T>> extends BaseService<T,D>  implements IGeneralService<T,D>{

	
	/**
	 * @param context
	 */
	public GeneralService(ApplicationContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.service.IMasterBaseService#save(java.lang.Object)
	 */
	public void save(T item) throws Exception {
		
		GeneralDao<T> dao=getDao();
		dao.save(item);
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.service.IMasterBaseService#save(java.util.List)
	 */
	public void save(List<T> itemList) throws Exception {
	
		GeneralDao<T> dao=getDao();
		dao.save(itemList);
				
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.service.IMasterBaseService#save(java.util.List)
	 */
	public void saveHqData(List<T> itemList) throws Exception {
	
		GeneralDao<T> dao=getDao();
		dao.saveHqData(itemList);
				
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.service.IMasterBaseService#delete(java.lang.Object)
	 */
	public void delete(T item) throws Exception {

		getDao().delete(item);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.service.IMasterBaseService#delete(java.util.List)
	 */
	public void delete(List<T> itemList) throws Exception {
		
		getDao().delete(itemList);
		
	}
	
	public Integer delete(String where) throws Exception {
		
	 return	getDao().delete(where);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.service.IMasterBaseService#getList()
	 */
	public List<T> getList() throws Exception {

		return getDao().getList();
	}

	@Override
	public List<T> getList(String cond) throws Exception {

		return getDao().getList(cond);
	}

	/**
	 * @return
	 * @throws Exception 
	 */
	public JsonArray getMastersRowJson() throws Exception {
		
		return getDao().getMastersRowJson();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {

		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.service.IGeneralService#beginTrans()
	 */
	@Override
	public void beginTrans() throws Exception {
		
		getDao().beginTrans();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.service.IGeneralService#endTrans()
	 */
	@Override
	public void endTrans() throws Exception {
		
		getDao().endTrans();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.service.IGeneralService#endTrans()
	 */
	@Override
	public void rollbackTrans() throws Exception {
		
		getDao().rollbackTrans();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.service.IGeneralService#beginTrans()
	 */
	@Override
	public void beginTrans(String startPoint) throws Exception {
		
		getDao().beginTrans(startPoint);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.service.IGeneralService#endTrans()
	 */
	@Override
	public void endTrans(String startPoint) throws Exception {
		
		getDao().endTrans(startPoint);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.service.IGeneralService#endTrans()
	 */
	@Override
	public void rollbackTrans(String startPoint) throws Exception {
		
		getDao().rollbackTrans(startPoint);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IGeneralService#getDataTable(com.indocosmo.mrp.web.masters.datatables.DataTableCriterias, java.util.List)
	 */
	@Override
	public JsonArray getDataTable(DataTableCriterias datatableParameters,List<String> tableFields) throws Exception {
		
			return getDao().getDataTableList(datatableParameters,tableFields);
	}

	/**
	 * @return
	 */
	public long getTableTotalRecord() {
		
		return getDao().dataTableTotalRecord();
	}
	
	/**
	 * @param item
	 * @return
	 * @throws Exception 
	 */
	public Integer isCodeExist(String code) throws Exception {
		
		return getDao().isCodeExist(code);
	}
	
	/**
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public Double getItemLastCostPrice(Integer itemId) throws Exception{
		
		return getDao().getItemLastCostPrice(itemId);
	}
	
	/**
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public Double getItemAverageCostPrice(Integer itemId) throws Exception{
		
		return getDao().getItemAverageCostPrice(itemId);
	}
	
	
}
