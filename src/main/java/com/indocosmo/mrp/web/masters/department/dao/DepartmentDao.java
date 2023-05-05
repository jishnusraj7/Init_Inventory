package com.indocosmo.mrp.web.masters.department.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.ReflectionUtil;
import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.Hq;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.enumDepartmentType;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.core.counter.dao.CounterDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.department.model.Department;

public class DepartmentDao extends MasterBaseDao<Department> implements
		IDepartmentDao {
	
	private CounterDao counterDao;

	public DepartmentDao(ApplicationContext context) {

		super(context);
		counterDao=new CounterDao(getContext());
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Department getNewModelInstance() {

		return new Department();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "mrp_department";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.masters.supplier.dao.ISupplierDao#getIsSystemSupplier
	 * ()
	 */
	@Override
	public Department getIsSystemDepartment() throws Exception {

		final String sql = "SELECT * " 
						   	+ "FROM " 
						   	+ getTable() + " "
						   	+ "WHERE "
						   	+ "is_system=1 and id="+ enumDepartmentType.DEP_STORE.getenumDepartmentTypeId()+ " "
						   	+ "AND (IFNULL(is_deleted,0) = 0)";

		CachedRowSet resultSet;

		Department department = new Department();

		try {
			resultSet = dbHelper.executeSQLForRowset(sql);

			if (resultSet.next()) {

				department.setId(resultSet.getInt("id"));
				department.setCode(resultSet.getString("code"));
				department.setName(resultSet.getString("name"));
			}

		} catch (Exception e) {

			throw e;
		}

		return department;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.masters.department.dao.IDepartmentDao#
	 * getProductionDepartment()
	 */
	@Override
	public Department getProductionDepartment() throws Exception {

		final String sql = "SELECT * " 
							+ "FROM " 
							+ getTable() + " " 
							+ "WHERE "
							+ "is_system=1 and id="+ enumDepartmentType.DEP_PRODUCTION.getenumDepartmentTypeId()
							+ " AND (IFNULL(is_deleted,0) = 0)";

		CachedRowSet resultSet;

		Department department = new Department();

		try {
			resultSet = dbHelper.executeSQLForRowset(sql);

			if (resultSet.next()) {

				department.setId(resultSet.getInt("id"));
				department.setCode(resultSet.getString("code"));
				department.setName(resultSet.getString("name"));
			}

		} catch (Exception e) {

			throw e;
		}
		return department;
	}

	@Override
	public Integer delete(String where) throws Exception {

		return super.delete(where);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getMastersRowJson()
	 */
	@Override
	public JsonArray getMastersRowJson() throws Exception {
		
		final String sql = "SELECT id,code,name FROM " + getTable() + " WHERE (IFNULL(is_deleted,0) = 0)";

		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,
			String wherePart, String searchCriteria, String sortPart,
			int limitRows, int offset, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {

		String sql="";
		
		if (wherePart == null || wherePart == "") {

			wherePart = "WHERE (IFNULL(is_deleted,0) = 0) AND (IFNULL(is_system,0) = 0)";
		} else {

			wherePart = "WHERE " + "(IFNULL(is_deleted,0) = 0) AND (IFNULL(is_system,0) = 0) "
						+ "AND (code LIKE '%" + searchCriteria + "%' "
						+ "OR name LIKE '%" + searchCriteria + "%' "
						+ "OR description LIKE '%" + searchCriteria + "%' "
						+ "OR `name` LIKE '%" + searchCriteria + "%' "
						+ "OR `dept_type_name` LIKE '%" + searchCriteria + "%')";
			}

		
		if(context.getCurrentHttpSession().getAttribute("COMPANY_ID").equals(Hq.HQ.getHqId()))
		{
		
				sql = "SELECT "
						+ "id,code,name,created_by,created_at,description,dept_type,is_deleted,is_system,last_sync_at,dept_type_name,quetableId "
						+ "FROM "
						+ " (SELECT "
						+ " getTab.id AS id,`code`,IFNULL(sync_queue.id, '') AS quetableId,`name`,description,dept_type,created_by,created_at,"
						+ " is_deleted,is_system,last_sync_at,"
						+ " (CASE(dept_type) WHEN 0 "
						+ " THEN 'Material Department' "
						+ " WHEN 1 THEN 'Production Department'"
						+ " WHEN 2 THEN 'Sales Department' "
						+ " ELSE '' "
						+ " END) AS dept_type_name "
						+ "FROM "
						+ getTable()
						+ " "
						+ " getTab "
						+ "LEFT JOIN sync_queue ON (getTab.id = sync_queue.record_id AND sync_queue.table_name = '"
						+ getTable() + "'))" + "TBL " + wherePart + " " + sortPart
						+ " LIMIT " + limitRows + " OFFSET " + offset + "";
				
		}
		else
		{
			
			sql = "SELECT "
					+ "id,code,name,created_by,created_at,description,dept_type,is_deleted,is_system,last_sync_at,dept_type_name "
					+ "FROM "
					+ " (SELECT "
					+ " getTab.id AS id,`code`,`name`,description,dept_type,created_by,created_at,"
					+ " is_deleted,is_system,last_sync_at,"
					+ " (CASE(dept_type) WHEN 0 "
					+ " THEN 'Material Department' "
					+ " WHEN 1 THEN 'Production Department'"
					+ " WHEN 2 THEN 'Sales Department' "
					+ " ELSE '' "
					+ " END) AS dept_type_name "
					+ "FROM "
					+ getTable()
					+ " "
					+ " getTab )" + "TBL " + wherePart + " " + sortPart
					+ " LIMIT " + limitRows + " OFFSET " + offset + "";
			
		}
					
							return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,
			String searchCriteria, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {
		
		String sqlCount="";
		
		if (wherePart == null || wherePart == "") {

			wherePart = "WHERE (IFNULL(is_deleted,0) = 0) AND (IFNULL(is_system,0) = 0) ";
		} else {
		
			wherePart = "WHERE "
						+ "(IFNULL(is_deleted,0) = 0) AND (IFNULL(is_system,0) = 0) "
						+ "AND (code LIKE '%" + searchCriteria + "%' "
						+ "OR name LIKE '%" + searchCriteria + "%' "
						+ "OR description LIKE '%" + searchCriteria + "%' "
						+ "OR `name` like '%" + searchCriteria + "%' "
						+ "OR `dept_type_name` LIKE '%" + searchCriteria + "%')";
		}
		
		sqlCount = "SELECT "
					+ "COUNT(id) as row_count "
					+ "FROM "
					+ " (SELECT	id,`code`,`name`,description,dept_type,is_deleted,is_system,last_sync_at,"
					+ " (CASE(dept_type) " 
					+ " WHEN 0 THEN 'Material Department' "
					+ " WHEN 1 THEN 'Production Department'"
					+ " WHEN 2 THEN 'Sales Department' " + "ELSE '' "
					+ " END) AS dept_type_name " 
					+ "FROM " + getTable() + ")TBL "
					+ wherePart + "";

		CachedRowSet totalRecs = executeSQLForRowset(sqlCount);
		return totalRecs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.masters.department.dao.IDepartmentDao#getDptData()
	 */
	public List<Department> getDptData() throws Exception {

		List<Department> dptList = null;

		final String SQL = "SELECT code,name  FROM  "+getTable()+"  WHERE (IFNULL(is_deleted,0) =0) ";

		CachedRowSet rs = getRowSet(SQL);
		Department department;
		
		if (rs != null) {
			
			dptList = new ArrayList<Department>();
		
			while (rs.next()) {
			
				department = getNewModelInstance();
				department.setCode(rs.getString("code"));
				department.setName(rs.getString("name"));
				dptList.add(department);
			}
		}

		return dptList;
	}
	
	@Override
	protected void setValuesForInsertPS(PreparedStatement ps , Department item) throws Exception {
	

		

		

	     Integer version=(Integer) context.getCurrentHttpSession().getAttribute("version");
	    
		String fieldName=null;
		Object fieledValue=null;

		final HashMap<String, PropertyDescriptor> propertyDescriptors=ReflectionUtil.getPropertyDescriptors(item.getClass());
		int index=1;

		for(Field field : ReflectionUtil.getAllFileds(item.getClass())){

			/**
			 * Column annotation
			 * Gets the column from bean
			 */
			final Column annotationColumn = (Column)field.getDeclaredAnnotation(Column.class);

			if(annotationColumn!=null && propertyDescriptors.get(field.getName())!=null){

				fieldName=null;

				/**
				 * Use the setter method to get field type and id generation type.
				 */
				final Method setterMethod=propertyDescriptors.get(field.getName()).getWriteMethod();
				final Method getterMethod=propertyDescriptors.get(field.getName()).getReadMethod();
				System.out.println("Column Name : "+annotationColumn.name());

				/**
				 * Check the Id generation type is overridden anywhere
				 */
				Id annotationId = (Id) ((setterMethod!=null && setterMethod.getDeclaredAnnotation(Id.class)!=null)?
						setterMethod.getDeclaredAnnotation(Id.class):
							((getterMethod!=null && getterMethod.getDeclaredAnnotation(Id.class)!=null)?getterMethod.getDeclaredAnnotation(Id.class):
								field.getDeclaredAnnotation(Id.class)));

				if(annotationId==null){

					fieldName=annotationColumn.name();
					field.setAccessible(true);
					Object value=field.get(item);
					fieledValue=DBUtil.modelValueToDBFieldValue(field, value,false);

				}else{

					final GenerationType genType=annotationId.generationType();
					switch (genType) {
					case IDENTITY:
						/**
						 *
						 * No need to set the filed. Id will be automatically generated by database
						 * 
						 **/ 
						fieldName=annotationColumn.name();
						field.setAccessible(true);
						
						field.set(item, null);
						fieledValue=DBUtil.modelValueToDBFieldValue(field, null);
						break;

					case COUNTER:
						/**
						 *
						 * If counter, get the id from counter table.
						 * 
						 **/ 
						fieldName=annotationColumn.name();
						field.setAccessible(true);

						Counter annotationCounter = (Counter)setterMethod.getDeclaredAnnotation(Counter.class);

					
						if(annotationCounter!=null && field.get(item) == null ){

							final String module=annotationCounter.module();
							final String key=annotationCounter.key();
							int value=-1;
							if(version==1)
							{
							 value=counterDao.getCounterFor(module, key);
							}
							else
							{
							 value=counterDao.getCounter(module, key);
							}
					
							
							Object customId=getCustomId(value);
							field.set(item, customId);
							fieledValue=DBUtil.modelValueToDBFieldValue(field, customId);

						}else if(annotationCounter!=null && field.get(item)!=null){
							
							Object value=field.get(item);
							fieledValue=DBUtil.modelValueToDBFieldValue(field, value,false);
//							
						}else 
							throw new Exception("Counter (annotaion) details not set for ID.");


						break;
					}
				}

				if(fieldName!=null){
					

						ps.setObject(index, fieledValue);

					index++;
				}
			}
		}
	
	
	
	}

	public JsonArray getDepartmentById(int id) throws Exception {

		final String sql = "SELECT id,code,name FROM " + getTable() + " WHERE id = "+ id +" AND (IFNULL(is_deleted,0) = 0) ";

		return getTableRowsAsJson(sql);
	}

}
