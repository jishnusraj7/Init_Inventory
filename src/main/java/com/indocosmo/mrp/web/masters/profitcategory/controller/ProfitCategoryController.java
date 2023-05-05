package com.indocosmo.mrp.web.masters.profitcategory.controller;

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
import com.indocosmo.mrp.web.masters.profitcategory.dao.ProfitCategoryDao;
import com.indocosmo.mrp.web.masters.profitcategory.model.ProfitCategory;
import com.indocosmo.mrp.web.masters.profitcategory.service.ProfitCategoryService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

/**
 * @author jo
 *
 */
@Controller
@RequestMapping("/profitcategory")
public class ProfitCategoryController extends
		MasterBaseController<ProfitCategory, ProfitCategoryDao, ProfitCategoryService> {
	
	@Autowired
	AppConfig appConf;
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public ProfitCategoryService getService() {
	
		return new ProfitCategoryService(getCurrentContext());
	}
	
	/**
	 * @param profitCategory
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute ProfitCategory profitCategory , HttpSession session , Model model)
			throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_PFT_CAT");
		
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("settings",true);
		model.addAttribute("profitclass",true);
		
		return "/profitcategory/list";
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
		 CounterWithPrefix = counterService.getNextCounterwithPrefix("mrp_profit_category", "mrp_profit_category");
		}
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param Quetable
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePro", method = RequestMethod.POST)
	public void saveProfit(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final ProfitCategoryService profitCategoryService = new ProfitCategoryService(getCurrentContext());
		final ProfitCategoryDao profitCategoryDao = new ProfitCategoryDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		
		final String DB_TXN_POINT = "profitCategoryService";
		
		Gson gson = new Gson();
		ProfitCategory profitCategory = null;
		SyncQueue syncQueue = null;
		profitCategoryService.beginTrans(DB_TXN_POINT);
		
		try {
			profitCategory = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			
			if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
					== ImplementationMode.FULL.getImplementationModeId() && session.getAttribute("COMPANY_ID").equals(Hq.HQ.getHqId()))
			{
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (profitCategory.getId().toString() != "") {
				
				syncQueue = new SyncQueue();
				
				String tableName = profitCategoryDao.getTable();
				String Syn_Id = SyncQueueService.fungetSynQueId(tableName, profitCategory.getId().toString());
				
				if (Syn_Id != "") {
					
					syncQueue.setId(Integer.parseInt(Syn_Id));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(profitCategory.getId());
				
				SyncQueueService.save(syncQueue);
			}
			}
			
			response.getWriter().print("1");
			
			profitCategoryService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			profitCategoryService.rollbackTrans(DB_TXN_POINT);
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
	
		final ProfitCategoryService profitCategoryService = new ProfitCategoryService(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final ProfitCategoryDao profitCategoryDao = new ProfitCategoryDao(getCurrentContext());
		ApplicationContext context=getCurrentContext();
		final String DB_TXN_POINT = "profitCategoryService";
		final String id = request.getParameter("id");
		
		profitCategoryService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = profitCategoryService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId() 
						== ImplementationMode.FULL.getImplementationModeId() && context.getCompanyID()==Hq.HQ.getHqId())
				{
				is_sync = SyncQueueService.updateSyncQueue(id, profitCategoryDao.getTable());
				}
				response.getWriter().print("1");
			}
			
			profitCategoryService.endTrans(DB_TXN_POINT);
			
		}
		catch (Exception e) {
			
			profitCategoryService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	

}
