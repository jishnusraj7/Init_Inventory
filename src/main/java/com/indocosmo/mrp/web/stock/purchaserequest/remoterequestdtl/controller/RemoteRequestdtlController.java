package com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.dao.RemoteRequestdtlDao;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.model.RPR_dtl;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.service.RemoteRequestdtlService;
@Controller
@RequestMapping("/remoterequestdtl")
public class RemoteRequestdtlController extends ViewController<RPR_dtl,RemoteRequestdtlDao,RemoteRequestdtlService>{



	@Override
	public RemoteRequestdtlService getService() {

		return new RemoteRequestdtlService(getCurrentContext());
	}

	@RequestMapping(value = "rpr_dtllist")
	public void getPO_hdrList(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		String[] remote_request_hdr_id = request.getParameterValues("id");
		final RemoteRequestdtlService purchaseorderService=new RemoteRequestdtlService(getCurrentContext());
/*		List<RPR_dtl> list = purchaseorderService.getList(remote_request_hdr_id[0]);
		String json = new Gson().toJson(list);	
		response.getWriter().print(json);*/
		
		JsonArray list = purchaseorderService.getPrDtlData(remote_request_hdr_id[0]);
		JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.add("rprDtl", list);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}



	@RequestMapping(value = "rpr_dtllist1")
	public void getRPR_hdrList(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {


		final RemoteRequestdtlService purchaseorderService=new RemoteRequestdtlService(getCurrentContext());
		List<RPR_dtl> list = purchaseorderService.getList();
		String json = new Gson().toJson(list);

		response.getWriter().print(json);
	}


}
