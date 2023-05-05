package com.indocosmo.mrp.web.masters.reasons.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.reasons.model.Reasons;

/**
 * @author jo
 *
 */
public class ReasonsDao extends GeneralDao<Reasons> implements IReasonsDao{


	/**
	 * @param context
	 */
	public ReasonsDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Reasons getNewModelInstance() {

		return new Reasons();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "reasons";
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
						+ "OR description LIKE '%"+searchCriteria+"%' "
						+ "OR `name` LIKE '%"+searchCriteria+"%' "
						+ "OR `context_type` LIKE '%"+searchCriteria+"%' )";
			}

		String sql = "SELECT "
						+ "tbl.id as id,tbl.code as code,tbl.name as name,IFNULL(sync_queue.id ,'') AS quetableId,"
						+ "tbl.description as description,tbl.context as context,tbl.is_deleted as is_deleted,tbl.created_by as created_by,"
						+ "tbl.created_at as created_at,tbl.updated_by as updated_by,tbl.updated_at as updated_at ,"
						+ "("
						+ "CASE(tbl.context) "
						+ " WHEN 0 THEN 'DISCOUNT' "
						+ " WHEN 1 THEN 'REFUND' "
						+ " ELSE '' "
						+ " END) AS context_type "
						+ "FROM "
						+  getTable()+" tbl "
						+ "LEFT JOIN sync_queue ON(tbl.id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"')"
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
		else
		{
			wherePart="WHERE "
						+ "(IFNULL(is_deleted,0) = 0) "
						+ "AND (code LIKE '%"+searchCriteria+"%' "
						+ "OR name LIKE '%"+searchCriteria+"%' "
						+ "OR description LIKE '%"+searchCriteria+"%' )";
			}
		
		String sqlCount="SELECT count(id) as row_count  FROM "+getTable()+" " +wherePart+"";
		
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}
}
