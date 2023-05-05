package com.indocosmo.mrp.web.stock.stockadjustments.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.stock.stockadjustments.model.StockAdjustment;

public class StockAdjustmentDao extends GeneralDao<StockAdjustment> implements
		IStockAdjustmentDao {

	public StockAdjustmentDao(ApplicationContext context) {

		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public StockAdjustment getNewModelInstance() {

		return new StockAdjustment();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "mrp_stock_adjust_hdr";
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

			if (col.getSearchValue() != "" && col.getSearchValue() != null) {

				adnlFilterPart += ((adnlFilterPart.isEmpty()) ? " " : " AND ")
						+ "" + getTable() + "." + col.getData() + " = ('"
						+ col.getSearchValue() + "')";
			}
		}
		return adnlFilterPart;
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

		if (wherePart == null || wherePart == "") {

			wherePart = "  ";
		} else {

			wherePart += ")  ";
		}

		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart == null || wherePart == "  ") {

				wherePart += " WHERE  " + adnlFilterPart;

			} else {

				wherePart += " AND " + adnlFilterPart;

			}
		}

		String sqlCount = "SELECT COUNT(id) as row_count FROM " + getTable()
				+ " " + wherePart + "";
		CachedRowSet totalRecs = executeSQLForRowset(sqlCount);
		return totalRecs;
	}

	// function to override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * int, int, java.lang.String, java.util.List)
	 */
	public JsonArray getTableRowsAsJsonForDataTable(String columns,
			String wherePart, String searchCriteria, String sortPart,
			int limitRows, int offset, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {

		if (wherePart == null || wherePart == "") {

			wherePart = "  ";
		} else {

			wherePart += ")  ";
		}

		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart == null || wherePart == "  ") {

				wherePart += " WHERE  " + adnlFilterPart;

			} else {

				wherePart += " AND " + adnlFilterPart;

			}
		}

		String sql = "SELECT " + getTable() + ".ref_no," + getTable() + ".id,"
				+ getTable() + ".department_id," + getTable() + ".adjust_date,"
				+ getTable() + ".adjust_by," + getTable() + ".approval_date,"
				+ getTable() + ".approval_by," + getTable() + ".remarks,"
				+ "mrp_department.`name` as department_name " + "FROM "
				+ getTable() + "  "
				+ "LEFT JOIN mrp_department ON mrp_department.id=" + getTable()
				+ ".department_id  " + wherePart + " " + sortPart + " "
				+ "LIMIT " + limitRows + " " + "OFFSET " + offset + "";

		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.stock.stockadjustments.dao.IStockAdjustmentDao#
	 * getStockAdjstDtlData
	 * (com.indocosmo.mrp.web.stock.stockadjustments.model.StockAdjustment)
	 */
	@Override
	public JsonArray getStockAdjstDtlData(StockAdjustment stockAdjst)
			throws Exception {

		String sql = "SELECT "
				+ "stockdtl.*,uom.code as uomcode "
				+ "FROM  "
				+ "mrp_stock_adjust_dtl stockdtl "
				+ "LEFT JOIN mrp_stock_item stkitm on stockdtl.stock_item_id = stkitm.id "
				+ "LEFT JOIN uoms uom ON uom.id = stkitm.uom_id " + "WHERE "
				+ "stock_adjust_hdr_id= " + stockAdjst.getId();

		return getTableRowsAsJson(sql);
	}

}
