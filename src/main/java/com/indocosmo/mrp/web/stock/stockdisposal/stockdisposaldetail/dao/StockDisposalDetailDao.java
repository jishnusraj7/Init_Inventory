package com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.model.ItemMasterBatch;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.service.ItemMasterBatchService;
import com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.model.StockDisposalDetail;

/**
 * @author jo
 *
 */
public class StockDisposalDetailDao extends GeneralDao<StockDisposalDetail>
		implements IStockDisposalDetailDao {

	/**
	 * @param context
	 */
	public StockDisposalDetailDao(ApplicationContext context) {

		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public StockDisposalDetail getNewModelInstance() {

		return new StockDisposalDetail();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {

		return "mrp_stock_disposal_dtl";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(java.util.List)
	 */
	@Override
	public void save(List<StockDisposalDetail> itemList) throws Exception {

		Double cur_stock_in_batch = 0.00;
		CachedRowSet resultSet;
		String stockDisposalDtlIdList = "";
		String stockDisposalDtlItemIdList = "";
		String wherePart = "";
		String wher = "";
		final Integer stockDisposalId = itemList.get(0).getStockDisposalHdrId();

		for (StockDisposalDetail stockDisposalItem : itemList) {

			if (stockDisposalItem.getStockDisposalHdrId() != null) {

				if (stockDisposalItem.getId() != null) {

					stockDisposalDtlIdList += ((stockDisposalDtlIdList
							.isEmpty()) ? "" : ",") + stockDisposalItem.getId();
				}

				if (stockDisposalItem.getStockItemId() != null) {
					stockDisposalDtlItemIdList += ((stockDisposalDtlItemIdList
							.isEmpty()) ? "" : ",")
							+ stockDisposalItem.getStockItemId();
				}

			}
		}
		if (stockDisposalDtlIdList.length() > 0) {
			if (!stockDisposalDtlIdList.equals("null")) {
				wherePart = " AND id not in (" + stockDisposalDtlIdList + ")";

			} else {
				wherePart = "";
			}

		}

		if (stockDisposalDtlItemIdList.length() > 0) {
			if (!stockDisposalDtlItemIdList.equals("null")) {
				wher = " AND stock_item_id not in ("
						+ stockDisposalDtlItemIdList + ")";

			} else {
				wher = "";
			}

		}
		final ItemMasterBatchService itemMasterBatchService = new ItemMasterBatchService(
				getContext());

		final String dmgQtySQl = "SELECT  stock_item_id,dispose_qty FROM" + " "
				+ getTable() + "  WHERE stock_disposal_hdr_id="
				+ stockDisposalId + "" + wher + ";";
		resultSet = dbHelper.executeSQLForRowset(dmgQtySQl);

		while (resultSet.next()) {

			final ItemMasterBatch itemMasterBatch = new ItemMasterBatch();
			itemMasterBatch.setStockItemId(resultSet.getInt("stock_item_id"));

			cur_stock_in_batch = itemMasterBatchService
					.currentStock(itemMasterBatch);

			final String itmMastUpdateSQl = "UPDATE mrp_stock_item_batch SET stock="
					+ (cur_stock_in_batch + resultSet.getDouble("dispose_qty"))
					+ "WHERE stock_item_id="
					+ resultSet.getInt("stock_item_id");

			executeSQL(itmMastUpdateSQl);
		}
		final String markAsDeletedSQl = "DELETE FROM " + getTable()
				+ "  WHERE stock_disposal_hdr_id=" + stockDisposalId + ""
				+ wherePart + ";";

		beginTrans();

		try {

			executeSQL(markAsDeletedSQl);

			for (StockDisposalDetail stockDisposalItem : itemList) {

				if (stockDisposalItem.getStockDisposalHdrId() != null
						&& stockDisposalItem.getId() != null) {
					super.update(stockDisposalItem);
				} else {
					super.insert(stockDisposalItem);
				}
			}
			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}

	}

	@Override
	public Integer delete(String where) throws Exception {

		final String sql = "DELETE FROM " + getTable() + "  WHERE " + where;
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

	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql = "SELECT * FROM " + getTable();

		return getTableRowsAsJson(sql);
	}

}
