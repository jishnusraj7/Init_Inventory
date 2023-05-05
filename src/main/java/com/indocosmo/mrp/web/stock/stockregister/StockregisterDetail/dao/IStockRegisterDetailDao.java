package com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.dao;

import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail;


public interface IStockRegisterDetailDao extends IGeneralDao<StockRegisterDetail>{

	/**
	 * @param stockRegDtl
	 * @return
	 * @throws Exception
	 */
	public Double getOutQtyInRegDtl(StockRegisterDetail stockRegDtl) throws Exception;
	
	/**
	 * @param stockRegDtl
	 * @return
	 * @throws Exception
	 */
	public Double getInQtyInRegDtl(StockRegisterDetail stockRegDtl) throws Exception;

	
}
