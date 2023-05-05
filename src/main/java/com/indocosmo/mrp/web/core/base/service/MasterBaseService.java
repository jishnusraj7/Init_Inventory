package com.indocosmo.mrp.web.core.base.service;

import java.util.ArrayList;
import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.core.base.model.MasterModelBase;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;

/**
 * Base service class for all master services
 * 
 * @author jojesh-13.2
 *
 * @param <T>
 */
public abstract class MasterBaseService<T extends MasterModelBase, D extends MasterBaseDao<T>> extends 	GeneralService<T,D> implements IMasterBaseService<T,D> {

	
	/**
	 * @param context
	 */
	public MasterBaseService(ApplicationContext context){
		super(context);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.posellaweb.web.common.base.service.IMasterBaseService#save
	 * (java.lang.Object)
	 */
	public void save(T item) throws Exception {

		GeneralDao<T> dao =  getDao();
		if (item.getId() == null || item.getId().equals(""))
			dao.insert(item);
		else
			dao.update(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.posellaweb.web.common.base.service.IMasterBaseService#save
	 * (java.util.List)
	 */
	public void save(List<T> itemList) throws Exception {

		ArrayList<T> iItems = new ArrayList<T>();
		ArrayList<T> uItems = new ArrayList<T>();

		GeneralDao<T> dao =  getDao();

		for (T item : itemList) {
			if (item.getId() == null || item.getId().equals(""))
				iItems.add(item);
			else
				uItems.add(item);
		}

		if (iItems.size() > 0)
			dao.insert(iItems);

		if (uItems.size() > 0)
			dao.update(uItems);

	}

	/* Build Query for Get Row Count
	 * (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.core.base.service.GeneralService#delete(java.lang.String)
	 */
	@Override
	public Integer delete(String id) throws Exception {
		
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		GeneralDao<T> dao =  getDao();
		
		int rowCount = 0;
		
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				
				final String wherePart = ""+table.getRefrenceKey()+"="+ id +" AND (is_system = 0 OR is_system IS NULL) AND (is_deleted = 0	OR is_deleted IS NULL);";
				rowCount = rowCount + dao.getReferenceRowCount(table.getRefrenceTable(),wherePart);
			}
		}
		
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			super.delete(where);
		}
		
		return is_deleted;
	}
	
}
