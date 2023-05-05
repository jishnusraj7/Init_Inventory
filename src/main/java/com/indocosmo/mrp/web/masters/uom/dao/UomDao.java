package com.indocosmo.mrp.web.masters.uom.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.uom.model.Uom;


public class UomDao extends MasterBaseDao<Uom> implements IUomDao {


	/**
	 * @param context
	 */
	public UomDao(ApplicationContext context) {

		super(context);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Uom getNewModelInstance() {

		return new Uom();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.MRP.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "uoms";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		
		String Sql="SELECT * FROM "+getTable()+" WHERE (is_deleted=0 OR is_deleted=NULL) ";
		
		return getTableRowsAsJson(Sql);
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{

		if(wherePart==null || wherePart==""){

			wherePart="WHERE (itm.is_deleted=0 OR itm.is_deleted=NULL)";
		}
		else{
			wherePart="WHERE "
					+ "(itm.is_deleted=0 OR itm.is_deleted=NULL) "
					+ "AND (itm.code LIKE '%"+searchCriteria+"%' "
					+ "OR itm.name LIKE '%"+searchCriteria+"%')";
		}

		String sql = "SELECT "
					  + "itm.id as id,itm.`code`,itm.`name`,IFNULL(sync_queue.id ,'') AS quetableId FROM "+getTable()+" itm "
					  + "LEFT JOIN sync_queue  ON (itm.id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"') "
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
			
			wherePart="WHERE (itm.is_deleted=0 OR itm.is_deleted=NULL)";
		}
		else{
			
			wherePart="WHERE "
					   + "(itm.is_deleted=0 OR itm.is_deleted=NULL) "
					   + "AND (itm.code LIKE '%"+searchCriteria+"%' "
					   + "OR itm.name LIKE '%"+searchCriteria+"%')";
		}
		
		String sqlCount="SELECT count(itm.id) as row_count from "+getTable()+" itm  "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.uom.dao.IUomDao#getBaseUom()
	 */
	public JsonArray getBaseUom() throws Exception{
		
		String Sql="SELECT * FROM "+getTable()+" WHERE (is_deleted=0 OR is_deleted=NULL) and (is_compound=0)";
		return getTableRowsAsJson(Sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.uom.dao.IUomDao#getEditDetails(int)
	 */
	public JsonArray getEditDetails(int id) throws Exception{
		
		final String sql="SELECT "
					      + "id,code,name,description,is_compound,base_uom_id,compound_unit,uom_symbol,"
					      + "decimal_places,created_by,created_at "
					      + "FROM "
					      +  getTable()+" "
					      + "WHERE "
					      + "id="+id+" AND is_deleted='0' ";

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
	 * @see com.indocosmo.mrp.web.masters.uom.dao.IUomDao#getUomData()
	 */
	public List<Uom> getUomData() throws Exception{
		
		List<Uom> uomList=null;
		
		final String SQL="SELECT code,name FROM "+getTable()+" WHERE (IFNULL(is_deleted,0) =0) ";

		CachedRowSet rs= getRowSet(SQL);
		Uom uom;
	
		if(rs!=null){
		
			uomList=new ArrayList<Uom>();
			while(rs.next()){		
		
				uom=getNewModelInstance();
				uom.setCode(rs.getString("code"));
				uom.setName(rs.getString("name"));
				uomList.add(uom);
			}
		}

		return uomList;
	}
}
