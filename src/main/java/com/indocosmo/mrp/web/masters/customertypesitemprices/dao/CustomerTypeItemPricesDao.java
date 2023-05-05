package com.indocosmo.mrp.web.masters.customertypesitemprices.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.customertypesitemprices.model.CustomerTypeItemPrices;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;

/**
 * @author jo
 *
 */
public class CustomerTypeItemPricesDao extends GeneralDao<CustomerTypeItemPrices> implements ICustomerTypeItemPricesDao{


	/**
	 * @param context
	 */
	public CustomerTypeItemPricesDao(ApplicationContext context) {

		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public CustomerTypeItemPrices getNewModelInstance() {

		return new CustomerTypeItemPrices();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "customers_type_item_prices";
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

	
	@Override
	public JsonArray getCustomerDataById(Integer itemId) throws Exception {

		final String sql="SELECT * from "+ getTable() + " WHERE (IFNULL(is_deleted,0) = 0) and sale_item_id="+itemId+" ";

		return getTableRowsAsJson(sql);
	}
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		
		if(wherePart==null || wherePart==""){
		
			wherePart="WHERE (IFNULL(c.is_deleted,0) = 0)";
		}
		else {
			
			wherePart="WHERE "
					   + "(IFNULL(c.is_deleted,0) = 0) "
					   + "AND (c.code LIKE '%"+searchCriteria+"%' "
					   + "OR c.name LIKE '%"+searchCriteria+"%' "
					   + "OR c.description LIKE '%"+searchCriteria+"%' "
					   + "OR c.name LIKE '%"+searchCriteria+"%' "
					   + "OR c.default_price_variance_pc LIKE '%"+searchCriteria+"%')";
		}

		String sql="SELECT	"
		  		    + "c.id,c.code,c.name,c.description,c.default_price_variance_pc,c.is_deleted,c.is_system,c.last_sync_at,"
		  		    + " IFNULL(sync_queue.id ,'') AS quetableId  "
		  		    + "FROM "
		  		    +  getTable()+ " c LEFT JOIN sync_queue  ON (c.id=sync_queue.record_id AND sync_queue.table_name='"+ getTable()+ "')"
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
		else {
			
			wherePart="WHERE "
					   + "(IFNULL(is_deleted,0) = 0) "
					   + "AND (code like '%"+searchCriteria+"%' "
					   + "OR name LIKE '%"+searchCriteria+"%' "
					   + "OR description LIKE '%"+searchCriteria+"%' "
					   + "OR `name` LIKE '%"+searchCriteria+"%' )";
		}
		
		String sqlCount="SELECT "
		 		         + "COUNT(id) AS row_count "
		 		         + "FROM "
		 		         + " (SELECT	id,`code`,`name`,description,default_price_variance_pc,is_deleted,is_system "
		 		         + " FROM "
		 		         +   getTable()+")TBL "
		 		         +  wherePart+"";
		
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}


	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getCustomerTypeList() throws Exception {
		// TODO Auto-generated method stub

		String sql1="SELECT id,name FROM "+getTable()+" WHERE (IFNULL(is_deleted,0) = 0)";
		
		return getTableRowsAsJson(sql1);
	}

}
