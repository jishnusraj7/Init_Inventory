package com.indocosmo.mrp.web.masters.companyprofile.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;

public class CompanyProfileDao extends GeneralDao<CompanyProfile> implements ICompanyProfileDao {
	
	/**
	 * @param context
	 */
	public CompanyProfileDao(ApplicationContext context) {
	
		super(context);
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance() */
	@Override
	public CompanyProfile getNewModelInstance() {
	
		return new CompanyProfile();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable() */
	@Override
	protected String getTable() {
	
		return "company_profile";
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(com.indocosmo.mrp
	 * .web.core.base.model.GeneralModelBase) */
	@Override
	public void save(CompanyProfile item) throws Exception {
	
		super.update(item);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson() */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
	
		final String sql = "SELECT * FROM " + getTable() + " WHERE (is_deleted=0 OR is_deleted IS NULL)";
		
		return getTableRowsAsJson(sql);
	}
	
}
