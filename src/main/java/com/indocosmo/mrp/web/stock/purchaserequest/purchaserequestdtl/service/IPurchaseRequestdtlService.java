package com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.dao.PurchaseRequestdtlDao;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.model.PR_dtl;


public interface IPurchaseRequestdtlService  extends IGeneralService<PR_dtl,PurchaseRequestdtlDao>{
	public JsonArray getPrDtlData(String id) throws Exception;

}
