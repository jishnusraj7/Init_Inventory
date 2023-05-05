package com.indocosmo.mrp.web.masters.saleitemchoices.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.choices.service.ChoicesService;
import com.indocosmo.mrp.web.masters.itemmaster.service.ItemMasterService;
import com.indocosmo.mrp.web.masters.saleitemchoices.dao.SaleItemChoicesDao;
import com.indocosmo.mrp.web.masters.saleitemchoices.model.SaleItemChoices;
import com.indocosmo.mrp.web.masters.saleitemchoices.service.SaleItemChoicesService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/saleitemchoices")
public class SaleItemChoicesController extends
		ViewController<SaleItemChoices, SaleItemChoicesDao, SaleItemChoicesService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public SaleItemChoicesService getService() {
	
		return new SaleItemChoicesService(getCurrentContext());
	}
	
	/**
	 * @param saleItemChoices
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute SaleItemChoices saleItemChoices , HttpSession session , Model model)
			throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_SALEITMCH");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("Products",true);
		model.addAttribute("saleitemchoicesclass",true);
		
		return "/saleitemchoices/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("sale_item_choices", "sale_item_choices");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/editData")
	public void getModalData(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		int Id = Integer.parseInt(request.getParameter("id"));
		JsonObject jsonResponse = new JsonObject();
		
		final SaleItemChoicesService saleItemChoicesService = new SaleItemChoicesService(getCurrentContext());
		JsonArray editDet = saleItemChoicesService.getEditDetails(Id);
		jsonResponse.add("editDet", editDet.get(0));
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDropDownData")
	public void getDropdownData(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		JsonObject jsonResponse = new JsonObject();
		final ChoicesService choicesService = new ChoicesService(getCurrentContext());
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		
		JsonArray choiceItem = choicesService.getDropdownArray();
		JsonArray saleItem = itemMasterService.getSaleItems();
		jsonResponse.add("choiceItem", choiceItem);
		jsonResponse.add("saleItem", saleItem);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	/**
	 * @param Quetable
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSaleItemChoice", method = RequestMethod.POST)
	public void saveSuppier(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final SaleItemChoicesService saleItemChoicesService = new SaleItemChoicesService(getCurrentContext());
		final SaleItemChoicesDao saleItemChoicesDao = new SaleItemChoicesDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		final String DB_TXN_POINT = "saleItemChoicesService";
		Gson gson = new Gson();
		SaleItemChoices sc = null;
		SyncQueue syncQueue = null;
		saleItemChoicesService.beginTrans(DB_TXN_POINT);
		
		try {
			sc = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (sc.getId().toString() != "") {
				
				syncQueue = new SyncQueue();
				String tableName = saleItemChoicesDao.getTable();
				String bathId = SyncQueueService.fungetSynQueId(tableName, sc.getId().toString());
				
				if (bathId != "") {
					
					syncQueue.setId(Integer.parseInt(bathId));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(sc.getId());
				
				SyncQueueService.save(syncQueue);
			}
			
			response.getWriter().print("1");
			
			saleItemChoicesService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			response.getWriter().print("0");
			saleItemChoicesService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveSaleItemChoice" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final SaleItemChoicesDao saleItemChoicesDao = new SaleItemChoicesDao(getCurrentContext());
		final SaleItemChoicesService saleItemChoicesService = new SaleItemChoicesService(getCurrentContext());
		final String DB_TXN_POINT = "saleItemChoicesService";
		final String id = request.getParameter("id");
		
		saleItemChoicesService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = saleItemChoicesService.delete(id);
			if (is_deleted == 0) {
				response.getWriter().print("0");
			}
			else {
				is_sync = SyncQueueService.updateSyncQueue(id, saleItemChoicesDao.getTable());
				response.getWriter().print("1");
			}
			saleItemChoicesService.endTrans(DB_TXN_POINT);
			
		}
		catch (Exception e) {
			saleItemChoicesService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}
	

	
}
