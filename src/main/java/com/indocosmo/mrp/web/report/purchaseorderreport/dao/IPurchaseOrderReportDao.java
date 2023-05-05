package com.indocosmo.mrp.web.report.purchaseorderreport.dao;

import java.util.List;

import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.report.purchaseorderreport.model.PurchaseOrderReportModel;


public interface IPurchaseOrderReportDao extends IGeneralDao<PurchaseOrderReportModel>{

	public List<PurchaseOrderReportModel> getreportDetails(PurchaseOrderReportModel pohdr) throws Exception;
	
}
