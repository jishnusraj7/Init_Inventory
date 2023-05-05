package com.indocosmo.mrp.web.stock.stocktransfer.stocktransferdetail.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.stock.stocktransfer.stocktransferdetail.model.StockTransDetail;


public class StockTransDetailDao  extends GeneralDao<StockTransDetail> implements IStocktransDetailDao{

	public StockTransDetailDao(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public StockTransDetail getNewModelInstance() {
	
		// TODO Auto-generated method stub
		return new StockTransDetail();
	}

	@Override
	protected String getTable() {
	
		// TODO Auto-generated method stub
		return "mrp_stock_transfer_dtl";
	}
	
	
	@Override
	public void save(List<StockTransDetail> itemList) throws Exception {

		
		CachedRowSet resultSet;
		String stockTransferDtlIdList = "";
		String wherePart = "";
		String wher = "";
		final Integer stockTransferId = itemList.get(0).getStock_transfer_hdr_id();

		for (StockTransDetail stockTransferItem : itemList) {

			if (stockTransferItem.getStock_transfer_hdr_id() != null) {

				if (stockTransferItem.getId() != null) {

					stockTransferDtlIdList += ((stockTransferDtlIdList
							.isEmpty()) ? "" : ",") + stockTransferItem.getId();
				}

			}
		}
		if (stockTransferDtlIdList.length() > 0) {
			if (!stockTransferDtlIdList.equals("null")) {
				wherePart = " AND id not in (" + stockTransferDtlIdList + ")";

			} else {
				wherePart = "";
			}

		}

		final String markAsDeletedSQl = "DELETE FROM " + getTable()
				+ "  WHERE stock_transfer_hdr_id=" + stockTransferId + ""
				+ wherePart + ";";

		beginTrans();

		try {

			executeSQL(markAsDeletedSQl);

			for (StockTransDetail stockTransferItem : itemList) {

				if (stockTransferItem.getStock_transfer_hdr_id() != null
						&& stockTransferItem.getId() != null) {
					super.update(stockTransferItem);
				} else {
					super.insert(stockTransferItem);
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


	
}
