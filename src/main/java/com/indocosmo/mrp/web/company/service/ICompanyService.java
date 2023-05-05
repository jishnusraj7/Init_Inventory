package com.indocosmo.mrp.web.company.service;

import com.indocosmo.mrp.web.company.dao.CompanyDao;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;

public interface ICompanyService extends IGeneralService<Company,CompanyDao>{

	public Company getCompanyByID(Integer companyId) throws Exception;
	
	//public Company getCurrentCompany(Integer companyId) throws Exception;
}
