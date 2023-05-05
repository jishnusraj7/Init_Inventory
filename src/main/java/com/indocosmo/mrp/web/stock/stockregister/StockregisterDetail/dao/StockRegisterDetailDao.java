package com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail;


public class StockRegisterDetailDao extends GeneralDao<StockRegisterDetail> implements IStockRegisterDetailDao{

	/**
	 * @param context
	 */
	public StockRegisterDetailDao(ApplicationContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public StockRegisterDetail getNewModelInstance() {

		return new StockRegisterDetail();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "mrp_stock_register_dtl";
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(java.util.List, javax.servlet.http.HttpSession)
	 */
	@Override
	public void save(List<StockRegisterDetail> itemList) throws Exception {

		String stockInRegDtlIdList = "";
		String poIdsList = "";
		String wherePart = "";

		final Integer stockRegId = itemList.get(0).getStockRegHdrid();

		for (StockRegisterDetail stockRegDtlItem : itemList) {

			if (stockRegDtlItem.getStockRegHdrid() != null) {
				if(stockRegDtlItem.getId() != null){
					stockInRegDtlIdList += ((stockInRegDtlIdList.isEmpty()) ? "" : ",")
							+ stockRegDtlItem.getId();
				}

			}

			if (stockRegDtlItem.getStockRegHdrid() != null) {
				if(stockRegDtlItem.getPoId() != null){
					poIdsList += ((poIdsList.isEmpty()) ? "" : ",")
							+ stockRegDtlItem.getPoId();
				}

			}
		}
		if (stockInRegDtlIdList.length() > 0) {
			if(!stockInRegDtlIdList.equals(null)){
				wherePart = " AND id not in (" + stockInRegDtlIdList + ")";
			}else{
				wherePart = "";
			}
		}

		GettingCurrentDateTime dateTime = new GettingCurrentDateTime(getContext());
		String date = dateTime.getCurrentDate();

		beginTrans();
		try {

			if (poIdsList.length() > 0) {
				if(!poIdsList.equals(null)){
					final String POClosedSQL = "UPDATE mrp_po_hdr SET po_status=2,po_close_date='"+date+"' WHERE id IN ("+poIdsList+")";
					executeSQL(POClosedSQL);
					final String PODtlClosedSQL = "UPDATE mrp_po_dtl SET po_dtl_status=2 WHERE po_hdr_id IN  ("+poIdsList+")";
					executeSQL(PODtlClosedSQL);
				}
			}

			final String markAsDeletedSQl = "DELETE FROM " + getTable()+ "  WHERE stock_register_hdr_id=" + stockRegId	+ "" + wherePart + ";";


			executeSQL(markAsDeletedSQl);

			for (StockRegisterDetail stockRegDtlItem : itemList) {

				if (stockRegDtlItem.getStockRegHdrid() != null && stockRegDtlItem.getId() != null) {
					super.update(stockRegDtlItem);
				}else{
					super.insert(stockRegDtlItem);
				}
			}

			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		final String sql="SELECT * FROM " + getTable();

		return getTableRowsAsJson(sql);
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.dao.IStockRegisterDetailDao#getOutQtyInRegDtl(com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail)
	 */
	@Override
	public Double getOutQtyInRegDtl(StockRegisterDetail stockRegDtl) throws Exception {
	
		Double curOutQty = 0.00;
		if(stockRegDtl.getId() != null){

			final String sql="SELECT "
							+ "out_qty "
							+ "FROM "
							+  getTable()+  "  "
							+ "WHERE "
							+ "id="+stockRegDtl.getId()+" AND stock_register_hdr_id="+stockRegDtl.getStockRegHdrid()+" "
							+ "AND stock_item_id="+stockRegDtl.getStockItemId()+"";
			try{
				
				final CachedRowSet srs=executeSQLForRowset(sql);
				
				if(srs!=null){

					if(srs.next()){

						curOutQty= srs.getDouble("out_qty");
					}

				}

			}catch (Exception e){

				throw e;
			}

		}

		return curOutQty;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.dao.IStockRegisterDetailDao#getInQtyInRegDtl(com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail)
	 */
	@Override
	public Double getInQtyInRegDtl(StockRegisterDetail stockRegDtl) throws Exception {
		
		Double curInQty = 0.00;
		if(stockRegDtl.getId() != null){
			
			final String sql="SELECT "
				           	 + "in_qty "
				           	 + "FROM "
				           	 + getTable()+  "  "
				           	 + "WHERE "
				           	 + "id="+stockRegDtl.getId()+" AND stock_register_hdr_id="+stockRegDtl.getStockRegHdrid()+" "
				           	 + "AND stock_item_id="+stockRegDtl.getStockItemId()+"";
		
			try{
				
				final CachedRowSet srs=executeSQLForRowset(sql);
				
				if(srs!=null){

					if(srs.next()){

						curInQty= srs.getDouble("in_qty");
					}

				}

			}catch (Exception e){

				throw e;
			}

		}

		return curInQty;
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#delete(java.lang.String)
	 */
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
