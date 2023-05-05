package com.indocosmo.mrp.web.masters.usergroup.userpermission.model;

import java.util.HashMap;

import javax.persistence.Entity;

import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;

@Entity
public class UserPermission {

	private HashMap<String, SysdefPermission> userPermisssion = new HashMap<String, SysdefPermission>();

	/**
	 * @return the userPermisssion
	 */
	public HashMap<String, SysdefPermission> getUserPermisssion() {
		return userPermisssion;
	}

	/**
	 * @param userPermisssion the userPermisssion to set
	 */
	public void setUserPermisssion(HashMap<String, SysdefPermission> userPermisssion) {
		this.userPermisssion = userPermisssion;
	}
	
	
}
