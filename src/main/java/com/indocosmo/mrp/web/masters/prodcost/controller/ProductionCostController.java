package com.indocosmo.mrp.web.masters.prodcost.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.indocosmo.mrp.config.AppConfig;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.Hq;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.ImplementationMode;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.controller.MasterBaseController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.prodcost.dao.ProductionCostDao;
import com.indocosmo.mrp.web.masters.prodcost.model.ProductionCost;
import com.indocosmo.mrp.web.masters.prodcost.service.ProductionCostService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/prodcost")
public class ProductionCostController extends MasterBaseController<ProductionCost, ProductionCostDao, ProductionCostService> {
	public static final Logger logger=Logger.getLogger(ProductionCostController.class);
	
	@Autowired
	AppConfig appConf;
	
	
	@Override
	public ProductionCostService getService() {
	
		return new ProductionCostService(getCurrentContext());
	}
	
	/**
	 * @param productionCost
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute ProductionCost productionCost , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "PROD_COST");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
        model.addAttribute("settings",true);
		
		model.addAttribute("prodcostclass",true);
		return "/prodcost/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("prod_costcalc_params", "prod_costcalc_params");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param productionCost
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "ProductionCostReport")
	public ModelAndView generateExcel(@ModelAttribute ProductionCost productionCost , HttpServletRequest request ,
			HttpServletResponse response) throws Exception {
	
		final ProductionCostService prodCostService = new ProductionCostService(getCurrentContext());
		
		try {
			
			//productionCost.setDptExcelData(departmentService.getProdCostData());
			productionCost.setDptExcelData(prodCostService.getExcelData());
			
		}
		catch (Exception e) {
			
			throw e;
		}
		
		return new ModelAndView("productionCostView", "invoiceData", productionCost);
	}
	
	@RequestMapping(value = "/saveProdCost", method = RequestMethod.POST)
	public void saveProductionCost(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final ProductionCostService productionCostService = new ProductionCostService(getCurrentContext());
		final ProductionCostDao prodCostDao = new ProductionCostDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		final String DB_TXN_POINT = "productionCostService";
		
		JsonParser parser1 = new JsonParser();
		Gson gson = new Gson();
		ProductionCost prodCost = null;
		SyncQueue syncQueue = null;
		
		productionCostService.beginTrans(DB_TXN_POINT);
		
		try {
			prodCost = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			
			if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
					== ImplementationMode.FULL.getImplementationModeId())
			{
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (prodCost.getId().toString() != "") {
				syncQueue = new SyncQueue();
				String tableName = prodCostDao.getTable();
				String Syn_Id = SyncQueueService.fungetSynQueId(tableName, prodCost.getId().toString());
				
				if (Syn_Id != "") {
					
					syncQueue.setId(Integer.parseInt(Syn_Id));
				}
				
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(prodCost.getId());
				
				
				SyncQueueService.save(syncQueue);
				}
			}
			
			response.getWriter().print("1");
			
			productionCostService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			productionCostService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveProductionCost" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	

	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final ProductionCostService productionCostService = new ProductionCostService(getCurrentContext());
		final ProductionCostDao productionCostDao = new ProductionCostDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		ApplicationContext context=getCurrentContext();
		final String DB_TXN_POINT = "productionCostService";
		
		final String id = request.getParameter("id");
		
		productionCostService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = productionCostService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
						== ImplementationMode.FULL.getImplementationModeId() && context.getCompanyID()==Hq.HQ.getHqId())
				{
				is_sync = SyncQueueService.updateSyncQueue(id, productionCostDao.getTable());
				}
				response.getWriter().print("1");
			}
			
			productionCostService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			productionCostService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	
	@RequestMapping("costTypeList")
	public void getCostTypeList(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {
	
		final ProductionCostService prodCostService = new ProductionCostService(getCurrentContext());
		
		JsonArray data = prodCostService.getMastersRowJson();
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("data", data);
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
}
