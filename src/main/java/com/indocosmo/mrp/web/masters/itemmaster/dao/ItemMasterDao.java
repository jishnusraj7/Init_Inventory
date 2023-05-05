package com.indocosmo.mrp.web.masters.itemmaster.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.Hq;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;

public class ItemMasterDao extends GeneralDao<ItemMaster> implements IItemMasterDao {
	
	private ItemMasterDao itemasterDao;
	
	/**
	 * @param context
	 */
	public ItemMasterDao(ApplicationContext context) {
	
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public ItemMaster getNewModelInstance() {
	
		return new ItemMaster();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {
	
		return "mrp_stock_item";
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.dao.IItemMasterDao#getitemBomListToImport(java.lang.String)
	 */
	public List<ItemMasterBom> getitemBomListToImport(String ids) throws Exception {
	
		final String sql = "SELECT * FROM hq_stock_item_bom  WHERE stock_item_id in(" + ids + ")";
		
		List<ItemMasterBom> list = null;
		
		CachedRowSet rs = getRowSet(sql);
		
		if (rs != null) {
			
			list = new ArrayList<ItemMasterBom>();
			
			while (rs.next()) {
				
				ItemMasterBom item = new ItemMasterBom();
				DBUtil.setModelFromSRS(rs, item);
				list.add(item);
			}
		}
		
		return list;
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson() */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
	
	   final String sql = "SELECT si.id, si. CODE, si. NAME, uoms.`code` AS uomcode, uoms.`name` AS uomname, "
							+ "si.is_combo_item AS is_combo_item, si.is_manufactured, si.is_active, si.is_synchable, "
							+ "ic. NAME AS item_category_name, si.item_category_id, si.prd_department_id AS department_id, "
							+ "dep. NAME AS department_name, si.fixed_price, si.item_cost "
							+ "FROM " +  getTable() + " si "
							+ "LEFT JOIN mrp_department dep ON si.`prd_department_id` = dep.id "
							+ "LEFT JOIN mrp_item_category ic ON ic.id = si.item_category_id "
							+ "LEFT JOIN uoms ON si.uom_id = uoms.`id` "
							+ "WHERE "
							+ "(IFNULL(si.is_deleted, 0) = 0) AND si.is_manufactured <> 0"
							+ " ORDER BY si. NAME";
		
		
		//query without department new requirement @gana
		
		/*final String sql = "SELECT si.id, si. CODE, si. NAME, uoms.`code` AS uomcode, uoms.`name` AS uomname, "
				+ "si.is_combo_item AS is_combo_item, si.is_manufactured, si.is_active, si.is_synchable, "
				+ "ic. NAME AS item_category_name, si.item_category_id, "
				+ "si.fixed_price, si.item_cost "
				+ "FROM " +  getTable() + " si "
				+ "LEFT JOIN mrp_item_category ic ON ic.id = si.item_category_id "
				+ "LEFT JOIN uoms ON si.uom_id = uoms.`id` "
				+ "WHERE "
				+ "(IFNULL(si.is_deleted, 0) = 0) AND si.is_manufactured <> 0"
				+ " ORDER BY si. NAME";*/
		return getTableRowsAsJson(sql);
	}
	
	public JsonArray getItemDetails(int itemId) throws Exception {
	
		
		

		
		final String sql = "SELECT "
							+ "`id`, `code`,is_valid, hsn_code,`name`, whls_price,is_whls_price_pc,`description`, `item_thumb`, `uom_id`, `is_batch`,`is_barcode_print`,`pack_uom_id`, `is_manufactured`,is_hot_item_1, display_order,hot_item_1_display_order, is_hot_item_2, hot_item_2_display_order, is_hot_item_3, hot_item_3_display_order,"
							+ "`is_sellable`, `is_group_item`, `group_item_id`, `is_combo_item`, `is_open`, `is_require_weighing`,"
							+ " `sub_class_id`, `kitchen_id`, `choice_ids`, `profit_category_id`, `name_to_print`, `alternative_name`, "
							+ "`alternative_name_to_print`, `fg_color`, `bg_color`, `attrib1_name`, `attrib1_options`, `attrib2_name`, "
							+ "`attrib2_options`, `attrib3_name`,`attrib3_options`, `attrib4_name`, `attrib4_options`, `attrib5_name`, "
							+ "`attrib5_options`, `movement_method`, `valuation_method`, `pack_contains`, `shelf_life`, `input_tax_id`,"
							+ "`output_tax_id`, `tax_calculation_method`, `min_stock`, `max_stock`, `std_purchase_qty`,`pref_supplier_id`, "
							+ "`lead_time`,`is_active`,`is_synchable`,`item_category_id`,`output_tax_id_home_service`,`output_tax_id_table_service`,"
							+ "`output_tax_id_take_away_service`,`taxation_based_on`,`item_cost`,"
							+ "`fixed_price`,`sys_sale_flag` ,"
							+ "IFNULL(IFNULL((SELECT stock_item_qty FROM stock_item_bom sib WHERE sib.stock_item_id = "+itemId+" "
							+ " AND (IFNULL(sib.is_deleted, 0) = 0) LIMIT 1),"
							+ "(SELECT stock_item_qty FROM mrp_stock_item_prod_costcalc sip WHERE sip.stock_item_id = "+itemId+" AND (IFNULL(sip.is_deleted, 0) = 0) LIMIT 1)),0)"
							+ " AS bom_qty "
							+ " FROM "
							+  getTable() + " "
							+ "WHERE "
							+ "id=" + itemId;
					
		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.dao.IItemMasterDao#getDataToEditChoice(java.lang.String)
	 */
	public JsonArray getDataToEditChoice(String itemId) throws Exception {
	
		final String sql = "SELECT id,code,name FROM choices WHERE id in(" + itemId + ") and is_deleted=0";
		
		return getTableRowsAsJson(sql);
	}

	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getMastersRowJson() */
	@Override
	public JsonArray getMastersRowJson() throws Exception {

		itemasterDao = new ItemMasterDao(getContext());
		String costPrice = "";
		String costPriceQuery = "";
		ApplicationContext context = itemasterDao.getContext();
		SystemSettings systemSettings=(SystemSettings) context.getCurrentHttpSession().getAttribute("systemSettings");

		if (context.getCompanyInfo().getId() != 0) {

			costPrice = "'"+", round(ifnull(vw_itemrate.cost_price,0),2) as unit_price"+"'";
			costPriceQuery = "'"+"LEFT JOIN  vw_itemrate on vw_itemrate.stock_item_id = si.id"+"'";
		}
		else {
			costPrice = "'"+", round(IFNULL(si.item_cost,0),"+systemSettings.getDecimal_places()+") as unit_price"+"'";
			costPriceQuery=null;
		}

		/*final String sql = "SELECT	"
							+ "si.id,si.code,si.name,si.input_tax_id,"
							+ "vw.tax_pc AS tax_percentage,"
							+ "IFNULL(tx.tax1_percentage,0) AS tax_percentage,"
							+ "si.valuation_method,si.is_active,"
							+ "si.is_manufactured ,uoms.`code` as uomcode,uoms.`name` as uomname "+ costPrice+ " "
							+ "FROM "
							+ "mrp_stock_item si  "
							+ "LEFT JOIN vw_stockitem_tax vw ON si.id = vw.id  "
							+ "LEFT JOIN taxes tx ON si.`input_tax_id` = tx.id  "
							+ "LEFT JOIN uoms ON si.uom_id=uoms.`id` " + costPriceQuery+" "
							+ "WHERE "
							+ "si.is_system = 0 AND si.is_deleted = 0 AND si.is_active = 1 AND si.is_manufactured = 0 ";*/

		String sql="CALL usp_stock_item_data("+costPrice+", "+costPriceQuery+")";
		return getTableRowsAsJson(sql);

	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#
	 * getUpdatedHqTableRowListToImport() */
	@Override
	public List<ItemMaster> getUpdatedHqTableRowListToImport() throws Exception {
	
		final String sql = "SELECT "
							+ "hq_stock_item.* "
							+ "FROM "
							+ "hq_stock_item "
							+ "INNER JOIN mrp_stock_item on mrp_stock_item.id=hq_stock_item.id "
							+ "WHERE "
							+ "hq_stock_item.last_sync_at != mrp_stock_item.last_sync_at AND mrp_stock_item.is_synchable=1";

		return buildItemList(sql);
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#getUpdatedHqTableRowJson() */
	@Override
	public JsonArray getUpdatedHqTableRowJson() throws Exception {
	
		final String sql = "SELECT "
							+ "hq_stock_item.id,hq_stock_item.code,hq_stock_item.name,hq_stock_item.is_deleted "
							+ "FROM "
							+ "hq_stock_item "
							+ "INNER JOIN mrp_stock_item on mrp_stock_item.id=hq_stock_item.id "
							+ "WHERE "
							+ "hq_stock_item.last_sync_at != mrp_stock_item.last_sync_at and mrp_stock_item.is_synchable=1";
					
		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * int, int, java.lang.String, java.util.List) */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns , String wherePart , String searchCriteria ,
			String sortPart , int limitRows , int offset , String adnlFilterPart , List<DataTableColumns> columnList)
			throws Exception {
	
		if (wherePart == null || wherePart == "") {
			
			wherePart = "WHERE (IFNULL(si.is_deleted,0) = 0)";
		}
		else {
			if (searchCriteria != "" && searchCriteria != null)
				
				wherePart = "WHERE (IFNULL(si.is_deleted,0) = 0) "
								+ "AND (si.code LIKE '%" + searchCriteria+ "%' "
								+ "OR si.name LIKE '%" + searchCriteria + "%' "
								+ "OR si.is_manufactured LIKE '%" + searchCriteria + "%' "
								+ "OR ic.name LIKE '%" + searchCriteria+ "%' "
								+ "OR si.item_category_id LIKE '%" + searchCriteria + "%')";
		}
		
		if (adnlFilterPart != "" && adnlFilterPart != null) {
			
			if (wherePart != null && wherePart != "") {
				
				wherePart += " AND " + adnlFilterPart;
			}
			else {
				
				wherePart = " WHERE " + adnlFilterPart;
			}
		}
		String sql="";
		if(context.getCurrentHttpSession().getAttribute("COMPANY_ID").equals(Hq.HQ.getHqId()))
		{
		 sql = "SELECT  "
						+ "si.id as id,si.sys_sale_flag,si.code,si.name,si.is_manufactured,ic.name as item_category_name ,si.is_combo_item, "
						+ "si.item_category_id,si.is_active,si.is_synchable,IFNULL(sync_queue.id ,'') AS quetableId  "
						+ "FROM "
						+ getTable()+" "
						+ " si "
						+ "LEFT JOIN sync_queue  ON (si.id=sync_queue.record_id AND sync_queue.table_name='"+ getTable()+ "')  "
						+ "LEFT JOIN mrp_item_category ic on ic.id=si.item_category_id "
						+  wherePart+ " "
						+  sortPart+ " "
						+ "LIMIT " + limitRows + " "
						+ "OFFSET " + offset + " ";
		
		}
		else
		{
			 sql = "SELECT  "
					+ "si.id as id,si.sys_sale_flag,si.code,si.name,si.is_manufactured,ic.name as item_category_name ,si.is_combo_item, "
					+ "si.item_category_id,si.is_active,si.is_synchable  "
					+ "FROM "
					+ getTable()+" "
					+ " si "
					
					+ "LEFT JOIN mrp_item_category ic on ic.id=si.item_category_id "
					+  wherePart+ " "
					+  sortPart+ " "
					+ "LIMIT " + limitRows + " "
					+ "OFFSET " + offset + " ";
		}
		
		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.util.List) */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart , String searchCriteria , String adnlFilterPart ,
			List<DataTableColumns> columnList) throws Exception {
	
		if (wherePart == null || wherePart == "") {
	
			wherePart = "WHERE (IFNULL(si.is_deleted,0) = 0)";
		}
		else {
			
			if (searchCriteria != "" && searchCriteria != null)
				
				wherePart = "WHERE (IFNULL(si.is_deleted,0) = 0) "
								+ "AND (si.code like '%" + searchCriteria+ "%' "
								+ "OR si.name LIKE '%" + searchCriteria + "%' "
								+ "OR si.is_manufactured LIKE '%"+ searchCriteria + "%' "
								+ "OR ic.name LIKE '%" + searchCriteria+ "%' "
								+ "OR si.item_category_id LIKE '%" + searchCriteria + "%')";
		}
		
		if (adnlFilterPart != "" && adnlFilterPart != null) {
			
			if (wherePart != null && wherePart != "") {
				
				wherePart += " AND " + adnlFilterPart;
			}
			else {
				wherePart = " WHERE " + adnlFilterPart;
			}
		}
		
		String sqlCount = "SELECT "
						+ "count(si.id) as row_count "
						+ "FROM " 
						+  getTable()+ " si "
						+ "LEFT JOIN mrp_item_category ic on ic.id=si.item_category_id  " 
						+  wherePart + "";
		
		CachedRowSet totalRecs = executeSQLForRowset(sqlCount);
		return totalRecs;
	}
	
	
	/**
	 * @param id
	 * @param inactiveId
	 * @return
	 * @throws Exception
	 */
	public boolean updateIsActive(JsonArray id , JsonArray inactiveId) throws Exception {
	
		int success = 0, pass = 0;
		
		if (id.size() != 0) {
			
			final String sql_1 = "UPDATE "
									+ getTable() + " st set st.is_active=1 "
									+ "WHERE "
									+ "st.id in(" + getQueryString(id)+ ") AND (IFNULL(st.is_deleted,0) = 0)";
			
			success = executeSQL(sql_1);
		}
		if (inactiveId.size() != 0) {
			
			final String sql_2 = "UPDATE " 
									+  getTable()+" " 	
									+ "st set st.is_active=0 "
									+ "WHERE "
									+ "st.id  in("+ getQueryString(inactiveId) + ") AND (st.is_deleted=0 OR st.is_deleted=NULL);";
			pass = executeSQL(sql_2);
		}
		
		return (success > 0 || pass > 0) ? true : false;
	}
	
	/**
	 * @param synchId
	 * @param nonSyncId
	 * @return
	 * @throws Exception
	 */
	public boolean updateIsSynch(JsonArray synchId , JsonArray nonSyncId) throws Exception {
	
		int success = 0, pass = 0;
		
		if (synchId.size() != 0) {
			
			final String sql_1 = "UPDATE "
								 + getTable() + " "
								 + "st set st.sys_sale_flag=1 "
								 + "WHERE "
								 + "st.id IN("+ getQueryString(synchId) + ") AND (IFNULL(st.is_deleted,0) = 0);";

			executeSQL(sql_1);
		}
		if (nonSyncId.size() != 0) {
			
			final String sql_2 = "UPDATE "
							 		+ getTable() + " "
							 		+ "st set st.sys_sale_flag=0 "
							 		+ "WHERE "
							 		+ "st.id IN("+ getQueryString(nonSyncId) + ") AND (IFNULL(st.is_deleted,0) = 0);";

			executeSQL(sql_2);
		}
		
		return true;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public String getQueryString(JsonArray id) {
	
		Integer[] idList = new Integer[id.size()];
		
		StringBuffer s = new StringBuffer();
		for (int i = 0; i <= id.size() - 1; i++) {
			idList[i] = id.get(i).getAsInt();
			s = s.append(idList[i].toString() + ',');
		}
		int p = s.lastIndexOf(",");
		s.deleteCharAt(p);
		return s.toString();
	}
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.dao.IItemMasterDao#getChoiceData()
	 */
	public JsonArray getChoiceData() throws Exception {
	
		final String sql = "SELECT id,code,name FROM choices WHERE (is_deleted=0 OR is_deleted IS NULL)";
		
		return getTableRowsAsJson(sql);
	}
	
	public JsonArray getGroupItem() throws Exception {
	
		final String sql = "SELECT "
							+ "id,code,name "
							+ "FROM "
							+  getTable()+" "
							+ "WHERE "
							+ "is_group_item='1' AND(is_system=0 OR is_system IS NULL) AND (is_deleted=0 OR is_deleted IS NULL)";
		
		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.dao.IItemMasterDao#getModalDetails(int)
	 */
	public JsonArray getModalDetails(int itemId) throws Exception {
	
		final String sql = "SELECT "
							+ "id,code,name,group_item_id,fg_color,bg_color,sys_sale_flag "
							+ "FROM "
							+  getTable()+" "
							+ "WHERE id="
							+  itemId+" "
							+ "AND is_group_item='1' AND(is_system=0 OR is_system IS NULL) AND (is_deleted=0 OR is_deleted IS NULL)";
					
		return getTableRowsAsJson(sql);
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer getIsManufactured(Integer id) throws Exception {
	
		final String sql = "SELECT is_manufactured FROM " + getTable() + " WHERE id=" + id + "";
		JsonArray is_manfcturd = getTableRowsAsJson(sql);
		JsonObject obj = (JsonObject) is_manfcturd.get(0);
		return obj.get("is_manufactured").getAsInt();
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.dao.IItemMasterDao#getItemData()
	 */
	public List<ItemMaster> getItemData() throws Exception {
	
		List<ItemMaster> itmmasterList = null;
		
		final String SQL = "SELECT itm.*,um.code as uomCode,itmcl.`name` AS itemClass,kit.`code` AS kitchenName,tx.`code` as taxcode "
							+ "FROM "
							+  getTable() +" "
							+ "itm "
							+ "LEFT JOIN uoms um ON itm.uom_id=um.id "
							+ "LEFT JOIN item_classes itmcl ON itmcl.id=itm.sub_class_id  "
							+ "LEFT JOIN kitchens kit ON kit.id=itm.kitchen_id "
							+ "LEFT JOIN taxes tx on tx.id=itm.output_tax_id "
							+ "WHERE "
							+ "itm.is_deleted=0 "
							+ "ORDER BY itm.id ";
		
					CachedRowSet rs = getRowSet(SQL);
		
		ItemMaster itemmaster;
		
		if (rs != null) {
			itmmasterList = new ArrayList<ItemMaster>();
			while (rs.next()) {
				String choiceName = "";
				String tax = "";
				Integer taxIDss = 0;
				itemmaster = getNewModelInstance();
				DBUtil.setModelFromSRS(rs, itemmaster);
				itemmaster.setUomCode(rs.getString("uomCode"));
				itemmaster.setItemClass(rs.getString("itemClass"));
				itemmaster.setKitchenName(rs.getString("kitchenName"));
				itemmaster.setOutPutTaxName(rs.getString("taxcode"));
				/* taxIDss=rs.getInt("output_tax_id"); if(taxIDss==1) {
				 * tax="NOTAX"; } */
				
				if (rs.getString("choice_ids") != null && rs.getString("choice_ids") != "") {
					final String sql1 = "select code from choices where id in (" + itemmaster.getChoiceIds() + ")";
					CachedRowSet rs2 = getRowSet(sql1);
					if (rs2 != null) {
						
						while (rs2.next()) {
							
							choiceName += ((choiceName.isEmpty()) ? "" : ",") + rs2.getString("code");
							
						}
						
					}
					
				}
				
				itemmaster.setChoicesName(choiceName);
				itmmasterList.add(itemmaster);
			}
		}
		
		return itmmasterList;
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getAdditionalFilter(java
	 * .util.List) */
	@Override
	public String getAdditionalFilter(List<DataTableColumns> columnList) {
	
		String adnlFilterPart = "";
		for (DataTableColumns col : columnList) {
			
			if (col.getSearchValue() != "" && col.getSearchValue() != null) {
				
				if (col.getData().equals("item_category_name")) {
					adnlFilterPart += ((adnlFilterPart.isEmpty()) ? " " : " AND ") + "ic.name LIKE '%"
							+ col.getSearchValue() + "%'";
					
				}
				else {
					adnlFilterPart += ((adnlFilterPart.isEmpty()) ? " " : " AND ") + "si." + col.getData() + " LIKE '%"
							+ col.getSearchValue() + "%'";
				}
				
			}
		}
		return adnlFilterPart;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.dao.IItemMasterDao#getdpData(java.lang.String)
	 */
	public JsonArray getdpData(String table) throws Exception {
	
		final String sql = "SELECT id,code,name FROM " + table + " WHERE (is_deleted=0 OR is_deleted IS NULL)";
		
		return getTableRowsAsJson(sql);
		
	}
	
	public JsonArray getUOmData(String table) throws Exception {
		
		final String sql = "SELECT id,code,name,uom_symbol FROM " + table + " WHERE (is_deleted=0 OR is_deleted IS NULL)";
		
		return getTableRowsAsJson(sql);
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.dao.IItemMasterDao#getSaleItems()
	 */
	public JsonArray getSaleItems() throws Exception {
	
		final String sql = "SELECT "
							+ "id,code,name "
							+ "FROM  "
							+  getTable()+" "
							+ "WHERE "
							+ "(is_deleted=0 OR is_deleted IS NULL) and is_sellable=1";
		
		return getTableRowsAsJson(sql);
		
	}
	
	
	public JsonArray getTransferItems() throws Exception {

		SystemSettings systemSettings = (SystemSettings) context.getCurrentHttpSession().getAttribute("systemSettings");
		
		final String transferItemQuery = "SELECT stock_item.id, stock_item. CODE, stock_item. NAME, "
				+ "stock_item.input_tax_id, IFNULL(tax.tax1_percentage, 0) AS tax_percentage, "
				+ "stock_item.valuation_method, stock_item.is_active, stock_item.prd_department_id AS department_id, "
				+ "department. NAME AS department_name, stock_item.is_manufactured, uoms.`code` AS uomcode, "
				+ "uoms.`name` AS uomname, round(ifnull(item_rate.cost_price, 0),"+ systemSettings.getDecimal_places()
				+ ") AS unit_price "
				+ "FROM "
				+ "(Select * FROM mrp_stock_item WHERE is_system = 0 AND is_deleted = 0 ) stock_item "
				+ "LEFT JOIN mrp_department department ON stock_item.`prd_department_id` = department.id "
				+ "LEFT JOIN taxes tax ON stock_item.`input_tax_id` = tax.id "
				+ "LEFT JOIN uoms ON stock_item.uom_id = uoms.`id` "
				+ "LEFT JOIN vw_itemrate item_rate ON item_rate.stock_item_id = stock_item.id "
				/*+ "WHERE "
				+ "stock_item.is_system = 0 AND stock_item.is_deleted = 0 AND stock_item.is_active = 1 "*/
				//+ "AND stock_item.is_manufactured = 1 "
				+ "ORDER BY stock_item. NAME";

		return getTableRowsAsJson(transferItemQuery);
	}
}