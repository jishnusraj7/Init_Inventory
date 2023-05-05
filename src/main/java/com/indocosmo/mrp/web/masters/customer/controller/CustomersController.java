package com.indocosmo.mrp.web.masters.customer.controller;

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
import com.indocosmo.mrp.web.masters.customer.dao.CustomersDao;
import com.indocosmo.mrp.web.masters.customer.model.Customers;
import com.indocosmo.mrp.web.masters.customer.service.CustomersService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping(value = "/customers")
public class CustomersController extends ViewController<Customers, CustomersDao, CustomersService> {
	
	public static final Logger logger = Logger.getLogger(CustomersController.class);
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public CustomersService getService() {
	
		return new CustomersService(getCurrentContext());
	}
	
	/**
	 * @param itemMaster
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute Customers itemMaster , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_CUST");
		
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("Customer",true);
		model.addAttribute("customerclass",true);
		return "/customers/list";
		
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("customers", "customers");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param Quetable
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveCustomers", method = RequestMethod.POST)
	public void saveCustomers(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final CustomersService customersService = new CustomersService(getCurrentContext());
		final CustomersDao customerDao = new CustomersDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		
		final String DB_TXN_POINT = "customersService";
		
		Gson gson = new Gson();
		Customers customers = null;
		SyncQueue syncQueue = null;
		customersService.beginTrans(DB_TXN_POINT);
		
		try {
			customers = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (customers.getId().toString() != "") {
				syncQueue = new SyncQueue();
				String tableName = customerDao.getTable();
				String bathId = SyncQueueService.fungetSynQueId(tableName, customers.getId().toString());
				
				if (bathId != "") {
					syncQueue.setId(Integer.parseInt(bathId));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(customers.getId());
				
				SyncQueueService.save(syncQueue);
				
			}
			
			response.getWriter().print("1");
			
			customersService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			customersService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveSupplier" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final SyncQueueService syncQueueService = new SyncQueueService(getCurrentContext());
		final CustomersDao customerDao = new CustomersDao(getCurrentContext());
		final CustomersService customersService = new CustomersService(getCurrentContext());
		final String DB_TXN_POINT = "customersService";
		final String id = request.getParameter("id");
		
		customersService.beginTrans(DB_TXN_POINT);
		try {
			
			Integer is_sync = 0;
			Integer is_deleted = 0;
			is_deleted = customersService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				is_sync = syncQueueService.updateSyncQueue(id, customerDao.getTable());
				response.getWriter().print("1");
			}
			
			customersService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			customersService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}

	
}
