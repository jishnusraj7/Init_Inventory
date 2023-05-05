package com.indocosmo.mrp.web.masters.supplier.country.service;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.supplier.country.dao.CountryDao;
import com.indocosmo.mrp.web.masters.supplier.country.model.Country;

public class CountryService extends GeneralService<Country,CountryDao> implements ICountryService{
	
	
	private CountryDao countryDao;

	public CountryService(ApplicationContext context) {
		super(context);
		countryDao=new CountryDao(getContext());
	}

	@Override
	public CountryDao getDao() {
		return countryDao;
	}
	
	
	



}