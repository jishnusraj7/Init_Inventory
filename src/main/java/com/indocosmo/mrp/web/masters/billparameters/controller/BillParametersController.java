package com.indocosmo.mrp.web.masters.billparameters.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.billparameters.dao.BillParametersDao;
import com.indocosmo.mrp.web.masters.billparameters.model.BillParameters;
import com.indocosmo.mrp.web.masters.billparameters.service.BillParametersService;

@Controller
@RequestMapping("/billparameters")
public class BillParametersController extends ViewController<BillParameters, BillParametersDao, BillParametersService>{

	@Override
	public BillParametersService getService() {
		return new BillParametersService(getCurrentContext());
	}

}
