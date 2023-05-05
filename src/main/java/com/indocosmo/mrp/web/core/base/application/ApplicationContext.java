package com.indocosmo.mrp.web.core.base.application;

import javax.servlet.http.HttpSession;

import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.masters.users.model.Users;

/**
 * @author jojesh-13.2
 *
 */
public class ApplicationContext {

	private Company companyInfo;
	private HttpSession currentHttpSession;
	private Users user;
	
	/**
	 * @param session
	 */
	public ApplicationContext(HttpSession session){
		currentHttpSession=session;
	}
	
	/**
	 * @return
	 */
	public HttpSession getCurrentHttpSession() {
		return currentHttpSession;
	}

	/**
	 * @param currentHttpSession
	 */
	public void setCurrentHttpSession(HttpSession currentHttpSession) {
		this.currentHttpSession = currentHttpSession;
	}

	/**
	 * @return
	 */
	public Company getCompanyInfo() {
		return companyInfo;
	}

	/**
	 * @param companyInfo
	 */
	public void setCompanyInfo(Company companyInfo) {
		this.companyInfo = companyInfo;
	}
	
	/**
	 * @return
	 */
	public int getCompanyID(){
		
		return ((companyInfo==null)?0:companyInfo.getId());
	}

	/**
	 * @return
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * @param user
	 */
	public void setUser(Users user) {
		this.user = user;
	}
	
	/**
	 * @return
	 */
	public boolean isHQ(){
		
		return (getCompanyID()==0);
	}
}
