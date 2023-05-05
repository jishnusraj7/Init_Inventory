package com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.model.ItemProdCost;

/**
 * @author jo
 *
 */
public class ItemProdCostDao extends GeneralDao<ItemProdCost> implements IItemProdCostDao {
	
	/**
	 * @param context
	 */
	public ItemProdCostDao(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public ItemProdCost getNewModelInstance() {
	
		return new ItemProdCost();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getList()
	 */
	@Override
	public List<ItemProdCost> getList() throws Exception {
	
		String SQL;
		List<ItemProdCost> testbom;
		try {
			SQL = "SELECT * from " + getTable();
			testbom = buildItemList(SQL);
		}
		catch (Exception e) {
			throw e;
		}
		return testbom;
		
	}
	
	@Override
	public String getTable() {
	
		return "mrp_stock_item_prod_costcalc";
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao.IItemMasterBomDao#getBomJsonArray(int)
	 */
	public JsonArray getProdCostArray(int id) throws Exception {
	
		
		final String sql="SELECT "
				           + "prd.id,prd.costcalc_param_id AS prod_cost_id,"
				           + "prd.is_percentage,ROUND(prd.rate,2) as rate,cost.`code` as prod_cost_code,"
				           + "cost.name as prod_cost_name,"
				           + "(CASE(cost.cost_type) "
				           + "WHEN 0 THEN 'Materials' "
				           + "WHEN 1 THEN 'Labour' "
				           + "WHEN 2 THEN 'Others' "
				           + "ELSE '' end )as prod_cost_type "
				           + "FROM "+getTable()+" prd "
				           + "LEFT JOIN mrp_prod_costcalc_params cost "
				           + "ON prd.costcalc_param_id=cost.id "
				           + "WHERE prd.stock_item_id="+id+" "
				           + " AND IFNULL(prd.is_deleted,0) = 0";

		
		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(java.util.List)
	 */
	public void save(List<ItemProdCost> itemList) throws Exception {
	
		String itemBomItemList = "";
		String wherePart = "";
		String syncWhere="";
		
		final Integer itemBomstockId = itemList.get(0).getStockItemId();
		
		for (ItemProdCost bomItem : itemList) {
			
			if (bomItem.getStockItemId() != null) {
				if (bomItem.getId() != null) {
					itemBomItemList += ((itemBomItemList.isEmpty()) ? "" : ",") + bomItem.getId();
				}
				
			}
		}
		if (itemBomItemList.length() > 0) {
			// wherePart = " AND bom_item_id not in (" + itemBomItemList + ")";
			wherePart = " AND id NOT IN (" + itemBomItemList + ")";
			syncWhere="AND calc.id NOT IN("+ itemBomItemList + ")";
		}
		
		final String markAsDeletedSQl = "UPDATE " + getTable() + " SET is_deleted=1  WHERE stock_item_id=" + itemBomstockId + ""
				+ wherePart + ";";
		
		final String updateSyncQueue="UPDATE sync_queue SET crud_action='D' "
				                    + "WHERE record_id IN "
				                    + "(SELECT calc.id FROM "+getTable()+" calc "
				                    + "WHERE calc.stock_item_id="+ itemBomstockId + " "
				                    + ""+syncWhere+")AND table_name='"+getTable()+"'";
		beginTrans();
		
		try {
			executeSQL(updateSyncQueue);
		    executeSQL(markAsDeletedSQl);
		    
		    
			for (ItemProdCost itemBomsItem : itemList) {
				
				if (itemBomsItem.getStockItemId() != null && itemBomsItem.getId() != null) {
					super.update(itemBomsItem);
				}
				else {
					super.insert(itemBomsItem);
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
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#getHqTableRowListToImport
	 * () */
	@Override
	public List<ItemProdCost> getHqTableRowListToImport() throws Exception {
	
		final String sql = "SELECT * FROM hq_stock_item_bom WHERE id NOT IN (SELECT id from stock_item_bom)";
		
		return buildItemList(sql);
	}
	
	/**
	 * @param stockItemId
	 * @return
	 * @throws Exception
	 */
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao.IItemMasterBomDao#getUpdatedBomListToImport(int)
	 */
	public List<ItemProdCost> getUpdatedBomListToImport(int stockItemId) throws Exception {
	
		final String sql = "SELECT * FROM hq_stock_item_bom WHERE stock_item_id=" + stockItemId;
		return buildItemList(sql);
	}
	
	/**
	 * @param stockItemId
	 * @return
	 * @throws Exception
	 */
	public List<ItemProdCost> getBomListForItem(int stockItemId) throws Exception {
	
		final String sql = "SELECT * FROM stock_item_bom where stock_item_id= " + stockItemId;
		return buildItemList(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao.IItemMasterBomDao#deleteData(java.lang.Integer)
	 */
	public void deleteData(Integer idss) throws Exception {
	
		final String sql = "UPDATE " + getTable() + " SET is_deleted=1 where stock_item_id='" + idss + "'";
		executeSQL(sql);
	}
	
}
