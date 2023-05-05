package com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



import com.google.gson.JsonArray;

import com.indocosmo.mrp.web.core.base.controller.ViewController;

import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.dao.SaleItemDiscountDao;
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.model.SaleItemDiscount;
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.service.SaleItemDiscountService;

@Controller
@RequestMapping("/saleitemdiscount")
public class SaleItemDiscountController extends ViewController<SaleItemDiscount, SaleItemDiscountDao, SaleItemDiscountService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public SaleItemDiscountService getService() {
	
		return new SaleItemDiscountService(getCurrentContext());
	}
	
	
	@RequestMapping(value = "saleItemDataByDiscountId")
	public void saleItemDataByDiscountId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String discountId = request.getParameter("discountId");
		final SaleItemDiscountService saleDicountService=new SaleItemDiscountService(getCurrentContext());
			
		JsonArray json=saleDicountService.getSaleItemJsonArray(Integer.parseInt(discountId));
		
		response.getWriter().print(json);
	}
	
	
}
