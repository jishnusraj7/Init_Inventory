package com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.ReflectionUtil;
import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.Hq;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.ImplementationMode;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.counter.dao.CounterDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;

/**
 * @author jo
 *
 */
public class ItemMasterBomDao extends GeneralDao<ItemMasterBom> implements IItemMasterBomDao {
	private ItemMasterBomDao itemMasterBomDao;
	private CounterDao counterDao;

	
	/**
	 * @param context
	 */
	public ItemMasterBomDao(ApplicationContext context) {
	
		super(context);
		counterDao=new CounterDao(getContext());
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public ItemMasterBom getNewModelInstance() {
	
		return new ItemMasterBom();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getList()
	 */
	@Override
	public List<ItemMasterBom> getList() throws Exception {
	
		String SQL;
		List<ItemMasterBom> testbom;
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
	
		return "stock_item_bom";
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao.IItemMasterBomDao#getBomJsonArray(int)
	 */
	public JsonArray getBomJsonArray(int id, int isBase) throws Exception {

		/*final String sql = "SELECT uom.code as uomcode,mrp_stock_item_bom.id,Round(mrp_stock_item_bom.cost_price,2) as unit_price,"
			+ "mrp_stock_item_bom.bom_item_id,mrp_stock_item_bom.qty,mrp_stock_item.code as bom_item_code,mrp_stock_item.name as bom_item_name "
				+ "FROM "
			+  getTable()+" "
			+ "INNER JOIN mrp_stock_item on mrp_stock_item.id=mrp_stock_item_bom.bom_item_id  "
				+ "LEFT JOIN uoms uom on uom.id=mrp_stock_item.uom_id "
			+ "WHERE "
				+ "stock_item_id="+ id +" "
				+ " AND IFNULL(mrp_stock_item_bom.is_deleted,0) = 0";

		return getTableRowsAsJson(sql);*/

		itemMasterBomDao=new ItemMasterBomDao(getContext());

		String costPrice = "";
		String costPriceQuery = "";
		ApplicationContext context = itemMasterBomDao.getContext();
		if (context.getCompanyInfo().getId() != 0) {

			costPrice = ", ifnull(vw_itemrate.cost_price,0) as last_unit_price";
			costPriceQuery = "LEFT JOIN  vw_itemrate on vw_itemrate.stock_item_id = stock_item_bom.bom_item_id";
		}
		else {
			costPrice = ", IFNULL(mrp_stock_item.cost_price,0) as last_unit_price";
			//Round(mrp_stock_item_bom.cost_price,2) as unit_price
		}

		String sql = "";

		if(isBase == 0) {
			sql = "SELECT uom.code as uomcode, uom.decimal_places, stock_item_bom.id,stock_item_bom.base_item_id,"
					+ "stock_item_bom.bom_item_id,stock_item_bom.bom_item_qty as qty,mrp_stock_item.code as bom_item_code,"
					+ "mrp_stock_item.name as bom_item_name,"
					+ "ifnull(mrp_stock_item.cost_price,0) AS unit_price "+ costPrice+ " "
					+ "FROM "
					+  getTable()+" "
					+ "INNER JOIN mrp_stock_item on mrp_stock_item.id=stock_item_bom.bom_item_id  "
					+ "LEFT JOIN uoms uom on uom.id=mrp_stock_item.uom_id " + costPriceQuery+" "
					+ "WHERE "
					+ "stock_item_bom.stock_item_id="+ id +" "
					+ " AND IFNULL(stock_item_bom.is_deleted,0) = 0";
		}else {

			sql = "SELECT uom.code as uomcode, uom.decimal_places, stock_item_bom.base_item_id,"
					+ "stock_item_bom.bom_item_id,stock_item_bom.bom_item_qty as qty,mrp_stock_item.code as bom_item_code,"
					+ "mrp_stock_item.name as bom_item_name,"
					+ "ifnull(mrp_stock_item.cost_price,0) AS unit_price "+ costPrice+ " "
					+ "FROM "
					+  getTable()+" "
					+ "INNER JOIN mrp_stock_item on mrp_stock_item.id=stock_item_bom.bom_item_id  "
					+ "LEFT JOIN uoms uom on uom.id=mrp_stock_item.uom_id " + costPriceQuery+" "
					+ "WHERE "
					+ "stock_item_bom.stock_item_id="+ id +" "
					+ " AND IFNULL(stock_item_bom.is_deleted,0) = 0";

		}

		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(java.util.List)
	 */
	public void save(List<ItemMasterBom> itemList) throws Exception {
	
		String itemBomItemList = "";
		String wherePart = "";
		String syncWhere="";
		
		final Integer itemBomstockId = itemList.get(0).getStockItemId();
		
		for (ItemMasterBom bomItem : itemList) {
			
			if (bomItem.getStockItemId() != null) {
				
				if (bomItem.getId() != null) {
					itemBomItemList += ((itemBomItemList.isEmpty()) ? "" : ",") + bomItem.getId();
				}
				/*if (bomItem.getStockBomItemId() != null) {
					itemBomItemList += ((itemBomItemList.isEmpty()) ? "" : ",") + bomItem.getStockBomItemId();
					bomItem.setId(bomItem.getStockBomItemId());
				}else {
					bomItem.setId(null);
				}*/
			}
		}
		if (itemBomItemList.length() > 0) {
			
			wherePart = " AND id NOT IN (" + itemBomItemList + ")";
			syncWhere="AND calc.id NOT IN("+ itemBomItemList + ")";
		}
		
		final String markAsDeletedSQl = "UPDATE " + getTable() + " SET is_deleted=1 WHERE stock_item_id=" + itemBomstockId + ""
				+ wherePart + ";";
		System.out.println(getTable());
		/*final String markAsDeletedSQl = "";
		  if(itemList.get(0).getStockBomItemId() != null) {
			 markAsDeletedSQl = "UPDATE " + getTable() + " SET is_deleted=1 WHERE stock_item_id=" + itemBomstockId + ""
					+ wherePart + ";";
		}else {
			 markAsDeletedSQl = "UPDATE " + getTable() + " SET is_deleted=1 WHERE stock_item_id=" + itemList.get(0).getBaseItemId() + ""
					+ wherePart + ";";
		}*/
		 String updateSyncQueue="";
		if(context.getCurrentHttpSession().getAttribute("COMPANY_ID").equals(Hq.HQ.getHqId()))
		{
		
		 updateSyncQueue="UPDATE sync_queue SET crud_action='D' "
                + "WHERE record_id IN "
                + "(SELECT calc.id FROM "+getTable()+" calc "
                + "WHERE calc.stock_item_id="+ itemBomstockId + " "
                + ""+syncWhere+")AND table_name='"+getTable()+"'";
		}
		beginTrans();
		
		try {
			if(context.getCurrentHttpSession().getAttribute("COMPANY_ID").equals(Hq.HQ.getHqId()))
			{
			executeSQL(updateSyncQueue);
			}
			executeSQL(markAsDeletedSQl);
			for (ItemMasterBom itemBomsItem : itemList) {
				
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
	public List<ItemMasterBom> getHqTableRowListToImport() throws Exception {
	
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
	public List<ItemMasterBom> getUpdatedBomListToImport(int stockItemId) throws Exception {
	
		final String sql = "SELECT * FROM hq_stock_item_bom WHERE stock_item_id=" + stockItemId;
		return buildItemList(sql);
	}
	
	/**
	 * @param stockItemId
	 * @return
	 * @throws Exception
	 */
	public List<ItemMasterBom> getBomListForItem(int stockItemId) throws Exception {
	
		final String sql = "SELECT * FROM stock_item_bom where stock_item_id= " + stockItemId +" AND IFNULL(stock_item_bom.is_deleted,0) = 0";
		return buildItemList(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao.IItemMasterBomDao#deleteData(java.lang.Integer)
	 */
	public void deleteData(Integer idss) throws Exception {
	
		final String sql = "UPDATE " + getTable() + " SET is_deleted=1 where stock_item_id='" + idss + "'";
		executeSQL(sql);
	}	
	
	@Override
	public String getAdditionalFilter(List<DataTableColumns> columnList) {
		
		String adnlFilterPart = "";
		for(DataTableColumns col:columnList) {

			if(col.getSearchValue() != "" && col.getSearchValue() != null) {
				
				if(col.getData().equals("stock_item_code") || col.getData().equals("stock_item_name")) {
					if(context.getCurrentHttpSession().getAttribute("version").equals(ImplementationMode.FULL.getImplementationModeId()))
						adnlFilterPart += ((adnlFilterPart.isEmpty()) ? " " : " AND ")	+ ""+col.getData()+" LIKE '%"+col.getSearchValue()+"%'";					
					else
						adnlFilterPart += ((adnlFilterPart.isEmpty()) ? " " : " AND ")	+ "stk.`name` LIKE '%"+col.getSearchValue()+"%'";	
				}

				if(col.getData().equals("item_category_name") || col.getData().equals("item_category_name")){
					String colname = "item_category_id";
					adnlFilterPart += ((adnlFilterPart.isEmpty()) ? " " : " AND ") +""+ colname +" = '"+col.getSearchValue()+"'";
				}
				else
					adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " AND ")+ "" +col.getData() +" = ('"+col.getSearchValue()+"')";
			}
		}	
		return adnlFilterPart;
	}
	
	
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns, String wherePart, String searchCriteria, String sortPart,
			int limitRows, int offset,String adnlFilterPart, List<DataTableColumns> columnList) throws Exception {

		String sql="";
		if (wherePart == null || wherePart == "")
			wherePart = "WHERE stk.id IN (SELECT stock_item_id FROM stock_item_bom) ";
		else {
			if (searchCriteria != "" && searchCriteria != null)
				
				wherePart = "WHERE stk.id IN (SELECT stock_item_id FROM stock_item_bom)"
						+ " AND (stk.`code` LIKE '%" + searchCriteria+ "%'"
						+ " OR stk.`name` LIKE '%" + searchCriteria + "%'"
						+ " OR itmcat.`name` LIKE '%" + searchCriteria + "%'"
						+ " OR dpt.`name` LIKE '%" + searchCriteria + "%'" +")";
		}

		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart != null && wherePart != "")
				wherePart += "AND " + adnlFilterPart;
			else
				wherePart = " WHERE " + adnlFilterPart;
		}
		
		if(	context.getCurrentHttpSession().getAttribute("version").equals(ImplementationMode.FULL.getImplementationModeId()))
		{
			sql="SELECT * from (SELECT    stk.id AS stock_item_id, "
					+ "stk.`code` AS stock_item_code,stk.is_semi_finished,stk.is_finished,stk.sales_margin,stk.is_sales_margin_percent,stk.tax_calculation_method, "
					+ "stk.`name` AS stock_item_name,um.code as uomcode,"
					+ "itmcat.`name` as item_category_name,stkbm.is_deleted,stkbm.stock_item_qty AS stock_item_qty,itmcat.id as item_category_id , "
					+ " stk.output_tax_id AS tax_id  "
					+ "FROM "+ getTable() +" stkbm "
					+ "INNER JOIN"
					+ " mrp_stock_item stk ON stkbm.stock_item_id = stk.id "
					+ "LEFT JOIN "
					+ "mrp_item_category itmcat ON stk.item_category_id = itmcat.id "
					+ "LEFT JOIN "
					+ "uoms um ON stk.uom_id = um.id  WHERE stkbm.is_deleted=0 "
					+ "GROUP BY stock_item_code)tbl "
					+  wherePart + " " 
					+  sortPart + " " 
					+ "LIMIT " + limitRows + " " 
					+ "OFFSET " + offset + "";
		}else{
			sql="SELECT "
					+ "dpt.`name` AS dept_name, stk.id AS stock_item_id, stk.`code` AS stock_item_code, stk.is_semi_finished,"
					+ "stk.is_finished, stk.sales_margin, stk.is_sales_margin_percent, stk.tax_calculation_method, stk.`name` AS stock_item_name,"
					+ "stk.qty_manufactured AS stock_item_qty, um. CODE AS uomcode, itmcat.`name` AS item_category_name, "
					+ "itmcat.id AS item_category_id, stk.tax_id AS tax_id "
					+ "FROM "
					+ "mrp_stock_item stk "
					+ "INNER JOIN uoms um ON stk.uom_id = um.id "
					+ "LEFT JOIN mrp_item_category itmcat ON stk.item_category_id = itmcat.id "
					+ "LEFT JOIN mrp_department dpt ON dpt.id = stk.prd_department_id "
					+  wherePart + " " 		   
					+  sortPart + " " 
					+ "LIMIT " + limitRows + " " 
					+ "OFFSET " + offset + "";
		}
		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart, String searchCriteria, String adnlFilterPart, 
			List<DataTableColumns> columnList) throws Exception {

		if (wherePart == null || wherePart == "")
			wherePart = "WHERE stk.id IN (SELECT stock_item_id FROM stock_item_bom)";
		else {
			if (searchCriteria != "" && searchCriteria != null)

				wherePart = "WHERE stk.id IN (SELECT stock_item_id FROM stock_item_bom) "
						+ "AND (stk.`code` LIKE '%" + searchCriteria+ "%' "
						+ "OR stk.`name` LIKE '%" + searchCriteria + "%' "
						+ "OR itmcat.`name` LIKE '%" + searchCriteria + "%'"
						+ " OR dpt.`name` LIKE '%" + searchCriteria + "%'" +")";

		}

		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart != null && wherePart != "")
				wherePart += " AND " + adnlFilterPart;
			else
				wherePart = " WHERE " + adnlFilterPart;
		}

		String sqlCount="SELECT count(stk.id) as row_count "
				+ "FROM "
				+ "mrp_stock_item stk "
				+ "INNER JOIN uoms um ON stk.uom_id = um.id "
				+ "LEFT JOIN mrp_item_category itmcat ON stk.item_category_id = itmcat.id "
				+ "LEFT JOIN mrp_department dpt ON dpt.id = stk.prd_department_id "
				+  wherePart + " " ;
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}

	public JsonArray getItemList() throws Exception{
		String sql="";
	if(	context.getCurrentHttpSession().getAttribute("version").equals(ImplementationMode.FULL.getImplementationModeId()))
	{
		 sql="SELECT "
				+ "stk.id AS id,stk. CODE AS code,stk.`name` name,"
				+ "itmcat.`name` as item_category_name,um.`code` as uom,stk.output_tax_id as tax_id,stk.tax_calculation_method as tax_calculation_method "
				+ "FROM "
				+ "mrp_stock_item stk "
				+ "LEFT JOIN "
				+ "mrp_item_category itmcat ON stk.item_category_id=itmcat.id "
				+ "LEFT JOIN "
				+ "uoms um ON stk.uom_id=um.id "
				+ "WHERE "
				+ "stk.id NOT IN (SELECT stock_item_id "
				+ "FROM stock_item_bom where is_deleted=0 GROUP BY stock_item_id) "
				+ "AND stk.is_manufactured = 1 AND stk.is_deleted=0 ";
		
	}
	else
	{
		
		 /*sql="SELECT "
				+ "stk.id AS id,stk. CODE AS code,stk.`name` name,"
				+ "itmcat.`name` as item_category_name,um.`code` as uom,stk.tax_id as tax_id,"
				+ " stk.tax_calculation_method as tax_calculation_method, dpt.`name` AS dept_name, um.decimal_places AS decimal_places "
				+ "FROM "
				+ "mrp_stock_item stk "
				+ "LEFT JOIN "
				+ "mrp_item_category itmcat ON stk.item_category_id=itmcat.id "
				+ "LEFT JOIN "
				+ "uoms um ON stk.uom_id=um.id "
				+"LEFT JOIN mrp_department dpt ON dpt.id = stk.prd_department_id "
				+ "WHERE "
				+ "stk.id NOT IN (SELECT stock_item_id "
				+ "FROM stock_item_bom where is_deleted=0 GROUP BY stock_item_id) "
				+ "AND stk.is_manufactured = 1 AND stk.is_deleted=0 ";*/
		//Added by udhay for War Implement
		sql="SELECT "
				+ "stk.id AS id,stk. CODE AS code,stk.`name` name,"
				+ "itmcat.`name` as item_category_name,um.`code` as uom,stk.tax_id as tax_id,"
				+ " stk.tax_calculation_method as tax_calculation_method, dpt.`name` AS dept_name, um.decimal_places AS decimal_places, "
				+" dpt.id as dept_id "//to fetch the dept_id
				+ "FROM "
				+ "mrp_stock_item stk "
				+ "LEFT JOIN "
				+ "mrp_item_category itmcat ON stk.item_category_id=itmcat.id "
				+ "LEFT JOIN "
				+ "uoms um ON stk.uom_id=um.id "
				+"LEFT JOIN mrp_department dpt ON dpt.id = stk.prd_department_id "
				+ "WHERE "
				+ "stk.id NOT IN (SELECT stock_item_id "
				+ "FROM stock_item_bom where is_deleted=0 GROUP BY stock_item_id) "
				+ "AND stk.is_manufactured = 1 AND stk.is_deleted=0 ";
						
	}
		
		return getTableRowsAsJson(sql);

		
		
	}
	
	public JsonArray getbasebomList() throws Exception{
		
		String sql="SELECT	bom.stock_item_id AS id, stk.`code`, stk.`name`, bom.stock_item_qty "
				+ "FROM	stock_item_bom bom JOIN stock_items stk ON bom.stock_item_id = stk.id "
				+ "WHERE	bom.is_deleted = 0 GROUP BY bom.stock_item_id ;";
		
		return getTableRowsAsJson(sql);
		
	}

	public void updateMrpStockItem(ItemMaster item , Integer version) throws Exception{

		String table = (version == 0) ? "mrp_stock_item" : "stock_items";
		final String sqlUpdate="UPDATE "+table+" "
				+ "SET "
				+ " sales_margin="+item.getSales_margin()+",is_sales_margin_percent="+item.getIs_sales_margin_percent()
				+ ",tax_calculation_method="+item.getTaxCalculationMethod()+" WHERE id="+item.getId()+"";

		executeSQL(sqlUpdate);
	}
		
	@Override
	protected void setValuesForInsertPS(PreparedStatement ps , ItemMasterBom item) throws Exception {
	     Integer version=(Integer) context.getCurrentHttpSession().getAttribute("version");
	    
		String fieldName=null;
		Object fieledValue=null;

		final HashMap<String, PropertyDescriptor> propertyDescriptors=ReflectionUtil.getPropertyDescriptors(item.getClass());
		int index=1;

		for(Field field : ReflectionUtil.getAllFileds(item.getClass())){

			/**
			 * Column annotation
			 * Gets the column from bean
			 */
			final Column annotationColumn = (Column)field.getDeclaredAnnotation(Column.class);

			if(annotationColumn!=null && propertyDescriptors.get(field.getName())!=null){

				fieldName=null;

				/**
				 * Use the setter method to get field type and id generation type.
				 */
				final Method setterMethod=propertyDescriptors.get(field.getName()).getWriteMethod();
				final Method getterMethod=propertyDescriptors.get(field.getName()).getReadMethod();
				System.out.println("Column Name : "+annotationColumn.name());

				/**
				 * Check the Id generation type is overridden anywhere
				 */
				Id annotationId = (Id) ((setterMethod!=null && setterMethod.getDeclaredAnnotation(Id.class)!=null)?
						setterMethod.getDeclaredAnnotation(Id.class):
							((getterMethod!=null && getterMethod.getDeclaredAnnotation(Id.class)!=null)?getterMethod.getDeclaredAnnotation(Id.class):
								field.getDeclaredAnnotation(Id.class)));

				if(annotationId==null){

					fieldName=annotationColumn.name();
					field.setAccessible(true);
					Object value=field.get(item);
					fieledValue=DBUtil.modelValueToDBFieldValue(field, value,false);

				}else{

					final GenerationType genType=annotationId.generationType();
					switch (genType) {
					case IDENTITY:
						/**
						 *
						 * No need to set the filed. Id will be automatically generated by database
						 * 
						 **/ 
						fieldName=annotationColumn.name();
						field.setAccessible(true);
						
						field.set(item, null);
						fieledValue=DBUtil.modelValueToDBFieldValue(field, null);
						break;

					case COUNTER:
						/**
						 *
						 * If counter, get the id from counter table.
						 * 
						 **/ 
						fieldName=annotationColumn.name();
						field.setAccessible(true);

						Counter annotationCounter = (Counter)setterMethod.getDeclaredAnnotation(Counter.class);

					
						if(annotationCounter!=null && field.get(item) == null ){

							final String module=annotationCounter.module();
							final String key=annotationCounter.key();
							int value=-1;
							if(version==1)
							{
							 value=counterDao.getCounterFor(module, key);
							}
							else
							{
							 value=counterDao.getCounter(module, key);
							}
					
							
							Object customId=getCustomId(value);
							field.set(item, customId);
							fieledValue=DBUtil.modelValueToDBFieldValue(field, customId);

						}else if(annotationCounter!=null && field.get(item)!=null){
							
							Object value=field.get(item);
							fieledValue=DBUtil.modelValueToDBFieldValue(field, value,false);
//							
						}else 
							throw new Exception("Counter (annotaion) details not set for ID.");


						break;
					}
				}

				if(fieldName!=null){
					
//					if(fieledValue!=null)
						ps.setObject(index, fieledValue);
//					else
//						ps.setNull(index, java.sql.Types.INTEGER);
					index++;
				}
			}
		}
	}

	public JsonArray getItemMastersRowJson() throws Exception {

		itemMasterBomDao=new ItemMasterBomDao(getContext());

		String costPrice = "";
		String costPriceQuery = "";
		ApplicationContext context = itemMasterBomDao.getContext();

		if (context.getCompanyInfo().getId() != 0) {

			costPrice = " "+", round(ifnull(vw_itemrate.cost_price,0),2) as unit_price,si.cost_price"+" ";
			costPriceQuery = " "+"LEFT JOIN  vw_itemrate on vw_itemrate.stock_item_id = si.id"+" ";
		}
		else {
			costPrice = "'"+", round(IFNULL(si.item_cost,0),2) as unit_price,si.cost_price"+"'";
			costPriceQuery=null;
		}

		
		final String sql = "SELECT "
				+ "si.id,si.code,si.name,si.input_tax_id,IFNULL(tx.tax1_percentage,0) AS tax_percentage,"
				+ "si.valuation_method,si.is_active,si.is_manufactured ,uoms.`code` as uomcode,"
				+ "uoms.`name` as uomname, uoms.decimal_places AS decimal_places "+ costPrice +" "
				+"FROM (select * FROM mrp_stock_item WHERE is_deleted = 0 AND is_system = 0) si LEFT JOIN taxes tx ON si.`input_tax_id` = tx.id "
				+ " LEFT JOIN uoms ON si.uom_id=uoms.`id` "+ costPriceQuery +" "
				+ "WHERE "
				+ "si.is_manufactured = 0"
				+ " OR (si.is_manufactured = 1 )";
		//AND si.is_sellable = 0 

		return getTableRowsAsJson(sql);


	}

	public void updateCostprice(ArrayList<ItemMasterBom> bomArrayList, Integer version) throws Exception {
	
		// TODO Auto-generated method stub
		
		String table=(version==0)?"mrp_stock_item":"stock_items";
		
		
		
		String sqlUpdate="UPDATE "+table+" "
				+ "SET unit_price = ? "
				+ "WHERE "
				+ " id = ? ";
		
		PreparedStatement st=dbHelper.buildPreparedStatement(sqlUpdate);
		for (int i = 0; i < bomArrayList.size(); i++) {
			st.setDouble(1,bomArrayList.get(i).getCost_price());
			st.setInt(2, bomArrayList.get(i).getBomItemId());
			
			
			st.addBatch();

		}
		
		
		beginTrans();
		try{

			st.executeBatch();
			endTrans();
		}catch (Exception e){

			rollbackTrans();
			throw e;
		}
	}
	
	public void updateStockItemUnitPrice(Integer stockitemid , String unitprice){
		
		
		
		
		
		final String sqlUpdate="UPDATE stock_items "
				+ "SET "
				+ " unit_price="+unitprice+""
				+ " WHERE id="+stockitemid+"";
				
						try {
							executeSQL(sqlUpdate);
						} catch (Exception e) {
						
							e.printStackTrace();
						}
		
	}

	/**
	 * @param stock_item_id
	 * @param stock_item_qty
	 * @param version
	 * @throws Exception
	 * to update qty_manufactured field in stock_items when save/update item BOM
	 */
	public void updateQtyManufactured(Integer stock_item_id, double stock_item_qty, Integer version) throws Exception {
		
		String table_name = (version == 0) ? "mrp_stock_item":"stock_items";
		String updateQtyQuery = "UPDATE "+ table_name +" SET qty_manufactured = '" + stock_item_qty 
				+ "' WHERE  id = '" + stock_item_id +"'";
		executeSQL(updateQtyQuery);
	}
}
