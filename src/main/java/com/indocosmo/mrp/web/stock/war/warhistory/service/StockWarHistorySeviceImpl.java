package com.indocosmo.mrp.web.stock.war.warhistory.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.war.dao.StockWarDaoImpl;
import com.indocosmo.mrp.web.stock.war.model.StockWarModel;
import com.indocosmo.mrp.web.stock.war.service.StockWarService;
import com.indocosmo.mrp.web.stock.war.warhistory.dao.StockWarHistoryDaoImpl;
import com.indocosmo.mrp.web.stock.war.warhistory.model.WarHistoryModel;

public class StockWarHistorySeviceImpl  extends GeneralService<WarHistoryModel,StockWarHistoryDaoImpl> implements StockWarHistoryService{
	private StockWarHistoryDaoImpl stockWarHistoryDaoImpl ;
	
	public StockWarHistorySeviceImpl(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	stockWarHistoryDaoImpl=new StockWarHistoryDaoImpl(getContext());
	}

	@Override
	public StockWarHistoryDaoImpl getDao() {
		// TODO Auto-generated method stub
		return stockWarHistoryDaoImpl;
	}

}
