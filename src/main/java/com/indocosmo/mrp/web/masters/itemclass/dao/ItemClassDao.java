package com.indocosmo.mrp.web.masters.itemclass.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.itemclass.model.ItemClass;

/**
 * @author jo
 *
 */
public class ItemClassDao extends MasterBaseDao<ItemClass> implements IItemClassDao {
	
	public ItemClassDao(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance() */
	@Override
	public ItemClass getNewModelInstance() {
	
		return new ItemClass();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable() */
	@Override
	protected String getTable() {
	
		return "item_classes";
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.supplier.dao.ISupplierDao#getIsSystemSupplier
	 * () */
	
	@Override
	public Integer delete(String where) throws Exception {
	
		// TODO Auto-generated method stub
		return super.delete(where);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getMastersRowJson() */
	@Override
	public JsonArray getMastersRowJson() throws Exception {
	
		final String sql = "SELECT id,code,name FROM " + getTable() + " WHERE (IFNULL(is_deleted,0) = 0)";
		
		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * int, int, java.lang.String, java.util.List) */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns , String wherePart , String searchCriteria ,
			String sortPart , int limitRows , int offset , String adnlFilterPart , List<DataTableColumns> columnList)
			throws Exception {
	
		if (wherePart == null || wherePart == "") {
			
			wherePart = "WHERE (IFNULL(itm.is_deleted,0) = 0)";
		}
		else {
			
			wherePart = "WHERE (IFNULL(itm.is_deleted,0) = 0) "
						+ "AND (itm.code LIKE '%" + searchCriteria+ "%' "
						+ "OR itm.name LIKE '%" + searchCriteria + "%' " 
						+ "OR itm.alternative_name LIKE '%"+ searchCriteria + "%' "
						+ "OR itm.name LIKE '%" + searchCriteria + "%'  "
						+ "OR itm_class1.name LIKE '%"+ searchCriteria + "%'  "
						+ "OR dpt.name LIKE '%" + searchCriteria + "%')";
		}
		
		
		String sql = "SELECT "
						+ "itm.id,itm.code,itm.name,itm.alternative_name,itm.department_id,itm.description,dpt.name as dept_name,itm.hsn_code,itm.print_order,"
						+ "itm.menu_id,itm.super_class_id,itm.display_order,itm.account_code,itm.fg_color,itm.bg_color,"
						+ "itm.created_at,itm.created_by,itm.item_thumb,itm_class1.name as super_class_name,"
						+ "itm_class1.id as super_class_id,itm_class1.name as supr_class_name "
						+ "FROM "
						+  getTable()+ " itm "
						+ "LEFT JOIN departments dpt on dpt.id=itm.department_id "
						+ "LEFT JOIN item_classes itm_class1 on itm.super_class_id=itm_class1.id "
						+  wherePart+ " "
						+  sortPart + " "
						+ "LIMIT " + limitRows + " "
						+ "OFFSET " + offset + "";
				
		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.util.List) */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart , String searchCriteria , String adnlFilterPart ,
			List<DataTableColumns> columnList) throws Exception {
	
		if (wherePart == null || wherePart == "") {
			
			wherePart = "WHERE (IFNULL(is_deleted,0) = 0)";
		}
		
		else {
			
			wherePart = "WHERE "
							+ "(IFNULL(is_deleted,0) = 0) "
							+ "AND (code LIKE '%" + searchCriteria + "%' "
							+ "OR name LIKE '%"+ searchCriteria + "%' "
							+ "OR alternative_name LIKE '%" + searchCriteria + "%' "
							+ "OR `name` LIKE '%"+ searchCriteria + "%')";
				}
		
		String sqlCount = "SELECT count(id) as row_count from " + getTable() + " " + wherePart + "";
		CachedRowSet totalRecs = executeSQLForRowset(sqlCount);
		return totalRecs;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.itemclass.dao.IItemClassDao#getSuperClassList
	 * () */
	public JsonArray getSuperClassList() throws Exception {
	
		// TODO Auto-generated method stub
		
		String sql1 = "SELECT id,name FROM " + getTable()+ " WHERE super_class_id !='' and (IFNULL(is_deleted,0) = 0)";
		
		return getTableRowsAsJson(sql1);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemclass.dao.IItemClassDao#getItemClassData()
	 */
	public List<ItemClass> getItemClassData() throws Exception {
	
		List<ItemClass> itemClassList = null;
		
		final String SQL = "SELECT "
							+ "itmcl.code,itmcl.name,itmcl.alternative_name,itmcl.description,itmcl.account_code,"
							+ "itmcl.bg_color,itmcl.fg_color,itmcl2.name as superclassName,dpt.name as departmentName "
							+ "FROM "
							+  getTable() + " itmcl "
							+ "LEFT JOIN departments dpt on itmcl.department_id=dpt.id  "
							+ "LEFT JOIN " + getTable() + " itmcl2 on itmcl.super_class_id=itmcl2.id  "
							+ "WHERE "
							+ "(IFNULL(itmcl.is_deleted,0) =0)";
		
		CachedRowSet rs = getRowSet(SQL);
		
		ItemClass itemClass;
		
		if (rs != null) {
			itemClassList = new ArrayList<ItemClass>();
			
			while (rs.next()) {
		
				itemClass = getNewModelInstance();
				itemClass.setCode(rs.getString("code"));
				itemClass.setName(rs.getString("name"));
				itemClass.setAlternative_name(rs.getString("alternative_name"));
				itemClass.setAccount_code(rs.getString("account_code"));
				itemClass.setBgColor(rs.getString("bg_color"));
				itemClass.setTextColor(rs.getString("fg_color"));
				itemClass.setDepartment_name(rs.getString("departmentName"));
				itemClass.setSuperClassName(rs.getString("superclassName"));
				itemClassList.add(itemClass);
			}
		}
		
		return itemClassList;
	}
}
