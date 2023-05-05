package com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.model.ItemMasterBatch;

/**
 * @author jo
 *
 */
public class ItemMasterBatchDao extends GeneralDao<ItemMasterBatch> implements IItemMasterBatchDao {
	
	public ItemMasterBatchDao(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.MRP.web.core.base.dao.GeneralDao#getNewModelInstance() */
	@Override
	public ItemMasterBatch getNewModelInstance() {
	
		return new ItemMasterBatch();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson() */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
	
		final String sql = "SELECT * FROM " + getTable();
		
		return getTableRowsAsJson(sql);
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable() */
	@Override
	public String getTable() {
	
		return "mrp_stock_item_batch";
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.dao.
	 * IItemMasterBatchDao#upadteItemMasterBatch(java.util.List) */
	public void upadteItemMasterBatch(List<ItemMasterBatch> itemList) throws Exception {
	
		beginTrans();
		try {
			
			for (ItemMasterBatch itemMasterBatch : itemList) {
				
				final String sql = "UPDATE " 
			                       + getTable() + " "
			                       + "SET stock=" + itemMasterBatch.getStock() + ",cost_price="+ itemMasterBatch.getCostPrice() +" " 
			                       +" WHERE "
			                       + "stock_item_id=" + itemMasterBatch.getStockItemId();
				
				if (itemMasterBatch.getStockItemId() != null) {
					executeSQL(sql);
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
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.dao.
	 * IItemMasterBatchDao
	 * #getCurrentStockInBatch(com.indocosmo.mrp.web.masters.itemmaster
 */
	@Override
	public Double getCurrentStockInBatch(ItemMasterBatch itemMasterBatch) throws Exception {
	
		Double cur_Stock_qty = 0.00;
		if (itemMasterBatch.getId() != null) {
			final String sql = "SELECT stock FROM " + getTable() + "  WHERE id=" + itemMasterBatch.getId() + "";
			try {
				final CachedRowSet srs = executeSQLForRowset(sql);
				if (srs != null) {
					
					if (srs.next()) {
						
						cur_Stock_qty = srs.getDouble("stock");
					}
					
				}
				
			}
			catch (Exception e) {
				
				throw e;
			}
			
		}
		
		return cur_Stock_qty;
	}
	
	/**
	 * @param itemMasterBatch
	 * @return
	 * @throws Exception
	 */
	public Double currentStock(ItemMasterBatch itemMasterBatch) throws Exception {
	
		Double cur_Stock_qty = 0.00;
		if (itemMasterBatch.getStockItemId() != null) {
			
			final String sql = "SELECT stock FROM " + getTable() + "  WHERE stock_item_id="+ itemMasterBatch.getStockItemId() + "";
			
			try {
				
				final CachedRowSet srs = executeSQLForRowset(sql);
				
				if (srs != null) {
					
					if (srs.next()) {
						
						cur_Stock_qty = srs.getDouble("stock");
					}
					
				}
				
			}
			catch (Exception e) {
				
				throw e;
			}
			
		}
		
		return cur_Stock_qty;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getMastersRowJson() */
	@Override
	public JsonArray getMastersRowJson() throws Exception {
	
		final String sql = "SELECT id,stock_item_id,stock,cost_price,selling_price FROM " + getTable();
		
		return getTableRowsAsJson(sql);
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.dao.
	 * IItemMasterBatchDao#getBatchJsonArray(int) */
	@Override
	public JsonArray getBatchJsonArray(int stockItemId) throws Exception {
	
		final String sql = "SELECT * FROM mrp_stock_item_batch WHERE stock_item_id=" + stockItemId;
		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#getHqTableRowListToImport
	 * () */
	@Override
	public List<ItemMasterBatch> getHqTableRowListToImport() throws Exception {
	
		final String sql = "SELECT * FROM hq_stock_item_batch WHERE id NOT IN (SELECT id from mrp_stock_item_batch)";
		
		return buildItemList(sql);
	}
	
	/**
	 * @param stockItemId
	 * @return
	 * @throws Exception
	 */
	public List<ItemMasterBatch> getUpdatedBatchListToImport(int stockItemId) throws Exception {
	
		final String sql = "SELECT * FROM hq_stock_item_batch WHERE stock_item_id=" + stockItemId;
		
		return buildItemList(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.dao.
	 * IItemMasterBatchDao#deleteData(java.lang.Integer) */
	public void deleteData(Integer idss) throws Exception {
	
		final String sql = "DELETE FROM " + getTable() + " WHERE stock_item_id='" + idss + "'";
		executeSQL(sql);
	}
	
}
