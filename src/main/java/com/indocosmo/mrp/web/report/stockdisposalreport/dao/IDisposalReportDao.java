package com.indocosmo.mrp.web.report.stockdisposalreport.dao;

import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.report.prodbomreport.model.ProdBomReportModel;
import com.indocosmo.mrp.web.report.productionreport.model.ProductionReportModel;
import com.indocosmo.mrp.web.report.stockdisposalreport.model.DisposalReportModel;


public interface IDisposalReportDao extends IGeneralDao<DisposalReportModel>{
	public JsonArray getProductionItemTotal(String startdate,String enddate,String option,HttpSession session) throws Exception ;
	public JsonArray getDisposalDateData(String stock_item_id,String companyId,DisposalReportModel disposal,String option,HttpSession session) throws Exception;
	public JsonArray getDisposalMonthData(DisposalReportModel disposal,HttpSession session) throws Exception;
}
