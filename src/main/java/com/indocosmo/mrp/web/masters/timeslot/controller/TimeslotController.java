package com.indocosmo.mrp.web.masters.timeslot.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.indocosmo.mrp.config.AppConfig;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.Hq;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.ImplementationMode;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.controller.MasterBaseController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.timeslot.dao.TimeslotDao;
import com.indocosmo.mrp.web.masters.timeslot.model.Timeslot;
import com.indocosmo.mrp.web.masters.timeslot.service.TimeslotService;
import com.indocosmo.mrp.web.masters.uom.model.Uom;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/timeslot")
public class TimeslotController extends MasterBaseController<Timeslot, TimeslotDao, TimeslotService>{

	@Autowired
	AppConfig appConf;
	
	@Override
	public TimeslotService getService() {
		// TODO Auto-generated method stub
		return new TimeslotService(getCurrentContext());
	}

	
	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute Uom uom , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_TIMESLOT");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("settings",true);
		model.addAttribute("timeslotclass",true);
		return "/timeslot/list";
	}
	
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		String CounterWithPrefix="";
		if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
				== ImplementationMode.FULL.getImplementationModeId())
		{
		 CounterWithPrefix = counterService.getNextCounterwithPrefix("mrp_time_slot", "mrp_time_slot");
		}
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	
	@RequestMapping(value = "/editData")
	public void getModalData(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		int Id = Integer.parseInt(request.getParameter("id"));
		
		JsonObject jsonResponse = new JsonObject();
		
		final TimeslotService timeslotService = new TimeslotService(getCurrentContext());
		
		JsonArray editDet = timeslotService.getEditDetails(Id);
		
		jsonResponse.add("editDet", editDet.get(0));
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	
	
	
	@RequestMapping(value = "/savetimeslot", method = RequestMethod.POST)
	public void saveUom(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final TimeslotService timeslotService = new TimeslotService(getCurrentContext());
		final TimeslotDao timeslotDao = new TimeslotDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		
		final String DB_TXN_POINT = "timeslotService";
		
		Gson gson = new Gson();
		
		Timeslot timeslot = null;
		SyncQueue syncQueue = null;
		timeslotService.beginTrans(DB_TXN_POINT);
		
		try {
			timeslot = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
					== ImplementationMode.FULL.getImplementationModeId())
			{
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (timeslot.getId().toString() != "") {
				
				syncQueue = new SyncQueue();
				String tableName = timeslotDao.getTable();
				String Syn_Id = SyncQueueService.fungetSynQueId(tableName, timeslot.getId().toString());
				
				if (Syn_Id != "") {
					
					syncQueue.setId(Integer.parseInt(Syn_Id));
				}
				
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(timeslot.getId());
				
				SyncQueueService.save(syncQueue);
			}
			}
			
			response.getWriter().print("1");
			
			timeslotService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			timeslotService.rollbackTrans(DB_TXN_POINT);
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
	
		final TimeslotService timeslotService = new TimeslotService(getCurrentContext());
		final TimeslotDao timeslotDao = new TimeslotDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		ApplicationContext context=getCurrentContext();
		final String DB_TXN_POINT = "uomService";
		
		final String id = request.getParameter("id");
		
		timeslotService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = timeslotService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
						== ImplementationMode.FULL.getImplementationModeId() && context.getCompanyID()==Hq.HQ.getHqId())
				{
				is_sync = SyncQueueService.updateSyncQueue(id, timeslotDao.getTable());
				}
				response.getWriter().print("1");
			}
			timeslotService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			timeslotService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	
}
