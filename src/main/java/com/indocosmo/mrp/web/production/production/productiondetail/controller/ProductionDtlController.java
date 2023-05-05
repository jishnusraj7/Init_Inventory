package com.indocosmo.mrp.web.production.production.productiondetail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.production.production.productiondetail.dao.ProductionDtlDao;
import com.indocosmo.mrp.web.production.production.productiondetail.model.ProductionDetail;
import com.indocosmo.mrp.web.production.production.productiondetail.service.ProductionDtlService;



@Controller
@RequestMapping("/productiondetail")
public class ProductionDtlController extends ViewController<ProductionDetail,ProductionDtlDao,ProductionDtlService>{

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public ProductionDtlService getService() {

		return new ProductionDtlService(getCurrentContext());
	}
}
