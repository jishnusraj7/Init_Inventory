package com.indocosmo.mrp.web.sales.stockin.dao;

import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.sales.stockin.model.StockIn;


public interface IStockInDao extends IGeneralDao<StockIn>{
	public int saveStockIn() throws Exception;
}
