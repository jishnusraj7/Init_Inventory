package com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.dao.PurchaseOrderdtlDao;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.model.PO_dtl;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.service.PurchaseOrderdtlService;

@Controller
@RequestMapping("/purchaseorderdtl")
public class PurchaseOrderdtlController extends ViewController<PO_dtl, PurchaseOrderdtlDao, PurchaseOrderdtlService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public PurchaseOrderdtlService getService() {
	
		return new PurchaseOrderdtlService(getCurrentContext());
	}
	
	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "po_dtllist")
	public void getPO_hdrList(HttpServletRequest request , HttpServletResponse response , Model model) throws Exception {
	
		final PurchaseOrderdtlService purchaseorderService = new PurchaseOrderdtlService(getCurrentContext());
		
		String[] po_hdr_id = request.getParameterValues("id");
		
		JsonArray list = purchaseorderService.getPrDtlData(po_hdr_id[0]);
		JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.add("poDtl", list);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
}
