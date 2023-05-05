package com.indocosmo.mrp.web.dashboard.dao;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.dashboard.model.Dashboard;


public class DashboardDao extends GeneralDao<Dashboard> implements IDashboardDao{

	
	
	public DashboardDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Dashboard getNewModelInstance() {
		
		return new Dashboard();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {
		
		return null;
	}

}
