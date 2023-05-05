package com.indocosmo.mrp.utils.core;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;

public class SessionManager {
	
	
	public final static String SESSION_CURRENT_COMPANY_TAG="COMPANY_SESSION_INFO";
	public final static String SESSION_CURRENT_COMPANY_ID="COMPANY_ID";
	public final static String SESSION_COMPANY_LIST_TAG="hqCompanyData";
	public final static String SESSION_CURRENT_USER_TAG="user";
	public final static String SESSION_CURRENT_SUPPLIER_TAG="supplier";
	public final static String SESSION_CURRENT_SYSTEMSETTINGS_TAG="systemSettings";
	public final static String SESSION_CURRENT_COMPANY_TYPE="companytype";
	public final static String SESSION_CURRENT_COMPANY_DETAILS="companyDetails";
	public final static String SESSION_USERPERMISSION="userPermissions";
	public final static String SESSION_CURRENT_DEPARTMENT_TAG="department";
	public final static String SESSION_VERSION="version";
	public final static String SESSION_COMBINEMODE="combineMode";
	public final static String SESSION_DAILYPRODVIEW="dailyprodview";
	public static final String SESSION_CURRENT_DEPARTMENT_PRD = "departmentProd";



	

	/**
	 * @param context
	 */
	public static void clearCurrentSession(ApplicationContext context) {
	
		context.getCurrentHttpSession().setAttribute(SESSION_CURRENT_COMPANY_TAG, null);
		context.getCurrentHttpSession().setAttribute(SESSION_COMPANY_LIST_TAG, null);
		context.getCurrentHttpSession().setAttribute(SESSION_CURRENT_USER_TAG, null);
		context.getCurrentHttpSession().setAttribute(SESSION_CURRENT_SUPPLIER_TAG, null);
		context.getCurrentHttpSession().setAttribute(SESSION_CURRENT_DEPARTMENT_TAG, null);

		context.getCurrentHttpSession().setAttribute(SESSION_CURRENT_SYSTEMSETTINGS_TAG, null);
		context.getCurrentHttpSession().setAttribute(SESSION_CURRENT_COMPANY_TYPE, null);
		context.getCurrentHttpSession().setAttribute(SESSION_CURRENT_COMPANY_DETAILS, null);
		context.getCurrentHttpSession().setAttribute(SESSION_USERPERMISSION, null);
		context.getCurrentHttpSession().setAttribute(SESSION_VERSION, null);
		context.getCurrentHttpSession().setAttribute(SESSION_COMBINEMODE, null);

		

//		context.getCurrentHttpSession().invalidate();
	} 

}
