package com.indocosmo.mrp.web.report.tallyexport.service;

import java.util.List;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.tallyexport.dao.TallyExportDaoImp;
import com.indocosmo.mrp.web.report.tallyexport.model.TallyExport;
/*
 * @gana
 */
public class TallyExportServiceImp extends GeneralService<TallyExport, TallyExportDaoImp> implements ITallyExportService{

	private TallyExportDaoImp tallyExportDao;
	
	public TallyExportServiceImp(ApplicationContext context) {
		super(context);
		tallyExportDao=new TallyExportDaoImp(getContext());
	}

	@Override
	public TallyExportDaoImp getDao() {
		
		return tallyExportDao;
	}

	public List<TallyExport> getTallyData(TallyExport tallyExport) {
		
		return tallyExportDao.getTallyData(tallyExport);
	}

}
