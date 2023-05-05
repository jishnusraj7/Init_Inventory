package com.indocosmo.mrp.web.stock.purchaserequest.remoterequesthdr.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.model.RPR_dtl;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.service.RemoteRequestdtlService;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequesthdr.dao.RemoteRequesthdrDao;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequesthdr.model.RPR_hdr;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequesthdr.service.RemoteRequesthdrService;





@Controller
@RequestMapping("/remoterequesthdr")
public class RemoteRequesthdrController extends ViewController<RPR_hdr,RemoteRequesthdrDao,RemoteRequesthdrService>{



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public RemoteRequesthdrService getService() {

		return new RemoteRequesthdrService(getCurrentContext());
	}

	/**
	 * @param PO_hdr
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute RPR_hdr PO_hdr,HttpSession session,Model model)
			throws Exception {		

		final CurrentUserPermissionService userPermissionService=new CurrentUserPermissionService();		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "STR_RPREQ");
		model.addAttribute("permission", permission);		
		model.addAttribute("Store",true);
		
		model.addAttribute("Purchase",true);
		model.addAttribute("rprclass",true);
		
		return "/remotepurchaserequest/list";
	}



	/**
	 * @param prDtl
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/saveStockItem", method=RequestMethod.POST) 
	public void save(@RequestBody String prDtl,HttpSession session,HttpServletResponse response,HttpServletRequest request) throws Exception
	{

		final RemoteRequesthdrService remotepurchaseorderService=new RemoteRequesthdrService(getCurrentContext());		
		final RemoteRequestdtlService purchaseorderdtlservice =new RemoteRequestdtlService(getCurrentContext());			
		final String DB_TXN_POINT = "remotepurchaseorderService";	
		RPR_hdr rprHdr = null;
		ObjectMapper mapper =new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {

			rprHdr = mapper.readValue(prDtl, RPR_hdr.class);
			rprHdr = remotepurchaseorderService.saveStockItem(rprHdr);
			String rprdtl = rprHdr.getStockDetailLists();
			JsonParser parser = new JsonParser();
			JsonArray rprdtlList = (JsonArray) parser.parse(rprdtl);

			final ArrayList<RPR_dtl> stockInItemsList = new ArrayList<RPR_dtl>();				

			for (int i = 0; i < rprdtlList.size(); i++) {

				JsonObject json = (JsonObject) rprdtlList.get(i);

				final RPR_dtl stockInItem = new RPR_dtl();

				stockInItem.setId((!json.get("id").isJsonNull()) ? json.get("id").getAsInt():null);
				stockInItem.setPo_id((!json.get("po_id").isJsonNull() && json.get("po_id").getAsString().length() != 0) ? json.get("po_id").getAsInt():null);
				stockInItem.setRemote_request_hdr_id(rprHdr.getId());
				stockInItem.setStock_item_id(json.get("stock_item_id").getAsInt());
				stockInItem.setStock_item_code(json.get("stock_item_code").getAsString());
				stockInItem.setStock_item_name(json.get("stock_item_name").getAsString());							
				stockInItem.setQty(json.get("qty").getAsDouble());
				stockInItem.setExpected_date(json.get("expectedDate").getAsString());
				stockInItem.setRequest_status(json.get("request_status").getAsInt());

				stockInItemsList.add(stockInItem);	
			}

			purchaseorderdtlservice.save(stockInItemsList);
			rprHdr.setPr_dtl(stockInItemsList);	
			remotepurchaseorderService.endTrans(DB_TXN_POINT);	

		}

		catch (Exception e) {

			e.printStackTrace();
			remotepurchaseorderService.rollbackTrans(DB_TXN_POINT);
			throw e;
		}

	}




	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.ViewController#saveData(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected RPR_hdr saveData(HttpServletRequest request) throws Exception {

		final RemoteRequesthdrService remotepurchaseorderService=new RemoteRequesthdrService(getCurrentContext());		
		final RemoteRequestdtlService purchaseorderdtlservice =new RemoteRequestdtlService(getCurrentContext());			

		RPR_hdr item = null;
		
		final String DB_TXN_POINT = "remotepurchaseorderService";	

		remotepurchaseorderService.beginTrans(DB_TXN_POINT);

		try {

			item = super.saveData(request);		
			
			String[] rpr_dtl_idList = request.getParameterValues("rpr_dtl_id");
			String[] stockInItemIdList = request.getParameterValues("stock_item_id");
			String[] stockInItemCodeList = request.getParameterValues("stock_item_code");
			String[] stockInItemNameList = request.getParameterValues("stock_item_name");
			String[] qtyList = request.getParameterValues("qty");
			String[] expected_dateList = request.getParameterValues("expected_date");
			String[] request_statusList = request.getParameterValues("request_status1");
			String[] po_idList = request.getParameterValues("po_id");		

			final ArrayList<RPR_dtl> stockInItemsList = new ArrayList<RPR_dtl>();

			if (stockInItemIdList != null && stockInItemIdList.length > 0) {

				for (int i = 0; i < stockInItemIdList.length; i++) {

					Integer pr_dtl_id = Integer.parseInt(rpr_dtl_idList[i]);
					final Integer stockInItemid = Integer.parseInt(stockInItemIdList[i]);
					final String stockInItemcode = stockInItemCodeList[i];
					final String stockInItemname = stockInItemNameList[i];
					final Double qty = Double.parseDouble(qtyList[i]);
					final String expecteddate=(expected_dateList[i]);
					final Integer request_status = Integer.parseInt(request_statusList[i]);
					
					Integer po_id = Integer.parseInt(po_idList[i]);

					if(po_id==0){	

						po_id=null;
					}

					if(pr_dtl_id==0){

						pr_dtl_id=null;
					}
					
					final RPR_dtl stockInItem = new RPR_dtl();
					
					stockInItem.setId(pr_dtl_id);
					stockInItem.setRemote_request_hdr_id(item.getId());
					stockInItem.setStock_item_id(stockInItemid);
					stockInItem.setStock_item_code(stockInItemcode);
					stockInItem.setStock_item_name(stockInItemname);					
					stockInItem.setQty(qty);
					stockInItem.setExpected_date(expecteddate);
					stockInItem.setRequest_status(request_status);
					stockInItem.setPo_id(po_id);
					stockInItemsList.add(stockInItem);
				}
			}
			else{

				final RPR_dtl stockInItem = new RPR_dtl();
				stockInItem.setRemote_request_hdr_id(item.getId());
				stockInItemsList.add(stockInItem);	
			}

			purchaseorderdtlservice.save(stockInItemsList);
			item.setPr_dtl(stockInItemsList);		
			remotepurchaseorderService.endTrans(DB_TXN_POINT);
		} catch (Exception e) {

			e.printStackTrace();
			remotepurchaseorderService.rollbackTrans(DB_TXN_POINT);
			throw e;
		}
		return item;

	}




	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.core.base.controller.ViewController#delete(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		final RemoteRequesthdrService remotepurchaseorderService=new RemoteRequesthdrService(getCurrentContext());
		final RemoteRequestdtlService purchaseorderdtlservice =new RemoteRequestdtlService(getCurrentContext());	
		final String DB_TXN_POINT = "remotepurchaseorderService";
		final String id = request.getParameter("id");
		remotepurchaseorderService.beginTrans(DB_TXN_POINT);

		try {

			Integer is_deleted = 0;
			is_deleted = remotepurchaseorderService.delete(id);			

			if (is_deleted == 0) {

				response.getWriter().print("0");
			}
			else{

				is_deleted = 0;
				is_deleted = purchaseorderdtlservice.delete(id);
				response.getWriter().print("1");
			}

			remotepurchaseorderService.endTrans(DB_TXN_POINT);
		} catch (Exception e) {

			remotepurchaseorderService.rollbackTrans(DB_TXN_POINT);
			throw e;
		}
	}
}
