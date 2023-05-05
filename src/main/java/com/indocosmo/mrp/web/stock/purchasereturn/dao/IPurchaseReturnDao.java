package com.indocosmo.mrp.web.stock.purchasereturn.dao;

import java.util.List;

import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.stock.purchasereturn.model.PurchaseReturn;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.model.PurchaseReturnDetail;

public interface IPurchaseReturnDao extends IGeneralDao<PurchaseReturn>{

	public List<PurchaseReturnDetail> getStockReturnData(List<String> stockList, String supplier) throws Exception;
}
