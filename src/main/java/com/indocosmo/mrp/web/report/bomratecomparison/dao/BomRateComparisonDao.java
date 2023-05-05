package com.indocosmo.mrp.web.report.bomratecomparison.dao;

import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.report.bomratecomparison.model.BomRateComparison;
import com.indocosmo.mrp.web.report.prodbomreport.model.ProdBomReportModel;

public class BomRateComparisonDao extends GeneralDao<BomRateComparison> implements IBomRateComparisonDao {
	
	public BomRateComparisonDao(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public BomRateComparison getNewModelInstance() {
	
		return new BomRateComparison();
	}
	
	@Override
	protected String getTable() {
	
		return "mrp_stock_item";
	}
	

	public JsonArray getBomRateData(BomRateComparison bomrate,HttpSession session) throws Exception {
		
		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		
		Integer itemcategory = 0;
		String stockitem = "0";
	
		String sql="";
		
		if((bomrate.getItemcategory()).isEmpty() == false) {
			
			itemcategory = Integer.parseInt(bomrate.getItemcategory());
			
		}
		
		if((bomrate.getStockitem()).isEmpty() == false) {
			
			
			stockitem = bomrate.getStockitem();
			
		}
		
		sql="CALL usp_bom_rate_comparison_report('"+itemcategory+"','"+stockitem+"')";
		
		return getTableRowsAsJson(sql);
		
		
	}
	
}
