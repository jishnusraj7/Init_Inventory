package com.indocosmo.mrp.web.masters.department.controller;

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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.config.AppConfig;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.Hq;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.ImplementationMode;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.controller.MasterBaseController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.department.dao.DepartmentDao;
import com.indocosmo.mrp.web.masters.department.model.Department;
import com.indocosmo.mrp.web.masters.department.service.DepartmentService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/department")
public class DepartmentController extends MasterBaseController<Department, DepartmentDao, DepartmentService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Autowired
	AppConfig appConf;
	
	@Override
	public DepartmentService getService() {
	
		return new DepartmentService(getCurrentContext());
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
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_DEPT");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
model.addAttribute("settings",true);
		
		model.addAttribute("departmentclass",true);
		return "/department/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix="";
		if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
				== ImplementationMode.FULL.getImplementationModeId())
		{
		 CounterWithPrefix = counterService.getNextCounterwithPrefix("mrp_department", "mrp_department");
		}
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	
	
	@RequestMapping(value = "/saveDep", method = RequestMethod.POST)
	public void saveSuppier(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		final DepartmentDao departmentDao = new DepartmentDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		final String DB_TXN_POINT = "departmentService";
		
		JsonParser parser1 = new JsonParser();
		Gson gson = new Gson();
		Department dep = null;
		SyncQueue syncQueue = null;
		
		departmentService.beginTrans(DB_TXN_POINT);
		
		try {
			dep = super.saveData(request);
			if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
					== ImplementationMode.FULL.getImplementationModeId() && session.getAttribute("COMPANY_ID").equals(Hq.HQ.getHqId()))
			{
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (dep.getId().toString() != "") {
				syncQueue = new SyncQueue();
				String tableName = departmentDao.getTable();
				String Syn_Id = SyncQueueService.fungetSynQueId(tableName, dep.getId().toString());
				
				if (Syn_Id != "") {
					
					syncQueue.setId(Integer.parseInt(Syn_Id));
				}
				
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(dep.getId());
				
			
				SyncQueueService.save(syncQueue);
				}
			}
			
			response.getWriter().print("1");
			
			departmentService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			departmentService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveDep" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		final DepartmentDao departmentDao = new DepartmentDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		ApplicationContext context=getCurrentContext();
		final String DB_TXN_POINT = "departmentService";
		
		final String id = request.getParameter("id");
		
		departmentService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = departmentService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
						== ImplementationMode.FULL.getImplementationModeId() && context.getCompanyID()==Hq.HQ.getHqId())
				{
				is_sync = SyncQueueService.updateSyncQueue(id, departmentDao.getTable());
				}
				response.getWriter().print("1");
			}
			
			departmentService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			departmentService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
}
