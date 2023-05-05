package com.indocosmo.mrp.web.report.stocktransferreport.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.stocktransferreport.dao.StockTransferReportDao;
import com.indocosmo.mrp.web.report.stocktransferreport.model.StockTransferReportModel;


public class StockTransferReportService  extends GeneralService<StockTransferReportModel, StockTransferReportDao>{

	private StockTransferReportDao stockTransferReportDao;
	
	public StockTransferReportService(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
		stockTransferReportDao=new StockTransferReportDao(getContext());
	}

	@Override
	public StockTransferReportDao getDao() {
	
		// TODO Auto-generated method stub
		return stockTransferReportDao;
	}
	
}
