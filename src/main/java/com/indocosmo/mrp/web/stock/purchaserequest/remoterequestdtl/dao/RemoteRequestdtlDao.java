package com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.dao;


import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.model.RPR_dtl;


public class RemoteRequestdtlDao extends GeneralDao<RPR_dtl> implements IRemoteRequestdtlDao{


	/**
	 * @param context
	 */
	public RemoteRequestdtlDao(ApplicationContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public RPR_dtl getNewModelInstance() {
		return new RPR_dtl();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {
		return "mrp_hq_remote_request_dtl";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.purchaserequest.remoterequestdtl.dao.IRemoteRequestdtlDao#getPrDtlData(java.lang.String)
	 */
	@Override
	public JsonArray getPrDtlData(String where) throws Exception {

		String SQL="SELECT "
				    + "prdtl.*,uom.code as uomcode "
				    + "FROM "
				    + ""+ getTable() + " prdtl "
				    + "LEFT JOIN uoms uom on uom.id="
				    +  "(SELECT "
				    +  "uom_id "
				    +  "FROM mrp_stock_item "
				    +  "WHERE "
				    +  "mrp_stock_item.id=prdtl.stock_item_id)  "
				    + "WHERE "
				    + "remote_request_hdr_id= " + where ;

		return getTableRowsAsJson(SQL);
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getList(java.lang.String)
	 */
	@Override
	public List<RPR_dtl> getList(String where) throws Exception {
		
		String sql="SELECT "
				   + "prdtl.*,uom.code as uomcode "
				   + "FROM "
				   +  getTable() + " prdtl "
				   + "LEFT JOIN uoms uom on uom.id="
				   +  "(SELECT uom_id "
				   +  "FROM mrp_stock_item "
				   +  "WHERE "
				   + "mrp_stock_item.id=prdtl.stock_item_id)  "
				   + "WHERE "
				   + "remote_request_hdr_id= " + where ;
		
		return buildItemList(sql);}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getList()
	 */
	@Override
	public List<RPR_dtl> getList() throws Exception {

		String SQL="SELECT * from "+ getTable();

		return buildItemList(SQL);
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(java.util.List)
	 */
	@Override
	public void save(List<RPR_dtl> itemList) throws Exception {

		String stockODtlIdList = "";
		String wherePart = "";

		final Integer stockOutId = itemList.get(0).getRemote_request_hdr_id();

		for (RPR_dtl stockOutItem : itemList) {

			if (stockOutItem.getId() != null) {

				stockODtlIdList += ((stockODtlIdList.isEmpty()) ? "" : ",")
						+ stockOutItem.getId();
			}
		}


		if (stockODtlIdList.length() > 0) {

			if(!stockODtlIdList.equals("null")){

				wherePart = " AND id not in (" + stockODtlIdList + ")";
			}
			else{
				wherePart = "";
			}
		}

		final String markAsDeletedSQl = "DELETE FROM " + getTable()+ "  WHERE remote_request_hdr_id=" + stockOutId	+ "" + wherePart + ";";

		beginTrans();

		try {

			executeSQL(markAsDeletedSQl);

			for (RPR_dtl stockOutItem : itemList) {

				if (stockOutItem.getRemote_request_hdr_id() != null && stockOutItem.getId() != null) {

					super.update(stockOutItem);
				}
				else{

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

		final String markAsDeletedSQl = "DELETE FROM " + getTable()+ " WHERE remote_request_hdr_id=" + where+	";";

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
}
