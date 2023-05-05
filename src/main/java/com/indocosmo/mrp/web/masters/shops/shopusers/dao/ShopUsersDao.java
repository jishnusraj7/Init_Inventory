package com.indocosmo.mrp.web.masters.shops.shopusers.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.shops.shopusers.model.ShopUsers;



public class ShopUsersDao extends GeneralDao<ShopUsers> implements IShopUsersDao{

	public ShopUsersDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShopUsers getNewModelInstance() {

		return new ShopUsers();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getList()
	 */
	@Override
	public List<ShopUsers> getList() throws Exception {
		String SQL;
		List<ShopUsers> testbom;
		try{
			SQL="SELECT * from "+getTable();
			testbom = buildItemList(SQL);
		}catch(Exception e){
			throw e;
		}
		return testbom;

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "shop_users";
	}

	public JsonArray getBomJsonArray(int id) throws Exception{
		final String sql = "select uom.code as uomcode,stock_item_bom.id,Round(stock_item_bom.cost_price,2) as unit_price,"
				+ "stock_item_bom.bom_item_id,stock_item_bom.qty,mrp_stock_item.code as bom_item_code,mrp_stock_item.name as bom_item_name "
				+ "from "+getTable()+" LEFT JOIN uoms uom on uom.id=(select uom_id FROM mrp_stock_item "
				+ "WHERE mrp_stock_item.id=stock_item_bom.stock_item_id) inner join mrp_stock_item on mrp_stock_item.id=stock_item_bom.bom_item_id "
				+ " where stock_item_id="+id;
		return getTableRowsAsJson(sql);
	}

	public void save(List<ShopUsers> itemList) throws Exception {
		String itemBomItemList = "";
		String wherePart = "";

		final Integer itemBomstockId = itemList.get(0).getShop_id();

		for (ShopUsers bomItem : itemList) {

			if (bomItem.getShop_id() != null) {
				if(bomItem.getId() != null){
					itemBomItemList += ((itemBomItemList.isEmpty()) ? "" : ",")
							+ bomItem.getId();
				}

			}
		}
		if (itemBomItemList.length() > 0) {
			//wherePart = " AND bom_item_id not in (" + itemBomItemList + ")";
			wherePart = " AND id not in (" + itemBomItemList + ")";
		}

		final String markAsDeletedSQl = "DELETE FROM " + getTable()
				+ "  WHERE stock_item_id=" + itemBomstockId
				+ "" + wherePart + ";";
		beginTrans();
		try {

			executeSQL(markAsDeletedSQl);
			/*if (itemBomItemList.length() > 0)
				super.save(itemList);*/
			for (ShopUsers itemBomsItem : itemList) {

				if (itemBomsItem.getShop_id() != null && itemBomsItem.getId() != null) {
					super.update(itemBomsItem);
				}else{
					super.insert(itemBomsItem);
				}
			}
			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getHqTableRowListToImport()
	 */
	@Override
	public List<ShopUsers> getHqTableRowListToImport()throws Exception{
		final String sql="SELECT * FROM hq_stock_item_bom WHERE id NOT IN (select id from stock_item_bom)";

		return buildItemList(sql);
	}

	/**
	 * @param stockItemId
	 * @return
	 * @throws Exception
	 */
	public List<ShopUsers> getUpdatedBomListToImport(int stockItemId) throws Exception{
		final String sql="SELECT * FROM hq_stock_item_bom WHERE stock_item_id="+stockItemId;	
		return buildItemList(sql);
	}

	public List<ShopUsers> getBomListForItem(int stockItemId) throws Exception
	{
		final String sql="SELECT * FROM stock_item_bom where stock_item_id= "+stockItemId;
		return buildItemList(sql);
	}

	public void deleteData(Integer idss) throws Exception{

		final String sql1="update "+getTable()+" set is_deleted=1 where shop_id="+idss+"";
		executeSQL(sql1);
	}
	@Override
	public Integer isShopUserExist(Integer shopId) throws Exception {
		String sql="select id from shop_users where shop_id="+shopId+" ";
		JsonArray jsonarry=getTableRowsAsJson(sql);
		if(jsonarry.size() != 0)
		{
		JsonObject obj=(JsonObject)jsonarry.get(0);
		return obj.get("id").getAsInt();
		}else return null;
	}

}
