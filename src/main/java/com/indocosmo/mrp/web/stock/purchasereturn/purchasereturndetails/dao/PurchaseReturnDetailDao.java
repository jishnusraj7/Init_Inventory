package com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.model.PurchaseReturnDetail;

public class PurchaseReturnDetailDao extends GeneralDao<PurchaseReturnDetail> implements IPurchaseReturnDetailDao{

	public PurchaseReturnDetailDao(ApplicationContext context) {
		super(context);
	}

	@Override
	public PurchaseReturnDetail getNewModelInstance() {
		
		return new PurchaseReturnDetail();
	}

	@Override
	protected String getTable() {
		
		return "mrp_stock_return_dtl";
	}

	public JsonArray getPurchaseReturnDetailData(int stock_return_hdr_id) throws Exception{
		
		/*final String returnDtlQuery = "SELECT *, 0 AS flag FROM"
										+ " " + getTable()
										+ " WHERE stock_return_hdr_id = '"
										+ stock_return_hdr_id +"'";*/
		final String returnDtlQuery=" SELECT " + 
				"	retn.*, 0 AS flag , IFNULL( " + 
				"   ( " + 
				"	SELECT " + 
				"	IFNULL( " + 
				"	SUM(dtl.in_qty) - SUM(dtl.out_qty), " + 
				"	0 " + 
				"	) AS current_stock " + 
				"	FROM " + 
				"	mrp_stock_register_hdr hdr " + 
				"	INNER JOIN mrp_stock_register_dtl dtl ON hdr.id = dtl.stock_register_hdr_id " + 
				"	WHERE " + 
				"	dtl.stock_item_id = retn.stock_item_id AND " + 
				"   dtl.department_id=2 " + 
				"	GROUP BY " + 
				"	dtl.stock_item_id " + 
				"		), " + 
				"		0 " + 
				"	) AS current_stock " + 
				" FROM " + 
				" mrp_stock_return_dtl retn " + 
				" INNER JOIN mrp_stock_return_hdr retnhd ON retnhd.id = retn.stock_return_hdr_id " + 
				" LEFT JOIN mrp_stock_item stkitm ON retn.stock_item_id = stkitm.id "+
				"  WHERE stock_return_hdr_id = '"+ stock_return_hdr_id +"'";
		return getTableRowsAsJson(returnDtlQuery);
	}
	
	@Override
	public void save(List<PurchaseReturnDetail> itemList) throws Exception {
		
		String stockReturnDtlIdList = "";
		String stockReturnDtlItemIdList = "";
		String wherePart = "";
		final Integer stockReturnHdrId = itemList.get(0).getStock_return_hdr_id();
		for(PurchaseReturnDetail purchaseReturnDetail : itemList)
		{
			if (purchaseReturnDetail.getStock_return_hdr_id() != null) {
				if(purchaseReturnDetail.getId() != null)
					stockReturnDtlIdList += ((stockReturnDtlIdList.isEmpty()) ? "" : ",")
							+ purchaseReturnDetail.getId();

				if(purchaseReturnDetail.getStock_item_id() != null)
					stockReturnDtlItemIdList += ((stockReturnDtlItemIdList.isEmpty()) ? "" : ",")
							+ purchaseReturnDetail.getStock_item_id();				
			}
		}
		if (stockReturnDtlIdList.length() > 0) {
			if(!stockReturnDtlIdList.equals("null"))
				wherePart = " AND id not in (" + stockReturnDtlIdList + ")";
			else
				wherePart = "";
		}

		final String markAsDeletedSQl = "DELETE "
				+ "FROM "
				+ "" + getTable()+ "  "
				+ "WHERE "
				+ "stock_return_hdr_id=" + stockReturnHdrId	+ "" + wherePart + ";";

		beginTrans();
		try {
			executeSQL(markAsDeletedSQl);
			for(PurchaseReturnDetail purchaseReturnDetail : itemList){
				if (purchaseReturnDetail.getStock_return_hdr_id() != null && purchaseReturnDetail.getId() != null) 
					super.update(purchaseReturnDetail);
				else
					super.insert(purchaseReturnDetail);				
			}
			endTrans();
		} catch (Exception e) {
			rollbackTrans();
			throw e;
		}
	}
	
	@Override
	public Integer delete(String stock_return_hdr_id) throws Exception {
		final String sql = "DELETE "
				+ "FROM "
				+ ""+getTable() + "  "
				+ "WHERE stock_return_hdr_id = '"
				+ stock_return_hdr_id +"'";
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
}
