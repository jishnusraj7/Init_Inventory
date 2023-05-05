package com.indocosmo.mrp.web.masters.employee.controller;

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
import com.indocosmo.mrp.web.masters.department.service.DepartmentService;
import com.indocosmo.mrp.web.masters.employee.dao.EmployeeDao;
import com.indocosmo.mrp.web.masters.employee.model.Employee;
import com.indocosmo.mrp.web.masters.employee.service.EmployeeService;
import com.indocosmo.mrp.web.masters.employeecategory.service.EmployeeCategoryService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/employee")
public class EmployeeController extends ViewController<Employee, EmployeeDao, EmployeeService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public EmployeeService getService() {
	
		return new EmployeeService(getCurrentContext());
	}
	
	/**
	 * @param Employee
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute Employee employee , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_EMP");
		
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("employeeclass",true);
		model.addAttribute("People",true);
		return "/employee/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("employee", "employee");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param employee
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getEmployeeData")
	public void getEmployeeData(@ModelAttribute Employee employee , HttpServletResponse response) throws Exception {
	
		final EmployeeDao employeeDao = new EmployeeDao(getCurrentContext());
		JsonArray employeDtl = employeeDao.getEmployeeDtlData(employee.getId());
		JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.add("employee", employeDtl);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "formJsonData")
	public void getFormJsonData(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {
	
		final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		final EmployeeCategoryService employeservice = new EmployeeCategoryService(getCurrentContext());
		
		JsonArray employee_category_data = employeservice.getMastersRowJson();
		JsonArray depData = departmentService.getMastersRowJson();
		
		JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.add("depData", depData);
		jsonResponse.add("employee_category_data", employee_category_data);
		
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
	@RequestMapping(value = "/saveEmp", method = RequestMethod.POST)
	public void saveEmp(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final EmployeeService employeeService = new EmployeeService(getCurrentContext());
		final EmployeeDao employeeDao = new EmployeeDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		
		final String DB_TXN_POINT = "employeeService";
		
		Gson gson = new Gson();
		
		Employee itm = null;
		SyncQueue syncQueue = null;
		employeeService.beginTrans(DB_TXN_POINT);
		
		try {
			itm = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (itm.getId().toString() != "") {
				
				syncQueue = new SyncQueue();
				
				String tableName = employeeDao.getTable();
				String Syn_Id = SyncQueueService.fungetSynQueId(tableName, itm.getId().toString());
				
				if (Syn_Id != "") {
					
					syncQueue.setId(Integer.parseInt(Syn_Id));
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
			
			employeeService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			employeeService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveItmCat" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final EmployeeService employeeService = new EmployeeService(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final EmployeeDao employeeDao = new EmployeeDao(getCurrentContext());
		
		final String DB_TXN_POINT = "employeeService";
		
		final String id = request.getParameter("id");
		
		employeeService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = employeeService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				
				is_sync = SyncQueueService.updateSyncQueue(id, employeeDao.getTable());
				response.getWriter().print("1");
			}
			
			employeeService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			employeeService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	

}
