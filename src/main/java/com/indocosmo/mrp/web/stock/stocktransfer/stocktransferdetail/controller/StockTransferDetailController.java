package com.indocosmo.mrp.web.stock.stocktransfer.stocktransferdetail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.stock.stocktransfer.stocktransferdetail.dao.StockTransDetailDao;
import com.indocosmo.mrp.web.stock.stocktransfer.stocktransferdetail.model.StockTransDetail;
import com.indocosmo.mrp.web.stock.stocktransfer.stocktransferdetail.service.StockTransDetailService;

@Controller
@RequestMapping("/stocktransdtl")
public class StockTransferDetailController extends ViewController<StockTransDetail, StockTransDetailDao, StockTransDetailService>{

	@Override
	public StockTransDetailService getService() {
	
		// TODO Auto-generated method stub
		return new StockTransDetailService(getCurrentContext());
	}
	
}
