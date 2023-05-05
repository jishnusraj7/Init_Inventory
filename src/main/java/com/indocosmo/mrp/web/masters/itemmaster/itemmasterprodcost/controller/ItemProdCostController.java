package com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao.ItemMasterBomDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.service.ItemMasterBomService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.dao.ItemProdCostDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.model.ItemProdCost;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.service.ItemProdCostService;

@Controller
@RequestMapping(value = "/prodcostcalc")
public class ItemProdCostController extends ViewController<ItemProdCost, ItemProdCostDao, ItemProdCostService> {
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "costCalcList")
	public void getBatchlist(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final ItemProdCostService itemmasterbomService = new ItemProdCostService(getCurrentContext());
		List<ItemProdCost> bomlist = itemmasterbomService.getList();
		String json = new Gson().toJson(bomlist);
		
		response.getWriter().print(json);
		
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public ItemProdCostService getService() {
	
		return new ItemProdCostService(getCurrentContext());
	}
	
}
