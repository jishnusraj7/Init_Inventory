package com.indocosmo.mrp.web.core.base.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.BaseDao;
import com.indocosmo.mrp.web.core.base.model.ModelBase;
import com.indocosmo.mrp.web.core.base.service.BaseService;
import com.indocosmo.mrp.web.masters.users.model.Users;

/**
 * @author jojesh-13.2
 * 
 * The base class for all controllers
 *
 */
@Controller
@Qualifier("BaseController")
public abstract class BaseController<T extends ModelBase, D extends BaseDao<T>, S extends BaseService<T,D>> {
	
	@Autowired
	protected HttpSession httpSession;
	private ApplicationContext context;

	/**
	 * Returns the current application context.
	 * @return ApplicationContext
	 */
	protected ApplicationContext getCurrentContext(){
		
		if(context==null)
			context=new ApplicationContext(httpSession);
		
		context.setCompanyInfo((Company)httpSession.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_TAG));
		context.setUser((Users)httpSession.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG));
		
		return context;
	}
	
	/**
	 * Returns new application context..
	 * @return
	 */
	protected ApplicationContext getNewContext(){
		
		return new ApplicationContext(httpSession);
	}
	
	/**
	 * Returns new application context with given company info.
	 * @return
	 */
	protected ApplicationContext getNewContext(Company company){
		
		final ApplicationContext context=new ApplicationContext(httpSession);
		context.setCompanyInfo(company);
		
		return context ;
	}
	
	/**
	 * Abstract method for getting the default service for the controller
	 * @return
	 */
	public abstract S getService();
}
