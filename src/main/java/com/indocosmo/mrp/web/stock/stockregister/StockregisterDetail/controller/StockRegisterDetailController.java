package com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.dao.StockRegisterDetailDao;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.service.StockRegisterDetailService;

@Controller
@RequestMapping("/stockregisterdetail")
public class StockRegisterDetailController extends
		ViewController<StockRegisterDetail, StockRegisterDetailDao, StockRegisterDetailService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public StockRegisterDetailService getService() {
	
		return new StockRegisterDetailService(getCurrentContext());
	}
	
}
