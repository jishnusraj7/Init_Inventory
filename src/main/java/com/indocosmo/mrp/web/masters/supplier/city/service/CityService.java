package com.indocosmo.mrp.web.masters.supplier.city.service;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.supplier.city.dao.CityDao;
import com.indocosmo.mrp.web.masters.supplier.city.model.City;

public class CityService extends GeneralService<City,CityDao> implements ICityService{
	
	private CityDao cityDao;

	public CityService(ApplicationContext context) {
		super(context);
		cityDao=new CityDao(getContext());
	}

	@Override
	public CityDao getDao() {
		return cityDao;
	}

}