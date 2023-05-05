package com.indocosmo.mrp.web.masters.vouchertypes.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.vouchertypes.model.Vouchers;


/**
 * @author jo
 *
 */
public class VouchersDao extends GeneralDao<Vouchers> implements IVouchersDao{


	/**
	 * @param context
	 */
	public VouchersDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Vouchers getNewModelInstance() {

		return new Vouchers();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "voucher_types";
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

		final String sql="SELECT id,code,name FROM "+ getTable() + " WHERE (IFNULL(is_deleted,0) = 0)";

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
						+ "v.id as id,v.code as code,v.name as name,v.description as description,IFNULL(sync_queue.id ,'') AS quetableId,"
						+ "v.is_deleted as is_deleted,v.created_by as created_by,v.created_at as created_at,"
						+ "v.value as value,v.account_code as account_code,v.is_change_payable as is_change_payable,"
						+ "v.voucher_type as voucher_type "
						+ "FROM  "
						+  getTable()+" v "
						+ "LEFT JOIN sync_queue ON (v.id=sync_queue.record_id and sync_queue.table_name='"+getTable()+"') "
						+  wherePart+" "
						+  sortPart+" "
						+ "LIMIT "+limitRows+" "
						+ "OFFSET "+offset+"";

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
