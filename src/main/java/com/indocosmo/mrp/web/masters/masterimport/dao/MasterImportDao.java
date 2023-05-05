package com.indocosmo.mrp.web.masters.masterimport.dao;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.masterimport.model.MasterImport;

public class MasterImportDao extends GeneralDao<MasterImport> implements IMasterImportDao {

	public MasterImportDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MasterImport getNewModelInstance() {

		return new MasterImport();
	}

	@Override
	protected String getTable() {
		// TODO Auto-generated method stub
		return null;
	}
}
