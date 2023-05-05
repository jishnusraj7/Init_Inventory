package com.indocosmo.mrp.web.masters.usergroup.userpermission.service;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;


public class CurrentUserPermissionService {

	/**
	 * @param session
	 * @param keyCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SysdefPermission getCurrentUserPermission(HttpSession session,String keyCode) {
		
	
		HashMap<String, SysdefPermission> permission = (HashMap<String, SysdefPermission>) session.getAttribute("userPermissions");
		SysdefPermission sysDefPermisssion=null;
		if(permission.containsKey(keyCode)){
			sysDefPermisssion = permission.get(keyCode);
		}
		
		return sysDefPermisssion;
	}
	
}
