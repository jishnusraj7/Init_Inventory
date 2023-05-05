package com.indocosmo.mrp.web.company.service;

import com.indocosmo.mrp.web.company.dao.CompanyDao;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;

/**
 * @author jojesh-13.2
 *
 */
public class CompanyService extends GeneralService<Company,CompanyDao> implements ICompanyService{
	
	private CompanyDao companyDao;
	
	/**
	 * @param context
	 */
	public CompanyService(ApplicationContext context){
		super(context);
		
		companyDao=new CompanyDao(getContext());
	}
	
	@Override
	public Company getCompanyByID(Integer companyId) throws Exception {

		return companyDao.get("id="+companyId);
	}


	@Override
	public CompanyDao getDao() {
 
		return companyDao;
	}

	public Integer getHqId() throws Exception {
	
		
		return companyDao.getHqId();
	}

	
	/*public Company getCurrentCompany(Integer companyId) throws Exception {
		
		return companyDao.getCompany(companyId);
		
	}*/
	
	
	
	
}
