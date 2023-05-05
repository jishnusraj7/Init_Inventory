package com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.dao.StockDisposalDetailDao;
import com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.model.StockDisposalDetail;
import com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.service.StockDisposalDetailService;

@Controller
@Qualifier("StockDisposalDetailController")
@RequestMapping("/stockdisposaldetail")
public class StockDisposalDetailController extends ViewController<StockDisposalDetail,StockDisposalDetailDao,StockDisposalDetailService> {
	
	
	
	@Override
	public StockDisposalDetailService getService() {
		// TODO Auto-generated method stub
		return new StockDisposalDetailService(getCurrentContext());
	}

}
