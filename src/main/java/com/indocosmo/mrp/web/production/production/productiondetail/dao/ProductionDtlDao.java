package com.indocosmo.mrp.web.production.production.productiondetail.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.production.production.productiondetail.model.ProductionDetail;




public class ProductionDtlDao extends GeneralDao<ProductionDetail> implements IProductionDtlDao{

	public ProductionDtlDao(ApplicationContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	/**
	 * @return
	 */
	@Override
	public ProductionDetail getNewModelInstance() {

		return new ProductionDetail();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "mrp_prod_dtl";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(java.util.List)
	 */
	@Override
	public void save(List<ProductionDetail> itemList) throws Exception {



		final Integer  prod_hdr_id  = itemList.get(0).getProdHdrId();

		final String deleteDtl = "DELETE FROM " + getTable()+ "  WHERE prod_hdr_id = "+prod_hdr_id+";";


		beginTrans();
		try {

			executeSQL(deleteDtl);
			for (ProductionDetail prodItem : itemList) {

				super.insert(prodItem);

			}


			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}


		/*
		String stockInDtlIdList = "";
		String stockItemIdList = "";
		String wherePartForStkDtlIds = "";
		String wherePartForStkItmIds = "";

		final Integer stockInId = itemList.get(0).getStockInHdrId();

		for (ProductionDetail stockInItem : itemList) {

			if (stockInItem.getStockInHdrId() != null) {
				if(stockInItem.getId() != null){
					stockInDtlIdList += ((stockInDtlIdList.isEmpty()) ? "" : ",")+ stockInItem.getId();
				}
			}
			if (stockInItem.getStockInHdrId() != null) {
				if(stockInItem.getStockItemId() != null){
					stockItemIdList += ((stockItemIdList.isEmpty()) ? "" : ",")	+ stockInItem.getStockItemId();
				}
			}
		}

		if (stockInDtlIdList.length() > 0) {
			if(!stockInDtlIdList.equals(null)){
				wherePartForStkDtlIds = " AND id not in (" + stockInDtlIdList + ")";
			}else{
				wherePartForStkDtlIds = "";
			}

		}
		if (stockItemIdList.length() > 0) {
			if(!stockItemIdList.equals(null)){
				wherePartForStkItmIds = "  AND stock_item_id NOT IN ("+stockItemIdList+")";
			}else{
				wherePartForStkItmIds = "";
			}
		}

		final String DeleteSQl = "DELETE FROM " + getTable()+ "  WHERE stock_in_hdr_id=" + stockInId	+ "" + wherePartForStkDtlIds + ";";

		beginTrans();
		try {

			executeSQL(DeleteSQl);

			for (ProductionDetail stockInItem : itemList) {
				if(stockInItem.getPoId() != null && stockInItem.getStockInHdrId() != null){
					final String DeletePODtlSQL = "DELETE FROM " + getTable()+ "  WHERE stock_in_hdr_id=" + stockInId	+ " AND po_id="+stockInItem.getPoId()+""+ wherePartForStkItmIds+"";
					executeSQL(DeletePODtlSQL);
					if(stockInItem.getPoQty() <= stockInItem.getReceivedQty()){
						final String UpdatePoOdrStatusSQL = "UPDATE po_dtl SET po_dtl_status=1 WHERE po_hdr_id="+stockInItem.getPoId()+" AND stock_item_id="+stockInItem.getStockItemId()+"";
						executeSQL(UpdatePoOdrStatusSQL);
					}
				}
				if (stockInItem.getStockInHdrId() != null && stockInItem.getId() != null) {
					super.update(stockInItem);
				}else{
					super.insert(stockInItem);
				}
			}

			for (ProductionDetail stockInItem : itemList) {
				if(stockInItem.getDeletedPOItems() !=null){
					if(stockInItem.getDeletedPOItems().length() > 0){
						final String updateFineshedStatus="UPDATE po_dtl SET po_dtl_status=0 WHERE id in("+stockInItem.getDeletedPOItems()+")";
						executeSQL(updateFineshedStatus);
					}
					break;
				}
			}

			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}
		 */}


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
	@Override
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

	public ArrayList<ProductionDetail> getstockInDtlData(Integer stockinHdrId) throws Exception {

		final String SQL="SELECT id,stock_item_name,received_qty,unit_price,amount,tax_amount FROM mrp_stock_in_dtl stock_in_dtl WHERE stock_in_hdr_id="+stockinHdrId;
		ArrayList<ProductionDetail> list=null;
		ProductionDetail item=null;

		CachedRowSet rs= getRowSet(SQL);

		if(rs!=null){

			list=new ArrayList<ProductionDetail>();

			while(rs.next()){
				item=getNewModelInstance();
				item.setId(rs.getInt("id"));
				/*item.setStockItemName(rs.getString("stock_item_name"));
			item.setReceivedQty(rs.getDouble("received_qty"));
			item.setUnitPrice(rs.getDouble("unit_price"));
			item.setAmount(rs.getDouble("amount"));
			item.setTaxAmount(rs.getDouble("tax_amount"));*/
				list.add(item);
			}
		}

		return list;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.production.dailyproduction.dailyproductiondetail.dao.IProductionDtlDao#getTableRowsAsJson(java.lang.Integer)
	 */
	@Override
	public JsonArray getTableRowsAsJson(Integer id) throws Exception {
		final String sql="select * from "+getTable()+" where prod_hdr_id= "+id+"";
		return getTableRowsAsJson(sql);
	}
}
