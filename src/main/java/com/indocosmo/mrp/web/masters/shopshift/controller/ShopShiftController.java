package com.indocosmo.mrp.web.masters.shopshift.controller;

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
import com.indocosmo.mrp.web.masters.shopshift.dao.ShopShiftDao;
import com.indocosmo.mrp.web.masters.shopshift.model.ShopShift;
import com.indocosmo.mrp.web.masters.shopshift.service.ShopShiftService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/shopshift")
public class ShopShiftController extends MasterBaseController<ShopShift, ShopShiftDao, ShopShiftService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public ShopShiftService getService() {
	
		return new ShopShiftService(getCurrentContext());
	}
	
	/**
	 * @param shopShift
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute ShopShift shopShift , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_SHOP_SHIFT");
		
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("shopshiftclass",true);
		model.addAttribute("People",true);
		return "/shopshift/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("shopshift", "shopshift");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param shopshifts
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getShopshiftData")
	public void getShopshiftData(@ModelAttribute ShopShift shopshifts , HttpServletResponse response) throws Exception {
	
		final ShopShiftDao shopshiftDao = new ShopShiftDao(getCurrentContext());
		
		JsonArray shopshiftDtl = shopshiftDao.getShopshiftDtlData(shopshifts.getId());
		
		JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.add("shopshift", shopshiftDtl);
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
	@RequestMapping(value = "/saveShopShift", method = RequestMethod.POST)
	public void saveShopShift(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final ShopShiftService shopShiftService = new ShopShiftService(getCurrentContext());
		final ShopShiftDao shopshiftDao = new ShopShiftDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		
		final String DB_TXN_POINT = "shopShiftService";
		
		Gson gson = new Gson();
		ShopShift itm = null;
		SyncQueue syncQueue = null;
		
		shopShiftService.beginTrans(DB_TXN_POINT);
		
		try {
			itm = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (itm.getId().toString() != "") {
				
				syncQueue = new SyncQueue();
				String tableName = shopshiftDao.getTable();
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
			
			shopShiftService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			shopShiftService.rollbackTrans(DB_TXN_POINT);
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
	
		final ShopShiftService shopShiftService = new ShopShiftService(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final ShopShiftDao shopShiftDao = new ShopShiftDao(getCurrentContext());
		
		final String DB_TXN_POINT = "shopShiftService";
		
		final String id = request.getParameter("id");
		
		shopShiftService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = shopShiftService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				
				is_sync = SyncQueueService.updateSyncQueue(id, shopShiftDao.getTable());
				response.getWriter().print("1");
			}
			
			shopShiftService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			shopShiftService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	
	/**
	 * @param shopshift
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "ShopShiftReport")
	public ModelAndView generateExcel(@ModelAttribute ShopShift shopshift , HttpServletRequest request ,
			HttpServletResponse response) throws Exception {
	
		final ShopShiftService shopShiftService = new ShopShiftService(getCurrentContext());
		
		try {
			
			shopshift.setShopshiftExcelData(shopShiftService.getList());
		}
		catch (Exception e) {
			
			throw e;
		}
		return new ModelAndView("shopshiftClassView", "invoiceData", shopshift);
	}
	
}
