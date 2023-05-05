package com.indocosmo.mrp.web.stock.war.warhistory.dao;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.stock.war.dao.StockWarDao;
import com.indocosmo.mrp.web.stock.war.warhistory.model.WarHistoryModel;


public class StockWarHistoryDaoImpl extends GeneralDao<WarHistoryModel> implements StockWarHistoryDao{

	public StockWarHistoryDaoImpl(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public WarHistoryModel getNewModelInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getTable() {
		// TODO Auto-generated method stub
		return null;
	}

}
