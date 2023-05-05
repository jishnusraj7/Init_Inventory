package com.indocosmo.mrp.web.stock.purchasereturn.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.purchasereturn.dao.PurchaseReturnDao;
import com.indocosmo.mrp.web.stock.purchasereturn.model.PurchaseReturn;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.model.PurchaseReturnDetail;

public class PurchaseReturnService extends GeneralService<PurchaseReturn, PurchaseReturnDao> implements IPurchaseReturnService{
	
	private PurchaseReturnDao purchaseReturnDao;
	public PurchaseReturnService(ApplicationContext context) {
		super(context);
		purchaseReturnDao = new PurchaseReturnDao(getContext());
	}

	@Override
	public PurchaseReturnDao getDao() {
		
		return purchaseReturnDao;
	}


	public List<PurchaseReturnDetail> getStockReturn(List<String> stockList, String supplier) throws Exception {
		// TODO Auto-generated method stub
		return purchaseReturnDao.getStockReturnData(stockList,supplier);
	}

	public PurchaseReturn saveReturnStock(PurchaseReturn purchaseReturnHdr) throws Exception {
		// TODO Auto-generated method stub
		purchaseReturnDao.save(purchaseReturnHdr);
		return purchaseReturnHdr;
	}



}
