package com.indocosmo.mrp.web.masters.areacode.controller;

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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.MasterBaseController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.areacode.dao.AreaCodeDao;
import com.indocosmo.mrp.web.masters.areacode.model.AreaCode;
import com.indocosmo.mrp.web.masters.areacode.service.AreaCodeService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/areacode")
public class AreaCodeController extends MasterBaseController<AreaCode, AreaCodeDao, AreaCodeService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public AreaCodeService getService() {
	
		return new AreaCodeService(getCurrentContext());
	}
	
	/**
	 * @param response
	 * @param session
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , HttpSession session , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("area_codes", "area_codes");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param areaCode
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute AreaCode areaCode , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_AREA");
		
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("settings",true);
			
		model.addAttribute("areacodeclass",true);
		return "/areacode/list";
	}
	
	/**
	 * @param Quetable
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveArea", method = RequestMethod.POST)
	public void saveSuppier(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final AreaCodeService areaCodeService = new AreaCodeService(getCurrentContext());
		final AreaCodeDao areaCodeDao = new AreaCodeDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		
		final String DB_TXN_POINT = "areaCodeService";
		
		Gson gson = new Gson();
		AreaCode area = null;
		SyncQueue syncQueue = null;
		areaCodeService.beginTrans(DB_TXN_POINT);
		
		try {
			area = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (area.getId().toString() != "") {
				
				syncQueue = new SyncQueue();
				String tableName = areaCodeDao.getTable();
				String bathId = SyncQueueService.fungetSynQueId(tableName, area.getId().toString());
				
				if (bathId != "") {
					
					syncQueue.setId(Integer.parseInt(bathId));
				}
				
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(area.getId());
				
				SyncQueueService.save(syncQueue);
			}
			
			response.getWriter().print("1");
			
			areaCodeService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			areaCodeService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveArea" + this + Throwables.getStackTraceAsString(e));
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
		final AreaCodeDao areaCodeDao = new AreaCodeDao(getCurrentContext());
		final AreaCodeService areaCodeService = new AreaCodeService(getCurrentContext());
		
		final String DB_TXN_POINT = "areaCodeService";
		
		final String id = request.getParameter("id");
		
		areaCodeService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = areaCodeService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				is_sync = SyncQueueService.updateSyncQueue(id, areaCodeDao.getTable());
				response.getWriter().print("1");
			}
			
			areaCodeService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			areaCodeService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	

}
