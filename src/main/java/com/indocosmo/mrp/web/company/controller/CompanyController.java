package com.indocosmo.mrp.web.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.company.dao.CompanyDao;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.company.service.CompanyService;
import com.indocosmo.mrp.web.core.base.controller.ViewController;

@Controller
@RequestMapping("/company")
public class CompanyController extends ViewController<Company, CompanyDao, CompanyService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public CompanyService getService() {
	
		return new CompanyService(getCurrentContext());
	}
}
