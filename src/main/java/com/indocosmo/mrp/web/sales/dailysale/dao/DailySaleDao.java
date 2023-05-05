package com.indocosmo.mrp.web.sales.dailysale.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.sales.dailysale.model.DailySale;



public class DailySaleDao extends MasterBaseDao<DailySale> implements IDailySaleDao {
	
	public DailySaleDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
	String Sql="Select * from "+getTable()+" WHERE (is_deleted=0 OR is_deleted=NULL) ";
	return getTableRowsAsJson(Sql);
	}
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public DailySale getNewModelInstance() {
		
		return new DailySale();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {
		
		return "uoms";
	}

}
