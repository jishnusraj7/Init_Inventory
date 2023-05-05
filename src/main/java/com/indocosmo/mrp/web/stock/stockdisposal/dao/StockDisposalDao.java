package com.indocosmo.mrp.web.stock.stockdisposal.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias.SearchCriterias;
import com.indocosmo.mrp.web.stock.stockdisposal.model.StockDisposal;

/**
 * @author jo
 *
 */
public class StockDisposalDao extends GeneralDao<StockDisposal> implements
		IStockDisposalDao {

	/**
	 * @param context
	 */
	public StockDisposalDao(ApplicationContext context) {

		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public StockDisposal getNewModelInstance() {

		return new StockDisposal();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "mrp_stock_disposal_hdr";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#getDataTableList(com.indocosmo
	 * .mrp.web.masters.datatables.DataTableCriterias, java.util.List)
	 */
	@Override
	public JsonArray getDataTableList(DataTableCriterias datatableParameters,
			List<String> tableFields) throws Exception {

		String sql;

		if (datatableParameters.getSearch().get(SearchCriterias.value)
				.isEmpty()) {

			sql = "SELECT  *  " + "FROM " + getTable() + " " + "LIMIT "
					+ datatableParameters.getLength() + " " + "OFFSET "
					+ datatableParameters.getStart() + "";
		} else {

			sql = " SELECT  *  "
					+ "FROM "
					+ getTable()
					+ " as rb "
					+ "WHERE "
					+ "rb."
					+ tableFields.get(0)
					+ " LIKE '%"
					+ datatableParameters.getSearch()
							.get(SearchCriterias.value) + "%'";

		}

		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#dataTableTotalRecord()
	 */
	@Override
	public Integer dataTableTotalRecord() {

		Integer countResults = 0;

		String sqlQuery = "SELECT COUNT(*) AS row_count FROM " + getTable();

		try {

			countResults = excuteSQLForRowCount(sqlQuery);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return countResults;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql = "SELECT "
				+ getTable()+".*,mrp_department.`name` as department_name "
				+ "FROM "
				+  getTable() +" "
				+ "INNER JOIN mrp_department on "+getTable()+".department_id=mrp_department.id";

		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#delete(java.lang.String)
	 */
	@Override
	public Integer delete(String where) throws Exception {

		final String sql = "DELETE FROM " + getTable() + " WHERE " + where;

		Integer is_deleted = 0;

		beginTrans();
		try {

			is_deleted = executeSQL(sql);
			endTrans();
		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}

		return is_deleted;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getAdditionalFilter(java.
	 * util.List)
	 */
	@Override
	public String getAdditionalFilter(List<DataTableColumns> columnList) {

		String adnlFilterPart = "";
		for (DataTableColumns col : columnList) {

			if (col.getSearchValue() != "" && col.getSearchValue() != null) 
				adnlFilterPart += ((adnlFilterPart.isEmpty()) ? " " : " AND ") + getTable() + "." 
						+ col.getData() + " = ('" + col.getSearchValue() + "')";
		}
		return adnlFilterPart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns, String wherePart, String searchCriteria, String sortPart,
			int limitRows, int offset, String adnlFilterPart, List<DataTableColumns> columnList) throws Exception {

		if (searchCriteria != "" && searchCriteria != null) {

			wherePart = "WHERE " + "("+getTable()+".ref_no LIKE '%" + searchCriteria + "%' "
					+ "OR "+getTable()+".disposal_date LIKE '%" + searchCriteria + "%' "
					+ "OR "+getTable()+".disposal_by LIKE '%" + searchCriteria + "%' "
					+ "OR "+getTable()+".total_amount LIKE '%" + searchCriteria + "%' "
					+ "OR  mrp_department.`name` LIKE '%" + searchCriteria + "%')";
		}

		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart != null && wherePart != "")
				wherePart += " AND " + adnlFilterPart;
			else 
				wherePart = " WHERE " + adnlFilterPart;			
		}

		String sql = "SELECT "
				+ getTable()+".`id`,"+getTable()+".`disposal_by`,"+getTable()+".`disposal_date`,"
				+ getTable()+".`ref_no`," + getTable()+".`total_amount`,"+getTable()+".`department_id`,"
				+ "mrp_department.`name` AS department_name "
				+ "FROM "
				+  getTable() +" " 
				+ "INNER JOIN mrp_department on "+getTable()+".department_id=mrp_department.id  "
				+ wherePart + " " + sortPart + " " + "LIMIT " + limitRows + " "
				+ "OFFSET " + offset + "";
		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,
			String searchCriteria, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {

		if (searchCriteria != "" && searchCriteria != null) {

			wherePart = "WHERE " + "("+getTable()+".ref_no LIKE '%"
					+ searchCriteria + "%' "
					+ "OR "+getTable()+".disposal_date LIKE '%"
					+ searchCriteria + "%' "
					+ "OR "+getTable()+".disposal_by LIKE '%"
					+ searchCriteria + "%' "
					+ "OR "+getTable()+".total_amount LIKE '%"
					+ searchCriteria + "%' "
					+ "OR  mrp_department.`name` LIKE '%" + searchCriteria
					+ "%')";
		}

		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart != null && wherePart != "") {

				wherePart += " AND " + adnlFilterPart;
			} else {

				wherePart = " WHERE " + adnlFilterPart;
			}
		}

		String sqlCount = "SELECT "
				+ "COUNT("
				+ getTable()
				+ ".id) as row_count "
				+ "FROM "
				+ getTable()
				+ " "
				+ "INNER JOIN mrp_department ON "+getTable()+".department_id=mrp_department.id  "
				+ wherePart + "";

		CachedRowSet totalRecs = executeSQLForRowset(sqlCount);
		return totalRecs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.stock.stockdisposal.dao.IStockDisposalDao#
	 * getStockDispDtlData
	 * (com.indocosmo.mrp.web.stock.stockdisposal.model.StockDisposal)
	 */
	@Override
	public JsonArray getStockDispDtlData(StockDisposal stockDisp)
			throws Exception {

		String sql = "SELECT "
				+ "a.*,srd.id as stockRegDtl_id "
				+ "FROM "
				+ "(SELECT "
				+ "sdd.id AS id,sdd.stock_disposal_hdr_id AS stock_disposal_hdr_id,"
				+ "sdd.stock_item_id AS stock_item_id,sdd.dispose_qty AS dispose_qty,sdd.cost_price AS cost_price,"
				+ "sdd.reason AS reason,stkItm.`code` AS stock_item_code,stkItm.`name` AS stock_item_name,"
				+ "uom. CODE AS uomcode,srh.id AS stkHdrId "
				+ "FROM "
				+ ""+getTable()+" sdh "
				+ "INNER JOIN mrp_stock_disposal_dtl sdd ON sdh.id = sdd.stock_disposal_hdr_id "
				+ "INNER JOIN mrp_stock_item stkItm ON stkItm.id = sdd.stock_item_id "
				+ "LEFT JOIN uoms uom ON stkItm.uom_id = uom.id "
				+ "INNER JOIN mrp_stock_register_hdr srh ON srh.ext_ref_no = sdh.ref_no "
				+ "WHERE "
				+ "sdd.stock_disposal_hdr_id = "
				+ stockDisp.getId()
				+ " )a  "
				+ "INNER JOIN  mrp_stock_register_dtl srd ON srd.stock_register_hdr_id=a.stkHdrId AND srd.ext_ref_dtl_id=a.id";

		return getTableRowsAsJson(sql);
	}

}
