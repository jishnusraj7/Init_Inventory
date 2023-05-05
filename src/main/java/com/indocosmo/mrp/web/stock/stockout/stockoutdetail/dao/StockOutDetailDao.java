package com.indocosmo.mrp.web.stock.stockout.stockoutdetail.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.model.StockOutDetail;

public class StockOutDetailDao extends GeneralDao<StockOutDetail> implements IStockOutDetailDao{

	/**
	 * @param context
	 */
	public StockOutDetailDao(ApplicationContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public StockOutDetail getNewModelInstance() {
		
		return new StockOutDetail();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {
		
		return "mrp_stock_out_dtl";
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(java.util.List)
	 */
	@Override
	public void save(List<StockOutDetail> itemList) throws Exception {
		String stockODtlIdList = "";
		String wherePart = "";

		final Integer stockOutId = itemList.get(0).getStockOutHdrId();

		for (StockOutDetail stockOutItem : itemList) {

			if (stockOutItem.getStockOutHdrId() != null) {
				if(stockOutItem.getId() != null){
					stockODtlIdList += ((stockODtlIdList.isEmpty()) ? "" : ",")
							+ stockOutItem.getId();
				}
			}
		}
		if (stockODtlIdList.length() > 0) {
			if(!stockODtlIdList.equals(null)){
				wherePart = " AND id not in (" + stockODtlIdList + ")";
			}else{
				wherePart = "";
			}
			
		}

		final String markAsDeletedSQl = "DELETE FROM " + getTable()+ "  WHERE stock_out_hdr_id=" + stockOutId	+ "" + wherePart + ";";
		
		beginTrans();
		try {

			executeSQL(markAsDeletedSQl);
			
			for (StockOutDetail stockOutItem : itemList) {

				if (stockOutItem.getStockOutHdrId() != null && stockOutItem.getId() != null) {
					super.update(stockOutItem);
				}else{
					super.insert(stockOutItem);
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
	 * @see com.indocosmo.posellaweb.web.common.base.dao.IMasterBaseDao#delete(java.lang.String)
	 */
	public Integer delete(String  where) throws Exception {

		final String sql="DELETE FROM " + getTable()+  "  WHERE "+where;
		Integer is_deleted = 0;

		beginTrans();
		try{

			is_deleted = executeSQL(sql);
			endTrans();
		}catch (Exception e){

			rollbackTrans();
			throw e;
		}

		return is_deleted;

	}

	/**
	 * @param stockoutHdrId
	 * @return
	 * @throws Exception
	 */
	public List<StockOutDetail> getStockOutDtlData(Integer stockoutHdrId) throws Exception {
		
		final String SQL="SELECT stock_out_dtl.id,stock_out_dtl.stock_item_name,stock_out_dtl.request_qty,stock_out_dtl.issued_qty,stock_out_dtl.cost_price,stock_out_dtl.amount"
							+" FROM mrp_stock_out_dtl stock_out_dtl WHERE stock_out_hdr_id="+stockoutHdrId;
		List<StockOutDetail> list=null;
		StockOutDetail item=null;

		CachedRowSet rs= getRowSet(SQL);

		if(rs!=null){

			list=new ArrayList<StockOutDetail>();

			while(rs.next()){
			    item=getNewModelInstance();
				item.setId(rs.getInt("id"));
				item.setStockItemName(rs.getString("stock_item_name"));
				item.setRequestQty(rs.getDouble("request_qty"));
				item.setDeliveredQty(rs.getDouble("issued_qty"));
				item.setUnitPrice(rs.getDouble("cost_price"));
				item.setAmount(rs.getDouble("amount"));
				list.add(item);
			}
		}

		return list;
		
	}
	//Added By Udhay on  31st Aug 2021 for StockOut Data Issue
	@Override
	public Double getUnitPrice(String item_code) throws Exception {
		//System.out.println("item_code is ==========================================================================>"+item_code);
		
		CachedRowSet resultSet;
		double unitPrice=0.0;	
		final String sql = "SELECT unit_price FROM stock_items WHERE CODE="+item_code+" ";
			
	resultSet = dbHelper.executeSQLForRowset(sql);
	if (resultSet.next()) {
		unitPrice=resultSet.getDouble("unit_price");
	
	}
	//System.out.println("unitprice is ==========================================================================>"+unitPrice);
	
	return unitPrice;
	}
		
}
