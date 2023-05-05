package com.indocosmo.mrp.web.masters.saleitemcombocontents.controller;

import java.util.ArrayList;

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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.areacode.model.AreaCode;
import com.indocosmo.mrp.web.masters.areacode.service.AreaCodeService;
import com.indocosmo.mrp.web.masters.combocontents.ComboContentSubstitution.dao.ComboContentsSubstituionDao;
import com.indocosmo.mrp.web.masters.combocontents.ComboContentSubstitution.service.ComboContentsSubstituionService;
import com.indocosmo.mrp.web.masters.combocontents.dao.ComboContentsDao;
import com.indocosmo.mrp.web.masters.combocontents.service.ComboContentsService;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.dao.SaleItemComboContentsDao;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.model.SaleItemComboContents;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.saleitemcombocontentsubstitution.dao.SaleItemComboContentsSubstituionDao;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.saleitemcombocontentsubstitution.model.SaleItemComboContentsSubstituion;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.saleitemcombocontentsubstitution.service.SaleItemComboContentsSubstituionService;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.service.SaleItemComboContentsService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/saleitemcombocontents")
public class SaleItemComboContentsController extends ViewController<SaleItemComboContents, SaleItemComboContentsDao, SaleItemComboContentsService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public SaleItemComboContentsService getService() {
	
		return new SaleItemComboContentsService(getCurrentContext());
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
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("combo_contents", "combo_contents");
		
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
	
	
	
	
	
	/**
	 * @param Quetable
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveComBo", method = RequestMethod.POST)
	public void saveSuppier(@RequestBody String comboData ,  HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	final SaleItemComboContentsService combocontentservice=new SaleItemComboContentsService(getCurrentContext());
		final SaleItemComboContentsSubstituionService combocontentService = new SaleItemComboContentsSubstituionService(getCurrentContext());
		final SaleItemComboContentsSubstituionDao combocontentSubstitutionDao = new SaleItemComboContentsSubstituionDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		
		final String DB_TXN_POINT = "combocontentsService";
		
		Gson gson = new Gson();
		SaleItemComboContents combocontents = null;
		SyncQueue syncQueue = null;
		combocontentService.beginTrans(DB_TXN_POINT);
		
		
		
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		
		
		
		try {
			
			combocontents = mapper.readValue(comboData, SaleItemComboContents.class);
		
			combocontents = combocontentservice.saveComboItem(combocontents);
			
	/*		combocontents = super.saveData(request);*/
			
			
			
		/*	JsonObject jsonObject = parser1.parse(comboData).getAsJsonObject();
			
			
	
			
			
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (combocontents.getId().toString() != "") {
				
				syncQueue = new SyncQueue();
				String tableName = combocontentSubstitutionDao.getTable();
				String bathId = SyncQueueService.fungetSynQueId(tableName, combocontents.getId().toString());
				
				if (bathId != "") {
					
					syncQueue.setId(Integer.parseInt(bathId));
				}
				
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(combocontents.getId());
				
				SyncQueueService.save(syncQueue);
			}
			*/
			
			

			
			
			String Combocontentdtl =combocontents.getSalecomboListItem();
			JsonParser parser = new JsonParser();
			JsonArray CombocontentdtlList = (JsonArray) parser.parse(Combocontentdtl);

		
			
			final ArrayList<SaleItemComboContentsSubstituion> combocontentList = new ArrayList<SaleItemComboContentsSubstituion>();
			
			for (int i = 0; i < CombocontentdtlList.size(); i++) {
			
				
				JsonObject json = (JsonObject) CombocontentdtlList.get(i);

				final SaleItemComboContentsSubstituion  combocontentsubstitution = new SaleItemComboContentsSubstituion();

				combocontentsubstitution.setIs_default(json.get("is_default").getAsInt());
				combocontentsubstitution.setPrice_difference(json.get("price_diff").getAsDouble());
				combocontentsubstitution.setQuantity(json.get("qty").getAsDouble());
				combocontentsubstitution.setSubstitution_sale_item_id(json.get("substitution_sale_item_id").getAsInt());
				combocontentsubstitution.setSale_item_combo_content_id(combocontents.getId());
				
				combocontentList.add(combocontentsubstitution);
				
				
			
			}
			
			combocontentSubstitutionDao.save(combocontentList);
			
			
			
			
			
			
			
			response.getWriter().print("1");
			
			combocontentService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			combocontentService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveComboContents" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}

	
	
	
	
	@RequestMapping(value = "editData")
	public void getPO_hdrList(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		String[] combo_content_id = request.getParameterValues("id");
		final ComboContentsSubstituionDao combocontentsservice = new ComboContentsSubstituionDao(
				getCurrentContext());
	
		JsonArray list = combocontentsservice.getComboContentList(combo_content_id[0]);
		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("data", list);
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
		final ComboContentsDao areaCodeDao = new ComboContentsDao(getCurrentContext());
		final ComboContentsService areaCodeService = new ComboContentsService(getCurrentContext());
		final ComboContentsSubstituionService combocontentsService = new ComboContentsSubstituionService(getCurrentContext());
		
		final String DB_TXN_POINT = "combocontents";
		
		final String id = request.getParameter("id");
		
		areaCodeService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = areaCodeService.delete(id);
			
			if (is_deleted == 0) {
				combocontentsService.delete(id);
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
	
	
}
