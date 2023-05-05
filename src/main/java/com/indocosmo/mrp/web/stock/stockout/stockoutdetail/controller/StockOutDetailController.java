package com.indocosmo.mrp.web.stock.stockout.stockoutdetail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.dao.StockOutDetailDao;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.model.StockOutDetail;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.service.StockOutDetailService;

@Controller
@RequestMapping("/stockoutdtl")
public class StockOutDetailController extends ViewController<StockOutDetail,StockOutDetailDao,StockOutDetailService>{

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public StockOutDetailService getService() {
	
		return new StockOutDetailService(getCurrentContext());
	}
	
}
