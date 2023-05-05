package com.indocosmo.mrp.web.masters.vouchertypes.voucherclass.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.department.model.Department;
import com.indocosmo.mrp.web.masters.department.service.DepartmentService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.masters.vouchertypes.model.Vouchers;
import com.indocosmo.mrp.web.masters.vouchertypes.voucherclass.dao.VouchersClassDao;
import com.indocosmo.mrp.web.masters.vouchertypes.voucherclass.model.VoucherClass;
import com.indocosmo.mrp.web.masters.vouchertypes.voucherclass.service.VoucherClassService;

/**
 * @author jiksa
 *
 */
@Controller
@RequestMapping("/vouchertype")
public class VoucherClassController extends ViewController<VoucherClass, VouchersClassDao, VoucherClassService> {
	
	public static final Logger logger = Logger.getLogger(VoucherClassController.class);
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public VoucherClassService getService() {
	
		return new VoucherClassService(getCurrentContext());
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
	 * @param completeData
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveData", method = RequestMethod.POST)
	public void save(@RequestParam(value = "data", required = true) String completeData , HttpSession session ,
			HttpServletResponse response , HttpServletRequest request) throws Exception {
	
		final String DB_TXN_POINT = "VoucherClassService";
		Gson gson = new Gson();
		JsonParser parser1 = new JsonParser();
		ObjectMapper mapper1 = new ObjectMapper();
		
		VoucherClass voucher = null;
		SyncQueue syncQueue = null;
		
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final VoucherClassService vchrService = new VoucherClassService(getCurrentContext());
		final VouchersClassDao vouchersDao = new VouchersClassDao(getCurrentContext());
		vchrService.beginTrans(DB_TXN_POINT);
		JsonObject jobject = parser1.parse(completeData).getAsJsonObject();
		
		// ObjectMapper mapper = new
		// ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
		// false);
		
		try {
			voucher = mapper1.readValue(gson.toJson(jobject.get("voucherTypeData")), VoucherClass.class);
			// voucher=mapper.readValue(voucherTypeData, VoucherClass.class);
			
			voucher = vchrService.saveVouchr(voucher);
			JsonObject jsonObject = parser1.parse(gson.toJson(jobject.get("Quetable"))).getAsJsonObject();
			String Qutable = jsonObject.toString();
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			if (voucher.getId().toString() != "") {
				syncQueue = new SyncQueue();
				String tableName = vouchersDao.getTable();
				String bathId = SyncQueueService.fungetSynQueId(tableName, voucher.getId().toString());
				if (bathId != "") {
					syncQueue.setId(Integer.parseInt(bathId));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(voucher.getId());
				
				SyncQueueService.save(syncQueue);
				
			}
			
			vchrService.endTrans(DB_TXN_POINT);
			JsonObject obj1 = new JsonObject();
			obj1.addProperty("id", voucher.getId());
			obj1.addProperty("name", voucher.getName());
			response.getWriter().print(obj1);
			
		}
		catch (Exception e) {
			response.getWriter().print("0");
			vchrService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method:  saveData in" + this + Throwables.getStackTraceAsString(e));
		}
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final VouchersClassDao voucherClsDao = new VouchersClassDao(getCurrentContext());
		final VoucherClassService vouchersService = new VoucherClassService(getCurrentContext());
		final String DB_TXN_POINT = "voucherClsService";
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
				is_sync = SyncQueueService.updateSyncQueue(id, voucherClsDao.getTable());
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
	
	/**
	 * @param department
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "excelReport")
	public ModelAndView generateExcel(@ModelAttribute Department department , HttpServletRequest request ,
			HttpServletResponse response) throws Exception {
	
		final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		
		try {
			department.setDptExcelData(departmentService.getDptData());
		}
		catch (Exception e) {
			throw e;
		}
		return new ModelAndView("departmentView", "invoiceData", department);
	}
	
}
