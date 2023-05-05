package com.indocosmo.mrp.web.report.purchasereturnreport.service;

import java.util.ArrayList;
import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.purchasereturnreport.dao.PurchaseReturnReportDao;
import com.indocosmo.mrp.web.report.purchasereturnreport.model.PurchaseReturnReport;
import com.indocosmo.mrp.web.stock.purchasereturn.model.PurchaseReturn;

public class PurchaseReturnReportService extends GeneralService<PurchaseReturnReport,PurchaseReturnReportDao>{

	private PurchaseReturnReportDao purchaseReturnDao;
	
	public PurchaseReturnReportService(ApplicationContext context) {
		super(context);
		purchaseReturnDao=new PurchaseReturnReportDao(getContext());
	}

	@Override
	public PurchaseReturnReportDao getDao() {
		return purchaseReturnDao;
	}
	
	public List<PurchaseReturnReport> getStockReturn(PurchaseReturnReport purchaseReturn) throws Exception {
		return purchaseReturnDao.getStockReturn(purchaseReturn);
	}

}
