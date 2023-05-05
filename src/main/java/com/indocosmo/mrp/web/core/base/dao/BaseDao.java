package com.indocosmo.mrp.web.core.base.dao;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.config.DBConfig;
import com.indocosmo.mrp.utils.core.dbutils.DBHelper;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.Hq;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockOutStatus;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.model.ModelBase;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias.ColumnCriterias;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias.OrderCriterias;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias.SearchCriterias;


/**
 * The Base class for all DAO classes
 * @author jojesh-13.2
 *
 */
public abstract class BaseDao<T extends ModelBase> implements IBaseDao<T> {


	protected  DBHelper dbHelper;

	protected ApplicationContext context;

	/**
	 * @return
	 */
	public ApplicationContext getContext() {
		return context;
	}

	/**
	 * @param context
	 */
	public BaseDao(ApplicationContext context){
		this.context=context;
		dbHelper=DBConfig.getInstance().getDBHelper(context) ;
	}

	public JsonObject getDataTableRowsAsJson(DataTableCriterias datatableParameters) throws Exception {
		JsonObject jobj=new JsonObject();
		List<DataTableColumns> columnList = new ArrayList<DataTableColumns>();
		String columns="";
	
			List<Map<ColumnCriterias, String>> colList=datatableParameters.getColumns();
			for(Map<ColumnCriterias, String> cols : colList){
				DataTableColumns column= new DataTableColumns();
				for (Entry<ColumnCriterias, String> entry : cols.entrySet()) {
					if(entry.getKey().toString().equals("data")){
						column.setData( entry.getValue());
					}else if(entry.getKey().toString().equals("name")){
						column.setName( entry.getValue());
					}else if(entry.getKey().toString().equals("searchable")){
						column.setSearchable(Boolean.valueOf(entry.getValue()));
					}else if(entry.getKey().toString().equals("orderable")){
						column.setOrderable(Boolean.valueOf(entry.getValue()));
					}else if(entry.getKey().toString().equals("searchValue")){
						column.setSearchValue(entry.getValue());
					}else if(entry.getKey().toString().equals("searchRegex")){
						column.setSearchRegex(Boolean.valueOf(entry.getValue()));
					} 
				}
				columnList.add(column);
				columns += ((columns.isEmpty()) ? "" : ",")
						+ column.getData();
			}
			String wherePart ="";
			String adnlFilterPart="";
			String sortPart ="";
			int i=Integer.parseInt(datatableParameters.getOrder().get(0).get(OrderCriterias.column));	
			//int j=Integer.parseInt(datatableParameters.getOrder().get(1).get(OrderCriterias.column));
			if(columnList.get(i).getOrderable()){
				
				if(columnList.size()<=2) {
				sortPart="order by "+columnList.get(i).getData()+" "+datatableParameters.getOrder().get(0).get(OrderCriterias.dir)+" ";
				}else {
					
				
				sortPart="order by "+columnList.get(2).getData()+" "+datatableParameters.getOrder().get(0).get(OrderCriterias.dir)+" "
						+ ","+columnList.get(i).getData()+" "+datatableParameters.getOrder().get(0).get(OrderCriterias.dir)+" " ;
			}
			}

			for(DataTableColumns col:columnList){
				if (!datatableParameters.getSearch().get(SearchCriterias.value).isEmpty()) {
					if(col.getSearchable()){
						wherePart+=((wherePart.isEmpty()) ? " where (" : " or ")	+ ""+getTable()+"."+col.getData()+" like '%"+(datatableParameters.getSearch().get(SearchCriterias.value)).trim()+"%'";
					}
				}
			/*	if(col.getSearchValue()!="" &&col.getSearchValue()!=null){
					adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " and ")	+ ""+getTable()+"."+col.getData()+" in ("+col.getSearchValue()+")";
				}*/
			}
			
			adnlFilterPart=getAdditionalFilter(columnList);

			int limitRows=datatableParameters.getLength();
			int offset=datatableParameters.getStart();

			int totalRecords=0;
			CachedRowSet totalRec=getTotalCountForDataTable(wherePart,(datatableParameters.getSearch().get(SearchCriterias.value)).trim(),adnlFilterPart,columnList);
			if(totalRec.next()){
				totalRecords=totalRec.getInt("row_count");
			}		
			JsonArray jDataArray=getTableRowsAsJsonForDataTable(columns,wherePart,(datatableParameters.getSearch().get(SearchCriterias.value)).trim(),sortPart,limitRows,offset,adnlFilterPart,columnList);
			jobj.addProperty("recordsTotal",totalRecords);
			jobj.addProperty("draw",datatableParameters.getDraw());
			jobj.addProperty("recordsFiltered",totalRecords);
			jobj.add("data",jDataArray);

		return jobj;		
	}

	
	
