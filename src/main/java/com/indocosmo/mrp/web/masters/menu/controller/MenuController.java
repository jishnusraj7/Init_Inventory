package com.indocosmo.mrp.web.masters.menu.controller;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.MasterBaseController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.menu.dao.MenuDao;
import com.indocosmo.mrp.web.masters.menu.menudepartment.dao.MenuDepartmentDao;
import com.indocosmo.mrp.web.masters.menu.menudepartment.model.MenuDepartment;
import com.indocosmo.mrp.web.masters.menu.menudepartment.service.MenuDepartmentService;
import com.indocosmo.mrp.web.masters.menu.model.Menu;
import com.indocosmo.mrp.web.masters.menu.service.MenuService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/menu")
public class MenuController extends MasterBaseController<Menu, MenuDao, MenuService> {

	@Override
	public MenuService getService() {
		
		return new MenuService(getCurrentContext());
	}

	
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response,HttpSession session,Model model) throws Exception{

		final CounterService counterService = new CounterService(getCurrentContext()); 

		String CounterWithPrefix = counterService.getNextCounterwithPrefix("menu", "menu");

		response.getWriter().print(CounterWithPrefix);
	}
	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute Menu Menu,HttpSession session,Model model)
			throws Exception {

		final CurrentUserPermissionService userPermissionService=new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_CRD");
		model.addAttribute("permission", permission);
		final CounterService counterService = new CounterService(getCurrentContext()); 


		String CounterWithPrefix = counterService.getNextCounterwithPrefix("menu", "menu");
		model.addAttribute("Settings",true);
		
		model.addAttribute("Products",true);
		model.addAttribute("menuclass",true);
		
		
		
		return "/menu/list";
	}
	
	
	@RequestMapping(value="/saveMenu", method=RequestMethod.POST) 
	public void saveSuppier(@RequestBody String completeData,HttpSession session,HttpServletResponse response,HttpServletRequest request) throws Exception
	{
		final MenuService menuService=new MenuService(getCurrentContext());
		final MenuDao menuDao=new MenuDao(getCurrentContext());
		final SyncQueueService SyncQueueService=new SyncQueueService(getCurrentContext());
		final MenuDepartmentService menuDepartmentService=new MenuDepartmentService(getCurrentContext());
		final MenuDepartmentDao menuDepartmentDao=new MenuDepartmentDao(getCurrentContext());
		ObjectMapper mapper = new ObjectMapper();
		JsonParser parser1 = new JsonParser();
		final String DB_TXN_POINT = "menuService";
		Gson gson = new Gson();
		Menu menu=null;
		SyncQueue syncQueue=null;
		MenuDepartment menudpt=null;

		final ArrayList<SyncQueue> SyncQueueArrayList = new ArrayList<SyncQueue>();
		final ArrayList<MenuDepartment> menudptArrayList= new ArrayList<MenuDepartment>();	
		menuService.beginTrans(DB_TXN_POINT);
		
		
		
		
		try
		{
			menu = super.saveData(request);
			
			
			JsonObject jobject = parser1.parse(completeData).getAsJsonObject();
			String Qutable=gson.toJson(jobject.get("Quetable"));
			JsonObject QutableData  = (JsonObject) parser1.parse(Qutable);
			
			
			
			
			
			//JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			//String Qutable=gson.toJson(jsonObject.get("Quetable"));
			// JsonObject QutableData  = (JsonObject) parser1.parse(Qutable);
			
		
			if(menu.getId().toString()!="")	
			{
				syncQueue=new SyncQueue();
				String tableName=menuDao.getTable();
				String bathId=SyncQueueService.fungetSynQueId(tableName,menu.getId().toString());
				if(bathId!="")
				{
					syncQueue.setId(Integer.parseInt(bathId));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(menu.getId());
				
				SyncQueueService.save(syncQueue);
				
				
			}
			String departData=gson.toJson(jobject.get("depData"));
			
			
			JsonArray departDataLuist = (JsonArray) parser1.parse(departData);
			String dptIds="";
			
     if(request.getParameter("id")!=null)
     {
			
			for(int i=0;i<departDataLuist.size();i++)
			{
				dptIds+=(dptIds.isEmpty() ? " ":",")+departDataLuist.get(i).getAsInt();
			}
			System.out.println(dptIds);
			
			menuDepartmentService.DeleteDepartments(dptIds,menu.getId());
     }		
			
		if(departDataLuist.size()!=0)
		{
			for(int i=0;i<departDataLuist.size();i++)
			{
				Integer dep_id=departDataLuist.get(i).getAsInt();
				 menudpt=new MenuDepartment();
				 
			
				 
				 
				 String shp_id=menuDepartmentService.getShopDptId(menu.getId().toString(),dep_id.toString());
				 
				 if(shp_id!="")
				 {
					 menudpt.setId(Integer.parseInt(shp_id)); 
				 }
				 
				 menudpt.setDepartment_id(dep_id);
				 menudpt.setMenu_id(menu.getId());
				 
				 
				 menudptArrayList.add(menudpt);
				
				
			}
			menuDepartmentService.save(menudptArrayList);
			
			for(int i=0;i<menudptArrayList.size();i++)
			{
				
				
			
				

				syncQueue=new SyncQueue();
				String tableName=menuDepartmentDao.getTable();
				String synId=SyncQueueService.fungetSynQueId(tableName,menudptArrayList.get(i).getId().toString());
				if(synId!="")
				{
					 syncQueue.setId(Integer.parseInt(synId));
				}
				
				
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
			     syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
			     syncQueue.setOrigin(QutableData.get("origin").getAsString());
			  
			     syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
			     syncQueue.setTableName(menuDepartmentDao.getTable());
			     syncQueue.setRecordId(menudptArrayList.get(i).getId());
			     SyncQueueArrayList.add(syncQueue);
			}
			
			SyncQueueService.save(SyncQueueArrayList);
			
			
			
		}	
			
			
			
			
			
			
			response.getWriter().print("1");
			
			menuService.endTrans(DB_TXN_POINT);	
		}
		catch (Exception e) {
			response.getWriter().print("0");
			menuService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveMenu"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
		
		
		
	}
	
	
	@Override
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final SyncQueueService SyncQueueService=new SyncQueueService(getCurrentContext());
		final MenuDao menuDao=new MenuDao(getCurrentContext());
		final MenuService menuService=new MenuService(getCurrentContext());
		final MenuDepartmentService menuDepartmentService=new MenuDepartmentService(getCurrentContext());
		final String DB_TXN_POINT = "MenuService";
		final String id = request.getParameter("id");
		
		menuService.beginTrans(DB_TXN_POINT);
		try {
			Integer is_deleted = 0;
			Integer is_sync=0;
			is_deleted = menuService.delete(id);
			if (is_deleted == 0 ) {
				response.getWriter().print("0");
			}
			else
			{
				is_sync=SyncQueueService.updateSyncQueue(id,menuDao.getTable());
				menuDepartmentService.deleteSyncQue(Integer.parseInt(id));
				is_deleted = menuDepartmentService.delete(id);
				
				response.getWriter().print("1");
			}
			menuService.endTrans(DB_TXN_POINT);
		
	}
		 catch (Exception e) {	
			 menuService.rollbackTrans(DB_TXN_POINT);
				logger.error("Method: delete in"+this+Throwables.getStackTraceAsString(e));
				throw new CustomException();
			}
	
	}
	
	
	@RequestMapping(value = "/editData")
	public void getModalData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int Id=Integer.parseInt(request.getParameter("id"));
		JsonObject jsonResponse = new JsonObject();
		
		final MenuService menuService=new MenuService(getCurrentContext());
		final MenuDepartmentService menuDepartmentService=new MenuDepartmentService(getCurrentContext());
		
		JsonArray editDet=menuService.getEditDetails(Id);
		JsonArray deptData=menuDepartmentService.getEditDetails(Id);
		jsonResponse.add("editDet",editDet.get(0));
		jsonResponse.add("deptData",deptData);
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());	
	}
	

	
	
}
