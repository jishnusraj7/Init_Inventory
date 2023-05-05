package com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.dao;

import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;



public class SysdefPermissionDao extends GeneralDao<SysdefPermission> implements ISysdefPermissionDao {

	public SysdefPermissionDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public SysdefPermission getNewModelInstance() {
		return new SysdefPermission();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {
		return "vw_mrp_system_functions";
	}
	
/* @Override
	public JsonArray getTableRowsAsJson() throws Exception {
		final String sql="SELECT * FROM " + getTable() + " WHERE (is_system=0 OR is_system IS NULL) AND (is_deleted=0 OR is_deleted IS NULL)"+" GROUP BY code,`name`,system_group";

		return getTableRowsAsJson(sql);
		
		
	}*/

	@Override
	public List<SysdefPermission> getList(String where) throws Exception {
		String SQL="SELECT * from "+ getTable() + " WHERE " + where + " ";

		return buildItemList(SQL);
	}
	
	@Override
	public List<SysdefPermission> getList() throws Exception {
	
		String SQL="SELECT * from "+ getTable() + "";

		return buildItemList(SQL);
	}

	

}
