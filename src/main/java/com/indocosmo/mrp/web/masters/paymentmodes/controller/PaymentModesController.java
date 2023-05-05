package com.indocosmo.mrp.web.masters.paymentmodes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.paymentmodes.dao.PaymentModesDao;
import com.indocosmo.mrp.web.masters.paymentmodes.model.PaymentModes;
import com.indocosmo.mrp.web.masters.paymentmodes.service.PaymentModesService;

@Controller
@RequestMapping("/paymentmodes")
public class PaymentModesController extends ViewController<PaymentModes, PaymentModesDao, PaymentModesService>{

	@Override
	public PaymentModesService getService() {
		return new PaymentModesService(getCurrentContext());
	}

}
