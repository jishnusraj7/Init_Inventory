package com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.dao.PurchaseOrderdtlDao;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.model.PO_dtl;

public interface IPurchaseOrderdtlService  extends IGeneralService<PO_dtl,PurchaseOrderdtlDao>{
	public JsonArray getPrDtlData(String id) throws Exception;
}