	public JsonObject getExportColoumnName() throws Exception {

		JsonObject jobj=new JsonObject();
		JsonArray jDataArray=getTableColomnName();
	
		jobj.add("data",jDataArray);

		return jobj;	
	}
	
	
	
	
	public JsonArray getExcelJsonListData(String coloumnList , String orderList) throws Exception {
		String SQL = "SELECT "+coloumnList+" FROM " + getTable() + " ORDER BY "+ orderList;

		return getTableRowsAsJson(SQL);
	}
	
	
	public JsonArray getTableColomnName() throws Exception{
		Company company=context.getCompanyInfo();
		final String sql = "SELECT  @a:=@a+1 SI, `COLUMN_NAME` as Display FROM `INFORMATION_SCHEMA`.`COLUMNS` , (SELECT @a:= 0) AS a WHERE `TABLE_SCHEMA`='"+company.getDatabaseName()+"' AND `TABLE_NAME`='"+ getTable() +"' ;";

		return  getTableRowsAsJson(sql);
	}
	
	

	public String getAdditionalFilter(List<DataTableColumns> columnList) {
		String adnlFilterPart="";
		for(DataTableColumns col:columnList){
			
			if(col.getSearchValue()!="" &&col.getSearchValue()!=null){
				adnlFilterPart+=((adnlFilterPart.isEmpty()) ? " " : " and ")	+ ""+getTable()+"."+col.getData()+" in ("+(col.getSearchValue()).trim()+")";
			}
		}
		return adnlFilterPart;
	}

	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart=" WHERE (is_system=0 OR is_system IS NULL) AND (is_deleted=0 OR is_deleted IS NULL) ";
		}else{
			wherePart+=") and (is_system=0 OR is_system IS NULL) AND (is_deleted=0 OR is_deleted IS NULL) ";
		}
		if(adnlFilterPart!="" && adnlFilterPart!=null){
			wherePart+=" and "+adnlFilterPart;
		}
		String sqlCount="select count(id) as row_count from "+getTable()+" "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}

	//function to override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		if(wherePart==null || wherePart==""){
			wherePart=" WHERE (is_system=0 OR is_system IS NULL) AND (is_deleted=0 OR is_deleted IS NULL)";
		}else{
			wherePart+=") and (is_system=0 OR is_system IS NULL) AND (is_deleted=0 OR is_deleted IS NULL)";
		}
		if(adnlFilterPart!="" && adnlFilterPart!=null){
			wherePart+=" and "+adnlFilterPart;
		}
		String sql="";
		if(context.getCurrentHttpSession().getAttribute("COMPANY_ID").equals(Hq.HQ.getHqId()))
		{
		 sql = "select "+getTable()+".id as id,"+columns+",IFNULL(sync_queue.id ,'') AS quetableId from "+getTable()+"  LEFT JOIN sync_queue  ON ("+getTable()+".id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"')    "+wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
		}
		else
		{
		 sql = "select "+getTable()+".id as id,"+columns+" from "+getTable()+"  "+wherePart+" "+sortPart+" limit "+limitRows+" OFFSET "+offset+"";
	
		}
		return getTableRowsAsJson(sql);
	}



	/**
	 * Builds the JSOn array object from the CachedRowSet
	 * @param rs the CachedRowSet
	 * @return the JSON array
	 */
	protected JsonArray buildJsonArrayListFromSRS(CachedRowSet rs) {

		JsonArray dataArray = null;
		try {

			if(rs!=null){

				dataArray  = new JsonArray();
				while(rs.next()){

					final JsonObject row =builJsonRowFromSRS(rs);
					dataArray.add(row);
				}
			}
		}
		catch (Exception e)  {

			e.printStackTrace();
		}

		return dataArray;
	}


	/**
	 * Builds the JSON object from CachedRowSet row
	 * @param rs the CachedRowSet
	 * @return the JSON object
	 * @throws Exception 
	 */
	protected JsonObject builJsonRowFromSRS(CachedRowSet rs) throws Exception {

		JsonObject row=new JsonObject();

		final ResultSetMetaData metaData=rs.getMetaData();

		for(int colIndex=1; colIndex<=metaData.getColumnCount(); colIndex++){
			final String key=metaData.getColumnName(colIndex);
			final Object value=rs.getObject(key);
			row.addProperty(key,(value==null)?"":String.valueOf(value));  
		}

		return row;
	}

	/**
	 * Get the table rows as JSON array
	 * @param sql the sql statement foe fetching the records from table
	 * @return JsonArray
	 */
	protected JsonArray getTableRowsAsJson(String sql) throws Exception{

		final CachedRowSet resultSet =getRowSet(sql);

		return buildJsonArrayListFromSRS(resultSet);
	}


	/**
	 * Get the table rows as JSON array
	 * @param tableName
	 * @return
	 * @throws Exception 
	 */
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql="SELECT * FROM " + getTable();

		return getTableRowsAsJson(sql);
	}
	
	public JsonArray getDropdownArray() throws Exception {

		final String sql="SELECT id,code,name FROM " + getTable()+ " where (IFNULL(is_deleted,0) = 0) ";

		return getTableRowsAsJson(sql);
	}


	/**
	 * Get the table rows as JSON array
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	public JsonArray getTableRowsAsJson(String[] fields) throws Exception {

		String csFields=String.join(",",fields);

		final String sql="SELECT " + csFields + " FROM " + getTable();

		return getTableRowsAsJson(sql);
	}

	/**
	 * Get the table rows as JSON array
	 * @param fields
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public JsonArray getTableRowsAsJson(String[] fields, String where) throws Exception {

		String csFields=String.join(",",fields);

		final String sql="SELECT " + csFields + " FROM " + getTable() + " WHERE "+where;

		return getTableRowsAsJson(sql);
	}


	/**
	 * Returns the sql rowset
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	protected CachedRowSet getRowSet(String sql) throws Exception{

		return executeSQLForRowset(sql);
	}


	/**
	 * The function that SQL
	 * @param sql  
	 * @return
	 * @throws Exception
	 */
	public int executeSQL(String sql) throws Exception{

		return dbHelper.executeSQL(sql);
	}

	/**
	 * execute and returns the result set as SQL rowset
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public CachedRowSet executeSQLForRowset(String sql)  throws Exception{

		return dbHelper.executeSQLForRowset(sql);
	}

	/**
	 * Execute the sqls in a batch
	 * @param sqlArray
	 * @return
	 * @throws Exception
	 */
	public int[] executeSQLBatch(String[] sqlArray) throws Exception{

		return dbHelper.executeSQLBatch(sqlArray);
	}

	/**
	 * Override this method to return he table
	 * @return
	 */
	protected abstract String getTable();

	/**
	 * @throws Exception
	 */
	public void beginTrans() throws Exception{

		dbHelper.beginTrans();
	}


	/**
	 * @throws Exception
	 */
	public void endTrans() throws Exception{

		dbHelper.endTrans();
	}

	/**
	 * @throws Exception
	 */
	public void rollbackTrans() throws Exception{

		try{

			dbHelper.rollbackTrans();
		}catch(Exception e){

			e.printStackTrace();
		}
	}


	/**
	 * @throws Exception
	 */
	public void beginTrans(String txnStartPoint) throws Exception{

		dbHelper.beginTrans(txnStartPoint);
	}


	/**
	 * @throws Exception
	 */
	public void endTrans(String txnStartPoint) throws Exception{

		dbHelper.endTrans(txnStartPoint);
	}

	/**
	 * @throws Exception
	 */
	public void rollbackTrans(String txnStartPoint) throws Exception{

		try{

			dbHelper.rollbackTrans(txnStartPoint);

		}catch(Exception e){

			e.printStackTrace();
		}
	}


}