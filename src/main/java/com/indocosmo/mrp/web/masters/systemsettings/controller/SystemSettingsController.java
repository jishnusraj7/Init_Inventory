package com.indocosmo.mrp.web.masters.systemsettings.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.billparameters.dao.BillParametersDao;
import com.indocosmo.mrp.web.masters.billparameters.model.BillParameters;
import com.indocosmo.mrp.web.masters.billparameters.service.BillParametersService;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.companyprofile.service.CompanyProfileService;
import com.indocosmo.mrp.web.masters.customertypes.service.CustomerTypeService;
import com.indocosmo.mrp.web.masters.envsettings.dao.EnvSerttingsDao;
import com.indocosmo.mrp.web.masters.envsettings.model.EnvSerttings;
import com.indocosmo.mrp.web.masters.envsettings.service.EnvSerttingsService;
import com.indocosmo.mrp.web.masters.paymentmodes.dao.PaymentModesDao;
import com.indocosmo.mrp.web.masters.paymentmodes.model.PaymentModes;
import com.indocosmo.mrp.web.masters.paymentmodes.service.PaymentModesService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.systemsettings.dao.SystemSettingsDao;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.systemsettings.service.SystemSettingsService;
import com.indocosmo.mrp.web.masters.tax.dao.TaxDao;
import com.indocosmo.mrp.web.masters.taxparam.model.TaxParam;
import com.indocosmo.mrp.web.masters.taxparam.service.TaxParamService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.masters.users.dao.UsersDao;
import com.indocosmo.mrp.web.masters.users.model.Users;

/**
 * @author jo
 *
 */
@Controller
@RequestMapping("/systemsettings")
public class SystemSettingsController extends ViewController<SystemSettings, SystemSettingsDao, SystemSettingsService> {
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public SystemSettingsService getService() {
	
		return new SystemSettingsService(getCurrentContext());
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute SystemSettings sytemsettings , HttpSession session , Model model)
			throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_SYS_SET");
		
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("systemsettingsclass",true);
		model.addAttribute("General",true);
		
		
		
