package com.indocosmo.mrp.web.masters.usergroup.service;

import java.util.ArrayList;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.model.RefereneceModelBase;
import com.indocosmo.mrp.web.core.base.service.MasterBaseService;
import com.indocosmo.mrp.web.masters.usergroup.dao.UserGroupDao;
import com.indocosmo.mrp.web.masters.usergroup.model.UserGroup;
import com.indocosmo.mrp.web.masters.users.dao.UsersDao;



public class UserGroupService extends MasterBaseService<UserGroup,UserGroupDao> implements IUserGroupService{

	private UserGroupDao userGroupDao;
	private UsersDao usersDao;
	
	public UserGroupService(ApplicationContext context) {
		super(context);
		userGroupDao=new UserGroupDao(getContext());
		usersDao=new UsersDao(getContext());
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
		public ArrayList<RefereneceModelBase> getReferenceTable() throws Exception {
		ArrayList<RefereneceModelBase> referenceTableDetails = new ArrayList<RefereneceModelBase>();
		ArrayList<String> referenceTables = new ArrayList<String>();
		referenceTables.add(usersDao.getTable());
		
		for (String table : referenceTables) {
			
			RefereneceModelBase referenceModel=new RefereneceModelBase();
			referenceModel.setRefrenceTable(table);
			referenceModel.setRefrenceKey("user_group_id");
			referenceTableDetails.add(referenceModel);
		}
			return referenceTableDetails;
		}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao()
	 */
	@Override
	public UserGroupDao getDao() {
		return userGroupDao;
	}
	
	@Override
	public Integer delete(String id) throws Exception {
		
		final String where = "id=" + id;
		Integer is_deleted = 1;
		
		ArrayList<RefereneceModelBase> referenceTables = getReferenceTable();
		int rowCount = 0;
		
		if (referenceTables != null) {
			for (RefereneceModelBase table : referenceTables) {
				
				final String wherePart = ""+table.getRefrenceKey()+"="+ id +" AND (is_deleted = 0	OR is_deleted IS NULL);";
				rowCount = rowCount + userGroupDao.getReferenceRowCount(table.getRefrenceTable(),wherePart);
			}
		}
		
		if (rowCount != 0) {
			
			is_deleted = 0;
		}
		
		if (referenceTables == null || rowCount == 0) {
			
			userGroupDao.delete(where);
		}
		
		return is_deleted;
	}
	
	
	public Integer deleteSyncQue(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return userGroupDao.deleteSyncQue(id);
	}
	
	
}
