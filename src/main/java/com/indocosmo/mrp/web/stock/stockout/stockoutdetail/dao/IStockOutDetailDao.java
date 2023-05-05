package com.indocosmo.mrp.web.stock.stockout.stockoutdetail.dao;

import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.model.StockOutDetail;


public interface IStockOutDetailDao extends IGeneralDao<StockOutDetail>{
	//Added By Udhay on  31st Aug 2021 for StockOut Data Issue
	public Double getUnitPrice(String item_code ) throws Exception;

}
