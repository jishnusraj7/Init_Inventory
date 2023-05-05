package com.indocosmo.mrp.web.masters.shopshift.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.shopshift.model.ShopShift;

public class ShopShiftDao extends MasterBaseDao<ShopShift> implements
		IShopShiftDao {

	/**
	 * @param context
	 */
	public ShopShiftDao(ApplicationContext context) {
		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public ShopShift getNewModelInstance() {
		return new ShopShift();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {
		return "shop_shifts";
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */

	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql = "SELECT * FROM " + getTable()+ " WHERE (is_deleted=0 OR is_deleted IS NULL)";

		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,
			String wherePart, String searchCriteria, String sortPart,
			int limitRows, int offset, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {
		
		if (wherePart == null || wherePart == "") {
		
			wherePart = "WHERE (itm.is_deleted=0 OR itm.is_deleted=NULL)";
			} 
		else {
			
			wherePart = "WHERE (itm.is_deleted=0 OR itm.is_deleted=NULL) "
							+ "AND (itm.code LIKE '%"+ searchCriteria+ "%' "
							+ "OR itm.name LIKE '%"+ searchCriteria+ "%' "
							+ "OR itm.description LIKE '%"+ searchCriteria + "%')";
		}
		
			String sql = "SELECT  "
						  + "*,IFNULL(sync_queue.id ,'') AS quetableId   "
						  + "FROM "
						  +  getTable()+ " itm "
						  + "LEFT JOIN sync_queue  ON (itm.id=sync_queue.record_id AND sync_queue.table_name='"+ getTable() + "')  "
						  +  wherePart + " " 
						  +  sortPart + " "
						  + "LIMIT "+ limitRows + " "
						  + "OFFSET " + offset + " ";
			
		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,
			String searchCriteria, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {
		
		if (wherePart == null || wherePart == "") {
			
			wherePart = "WHERE (itm.is_deleted=0 OR itm.is_deleted=NULL)";
		} 
		else {
			
			wherePart = "WHERE (itm.is_deleted=0 OR itm.is_deleted=NULL) "
					     + "AND (itm.code LIKE '%"+ searchCriteria+ "%' "
					     + "OR itm.name LIKE '%"+ searchCriteria+ "%' "
					     + "OR itm.description LIKE '%"+ searchCriteria + "%')";
		}
		
		String sqlCount = "SELECT count(itm.id) as row_count FROM "+ getTable() + " itm " + wherePart + "";
		CachedRowSet totalRecs = executeSQLForRowset(sqlCount);
		
		return totalRecs;
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JsonArray getShopshiftDtlData(Integer id) throws Exception {

		final String sql = "SELECT * FROM " +getTable()+ " WHERE (is_deleted=0 OR is_deleted IS NULL) and id =" + id+ "";

		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#isCodeExist(java.lang.String)
	 */
	@Override
	public Integer isCodeExist(String code) throws Exception {
		final String sql = "SELECT "
							+ "COUNT(code) AS  row_count "
							+ "FROM "
							+  getTable() + " "
							+ "WHERE "
							+ "code='" + code+ "'  AND (is_deleted=0 OR is_deleted IS NULL) "
							+ "LIMIT 1";

		return excuteSQLForRowCount(sql);
	}

}
