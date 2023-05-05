package com.indocosmo.mrp.web.production.production.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.Hq;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.productionOrderStatusType;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.transactionType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.counter.dao.CounterDao;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias.SearchCriterias;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.production.production.model.Production;
import com.indocosmo.mrp.web.production.production.productiondetail.model.ProductionDetail;

/**
 * @author jiksa
 *
 */
public class ProductionDao extends GeneralDao<Production> implements
IProductionDao {
	private CounterDao counterDao;

	/**
	 * @param context
	 */
	public ProductionDao(ApplicationContext context) {

		super(context);
		counterDao=new CounterDao(getContext());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Production getNewModelInstance() {

		return new Production();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "mrp_prod_hdr";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#getDataTableList(com.indocosmo
	 * .mrp.web.masters.datatables.DataTableCriterias, java.util.List)
	 */
	@Override
	public JsonArray getDataTableList(DataTableCriterias datatableParameters,
			List<String> tableFields) throws Exception {

		String sql;

		if (datatableParameters.getSearch().get(SearchCriterias.value)
				.isEmpty()) {

			sql = "SELECT  *  " + "FROM " + getTable() + " " + "LIMIT "
					+ datatableParameters.getLength() + " " + "OFFSET "
					+ datatableParameters.getStart() + "";
		} else {

			sql = " SELECT  *  "
					+ "FROM "
					+ getTable()
					+ " as rb "
					+ "WHERE "
					+ "rb."
					+ tableFields.get(0)
					+ " LIKE '%"
					+ datatableParameters.getSearch()
					.get(SearchCriterias.value) + "%'";

		}

		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#dataTableTotalRecord()
	 */
	@Override
	public Integer dataTableTotalRecord() {

		Integer countResults = 0;

		String sqlQuery = "SELECT COUNT(*) AS row_count FROM " + getTable();

		try {

			countResults = excuteSQLForRowCount(sqlQuery);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return countResults;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql = "SELECT * from "+getTable()+"";

		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#delete(java.lang.String)
	 */
	@Override
	public Integer delete(String where) throws Exception {

		final String sql = "DELETE FROM " + getTable() + " WHERE " + where;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getAdditionalFilter(java.
	 * util.List)
	 */
	@Override
	public String getAdditionalFilter(List<DataTableColumns> columnList) {

		String adnlFilterPart = "";

		for (DataTableColumns col : columnList) {



			if (col.getSearchValue() != "" && col.getSearchValue() != null) {

				if(col.getData().equals("status")) {
					adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " AND ")	+ ""+col.getData()+" IN("+col.getSearchValue()+")";	
				}else
					adnlFilterPart += ((adnlFilterPart.isEmpty()) ? " " : " AND ")
					+ "" + col.getData() + " like ('%"
					+ col.getSearchValue() + "%')";
			}
		}
		return adnlFilterPart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,
			String wherePart, String searchCriteria, String sortPart,
			int limitRows, int offset, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {

		if (searchCriteria != "" && searchCriteria != null) {

			wherePart = " WHERE " + "(prod_no LIKE '%"+ searchCriteria + "%' "
					+ " OR prod_date LIKE '%"+ searchCriteria + "%' "
					/*+ " OR shop_name LIKE '%"+ searchCriteria + "%' "*/
					/*+ " OR dept LIKE '%"+ searchCriteria + "%' "*/
					/*+ " OR status_name LIKE '%"+ searchCriteria + "%'"*/
							+ ")";
		}

		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart != null && wherePart != "") {

				wherePart += " AND " + adnlFilterPart;
			} else {

				wherePart = " WHERE " + adnlFilterPart;
			}
		}
		
	/*	String sql="SELECT * "
				+ "FROM (SELECT mrp_prod_hdr.*, customers.`name` AS shop_name,"
				+ "(CASE WHEN customers.shop_id IS NOT NULL THEN 0 ELSE 1 END) AS slctypreq,"
				+ "(CASE WHEN mrp_prod_hdr.`status` = 0 THEN 'pending' "
				+ "WHEN mrp_prod_hdr.`status` = 1 THEN 'finalized' ELSE 1 END) AS status_name "
				+ "FROM mrp_prod_hdr "
				+ "LEFT JOIN customers ON mrp_prod_hdr.shop_id = customers.id) tbl"
				+ wherePart + " " + sortPart + " " + "LIMIT " + limitRows + " "
				+ "OFFSET " + offset + "";*/

		/*String sql="SELECT * "
				+ "FROM (SELECT dep.name AS dept,mrp_prod_hdr.*, customers.`name` AS shop_name,"
				+ "(CASE WHEN customers.shop_id IS NOT NULL THEN 0 ELSE 1 END) AS slctypreq,"
				+ "(CASE WHEN mrp_prod_hdr.`status` = 0 THEN 'pending' "
				+ "WHEN mrp_prod_hdr.`status` = 1 THEN 'finalized' ELSE 1 END) AS status_name "
				+ "FROM mrp_prod_hdr "
				+ "LEFT JOIN customers ON mrp_prod_hdr.shop_id = customers.id "
				+" INNER JOIN mrp_prod_dtl dtl ON mrp_prod_hdr.id = dtl.prod_hdr_id "
				+" INNER JOIN mrp_department dep ON dtl.department_id = dep.id ) tbl" 
				+ wherePart + " " + sortPart + " " + "LIMIT " + limitRows + " "
				+ "OFFSET " + offset + "";*/
		
		
		String sql="SELECT * "
				+ "FROM mrp_prod_hdr "
				+ wherePart + " " + sortPart + " " + "LIMIT " + limitRows + " "
				+ "OFFSET " + offset + "";	

	/*	
		String sql="SELECT mrp_prod_hdr.*, customers.`name` AS shop_name,(CASE "
				+ "WHEN customers.shop_id IS NOT NULL THEN 0 ELSE 1  END) AS slctypreq "
				+ "FROM mrp_prod_hdr LEFT JOIN customers ON mrp_prod_hdr.shop_id = customers.id" 
				+ wherePart + " " + sortPart + " " + "LIMIT " + limitRows + " "
				+ "OFFSET " + offset + "";*/

		
		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable
	 * (java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,
			String searchCriteria, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {

		if (searchCriteria != "" && searchCriteria != null) {

			wherePart = " WHERE " + "(prod_no LIKE '%"+ searchCriteria + "%' "
					+ " OR prod_date LIKE '%"+ searchCriteria + "%'"
					+ " OR shop_name LIKE '%"+ searchCriteria + "%' "
						+ " OR dept LIKE '%"+ searchCriteria + "%' "
					+ " OR status_name LIKE '%"+ searchCriteria + "%')";
			}

		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart != null && wherePart != "") {

				wherePart += " AND " + adnlFilterPart;
			} else {

				wherePart = " WHERE " + adnlFilterPart;
			}
		}

		/*String sqlCount = "SELECT "
				+ "COUNT("
				+ getTable()
				+ ".id) as row_count "
				+ "FROM "
				+ getTable()
				+ wherePart + "";
*/
		
		
		
/*	String sqlCount="SELECT COUNT(id)  as row_count "
			+ "FROM (SELECT mrp_prod_hdr.*, customers.`name` AS shop_name,"
			+ "(CASE WHEN customers.shop_id IS NOT NULL THEN 0 ELSE 1 END) AS slctypreq,"
			+ "(CASE WHEN mrp_prod_hdr.`status` = 0 THEN 'pending' "
			+ "WHEN mrp_prod_hdr.`status` = 1 THEN 'finalized' ELSE 1 END) AS status_name "
			+ "FROM mrp_prod_hdr "
			+ "LEFT JOIN customers ON mrp_prod_hdr.shop_id = customers.id) tbl"
			+ wherePart + "";*/
		String sqlCount="SELECT COUNT(id)  as row_count "
				+ "FROM (SELECT  dep.name AS dept, mrp_prod_hdr.*, customers.`name` AS shop_name,"
				+ "(CASE WHEN customers.shop_id IS NOT NULL THEN 0 ELSE 1 END) AS slctypreq,"
				+ "(CASE WHEN mrp_prod_hdr.`status` = 0 THEN 'pending' "
				+ "WHEN mrp_prod_hdr.`status` = 1 THEN 'finalized' ELSE 1 END) AS status_name "
				+ "FROM mrp_prod_hdr "
				+ "LEFT JOIN customers ON mrp_prod_hdr.shop_id = customers.id "
				+" INNER JOIN mrp_prod_dtl dtl ON mrp_prod_hdr.id = dtl.prod_hdr_id "
				+" INNER JOIN mrp_department dep ON dtl.department_id = dep.id ) tbl" 
				+ wherePart + "";
	
	
		CachedRowSet totalRecs = executeSQLForRowset(sqlCount);
		return totalRecs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.stock.stockdisposal.dao.IStockDisposalDao#
	 * getStockDispDtlData
	 * (com.indocosmo.mrp.web.stock.stockdisposal.model.StockDisposal)
	 */
	@Override
	public JsonArray getStockDispDtlData(Production stockDisp)
			throws Exception {

		String sql = "SELECT "
				+ "a.*,srd.id as stockRegDtl_id "
				+ "FROM "
				+ "(SELECT "
				+ "sdd.id AS id,sdd.stock_disposal_hdr_id AS stock_disposal_hdr_id,"
				+ "sdd.stock_item_id AS stock_item_id,sdd.dispose_qty AS dispose_qty,sdd.cost_price AS cost_price,"
				+ "sdd.reason AS reason,stkItm.`code` AS stock_item_code,stkItm.`name` AS stock_item_name,"
				+ "uom. CODE AS uomcode,srh.id AS stkHdrId "
				+ "FROM "
				+ ""+getTable()+" sdh "
				+ "INNER JOIN mrp_stock_disposal_dtl sdd ON sdh.id = sdd.stock_disposal_hdr_id "
				+ "INNER JOIN mrp_stock_item stkItm ON stkItm.id = sdd.stock_item_id "
				+ "LEFT JOIN uoms uom ON stkItm.uom_id = uom.id "
				+ "INNER JOIN mrp_stock_register_hdr srh ON srh.ext_ref_no = sdh.ref_no "
				+ "WHERE "
				+ "sdd.stock_disposal_hdr_id = "
				+ stockDisp.getId()
				+ " )a  "
				+ "INNER JOIN  mrp_stock_register_dtl srd ON srd.stock_register_hdr_id=a.stkHdrId AND srd.ext_ref_dtl_id=a.id";

		return getTableRowsAsJson(sql);
	}

	public JsonArray getMaterialAndOtherCost() throws Exception
	{
		final String sql="SELECT  "
				+ "bomcalc.id AS stock_id,bomcalc.`code` AS stock_code,bomcalc.name AS stock_name,"
				+ "IFNULL(bomcalc.material_cost,0) AS material_cost,IFNULL(prodcalc.other_cost,0) AS other_cost "
				+ "FROM vw_bom_cost_calc bomcalc "
				+ "INNER JOIN vw_prod_costcalc prodcalc ON bomcalc.id=prodcalc.id ";
		return getTableRowsAsJson(sql);
	}


	public JsonArray getSalesOrder() throws Exception {
		String sql="select * from vw_production";
		return getTableRowsAsJson(sql);
	}
	public JsonArray getItemRates() throws Exception {

		final String sql = "SELECT * from vw_itemrate";

		return getTableRowsAsJson(sql);
	}

	/**
	 * @param prodDate
	 * @return
	 */
	public JsonArray getProductionOrderData(String prodDate,String orderIds,HttpSession session) throws Exception {


		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

		String orderQuery="";
		if(!orderIds.equals(""))
		{
			orderQuery=" hdr.order_id IN ("+orderIds+") AND "; 
		}
		
		
		String sql="SELECT "
				+ "prd.stock_item_id AS stock_item_id,prd.stock_item_code AS stock_item_code,"
				+ "prd.stock_item_name AS stock_item_name,prd.uomcode AS uomcode,"
				+ "prd.delivery_date AS delivery_date,GREATEST((prd.balnce_qty + (SELECT SUM(TBL.ord_qty) "
				+ "FROM "
				+ "(SELECT smry.stock_item_id AS stock_item_id, GREATEST(sum(smry.order_qty) - sum(issued_qty),0) AS ord_qty "
				+ "FROM "
				+ "order_booking_summary smry "
				+ "WHERE "
				+ "smry.trans_date = '"+prodDate+"' "
				+ "AND smry.id <= (SELECT max(id) "
				+ "FROM "
				+ "order_booking_summary smry1 "
				+ "LEFT JOIN order_hdrs_booking hdr ON smry1.ext_ref_id = hdr.order_no "
				+ "WHERE "
				+ ""+orderQuery+" "
				+ " smry1.trans_date = '"+prodDate+"') GROUP BY smry.stock_item_id,smry.trans_date "
				+ "UNION "
				+ "SELECT "
				+ "odb.sale_item_id AS stock_item_id, sum(odb.qty)  AS ord_qty "
				+ "FROM "
				+ "order_hdrs_booking ohb "
				+ "LEFT JOIN "
				+ "order_dtls_booking odb ON ohb.order_id = odb.order_id "
				+ "WHERE "
				+ "ohb.closing_date = '"+prodDate+"'  "
				+ "AND "
				+ "ohb.customer_id = 0 "
				+ "GROUP BY "
				+ "odb.sale_item_id,ohb.closing_date) TBL "
				+ "WHERE "
				+ "TBL.stock_item_id = prd.stock_item_id)),0) AS order_qty,"
				+ "0 AS flag,ROUND(prd.total_qty, "+systemSettings.getDecimal_places()+") AS prod_qty,0 AS schedule_qty,"
				+ "0 AS damageqty,ROUND(prd.itemMaterialCost, "+systemSettings.getDecimal_places()+") AS itemMaterialCost,ROUND(prd.otherCost,"+systemSettings.getDecimal_places()+") AS otherCost,0 AS saleRate,"
				+ "prd.prod_hdr_id AS prodnHdrId "
				+ "FROM "
				+ "vw_production prd "
				+ "LEFT JOIN "
				+ "order_hdrs_booking hdrbook ON hdrbook.order_id = prd.order_id "
				+ "WHERE "
				+ "prd.delivery_date = '"+prodDate+"'";
		
		
		

		return getTableRowsAsJson(sql);
	}

	/**
	 * @param stockDisp
	 * @return
	 */
	public JsonArray getProductionDtlData(Production prodHdr) throws Exception{
		// TODO Auto-generated method stub

		String sql="select  dtl.id as id,hdr.id as prodnHdrId,st_hdr.id as stckRegHdrId,"
				+ "hdr.prod_no as prod_no,hdr.prod_time as prod_time,"
				+ "dtl.department_id,"
				+ "dtl.stock_item_id,dtl.stock_item_code,"
				+ "s.name as stock_item_name,dtl.schedule_qty,dtl.order_qty,dtl.prod_qty,dtl.remarks,"
				+ " 0 as flag ,0 as Dflag,	dtl.total_cost AS itemRate,dtl.material_cost AS itemMaterialCost,"
				+ "dtl.other_cost as otherCost,dtl.total_cost as totalCost,"
				+ "dtl.sales_price AS saleRate,"
				/*+ " dtl.sales_value as sales_value,"*/
				+ "dtl.damage_qty as damageqty,"
				/*+ "dtl.stock_value as stock_value,"*/
				+ "u.code as uomcode, hdr.remarks as hdrRemarks,hdr.status as status"
				+ " from mrp_prod_hdr hdr LEFT JOIN mrp_prod_dtl dtl on dtl.prod_hdr_id=hdr.id "
				+ "INNER JOIN mrp_stock_item s ON s.id = dtl.stock_item_id LEFT JOIN uoms u on u.id=s.uom_id "
				+ "LEFT JOIN mrp_stock_register_hdr st_hdr ON (st_hdr.ext_ref_id=hdr.id AND "
				+ "st_hdr.trans_type= "+transactionType.PRODUCTION.gettransactionTypeId()+""
				+ " ) "
				+ " where hdr.id='"+ prodHdr.getId()+"' GROUP BY stock_item_id";
		return getTableRowsAsJson(sql);
	}



	public JsonArray getOnProductionIds(String prodDate) throws Exception{
		// TODO Auto-generated method stub

		final String sql="select order_id from order_hdrs_booking hdr where hdr.status=2 and hdr.mrp_prod_hdr_id is NULL"
				+ " AND hdr.closing_date='"+prodDate+"'";
		return getTableRowsAsJson(sql);
	}

	/**
	 * @param prod_id
	 * @param orderIdsString
	 * @throws Exception 
	 */
	public void updateOrderHdr(Integer prod_id, String orderIdsString) throws Exception {
		// TODO Auto-generated method stub
		final String sqlUpdate="UPDATE order_hdrs_booking "
				+ "SET mrp_prod_hdr_id="+prod_id+""
				/*+ "status="+productionOrderStatusType.ON_PRODUCTION.getProductionOrderStatusId()+""*/
				+ " WHERE order_id IN ("+orderIdsString+")";
		executeSQL(sqlUpdate);
	}

	/**
	 * @param prodId
	 * @return
	 */
	public JsonArray getOrderHdrIdsByProdId(Integer prodId) throws Exception {
		// TODO Auto-generated method stub
		final String sql1="SELECT order_id from order_hdrs_booking WHERE mrp_prod_hdr_id="+prodId;
		return getTableRowsAsJson(sql1);
	}


	public JsonArray getBomQty(int itemId) throws Exception{
		// TODO Auto-generated method stub
		JsonArray bomQtyArry;

		final String sql1="SELECT ifnull(stock_item_qty,0) as bom_qty from stock_item_bom sib where sib.stock_item_id=  "+itemId+" and sib.is_deleted=0 limit 1";
		final String sql2="SELECT ifnull(stock_item_qty,0) as bom_qty from mrp_stock_item_prod_costcalc sib where sib.stock_item_id=  "+itemId+" and sib.is_deleted=0 limit 1";

		bomQtyArry= getTableRowsAsJson(sql1);
		if(bomQtyArry.size()==0)
		{
			bomQtyArry= getTableRowsAsJson(sql2);
		}
		return bomQtyArry;
	}

	public Integer updateBomQty(Integer stockId, Double bomQty,boolean isLiteVersion) {
		// TODO Auto-generated method stub
		Integer update=0;
		final String sql="UPDATE stock_item_bom SET stock_item_qty="+bomQty+" WHERE stock_item_id="+stockId+" AND is_deleted=0";
		final String sql2="UPDATE mrp_stock_item_prod_costcalc SET stock_item_qty="+bomQty+" WHERE stock_item_id="+stockId+" AND is_deleted=0";
		final String sql1="UPDATE sync_queue SET crud_action='U' WHERE record_id="+stockId+" AND table_name='mrp_stock_item' ";
		try
		{
			update=executeSQL(sql);
			update=executeSQL(sql2);
			if(update !=0)
			{
				if(isLiteVersion == false && context.getCurrentHttpSession().getAttribute("COMPANY_ID").equals(Hq.HQ.getHqId()))
				{
					update=executeSQL(sql1);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return update;
	}

	@Override
	public Integer updateStatus(Integer id) throws Exception {
		final String sql="update "+getTable()+" set status=1 where id="+id+"";
		final String updateOrderHdrStatus="UPDATE order_hdrs_booking "
				+ "SET "
				+ "status="+productionOrderStatusType.FINISH_PRODUCTION.getProductionOrderStatusId()+""
				+ " WHERE mrp_prod_hdr_id="+id;
		Integer status=executeSQL(sql);
		executeSQL(updateOrderHdrStatus);
		return status;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.production.production.dao.IProductionDao#updateProDBomItems(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public int updateProDBomItems(Integer dtl_id, Integer roundVal,
			Integer source_dep, Integer dest_dep, Integer stock_hdr_id)
					throws Exception {
		Connection con = null;
		CallableStatement st = null;
		con =this.dbHelper.getConnection();
		st = con.prepareCall("{call usp_daily_prod_finalize(?,?,?,?,?,?,?)}");
		// st=con.prepareCall("{call usp_production_finalize(?,?,?,?,?,?)}");
		st.setInt(1, dtl_id);
		st.setInt(2, roundVal);
		st.setInt(3, source_dep);
		st.setInt(4, dest_dep);
		st.setInt(5, stock_hdr_id);
		st.setInt(6, CounterDao.COUNTER_CONST);
		st.registerOutParameter(7, Types.INTEGER);
		st.executeUpdate();
		Integer error=st.getInt("error_status");
		
		return error;
	}

	/**
	 * @param parseInt
	 * @return
	 */
	public void upDateProductionOrderData(int prodHdrId) throws Exception{
		// TODO Auto-generated method stub
		String updateQuery="UPDATE order_hdrs_booking SET mrp_prod_hdr_id=NULL where mrp_prod_hdr_id="+prodHdrId;
		executeSQL(updateQuery);

	}

	public JsonArray getShopWiseOrderData(int stockItemId,String deliveryDate,HttpSession session) throws Exception
	{
		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		final String sql1="select hdr.customer_id,ci.name as customer_name,hdr.status,"
				+ "dtl.sale_item_id as stock_item_id,si.`name` as stock_item_name ,round(sum(dtl.qty),"+systemSettings.getDecimal_places()+") as orderqty,"
				+ "hdr.closing_date as delivery_date,round(ifnull(bal.balnce_qty,0),"+systemSettings.getDecimal_places()+") as balanceqty "
				+ "from order_hdrs_booking hdr left join order_dtls_booking dtl on hdr.order_id=dtl.order_id "
				+ "LEFT JOIN vw_production bal on "
				+ "(bal.stock_item_id=dtl.sale_item_id  and bal.delivery_date=hdr.closing_date) "
				+ "left join mrp_stock_item si on si.id=dtl.sale_item_id "
				+ "left join customers ci on ci.id=hdr.customer_id GROUP BY sale_item_id,customer_id,closing_date,status"
				+ " HAVING stock_item_id="+stockItemId+" AND delivery_date='"+deliveryDate+"' AND "
				+ "status="+productionOrderStatusType.ON_PRODUCTION.getProductionOrderStatusId()+"";

		return getTableRowsAsJson(sql1);
	}

	/**
	 * @param orderIds
	 * @param stockId
	 * @param date
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	public JsonArray getCurrentDayPreviousBalance(String orderIds,
			Integer stockId, String date, HttpSession session) throws Exception {
		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		
		final String sql="select smry.stock_item_id,"
				+ "sum(smry.order_qty)-sum(issued_qty)as currentDayPrevBalance,"
				+ "smry.trans_date from order_booking_summary smry "
				+ "where smry.stock_item_id="+stockId+" "
				+ "and smry.id <= (SELECT max(id) from order_booking_summary smry1 left join order_hdrs_booking hdr"
				+ " on smry1.ext_ref_id=hdr.order_no where hdr.order_id in("+orderIds+") and smry1.trans_date='"+date+"') "
				+ "GROUP BY smry.stock_item_id,smry.trans_date having trans_date='"+date+"'";
		return getTableRowsAsJson(sql);
	}

	public JsonArray getCustomerIdsAsJson() throws Exception {
	
		// TODO Auto-generated method stub
		final String Sql1="SELECT id,code,name from vw_production_customers WHERE shop_id IS NULL";
		return getTableRowsAsJson(Sql1);

	}

	public JsonArray getOnProductionIdsShopwise(String currentDate , String prod_upto, String custId) throws Exception {
		/*Date date = new Date();
		String today= new SimpleDateFormat("yyyy-MM-dd").format(date);*/
		
		final String sql="select order_id from order_hdrs_booking hdr where hdr.status=2 and hdr.mrp_prod_hdr_id is NULL"
				+ " AND hdr.closing_date BETWEEN '"+currentDate+"' AND '"+prod_upto+"' AND hdr.customer_id='"+custId+"'";
		return getTableRowsAsJson(sql);
		
	}

	public JsonArray getProductionOrderDataShopwise(String currntDate , String prodDate , String custId, Integer request_type, HttpSession session) throws Exception {
		
		
		/*Date date = new Date();
		String today= new SimpleDateFormat("yyyy-MM-dd").format(date);*/
		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

		String sql="";
		
		if(request_type==0)
		{
			sql="SELECT "
					+ "obs.stock_item_id AS stock_item_id,mstk. CODE AS stock_item_code,mstk. NAME AS stock_item_name,"
					+ "um. CODE AS uomcode,obs.shop_id shop_id,"
					+ "obs.trans_date AS delivery_date,"
					+ "IFNULL((SELECT(SUM(order_qty) - SUM(issued_qty)) "
					+ "FROM order_booking_summary "
					+ "WHERE trans_date < obs.trans_date "
					+ "AND shop_id = obs.shop_id "
					+ "AND stock_item_id = obs.stock_item_id "
					+ "GROUP BY shop_id,stock_item_id),0) AS opening_qty,"
					+ "IFNULL(SUM(obs.order_qty), 0) AS order_qty,0 AS flag,"
					+ "ROUND(SUM(obs.order_qty), "+systemSettings.getDecimal_places()+") AS prod_qty,0 AS schedule_qty,0 AS damageqty,"
					+ "ROUND(IFNULL(bomcalc.material_cost,0),"+systemSettings.getDecimal_places()+") itemMaterialCost,"
					+ "ROUND(IFNULL(costcalc.other_cost,0),"+systemSettings.getDecimal_places()+") otherCost,0 AS saleRate,IFNULL(hdbk.mrp_prod_hdr_id,0) AS prodnHdrId "
					+ "FROM order_booking_summary obs "
					+ "INNER JOIN mrp_stock_item mstk ON obs.stock_item_id = mstk.id "
					+ "LEFT JOIN uoms um ON mstk.uom_id = um.id "
					+ "INNER JOIN order_hdrs_booking hdbk ON obs.ext_ref_id = hdbk.order_no "
					+ "LEFT JOIN vw_bom_cost_calc bomcalc ON bomcalc.id = mstk.id "
					+ "LEFT JOIN vw_prod_costcalc costcalc ON costcalc.id = mstk.id "
					+ "WHERE obs.shop_id = '"+custId+"' AND  obs.trans_date BETWEEN '"+currntDate+"' AND '"+prodDate+"'  AND hdbk.`status` = 2 AND isnull(hdbk.mrp_prod_hdr_id) "
					+ "GROUP BY obs.shop_id,obs.stock_item_id";
		}
		else
		{
			
			/*sql="SELECT "
					+ "tbl.*,ROUND((order_qty1+opening_qty),"+systemSettings.getDecimal_places()+")AS prod_qty,ROUND((order_qty1+opening_qty),"+systemSettings.getDecimal_places()+")AS order_qty "
					+ "FROM "
					+ "(SELECT "
					+ "obs.stock_item_id AS stock_item_id,mstk. CODE AS stock_item_code,mstk. NAME AS stock_item_name,"
					+ "um. CODE AS uomcode,obs.shop_id shop_id,obs.trans_date AS delivery_date,"
					+ "IFNULL((SELECT(vpb.previous_balance-vpb.adjust_qty + vpb.prod_order_qty - vpb.prod_qty) FROM vw_production_balance "
					+ "vpb WHERE vpb.shop_id = obs.shop_id AND vpb.trans_date = '"+today+"' AND "
					+ "vpb.stock_item_id = obs.stock_item_id),0) AS opening_qty,IFNULL(SUM(obs.order_qty), 0) AS order_qty1,"
					+ "0 AS flag,0 AS schedule_qty,0 AS damageqty,ROUND(IFNULL(bomcalc.material_cost,0),"+systemSettings.getDecimal_places()+") itemMaterialCost,"
					+ " ROUND(IFNULL(costcalc.other_cost,0),"+systemSettings.getDecimal_places()+") otherCost,0 AS saleRate,IFNULL(hdbk.mrp_prod_hdr_id,0) AS prodnHdrId "
					+ "FROM "
					+ "order_booking_summary obs "
					+ "INNER JOIN mrp_stock_item mstk ON obs.stock_item_id = mstk.id "
					+ "LEFT JOIN uoms um ON mstk.uom_id = um.id "
					+ "INNER JOIN order_hdrs_booking hdbk ON obs.ext_ref_id = hdbk.order_no "
					+ "LEFT JOIN vw_bom_cost_calc bomcalc ON bomcalc.id = mstk.id "
					+ "LEFT JOIN vw_prod_costcalc costcalc ON costcalc.id = mstk.id "
					+ "WHERE "
					+ "obs.shop_id = '"+custId+"' AND obs.trans_date BETWEEN '"+today+"' AND '"+prodDate+"' "
					+ "AND hdbk.`status` = 2 AND isnull(hdbk.mrp_prod_hdr_id) GROUP BY obs.shop_id,obs.stock_item_id)tbl";*/
			
			
			
			sql="SELECT "
					+ "tbl.*,ROUND((order_qty1+opening_qty+prod_order_qty-prod_qty_from),"+systemSettings.getDecimal_places()+")AS prod_qty,ROUND((order_qty1+opening_qty+prod_order_qty-prod_qty_from),"+systemSettings.getDecimal_places()+")AS order_qty "
					+ "FROM "
					+ "(SELECT "
					+ "obs.stock_item_id AS stock_item_id,mstk. CODE AS stock_item_code,mstk. NAME AS stock_item_name,"
					+ "um. CODE AS uomcode,obs.shop_id shop_id,obs.trans_date AS delivery_date,"
					+ "(SELECT ifnull(sum(obsm.order_qty), 0) "
					+ "FROM order_booking_summary obsm "
					+ "JOIN order_hdrs_booking orhdbk ON obsm.ext_ref_id = orhdbk.order_no JOIN mrp_prod_hdr prodhd ON "
					+ "prodhd.id = orhdbk.mrp_prod_hdr_id "
					+ "WHERE obsm.shop_id = obs.shop_id AND obsm.stock_item_id = obs.stock_item_id AND "
					+ "prodhd.prod_date =  '"+currntDate+"') AS prod_order_qty,"
					+ "(SELECT ifnull(sum(mrp_prod_dtl.prod_qty),0) "
					+ "FROM "
					+ "mrp_prod_hdr JOIN mrp_prod_dtl ON mrp_prod_hdr.id = mrp_prod_dtl.prod_hdr_id "
					+ "WHERE mrp_prod_hdr.shop_id = obs.shop_id AND  mrp_prod_hdr.prod_date = '"+currntDate+"' "
					+ "AND mrp_prod_dtl.stock_item_id = obs.stock_item_id) AS prod_qty_from,"
					+ "IFNULL((SELECT(vpb.previous_balance-vpb.adjust_qty ) FROM vw_production_balance "
					+ "vpb WHERE vpb.shop_id = obs.shop_id AND vpb.trans_date = '"+currntDate+"' AND "
					+ "vpb.stock_item_id = obs.stock_item_id),0) AS opening_qty,IFNULL(SUM(obs.order_qty), 0) AS order_qty1,"
					+ "0 AS flag,0 AS schedule_qty,0 AS damageqty,ROUND(IFNULL(bomcalc.material_cost,0),"+systemSettings.getDecimal_places()+") itemMaterialCost,"
					+ " ROUND(IFNULL(costcalc.other_cost,0),"+systemSettings.getDecimal_places()+") otherCost,0 AS saleRate,IFNULL(hdbk.mrp_prod_hdr_id,0) AS prodnHdrId "
					+ "FROM "
					+ "order_booking_summary obs "
					+ "INNER JOIN mrp_stock_item mstk ON obs.stock_item_id = mstk.id "
					+ "LEFT JOIN uoms um ON mstk.uom_id = um.id "
					+ "INNER JOIN order_hdrs_booking hdbk ON obs.ext_ref_id = hdbk.order_no "
					+ "LEFT JOIN vw_bom_cost_calc bomcalc ON bomcalc.id = mstk.id "
					+ "LEFT JOIN vw_prod_costcalc costcalc ON costcalc.id = mstk.id "
					+ "WHERE "
					+ "obs.shop_id = '"+custId+"' AND obs.trans_date BETWEEN '"+currntDate+"' AND '"+prodDate+"' "
					+ "AND hdbk.`status` = 2 AND isnull(hdbk.mrp_prod_hdr_id) GROUP BY obs.shop_id,obs.stock_item_id)tbl";
			
			
			
			
		}
		
		
		
		
	
		

		return getTableRowsAsJson(sql);
	}

	public JsonArray getShopWiseOrderDataShopwise(Integer stockId , Integer shopId , String date , String prod_date, HttpSession session) throws Exception {
	
		// TODO Auto-generated method stub
		
		SystemSettings systemSettings =(SystemSettings)session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
	/*	Date datetdy = new Date();
		String today= new SimpleDateFormat("yyyy-MM-dd").format(datetdy);*/
		
		String sql="SELECT obs.shop_id,ci. NAME AS customer_name,hdr. STATUS,obs.stock_item_id AS stock_item_id,"
				+ "si.`name` AS stock_item_name,round(sum(obs.order_qty), "+systemSettings.getDecimal_places()+") AS orderqty,hdr.closing_date AS delivery_date,0 AS balanceqty "
				+ "FROM order_booking_summary obs "
				+ "INNER JOIN order_hdrs_booking hdr ON obs.ext_ref_id=hdr.order_no "
				+ "LEFT JOIN customers ci ON ci.id = obs.shop_id "
				+ "LEFT JOIN mrp_stock_item si ON si.id =obs.stock_item_id "
				+ "WHERE obs.trans_date BETWEEN '"+prod_date+"' AND '"+date+"' "
				+ "AND obs.stock_item_id = "+stockId+" AND obs.shop_id="+shopId+" AND  hdr.`status`=2";
		
		
		
		
		return getTableRowsAsJson(sql);

	}

	public JsonArray getDepList() throws Exception {
	
		// TODO Auto-generated method stub
    //final String sql="SELECT id,code,name from mrp_department WHERE dept_type=1 AND id!=0 AND (is_deleted=0 OR is_deleted IS NULL) ";
		final String sql = "SELECT id,code,name FROM mrp_department WHERE (IFNULL(is_deleted,0) = 0)";
		
		return getTableRowsAsJson(sql);	}

	public JsonArray getPending(String currentDate , String custId) throws Exception {
	
		// TODO Auto-generated method stub
		/*Date datetdy = new Date();
		String today= new SimpleDateFormat("yyyy-MM-dd").format(datetdy);*/
	    final String sql="SELECT * from mrp_prod_hdr where shop_id='"+custId+"' and  prod_date='"+currentDate+"' and status=0";

		return getTableRowsAsJson(sql);
	}

	public JsonArray getPendingStockItem(String currentDate) throws Exception{
	
		// TODO Auto-generated method stub
		final String sql="SELECT hdr.* FROM mrp_prod_hdr hdr "
				+ "INNER JOIN mrp_prod_dtl dtl ON hdr.id = dtl.prod_hdr_id "
				+ "WHERE `status` = 0 AND prod_date ='"+currentDate+"' AND dtl.stock_item_id IN (SELECT order_dtl.sale_item_id FROM order_hdrs_booking order_hd "
				+ "INNER JOIN order_dtls_booking order_dtl ON order_hd.order_id = order_dtl.order_id "
				+ "WHERE order_hd. STATUS = 2 AND order_hd.closing_date='"+currentDate+"'"
				+ "AND ISNULL(order_hd.mrp_prod_hdr_id))";
		return getTableRowsAsJson(sql);	
	}

	public JsonArray getAllCustomerIds() throws Exception{
	
		// TODO Auto-generated method stub
				final String Sql1="SELECT id,code,name from vw_production_customers WHERE shop_id IS NULL";
				return getTableRowsAsJson(Sql1);
	}
	
	public JsonArray getShopList() throws Exception{

		final String shop_query = "SELECT id,code,name,shop_id "
								+ "from "
								+ "vw_production_customers "
								+ "WHERE " 
								+ "shop_id IS NOT NULL";	
		return getTableRowsAsJson(shop_query);
	}

	public JsonArray getVerifiedTotalItems(String delevery_date , String custId, String deptId,String checkedItemArray,
			String dtlArray) throws Exception
	{
		String cust_query="";
		SystemSettings systemSettings =(SystemSettings) context.getCurrentHttpSession().getAttribute("systemSettings");
		
		if(!custId.equals("") && !custId.equals("0"))
		{
			cust_query="AND hdr.shop_code = '"+custId+"'";
		}
	/*	if(custId.equals("0"))
		{
			cust_query="AND hdr.customer_id = '"+custId+"'";
		}*/
		
		if(deptId!=null && !deptId.equals("")){
			cust_query+=" AND stkitem.prd_department_id = '"+deptId+"'";
		}
		
		if(checkedItemArray!=null && !checkedItemArray.equals("")){
			cust_query+=" AND stkitem.id IN ("+checkedItemArray+")";
		}
		
		if(dtlArray!=null && !dtlArray.equals(""))
		{
			cust_query+=" AND dtl.id IN ("+ dtlArray +")";
		}
		else{
			cust_query += " AND dtl.`status` = 1 ";
		}
		
		final String Sql1="SELECT  false AS check_val,dtl.order_id AS order_id,dtl.id AS order_dtl_id,"
				+ "dpt.`name` AS department,dpt.id as dept_id,"
				+ "dtl.sale_item_id AS sale_item_id,stkitem.`name` AS stock_item_name,"
				+ "hdr.closing_time_slot_id AS closing_time_slot_id,timeslt.`name` AS timeslot,round(dtl.qty, "
				+ systemSettings.getDecimal_places()+") as qty, dtl.remarks,hdr.shop_code,hdr.customer_id, hdr.order_no "
				+ "FROM "
				+ "order_hdrs_booking hdr "
				+ "INNER JOIN "
				+ "order_dtls_booking dtl ON hdr.order_id = dtl.order_id "
				+ "INNER JOIN "
				+ "mrp_stock_item stkitem ON stkitem.id = dtl.sale_item_id "
				+ "INNER JOIN "
				+ "mrp_time_slot timeslt ON timeslt.id = hdr.closing_time_slot_id "
				+ "LEFT JOIN "
				+ "mrp_department dpt ON stkitem.prd_department_id=dpt.id "
				+ "WHERE "
				+ "hdr.closing_date = '"+delevery_date+"' "
				+ cust_query +" GROUP BY  sale_item_id,closing_time_slot_id,dtl.order_id ORDER BY stock_item_name";
		return getTableRowsAsJson(Sql1);
	}
	
	public JsonArray getVerifiedTotalItems1(String delevery_date , String custId, String deptId,String checkedItemArray) throws Exception{
		
		SystemSettings systemSettings =(SystemSettings) context.getCurrentHttpSession().getAttribute("systemSettings");
		String cust_query="";
		if(!custId.equals(""))
		{
			cust_query="AND hdr.customer_id = '"+custId+"'";

		}
		
		if(deptId!=null && !deptId.equals("")){
			cust_query+=" AND stkitem.prd_department_id = '"+deptId+"'";
		}
		if(checkedItemArray!=null && !checkedItemArray.equals("")){
		
			
			cust_query+=" AND stkitem.id IN ("+checkedItemArray+")";
		}
		
		// TODO Auto-generated method stub
		final String Sql1="SELECT  false AS check_val,dpt.`name` AS department,dpt.id as dept_id,"
				+ "dtl.sale_item_id AS sale_item_id,stkitem.`name` AS stock_item_name,"
				+ "hdr.closing_time_slot_id AS closing_time_slot_id,timeslt.`name` AS timeslot,round(SUM(dtl.qty), "+systemSettings.getDecimal_places()+") as qty "
				+ "FROM "
				+ "order_hdrs_booking hdr "
				+ "INNER JOIN "
				+ "order_dtls_booking dtl ON hdr.order_id = dtl.order_id "
				+ "INNER JOIN "
				+ "mrp_stock_item stkitem ON stkitem.id = dtl.sale_item_id "
				+ "INNER JOIN "
				+ "mrp_time_slot timeslt ON timeslt.id = hdr.closing_time_slot_id "
				+ "LEFT JOIN "
				+ "mrp_department dpt ON stkitem.prd_department_id=dpt.id "
				+ "WHERE "
				+ "hdr.closing_date = '"+delevery_date+"' "
				+ "AND dtl.`status` = 1 "
				+cust_query+" GROUP BY  stkitem.prd_department_id";
		return getTableRowsAsJson(Sql1);
	}

	public void updateStatusToProduction(int stock_item_id , int closing_time_slot_id , String close_date , String customerID ,
			String prod_date) throws Exception{
	
		// TODO Auto-generated method stub
		System.out.println("customerID======service============="+customerID);
		String cust_query="";
		if(!customerID.equals(""))
		{
			cust_query="and hdr.shop_code ='"+customerID+"'";
		}
		System.out.println("cust_query============="+cust_query);
		String sqlUpdate="UPDATE order_dtls_booking dtl "
				+ "INNER JOIN order_hdrs_booking hdr ON dtl.order_id = hdr.order_id "
				+ "SET dtl.`status` = 2, dtl.production_date = '"+prod_date+"' "
				+ "WHERE "
				+ "hdr.closing_time_slot_id = '"+closing_time_slot_id+"' "
				+ "AND dtl.`status` = 1 "
				+ "AND hdr.closing_date = '"+close_date+"' "
				+ "AND dtl.sale_item_id = '"+stock_item_id+"' "+cust_query+" ";
		
		System.out.println("sqlUpdate============="+sqlUpdate);
		executeSQL(sqlUpdate);
		
		
		
	}

	public JsonArray getShopOrderProcess(String closing_date , String customer_id , String stock_item_id ,
			String time_slot) throws Exception{
	
		// TODO Auto-generated method stub
		
	String cust_query="";
	if(!customer_id.equals(""))
	{
		cust_query=	"and hdr.cutomer_id='"+customer_id+"'";
	}
		final String Sql1="SELECT "
				+ "hdr.customer_id AS customer_id,cust. NAME AS customer_name,"
				+ "dtl.sale_item_id AS sale_item_id,SUM(dtl.qty) AS qty,hdr.closing_time_slot_id as timeslot "
				+ "FROM "
				+ "order_hdrs_booking hdr "
				+ "INNER JOIN "
				+ "order_dtls_booking dtl ON hdr.order_id = dtl.order_id "
				+ "LEFT JOIN "
				+ "customers cust ON cust.id = hdr.customer_id "
				+ "WHERE "
				+ "hdr.closing_date = '"+closing_date+"' "
				+ "AND hdr.closing_time_slot_id = '"+time_slot+"' "+cust_query+" "
				+ "AND dtl.sale_item_id ='"+stock_item_id+"' "
				+ "AND dtl.`status` = 1 GROUP BY customer_id";
		
		return getTableRowsAsJson(Sql1);
	}

	/*public JsonArray getProductionList(String production_date , String time_slot_id , String department_id,
			String stock_item_array) throws Exception
	{
		SystemSettings systemSettings =(SystemSettings) context.getCurrentHttpSession().getAttribute("systemSettings");
		Integer daily_prod_view=(Integer) context.getCurrentHttpSession().getAttribute("dailyprodview");
		
		String dept_query="";
		String stock_item_query = "";
		if( !(department_id==null) && !(department_id.equals("")))
		{
			dept_query=" and stkitm.prd_department_id='"+department_id+"'";
		}
		
		if( !(stock_item_array == null) && !(stock_item_array.equals("")))
		{
			stock_item_query =" AND ordrdtl.sale_item_id IN ("+ stock_item_array +")";
		}
		 String Sql1="";
		final String Sql1="SELECT tbl.*,(tbl.previous_balance-tbl.adjust_qty+tbl.prod_order_qty-tbl.prod_qty_from) AS order_qty,"
				+ "(tbl.previous_balance-tbl.adjust_qty+tbl.prod_order_qty-tbl.prod_qty_from) AS prod_qty "
				+ "FROM (SELECT 0 as check_valIn,dept.`name` AS department_name,dept.id AS department_id,ordrdtl.sale_item_id AS stock_item_id,"
				+ "stkitm.`code` AS stock_item_code,stkitm.`name` AS stock_item_name,0 AS shop_id,"
				+ "um.`code` AS uomcode,orhdr.closing_date AS delivery_date,SUM(ordrdtl.qty) AS order_qty1,0 AS flag,"
				+ "SUM(ordrdtl.qty)  AS prod_qty1,0 AS schedule_qty,0 AS damageqty,tmslt.name AS time_slot_name,"
				+ "tmslt.id AS time_slot_id,ROUND(IFNULL(bomcalc.material_cost, 0),2) itemMaterialCost,"
				+ "ROUND(IFNULL(costcalc.other_cost, 0),2) otherCost,0 AS saleRate,"
				+ "IFNULL((SELECT(SUM(order_qty) - SUM(issued_qty)) FROM order_booking_summary obs "
				+ "INNER JOIN customers cust ON cust.id=obs.shop_id WHERE obs.trans_date < ordrdtl.production_date "
				+ "AND obs.stock_item_id = ordrdtl.sale_item_id AND cust.shop_id is NULL "
				+ "GROUP BY	obs.stock_item_id),0) AS previous_balance,IFNULL((SELECT(SUM(issued_qty)) "
				+ "FROM order_booking_summary obs2 INNER JOIN customers cust ON cust.id=obs2.shop_id "
				+ "WHERE obs2.trans_date = ordrdtl.production_date AND obs2.stock_item_id = ordrdtl.sale_item_id"
				+ " AND cust.shop_id is NULL GROUP BY obs2.stock_item_id),0) AS adjust_qty,(SELECT ifnull(sum(ordtl.qty), 0) "
				+ "FROM order_dtls_booking ordtl WHERE ordtl.sale_item_id = ordrdtl.sale_item_id "
				+ "AND ordtl.production_date = ordrdtl.production_date) AS prod_order_qty,"
				+ "(SELECT ifnull(sum(mrp_prod_dtl.prod_qty),0) FROM mrp_prod_hdr "
				+ "JOIN mrp_prod_dtl ON mrp_prod_hdr.id = mrp_prod_dtl.prod_hdr_id WHERE"
				+ " mrp_prod_hdr.prod_date = ordrdtl.production_date AND "
				+ "mrp_prod_dtl.stock_item_id =ordrdtl.sale_item_id) AS prod_qty_from "
				+ "FROM "
				+ "	order_dtls_booking ordrdtl "
				+ "INNER JOIN order_hdrs_booking orhdr ON ordrdtl.order_id = orhdr.order_id "
				+ "INNER JOIN mrp_stock_item stkitm ON stkitm.id = ordrdtl.sale_item_id "
				+ "LEFT JOIN mrp_department dept ON dept.id = stkitm.prd_department_id "
				+ "LEFT JOIN uoms um ON um.id=stkitm.uom_id "
				+ "LEFT JOIN vw_bom_cost_calc bomcalc ON bomcalc.id = stkitm.id "
				+ "LEFT JOIN vw_prod_costcalc costcalc ON costcalc.id = stkitm.id "
				+ "LEFT JOIN mrp_time_slot tmslt ON tmslt.id=orhdr.closing_time_slot_id "
				+ "WHERE ordrdtl.production_date='"+production_date+"'"
				+ " AND ordrdtl.`status`='2' AND tmslt.id='"+time_slot_id+"' "+dept_query+" "
				+ "GROUP BY "
				+ "ordrdtl.sale_item_id,dept.id,tmslt.id)tbl";
		if( daily_prod_view == 1)
		{
		  Sql1="SELECT tbl.*,ROUND((tbl.previous_balance-tbl.adjust_qty+tbl.prod_order_qty-tbl.prod_qty_from+order_qty1),"+systemSettings.getDecimal_places()+") AS order_qty,"
				+ "ROUND((tbl.previous_balance-tbl.adjust_qty+tbl.prod_order_qty-tbl.prod_qty_from+order_qty1),"+systemSettings.getDecimal_places()+") AS prod_qty "
				+ "FROM (SELECT 0 as check_valIn,dept.`name` AS department_name,dept.id AS department_id,ordrdtl.sale_item_id AS stock_item_id,"
				+ "stkitm.`code` AS stock_item_code,stkitm.`name` AS stock_item_name,0 AS shop_id,"
				+ "um.`code` AS uomcode,orhdr.closing_date AS delivery_date,SUM(ordrdtl.qty) AS order_qty1,0 AS flag,"
				+ "SUM(ordrdtl.qty)  AS prod_qty1,0 AS schedule_qty,0 AS damageqty,tmslt.name AS time_slot_name,"
				+ "tmslt.id AS time_slot_id,ROUND(IFNULL(bomcalc.material_cost, 0),"+systemSettings.getDecimal_places()+") itemMaterialCost,"
				+ "ROUND(IFNULL(costcalc.other_cost, 0),"+systemSettings.getDecimal_places()+") otherCost,0 AS saleRate,"
				+ "IFNULL((SELECT(SUM(order_qty) - SUM(issued_qty)) FROM order_booking_summary obs "
				+ "INNER JOIN customers cust ON cust.id=obs.shop_id WHERE obs.trans_date < ordrdtl.production_date "
				+ "AND obs.stock_item_id = ordrdtl.sale_item_id "
				+ " AND cust.shop_id is NULL "
				+ "GROUP BY	obs.stock_item_id),0) AS previous_balance,IFNULL((SELECT(SUM(issued_qty)) "
				+ "FROM order_booking_summary obs2 INNER JOIN customers cust ON cust.id=obs2.shop_id "
				+ "WHERE obs2.trans_date = ordrdtl.production_date AND obs2.stock_item_id = ordrdtl.sale_item_id   AND obs2.trans_type = 3 "
				+ " AND cust.shop_id is NULL "
				+ " GROUP BY obs2.stock_item_id),0) AS adjust_qty,(SELECT ifnull(sum(ordtl.qty), 0) "
				+ "FROM order_dtls_booking ordtl WHERE ordtl.sale_item_id = ordrdtl.sale_item_id  AND ordtl.`status`=3 "
				+ "AND ordtl.production_date = ordrdtl.production_date) AS prod_order_qty,"
				+ "(SELECT ifnull(sum(mrp_prod_dtl.prod_qty),0) FROM mrp_prod_hdr "
				+ "JOIN mrp_prod_dtl ON mrp_prod_hdr.id = mrp_prod_dtl.prod_hdr_id WHERE"
				+ " mrp_prod_hdr.prod_date = ordrdtl.production_date AND "
				+ "mrp_prod_dtl.stock_item_id =ordrdtl.sale_item_id) AS prod_qty_from "
				+ "FROM "
				+ "	order_dtls_booking ordrdtl "
				+ "INNER JOIN order_hdrs_booking orhdr ON ordrdtl.order_id = orhdr.order_id "
				+ "INNER JOIN mrp_stock_item stkitm ON stkitm.id = ordrdtl.sale_item_id "
				+ "LEFT JOIN mrp_department dept ON dept.id = stkitm.prd_department_id "
				+ "LEFT JOIN uoms um ON um.id=stkitm.uom_id "
				+ "LEFT JOIN vw_bom_cost_calc bomcalc ON bomcalc.id = stkitm.id "
				+ "LEFT JOIN vw_prod_costcalc costcalc ON costcalc.id = stkitm.id "
				+ "LEFT JOIN mrp_time_slot tmslt ON tmslt.id=orhdr.closing_time_slot_id "
				+ "WHERE ordrdtl.production_date='"+production_date+"'"
				+ " AND ordrdtl.`status`='2' AND tmslt.id='"+time_slot_id+"' "+dept_query+" " + stock_item_query 
				+ "GROUP BY "
				+ "ordrdtl.sale_item_id,dept.id,tmslt.id)tbl";
		}
		else
		{
			
			
			 Sql1="SELECT tbl.*,ROUND((tbl.previous_balance-tbl.adjust_qty+tbl.prod_order_qty-tbl.prod_qty_from+order_qty1),"+systemSettings.getDecimal_places()+") AS order_qty,"
						+ "ROUND((tbl.previous_balance-tbl.adjust_qty+tbl.prod_order_qty-tbl.prod_qty_from+order_qty1),"+systemSettings.getDecimal_places()+") AS prod_qty "
						+ "FROM (SELECT 0 as check_valIn,dept.`name` AS department_name,dept.id AS department_id,ordrdtl.sale_item_id AS stock_item_id,"
						+ "stkitm.`code` AS stock_item_code,stkitm.`name` AS stock_item_name,0 AS shop_id,"
						+ "um.`code` AS uomcode,orhdr.closing_date AS delivery_date,SUM(ordrdtl.qty) AS order_qty1,0 AS flag,"
						+ "SUM(ordrdtl.qty)  AS prod_qty1,0 AS schedule_qty,0 AS damageqty,tmslt.name AS time_slot_name,"
						+ "tmslt.id AS time_slot_id,ROUND(IFNULL(bomcalc.material_cost, 0),"+systemSettings.getDecimal_places()+") itemMaterialCost,"
						+ "ROUND(IFNULL(costcalc.other_cost, 0),"+systemSettings.getDecimal_places()+") otherCost,0 AS saleRate,"
						+ "IFNULL((SELECT(SUM(order_qty) - SUM(issued_qty)) FROM order_booking_summary obs "
						+ "INNER JOIN customers cust ON cust.id=obs.shop_id WHERE obs.trans_date < ordrdtl.production_date "
						+ "AND obs.stock_item_id = ordrdtl.sale_item_id "
						+ " AND cust.shop_id is NULL "
						+ "GROUP BY	obs.stock_item_id),0) AS previous_balance,IFNULL((SELECT(SUM(issued_qty)) "
						+ "FROM order_booking_summary obs2 INNER JOIN customers cust ON cust.id=obs2.shop_id "
						+ "WHERE obs2.trans_date = ordrdtl.production_date AND obs2.stock_item_id = ordrdtl.sale_item_id   AND obs2.trans_type = 3 "
						+ " AND cust.shop_id is NULL "
						+ " GROUP BY obs2.stock_item_id),0) AS adjust_qty,(SELECT ifnull(sum(ordtl.qty), 0) "
						+ "FROM order_dtls_booking ordtl WHERE ordtl.sale_item_id = ordrdtl.sale_item_id  AND ordtl.`status`=3 "
						+ "AND ordtl.production_date = ordrdtl.production_date) AS prod_order_qty,"
						+ "(SELECT ifnull(sum(mrp_prod_dtl.prod_qty),0) FROM mrp_prod_hdr "
						+ "JOIN mrp_prod_dtl ON mrp_prod_hdr.id = mrp_prod_dtl.prod_hdr_id WHERE"
						+ " mrp_prod_hdr.prod_date = ordrdtl.production_date AND "
						+ "mrp_prod_dtl.stock_item_id =ordrdtl.sale_item_id) AS prod_qty_from "
						+ "FROM "
						+ "	order_dtls_booking ordrdtl "
						+ "INNER JOIN order_hdrs_booking orhdr ON ordrdtl.order_id = orhdr.order_id "
						+ "INNER JOIN mrp_stock_item stkitm ON stkitm.id = ordrdtl.sale_item_id "
						+ "LEFT JOIN mrp_department dept ON dept.id = stkitm.prd_department_id "
						+ "LEFT JOIN uoms um ON um.id=stkitm.uom_id "
						+ "LEFT JOIN vw_bom_cost_calc bomcalc ON bomcalc.id = stkitm.id "
						+ "LEFT JOIN vw_prod_costcalc costcalc ON costcalc.id = stkitm.id "
						+ "LEFT JOIN mrp_time_slot tmslt ON tmslt.id=orhdr.closing_time_slot_id "
						+ "WHERE ordrdtl.production_date='"+production_date+"'"
						+ " AND ordrdtl.`status`='2' AND tmslt.id='"+time_slot_id+"' "+dept_query+" " + stock_item_query
						+ "GROUP BY "
						+ "ordrdtl.sale_item_id,dept.id,tmslt.id)tbl";
	
			
			
			
		}
		return getTableRowsAsJson(Sql1);
	}*/
	
	public JsonArray getProductionList(String stock_item_id, String time_slot_id , String department_id, String production_date) throws Exception
	{
		SystemSettings systemSettings =(SystemSettings) context.getCurrentHttpSession().getAttribute("systemSettings");
		Integer daily_prod_view=(Integer) context.getCurrentHttpSession().getAttribute("dailyprodview");
		
		String query = "";
		if( daily_prod_view == 1)
		{
			query = "SELECT stock_items.id, "
					+ "stock_items.`name`, "
					+ "ROUND(IFNULL(bom_cost.material_cost, 0),"+ systemSettings.getDecimal_places() +") AS item_material_cost,"
					+ "ROUND(IFNULL(prod_cost.other_cost, 0),"+ systemSettings.getDecimal_places() +" ) AS other_cost, "
					+ "ROUND(IFNULL(itm_stock.current_stock, 0 )," + systemSettings.getDecimal_places() + ") AS current_stock,"
					+ " (SELECT ifnull(sum(ordtl.qty), 0) "
					+ "FROM "
					+ "order_dtls_booking ordtl INNER JOIN order_hdrs_booking orderHdr ON orderHdr.order_id = ordtl.order_id "
					+ "WHERE ordtl.sale_item_id = stock_items.id "
					+ "AND ordtl.`status` = 2 AND ordtl.production_date = '"+ production_date +"' AND orderHdr.closing_time_slot_id = '"
					+ time_slot_id +"') AS order_qty "
					+ "FROM "
					+ "mrp_stock_item AS stock_items  "
					+ "LEFT JOIN vw_bom_cost_calc AS bom_cost ON bom_cost.id = stock_items.id "
					+ "LEFT JOIN vw_prod_costcalc AS prod_cost ON prod_cost.id = stock_items.id "
					+ "LEFT JOIN (select stock_item_id, current_stock FROM vw_itemstock WHERE department_id = '"+ department_id +"' AND stock_item_id='"+ stock_item_id +"') AS itm_stock  ON itm_stock.stock_item_id = stock_items.id WHERE stock_items.id = '"+ stock_item_id +"'";
		}
		else
		{
			query = "SELECT stock_items.id, "
					+ "stock_items.`name`, "
					+ "ROUND(IFNULL(bom_cost.material_cost, 0),"+ systemSettings.getDecimal_places() +") AS item_material_cost,"
					+ "ROUND(IFNULL(prod_cost.other_cost, 0),"+ systemSettings.getDecimal_places() +" ) AS other_cost, "
					+ "ROUND(IFNULL(item_stock.current_stock, 0 )," + systemSettings.getDecimal_places() + ") AS current_stock "
					+ "FROM mrp_stock_item AS stock_items  "
					+ "LEFT JOIN vw_bom_cost_calc AS bom_cost ON bom_cost.id = stock_items.id "
					+ "LEFT JOIN vw_prod_costcalc AS prod_cost ON prod_cost.id = stock_items.id "
					+ "LEFT JOIN vw_itemstock AS item_stock ON item_stock.stock_item_id = stock_items.id WHERE stock_items.id = '"+ stock_item_id +"'";
		}
		return getTableRowsAsJson(query);
	}

	public void updateOrderDtl(ArrayList<ProductionDetail> productionItemsList, Integer timeslot_id, String prod_date) throws Exception{
		
	
		// TODO Auto-generated method stub
		
		
		
		String sqlUpdate="UPDATE order_dtls_booking dtl "
				+ "INNER JOIN order_hdrs_booking hdr ON dtl.order_id = hdr.order_id "
				+ "SET dtl.`status` = ? ,dtl.mrp_prod_dtl_id=? "
				+ "WHERE "
				+ "hdr.closing_time_slot_id = ? "
				+ "AND dtl.`status` = ? "
				+ "AND dtl.production_date = ?  "
				+ "AND dtl.sale_item_id = ? ";
		
		PreparedStatement st=dbHelper.buildPreparedStatement(sqlUpdate);
		for (int i = 0; i < productionItemsList.size(); i++) {
			st.setInt(1, 3);
			st.setInt(2, productionItemsList.get(i).getId());
			st.setInt(3, timeslot_id);
			st.setInt(4,2);
			st.setString(5,prod_date);
			st.setInt(6, productionItemsList.get(i).getStockItemId());
			
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
		//st.executeBatch();
		
	//	executeSQL(sqlUpdate);
		
	}

	public JsonArray getOrderDetailsProd(String production_date , String time_slot_id , String stock_item_id) throws Exception{
	
		// TODO Auto-generated method stub
		SystemSettings systemSettings =(SystemSettings) context.getCurrentHttpSession().getAttribute("systemSettings");

		final String Sql1="SELECT "
				+ "hdr.customer_id as cust_id,ROUND(SUM(dtl.qty),"+systemSettings.getDecimal_places()+") AS orderqty,cust.`name` AS customer_name "
				+ "FROM order_dtls_booking dtl "
				+ "INNER JOIN order_hdrs_booking hdr ON dtl.order_id = hdr.order_id "
				+ "INNER JOIN customers cust ON cust.id=hdr.customer_id "
				+ "WHERE dtl.production_date = '"+production_date+"' AND dtl.`status` = '2' "
				+ "AND hdr.closing_time_slot_id = '"+time_slot_id+"' AND dtl.sale_item_id='"+stock_item_id+"' GROUP BY hdr.customer_id";
		return getTableRowsAsJson(Sql1);
	}

	public JsonArray getOrderDetailsDailyprd(String dtl_id) throws Exception{
	
		// TODO Auto-generated method stub
		SystemSettings systemSettings =(SystemSettings) context.getCurrentHttpSession().getAttribute("systemSettings");

		final String Sql1="SELECT "
				+ "hdr.customer_id as customer_id,cst.`name` AS customer_name,ROUND(SUM(dtl.qty),"+systemSettings.getDecimal_places()+") AS orderqty "
				+ "FROM order_dtls_booking dtl INNER JOIN order_hdrs_booking hdr ON dtl.order_id=hdr.order_id "
				+ "INNER JOIN customers cst ON cst.id=hdr.customer_id "
				+ "WHERE dtl.mrp_prod_dtl_id = '"+dtl_id+"' GROUP BY cst.id";
		return getTableRowsAsJson(Sql1);
	}

	

	public JsonArray getReportData(String production_date , String dept_id , String time_slot_id) throws Exception {
		
		Integer department_id=-1;
		Integer Time_slot=Integer.parseInt(time_slot_id);
		if( !(dept_id==null) && !(dept_id.equals("")))
		{
			department_id=Integer.parseInt(dept_id);
		}
	
		final String sql="CALL usp_rawmaterial_req_report('"+production_date+"',"+Time_slot +", "+department_id+" )";

	
	return getTableRowsAsJson(sql);
	}
	
public JsonArray getmaterialReportData(String req_no) throws Exception {
		
	
		final String sql="CALL usp_material_rqstn_report('"+req_no+"')";

	
	return getTableRowsAsJson(sql);
	}

	public String getDateCheckExistPrd(String production_date) throws Exception {
	
		// TODO Auto-generated method stub
		String count="0";
		CachedRowSet resultSet;
		final String sql="SELECT COUNT(id) AS count FROM mrp_prod_hdr WHERE prod_date>'"+production_date+"'";
		resultSet = dbHelper.executeSQLForRowset(sql);
		if (resultSet.next()) {
			count=resultSet.getString("count");
		}
		return count;
	}

	public Integer saveIntoSalesStockin(Production dailyProd) throws Exception{
	
		// TODO Auto-generated method stub
		Integer stock_in_hdr_id=counterDao.getCounterFor("stockin", "stockin");
		
		Users user =(Users) context.getCurrentHttpSession().getAttribute("user");
		final String sql="insert INTO stock_in_hdr(id,date,type,created_by,created_at,is_deleted) "
				+ "values("+stock_in_hdr_id+",'"+dailyProd.getSchedDate()+"',1,"+user.getId()+",'"+dailyProd.getProd_time()+"',0)";
		
		beginTrans();
		try{
		executeSQL(sql);
		endTrans();
		}
		catch (Exception e){

			rollbackTrans();
			throw e;
		}
		
		
		return stock_in_hdr_id;
	}

	public void saveSaleStockInDetail(ArrayList<ProductionDetail> productionItemsList , Integer sale_stock_in_hdr_id) throws Exception{
	
		// TODO Auto-generated method stub
		
		
		String sql="Insert INTO stock_in_dtl(stock_in_hdr_id,stock_item_id,qty_received) values(?,?,?) ";
				
		
		PreparedStatement st=dbHelper.buildPreparedStatement(sql);
		for (int i = 0; i < productionItemsList.size(); i++) {
		

		
			st.setInt(1,sale_stock_in_hdr_id );
			st.setInt(2, productionItemsList.get(i).getStockItemId());
			st.setFloat(3,productionItemsList.get(i).getProdQty().floatValue());
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

	public JsonArray getPendingMaterial(String department) throws Exception {
		
		
		final String sql="CALL usp_material_rqstn_pending('"+department+"')";

	
	return getTableRowsAsJson(sql);
	}

	public JsonArray getDeptDetails() throws Exception {
		
		// TODO Auto-generated method stub
		final String Sql1="SELECT prddtl.prod_hdr_id, prddtl.department_id, dep.`name` FROM mrp_prod_dtl prddtl INNER JOIN mrp_department dep ON dep.id = prddtl.department_id";
		return getTableRowsAsJson(Sql1);

	}

	public JsonArray getVerifiedTotalItemsWithDepartment(String delevery_date, String custId, String deptId,
			String checkedItemArray, String string) throws Exception {
		// TODO Auto-generated method stub
		String andPart = "";
		if(!custId.equals("0")) {
			if(custId.equalsIgnoreCase("CNTRL1")){
				andPart = "ordhdr.shop_code = '"+custId+"' AND ";
			}
			else{
				andPart = "ordhdr.customer_id = '"+custId+"' AND ";
			}
			
		}
		final String sql="SELECT FALSE AS check_val,ordhdr.order_id,ordhdr.order_no,ordtl.sub_class_name,ordhdr.shop_code,ordtl.qty,ordhdr.closing_time_slot_id AS closing_time_slot_id,timeslt.`name` AS timeslot,dpt.`name` AS department,dpt.id AS dept_id,stkitem.`name` AS stock_item_name,ordtl.sale_item_id AS sale_item_id,"
				+ " ordtl.remarks,ordtl.id AS order_dtl_id FROM order_hdrs_booking ordhdr " + 
				"LEFT JOIN order_dtls_booking ordtl ON ordhdr.order_id = ordtl.order_id "
				+ "INNER JOIN mrp_stock_item stkitem ON stkitem.id = ordtl.sale_item_id   "
				+ "LEFT JOIN mrp_department dpt ON stkitem.prd_department_id = dpt.id"
				+ " INNER JOIN mrp_time_slot timeslt ON timeslt.id = ordhdr.closing_time_slot_id WHERE "+andPart+" ordhdr.closing_date = '"+delevery_date+"' AND ordtl.`status` = 1";
		return getTableRowsAsJson(sql);
	}

	public JsonArray getBomListOfCurrentProduct(ArrayList<String> stockItemIdList) throws Exception {
		String wherePart=null;
		for(int i=0;i<stockItemIdList.size();i++) {
			if(wherePart==null||wherePart=="") {
				wherePart= stockItemIdList.get(i);
			}
			else {
				wherePart+= ","+stockItemIdList.get(i);
			}
			
		}
		wherePart+=")";
		
		final String Sql="SELECT mrp_stock_item.name AS Product_name,vw_itemstock.name AS bom_name, stock_item_bom.stock_item_id, \n" + 
				"stock_item_bom.bom_item_id,stock_item_bom.bom_item_qty,stock_item_bom.stock_item_qty\n" + 
				"FROM stock_item_bom \n" + 
				"INNER JOIN mrp_stock_item ON stock_item_bom.stock_item_id=mrp_stock_item.id\n" + 
				"INNER JOIN vw_itemstock ON stock_item_bom.bom_item_id=vw_itemstock.stock_item_id \n" + 
				"WHERE stock_item_bom.stock_item_id IN("+wherePart+" AND stock_item_bom.is_deleted=0 \n" + 
				"ORDER BY stock_item_bom.stock_item_id DESC";
		return getTableRowsAsJson(Sql);
	}

	public JsonArray getCurrentBomStock(ArrayList<String> bomIdList, String department_id) throws Exception {
		
		String wherePart=null;
		for(int i=0;i<bomIdList.size();i++) {
			if(wherePart==null||wherePart=="") {
				wherePart= bomIdList.get(i);
			}
			else {
				wherePart+= ","+bomIdList.get(i);
			}
			
		}
		wherePart+=")";
		
		String sql = "SELECT vw.stock_item_id,vw.code,vw.name,vw.current_stock,dept.name as department_name,dept.id AS department_id\n" + 
				"FROM vw_itemstock vw INNER JOIN mrp_department dept ON vw.department_id = dept.id \n" + 
				"WHERE vw.stock_item_id IN("+wherePart+ "AND dept.id="+department_id;

		return  getTableRowsAsJson(sql);
	}

	/*@Override
	public void updateIssuedQty(String req_date, String issuedQty,String stock_item_id) throws Exception {
		
		
	}*/
	
	@Override
	public LinkedHashMap<Integer,Double> updateIssuedQty(String req_date, String issuedQty,int stockitemid) throws Exception{
		System.out.println("issuedQty is ==========================================================================>"+issuedQty);
		LinkedHashMap<Integer, Double> item
	     = new LinkedHashMap<Integer, Double>();
		CachedRowSet resultSet;
		//final String sql = "SELECT id,order_qty FROM order_booking_summary WHERE order_date='"+req_date+"' and stock_item_id="+stockitemid+" ";
		final String sql = "SELECT id,order_qty FROM order_booking_summary WHERE trans_date='"+req_date+"' and stock_item_id="+stockitemid+" ";
			System.out.println("sql==============+++++++++++"+sql);
	resultSet = dbHelper.executeSQLForRowset(sql);
	while(resultSet.next()) {
		System.out.println("id=============="+resultSet.getInt("id")+"order_qty==============="+resultSet.getDouble("order_qty"));
		item.put(resultSet.getInt("id"), resultSet.getDouble("order_qty"));
	}
	System.out.println("item is ==========================================================================>"+item);
	
	return item;
	}
	@Override
	public Double getcurrentIssuedqty(Integer key) throws Exception {
		CachedRowSet resultSet;
		Double IssuedQty=0.00;
		final String sql = "SELECT issued_qty FROM order_booking_summary WHERE id="+key+"";
		resultSet = dbHelper.executeSQLForRowset(sql);
		if (resultSet.next()) {
			IssuedQty=resultSet.getDouble("issued_qty");
			
		}
		return IssuedQty;
	}

	@Override
	public void updateissuedValue(Integer key, Double prodQty) throws Exception {
		final String sql = "UPDATE order_booking_summary SET issued_qty="+prodQty+" WHERE id="+key+" ";
		dbHelper.executeSQL(sql);
			
	}
	
}
