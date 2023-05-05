package com.indocosmo.mrp.web.masters.companyprofile.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.companyprofile.dao.CompanyProfileDao;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;

public class CompanyProfileService extends GeneralService<CompanyProfile, CompanyProfileDao> implements
		ICompanyProfileService {
	
	private CompanyProfileDao companyProfileDao;
	
	/**
	 * @param context
	 */
	public CompanyProfileService(ApplicationContext context) {
	
		super(context);
		companyProfileDao = new CompanyProfileDao(getContext());
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public CompanyProfileDao getDao() {
	
		// TODO Auto-generated method stub
		return companyProfileDao;
	}
	
}
