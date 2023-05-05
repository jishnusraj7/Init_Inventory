package com.indocosmo.mrp.web.masters.salesdepartment.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.salesdepartment.model.SalesDepartment;

/**
 * @author jo
 *
 */
public class SalesDepartmentDao extends MasterBaseDao<SalesDepartment> implements ISalesDepartmentDao{

	public SalesDepartmentDao(ApplicationContext context) {
		super(context);
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public SalesDepartment getNewModelInstance() {
		
		return new SalesDepartment();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {
		
		return "departments";
	}

	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0) and (itm.code like '%"+searchCriteria+"%' or itm.name like '%"+searchCriteria+"%' or itm.description like '%"+searchCriteria+"%' )";
		}
		String sql = "SELECT itm.id,itm.`code`,itm.`name`,itm.description,IFNULL(sync_queue.id ,'') AS quetableId FROM "+getTable()+" itm LEFT JOIN sync_queue  ON (itm.id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"') "+wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0)";
		}else{
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0) and (itm.code like '%"+searchCriteria+"%' or itm.name like '%"+searchCriteria+"%' or itm.description like '%"+searchCriteria+"%') ";
		}
		String sqlCount="SELECT count(itm.id) as row_count from "+getTable()+" itm  "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}
	
	public JsonArray getEditDetails(int id) throws Exception
	{
  final String sql="SELECT * from "+getTable()+" WHERE id="+id+"  and is_deleted='0' ";
		
		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#isCodeExist(java.lang.String)
	 */
	@Override
	public Integer isCodeExist(String code) throws Exception {
		final String sql= "SELECT COUNT(code) AS  row_count FROM "+getTable()+" WHERE code='"+code+"'  AND (is_deleted=0 OR is_deleted IS NULL) LIMIT 1";

		return excuteSQLForRowCount(sql);
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getMastersRowJson()
	 */
	@Override
	public JsonArray getMastersRowJson() throws Exception {
     final String sql="SELECT id,code,name from "+ getTable() + " WHERE (IFNULL(is_deleted,0) = 0)";
		
		return getTableRowsAsJson(sql);
	}
	
}
