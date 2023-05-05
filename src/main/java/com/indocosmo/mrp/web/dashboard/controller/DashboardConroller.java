package com.indocosmo.mrp.web.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.dashboard.dao.DashboardDao;
import com.indocosmo.mrp.web.dashboard.model.Dashboard;
import com.indocosmo.mrp.web.dashboard.service.DashboardService;

@Controller
@RequestMapping("/dashboard")
public class DashboardConroller extends ViewController<Dashboard,DashboardDao,DashboardService>{

	
	 private DashboardService dashboardService;
	
	 @Override
		public DashboardService getService() {
			
			return new DashboardService(getCurrentContext());
		}
	 
	 
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value ="/list")
	public String  getDashborad() throws Exception{
		
		return "dashboard/dashboard";
	}

}
