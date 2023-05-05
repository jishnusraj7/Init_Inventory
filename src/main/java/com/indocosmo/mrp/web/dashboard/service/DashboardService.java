package com.indocosmo.mrp.web.dashboard.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.dashboard.dao.DashboardDao;
import com.indocosmo.mrp.web.dashboard.model.Dashboard;


public class DashboardService extends GeneralService<Dashboard,DashboardDao> implements IDashboardService{
	


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */

	private DashboardDao dashboardDao;

	public DashboardService(ApplicationContext context) {
		super(context);
		dashboardDao=new DashboardDao(getContext());
	}

	@Override
	public DashboardDao getDao() {
		// TODO Auto-generated method stub
		return dashboardDao;
	}

}
