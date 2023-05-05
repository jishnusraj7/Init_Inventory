package com.indocosmo.mrp.web.masters.currencydetails.controller;

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
import com.indocosmo.mrp.web.core.base.controller.MasterBaseController;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.currencydetails.dao.CurrencyDetailsDao;
import com.indocosmo.mrp.web.masters.currencydetails.model.CurrencyDetails;
import com.indocosmo.mrp.web.masters.currencydetails.service.CurrencyDetailsService;
import com.indocosmo.mrp.web.masters.rounding.service.RoundingService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/currencydetails")
public class CurrencyDetailsController extends
		ViewController<CurrencyDetails, CurrencyDetailsDao, CurrencyDetailsService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public CurrencyDetailsService getService() {
	
		return new CurrencyDetailsService(getCurrentContext());
	}
	
	/**
	 * @param currencyDetails
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute CurrencyDetails currencyDetails , HttpSession session , Model model)
			throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_CUR");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		
		model.addAttribute("settings",true);
		
		model.addAttribute("currencyclass",true);
		
		
		return "/currencydetails/list";
	}
	
	
	@RequestMapping(value = "formJsonData")
	public void getFormJsonData(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {
	

		final RoundingService roundingService = new RoundingService(getCurrentContext());		
		
		JsonArray roundingData = roundingService.getMastersRowJson();
		
		JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.add("roundingData", roundingData);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("currency", "currency");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "basecurrencyExisting")
	public void isBaseCurrencyExist(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final CurrencyDetailsService currencyDetailsService = new CurrencyDetailsService(getCurrentContext());
		
		Integer isBaseCurrExisting = 0;
		final Integer rowCount = currencyDetailsService.isBaseCurrencyExist();
		
		if (rowCount > 0) {
			
			isBaseCurrExisting = 1;
		}
		
		response.getWriter().print(isBaseCurrExisting);
	}
	
	@RequestMapping(value = "/saveCur", method = RequestMethod.POST)
	public void saveCur(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final CurrencyDetailsService currencyDetailsService = new CurrencyDetailsService(getCurrentContext());
		final CurrencyDetailsDao currencyDetailsDao = new CurrencyDetailsDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		final String DB_TXN_POINT = "currencyDetailsService";
		Gson gson = new Gson();
		CurrencyDetails curr = null;
		SyncQueue syncQueue = null;
		
		currencyDetailsService.beginTrans(DB_TXN_POINT);
		
		try {
			curr = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (curr.getId().toString() != "") {
				
				syncQueue = new SyncQueue();
				String tableName = currencyDetailsDao.getTable();
				String Syn_Id = SyncQueueService.fungetSynQueId(tableName, curr.getId().toString());
				
				if (Syn_Id != "") {
					
					syncQueue.setId(Integer.parseInt(Syn_Id));
				}
				
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(curr.getId());
				
				SyncQueueService.save(syncQueue);
			}
			
			response.getWriter().print("1");
			
			currencyDetailsService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			currencyDetailsService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveCur" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final CurrencyDetailsService currencyDetailsService = new CurrencyDetailsService(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final CurrencyDetailsDao currencyDetailsDao = new CurrencyDetailsDao(getCurrentContext());
		
		final String DB_TXN_POINT = "currencyDetailsService";
		
		final String id = request.getParameter("id");
		
		currencyDetailsService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = currencyDetailsService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				
				is_sync = SyncQueueService.updateSyncQueue(id, currencyDetailsDao.getTable());
				response.getWriter().print("1");
			}
			
			currencyDetailsService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			currencyDetailsService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}
	

	
}
