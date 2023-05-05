package com.indocosmo.mrp.web.masters.employee.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.employee.model.Employee;

/**
 * @author jo
 *
 */
public class EmployeeDao extends GeneralDao<Employee> implements IEmployeeDao {

	/**
	 * @param context
	 */
	public EmployeeDao(ApplicationContext context) {

		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Employee getNewModelInstance() {

		return new Employee();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "employees";
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql = "SELECT * FROM " + getTable()+ " WHERE (is_deleted=0 OR is_deleted IS NULL)";

		return getTableRowsAsJson(sql);
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JsonArray getEmployeeDtlData(Integer id) throws Exception {

		final String sql = "SELECT "
							+ "employees.*,mrp_department.id as department_ids, mrp_department.`code` as department_code ,"
							+ "mrp_department.`name` as department_name,employee_categories.id as employee_categories_id ,"
							+ "employee_categories.`code` as employee_category_code,employee_categories.`name` as employee_category_name  "
							+ "FROM "
							+ "employees "
							+ "LEFT JOIN employee_categories on employees.employee_category_id= employee_categories.id  "
							+ "LEFT JOIN mrp_department on mrp_department.id=employees.department_id  "
							+ "WHERE "
							+ "(employees.is_deleted=0 OR employees.is_deleted IS NULL) AND employees.id ="+ id + "";

		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public CachedRowSet getTotalCountForDataTable(String wherePart,
			String searchCriteria, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {

		if (wherePart == null || wherePart == "") {

			wherePart = " WHERE (is_deleted=0 OR is_deleted IS NULL) ";
		} 
		else {

			wherePart += ") AND  (is_deleted=0 OR is_deleted IS NULL) ";
		}
		
		if (adnlFilterPart != "" && adnlFilterPart != null) {

			wherePart += " AND " + adnlFilterPart;
		}

		String sqlCount = "SELECT COUNT(id) as row_count FROM " + getTable()+ " " + wherePart + "";
		
		CachedRowSet totalRecs = executeSQLForRowset(sqlCount);
		return totalRecs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * int, int, java.lang.String, java.util.List)
	 */
	public JsonArray getTableRowsAsJsonForDataTable(String columns,
			String wherePart, String searchCriteria, String sortPart,
			int limitRows, int offset, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {

		if (wherePart == null || wherePart == "") {

			wherePart = " WHERE (is_deleted=0 OR is_deleted IS NULL)";
		} 
		else {

			wherePart += ")  AND (is_deleted=0 OR is_deleted IS NULL)";
		}

		if (adnlFilterPart != "" && adnlFilterPart != null) {

			wherePart += " and " + adnlFilterPart;
		}

		String sql = "SELECT  "
					  + "employees.id,code,f_name,sex,dob,card_no,status,IFNULL(sync_queue.id ,'') AS quetableId "
					  + "FROM "
					  + getTable()+ " "
					  + "LEFT JOIN sync_queue  ON (employees.id=sync_queue.record_id AND sync_queue.table_name='"+ getTable()+ "')  "
					  + wherePart+ " "
					  + sortPart+ " "
					  + "LIMIT "+ limitRows + " "
					  + "OFFSET " + offset + " ";

		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#isCodeExist(java.lang.
	 * String)
	 */
	@Override
	public Integer isCodeExist(String code) throws Exception {

		final String sql = "SELECT "
				            + "COUNT(code) AS  row_count "
				            + "FROM "
				            + getTable() + " " 
				            + "WHERE "
				            + "code='" + code + "' "
				            + "AND (is_deleted=0 OR is_deleted IS NULL) "
				            + "LIMIT 1";

		return excuteSQLForRowCount(sql);
	}

}
