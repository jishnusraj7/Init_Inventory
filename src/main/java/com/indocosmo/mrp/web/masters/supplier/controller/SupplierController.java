package com.indocosmo.mrp.web.masters.supplier.controller;

import java.util.List;

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
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.supplier.city.dao.CityDao;
import com.indocosmo.mrp.web.masters.supplier.city.service.CityService;
import com.indocosmo.mrp.web.masters.supplier.country.service.CountryService;
import com.indocosmo.mrp.web.masters.supplier.dao.SupplierDao;
import com.indocosmo.mrp.web.masters.supplier.model.Supplier;
import com.indocosmo.mrp.web.masters.supplier.service.SupplierService;
import com.indocosmo.mrp.web.masters.supplier.state.service.StateService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/supplier")
public class SupplierController extends ViewController<Supplier, SupplierDao, SupplierService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Autowired
	AppConfig appConf;
	
	@Override
	public SupplierService getService() {
	
		return new SupplierService(getCurrentContext());
	}
	
	/**
	 * @param Supplier
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute Supplier Supplier , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_SUPP");
		
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("settings",true);
		
		model.addAttribute("supplierclass",true);
		
		
		return "/supplier/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("supplier", "supplier");
		
		response.getWriter().print(CounterWithPrefix);
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
	
		final CityService cityservice = new CityService(getCurrentContext());
		final StateService stateservice = new StateService(getCurrentContext());
		final CountryService countryservice = new CountryService(getCurrentContext());
		
		JsonArray country = countryservice.getJsonArray();
		JsonArray state = stateservice.getJsonArray();
		//JsonArray city = cityservice.getJsonArray();
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("country", country);
		jsonResponse.add("state", state);
		//jsonResponse.add("city", city);
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	@RequestMapping(value = "getCity")
	public void getCity(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {
	
		final CityDao cityservice = new CityDao(getCurrentContext());
		
		String state_id=request.getParameter("state_id");
		
		
		JsonArray city = cityservice.getJsonArrayCity(state_id);
		JsonObject jsonResponse = new JsonObject();
		
		
		jsonResponse.add("city", city);
		
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
	@RequestMapping(value = "supplierList")
	public void getPO_hdrList(HttpServletRequest request , HttpServletResponse response , Model model) throws Exception {
	
		final SupplierService supplierService = new SupplierService(getCurrentContext());
		
		String[] sup_id = request.getParameterValues("id");
		
		List<Supplier> list = supplierService.getList(sup_id[0]);
		String json = new Gson().toJson(list);
		
		response.getWriter().print(json);
	}
	
	/**
	 * @param Quetable
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSupplier", method = RequestMethod.POST)
	public void saveSuppier(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final SupplierService supplierService = new SupplierService(getCurrentContext());
		final SupplierDao supplierDao = new SupplierDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		
		final String DB_TXN_POINT = "supplierService";
		
		Gson gson = new Gson();
		Supplier supplier = null;
		SyncQueue syncQueue = null;
		supplierService.beginTrans(DB_TXN_POINT);
		
		try {
			supplier = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
					== ImplementationMode.FULL.getImplementationModeId() && session.getAttribute("COMPANY_ID").equals(Hq.HQ.getHqId()))
			{
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (supplier.getId().toString() != "") {
				syncQueue = new SyncQueue();
				String tableName = supplierDao.getTable();
				String bathId = SyncQueueService.fungetSynQueId(tableName, supplier.getId().toString());
				
				if (bathId != "") {
					syncQueue.setId(Integer.parseInt(bathId));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(supplier.getId());
				
				
				SyncQueueService.save(syncQueue);
				}
				
			}
			
			response.getWriter().print("1");
			
			supplierService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			supplierService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveSupplier" + this + Throwables.getStackTraceAsString(e));
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
		final SupplierDao supplierDao = new SupplierDao(getCurrentContext());
		final SupplierService supplierService = new SupplierService(getCurrentContext());
		final String DB_TXN_POINT = "supplierService";
		final String id = request.getParameter("id");
		ApplicationContext context=getCurrentContext();
		
		
		supplierService.beginTrans(DB_TXN_POINT);
		try {
			
			Integer is_sync = 0;
			Integer is_deleted = 0;
			is_deleted = supplierService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
						== ImplementationMode.FULL.getImplementationModeId() && context.getCompanyID()==Hq.HQ.getHqId())
				{
				is_sync = SyncQueueService.updateSyncQueue(id, supplierDao.getTable());
				}
				response.getWriter().print("1");
			}
			
			supplierService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			supplierService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	
	/**
	 * @param supplier
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "SupplierReport")
	public ModelAndView generateExcel(@ModelAttribute Supplier supplier , HttpServletRequest request ,
			HttpServletResponse response) throws Exception {
		JsonArray supplierData = new JsonArray();
		final SupplierService supplierService = new SupplierService(getCurrentContext());
		final SupplierDao supplierdao = new SupplierDao(getCurrentContext());

		try {
			
		//	supplier.setSupExcelData(supplierdao.getListData(supplier.getColoumnList(),supplier.getOrderList()));
			JsonObject row=new JsonObject();
			row.addProperty("ColoumnList",supplier.getColoumnList());
			row.addProperty("reportname", supplier.getReportName());
supplierData.add(supplierdao.getJsonListData(supplier.getColoumnList(),supplier.getOrderList()));
supplierData.add(row);

		}
		catch (Exception e) {
			
			throw e;
		}
		
		return new ModelAndView("excelReportView", "reportData", supplierData);
	}*/
}
