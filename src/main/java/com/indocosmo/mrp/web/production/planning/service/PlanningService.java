package com.indocosmo.mrp.web.production.planning.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.production.planning.dao.PlanningDao;
import com.indocosmo.mrp.web.production.planning.model.Planning;


public class PlanningService extends GeneralService<Planning,PlanningDao> implements IPlanningService{


	
	private PlanningDao planningDao;

	public PlanningService(ApplicationContext context) {
		super(context);
		planningDao=new PlanningDao(getContext());
	}

	@Override
	public PlanningDao getDao() {
		// TODO Auto-generated method stub
		return planningDao;
	}

	
	public JsonArray  getCustomerDataAsJson() throws Exception {
		return planningDao.getCustomerDataAsJson();
	}
	
	public Planning saveStockItem(Planning stockIn) throws Exception {
		planningDao.save(stockIn);
		
		return stockIn;
	}

	/**
	 * @param string
	 */
	public boolean isOrderDateValid(String orderDate) throws Exception{
		return planningDao.isOrderDateValid(orderDate);
		
	}

	public boolean isOrderDateValidAdd(String orderDate) throws Exception{
	
		// TODO Auto-generated method stub
		return planningDao.isOrderDateValidAdd(orderDate);
	}

	public Integer gotoProduction(String productionIds, int status) throws Exception{
	
		// TODO Auto-generated method stub
		return planningDao.updateOrderStatus(productionIds, status);
	}

	public JsonArray getOrdertotalList(String currentDate) throws Exception{
	
		// TODO Auto-generated method stub
		return planningDao.getOrdertotalList(currentDate);
	}

	public JsonArray getaddorderdate() throws Exception{
	
		// TODO Auto-generated method stub
		return planningDao.getaddorderdate();
	}

}
