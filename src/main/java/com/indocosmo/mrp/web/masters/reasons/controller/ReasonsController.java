package com.indocosmo.mrp.web.masters.reasons.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.department.model.Department;
import com.indocosmo.mrp.web.masters.reasons.dao.ReasonsDao;
import com.indocosmo.mrp.web.masters.reasons.model.Reasons;
import com.indocosmo.mrp.web.masters.reasons.service.ReasonsService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/reasons")
public class ReasonsController extends ViewController<Reasons, ReasonsDao, ReasonsService> {
	
	public static final Logger logger = Logger.getLogger(ReasonsController.class);
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public ReasonsService getService() {
	
		return new ReasonsService(getCurrentContext());
	}
	
	/**
	 * @param department
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute Department department , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_RSNS");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("reasonsclass",true);
		model.addAttribute("Promotions",true);
		
		return "/reasons/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("reasons", "reasons");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param Quetable
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveReasons", method = RequestMethod.POST)
	public void saveSuppier(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final ReasonsService reasonService = new ReasonsService(getCurrentContext());
		final ReasonsDao reasonsDao = new ReasonsDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		final String DB_TXN_POINT = "reasonService";
		Gson gson = new Gson();
		Reasons reasons = null;
		SyncQueue syncQueue = null;
		reasonService.beginTrans(DB_TXN_POINT);
		
		try {
			reasons = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (reasons.getId().toString() != "") {
				syncQueue = new SyncQueue();
				String tableName = reasonsDao.getTable();
				String bathId = SyncQueueService.fungetSynQueId(tableName, reasons.getId().toString());
				if (bathId != "") {
					syncQueue.setId(Integer.parseInt(bathId));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(reasons.getId());
				
				SyncQueueService.save(syncQueue);
				
			}
			
			response.getWriter().print("1");
			
			reasonService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			response.getWriter().print("0");
			reasonService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveReasons" + this + Throwables.getStackTraceAsString(e));
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
		final ReasonsDao reasonsDao = new ReasonsDao(getCurrentContext());
		final ReasonsService reasonsService = new ReasonsService(getCurrentContext());
		final String DB_TXN_POINT = "reasonsService";
		final String id = request.getParameter("id");
		
		reasonsService.beginTrans(DB_TXN_POINT);
		
		try {
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = reasonsService.delete(id);
			if (is_deleted == 0) {
				response.getWriter().print("0");
			}
			else {
				is_sync = SyncQueueService.updateSyncQueue(id, reasonsDao.getTable());
				response.getWriter().print("1");
			}
			reasonsDao.endTrans(DB_TXN_POINT);
			
		}
		catch (Exception e) {
			reasonsService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}

	
}
