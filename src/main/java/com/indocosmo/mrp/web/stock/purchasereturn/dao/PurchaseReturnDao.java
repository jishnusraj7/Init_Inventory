package com.indocosmo.mrp.web.stock.purchasereturn.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.sales.stockin.stockindetail.model.StockInDetail;
import com.indocosmo.mrp.web.stock.purchasereturn.model.PurchaseReturn;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.model.PurchaseReturnDetail;

public class PurchaseReturnDao extends GeneralDao<PurchaseReturn> implements IPurchaseReturnDao{

	public PurchaseReturnDao(ApplicationContext context) {
		super(context);
	}

	@Override
	public PurchaseReturn getNewModelInstance() {
		
		return new PurchaseReturn();
	}

	@Override
	protected String getTable() {
		
		return "mrp_stock_return_hdr";
	}

	@Override
	public String getAdditionalFilter(List<DataTableColumns> columnList) {
		String adnlFilterPart="";
		
		for(DataTableColumns col:columnList){

			if(col.getSearchValue()!="" &&col.getSearchValue()!=null){

				if(col.getData().equals("return_number") && col.getSearchValue()!="" && col.getSearchValue()!=null)
					adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " AND ")	+ ""+getTable()+"."
							+col.getData()+" LIKE '%"+(col.getSearchValue()).trim()+"%'";	
				else
					adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " AND ")	+ ""+getTable()+"."
							+col.getData()+" = ('"+(col.getSearchValue()).trim()+"')";
			}
		}
		return adnlFilterPart;
	}
	
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart, String searchCriteria, String adnlFilterPart,
			List<DataTableColumns> columnList) throws Exception {

		if (wherePart == null || wherePart == "") 
			wherePart = "  ";
		 else 
			wherePart += ")  ";
	
		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart == null || wherePart == "  ") {

				wherePart += " WHERE  " + adnlFilterPart;

			} else {

				wherePart += " AND " + adnlFilterPart;

			}
		}

		String sqlCount = "SELECT COUNT(id) AS row_count FROM " + getTable() + " " + wherePart + "";
		CachedRowSet totalRecs = executeSQLForRowset(sqlCount);
		return totalRecs;
	}
	
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns, String wherePart, String searchCriteria, String sortPart,
			int limitRows, int offset, String adnlFilterPart, List<DataTableColumns> columnList) throws Exception {		

		if (wherePart == null || wherePart == "") 
			wherePart = "  ";
		 else 
			wherePart += ")  ";
		if (adnlFilterPart != "" && adnlFilterPart != null) {

			if (wherePart == null || wherePart == "  ") 
				wherePart += " WHERE  " + adnlFilterPart;
			else 
				wherePart += " AND " + adnlFilterPart;		
		}

		String sql = "SELECT id, return_no, supplier_id, supplier_code, supplier_name,"
				+ " return_date, return_status,"
				+ "remarks "
				+ "FROM "
				+ getTable() + "  "
				+ wherePart + " " + sortPart + " "
				+ "LIMIT " + limitRows + " " + "OFFSET " + offset + "";

		return getTableRowsAsJson(sql);
	}
	
	
	@Override
	public Integer delete(String id) throws Exception {
		final String sql = "DELETE "
				+ "FROM "
				+ ""+getTable() + "  "
				+ "WHERE id = '" + id +"'";
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

	
	public List<PurchaseReturnDetail> getStockReturnData(List<String> stockList, String supplier) throws Exception {
		String selSql=null;
		PurchaseReturnDetail purchaseDetail=null;
		List<PurchaseReturnDetail> stockInList=new ArrayList<PurchaseReturnDetail>();
		for(String purchaseReturn:stockList) {
			purchaseDetail=new PurchaseReturnDetail();
			CachedRowSet resultSet=null;
			selSql="SELECT * from mrp_stock_in_hdr hdr INNER JOIN "
					+ "mrp_stock_in_dtl dtl ON hdr.id=dtl.stock_in_hdr_id WHERE hdr.supplier_id='"+supplier+"'AND dtl.stock_item_code='"+purchaseReturn+"'";
			resultSet= getRowSet(selSql);
			if(!resultSet.isBeforeFirst()) {
				//while(resultSet.next()) {
				purchaseDetail.setStock_item_code(purchaseReturn);
				stockInList.add(purchaseDetail);
				//}
			}
		}
		
		return stockInList;
	}

	
}
