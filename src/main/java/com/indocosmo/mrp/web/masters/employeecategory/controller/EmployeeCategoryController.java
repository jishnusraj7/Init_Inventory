package com.indocosmo.mrp.web.masters.employeecategory.controller;

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
import com.indocosmo.mrp.web.masters.employeecategory.dao.EmployeeCategoryDao;
import com.indocosmo.mrp.web.masters.employeecategory.model.EmployeeCategory;
import com.indocosmo.mrp.web.masters.employeecategory.service.EmployeeCategoryService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

/**
 * @author jo
 *
 */
@Controller
@RequestMapping("/employeecategory")
public class EmployeeCategoryController extends
		MasterBaseController<EmployeeCategory, EmployeeCategoryDao, EmployeeCategoryService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public EmployeeCategoryService getService() {
	
		return new EmployeeCategoryService(getCurrentContext());
	}
	
	/**
	 * @param EmployeeCategory
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute EmployeeCategory employeeCategory , HttpSession session , Model model)
			throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_EMP_CAT");
		
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("employeecatclass",true);
		model.addAttribute("People",true);
		
		return "/employeecategory/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("employee_category", "employee_category");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param Quetable
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveEmpCat", method = RequestMethod.POST)
	public void saveEmpCat(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final EmployeeCategoryService employeeCategoryService = new EmployeeCategoryService(getCurrentContext());
		final EmployeeCategoryDao employeeCategoryDao = new EmployeeCategoryDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		
		final String DB_TXN_POINT = "employeeCategoryService";
		
		Gson gson = new Gson();
		EmployeeCategory itm = null;
		SyncQueue syncQueue = null;
		
		employeeCategoryService.beginTrans(DB_TXN_POINT);
		
		try {
			itm = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (itm.getId().toString() != "") {
				
				syncQueue = new SyncQueue();
				String tableName = employeeCategoryDao.getTable();
				String syn_Id = SyncQueueService.fungetSynQueId(tableName, itm.getId().toString());
				
				if (syn_Id != "") {
					
					syncQueue.setId(Integer.parseInt(syn_Id));
				}
				
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(itm.getId());
				
				SyncQueueService.save(syncQueue);
			}
			
			response.getWriter().print("1");
			
			employeeCategoryService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			employeeCategoryService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveItmCat" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final EmployeeCategoryService employeeCategoryService = new EmployeeCategoryService(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final EmployeeCategoryDao employeeCategoryDao = new EmployeeCategoryDao(getCurrentContext());
		
		final String DB_TXN_POINT = "employeeCategoryService";
		
		final String id = request.getParameter("id");
		
		employeeCategoryService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = employeeCategoryService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				
				is_sync = SyncQueueService.updateSyncQueue(id, employeeCategoryDao.getTable());
				response.getWriter().print("1");
			}
			
			employeeCategoryService.endTrans(DB_TXN_POINT);
			
		}
		catch (Exception e) {
			
			employeeCategoryService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	

}
