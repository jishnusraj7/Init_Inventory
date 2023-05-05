package com.indocosmo.mrp.web.report.profitsummary.dao;

import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.enumDepartmentType;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.transactionType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.report.prodbomreport.model.ProdBomReportModel;
import com.indocosmo.mrp.web.report.profitsummary.model.ProfitSummaryModel;

public class ProfitSummaryDao extends GeneralDao<ProfitSummaryModel> implements IProfitSummaryDao {
	
	public ProfitSummaryDao(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ProfitSummaryModel getNewModelInstance() {
	
		return new ProfitSummaryModel();
	}
	
	@Override
	protected String getTable() {
	
		return "";
	}
	
	
	@Override
	public JsonArray getProductionData(ProfitSummaryModel prod,HttpSession session) throws Exception {
		
		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		
		String sql="";
		if(prod.getOption() == 1)
		{
		sql="CALL usp_profit_summary_report( '"+prod.getStartdate()+"' ,'"+prod.getEnddate()+"',"+systemSettings.getDecimal_places()+")";
		}else if(prod.getOption() ==2)
		{
		sql="CALL usp_production_profit_report( '"+prod.getStartdate()+"' ,'"+prod.getEnddate()+"',"+systemSettings.getDecimal_places()+")";
		}
		return getTableRowsAsJson(sql);
		
		
		
	}
	
}
