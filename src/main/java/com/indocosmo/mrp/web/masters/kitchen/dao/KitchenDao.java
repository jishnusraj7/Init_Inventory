package com.indocosmo.mrp.web.masters.kitchen.dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.masters.currencydetails.model.CurrencyDetails;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.kitchen.model.Kitchen;



/**
 * @author jo
 *
 */
public class KitchenDao extends MasterBaseDao<Kitchen> implements IKitchenDao{

	/**
	 * @param context
	 */
	public KitchenDao(ApplicationContext context) {

		super(context);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Kitchen getNewModelInstance() {

		return new Kitchen();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */

	@Override
	public String getTable() {

		return "kitchens";
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql="SELECT * FROM " + getTable() + " WHERE (is_deleted=0 OR is_deleted IS NULL)";

		return getTableRowsAsJson(sql);
	}


	/**
	 * @return
	 * @throws Exception
	 */
	public String getCurrentCurrency() throws Exception {

		final String sql="SELECT * FROM " + getTable()+  " WHERE  (is_deleted=0 OR is_deleted IS NULL) AND is_base_currency=1";
		CachedRowSet resultSet;
		CurrencyDetails currency = new CurrencyDetails();

		try {

			resultSet = dbHelper.executeSQLForRowset(sql);

			if (resultSet.next()) {

				currency.setId(resultSet.getInt("id"));
				currency.setSymbol(resultSet.getString("symbol"));
			}
		} catch (SQLException e) {

			throw e;
		}

		return currency.getSymbol();
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
					   + "OR itm.name LIKE '%"+searchCriteria+"%' "
					   + "OR itm.created_by LIKE '%"+searchCriteria+"%' "
					   + "OR itm.description LIKE '%"+searchCriteria+"%')";
		}

		String sql = "SELECT * FROM "+getTable()+" itm "+wherePart+" "+sortPart+" LIMIT "+limitRows+" OFFSET "+offset+"";
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
					+ "OR itm.name LIKE '%"+searchCriteria+"%' "
					+ "OR itm.description LIKE '%"+searchCriteria+"%')";
		}

		String sqlCount="SELECT count(itm.id) as row_count from "+getTable()+" itm "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		

		return totalRecs;
	}


	/**
	 * @return
	 * @throws Exception
	 */
	public Integer isBaseCurrencyExist() throws Exception {

		final String sql= "SELECT "
				+ "COUNT(is_base_currency) AS  row_count "
				+ "FROM "
				+ ""+getTable()+" "
				+ "WHERE is_base_currency=1 AND  (is_deleted=0 OR is_deleted IS NULL) "
				+ "LIMIT 1";

		return excuteSQLForRowCount(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#isCodeExist(java.lang.String)
	 */
	@Override
	public Integer isCodeExist(String code) throws Exception {

		final String sql= "SELECT COUNT(code) AS  row_count FROM "+getTable()+" WHERE code='"+code+"'  AND (is_deleted=0 OR is_deleted IS NULL) LIMIT 1";

		return excuteSQLForRowCount(sql);
	}
}
