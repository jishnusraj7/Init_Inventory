package com.indocosmo.mrp.web.stock.stockadjustments.stockadjustmentdetail.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.stock.stockadjustments.stockadjustmentdetail.model.StockAdjustmentDetail;


public class StockAdjustmentDetailDao extends GeneralDao<StockAdjustmentDetail> implements IStockAdjustmentDetailDao {

	public StockAdjustmentDetailDao(ApplicationContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public StockAdjustmentDetail getNewModelInstance() {

		return new StockAdjustmentDetail();
	}

	@Override
	protected String getTable() {

		return "mrp_stock_adjust_dtl";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(java.util.List)
	 */
	@Override
	public void save(List<StockAdjustmentDetail> itemList)
			throws Exception {
		Double cur_stock_in_batch = 0.00;
		CachedRowSet resultSet;
		String stockDisposalDtlIdList = "";
		String stockDisposalDtlItemIdList="";
		String wherePart = "";
		String wher="";
		final Integer stockDisposalId = itemList.get(0).getStock_adjust_hdr_id();
		for(StockAdjustmentDetail stockDisposalItem : itemList)
		{
			if (stockDisposalItem.getStock_adjust_hdr_id() != null) {
				if(stockDisposalItem.getId() != null){
					stockDisposalDtlIdList += ((stockDisposalDtlIdList.isEmpty()) ? "" : ",")
							+ stockDisposalItem.getId();
				}

				if(stockDisposalItem.getStock_item_id() != null){
					stockDisposalDtlItemIdList += ((stockDisposalDtlItemIdList.isEmpty()) ? "" : ",")
							+ stockDisposalItem.getStock_item_id();
				}

			}
		}
		if (stockDisposalDtlIdList.length() > 0) {
			if(!stockDisposalDtlIdList.equals("null")){
				wherePart = " AND id not in (" + stockDisposalDtlIdList + ")";

			}else{
				wherePart = "";
			}

		}

		if (stockDisposalDtlItemIdList.length() > 0) {
			if(!stockDisposalDtlItemIdList.equals("null")){
				wher = " AND stock_item_id not in (" + stockDisposalDtlItemIdList + ")";

			}else{
				wher = "";
			}

		}

		final String markAsDeletedSQl = "DELETE "
				+ "FROM "
				+ "" + getTable()+ "  "
				+ "WHERE "
				+ "stock_adjust_hdr_id=" + stockDisposalId	+ "" + wherePart + ";";

		beginTrans();


		try {

			executeSQL(markAsDeletedSQl);

			for (StockAdjustmentDetail stockDisposalItem : itemList) {

				if (stockDisposalItem.getStock_adjust_hdr_id() != null && stockDisposalItem.getId() != null) {
					super.update(stockDisposalItem);
				}else{
					super.insert(stockDisposalItem);
				}
			}
			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}

	}




	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#delete(java.lang.String)
	 */
	@Override
	public Integer delete(String where) throws Exception {
		final String sql = "DELETE "
				+ "FROM "
				+ ""+getTable() + "  "
				+ "WHERE " + where;
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

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql = "SELECT * "
				+ "FROM "
				+ "" + getTable();

		return getTableRowsAsJson(sql);
	}

}
