package com.indocosmo.mrp.web.report.bomanalysisreport.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.report.bomanalysisreport.model.BomAnalysisReportModel;


public class BomAnalysisReportDao extends GeneralDao<BomAnalysisReportModel> implements IBomAnalysisReportDao {

	public BomAnalysisReportDao(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BomAnalysisReportModel getNewModelInstance() {
	
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getTable() {
	
		// TODO Auto-generated method stub
		return null;
	}

	public JsonArray getItemData(String startdate , String enddate) throws Exception {
	
		String sql="";
		
		sql="CALL usp_bom_analysis_summary('"+startdate+"','"+enddate+"')";
		return getTableRowsAsJson(sql);
	}
	
}
