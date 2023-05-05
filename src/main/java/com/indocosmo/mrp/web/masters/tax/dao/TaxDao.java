package com.indocosmo.mrp.web.masters.tax.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.tax.model.Tax;


public class TaxDao extends GeneralDao<Tax> implements ITaxDao{

	/**
	 * @param context
	 */
	public TaxDao(ApplicationContext context) {
		
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Tax getNewModelInstance() {
		
		return new Tax();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {
		
		return "taxes";
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		
		if(wherePart==null || wherePart==""){
		
			wherePart="WHERE (tx.is_deleted=0 OR tx.is_deleted=NULL)";
		}
		else{
			
			wherePart="WHERE "
		    		   + "(tx.is_deleted=0 OR tx.is_deleted=NULL) "
		    		   + "AND (tx.code LIKE '%"+searchCriteria+"%' "
		    		   + "OR tx.name LIKE '%"+searchCriteria+"%')";
		}
		
		String sql = "SELECT "
				      + "tx.id as id,tx.`code`,tx.name,IFNULL(sync_queue.id ,'') AS quetableId "
				      + "FROM "
				      +  getTable()+" tx "
				      + "LEFT JOIN sync_queue ON (tx.id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"')  "
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
		
			wherePart="WHERE (tx.is_deleted=0 OR tx.is_deleted=NULL)";
		}
		else {
			
			wherePart="WHERE (tx.is_deleted=0 OR tx.is_deleted=NULL) "
		 			   + "AND (tx.code LIKE '%"+searchCriteria+"%' "
		 			   + "OR tx.name LIKE '%"+searchCriteria+"%')";
		}
		
		String sqlCount="SELECT count(tx.id) as row_count from "+getTable()+" tx "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.tax.dao.ITaxDao#getEditDetails(int)
	 */
	public JsonArray getEditDetails(int id) throws Exception
	{
		final String sql="SELECT "
				          + "id,code,name,is_tax1_applicable,tax1_percentage,is_tax2_applicable,tax2_percentage,"
				          + "is_tax3_applicable,tax3_percentage,tax1_refund_rate,tax2_refund_rate,tax3_refund_rate,created_at,created_by "
				          + "FROM "
				          +  getTable()+" "
				          + "WHERE "
				          + "id="+id+"  AND is_deleted='0' ";

		return getTableRowsAsJson(sql);
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#isCodeExist(java.lang.String)
	 */
	@Override
	public Integer isCodeExist(String code) throws Exception {

		final String sql= "SELECT "
  				           + "COUNT(code) AS  row_count "
  				           + "FROM "
  				           +  getTable()+" "
  				           + "WHERE "
  				           + "code='"+code+"' AND (is_deleted=0 OR is_deleted IS NULL) LIMIT 1";

		return excuteSQLForRowCount(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.tax.dao.ITaxDao#getTaxData()
	 */
	public List<Tax> getTaxData() throws Exception{
		
		List<Tax> taxList=null;
		
		final String SQL="SELECT code,name FROM "+getTable()+" WHERE (IFNULL(is_deleted,0) =0) ";
		
		CachedRowSet rs= getRowSet(SQL);
		
		Tax tax;
		
		if(rs!=null){
		
			taxList=new ArrayList<Tax>();
		
			while(rs.next()){		
			
				tax=getNewModelInstance();
				tax.setCode(rs.getString("code"));
				tax.setName(rs.getString("name"));
				taxList.add(tax);
			}
		}

		return taxList;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql="SELECT * FROM " + getTable()+ " where (IFNULL(is_deleted,0) =0)";

		return getTableRowsAsJson(sql);
	}

	public JsonArray getTaxListForBom() throws Exception {
	
		// TODO Auto-generated method stub
		final String sql="SELECT * FROM " + getTable()+ " where (IFNULL(is_deleted,0) =0)";
		return getTableRowsAsJson(sql);
	}
}