		return "/systemsettings/list";
	}
	
	/**
	 * @param session
	 * @param systemSettings
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getsystemsetting")
	public void getSystemSettings(HttpSession session , @ModelAttribute SystemSettings systemSettings ,
			HttpServletResponse response) throws Exception {
	
		final CompanyProfileService companyprofileService = new CompanyProfileService(getNewContext());
		final SystemSettingsService systemsettingsService = new SystemSettingsService(getCurrentContext());
		final UsersDao userDao = new UsersDao(getCurrentContext());
		String currentLoginDate = "";
		Users user = null;
		List<CompanyProfile> CompanyProfile = companyprofileService.getList();
		
		session.setAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS, CompanyProfile.get(0));
		systemSettings = systemsettingsService.getSystemData();
		session.setAttribute("systemSettings", systemSettings);
		user = (Users) session.getAttribute("user");
		currentLoginDate = userDao.getCurrentDateTimeFormat(user.getLastLoginDate(), systemSettings);
		user.setLoginDate(currentLoginDate);
		session.setAttribute("user", user);
		
		response.getWriter().print("1");
	}
	
	/**
	 * @param itemData
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveItem", method = RequestMethod.POST)
	public void save(@RequestBody String itemData , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		Gson gson = new Gson();
		
		JsonParser parser1 = new JsonParser();
		
		final String DB_TXN_POINT = "systemSettingsService";
		
		final SystemSettingsService systemSettingsService = new SystemSettingsService(getCurrentContext());
		final TaxParamService taxParamService = new TaxParamService(getCurrentContext());
		final BillParametersService billParametersService = new BillParametersService(getCurrentContext());
		final EnvSerttingsService envSerttingsService = new EnvSerttingsService(getCurrentContext());
		final PaymentModesService paymentModesService = new PaymentModesService(getCurrentContext());
		final SyncQueueService syncQueueService = new SyncQueueService(getCurrentContext());
		final SystemSettingsDao systemSettingsDao = new SystemSettingsDao(getCurrentContext());
		final TaxDao taxDao = new TaxDao(getCurrentContext());
		final BillParametersDao billParametersDao = new BillParametersDao(getCurrentContext());
		final PaymentModesDao paymentModesDao = new PaymentModesDao(getCurrentContext());
		final EnvSerttingsDao envSerttingsDao = new EnvSerttingsDao(getCurrentContext());
		CompanyProfile company = (CompanyProfile) session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
		final Integer cmpId = company.getCompany_id();
		
		systemSettingsService.beginTrans(DB_TXN_POINT);
		
		SystemSettings systemSettings = null;
		TaxParam itemTax = null;
		BillParameters params = null;
		EnvSerttings env = null;
		PaymentModes paymode = null;
		SyncQueue que = null;
		String Qutable = "";
		JsonObject jobject = parser1.parse(itemData).getAsJsonObject();
		
		try {
			
			ObjectMapper mapper = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			systemSettings = mapper.readValue(itemData, SystemSettings.class);
			systemSettings = systemSettingsService.saveItem(systemSettings);
			Qutable = gson.toJson(jobject.get("Quetable"));
			
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			if (systemSettings.getId().toString() != "") {
				
				que = new SyncQueue();
				
				String tableName = systemSettingsDao.getTable();
				String syncId = syncQueueService.fungetSynQueId(tableName, systemSettings.getId().toString());
				
				if (syncId != "") {
					
					que.setId(Integer.parseInt(syncId));
				}
				que.setShopId(QutableData.get("shopId").getAsInt());
				que.setCrudAction(QutableData.get("curdAction").getAsString());
				que.setOrigin(QutableData.get("origin").getAsString());
				que.setPublishingDate(QutableData.get("dateTime").getAsString());
				que.setSyncStatus(QutableData.get("syncNow").getAsInt());
				que.setRecordId(systemSettings.getId());
				que.setTableName(tableName);
				
				syncQueueService.save(que);
			}
			
			itemTax = mapper.readValue(gson.toJson(jobject.get("stockDetailLists")), TaxParam.class);
			itemTax = taxParamService.saveItem(itemTax);
			
			if (itemTax.getId().toString() != "") {
				
				que = new SyncQueue();
				String tableName = taxDao.getTable();
				String syncId = syncQueueService.fungetSynQueId(tableName, itemTax.getId().toString());
				
				if (syncId != "") {
					
					que.setId(Integer.parseInt(syncId));
				}
				que.setShopId(QutableData.get("shopId").getAsInt());
				que.setCrudAction(QutableData.get("curdAction").getAsString());
				que.setOrigin(QutableData.get("origin").getAsString());
				que.setPublishingDate(QutableData.get("dateTime").getAsString());
				que.setSyncStatus(QutableData.get("syncNow").getAsInt());
				que.setRecordId(itemTax.getId());
				que.setTableName(tableName);
				
				syncQueueService.save(que);
			}
			params = mapper.readValue(gson.toJson(jobject.get("billParameters")), BillParameters.class);
			params = billParametersService.saveItem(params);
			
			if (params.getId().toString() != "") {
				
				que = new SyncQueue();
				
				String tableName = billParametersDao.getTable();
				String syncId = syncQueueService.fungetSynQueId(tableName, params.getId().toString());
				
				if (syncId != "") {
					
					que.setId(Integer.parseInt(syncId));
				}
				que.setShopId(QutableData.get("shopId").getAsInt());
				que.setCrudAction(QutableData.get("curdAction").getAsString());
				que.setOrigin(QutableData.get("origin").getAsString());
				que.setPublishingDate(QutableData.get("dateTime").getAsString());
				que.setSyncStatus(QutableData.get("syncNow").getAsInt());
				que.setRecordId(params.getId());
				que.setTableName(tableName);
				
				syncQueueService.save(que);
			}
			paymode = mapper.readValue(gson.toJson(jobject.get("paymentModes")), PaymentModes.class);
			paymode = paymentModesService.saveItem(paymode);
			
			if (paymode.getId().toString() != "") {
				
				que = new SyncQueue();
				
				String tableName = paymentModesDao.getTable();
				String syncId = syncQueueService.fungetSynQueId(tableName, paymode.getId().toString());
				
				if (syncId != "") {
					
					que.setId(Integer.parseInt(syncId));
				}
				que.setShopId(QutableData.get("shopId").getAsInt());
				que.setCrudAction(QutableData.get("curdAction").getAsString());
				que.setOrigin(QutableData.get("origin").getAsString());
				que.setPublishingDate(QutableData.get("dateTime").getAsString());
				que.setSyncStatus(QutableData.get("syncNow").getAsInt());
				que.setRecordId(paymode.getId());
				que.setTableName(tableName);
				
				syncQueueService.save(que);
			}
			
			if (cmpId == 0) {
				
				env = mapper.readValue(gson.toJson(jobject.get("envSerttings")), EnvSerttings.class);
				env = envSerttingsService.saveItem(env);
				
				if (env.getId().toString() != "") {
					
					que = new SyncQueue();
					
					String tableName = envSerttingsDao.getTable();
					String syncId = syncQueueService.fungetSynQueId(tableName, env.getId().toString());
					
					if (syncId != "") {
						
						que.setId(Integer.parseInt(syncId));
					}
					que.setShopId(QutableData.get("shopId").getAsInt());
					que.setCrudAction(QutableData.get("curdAction").getAsString());
					que.setOrigin(QutableData.get("origin").getAsString());
					que.setPublishingDate(QutableData.get("dateTime").getAsString());
					que.setSyncStatus(QutableData.get("syncNow").getAsInt());
					que.setRecordId(env.getId());
					que.setTableName(tableName);
					
					syncQueueService.save(que);
				}
			}
			
			systemSettingsService.endTrans(DB_TXN_POINT);
			
		}
		catch (Exception e) {
			
			e.printStackTrace();
			systemSettingsService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: save in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	
	/**
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFormData")
	public void getFormData(HttpServletRequest request , HttpServletResponse response , HttpSession session)
			throws Exception {
	
		JsonObject jsonResponse = new JsonObject();
		
		final SystemSettingsService systemSettingsService = new SystemSettingsService(getCurrentContext());
		final TaxParamService taxParamService = new TaxParamService(getCurrentContext());
		final BillParametersService billParametersService = new BillParametersService(getCurrentContext());
		final EnvSerttingsService envSerttingsService = new EnvSerttingsService(getCurrentContext());
		final PaymentModesService paymentModesService = new PaymentModesService(getCurrentContext());
		
		JsonArray editDetsys = systemSettingsService.getJsonArray();
		JsonArray editDetTaxParam = taxParamService.getEditData();
		JsonArray billParameters = billParametersService.getEditData();
		JsonArray paymentModes = paymentModesService.getEditData();
		
		CompanyProfile company = (CompanyProfile) session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
		final Integer cmpId = company.getCompany_id();
		
		if (cmpId == 0) {
			
			JsonArray envSerttings = envSerttingsService.getEditData();
			jsonResponse.add("envSerttings", envSerttings);
		}
		jsonResponse.add("editDetsys", editDetsys);
		jsonResponse.add("editDetTaxParam", editDetTaxParam);
		jsonResponse.add("billparameters", billParameters);
		jsonResponse.add("paymentModes", paymentModes);
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	/**
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDropdown")
	public void getDropdownData(HttpServletRequest request , HttpServletResponse response , HttpSession session)
			throws Exception {
	
		final CustomerTypeService customerTypeService = new CustomerTypeService(getCurrentContext());
		
		JsonArray customer_types = customerTypeService.getDropdownArray();
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("customer_types", customer_types);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
		
	}
	
}
