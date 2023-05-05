package com.indocosmo.mrp.web.report.stockadjustmentsummary.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.stockadjustmentsummary.dao.StockAdjustmentReportDao;
import com.indocosmo.mrp.web.report.stockadjustmentsummary.model.StockAdjustmentReport;

public class StockAdjustmentReportService extends GeneralService<StockAdjustmentReport, StockAdjustmentReportDao> implements IStockAdjustmentReportService{

	public StockAdjustmentReportService(ApplicationContext context) {
		super(context);
	}

	@Override
	public StockAdjustmentReportDao getDao() {
		
		return null;
	}

}
