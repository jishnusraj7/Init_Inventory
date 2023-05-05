package com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.dao.UserGroupPermissionDao;
import com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.model.UserGroupPermission;
import com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.service.UserGroupPermissionService;
import com.indocosmo.mrp.web.masters.users.model.Users;

@Controller
@RequestMapping("/usergrouppermission")
public class UserGroupPermissionController extends ViewController<UserGroupPermission,UserGroupPermissionDao,UserGroupPermissionService> {
	
	
	
	@Override
	public UserGroupPermissionService getService() {
		return  new UserGroupPermissionService(getCurrentContext());
	}
	
	
	@RequestMapping(value = "savePermission")
	public void save(@RequestBody String groupPermissions,HttpSession session,HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		final UserGroupPermissionService userGroupPermissionService=new UserGroupPermissionService(getCurrentContext());
		final SyncQueueService syncQueueService=new SyncQueueService(getCurrentContext());
       final UserGroupPermissionDao userGroupPermissionDao=new UserGroupPermissionDao(getCurrentContext());
		final ArrayList<SyncQueue> SyncQueueArrayList = new ArrayList<SyncQueue>();
		final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
		final String dateTime = currentDateFormat.getCurrentDateTime(); 
		Users userDtls = (Users) httpSession.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
		
		
       String origin=request.getParameter("origin");
       String shopId=request.getParameter("shopId");
       String curdAction=request.getParameter("curdAction");
       String syncNow=request.getParameter("syncNow");

       
       
       final String DB_TXN_POINT = "userGroupPermissionService";
		UserGroupPermission up=null;
		SyncQueue syncQueueItem=null;
		
		
		
		
		JSONParser parser = new JSONParser();
		 JSONObject json = (JSONObject) parser.parse(groupPermissions);
		 System.out.println(json);
		 final ArrayList arrList =(ArrayList) json.get("groupPermissions");
		 System.out.println(arrList);
	       final ArrayList<UserGroupPermission> UserGroupPermissionList = new ArrayList<UserGroupPermission>();

		
			 System.out.println(arrList.size());
			  for(int i=0;i<arrList.size();i++)
		      {
				 UserGroupPermission usrGrpPrmsn = new UserGroupPermission();
				  JSONObject obj=(JSONObject) arrList.get(i);
				  
					usrGrpPrmsn.setCanView(Boolean.parseBoolean(obj.get("canView").toString()));
					usrGrpPrmsn.setCanAdd(Boolean.parseBoolean(obj.get("canAdd").toString()));
					usrGrpPrmsn.setCanEdit(Boolean.parseBoolean(obj.get("canEdit").toString()));
					usrGrpPrmsn.setCanDelete(Boolean.parseBoolean(obj.get("canDelete").toString()));
					usrGrpPrmsn.setCanExport(Boolean.parseBoolean(obj.get("canExport").toString()));
					usrGrpPrmsn.setCanExecute(Boolean.parseBoolean(obj.get("canExecute").toString()));
					usrGrpPrmsn.setUserGroupId(Integer.parseInt(obj.get("userGroupId").toString()));
					usrGrpPrmsn.setSysdefPermissionId(Integer.parseInt(obj.get("id").toString()));
					
					usrGrpPrmsn.setCreated_at(dateTime);
					
					usrGrpPrmsn.setCreated_by(userDtls.getId());
				
					
					
					
					
					if(obj.get("usergrouppermissionId")!=null)
					{
					usrGrpPrmsn.setId(Integer.parseInt(obj.get("usergrouppermissionId").toString()));
					
                   usrGrpPrmsn.setUpdated_at(dateTime);
					
					usrGrpPrmsn.setUpdated_by(userDtls.getId());      
					
                    usrGrpPrmsn.setCreated_at(obj.get("created_at").toString());
					
					usrGrpPrmsn.setCreated_by(Integer.parseInt(obj.get("created_by").toString()));
					}
					
						
					
					
					UserGroupPermissionList.add(usrGrpPrmsn);

		 }
			  
			 
			  
			  
			  
			  
			  
			  
			  
			  userGroupPermissionService.beginTrans(DB_TXN_POINT);
  
	
		
		try{
			userGroupPermissionService.save(UserGroupPermissionList);
			
			if(UserGroupPermissionList.size()!=0)
			{
				for(int i=0;i<UserGroupPermissionList.size();i++)
				{
					syncQueueItem=new SyncQueue();
					String tableName=userGroupPermissionDao.getTable();
					String permId=syncQueueService.fungetSynQueId(tableName,UserGroupPermissionList.get(i).getId().toString());
					if(permId!="")
					{
						 syncQueueItem.setId(Integer.parseInt(permId));
					}
					
					
					syncQueueItem.setShopId(Integer.parseInt(shopId));
				     syncQueueItem.setCrudAction(curdAction);
				     syncQueueItem.setOrigin(origin);
				     syncQueueItem.setSyncStatus(Integer.parseInt(syncNow));
					
				     syncQueueItem.setTableName(userGroupPermissionDao.getTable());
				     syncQueueItem.setRecordId(UserGroupPermissionList.get(i).getId());
				     SyncQueueArrayList.add(syncQueueItem);
				     
				     
					
					
				}
				
				syncQueueService.save(SyncQueueArrayList);
			}
			
			
			
			
			
			
			
			userGroupPermissionService.endTrans(DB_TXN_POINT);	

		}catch (Exception e) {
			
			e.printStackTrace();
			userGroupPermissionService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: savePermission in"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}		 
		
	}
	
	
	@RequestMapping(value = "userspermissions")
	public void getUsersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		final UserGroupPermissionService userGroupPermissionService=new UserGroupPermissionService(getCurrentContext());
		final String groupid = request.getParameter("userGroupId");
		final String cond = "user_group_id="+groupid;
		List<UserGroupPermission> list = userGroupPermissionService.getList(cond);
		String json = new Gson().toJson(list);
		
		response.getWriter().print(json);
	}


	
}
