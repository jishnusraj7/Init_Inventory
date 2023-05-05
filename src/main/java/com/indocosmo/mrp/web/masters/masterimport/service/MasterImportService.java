package com.indocosmo.mrp.web.masters.masterimport.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.masterimport.dao.MasterImportDao;
import com.indocosmo.mrp.web.masters.masterimport.model.MasterImport;

public class MasterImportService extends GeneralService<MasterImport,MasterImportDao> implements IMasterImportService {
	private MasterImportDao masterImportDao;
	public MasterImportService(ApplicationContext context) {
		super(context);
		masterImportDao=new MasterImportDao(getContext());
	}
	
	@Override
	public MasterImportDao getDao() {

		return masterImportDao;
	}
}
