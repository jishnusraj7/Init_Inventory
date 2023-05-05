package com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.service;

import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.dao.StockRegisterDetailDao;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail;

public interface IStockRegisterDetailService extends IGeneralService<StockRegisterDetail,StockRegisterDetailDao>{

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
