package com.indocosmo.mrp.web.stock.stockdisposal.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.stock.stockdisposal.model.StockDisposal;

public interface IStockDisposalDao extends IGeneralDao<StockDisposal>{
	public JsonArray getStockDispDtlData(StockDisposal stockDisp) throws Exception;

}
