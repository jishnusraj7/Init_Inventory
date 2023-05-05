package com.indocosmo.mrp.web.masters.department.dao;

import java.util.List;

import com.indocosmo.mrp.web.core.base.dao.IMasterBaseDao;
import com.indocosmo.mrp.web.masters.department.model.Department;


public interface IDepartmentDao extends IMasterBaseDao<Department>{

	public Department getIsSystemDepartment() throws Exception;
	public Department getProductionDepartment() throws Exception;
	public List<Department> getDptData() throws Exception;
}
