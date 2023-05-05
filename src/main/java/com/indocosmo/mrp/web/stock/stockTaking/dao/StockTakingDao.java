package com.indocosmo.mrp.web.stock.stockTaking.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.stock.stockTaking.modal.StockTaking;

public class StockTakingDao extends GeneralDao<StockTaking>{

	public StockTakingDao(ApplicationContext context) {
		super(context);
	}

	@Override
	public StockTaking getNewModelInstance() {

		return new StockTaking();
	}

	@Override
	protected String getTable() {
		
		return "vw_itemstock";
	}

	/*
	 * 
	 *  Done by anandu on 22-01-2020
	 *   
	 */	
	public JsonArray getItemCtegory() throws Exception {

		String Sql="SELECT id, name from mrp_item_category where is_deleted=0";
		return getTableRowsAsJson(Sql);
	}
	
	/*
	 * 
	 *  end Done by anandu on 22-01-2020
	 *   
	 */	
	public JsonArray getDataTableList(String selDate,int dept_id,int cat_id) throws Exception {

		/*String selSql="SELECT * FROM vw_stock_taking WHERE department_id="+dept_id+ " ORDER BY vw_stock_taking.`name`";
		return getTableRowsAsJson(selSql);*/
		String selSql="CALL usp_stock_taking('"+selDate+"',"+dept_id+","+cat_id+")";
		return getTableRowsAsJson(selSql);
	}

}
