package com.indocosmo.mrp.web.masters.usergroup.service;

import com.indocosmo.mrp.web.core.base.service.IMasterBaseService;
import com.indocosmo.mrp.web.masters.usergroup.dao.UserGroupDao;
import com.indocosmo.mrp.web.masters.usergroup.model.UserGroup;

public interface IUserGroupService extends IMasterBaseService<UserGroup,UserGroupDao>{
	public Integer deleteSyncQue(Integer id) throws Exception;
}
