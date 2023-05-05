package com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.dao;


import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.remotrequestType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.model.PO_dtl;


public class PurchaseOrderdtlDao extends GeneralDao<PO_dtl> implements IPurchaseOrderdtlDao{


	public PurchaseOrderdtlDao(ApplicationContext context) {
		
		super(context);
		// TODO Auto-generated constructor stub
	}



	@Override
	public PO_dtl getNewModelInstance() {
		
		// TODO Auto-generated method stub
		return new PO_dtl();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {
		
		// TODO Auto-generated method stub
		return "mrp_po_dtl";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getList(java.lang.String)
	 */
	@Override
	public List<PO_dtl> getList( String where ) throws Exception {
		
		String SQL="SELECT * FROM "+getTable()+" WHERE po_hdr_id="+ where +" ORDER BY (stock_item_name)";

		return buildItemList(SQL);

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderdtl.dao.IPurchaseOrderdtlDao#getPrDtlData(java.lang.String)
	 */
	@Override
	public JsonArray getPrDtlData(String where) throws Exception {

		String SQL="SELECT "
				+ "prdtl.*,uom.code as uomcode "
				+ "FROM "
				+ ""+ getTable() + " prdtl "
				+ "LEFT JOIN uoms uom on uom.id="
				+  "(SELECT uom_id "
				+  "FROM mrp_stock_item "
				+  "WHERE "
				+  "mrp_stock_item.id=prdtl.stock_item_id)  "
				+ "WHERE "
				+ "po_hdr_id= " + where ;

		return getTableRowsAsJson(SQL);
	}





	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(java.util.List)
	 */
	@Override
	public void save(List<PO_dtl> itemList) throws Exception {
		String stockODtlIdList = "";
		String	remote_request_dtl_id= "";
		String	remote_request_hdr_id= "";

		String remote_request_dtlid="";

		String wherePart = "";

		final Integer stockOutId = itemList.get(0).getPo_hdr_id();

		for (PO_dtl stockOutItem : itemList) {

			if (stockOutItem.getId() != null) {
				stockODtlIdList += ((stockODtlIdList.isEmpty()) ? "" : ",")
						+ stockOutItem.getId();

			}


			if(stockOutItem.getRequest_dtl_id()!=null){
				remote_request_dtl_id += ((remote_request_dtl_id.isEmpty()) ? "" : ",")
						+ stockOutItem.getRequest_dtl_id();

			}
			if(stockOutItem.getRemote_request_hdr_id()!=null){
				remote_request_hdr_id += ((remote_request_hdr_id.isEmpty()) ? "" : ",")
						+ stockOutItem.getRemote_request_hdr_id();

			}

			if(remote_request_hdr_id==""){
				remote_request_hdr_id="0";

			}

			if(remote_request_dtl_id==""){
				remote_request_dtl_id="0";

			}

		}
		if (stockODtlIdList.length() > 0) {
			
			if(!stockODtlIdList.equals("null")){
				
				wherePart = " AND id NOT IN (" + stockODtlIdList + ")";
			}
			else{
				wherePart = "";
			}

		}

		 final String updateHdrId="UPDATE "
									+ "mrp_hq_remote_request_hdr SET request_status="+remotrequestType.PROCESSING.getRemotrequestTypeId()+" "
									+ "WHERE "
									+ "id in(" +remote_request_hdr_id+")";
		
		 final String updateHdrStatus="UPDATE "
									  + "mrp_hq_remote_request_hdr SET request_status="+remotrequestType.FINISHED.getRemotrequestTypeId()+" "
									  + "WHERE "
									  + "id IN(" +remote_request_hdr_id+")";
		
		 final String updateReqDtl="UPDATE "
					                  + "mrp_hq_remote_request_dtl SET request_status="+remotrequestType.NEW.getRemotrequestTypeId()+" AND po_id=null "
					                  + "WHERE "
					                  + "id IN(" +remote_request_dtlid+")";
			
		 final String updateReqDtlId="UPDATE "
									  + "mrp_hq_remote_request_dtl SET request_status="+remotrequestType.PROCESSING.getRemotrequestTypeId()+" , po_id ="+stockOutId+ " "
									  + "WHERE id IN(" +remote_request_dtl_id+")";
			
		 final String markAsDeletedSQl = "DELETE "
								          + "FROM "
								          + ""+ getTable()+ "  "
								          + "WHERE "
								          + "po_hdr_id=" + stockOutId	+ "" + wherePart + ";";
		beginTrans();
		try {

			executeSQL(updateReqDtlId);

			executeSQL(markAsDeletedSQl);
			if(remote_request_dtlid!=""){
				executeSQL(updateReqDtl);
			}

			for (PO_dtl stockOutItem : itemList) {

				if(stockOutItem.getRemote_request_status()==1 ){
					executeSQL(updateHdrId);

				}else if(stockOutItem.getRemote_request_status()==2 ){
					executeSQL(updateHdrStatus);
				}

				if (stockOutItem.getPo_hdr_id() != null && stockOutItem.getId() != null) {
					super.update(stockOutItem);
				}else{
					super.insert(stockOutItem);
				}
			}
			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}
	}




	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#delete(java.lang.String)
	 */
	@Override
	public Integer delete(String where) throws Exception {
		final String markAsDeletedSQl = "DELETE FROM " + getTable()+ " WHERE po_hdr_id=" + where+	";";
		beginTrans();
		try {

			executeSQL(markAsDeletedSQl);

			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}
		return 1;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql = "SELECT * FROM " + getTable();

		return getTableRowsAsJson(sql);
	}



	/**
	 * @param poId
	 * @return
	 * @throws Exception 
	 */
	public JsonArray getpoDtlList(Integer poId) throws Exception {

		final String sql = "SELECT "
				           + "po_dtl.id,po_dtl.po_hdr_id,po_dtl.stock_item_id,po_dtl.stock_item_code,po_dtl.stock_item_name,"
				           + "po_dtl.qty,po_dtl.unit_price,po_dtl.amount,po_dtl.po_dtl_status,"
				           + "CASE (po_dtl.is_tax_inclusive) WHEN 1 "
				           + " THEN 0 "
				           + " ELSE IFNULL(vw.tax_pc, 0) "
				           + "END AS tax_pc,"
				           + "CASE (po_dtl.is_tax_inclusive)	WHEN 1 THEN	0 "
				         //  + " ELSE IFNULL(vw.tax_hdr_id, 0) "
				           + " ELSE IFNULL(vw.tax_id, 0) "
				           + "END AS tax_id "
				           + "FROM "
				           +  getTable()+" po_dtl "
				           + "LEFT JOIN "
				         //  +  "(SELECT id,tax_pc,tax_hdr_id,valid_from "
				           +  "(SELECT id,tax_pc,tax_id "
				           +  "FROM "
				           +  "vw_stockitem_tax "
				          /* +  "WHERE "
				           +  "vw_stockitem_tax.valid_from < NOW() "
				           +  "ORDER BY valid_from DESC "*/
				           + ") vw ON po_dtl.stock_item_id = vw.id "
				           + "WHERE  "
				           + "po_dtl.po_hdr_id="+poId+" "
				           	/*	+ "AND po_dtl.po_dtl_status NOT IN(1,2)"*/
				           		+ "";

		return getTableRowsAsJson(sql);
	}



}
