package com.indocosmo.mrp.web.stock.stockadjustments.stockadjustmentdetail.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.stock.stockadjustments.stockadjustmentdetail.dao.StockAdjustmentDetailDao;
import com.indocosmo.mrp.web.stock.stockadjustments.stockadjustmentdetail.model.StockAdjustmentDetail;
import com.indocosmo.mrp.web.stock.stockadjustments.stockadjustmentdetail.service.StockAdjustmentDetailService;

@Controller
@Qualifier("StockAdjustmentDetailController")
@RequestMapping("/stockadjustmentdetail")
public class StockAdjustmentDetailController extends ViewController<StockAdjustmentDetail,StockAdjustmentDetailDao,StockAdjustmentDetailService> {
	
	
	
	@Override
	public StockAdjustmentDetailService getService() {
		// TODO Auto-generated method stub
		return new StockAdjustmentDetailService(getCurrentContext());
	}

}
