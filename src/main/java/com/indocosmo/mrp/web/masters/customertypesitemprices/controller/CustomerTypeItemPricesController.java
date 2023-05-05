package com.indocosmo.mrp.web.masters.customertypesitemprices.controller;

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
import com.indocosmo.mrp.web.masters.customertypes.dao.CustomerTypeDao;
import com.indocosmo.mrp.web.masters.customertypes.model.CustomerType;
import com.indocosmo.mrp.web.masters.customertypes.service.CustomerTypeService;
import com.indocosmo.mrp.web.masters.customertypesitemprices.dao.CustomerTypeItemPricesDao;
import com.indocosmo.mrp.web.masters.customertypesitemprices.model.CustomerTypeItemPrices;
import com.indocosmo.mrp.web.masters.customertypesitemprices.service.CustomerTypeItemPricesService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/customertypesitemprices")
public class CustomerTypeItemPricesController extends ViewController<CustomerTypeItemPrices, CustomerTypeItemPricesDao, CustomerTypeItemPricesService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public CustomerTypeItemPricesService getService() {
	
		return new CustomerTypeItemPricesService(getCurrentContext());
	}
	
	/**
	 * @param department
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute CustomerType department , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_CUST_TYPE");
		
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("Customer",true);
		model.addAttribute("customertypeclass",true);
		
		return "/customertypes/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("customer_types", "customer_types");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "customerTypeList")
	public void getCustomerTypes(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		JsonObject jsonResponse = new JsonObject();
		
		try {
			
			JsonArray customTypeList = getService().getCustomerTypesAsJson();
			jsonResponse.add("customTypeList", customTypeList);
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse.toString());
			
		}
		catch (Exception e) {
			
			e.printStackTrace();
			logger.error("Method : DataAsJson " + this + "  " + Throwables.getStackTraceAsString(e));
			// throw new CustomException();
		}
		
	}
	
	/**
	 * @param Quetable
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveCustomerType", method = RequestMethod.POST)
	public void saveCustomerType(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final CustomerTypeService customerTypeService = new CustomerTypeService(getCurrentContext());
		final CustomerTypeDao supplierDao = new CustomerTypeDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		
		final String DB_TXN_POINT = "customerTypeService";
		
		Gson gson = new Gson();
		CustomerTypeItemPrices custType = null;
		SyncQueue syncQueue = null;
		customerTypeService.beginTrans(DB_TXN_POINT);
		
		try {
			custType = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (custType.getId().toString() != "") {
				syncQueue = new SyncQueue();
				String tableName = supplierDao.getTable();
				String bathId = SyncQueueService.fungetSynQueId(tableName, custType.getId().toString());
				
				if (bathId != "") {
					syncQueue.setId(Integer.parseInt(bathId));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(custType.getId());
				
				SyncQueueService.save(syncQueue);
				
			}
			
			response.getWriter().print("1");
			
			customerTypeService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			customerTypeService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveCustomerType" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final CustomerTypeDao custTypeDao = new CustomerTypeDao(getCurrentContext());
		final CustomerTypeService custTypeService = new CustomerTypeService(getCurrentContext());
		final String DB_TXN_POINT = "custTypeService";
		final String id = request.getParameter("id");
		
		custTypeService.beginTrans(DB_TXN_POINT);
		try {
			
			Integer is_sync = 0;
			Integer is_deleted = 0;
			is_deleted = custTypeService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				is_sync = SyncQueueService.updateSyncQueue(id, custTypeDao.getTable());
				response.getWriter().print("1");
			}
			
			custTypeService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			custTypeService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	

}
