package com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.dao.UserGroupPermissionDao;
import com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.model.UserGroupPermission;


public class UserGroupPermissionService extends GeneralService<UserGroupPermission,UserGroupPermissionDao> implements IUserGroupPermissionService{

	
	private UserGroupPermissionDao userGroupPermissionDao;
	
	public UserGroupPermissionService(ApplicationContext context) {
		super(context);
		userGroupPermissionDao=new UserGroupPermissionDao(getContext());
	}
	@Override
	public UserGroupPermissionDao getDao() {
		return userGroupPermissionDao;
	}
}
