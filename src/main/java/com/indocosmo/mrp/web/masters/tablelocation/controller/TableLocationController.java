package com.indocosmo.mrp.web.masters.tablelocation.controller;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.indocosmo.mrp.web.masters.tablelocation.dao.TableLocationDao;
import com.indocosmo.mrp.web.masters.tablelocation.model.TableLocation;
import com.indocosmo.mrp.web.masters.tablelocation.service.TableLocationService;
import com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.service.TableImageService;
import com.indocosmo.mrp.web.masters.tablelocation.servingtables.service.ServingTablesService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping(value = "/tablelocation")
public class TableLocationController extends ViewController<TableLocation, TableLocationDao, TableLocationService> {
	
	public static final Logger logger = Logger.getLogger(TableLocationController.class);
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public TableLocationService getService() {
	
		return new TableLocationService(getCurrentContext());
	}
	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute TableLocation tableLocation , HttpSession session , Model model)
			throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_SERV_TBL");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("tablelocationclass",true);
		model.addAttribute("Serving",true);
		return "/tablelocation/list";
	}
	
	@RequestMapping(value = "/getImageData")
	public void getDataToEditChoice(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		JsonObject jsonResponse = new JsonObject();
		
		final TableImageService tblSrvice = new TableImageService(getCurrentContext());
		JsonArray tbleImages = tblSrvice.getdpData("tblImages");
		
		jsonResponse.add("tbleImages", tbleImages);
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
		
	}
	
	@RequestMapping(value = "/getDataQue")
	public void getDataQue(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
	}
	
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("serving_table_location",
				"serving_table_location");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	@RequestMapping(value = "/savetblLocation")
	protected @ResponseBody String saveItem(MultipartHttpServletRequest request ,
			@RequestParam(value = "data", required = true) String completeData) throws Exception {
	
		String status = "success";
		Gson gson = new Gson();
		JsonParser parser1 = new JsonParser();
		SyncQueue syncQueue = new SyncQueue();
		ObjectMapper mapper1 = new ObjectMapper();
		final String DB_TXN_POINT = "tblLocationService";
		
		final TableLocationService tblLocationService = new TableLocationService(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final TableLocationDao tblLocationDao = new TableLocationDao(getCurrentContext());
		JsonObject jobject = parser1.parse(completeData).getAsJsonObject();
		try {
			
			TableLocation tblLoc = mapper1.readValue(gson.toJson(jobject.get("itemData")), TableLocation.class);
			JsonObject jsonObject = parser1.parse(gson.toJson(jobject.get("Quetable"))).getAsJsonObject();
			String Qutable = jsonObject.toString();
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			tblLocationService.beginTrans(DB_TXN_POINT);
			tblLoc = tblLocationService.saveItem(tblLoc);
			
			if (tblLoc.getId().toString() != "") {
				
				syncQueue = new SyncQueue();
				String tableName = tblLocationDao.getTable();
				String bathId = SyncQueueService.fungetSynQueId(tableName, tblLoc.getId().toString());
				if (bathId != "") {
					
					syncQueue.setId(Integer.parseInt(bathId));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(tblLoc.getId());
				
				SyncQueueService.save(syncQueue);
				
			}
			tblLocationService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			status = "error";
			e.printStackTrace();
			tblLocationService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method:  saveShop in" + this + Throwables.getStackTraceAsString(e));
		}
		status = "status:" + status;
		return gson.toJson(status);
	}
	
	@RequestMapping(value = "saveBgImage")
	protected @ResponseBody void saveBgImage(MultipartHttpServletRequest request ,
			@RequestParam(value = "imgFile", required = false) MultipartFile file1 ,
			@RequestParam(value = "data", required = false) String completeData , HttpServletResponse reponse)
			throws Exception {
	
		final String DB_TXN_POINT = "tblImageService";
		
		final TableLocationService tblLocationService = new TableLocationService(getCurrentContext());
		
		String rootPath = System.getProperty("catalina.home");
		String absolutePath = "/mrp_uploads/bgImages/";
		String rootDirectory = rootPath + "/webapps/" + absolutePath;
		
		File directory = new File(rootDirectory);
		if (! directory.exists()){
	        directory.mkdirs();
	       
	    }
		
		try {
			
			String uniqueID = UUID.randomUUID().toString();
			int id = Integer.parseInt(request.getParameter("id"));
			tblLocationService.beginTrans(DB_TXN_POINT);
			if (file1 != null) {
				
				file1.transferTo(new File(rootDirectory + uniqueID + "_img.jpg"));
				
			}
			JsonArray json = tblLocationService.updateBgImage(id, absolutePath + uniqueID + "_img.jpg");
			JsonObject obj = new JsonObject();
			
			obj.add("newBgImage", json);
			reponse.getWriter().print(obj);
			tblLocationService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			e.printStackTrace();
			tblLocationService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method:  saveItem in" + this + Throwables.getStackTraceAsString(e));
		}
		
	}
	
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final TableLocationDao tableLocDao = new TableLocationDao(getCurrentContext());
		final TableLocationService tableLocService = new TableLocationService(getCurrentContext());
		final ServingTablesService servTableService = new ServingTablesService(getCurrentContext());
		
		final String DB_TXN_POINT = "tableLocService";
		final String id = request.getParameter("id");
		
		tableLocService.beginTrans(DB_TXN_POINT);
		try {
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = tableLocService.delete(id);
			if (is_deleted == 0) {
				response.getWriter().print("0");
			}
			else {
				is_sync = SyncQueueService.updateSyncQueue(id, tableLocDao.getTable());
				is_deleted = servTableService.delete(id);
				
				response.getWriter().print("1");
			}
			tableLocService.endTrans(DB_TXN_POINT);
			
		}
		catch (Exception e) {
			tableLocService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}
	
	@RequestMapping(value = "deleteBgImage")
	public void deleteBgImage(@RequestBody String Quetable , HttpServletRequest request , HttpServletResponse response)
			throws Exception {
	
		final String DB_TXN_POINT = "tblImageService";
		
		final TableLocationService tblLocationService = new TableLocationService(getCurrentContext());
		final TableLocationDao tableLocDao = new TableLocationDao(getCurrentContext());
		final SyncQueueService syncQueueService = new SyncQueueService(getCurrentContext());
		JsonParser parser1 = new JsonParser();
		Gson gson = new Gson();
		SyncQueue syncQueue = null;
		String bg_img = null;
		String rootPath = System.getProperty("catalina.home");
		String absolutePath = "/mrp_uploads/bgImages/";
		String rootDirectory = rootPath + "/webapps/" + absolutePath;
		
		File directory1 = new File(rootDirectory);
		if (! directory1.exists()){
	        directory1.mkdirs();
	       
	    }
		
		try {
			
			// TableImage
			// tblLoc=mapper1.readValue(gson.toJson(jobject.get("tableImgData")),
			// TableImage.class);
			
			int id = Integer.parseInt(request.getParameter("id"));
			tblLocationService.beginTrans(DB_TXN_POINT);
			
			bg_img = tblLocationService.getBgImage(id);
			File directory = new File(rootDirectory);
			File[] files = directory.listFiles();
			for (File f : files) {
				if (f.getName().equals(bg_img)) {
					f.delete();
				}
			}
			tblLocationService.deleteBgImage(id);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			
			syncQueue = new SyncQueue();
			String tableName = tableLocDao.getTable();
			String bathId = syncQueueService.fungetSynQueId(tableName, request.getParameter("id"));
			
			if (bathId != "") {
				syncQueue.setId(Integer.parseInt(bathId));
			}
			syncQueue.setShopId(QutableData.get("shopId").getAsInt());
			syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
			syncQueue.setOrigin(QutableData.get("origin").getAsString());
			syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
			syncQueue.setTableName(tableName);
			syncQueue.setRecordId(Integer.parseInt(request.getParameter("id")));
			
			syncQueueService.save(syncQueue);
			
			response.getWriter().print(1);
			tblLocationService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			e.printStackTrace();
			tblLocationService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method:  deleteBgImage in" + this + Throwables.getStackTraceAsString(e));
		}
		
	}
	
}
