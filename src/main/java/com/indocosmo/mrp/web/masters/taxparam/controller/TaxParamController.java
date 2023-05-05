package com.indocosmo.mrp.web.masters.taxparam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.taxparam.dao.TaxParamDao;
import com.indocosmo.mrp.web.masters.taxparam.model.TaxParam;
import com.indocosmo.mrp.web.masters.taxparam.service.TaxParamService;

@Controller
@RequestMapping("/taxparam")
public class TaxParamController extends ViewController<TaxParam, TaxParamDao, TaxParamService>{

	@Override
	public TaxParamService getService() {
		return new TaxParamService(getCurrentContext());
	}

}
