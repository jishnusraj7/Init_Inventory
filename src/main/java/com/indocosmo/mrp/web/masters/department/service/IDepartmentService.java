package com.indocosmo.mrp.web.masters.department.service;



import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IMasterBaseService;
import com.indocosmo.mrp.web.masters.department.dao.DepartmentDao;
import com.indocosmo.mrp.web.masters.department.model.Department;


public interface IDepartmentService extends IMasterBaseService<Department,DepartmentDao>{
	public JsonArray getDepartmentImportList() throws Exception ;
	public JsonArray getDepartmentImportUpdatedList() throws Exception ;
	public List<Department> getDataToImport()throws Exception;
	public List<Department> getUpdatedDataToImport()throws Exception;
	public Department getIsSystemDepartment() throws Exception;
	public Department getProductionDepartment() throws Exception;
	public List<Department> getDptData() throws Exception;
	
}
