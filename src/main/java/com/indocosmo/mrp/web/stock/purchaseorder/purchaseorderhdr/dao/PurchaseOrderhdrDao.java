package com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.purchaseordersType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias.SearchCriterias;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.model.PO_hdr;


/**
 * @author jo
 *
 */
public class PurchaseOrderhdrDao extends GeneralDao<PO_hdr> implements IPurchaseOrderhdrDao{


	public PurchaseOrderhdrDao(ApplicationContext context) {
		
		super(context);
		// TODO Auto-generated constructor stub
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public PO_hdr getNewModelInstance() {
		
		// TODO Auto-generated method stub
		return new PO_hdr();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {
		// TODO Auto-generated method stub
		
		return "mrp_po_hdr";
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.dao.IPurchaseOrderhdrDao#getPohdrdata(java.lang.String)
	 */
	public PO_hdr  getPohdrdata(String id) throws Exception 
	{
		String SQL="SELECT * FROM "+ getTable() + " WHERE id=" + id;
		CachedRowSet rs= getRowSet(SQL);
		PO_hdr pohdr=getNewModelInstance();
		
		if(rs!=null){
			
			while(rs.next()){			
		
				DBUtil.setModelFromSRS(rs, pohdr);
			}
		}	
		return pohdr;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		
		final String sql="SELECT * FROM " + getTable();
		
		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#save(java.util.List)
	 */
	@Override
	public void save(List<PO_hdr> itemList) throws Exception {
	
		String stockInItemList = "";
		String wherePart = "";

		final Integer stockInId = itemList.get(0).getId();

		for (PO_hdr stockInItem : itemList) {

			if (stockInItem.getId() != null) {
		
				stockInItemList += ((stockInItemList.isEmpty()) ? "" : ",")
						+ stockInItem.getId();
			}
		}
		if (stockInItemList.length() > 0) {
		
			wherePart = " AND stock_item_id not in (" + stockInItemList + ")";
		
		}

		final String markAsDeletedSQl = "DELETE FROM " + getTable()+ "  WHERE stock_in_hdr_id=" + stockInId	+ "" + wherePart + ";";


		beginTrans();
		try {

			executeSQL(markAsDeletedSQl);
			if (stockInItemList.length() > 0)
				super.save(itemList);
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
		
		final String markAsDeletedSQl = "DELETE FROM " + getTable()+ " WHERE id=" + where+	";";
		
		Integer is_deleted = 0;

		beginTrans();
		try {
			
			is_deleted =executeSQL(markAsDeletedSQl);

			endTrans();

		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}
		return is_deleted;
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.dao.IPurchaseOrderhdrDao#getPurchaseRequestList(com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.model.PO_hdr)
	 */
	@Override
	public List<PO_hdr> getPurchaseRequestList(PO_hdr pohdr) throws Exception {

		final String sql="SELECT "
				         + "* "
				         + "FROM "
				         +  getTable()+  "  "
				         + "WHERE "
				         + "po_status="+purchaseordersType.NEW.getPurchaseorderTypeId()+" OR  po_status="+purchaseordersType.CLOSED.getPurchaseorderTypeId()+" ";
	
		List<PO_hdr> list=null;

		CachedRowSet rs= getRowSet(sql);

		if(rs!=null){

			list=new ArrayList<PO_hdr>();

			while(rs.next()){

				PO_hdr item=getNewModelInstance();
				DBUtil.setModelFromSRS(rs, item);
				list.add(item);
			}
		}

		return list;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.dao.IPurchaseOrderhdrDao#getemote_pr_requestList()
	 */
	public JsonArray getemote_pr_requestList() throws Exception {

		final String sql="SELECT *"
						+ "FROM "
						+ "vw_remote_requests "
						+ "WHERE "
						+ "request_status="+purchaseordersType.NEW.getPurchaseorderTypeId()+" AND request_status_hdr IN(0,1)";

		return getTableRowsAsJson(sql);
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getDataTableList(com.indocosmo.mrp.web.masters.datatables.DataTableCriterias, java.util.List)
	 */
	@Override
	public JsonArray getDataTableList(DataTableCriterias datatableParameters, List<String> tableFields) throws Exception {

		String sql;

		if (datatableParameters.getSearch().get(SearchCriterias.value).isEmpty())  
		{
			sql = "SELECT  *  FROM "+getTable()+" LIMIT "+datatableParameters.getLength()+" OFFSET "+datatableParameters.getStart()+"";
		}
		else{
			sql = " SELECT  *  FROM "+getTable()+" as rb WHERE rb."+tableFields.get(0)+" LIKE '%"+datatableParameters.getSearch().get(SearchCriterias.value)+"%'";

		}

		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.IGeneralDao#dataTableTotalRecord()
	 */
	@Override
	public Integer dataTableTotalRecord() {

		Integer countResults = 0 ;
		String sqlQuery="SELECT COUNT(*) AS row_count FROM "+getTable()+"";

		try {
			
			countResults = excuteSQLForRowCount(sqlQuery);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return countResults;
	}




	/**
	 * @param supllierId
	 * @return
	 * @throws Exception 
	 */
	public JsonArray getpoList(Integer supllierId) throws Exception {

		final String sql = "SELECT "
				           + "DISTINCT po_hdr.id,po_hdr.po_number,po_hdr.supplier_id "
				           + "FROM "
				           +  getTable()+" po_hdr "
				           + "INNER JOIN mrp_po_dtl  po_dtl ON po_dtl.po_hdr_id=po_hdr.id "
				           + "WHERE "
				         /*  + "po_hdr.id = po_dtl.po_hdr_id AND  po_hdr.supplier_id="+supllierId+" AND po_hdr.po_status = "+purchaseordersType.NEW.getPurchaseorderTypeId()+" "*/
				            + "po_hdr.id = po_dtl.po_hdr_id AND  po_hdr.supplier_id="+supllierId+" AND po_hdr.po_status != "+purchaseordersType.CLOSED.getPurchaseorderTypeId()+" "
				            		+ " AND po_hdr.po_status != "+purchaseordersType.PROCESSING.getPurchaseorderTypeId()+""
				         /*  + "AND po_dtl.po_dtl_status="+purchaseordersType.CLOSED.getPurchaseorderTypeId()+""*/
				           		+ "";

		return getTableRowsAsJson(sql);
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getAdditionalFilter(java.util.List)
	 */
	@Override
	public String getAdditionalFilter(List<DataTableColumns> columnList) {
		String adnlFilterPart="";
		
		for(DataTableColumns col:columnList){

			if(col.getSearchValue()!="" &&col.getSearchValue()!=null){

				if(col.getData().equals("po_status") && col.getSearchValue()!="" && col.getSearchValue()!=null){
					adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " AND ")	+ ""+getTable()+"."+col.getData()+" IN ("+(col.getSearchValue()).trim()+")";

				}
				else if(col.getData().equals("po_number") && col.getSearchValue()!="" && col.getSearchValue()!=null)
				{
					adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " AND ")	+ ""+getTable()+"."+col.getData()+" LIKE '%"+(col.getSearchValue()).trim()+"%'";	
				}
				else{
					adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " AND ")	+ ""+getTable()+"."+col.getData()+" = ('"+(col.getSearchValue()).trim()+"')";

				}
			}
		}
		return adnlFilterPart;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		
		if(searchCriteria!="" && searchCriteria!=null){
			
			wherePart="WHERE "
		     		   + "(po_number like '%"+searchCriteria+"%' "
		     		   + "OR supplier_name LIKE '%"+searchCriteria+"%' "
		     		   + "OR po_date LIKE '%"+searchCriteria+"%' "
		     		   + "OR po_print_date LIKE '%"+searchCriteria+"%' "
		     		   + "OR po_close_date LIKE '%"+searchCriteria+"%' "
		     		   + "OR total_amount LIKE '%"+searchCriteria+"%')";
		}

		if(adnlFilterPart!="" && adnlFilterPart!=null){

			if(wherePart!=null && wherePart!=""){
				
				wherePart+=" AND "+adnlFilterPart;
			}
			else{
			
				wherePart=" WHERE "+adnlFilterPart;
			}
		}
		
		String sql = "SELECT id,"+columns+" FROM "+ getTable()+"  "+wherePart+" "+sortPart+" LIMIT "+limitRows+" OFFSET "+offset+"";
		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{


		if(searchCriteria!="" && searchCriteria!=null){
			wherePart=" WHERE "
					+ "(po_number LIKE '%"+searchCriteria+"%' "
					+ "OR supplier_name LIKE '%"+searchCriteria+"%' "
					+ "OR po_date LIKE '%"+searchCriteria+"%' "
					+ "OR po_print_date LIKE '%"+searchCriteria+"%' "
					+ "OR po_close_date LIKE '%"+searchCriteria+"%' "
					+ "OR total_amount LIKE '%"+searchCriteria+"%')";
		}
		if(adnlFilterPart!="" && adnlFilterPart!=null){

			if(wherePart!=null && wherePart!=""){
				
				wherePart+=" AND "+adnlFilterPart;
				
			}else{
				
				wherePart=" WHERE "+adnlFilterPart;
			}
		}
		String sqlCount="SELECT COUNT(id) as row_count FROM "+ getTable()+" "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}


}