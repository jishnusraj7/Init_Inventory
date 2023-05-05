/**
 * 
 */
package com.indocosmo.mrp.web.stock.stocksummary.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.stock.stocksummary.model.StockSummary;

/**
 * @author Mohsina
 *
 */

public class StockSummaryDao extends GeneralDao<StockSummary> implements IStockSummaryDao{

	SystemSettings systemSettings =(SystemSettings) context.getCurrentHttpSession().getAttribute("systemSettings");
	
	public StockSummaryDao(ApplicationContext context) {
		super(context);
	}

	@Override
	public StockSummary getNewModelInstance() {
		
		return new StockSummary();
	}

	@Override
	protected String getTable() {
		
		return "usp_stock_summary";
	}
	
	/*
	 * 
	 *  Done by anandu on 25-01-2020
	 *   
	 */	
	public JsonArray getItemCtegory() throws Exception {

		String Sql="SELECT id, name from mrp_item_category where is_deleted=0";
		return getTableRowsAsJson(Sql);
	}
	
	/*
	 * 
	 *  Done by anandu on 25-01-2020
	 *   
	 */	

	public JsonArray getStockSummary(String date_from, String date_to, String department_id, String category_id) throws Exception{
		
		String selectQuery = "call usp_stock_summary('"+ date_from +"', '"+ date_to +"','"+department_id+"','"+category_id+"')";
		return getTableRowsAsJson(selectQuery);		
	}

}
