package com.indocosmo.mrp.web.masters.usergroup.controller;



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

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.subheaderMenus;
import com.indocosmo.mrp.web.core.base.controller.MasterBaseController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.dao.UserGroupDao;
import com.indocosmo.mrp.web.masters.usergroup.model.UserGroup;
import com.indocosmo.mrp.web.masters.usergroup.service.UserGroupService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.service.UserGroupPermissionService;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/usergroup")
public class UserGroupController extends MasterBaseController<UserGroup,UserGroupDao,UserGroupService>{
	
	
	
	
	@Override
	public UserGroupService getService() {
		return new UserGroupService(getCurrentContext());
	}
	

	

	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute UserGroup userGroup,HttpSession session,Model model)
			throws Exception {
      final CurrentUserPermissionService userPermissionService=new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_UG");
		model.addAttribute("permission", permission);
		//HashMap<Integer, String> subHeaders = subheaderMenus.getSubheadermenus();
		//System.out.println(subHeaders);
		//model.addAttribute("subheader",subHeaders);
		model.addAttribute("Settings",true);
		model.addAttribute("Users",true);
		
		model.addAttribute("usergroupclass",true);
		
		
		return "/usergroup/list";
	}
	
	
	@RequestMapping(value = "enumsubheader")
	public void getSub_hdrList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final ArrayList enumList = new ArrayList();


		subheaderMenus[] list= subheaderMenus.values();
		for(int i=0;i< list.length;i++){
			
			enumList.add(list[i].getSubheaderMenusId(),list[i].getSubheaderMenusName());
		}
		String json = new Gson().toJson(enumList);
		
		response.getWriter().print(json);
	
	}
	
	@Override
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		final UserGroupService userGroupService=new UserGroupService(getCurrentContext());
		final UserGroupPermissionService userGroupPermissionService=new UserGroupPermissionService(getCurrentContext());
		final SyncQueueService SyncQueueService=new SyncQueueService(getCurrentContext());
		final UserGroupDao userGroupDao=new UserGroupDao(getCurrentContext());

		
		final String DB_TXN_POINT = "userGroupService";
		final String id = request.getParameter("id");
		userGroupService.beginTrans(DB_TXN_POINT);
		try {
			
			Integer is_deleted = 0;
			Integer is_sync=0;
			is_deleted = userGroupService.delete(id);
			
			if (is_deleted == 0) {
				response.getWriter().print("0");
			}else{
				is_deleted = 0;
				is_sync=SyncQueueService.updateSyncQueue(id,userGroupDao.getTable());
				userGroupService.deleteSyncQue(Integer.parseInt(id));
				is_deleted = userGroupPermissionService.delete(id);
				

				response.getWriter().print("1");
				
				
				if (is_deleted == 0) {
					response.getWriter().print("0");
				}else{
					
					response.getWriter().print("1");
				}
				
			}

			userGroupService.endTrans(DB_TXN_POINT);
		} catch (Exception e) {

			userGroupService.rollbackTrans(DB_TXN_POINT);
			throw e;
		}

	}

	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response,Model model) throws Exception{

		final CounterService counterService = new CounterService(getCurrentContext()); 

		String CounterWithPrefix = counterService.getNextCounterwithPrefix("usergroup", "usergroup");

		response.getWriter().print(CounterWithPrefix);
	}
	
	
	@RequestMapping(value="/saveUsrGrp", method=RequestMethod.POST) 
	public void saveUsrGrp(@RequestBody String Quetable,HttpSession session,HttpServletResponse response,HttpServletRequest request) throws Exception
	{
		final UserGroupService userGroupService=new UserGroupService(getCurrentContext());
		final UserGroupDao userGroupDao=new UserGroupDao(getCurrentContext());
		final SyncQueueService SyncQueueService=new SyncQueueService(getCurrentContext());

		JsonParser parser1 = new JsonParser();
		final String DB_TXN_POINT = "userGroupService";
		Gson gson = new Gson();
		UserGroup usGp=null;
		SyncQueue syncQueue=null;
		userGroupService.beginTrans(DB_TXN_POINT);
		
		try
		{
			usGp = super.saveData(request);
			
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable=gson.toJson(jsonObject.get("Quetable"));
			 JsonObject QutableData  = (JsonObject) parser1.parse(Qutable);
			
			System.out.println(QutableData);
			if(usGp.getId().toString()!="")	
			{
				syncQueue=new SyncQueue();
				String tableName=userGroupDao.getTable();
				String Syn_Id=SyncQueueService.fungetSynQueId(tableName,usGp.getId().toString());
				if(Syn_Id!="")
				{
					syncQueue.setId(Integer.parseInt(Syn_Id));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(usGp.getId());
				
				SyncQueueService.save(syncQueue);
				
				
			}
			
			
			
			
			
			response.getWriter().print("1");
			
			userGroupService.endTrans(DB_TXN_POINT);	
		}
		catch (Exception e) {
			response.getWriter().print("0");
			userGroupService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveUsrGrp"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
		
		
		
	}


	
}
