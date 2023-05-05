package com.indocosmo.mrp.web.masters.rounding.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.masters.rounding.model.Rounding;

/**
 * @author jo
 *
 */
public class RoundingDao  extends MasterBaseDao<Rounding> implements IRoundingDao{

	/**
	 * @param context
	 */
	public RoundingDao(ApplicationContext context) {

		super(context);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Rounding getNewModelInstance() {
		// TODO Auto-generated method stub
		return new Rounding();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {
		// TODO Auto-generated method stub
		return "rounding";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.rounding.dao.IRoundingDao#getEditDetails(int)
	 */
	public JsonArray getEditDetails(int id) throws Exception
	{
		final String sql="SELECT * FROM "+getTable()+" WHERE id="+id+" AND  is_deleted='0' ";

		return getTableRowsAsJson(sql);
	}
	@Override
   public JsonArray getMastersRowJson() throws Exception{
		
		final String sql="SELECT id,code,name from "+ getTable() + " WHERE IFNULL(is_deleted,0) = 0";
		
		return getTableRowsAsJson(sql);
	}

}
