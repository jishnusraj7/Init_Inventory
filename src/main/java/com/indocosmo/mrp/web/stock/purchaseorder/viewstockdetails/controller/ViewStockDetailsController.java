package com.indocosmo.mrp.web.stock.purchaseorder.viewstockdetails.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.stock.purchaseorder.viewstockdetails.dao.ViewStockDetailsDao;
import com.indocosmo.mrp.web.stock.purchaseorder.viewstockdetails.modal.ViewStockDetails;
import com.indocosmo.mrp.web.stock.purchaseorder.viewstockdetails.service.ViewStockDetailsService;


@Controller
@RequestMapping("/ViewStockDetails")
public class ViewStockDetailsController extends ViewController<ViewStockDetails,ViewStockDetailsDao,ViewStockDetailsService>{


	
	
	@Override
	public ViewStockDetailsService getService() {
		
		return new ViewStockDetailsService(getCurrentContext());
	}


	@RequestMapping(value = "viewstockdetailslist")
	public void getviewstockdetailsList(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {

		final ViewStockDetailsService viewstockdetailsservice=new ViewStockDetailsService(getCurrentContext());
		
		List<ViewStockDetails> list = viewstockdetailsservice.getList();
		String json = new Gson().toJson(list);
		
		response.getWriter().print(json);
	}
	
}
