package com.indocosmo.mrp.web.masters.tax.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.tax.dao.TaxDao;
import com.indocosmo.mrp.web.masters.tax.model.Tax;
import com.indocosmo.mrp.web.masters.tax.service.TaxService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/tax")
public class TaxController extends ViewController<Tax, TaxDao, TaxService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public TaxService getService() {
	
		return new TaxService(getCurrentContext());
	}
	
	/**
	 * @param tax
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "list")
	public String getList(@ModelAttribute Tax tax , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_TAX");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("settings",true);
		model.addAttribute("taxclass",true);
		
		return "/tax/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("tax", "tax");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/editData")
	public void getModalData(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final TaxService taxService = new TaxService(getCurrentContext());
		JsonObject jsonResponse = new JsonObject();
		
		int Id = Integer.parseInt(request.getParameter("id"));
		
		JsonArray editDet = taxService.getEditDetails(Id);
		jsonResponse.add("editDet", editDet.get(0));
		
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
	@RequestMapping(value = "/saveTax", method = RequestMethod.POST)
	public void saveTax(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final TaxService taxService = new TaxService(getCurrentContext());
		final TaxDao taxDao = new TaxDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		
		final String DB_TXN_POINT = "taxService";
		
		Gson gson = new Gson();
		Tax tax = null;
		SyncQueue syncQueue = null;
		
		taxService.beginTrans(DB_TXN_POINT);
		
		try {
			tax = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (tax.getId().toString() != "") {
				
				syncQueue = new SyncQueue();
				String tableName = taxDao.getTable();
				String Syn_Id = SyncQueueService.fungetSynQueId(tableName, tax.getId().toString());
				
				if (Syn_Id != "") {
					
					syncQueue.setId(Integer.parseInt(Syn_Id));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(tax.getId());
				
				SyncQueueService.save(syncQueue);
				
			}
			
			taxService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			taxService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveTax" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final TaxService taxService = new TaxService(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final TaxDao taxDao = new TaxDao(getCurrentContext());
		
		final String DB_TXN_POINT = "taxService";
		
		final String id = request.getParameter("id");
		
		taxService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = taxService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				
				is_sync = SyncQueueService.updateSyncQueue(id, taxDao.getTable());
				response.getWriter().print("1");
			}
			
			taxService.endTrans(DB_TXN_POINT);
			
		}
		catch (Exception e) {
			
			taxService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
}
