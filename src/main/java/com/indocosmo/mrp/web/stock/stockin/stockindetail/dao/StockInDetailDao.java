package com.indocosmo.mrp.web.stock.stockin.stockindetail.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.purchaseordersType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.model.StockInDetail;


public class StockInDetailDao extends GeneralDao<StockInDetail> implements IStockInDetailDao{

	public StockInDetailDao(ApplicationContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	/**
	 * @return
	 */
	@Override
	public StockInDetail getNewModelInstance() {
		
		return new StockInDetail();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {
		
		return "mrp_stock_in_dtl";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(java.util.List)
	 */
	@Override
	public void save(List<StockInDetail> itemList) throws Exception {
		
		String stockInDtlIdList = "";
		String stockItemIdList = "";
		String wherePartForStkDtlIds = "";
		String wherePartForStkItmIds = "";

		final Integer stockInId = itemList.get(0).getStockInHdrId();

		for (StockInDetail stockInItem : itemList) {

			if (stockInItem.getStockInHdrId() != null) {
				
				if(stockInItem.getId() != null)
					stockInDtlIdList += ((stockInDtlIdList.isEmpty()) ? "" : ",")+ stockInItem.getId();				
			}
			if (stockInItem.getStockInHdrId() != null) {
				
				if(stockInItem.getStockItemId() != null)
					stockItemIdList += ((stockItemIdList.isEmpty()) ? "" : ",")	+ stockInItem.getStockItemId();				
			}
		}
		
		if (stockInDtlIdList.length() > 0) {
			
			if(!stockInDtlIdList.equals(null)){
				
				wherePartForStkDtlIds = " AND id not in (" + stockInDtlIdList + ")";
			}
			else {
				
				wherePartForStkDtlIds = "";
			}			
		}
		
		if (stockItemIdList.length() > 0) {
			
			if(!stockItemIdList.equals(null)){
				
				wherePartForStkItmIds = "  AND stock_item_id NOT IN ("+stockItemIdList+")";
			}
			else {
				
				wherePartForStkItmIds = "";
			}
		}

		final String DeleteSQl = "DELETE FROM " + getTable()+ "  WHERE stock_in_hdr_id=" + stockInId + "" + wherePartForStkDtlIds + ";";
		
		beginTrans();
		try {

			executeSQL(DeleteSQl);
			
			for (StockInDetail stockInItem : itemList) {
				
				if(stockInItem.getPoId() != null && stockInItem.getStockInHdrId() != null){
					
					final String DeletePODtlSQL = "DELETE FROM " + getTable()+ "  WHERE stock_in_hdr_id=" + stockInId + " AND po_id="+ stockInItem.getPoId() +""+ wherePartForStkItmIds +"";
					executeSQL(DeletePODtlSQL);
					
					final String UpdatePoHdrStatus = "UPDATE mrp_po_hdr "
							                       + "SET po_status="+purchaseordersType.PROCESSING.getPurchaseorderTypeId()+""
							                       + " WHERE id="+stockInItem.getPoId()+" ";
					executeSQL(UpdatePoHdrStatus);
					
					if(stockInItem.getPoQty() <= stockInItem.getReceivedQty()){
						
						final String UpdatePoOdrStatusSQL = "UPDATE mrp_po_dtl SET po_dtl_status=1 WHERE po_hdr_id="+stockInItem.getPoId()+" AND stock_item_id="+stockInItem.getStockItemId()+"";
						executeSQL(UpdatePoOdrStatusSQL);
					}
				}
				
				if (stockInItem.getStockInHdrId() != null && stockInItem.getId() != null) {
					
					super.update(stockInItem);
				}else{
					
					super.insert(stockInItem);
				}
			}
			
			for (StockInDetail stockInItem : itemList) {
				
				if(stockInItem.getDeletedPOItems() !=null){
					
					if(stockInItem.getDeletedPOItems().length() > 0){
						
						final String updateFineshedStatus="UPDATE mrp_po_dtl SET po_dtl_status=0 WHERE id in("+stockInItem.getDeletedPOItems()+")";
						executeSQL(updateFineshedStatus);
					}
					break;
				}
			}
			
			endTrans();

		} 
		catch (Exception e) {

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

		final String sql= "DELETE FROM " + getTable()+  "  WHERE "+where;
		Integer is_deleted = 0;
		
		try {
			
			beginTrans();
			
			is_deleted = executeSQL(sql);
			final String isPoExistSQL = "SELECT COUNT(po_id) AS row_count FROM "+getTable()+" WHERE "+where;
			final Integer rowCount = excuteSQLForRowCount(isPoExistSQL);
			if(rowCount > 0){
				final String updatePoDtlStatus = "UPDATE mrp_po_dtl SET po_dtl_status=0 WHERE po_hdr_id in (SELECT stkdtl.po_id FROM "+getTable()+" AS stkdtl WHERE "+where+")";
				executeSQL(updatePoDtlStatus);
			}
		
			endTrans();
		}
		catch (Exception e){

			rollbackTrans();
			throw e;
		}

		return is_deleted;
	}

	public ArrayList<StockInDetail> getstockInDtlData(Integer stockinHdrId) throws Exception {
	
		final String SQL="SELECT id,stock_item_name,received_qty,unit_price,amount,tax_amount,po_qty "
						+ "FROM mrp_stock_in_dtl "
						+ "WHERE stock_in_hdr_id="+stockinHdrId;
		
		ArrayList<StockInDetail> list=null;
		StockInDetail item=null;
		
		CachedRowSet rs= getRowSet(SQL);
		
		if(rs!=null) {
			
			list=new ArrayList<StockInDetail>();		
			while(rs.next()){
				
				item=getNewModelInstance();
				item.setId(rs.getInt("id"));
				item.setStockItemName(rs.getString("stock_item_name"));
				item.setReceivedQty(rs.getDouble("received_qty"));
				item.setUnitPrice(rs.getDouble("unit_price"));
				item.setAmount(rs.getDouble("amount"));
				item.setTaxAmount(rs.getDouble("tax_amount"));
				item.setPoQty(rs.getDouble("po_qty"));
				list.add(item);
			}
		}
		
		return list;
	}
}
