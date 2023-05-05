package com.indocosmo.mrp.web.masters.saleitemcombocontents.saleitemcombocontentsubstitution.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.areacode.dao.AreaCodeDao;
import com.indocosmo.mrp.web.masters.areacode.model.AreaCode;
import com.indocosmo.mrp.web.masters.areacode.service.AreaCodeService;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.saleitemcombocontentsubstitution.dao.SaleItemComboContentsSubstituionDao;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.saleitemcombocontentsubstitution.model.SaleItemComboContentsSubstituion;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.saleitemcombocontentsubstitution.service.SaleItemComboContentsSubstituionService;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/saleitemcombocontentsubstitions")
public class SaleItemComboContentsSubstituionController extends ViewController<SaleItemComboContentsSubstituion, SaleItemComboContentsSubstituionDao, SaleItemComboContentsSubstituionService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public SaleItemComboContentsSubstituionService getService() {
	
		return new SaleItemComboContentsSubstituionService(getCurrentContext());
	}
	
	/**
	 * @param response
	 * @param session
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , HttpSession session , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("combo_contents_subtitution", "combo_contents_subtitution");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param areaCode
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute AreaCode areaCode , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_AREA");
		
		model.addAttribute("permission", permission);
		model.addAttribute("Products",true);
		model.addAttribute("Settings",true);
			
		model.addAttribute("combocontentsclass",true);
		return "/combocontents/list";
	}
	
	
	
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDropDownData")
	public void getDropdownData(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		JsonObject jsonResponse = new JsonObject();
		
		final ItemMasterDao itemMasterDao = new ItemMasterDao(getCurrentContext());
		JsonArray uom = itemMasterDao.getUOmData("uoms");
		jsonResponse.add("uom", uom);
		
	
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final AreaCodeDao areaCodeDao = new AreaCodeDao(getCurrentContext());
		final AreaCodeService areaCodeService = new AreaCodeService(getCurrentContext());
		
		final String DB_TXN_POINT = "combocontents";
		
		final String id = request.getParameter("id");
		
		areaCodeService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = areaCodeService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				is_sync = SyncQueueService.updateSyncQueue(id, areaCodeDao.getTable());
				response.getWriter().print("1");
			}
			
			areaCodeService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			areaCodeService.rollbackTrans(DB_TXN_POINT);
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
	@RequestMapping(value = "AreacodeReport")
	public ModelAndView generateExcel(@ModelAttribute AreaCode supplier , HttpServletRequest request ,
			HttpServletResponse response) throws Exception {
	
		final AreaCodeService supplierService = new AreaCodeService(getCurrentContext());
		
		try {
			
			supplier.setAreaExcelData(supplierService.getList());
			
		}
		catch (Exception e) {
			
			throw e;
		}
		return new ModelAndView("areacodeClassView", "invoiceData", supplier);
	}
}
