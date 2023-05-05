package com.indocosmo.mrp.web.report.departmentwisereport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.report.departmentwisereport.dao.DepartmentWiseReportDao;
import com.indocosmo.mrp.web.report.departmentwisereport.dao.DepartmentWiseReportDaoImp;
import com.indocosmo.mrp.web.report.departmentwisereport.model.DepartmentWiseReport;

public class DepartmentWiseReportServiceImp extends GeneralService<DepartmentWiseReport, DepartmentWiseReportDaoImp> implements DepartmentWiseReportService{

    
    private DepartmentWiseReportDaoImp departmentWiseDao;
    
	public DepartmentWiseReportServiceImp(ApplicationContext context) {
		super(context);
		departmentWiseDao=new DepartmentWiseReportDaoImp(getContext());
	}


	@Override
	public DepartmentWiseReportDaoImp getDao() {
		return new DepartmentWiseReportDaoImp(getContext());
	}


	public List<DepartmentWiseReport> getDepartmentWiseReport(DepartmentWiseReport departmentWiseReport) {
		return departmentWiseDao.getDepartmentWiseReport(departmentWiseReport);
	}

	
}
