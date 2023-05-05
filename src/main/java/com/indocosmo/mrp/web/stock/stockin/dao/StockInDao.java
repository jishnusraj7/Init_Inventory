package com.indocosmo.mrp.web.stock.stockin.dao;

import java.sql.PreparedStatement;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.purchaseordersType;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockInStatus;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockInType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.stock.stockin.model.StockIn;


public class StockInDao extends GeneralDao<StockIn> implements IStockInDao {

	public StockInDao(ApplicationContext context) {

		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public StockIn getNewModelInstance() {

		return new StockIn();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "mrp_stock_in_hdr";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql = "SELECT * FROM " + getTable();
		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{

		if(wherePart!=null && wherePart!=""){

			wherePart = " WHERE "
					+ "(grn_no LIKE '%"+searchCriteria+"%' "
					+ "OR received_date LIKE '%"+searchCriteria+"%' "
					+ "OR supplier_name LIKE '%"+searchCriteria+"%' "
					+ "OR supplier_doc_no LIKE '%"+searchCriteria+"%' )";
				//	+ "OR stockin_status LIKE '%"+searchCriteria+"%') ";
			//+ "OR stock_in_type_name LIKE '%"+searchCriteria+"%' "
			
		}

		if(adnlFilterPart!="" && adnlFilterPart!=null){

			if(wherePart!=null && wherePart!=""){

				wherePart+=" AND " + adnlFilterPart;

			} 
			else {

				wherePart=" WHERE " + adnlFilterPart;

			}
		}

		String sql =  "SELECT "
				+ "id,department_id,stock_in_type,`status`,source_company_id, source_company_code,source_company_name,grn_no, "
				+ "supplier_doc_no, supplier_doc_date, supplier_id, supplier_code, supplier_name, remarks, received_date, "
				+ "received_by,finalized_by,finalized_date,last_sync_at,payment_mode, "
				+ " CASE(payment_mode) " + 
				"WHEN 0 THEN " + 
				"'CASH' " + 
				"WHEN 1 THEN " + 
				"'CREDIT' " + 
				"ELSE " + 
				"''" + 
				"END AS payment_type, "
				+ " CASE (STATUS)"
				+ "  WHEN 0 THEN '"+ stockInStatus.PENDING.getStockInStatusName() +"' "
				+ "  WHEN 1 THEN '"+ stockInStatus.FINALIZED.getStockInStatusName() +"' "
				+ "  WHEN 2 THEN '"+ stockInStatus.PRINTED.getStockInStatusName() +"' "
				+ " ELSE '' "
				+ " END AS stockin_status,"
				+ " CASE (stock_in_type) "
				+ "  WHEN 0 THEN '"+ stockInType.DC.getName() +"' "
				+ "  WHEN 1 THEN '"+ stockInType.INVOICE.getName() +"' "
				+ "  WHEN 2 THEN '"+ stockInType.STORE_TO_STORE.getName() +"' "
				+ "  WHEN 3 THEN '"+ stockInType.DIRECT_PURCHASE.getName() +"' "
				+ "  ELSE '' "
				+ " END AS stock_in_type_name "
				+ " FROM  "
				+   getTable()  + " "  
				+  wherePart + " " +  sortPart + " " 
				+ "LIMIT " + limitRows + " " + "OFFSET " + offset + "";
 		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{

		if(wherePart!=null && wherePart!=""){

			wherePart = " WHERE "
					+ "(grn_no LIKE '%"+searchCriteria+"%' "
					+ "OR received_date LIKE '%"+searchCriteria+"%' "
					+ "OR supplier_name LIKE '%"+searchCriteria+"%' "
					+ "OR supplier_doc_no LIKE '%"+searchCriteria+"%')";
				//	+ "OR stockin_status LIKE '%"+searchCriteria+"%') ";
			//+ "OR stock_in_type_name LIKE '%"+searchCriteria+"%' "
			//+ "OR stockin_status LIKE '%"+searchCriteria+"%') ";
		}

		if(adnlFilterPart!="" && adnlFilterPart!=null){

			if(wherePart!=null && wherePart!=""){

				wherePart+=" AND "+ adnlFilterPart;
			} 
			else {

				wherePart=" WHERE "+ adnlFilterPart;

			}
		}

		/*String sql =  "SELECT "
				+ "id,department_id,stock_in_type,`status`,source_company_id, source_company_code,source_company_name,grn_no, "
				+ "supplier_doc_no, supplier_doc_date, supplier_id, supplier_code, supplier_name, remarks, received_date, "
				+ "received_by,finalized_by,finalized_date,last_sync_at,payment_mode, "
				+ " CASE(payment_mode) " + 
				"WHEN 0 THEN " + 
				"'CASH' " + 
				"WHEN 1 THEN " + 
				"'CREDIT' " + 
				"ELSE " + 
				"''" + 
				"END AS payment_type, "
				+ " CASE (STATUS)"
				+ "  WHEN 0 THEN '"+ stockInStatus.PENDING.getStockInStatusName() +"' "
				+ "  WHEN 1 THEN '"+ stockInStatus.FINALIZED.getStockInStatusName() +"' "
				+ "  WHEN 2 THEN '"+ stockInStatus.PRINTED.getStockInStatusName() +"' "
				+ " ELSE '' "
				+ " END AS stockin_status,"
				+ " CASE (stock_in_type) "
				+ "  WHEN 0 THEN '"+ stockInType.DC.getName() +"' "
				+ "  WHEN 1 THEN '"+ stockInType.INVOICE.getName() +"' "
				+ "  WHEN 2 THEN '"+ stockInType.STORE_TO_STORE.getName() +"' "
				+ "  WHEN 3 THEN '"+ stockInType.DIRECT_PURCHASE.getName() +"' "
				+ "  ELSE '' "
				+ " END AS stock_in_type_name "
				+ " FROM  "
				+   getTable()  + " "  
				+  wherePart + " ";
				
		String sqlCount="SELECT COUNT(id) AS row_count FROM (" + sql + ") TBL";
*/
		
		String sqlCount="SELECT COUNT(id) AS row_count FROM " + getTable() + " "+ wherePart +"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#delete(java.lang.String)
	 */
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

	/**
	 * @param stockin
	 * @throws Exception
	 */
	public void updateAftrFinalize(StockIn stockin) throws Exception {

		/*System.out.println(stockInStatus.PRINTED.getStockInStatusId());*/

		String  updateSqlAftrFinalize = "UPDATE  " 
										 + getTable() + " " 
										 + "SET status="+stockInStatus.PRINTED.getStockInStatusId()+"  " 
										 + "WHERE "
										 + "id="+ stockin.getId();

		PreparedStatement ps = dbHelper.buildPreparedStatement(updateSqlAftrFinalize);

		if (ps != null) {

			beginTrans();
			try {

				ps.execute();
				endTrans();
			} catch (Exception e) {

				rollbackTrans();
				throw e;

			}
		}
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.stockin.dao.IStockInDao#getStockInDtlData(com.indocosmo.mrp.web.stock.stockin.model.StockIn)
	 */
	@Override
	public JsonArray getStockInDtlData(StockIn stockIn) throws Exception {

		final String sql = "SELECT stk.*, stk.uom_code as uomcode, stkitm.pack_contains,po.po_number as po_no,sib.id AS stock_item_batch_id, "
				+ "podt.id AS poDtl_id , IFNULL(vw.current_stock,0) AS current_stock,pay.payment_mode AS payment_mode "
				+ "FROM mrp_stock_in_dtl stk "
				+ "LEFT JOIN mrp_po_hdr po ON po.id=stk.po_id "
				+ "LEFT JOIN vw_itemstock vw ON  vw.stock_item_id=stk.stock_item_id "  
				+ "LEFT JOIN mrp_po_dtl podt on po.id = podt.po_hdr_id "
				+ "LEFT JOIN mrp_stock_item stkitm on stk.stock_item_id = stkitm.id "
				+ "LEFT JOIN mrp_stock_item_batch sib ON stk.stock_item_id = sib.stock_item_id "
				+ "LEFT JOIN mrp_stock_in_hdr pay ON  stk.stock_in_hdr_id=pay.id "
				+ "WHERE stk.stock_in_hdr_id= "+ stockIn.getId() + " "
				+ "GROUP BY stk.stock_item_id "
				+ "ORDER BY stk.id";

		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.stockin.dao.IStockInDao#getStockregData(java.lang.Integer)
	 */
	@Override
	public JsonArray getStockregData(Integer id) throws Exception {

		final String sql = "SELECT id FROM mrp_stock_register_hdr WHERE ext_ref_id="+id+"";

		return getTableRowsAsJson(sql);
	}

	@Override
	public JsonArray getStockregdtlData(Integer id) throws Exception {

		final String sql = "SELECT id FROM mrp_stock_register_dtl WHERE ext_ref_dtl_id="+id+"";

		return getTableRowsAsJson(sql);
	}

	/**
	 * @param stockIn
	 * @return
	 * @throws Exception
	 */
	public StockIn getInterStoreSupplier(StockIn stockIn) throws Exception {
		if(stockIn != null){
			final String sql="SELECT id,code,name FROM supplier WHERE id=2";
			try {

					final CachedRowSet srs=executeSQLForRowset(sql);
					if(srs!=null){

						if(srs.next()){
							
							stockIn.setSupplierId(srs.getInt("id"));
							stockIn.setSupplierCode(srs.getString("code"));
							stockIn.setSupplierName(srs.getString("name"));
						}
					}

			}
			catch (Exception e){

				throw e;
			}
		}
		return stockIn;
	}

	/**
	 * @param stockinHdrId
	 * @return
	 * @throws Exception
	 */
	public StockIn getStockInData(Integer stockinHdrId) throws Exception {

		final String SQL="SELECT "
				+ "stock_in_hdr.id,stock_in_hdr.status,stock_in_hdr.grn_no,stock_in_hdr.payment_mode,stock_in_hdr.supplier_doc_no, "
				+ "stock_in_hdr.supplier_name,stock_in_hdr.supplier_id,stock_in_hdr.supplier_code, "
				+ "stock_in_hdr.received_date,stock_in_hdr.finalized_date,supplier.address,supplier.contact_no " 
				+ "FROM mrp_stock_in_hdr stock_in_hdr "
				+ "INNER JOIN mrp_supplier as supplier ON stock_in_hdr.supplier_id = supplier.id  "
				+ "WHERE stock_in_hdr.id= "+ stockinHdrId; 

		CachedRowSet rs= getRowSet(SQL);
		StockIn stockIn=getNewModelInstance();
		if(rs!=null){

			while(rs.next()){

				stockIn.setId(rs.getInt("id"));
				stockIn.setSupplierId(rs.getInt("supplier_id"));
				stockIn.setSupplierCode(rs.getString("supplier_code"));
				stockIn.setSupplierName(rs.getString("supplier_name"));
				stockIn.setSupplierRefNo(rs.getString("supplier_doc_no"));
				stockIn.setSupplierAdress1(rs.getString("address"));
				stockIn.setSupplierAdress2(rs.getString("contact_no"));
				stockIn.setStatus(rs.getInt("status"));
				stockIn.setReceivedDate(rs.getString("received_date"));
				stockIn.setFinalizedDate(rs.getString("finalized_date"));
				stockIn.setGrnNo(rs.getString("grn_no"));
				stockIn.setStockInPaymentType(rs.getString("payment_mode"));
			}
		}	

		return stockIn;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getItemLastCostPrice(java.lang.Integer)
	 */
	@Override
	public Double getItemLastCostPrice(Integer itemId) throws Exception {
		Double lastCcostPrice = 0.00;
		if(itemId != null){
			final String sql="SELECT cost_price FROM vw_itemrate WHERE stock_item_id = "+itemId+"";
			try {
					final CachedRowSet srs=executeSQLForRowset(sql);
					if(srs!=null){

						if(srs.next())
							lastCcostPrice= srs.getDouble("cost_price");					
					}
					else
					{
						lastCcostPrice=0.0;
					}
			} 
			catch (Exception e) {
					throw e;
			}
		}

		return lastCcostPrice;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getAdditionalFilter(java.util.List)
	 */
	@Override
	public String getAdditionalFilter(List<DataTableColumns> columnList) {
		String adnlFilterPart="";
		for(DataTableColumns col:columnList) {

			if(col.getSearchValue()!="" && col.getSearchValue()!=null) {
				
				if(col.getData().equals("status")) {
					adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " AND ")	+ ""+getTable()+"."+col.getData()+" IN("+col.getSearchValue()+")";	
				}
				else if(col.getData().equals("supplier_name") || col.getData().equals("grn_no")) {
					adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " AND ")	+ ""+getTable()+"."+col.getData()+" LIKE '%"+col.getSearchValue()+"%'";	
				}
				else {
					adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " AND ")	+ ""+getTable()+"."+col.getData()+" = ('"+col.getSearchValue()+"')";
				}
			}
		}
		return adnlFilterPart;
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public void updatePOStatus(String id) throws Exception {
		// TODO Auto-generated method stub
		
		String updateSql;
		final String sql = "select distinct po_id from mrp_stock_in_dtl where stock_in_hdr_id="+id+"";

		CachedRowSet rs= getRowSet(sql);
	
		if(rs!=null){

			while(rs.next()){
				updateSql="update mrp_po_hdr set po_status="+purchaseordersType.NEW.getPurchaseorderTypeId()+""
						+ " where id="+rs.getString("po_id")+"";
				executeSQL(updateSql);
				
				
				
			}
		}	
		
	}
	
	
	@Override
	public Integer isCodeExist(String code) throws Exception {
		final String sql= "SELECT COUNT(grn_no) AS  row_count FROM "+getTable()+" WHERE grn_no='"+code+"' LIMIT 1";

		return excuteSQLForRowCount(sql);
	}

	public String getSupplierAddress(Integer supllierId) throws Exception {
	
		// TODO Auto-generated method stub
		CachedRowSet resultSet;
		String address="";
		final String sql= "SELECT address from mrp_supplier  WHERE id='"+supllierId+"' ";
		resultSet = dbHelper.executeSQLForRowset(sql);
		if (resultSet.next()) {
			address=resultSet.getString("address");
		}
		return address;
	}

	public JsonArray getStockItemData() throws Exception {
	
		String costPrice = "";
		String costPriceQuery = "";
		
		SystemSettings systemSettings=(SystemSettings) context.getCurrentHttpSession().getAttribute("systemSettings");
		
		if (context.getCompanyInfo().getId() != 0) {

			costPrice = ""+", round(ifnull(vw_itemrate.cost_price,0),"+systemSettings.getDecimal_places()+") as unit_price"+"";
			costPriceQuery = ""+"LEFT JOIN  vw_itemrate on vw_itemrate.stock_item_id = si.id"+"";
		}
		else {
			costPrice = ""+", round(IFNULL(si.item_cost,0),"+systemSettings.getDecimal_places()+") as unit_price"+"";
			costPriceQuery="";
		}
		/*
		final String sql="SELECT "
				+ "si.id,si.CODE,si.NAME,si.input_tax_id,IFNULL(tx.tax1_percentage, 0) AS tax_percentage,"
				+ "si.valuation_method,si.is_active,IFNULL(vw.current_stock,0) AS current_stock,,si.is_manufactured,si.pack_contains,uoms.`code` AS uomcode,uoms.`name` AS uomname "+costPrice+" "
				+ "FROM mrp_stock_item si "
				+ "LEFT JOIN taxes tx ON si.`input_tax_id` = tx.id "
				+ "LEFT JOIN vw_itemstock vw ON vw.stock_item_id = si.id AND vw.department_id = 2"
				+ "LEFT JOIN uoms ON si.uom_id = uoms.`id` "+costPriceQuery+" "
				+ "WHERE si.is_system = 0 AND si.is_deleted = 0 AND si.is_active = 1 AND si.is_manufactured = 0 AND vw.department_id = 2 " + " "
				+ " OR (si.is_manufactured = 1 AND si.is_sellable = 0) ORDER BY si.NAME";*/
		
		final String sql="SELECT" + 
				"	si.id," + 
				"	si. CODE," + 
				"	si. NAME," + 
				"	si.input_tax_id,"+
				"	IFNULL(tx.tax1_percentage, 0) AS tax_percentage," + 
				"	si.valuation_method," + 
				"	si.is_active," + 
				"	IFNULL(vw.current_stock,0) AS current_stock," + "" + 
				"	si.is_manufactured," + 
			/*	"	si.pack_contains," + */
				"	uoms.`code` AS uomcode," + 
				"	uoms.`name` AS uomname "+costPrice+"," + 
				"	round(" + 
				"		ifnull(" + 
				"			vw_itemrate.cost_price ," + 
				"			0" + 
				"		)," + 
				"		3" + 
				"	) AS unit_price" + 
				" FROM " + 
				"	(Select * FROM mrp_stock_item WHERE is_system = 0 AND is_deleted = 0)  si" + 
				" LEFT JOIN taxes tx ON si.`input_tax_id` = tx.id" + 
				" LEFT JOIN vw_itemstock vw ON vw.stock_item_id = si.id AND vw.department_id = 2" + 
				" INNER JOIN uoms ON si.uom_id = uoms.`id` "+costPriceQuery+"" + 
				" WHERE " + 
				" si.is_manufactured = 0" + 
				" OR (" + 
				"	si.is_manufactured = 1 " + 
				"	AND si.is_sellable = 0 " + 
				") ORDER BY si.NAME";
		return getTableRowsAsJson(sql);
	}

	
	public JsonArray getItemData() throws Exception {

		final String sql = "SELECT " + 
				"	si.id, " + 
				"	si. CODE, " + 
				"	si. NAME, " + 
				"	si.input_tax_id, " + 
				"	IFNULL(tx.tax1_percentage, 0) AS tax_percentage, " + 
				"	si.valuation_method, " + 
				"	si.is_active, " + 
				"	IFNULL(vw.current_stock, 0) AS current_stock, " + 
				"	si.is_manufactured, " + 
				//"	si.pack_contains, " + 
				"	uoms.`code` AS uomcode, " + 
				"	uoms.`name` AS uomname, " + 
				"	round( " + 
				"		ifnull( " + 
				"			vw_itemrate.cost_price , " + 
				"			0 " + 
				"		), " + 
				"		3 " + 
				"	) AS unit_price " + 
				"FROM " + 
				"	mrp_stock_item si " + 
				"LEFT JOIN taxes tx ON si.`input_tax_id` = tx.id " + 
				"LEFT JOIN vw_itemstock vw ON vw.stock_item_id = si.id " + 
				"AND vw.department_id = 2 " + 
				"INNER JOIN uoms ON si.uom_id = uoms.`id` " + 
				"LEFT JOIN vw_itemrate ON vw_itemrate.stock_item_id = si.id " + 
				"WHERE " + 
				"	si.is_system = 0 " + 
				"AND si.is_deleted = 0 " + 
				"AND si.is_active = 1 " + 
				"ORDER BY " + 
				"	si. NAME";

		return getTableRowsAsJson(sql);
	}
	
	public JsonArray getItemStockData(String itemCode, String itemName) throws Exception {
		// TODO Auto-generated method stub
		
		/*final String sql="SELECT "
				+ "name , current_stock  "
				+ "FROM vw_itemstock "
				+ "WHERE "
				+ "name LIKE '%"+itemCode+"%' and  department_id="+itemName+" "  ;*/
		
		final String sql="SELECT " + 
				"vw.code," + 
				"vw.name," + 
				"vw.current_stock," + 
				" dept.name as department_name " + 
				"FROM " + 
				"vw_itemstock vw INNER JOIN mrp_department dept ON vw.department_id = dept.id " + 
				"WHERE vw.`code`='"+itemCode+"' AND " + 
				"vw.`name`='"+itemName+"'";
		return getTableRowsAsJson(sql);

	}

	public JsonArray getPunitData(String uomCode) throws Exception {
		
		final String sql="SELECT " + 
				"	uoms.`code` AS uomcode, " + 
				"	uoms.id AS id, " + 
				"	uoms.id AS baseuomid, " + 
				"	uoms.`code` AS base_uom_code, " + 
				"	1 AS compound_unit " + 
				"FROM " + 
				"	uoms " + 
				"WHERE " + 
				"	uoms.`code` = '"+uomCode+"' AND uoms.is_deleted=0 " + 
				"UNION " + 
				"SELECT " + 
				"	uoms.`code` AS uomcode, " + 
				"	uoms.id AS id, " + 
				"	uom.base_uom_id AS baseuomid, " + 
				"	uom.`code` AS base_uom_code, " + 
				"	IFNULL(uom.compound_unit,1) AS compound_unit " + 
				"FROM " + 
				"	uoms " + 
				"INNER JOIN uoms uom ON uoms.id = uom.base_uom_id " + 
				"WHERE " + 
				"	uoms.`code` = '"+uomCode+"' AND uoms.is_deleted=0 ";
		return getTableRowsAsJson(sql);
	}

	public JsonArray getCategory() throws Exception {
		final String categorySql="SELECT id, `name` FROM mrp_item_category";
		return getTableRowsAsJson(categorySql);
	}

	public JsonArray getReportData() throws Exception {
		//itemasterDao = new ItemMasterDao(getContext());
		String costPrice = "";
		String costPriceQuery = "";
		//ApplicationContext context = itemasterDao.getContext();
		SystemSettings systemSettings=(SystemSettings) context.getCurrentHttpSession().getAttribute("systemSettings");

		if (context.getCompanyInfo().getId() != 0) {
		costPrice = " '"+", round(ifnull(vw_itemrate.cost_price,0),2) as unit_price"+" '";
			costPriceQuery = " '"+"LEFT JOIN  vw_itemrate on vw_itemrate.stock_item_id = si.id"+"' ";
		}
		else {
			costPrice = "'"+", round(IFNULL(si.item_cost,0),2) as unit_price,si.cost_price"+"'";
			costPriceQuery=null;
		}


		String sql="CALL usp_stock_item_data("+costPrice+", "+costPriceQuery+")";
		return getTableRowsAsJson(sql);
	}
	
		@Override
	public Double getLatestCostPrice(Integer stockId,Integer depId) throws Exception {
		final String sql = "SELECT dtl.cost_price FROM mrp_stock_register_dtl dtl"+ 
				"JOIN mrp_stock_register_hdr hdr  ON dtl.stock_register_hdr_id=hdr.id WHERE dtl.department_id="+depId+
				"AND dtl.stock_item_id="+stockId+" ORDER BY hdr.created_at DESC LIMIT 1";
		Double costPrice = 0.00;
		beginTrans();
		try {

			costPrice = (double) executeSQL(sql);
			System.out.println("costPrice================>"+costPrice);
			endTrans();
		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}

		return costPrice;
	}
	
	@Override
	public Double getCurrentStockCount(Integer stockId,Integer depId) throws Exception {
		/*final String sql = "SELECT dtl.cost_price FROM mrp_stock_register_dtl dtl"+ 
				"JOIN mrp_stock_register_hdr hdr  ON dtl.stock_register_hdr_id=hdr.id WHERE dtl.department_id="+depId+
				"AND dtl.stock_item_id="+stockId+" ORDER BY hdr.created_at DESC LIMIT 1";*/
		
		final String sql = "SELECT vw.current_stock FROM vw_itemstock vw INNER JOIN mrp_department dept ON vw.department_id = dept.id "+
							"WHERE vw.`stock_item_id`="+stockId+" AND vw.`department_id`="+depId+";";
		
		Double currentstockCount = 0.00;
		beginTrans();
		try {

			CachedRowSet srs = executeSQLForRowset(sql);
			if(srs!=null){

				if(srs.next())
					currentstockCount= srs.getDouble("current_stock");					
			}
			else
			{
				currentstockCount=0.0;
			}
			System.out.println("currentstockCount================>"+currentstockCount);
			endTrans();
		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}

		return currentstockCount;
	}
	
	public JsonArray getItemStockCount(String stockId, String DepId) throws Exception {
		
		
		final String sql="SELECT " + 
				"vw.code," + 
				"vw.name," + 
				"vw.current_stock," + 
				" dept.name as department_name " + 
				"FROM " + 
				"vw_itemstock vw INNER JOIN mrp_department dept ON vw.department_id = dept.id " + 
				"WHERE vw.`stock_item_id`='"+stockId+"' AND " + 
				"vw.`department_id`='"+DepId+"'";
       return getTableRowsAsJson(sql);
		
	}

}