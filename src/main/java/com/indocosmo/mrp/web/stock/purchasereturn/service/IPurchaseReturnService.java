package com.indocosmo.mrp.web.stock.purchasereturn.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.stock.purchasereturn.dao.PurchaseReturnDao;
import com.indocosmo.mrp.web.stock.purchasereturn.model.PurchaseReturn;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.model.PurchaseReturnDetail;

public interface IPurchaseReturnService extends IGeneralService<PurchaseReturn, PurchaseReturnDao>{

	public List<PurchaseReturnDetail> getStockReturn(List<String> stockList, String supplier) throws Exception;
	public PurchaseReturn saveReturnStock(PurchaseReturn purchaseReturnHdr) throws Exception;
	
}
