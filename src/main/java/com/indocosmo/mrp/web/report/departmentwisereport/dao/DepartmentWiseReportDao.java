package com.indocosmo.mrp.web.report.departmentwisereport.dao;

import java.util.List;

import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.report.departmentwisereport.model.DepartmentWiseReport;

public interface DepartmentWiseReportDao extends IGeneralDao<DepartmentWiseReport>{

	List<DepartmentWiseReport> getDepartmentWiseReport(DepartmentWiseReport departmentWiseReport);

}
