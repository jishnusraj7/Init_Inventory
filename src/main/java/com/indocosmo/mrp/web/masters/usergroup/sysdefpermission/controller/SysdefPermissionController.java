package com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.dao.SysdefPermissionDao;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.service.SysdefPermissionService;
import com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.model.UserGroupPermission;
import com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.service.UserGroupPermissionService;


@Controller
@RequestMapping("/sysdefpermission")
public class SysdefPermissionController extends ViewController<SysdefPermission,SysdefPermissionDao,SysdefPermissionService> {

	

	@Override
	public SysdefPermissionService getService() {
		return new SysdefPermissionService(getCurrentContext());
	}
	
	@RequestMapping(value = "permission")
	public void permission(HttpServletResponse response,HttpServletRequest request) throws Exception {
		
		final String userGroupId=request.getParameter("userGroupId");
		final String cond = "user_group_id="+userGroupId;
	
		SysdefPermissionService sysdefPermissionService=new SysdefPermissionService(getCurrentContext());
		List<SysdefPermission> sysdefPermssionList=sysdefPermissionService.getList();
		
		UserGroupPermissionService userGroupPermissionservice=new UserGroupPermissionService(getCurrentContext());
		List<UserGroupPermission> userGroupPermissionList=userGroupPermissionservice.getList(cond);
		
		
		for(SysdefPermission systemPermission : sysdefPermssionList){
			
			systemPermission.setCanDelete(false);
			systemPermission.setCanEdit(false);
			systemPermission.setCanExecute(false);
			systemPermission.setCanView(false);
			systemPermission.setCanExport(false);
			systemPermission.setCanAdd(false);
			systemPermission.setUserGroupId(Integer.parseInt(userGroupId));
			
			
		}
		
		
		HashMap<String,SysdefPermission> permissions=new HashMap<String, SysdefPermission>();
		for(SysdefPermission systemPermission : sysdefPermssionList){

			for(UserGroupPermission userGroupPermission : userGroupPermissionList){
				if(systemPermission.getId() == userGroupPermission.getSysdefPermissionId()){
					systemPermission.setCanDelete(userGroupPermission.getCanDelete());
					systemPermission.setCanEdit(userGroupPermission.getCanDelete());
					systemPermission.setCanExecute(userGroupPermission.getCanExecute());
					systemPermission.setCanView(userGroupPermission.getCanView());
					systemPermission.setCanExport(userGroupPermission.getCanExport());
					systemPermission.setCanAdd(userGroupPermission.getCanAdd());
					systemPermission.setUsergrouppermissionId(userGroupPermission.getId());
					
					systemPermission.setCreated_at(userGroupPermission.getCreated_at());
					systemPermission.setCreated_by(userGroupPermission.getCreated_by());
				}
			}	
			permissions.put(systemPermission.getCode(), systemPermission);
	}
		Gson g= new Gson();
		System.out.println(g.toJson(sysdefPermssionList));
		
		 
		
		
		/*JsonArray permission = permissions;*/
	/*	JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.addProperty("permisssion", g.toJson(sysdefPermssionList));
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");*/
		response.getWriter().print(g.toJson(sysdefPermssionList).toString());

	
	}
	

}