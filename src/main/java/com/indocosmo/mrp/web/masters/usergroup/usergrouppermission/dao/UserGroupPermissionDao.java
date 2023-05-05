package com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.model.UserGroupPermission;


public class UserGroupPermissionDao extends GeneralDao<UserGroupPermission> implements IUserGroupPermissionDao{

	public UserGroupPermissionDao(ApplicationContext context) {
		super(context);
	}

	@Override
	public UserGroupPermission getNewModelInstance() {
		// TODO Auto-generated method stub
		return new UserGroupPermission();
	}

	@Override
	public String getTable() {
		// TODO Auto-generated method stub
		return "mrp_user_group_functions";
	}
	
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		final String sql="SELECT * FROM " + getTable();

		return getTableRowsAsJson(sql);
	}
	
	@Override
	public Integer delete(String  where) throws Exception {

		final String sql="DELETE FROM " + getTable()+ " WHERE user_group_id=" + where+	";";
		Integer is_deleted = 0;

		beginTrans();
		try{

			is_deleted = executeSQL(sql);
			endTrans();
		}catch (Exception e){

			rollbackTrans();
			throw e;
		}
		return is_deleted;

	}
	
	@Override
	public List<UserGroupPermission> getList(String where) throws Exception {
		String SQL="SELECT * from "+ getTable() + " WHERE " + where + " ";

		return buildItemList(SQL);
	}

}
