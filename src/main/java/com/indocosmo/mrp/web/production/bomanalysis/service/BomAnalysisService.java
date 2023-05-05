package com.indocosmo.mrp.web.production.bomanalysis.service;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.production.bomanalysis.dao.BomAnalysisDao;
import com.indocosmo.mrp.web.production.bomanalysis.model.BomAnalysis;


public class BomAnalysisService extends GeneralService<BomAnalysis, BomAnalysisDao> implements IBomAnalysisService{

	
	private BomAnalysisDao bomAnalysisDao;
	public BomAnalysisService(ApplicationContext context) {
	
		super(context);
		bomAnalysisDao=new BomAnalysisDao(getContext());
		// TODO Auto-generated constructor stub
	}

	@Override
	public BomAnalysisDao getDao() {
	
		// TODO Auto-generated method stub
		return bomAnalysisDao;
	}

	public JsonArray getItemData(String startDate,String endDate, String departmentId)  throws Exception{
	
		// TODO Auto-generated method stub
		return bomAnalysisDao.getItemData(startDate, endDate, departmentId);
	}

	public JsonArray getBomConsumption(String startDate, String endDate , String departmentId , String stockItemId) throws Exception{
	
		// TODO Auto-generated method stub
		return bomAnalysisDao.getBomConsumption(startDate,endDate,departmentId,stockItemId);
	}
	
	public JsonArray getBomDates() throws Exception{
		
		// TODO Auto-generated method stub
		return bomAnalysisDao.getBomDates();
	}

	public JsonArray getStatusOfBomItem(ArrayList<String> stockItemIdList,String bom_item_id) throws Exception {
		return bomAnalysisDao.getStatusOfBomItem(stockItemIdList,bom_item_id);
	}
}
