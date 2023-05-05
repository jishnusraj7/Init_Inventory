package com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.model.PO_dtl;


public interface IPurchaseOrderdtlDao extends IGeneralDao<PO_dtl> {
	public JsonArray getPrDtlData(String id) throws Exception;

}
