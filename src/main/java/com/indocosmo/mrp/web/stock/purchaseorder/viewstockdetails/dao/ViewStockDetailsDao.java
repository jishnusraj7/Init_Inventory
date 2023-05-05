package com.indocosmo.mrp.web.stock.purchaseorder.viewstockdetails.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.stock.purchaseorder.viewstockdetails.modal.ViewStockDetails;

public class ViewStockDetailsDao extends GeneralDao<ViewStockDetails> implements IViewStockDetailsDao{

	
	
	public ViewStockDetailsDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public ViewStockDetails getNewModelInstance() {
		// TODO Auto-generated method stub
		return new ViewStockDetails();
	}

	@Override
	public String getTable() {
		// TODO Auto-generated method stub
		return "vw_itemstock";
	}

	
	@Override
	public List<ViewStockDetails> getList() throws Exception {
		String SQL="SELECT * from "+getTable();

		return buildItemList(SQL);
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		final String sql = "SELECT * FROM " + getTable();

		return getTableRowsAsJson(sql);
	}
}
