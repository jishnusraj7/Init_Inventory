package com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.dao.SysdefPermissionDao;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;


public class SysdefPermissionService extends GeneralService<SysdefPermission,SysdefPermissionDao> implements ISysdefPermissionService{
	
	
	private SysdefPermissionDao sysdefPermissionDao;
	public SysdefPermissionService(ApplicationContext context) {
		super(context);
		sysdefPermissionDao=new SysdefPermissionDao(getContext());
		
	}
	@Override
	public SysdefPermissionDao getDao() {
		return sysdefPermissionDao;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	
}
