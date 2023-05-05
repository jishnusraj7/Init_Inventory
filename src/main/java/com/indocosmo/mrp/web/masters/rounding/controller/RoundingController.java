package com.indocosmo.mrp.web.masters.rounding.controller;

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
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.rounding.dao.RoundingDao;
import com.indocosmo.mrp.web.masters.rounding.model.Rounding;
import com.indocosmo.mrp.web.masters.rounding.service.RoundingService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.uom.model.Uom;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/rounding")
public class RoundingController extends MasterBaseController<Rounding, RoundingDao, RoundingService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public RoundingService getService() {
	
		return new RoundingService(getCurrentContext());
	}
	
	/**
	 * @param uom
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute Uom uom , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_ROUND");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("settings",true);

		model.addAttribute("roundingclass",true);
		
		
		return "/rounding/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("rounding", "rounding");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/editData")
	public void getModalData(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final RoundingService roundingService = new RoundingService(getCurrentContext());
		
		int Id = Integer.parseInt(request.getParameter("id"));
		
		JsonObject jsonResponse = new JsonObject();
		
		JsonArray editDet = roundingService.getEditDetails(Id);
		
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
	@RequestMapping(value = "/saveRounding", method = RequestMethod.POST)
	public void saveSuppier(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final RoundingService roundingService = new RoundingService(getCurrentContext());
		final RoundingDao roundingDao = new RoundingDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		
		final String DB_TXN_POINT = "roundingService";
		
		Gson gson = new Gson();
		Rounding rounding = null;
		SyncQueue syncQueue = null;
		roundingService.beginTrans(DB_TXN_POINT);
		
		try {
			rounding = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (rounding.getId().toString() != "") {
				syncQueue = new SyncQueue();
				String tableName = roundingDao.getTable();
				String bathId = SyncQueueService.fungetSynQueId(tableName, rounding.getId().toString());
				
				if (bathId != "") {
					syncQueue.setId(Integer.parseInt(bathId));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(rounding.getId());
				
				SyncQueueService.save(syncQueue);
				
			}
			
			response.getWriter().print("1");
			
			roundingService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			roundingService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saverounding" + this + Throwables.getStackTraceAsString(e));
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
		final RoundingDao roundingDao = new RoundingDao(getCurrentContext());
		final RoundingService roundingService = new RoundingService(getCurrentContext());
		final String DB_TXN_POINT = "roundingService";
		final String id = request.getParameter("id");
		
		roundingService.beginTrans(DB_TXN_POINT);
		try {
			
			Integer is_sync = 0;
			Integer is_deleted = 0;
			is_deleted = roundingService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				is_sync = SyncQueueService.updateSyncQueue(id, roundingDao.getTable());
				response.getWriter().print("1");
			}
			
			roundingService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			roundingService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	

	
}
