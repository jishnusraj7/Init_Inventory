package com.indocosmo.mrp.web.masters.vouchertypes.voucherclass.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.vouchertypes.voucherclass.model.VoucherClass;


/**
 * @author jo
 *
 */
public class VouchersClassDao extends GeneralDao<VoucherClass> implements IVoucherClassDao{

	/**
	 * @param context
	 */
	public VouchersClassDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public VoucherClass getNewModelInstance() {
		
		return new VoucherClass();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {
		
		return "voucher_class";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getDropdownArray()
	 */
	@Override
	public JsonArray getDropdownArray() throws Exception {

		final String sql="SELECT id,name FROM " + getTable()+ " where (IFNULL(is_deleted,0) = 0) ";

		return getTableRowsAsJson(sql);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#delete(java.lang.String)
	 */
	@Override
	public Integer delete(String where) throws Exception {
		// TODO Auto-generated method stub
		return super.delete(where);
	}
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getMastersRowJson()
	 */
	@Override
	public JsonArray getMastersRowJson() throws Exception {

		final String sql="SELECT id,code,name from "+ getTable() + " WHERE (IFNULL(is_deleted,0) = 0)";
		
		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			
			wherePart="WHERE (IFNULL(is_deleted,0) = 0)";
		}
		else{
			wherePart="WHERE "
						+ "(IFNULL(is_deleted,0) = 0) "
						+ "AND (code LIKE '%"+searchCriteria+"%' "
						+ "OR name LIKE '%"+searchCriteria+"%' "
						+ "OR description LIKE '%"+searchCriteria+"%' )";
		}
		
		String sql = "SELECT "
						+ "id,code,name,description,is_deleted,created_by,created_at,value "
						+ "FROM "
						+  getTable()+" "
						+  wherePart +" "
						+  sortPart+" "
						+  "LIMIT "+limitRows+" "
						+  "OFFSET "+offset+"";
			
		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
	
		if(wherePart==null || wherePart==""){
		
			wherePart="WHERE (IFNULL(is_deleted,0) = 0)";
		}
		else{
			wherePart="WHERE "
						+ "(IFNULL(is_deleted,0) = 0) "
						+ "AND (code LIKE '%"+searchCriteria+"%' "
						+ "OR name LIKE '%"+searchCriteria+"%' "
						+ "OR description LIKE '%"+searchCriteria+"%' )";
	}
		String sqlCount="SELECT COUNT(id) as row_count  FROM "+getTable()+" " +wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		
		return totalRecs;
	}
}
