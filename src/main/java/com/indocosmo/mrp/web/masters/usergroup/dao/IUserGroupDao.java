package com.indocosmo.mrp.web.masters.usergroup.dao;


import com.indocosmo.mrp.web.core.base.dao.IMasterBaseDao;
import com.indocosmo.mrp.web.masters.usergroup.model.UserGroup;


public interface IUserGroupDao extends IMasterBaseDao<UserGroup> {
	public Integer deleteSyncQue(Integer id) throws Exception;
}
