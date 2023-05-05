package com.indocosmo.mrp.web.stock.stockin.stockindetail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.dao.StockInDetailDao;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.model.StockInDetail;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.service.StockInDetailService;

@Controller
@RequestMapping("/stockindtl")
public class StockInDetailController extends ViewController<StockInDetail,StockInDetailDao,StockInDetailService>{

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public StockInDetailService getService() {
	
		return new StockInDetailService(getCurrentContext());
	}
}
