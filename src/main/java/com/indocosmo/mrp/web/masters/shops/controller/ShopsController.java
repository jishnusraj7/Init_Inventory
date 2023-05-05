package com.indocosmo.mrp.web.masters.shops.controller;

import java.io.File;
import java.util.ArrayList;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.company.service.CompanyService;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.dao.CounterDao;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.areacode.service.AreaCodeService;
import com.indocosmo.mrp.web.masters.customer.dao.CustomersDao;
import com.indocosmo.mrp.web.masters.customer.model.Customers;
import com.indocosmo.mrp.web.masters.customer.service.CustomersService;
import com.indocosmo.mrp.web.masters.shops.dao.ShopsDao;
import com.indocosmo.mrp.web.masters.shops.model.Shops;
import com.indocosmo.mrp.web.masters.shops.service.ShopsService;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.dao.ShopDBDao;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.model.ShopDBSettings;
import com.indocosmo.mrp.web.masters.shops.shopdbsettings.service.ShopDBService;
import com.indocosmo.mrp.web.masters.shops.shopdepartments.dao.ShopDepartmentDao;
import com.indocosmo.mrp.web.masters.shops.shopdepartments.model.ShopDepartment;
import com.indocosmo.mrp.web.masters.shops.shopdepartments.service.ShopDepartmentService;
import com.indocosmo.mrp.web.masters.shops.shopusers.dao.ShopUsersDao;
import com.indocosmo.mrp.web.masters.shops.shopusers.model.ShopUsers;
import com.indocosmo.mrp.web.masters.shops.shopusers.service.ShopUsersService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.masters.users.service.UsersService;

@Controller
@RequestMapping("/shops")
public class ShopsController extends ViewController<Shops, ShopsDao, ShopsService> {
	
	public static final Logger logger = Logger.getLogger(ShopsController.class);
	
	@Override
	public ShopsService getService() {
	
		return new ShopsService(getCurrentContext());
	}
	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute Shops shops , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_SHP");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("shopsclass",true);
		model.addAttribute("settings",true);
		
