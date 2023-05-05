package com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.model.PR_dtl;


public interface IPurchaseRequestdtlDao extends IGeneralDao<PR_dtl> {
	public JsonArray getPrDtlData(String id) throws Exception;

	

}
