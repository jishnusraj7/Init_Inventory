package com.indocosmo.mrp.web.masters.vouchertypes.controller;

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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.masters.vouchertypes.dao.VouchersDao;
import com.indocosmo.mrp.web.masters.vouchertypes.model.Vouchers;
import com.indocosmo.mrp.web.masters.vouchertypes.service.VouchersService;
import com.indocosmo.mrp.web.masters.vouchertypes.voucherclass.service.VoucherClassService;

/**
 * @author jo
 *
 */
@Controller
@RequestMapping("/vouchers")
public class VouchersController extends ViewController<Vouchers, VouchersDao, VouchersService> {
	
	public static final Logger logger = Logger.getLogger(VouchersController.class);
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public VouchersService getService() {
	
		return new VouchersService(getCurrentContext());
	}
	
	/**
	 * @param department
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute Vouchers department , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_VOUCH");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("vouchersclass",true);
		model.addAttribute("Promotions",true);
		return "/vouchers/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("voucher_types", "voucher_types");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getVouchrDropDown")
	public void getDropDown(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		JsonObject jObj = new JsonObject();
		final VoucherClassService clssService = new VoucherClassService(getCurrentContext());
		JsonArray voucherTypeList = clssService.getDropdownArray();
		jObj.add("voucherTypeList", voucherTypeList);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jObj.toString());
	}
	
	/**
	 * @param Quetable
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveVoucher", method = RequestMethod.POST)
	public void saveVoucher(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final VouchersService voucherService = new VouchersService(getCurrentContext());
		final VouchersDao voucherDao = new VouchersDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		final String DB_TXN_POINT = "voucherService";
		Gson gson = new Gson();
		Vouchers vouchers = null;
		SyncQueue syncQueue = null;
		voucherService.beginTrans(DB_TXN_POINT);
		
		try {
			
			vouchers = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (vouchers.getId().toString() != "") {
				syncQueue = new SyncQueue();
				String tableName = voucherDao.getTable();
				String bathId = SyncQueueService.fungetSynQueId(tableName, vouchers.getId().toString());
				if (bathId != "") {
					syncQueue.setId(Integer.parseInt(bathId));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(vouchers.getId());
				
				SyncQueueService.save(syncQueue);
				
			}
			response.getWriter().print("1");
			
			voucherService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			response.getWriter().print("0");
			voucherService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveVoucher" + this + Throwables.getStackTraceAsString(e));
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
		final VouchersDao vouchersDao = new VouchersDao(getCurrentContext());
		final VouchersService vouchersService = new VouchersService(getCurrentContext());
		final String DB_TXN_POINT = "vouchersService";
		final String id = request.getParameter("id");
		vouchersService.beginTrans(DB_TXN_POINT);
		try {
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = vouchersService.delete(id);
			if (is_deleted == 0) {
				response.getWriter().print("0");
			}
			else {
				is_sync = SyncQueueService.updateSyncQueue(id, vouchersDao.getTable());
				response.getWriter().print("1");
			}
			vouchersService.endTrans(DB_TXN_POINT);
			
		}
		catch (Exception e) {
			vouchersService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}
	

}
