package com.indocosmo.mrp.web.stock.stockdisposal.service;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.stock.stockdisposal.dao.StockDisposalDao;
import com.indocosmo.mrp.web.stock.stockdisposal.model.StockDisposal;

public interface IStockDisposalService extends IGeneralService<StockDisposal,StockDisposalDao> {
	public JsonArray getStockDispDtlData(StockDisposal stockDisp) throws Exception;

}
