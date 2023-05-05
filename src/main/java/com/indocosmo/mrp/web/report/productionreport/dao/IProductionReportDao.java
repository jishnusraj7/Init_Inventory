package com.indocosmo.mrp.web.report.productionreport.dao;

import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.report.productionreport.model.ProductionReportModel;


public interface IProductionReportDao extends IGeneralDao<ProductionReportModel>{
	public JsonArray getProductionItemTotal(String startdate,String enddate,String option,HttpSession session) throws Exception ;
	public JsonArray getProductionData(String stock_item_id,String companyId,String startdate,String enddate,String option,HttpSession session) throws Exception;	
}