		return "/shops/list";
	}
	
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("shop", "shop");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	@RequestMapping(value = "/saveShop", method = RequestMethod.POST)
	public @ResponseBody String save(HttpSession session , HttpServletResponse response , HttpServletRequest request ,
			@RequestParam(value = "imgFile", required = false) MultipartFile file1 ,
			@RequestParam(value = "data", required = true) String completeData) throws Exception {
	
		
		
		String status = "success";
		Gson gson = new Gson();
		JsonParser parser1 = new JsonParser();
		ObjectMapper mapper1 = new ObjectMapper();
		final String DB_TXN_POINT = "ShopsService";
		
		JsonObject jobject = parser1.parse(completeData).getAsJsonObject();
		final ShopsService shpService = new ShopsService(getCurrentContext());
		final CustomersService customerService = new CustomersService(getCurrentContext());
		
		final CounterDao coounterdao =new CounterDao(getCurrentContext());
		final ShopsDao shopsDao = new ShopsDao(getCurrentContext());
		final ShopDepartmentService shpDepService = new ShopDepartmentService(getCurrentContext());
		final ShopUsersService shopUsersService=new ShopUsersService(getCurrentContext());
		final ShopDepartmentDao depDao = new ShopDepartmentDao(getCurrentContext());
		final ShopDBService shopDbService = new ShopDBService(getCurrentContext());
		final ShopDBDao dbDao = new ShopDBDao(getCurrentContext());
		final ShopUsersDao shopUserDao=new ShopUsersDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
	      ApplicationContext currentCompanyContext=null;
		final UsersService usersService;
		Users user=null;
		
		ArrayList<ShopDepartment> shopDepartments = new ArrayList<ShopDepartment>();
		final CompanyService cmpnyService = new CompanyService(getCurrentContext());
		SyncQueue syncQueue = null;
		ShopDepartment shpDep = null;
		
		String rootPath = System.getProperty("catalina.home");
		String absolutePath = "/mrp_uploads/logos/";
		String rootDirectory = rootPath + "/webapps/" + absolutePath;
		
		File directory = new File(rootDirectory);
		if (! directory.exists()){
	        directory.mkdirs();
	       
	    }
		
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			
			Shops shop = mapper1.readValue(gson.toJson(jobject.get("itemData")), Shops.class);
			Customers cust =mapper1.readValue(gson.toJson(jobject.get("CustomerData")), Customers.class);
			
			
			shpService.beginTrans(DB_TXN_POINT);
			String client_name = shop.getDb_database();
			shop = shpService.saveShop(shop);
			
			if(cust.getId()==null){
				
			
			int value=coounterdao.getCounter("customers", "customers");
			cust.setId(value);
			}
			cust.setCode(shop.getCode());
			cust.setName(shop.getName());
			cust.setShop_id(shop.getId());
			
			if(shop.getIs_customer() == 1)
			{
			cust = customerService.saveItem(cust);
			}
			JsonObject jsonObject = parser1.parse(gson.toJson(jobject.get("Quetable"))).getAsJsonObject();
			String Qutable = jsonObject.toString();
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			if (shop.getId().toString() != "") {
				syncQueue = new SyncQueue();
				String tableName = shopsDao.getTable();
				String bathId = SyncQueueService.fungetSynQueId(tableName, shop.getId().toString());
				if (bathId != "") {
					
					syncQueue.setId(Integer.parseInt(bathId));
				}
				
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(shop.getId());
				
				SyncQueueService.save(syncQueue);
				
			}
			String departData = gson.toJson(jobject.get("depData1"));
			
			JsonArray departDataList = (JsonArray) parser1.parse(departData);
			String dptIds = "";
			
			if (request.getParameter("id") != null) {
				
				for (int i = 0; i < departDataList.size(); i++) {
					dptIds += (dptIds.isEmpty() ? " " : ",") + departDataList.get(i).getAsInt();
				}
				System.out.println(dptIds);
				
				shpDepService.DeleteDepartments(dptIds, shop.getId());
			}
			
			boolean is_hq = shop.getIs_hq();
			if (departDataList.size() != 0) {
				String tableName = depDao.getTable();
				for (int i = 0; i < departDataList.size(); i++) {
					shpDep = new ShopDepartment();
					if (shpDepService.DepData(departDataList.get(i).getAsInt(), shop.getId()) != null) {
						shpDep.setId(shpDepService.DepData(departDataList.get(i).getAsInt(), shop.getId()));
						
					}
					shpDep.setDepartment_id(departDataList.get(i).getAsInt());
					shpDep.setIs_deleted(0);
					shpDep.setShop_id(shop.getId());
					shpDep.setPublish_status(0);
					shopDepartments.add(shpDep);
					shpDep = shpDepService.saveShopDep(shpDep);
					if (shpDep.getId().toString() != "") {
						syncQueue = new SyncQueue();
						String bathId = SyncQueueService.fungetSynQueId(tableName, shpDep.getId().toString());
						if (bathId != "") {
							syncQueue.setId(Integer.parseInt(bathId));
							syncQueue.setCrudAction("U");
						}
						else {
							syncQueue.setCrudAction("C");
						}
						syncQueue.setShopId(QutableData.get("shopId").getAsInt());
						
						syncQueue.setOrigin(QutableData.get("origin").getAsString());
						
						syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
						
						syncQueue.setTableName(tableName);
						syncQueue.setRecordId(shpDep.getId());
						
						SyncQueueService.save(syncQueue);
						
					}
					
				}
				// shpDepService.save(shopDepartments);
			}
			if ((!is_hq) && file1 != null) {
				
				file1.transferTo(new File(rootDirectory + "logo_" + shop.getId() + ".jpg"));
				
			}
			
			if (is_hq) {
				Company company = cmpnyService.getCompanyByID(shop.getCompnyId());
				ShopDBSettings shopDb = new ShopDBSettings();
				if ((shpDepService.DbData(shop.getId())) != null) {
					shopDb.setId(shpDepService.DbData(shop.getId()));
				}
				shopDb.setDb_database(client_name);
				shopDb.setDb_user(company.getDatabaseUser());
				shopDb.setDb_password(company.getDatabasePassword());
				shopDb.setShop_id(shop.getId());
				shopDb.setDescription("test");
				shopDb.setIs_hq(1);
				shopDb.setDb_server("localhost");
				shopDb.setDb_server(company.getDatabaseServer());
				
				// shopDbService.save(shopDb);
				shopDb = shopDbService.saveShopDb(shopDb);
				String tableName = dbDao.getTable();
				if (shopDb.getId().toString() != "") {
					syncQueue = new SyncQueue();
					String bathId = SyncQueueService.fungetSynQueId(tableName, shopDb.getId().toString());
					if (bathId != "") {
						syncQueue.setId(Integer.parseInt(bathId));
					}
					syncQueue.setShopId(QutableData.get("shopId").getAsInt());
					syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
					syncQueue.setOrigin(QutableData.get("origin").getAsString());
					
					syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
					
					syncQueue.setTableName(tableName);
					syncQueue.setRecordId(shopDb.getId());
					
					SyncQueueService.save(syncQueue);
					
				}
				
				//Save Data to shop_users:
			   
				user = getCurrentContext().getUser();
				ShopUsers shopUser=new ShopUsers();
				if ((shopUsersService.isShopUserExist(shop.getId())) != null) {
					shopUser.setId(shopUsersService.isShopUserExist(shop.getId()));
				}
				shopUser.setShop_id(shop.getId());
				shopUser.setUser_group_id(user.getUserGroupId());
				shopUser.setUser_id(user.getId());
				
				shopUser=shopUsersService.saveShopUser(shopUser);
			    tableName = shopUserDao.getTable();
				if (shopDb.getId().toString() != "") {
					syncQueue = new SyncQueue();
					String bathId = SyncQueueService.fungetSynQueId(tableName, shopUser.getId().toString());
					if (bathId != "") {
						syncQueue.setId(Integer.parseInt(bathId));
					}
					syncQueue.setShopId(QutableData.get("shopId").getAsInt());
					syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
					syncQueue.setOrigin(QutableData.get("origin").getAsString());
					
					syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
					
					syncQueue.setTableName(tableName);
					syncQueue.setRecordId(shopUser.getId());
					
					SyncQueueService.save(syncQueue);
					
				}
				
			}
			
			shpService.endTrans(DB_TXN_POINT);
			
		}
		catch (Exception e) {
			status = "error";
			e.printStackTrace();
			shpService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method:  saveShop in" + this + Throwables.getStackTraceAsString(e));
		}
		status = "status:" + status;
		return gson.toJson(status);
	}
	
	
	
	@RequestMapping(value = "editData")
	public void getPO_hdrList(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		String[] combo_content_id = request.getParameterValues("id");
		final CustomersDao cust =new CustomersDao(getCurrentContext());
	
		JsonArray list = cust.getCustomerDataByShopId(Integer.parseInt(combo_content_id[0]));
		JsonObject jsonResponse = new JsonObject();

		
		jsonResponse.add("data", list);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}
	
	
	
	@RequestMapping(value = "getShopDataById")
	public void getShopDataById(HttpServletRequest request , HttpServletResponse response) {
	
		JsonArray data1, data2, data3, imagPath,customerData;
		final ShopDepartmentService shpDepSrvice = new ShopDepartmentService(getCurrentContext());

		
		int id;
		
		
		
		try {
			id = Integer.parseInt(request.getParameter("shopId"));
			data1 = getService().getShopDataById(id);
			data2 = getService().getAreaByShopId(id);
			data3 = shpDepSrvice.getDepdataByShopId(id);
			imagPath = getService().getImagePath(id);
			
			
			JsonObject jsonResponse = new JsonObject();
			
			
			jsonResponse.add("data1", data1);
			jsonResponse.add("data2", data2);
			jsonResponse.add("data3", data3);
			jsonResponse.add("imagPath", imagPath);
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getTableRowsAsJson " + Throwables.getStackTraceAsString(e));
		}
		
	}
	
	@RequestMapping(value = "getAreaDropDown")
	public void getAreaDropDown(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		JsonObject jObj = new JsonObject();
		final AreaCodeService areaCodeService = new AreaCodeService(getCurrentContext());
		JsonArray areaList = areaCodeService.getDropdownArray();
		jObj.add("areaList", areaList);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jObj.toString());
		
	}
	
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final ShopsDao shopDao = new ShopsDao(getCurrentContext());
		final ShopsService shopService = new ShopsService(getCurrentContext());
		final ShopDepartmentService shopDepartmentService = new ShopDepartmentService(getCurrentContext());
		final ShopDepartmentDao shopDepDao=new ShopDepartmentDao(getCurrentContext());
		final ShopDBService shopDbService = new ShopDBService(getCurrentContext());
		
		final String DB_TXN_POINT = "shopService";
		final String id = request.getParameter("id");
		
		shopService.beginTrans(DB_TXN_POINT);
		try {
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = shopService.delete(id);
			if (is_deleted == 0) {
				response.getWriter().print("0");
			}
			else {
				is_sync = SyncQueueService.updateSyncQueue(id, shopDao.getTable());
				shopDepartmentService.deleteSyncQue(shopDepDao.getTable(),Integer.parseInt(id));
				is_deleted = shopDepartmentService.delete(id);
				is_deleted = shopDbService.delete(id);
				
				response.getWriter().print("1");
			}
			shopService.endTrans(DB_TXN_POINT);
			
		}
		catch (Exception e) {
			shopService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}
	
	@RequestMapping(value = "deleteLogo")
	public void deleteBgImage(HttpServletRequest request , HttpServletResponse response)
			throws Exception {
	
		
		String bg_img = request.getParameter("imgName");
		String rootPath = System.getProperty("catalina.home");
		String absolutePath = "/mrp_uploads/logos/";
		String rootDirectory = rootPath + "/webapps/" + absolutePath;
		
		try {
			
			File directory = new File(rootDirectory);
			File[] files = directory.listFiles();
			for (File f : files) {
				if (f.getName().equals(bg_img)) {
					f.delete();
				}
			}
			
			response.getWriter().print(1);
			
		}
		catch (Exception e) {
			response.getWriter().print(0);
			e.printStackTrace();
			logger.error("Method:  deleteLogo in" + this + Throwables.getStackTraceAsString(e));
		}
		
	}
	
	@RequestMapping(value = "ShopsReport")
	public ModelAndView generateExcel(@ModelAttribute Shops shops , HttpServletRequest request ,
			HttpServletResponse response) throws Exception {
	
		final ShopsService shopsService = new ShopsService(getCurrentContext());
		
		try {
			
			shops.setShopsExcelData(shopsService.getExcelData());
		}
		catch (Exception e) {
			
			throw e;
		}
	
		
		return new ModelAndView("shopsClassView", "invoiceData", shops);
	}
	
}
