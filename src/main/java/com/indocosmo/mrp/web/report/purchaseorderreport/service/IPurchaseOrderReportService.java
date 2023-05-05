package com.indocosmo.mrp.web.report.purchaseorderreport.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.report.purchaseorderreport.dao.PurchaseOrderReportDao;
import com.indocosmo.mrp.web.report.purchaseorderreport.model.PurchaseOrderReportModel;


public interface IPurchaseOrderReportService extends IGeneralService<PurchaseOrderReportModel,PurchaseOrderReportDao>{

	public List<PurchaseOrderReportModel> getreportDetails(PurchaseOrderReportModel pohdr) throws Exception;
	
}
