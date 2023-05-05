package com.indocosmo.mrp.web.stock.stockregister.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.stock.stockregister.dao.StockRegisterDao;
import com.indocosmo.mrp.web.stock.stockregister.model.StockRegister;
import com.indocosmo.mrp.web.stock.stockregister.service.StockRegisterService;

@Controller
@RequestMapping("/stockregister")
public class StockRegisterController extends ViewController<StockRegister, StockRegisterDao, StockRegisterService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public StockRegisterService getService() {
	
		return new StockRegisterService(getCurrentContext());
	}
	
}
