package com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.dao.PurchaseRequestdtlDao;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.model.PR_dtl;
import com.indocosmo.mrp.web.stock.purchaserequest.purchaserequestdtl.service.PurchaseRequestdtlService;

@Controller
@RequestMapping("/purchaserequestdtl")
public class PurchaseRequestdtlController
		extends
		ViewController<PR_dtl, PurchaseRequestdtlDao, PurchaseRequestdtlService> {

	@Override
	public PurchaseRequestdtlService getService() {

		return new PurchaseRequestdtlService(getCurrentContext());
	}

	@RequestMapping(value = "pr_dtllist")
	public void getPO_hdrList(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		String[] pr_hdr_id = request.getParameterValues("id");
		final PurchaseRequestdtlService purchaseorderdtlservice = new PurchaseRequestdtlService(
				getCurrentContext());
		/*
		 * List<PR_dtl> list = purchaseorderdtlservice.getList(pr_hdr_id[0]);
		 * String json = new Gson().toJson(list);
		 * 
		 * response.getWriter().print(json);
		 */

		JsonArray list = purchaseorderdtlservice.getPrDtlData(pr_hdr_id[0]);
		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("prDtl", list);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

}
