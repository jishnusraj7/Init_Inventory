package com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.dao.PurchaseReturnDetailDao;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.model.PurchaseReturnDetail;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.service.PurchaseReturnDetailService;

@Controller
@RequestMapping("/purchasereturndetail")
public class PurchaseReturnDetailController extends ViewController<PurchaseReturnDetail, PurchaseReturnDetailDao, PurchaseReturnDetailService>{

	@Override
	public PurchaseReturnDetailService getService() {
		
		return new PurchaseReturnDetailService(getCurrentContext());
	}

}
