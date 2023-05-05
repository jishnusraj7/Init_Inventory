package com.indocosmo.mrp.web.masters.uom.controller;

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
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.uom.dao.UomDao;
import com.indocosmo.mrp.web.masters.uom.model.Uom;
import com.indocosmo.mrp.web.masters.uom.service.UomService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/uom")
public class UomController extends MasterBaseController<Uom, UomDao, UomService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public UomService getService() {
	
		return new UomService(getCurrentContext());
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
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_UOM");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("settings",true);
		model.addAttribute("uomclass",true);
		return "/uom/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("uom", "uom");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDropDownData")
	public void getDropDownData(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		JsonObject jsonResponse = new JsonObject();
		
		final UomService uomService = new UomService(getCurrentContext());
		
		JsonArray baseUom = uomService.getBaseUom();
		
		jsonResponse.add("baseUom", baseUom);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
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
		
		final UomService uomService = new UomService(getCurrentContext());
		
		JsonArray editDet = uomService.getEditDetails(Id);
		
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
	@RequestMapping(value = "/saveUom", method = RequestMethod.POST)
	public void saveUom(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final UomService uomService = new UomService(getCurrentContext());
		final UomDao uomDao = new UomDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		
		final String DB_TXN_POINT = "uomService";
		
		Gson gson = new Gson();
		
		Uom uom = null;
		SyncQueue syncQueue = null;
		uomService.beginTrans(DB_TXN_POINT);
		
		try {
			uom = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (uom.getId().toString() != "") {
				
				syncQueue = new SyncQueue();
				String tableName = uomDao.getTable();
				String Syn_Id = SyncQueueService.fungetSynQueId(tableName, uom.getId().toString());
				
				if (Syn_Id != "") {
					
					syncQueue.setId(Integer.parseInt(Syn_Id));
				}
				
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(uom.getId());
				
				SyncQueueService.save(syncQueue);
			}
			
			response.getWriter().print("1");
			
			uomService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			uomService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveUom" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final UomService uomService = new UomService(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final UomDao uomDao = new UomDao(getCurrentContext());
		
		final String DB_TXN_POINT = "uomService";
		
		final String id = request.getParameter("id");
		
		uomService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = uomService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				is_sync = SyncQueueService.updateSyncQueue(id, uomDao.getTable());
				response.getWriter().print("1");
			}
			uomService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			uomService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
}
