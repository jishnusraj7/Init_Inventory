package com.indocosmo.mrp.web.core.base.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.ReflectionUtil;
import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.annotation.Pk;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.model.GeneralModelBase;
import com.indocosmo.mrp.web.core.counter.dao.CounterDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias.SearchCriterias;


/**
 * Base class for all master DAO
 * @author jojesh-13.2
 *
 * @param <T>
 */
public abstract class GeneralDao <T extends GeneralModelBase> extends BaseDao<T> implements IGeneralDao <T> {

	private CounterDao counterDao;

	/**
	 * @param context
	 */
	public GeneralDao(ApplicationContext context) {
		super(context);
		
		counterDao=new CounterDao(getContext());
	}

	

	/**
	 * @param item
	 * @throws Exception
	 */
	public void save(T item) throws Exception{

		if(!isExist(item))
			insert(item);
		else
			update(item);

	}

	/**
	 * @param item
	 * @throws Exception
	 */
	public void save(List<T> itemList) throws Exception{

		ArrayList<T> iItems=new ArrayList<T>();
		ArrayList<T> uItems=new ArrayList<T>();

		for(T item:itemList){

			if(!isExist(item))
				iItems.add(item);
			else
				uItems.add(item);
		}

		if(iItems.size()>0)
			insert(iItems);

		if(uItems.size()>0)
			update(uItems);
	}
	
	
	/**
	 * @param item
	 * @throws Exception
	 */
	public void saveHqData(List<T> itemList) throws Exception{

		ArrayList<T> iItems=new ArrayList<T>();
		ArrayList<T> uItems=new ArrayList<T>();

		for(T item:itemList){

			if(!isExist(item))
				iItems.add(item);
			else
				uItems.add(item);
		}

		if(iItems.size()>0)
			insert(iItems);

		if(uItems.size()>0)
			update(uItems);
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.dao.IMasterBaseDao#insert(java.lang.Object)
	 */
	public void insert(T item) throws Exception{

		PreparedStatement ps= getInsertPs(item);

		if(ps!=null){
			
			setValuesForInsertPS(ps, item);
			System.out.println("start the trnsaction=========>");
			beginTrans();
			System.out.println("start the beginTrans=========>");
			try{
				System.out.println("start the ps.execute=========>"+ps.toString());
				ps.execute();
				System.out.println("after the ps.execute=========>");
				endTrans();
				System.out.println("after the endTrans=========>");

			}catch (Exception e){
				System.out.println("before the rollbackTrans=========>");
				rollbackTrans();
				System.out.println("after the rollbackTrans=========>");
				throw e;
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.dao.IMasterBaseDao#insert(java.util.List)
	 */
	public void insert(List<T> itemList) throws Exception {

		PreparedStatement ps= getInsertPs(itemList.get(0));

		if(ps!=null){
			
			for(int index=0; index<itemList.size();index++){

				T item=itemList.get(index);
				setValuesForInsertPS(ps, item);

				/**
				 * Add new batch if more records exist
				 */
				ps.addBatch();
			}

			beginTrans();
			try{

				ps.executeBatch();
				endTrans();

			}catch (Exception e){

				rollbackTrans();
				throw e;
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.dao.IMasterBaseDao#update(java.lang.Object)
	 */
	public void update(T item) throws Exception {

		
		PreparedStatement ps= getUpdatePs(item);

		if(ps!=null){
			
			setValuesForUpdatePS(ps, item);

			beginTrans();
			try{

				ps.execute();
				endTrans();
			}catch (Exception e){

				rollbackTrans();
				throw e;

			}
		}

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.dao.IMasterBaseDao#update(java.util.List)
	 */
	public void update(List<T> itemList) throws Exception {

//		final String[] sqlArray=new String[itemList.size()];
		
		PreparedStatement ps= getUpdatePs(itemList.get(0));

		if(ps!=null){
		
			for(int index=0; index<itemList.size();index++){

				T item=itemList.get(index);
				setValuesForUpdatePS(ps, item);

				/**
				 * Add new batch if more records exist
				 */
				ps.addBatch();
			}

			beginTrans();
			try{

				ps.executeBatch();
				endTrans();
			}catch (Exception e){

				rollbackTrans();
				throw e;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.dao.IMasterBaseDao#delete(java.lang.Object)
	 */
	public void delete(T item) throws Exception {

		final String sql=getDeleteSQL(item);

		beginTrans();
		try{

			executeSQL(sql);
			endTrans();
		}catch (Exception e){

			rollbackTrans();
			throw e;
		}

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.dao.IMasterBaseDao#delete(java.lang.String)
	 */
	public Integer delete(String  where) throws Exception {

		final String sql="UPDATE " + getTable()+  " SET is_deleted=1 " +" WHERE "+where;
		Integer is_deleted = 0;

		beginTrans();
		try{

			is_deleted = executeSQL(sql);
			endTrans();
		}catch (Exception e){

			rollbackTrans();
			throw e;
		}

		return is_deleted;

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.dao.IMasterBaseDao#delete(java.util.List)
	 */
	public void delete(List<T> itemList) throws Exception {

		final String[] sqlArray=new String[itemList.size()-1];

		for(int index=0; index<itemList.size();index++){

			T item=itemList.get(index);
			sqlArray[index]=getDeleteSQL(item);
		}

		beginTrans();
		try{

			executeSQLBatch(sqlArray);
			endTrans();
			
		}catch (Exception e){

			rollbackTrans();
			throw e;
		}

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.dao.IMasterBaseDao#getList()
	 */
	public List<T> getList() throws Exception {

		String SQL="SELECT * from "+ getTable() + " WHERE (is_deleted=0 OR is_deleted IS NULL)";

		return buildItemList(SQL);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.dao.IMasterBaseDao#getList(java.lang.String)
	 */
	public List<T> getList(String where) throws Exception {

		String SQL="SELECT * from "+ getTable() + " WHERE " + where + "";

		return buildItemList(SQL);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.dao.IMasterBaseDao#get(java.lang.String)
	 */
	public T get(String where) throws Exception {

		T item=null;
		List<T> list=getList(where);

		if(list!=null && list.size()>0 )
			item=list.get(0);

		return item;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.dao.BaseDao#getTableRowsAsJson()
	 */
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql="SELECT * FROM " + getTable() + " WHERE (is_system=0 OR is_system IS NULL) AND (is_deleted=0 OR is_deleted IS NULL)";

		return getTableRowsAsJson(sql);
	}

	/**
	 * @param item
	 * @return
	 * @throws Exception
	 */
	private PreparedStatement getInsertPs(T item) throws Exception{

		PreparedStatement ps=null;

		String insertSQL="INSERT INTO ";
		String fieldName=null;
		String fieldNamePart="(";
		String fieldValuePart="Values(";
		final String tableName=getTable(item);
		System.out.println(tableName);
		final HashMap<String, PropertyDescriptor> propertyDescriptors=ReflectionUtil.getPropertyDescriptors(item.getClass());

		for(Field field : ReflectionUtil.getAllFileds(item.getClass())){

			/**
			 * Column annotation
			 * Gets the column from bean
			 */
			final Column annotationColumn = (Column)field.getDeclaredAnnotation(Column.class);

			if(annotationColumn!=null && propertyDescriptors.get(field.getName())!=null){

				fieldName=null;

				fieldName=annotationColumn.name();

				if(fieldName!=null){

					fieldNamePart+=fieldName+",";
					fieldValuePart+="?,";
				}
			}
		}

		fieldNamePart=fieldNamePart.substring(0, fieldNamePart.length()-1)+")";
		fieldValuePart=fieldValuePart.substring(0, fieldValuePart.length()-1)+")";
		//insertSQL+=tableName + fieldNamePart + fieldValuePart +";";
		insertSQL+=tableName + fieldNamePart + fieldValuePart;

		ps=dbHelper.buildPreparedStatement(insertSQL);
		
		System.out.println(insertSQL);

		return ps;
	}


	/**
	 * @param ps
	 * @param item
	 * @throws Exception
	 */
	protected void setValuesForInsertPS(PreparedStatement ps, T item) throws Exception{

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
							int value=counterDao.getCounter(module, key);
					
							
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
	
	/**
	 * Generic method to generate the upadte statement 
	 * @param bean item
	 * @throws Exception
	 */
	protected PreparedStatement getUpdatePs(T item) throws Exception{

		PreparedStatement ps=null;		
		
		String updateSQL="UPDATE ";
		String tableName=getTable(item);
		System.out.println(tableName);
		String wherePart="";
		String valuePart="";

		for(Field field : ReflectionUtil.getAllFileds(item.getClass())){

			/**
			 * Column annotation
			 * Gets the column from bean
			 */
			Column annotationColumn = (Column)field.getDeclaredAnnotation(Column.class);

			if(annotationColumn!=null){

				/**
				 * Pk annotation
				 * get the Filed Pk type 
				 */
				final Pk annotationPk = (Pk)field.getDeclaredAnnotation(Pk.class);

				final String fieldName=annotationColumn.name();

				if(annotationPk!=null){ 

					if(!wherePart.equals(""))
						wherePart+= " AND "; 

					wherePart+= "`"+fieldName+"`=?";

				}else{

					if(!valuePart.equals(""))
						valuePart+= ", ";

					valuePart+= "`"+fieldName+"`=?";

				}
			}
		}

		updateSQL += tableName + " Set " + valuePart + ((wherePart!=null)? " WHERE " + wherePart:"") + ";";

		ps=dbHelper.buildPreparedStatement(updateSQL);

		return ps;
	}
	
	/**
	 * @param ps
	 * @param item
	 * @return
	 * @throws Exception
	 */
	protected void setValuesForUpdatePS(PreparedStatement ps, T item) throws Exception{

		ArrayList<Object> whereKeyValues=new ArrayList<Object>();
//		String valuePart="";

		int index=1;
		for(Field field : ReflectionUtil.getAllFileds(item.getClass())){

			/**
			 * Column annotation
			 * Gets the column from bean
			 */
			Column annotationColumn = (Column)field.getDeclaredAnnotation(Column.class);

			if(annotationColumn!=null){

				field.setAccessible(true);

				/**
				 * Pk annotation
				 * get the Filed Pk type 
				 */
				final Pk annotationPk = (Pk)field.getDeclaredAnnotation(Pk.class);
//				final String fieldName=annotationColumn.name();
				final Object value=field.get(item);
				final Object fieledValue=DBUtil.modelValueToDBFieldValue(field, value,false);

				if(annotationPk!=null){ 

					whereKeyValues.add(fieledValue);
					
					
				}else{
					
					ps.setObject(index, fieledValue);
					index++;
				}
			}
		}
		
		for(Object value:whereKeyValues){
			
			ps.setObject(index, value);
			index++;
		}

	}
	
	




	/**
	 * Generic method to generate the upadte statement 
	 * @param bean item
	 * @throws Exception
	 */
	protected String getDeleteSQL(T item) throws Exception{

		String deleteSQL="UPDATE ";

		String tableName= getTable(item);
		final String wherePart=getWhere(item);

		deleteSQL += tableName +  " SET is_deleted=1 " + ((wherePart!=null)? " WHERE " + wherePart:"") + ";";

		return deleteSQL;
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.dao.IMasterBaseDao#getList()
	 */
	protected List<T> buildItemList(String sql) throws Exception {

		List<T> list=null;


		CachedRowSet rs= getRowSet(sql);

		if(rs!=null){

			list=new ArrayList<T>();

			while(rs.next()){

				T item=getNewModelInstance();
				DBUtil.setModelFromSRS(rs, item);
				list.add(item);
			}
		}

		return list;
	}



	/**
	 * Return weather item exist or not.
	 * @param item
	 * @return
	 * @throws Exception 
	 */
	protected boolean isExist(T item) throws Exception{

		boolean result=false;

		final int rowCount=getRowWount(item);
		result=(rowCount>0);

		return result;
	}

	/**
	 * Return the total number rows;
	 * @param item
	 * @return
	 */
	protected int getRowWount(T item) throws Exception{


		final String tableName=getTable(item);
		final String wherePart=getWhere(item);
		final String sql;
		System.out.println("tableName=============>"+tableName);
		if(tableName.equalsIgnoreCase("mrp_dept_stock_item")){

			sql= "SELECT COUNT(dept_stock_item_id) AS  row_count FROM " + tableName + ((wherePart!=null)? " WHERE " + wherePart:"") + ";";
			System.out.println("rowcount query=============>"+sql);
		}else if(tableName.equalsIgnoreCase("mrp_dept_stock_item_history")){
			sql= "SELECT COUNT(dept_stock_item_history_id) AS  row_count FROM " + tableName + ((wherePart!=null)? " WHERE " + wherePart:"") + ";";
			System.out.println("rowcount query=============>"+sql);
		}else{
			sql= "SELECT COUNT(id) AS  row_count FROM " + tableName + ((wherePart!=null)? " WHERE " + wherePart:"") + ";";
			System.out.println("else========rowcount query=============>"+sql);
		}
		

		return excuteSQLForRowCount(sql);

	}

	/**
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	protected int  excuteSQLForRowCount(String sql) throws Exception {

		int rowCount=0;

		final CachedRowSet srs=executeSQLForRowset(sql);

		if(srs!=null){

			if(srs.next()){

				rowCount=srs.getInt("row_count");
			}

		}

		return rowCount;
	}


	/**
	 * @param tableName
	 * @param where
	 * @return
	 * @throws Exception
	 */
	public int  getReferenceRowCount(String tableName,String where) throws Exception {

		final String sql= "SELECT COUNT(id) AS  row_count FROM " + tableName + ((where!=null)? " WHERE " + where:"") + ";";

		return excuteSQLForRowCount(sql);
	}

	/**
	 * @param item
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	protected String getWhere(T item ) throws IllegalArgumentException, IllegalAccessException{

		String wherePart=null;

		for(Field field : ReflectionUtil.getAllFileds(item.getClass())){

			/**
			 * Column annotation
			 * Gets the column from bean
			 */
			Column annotationColumn = (Column)field.getDeclaredAnnotation(Column.class);

			if(annotationColumn!=null){

				field.setAccessible(true);

				/**
				 * Pk annotation
				 * get the Filed Pk type 
				 */
				final Pk annotationPk = (Pk)field.getDeclaredAnnotation(Pk.class);

				if(annotationPk!=null){

					final String fieldName=annotationColumn.name();
					final Object value = field.get(item);
					final String fieledValue =
							(value==null) ? "NULL" : "'" + value.toString() + "'";

					if(wherePart==null)
						wherePart = "`" + fieldName + "`=" + fieledValue;
					else
						wherePart += " AND `" + fieldName + "`=" + fieledValue;

				}
			}
		}

		return wherePart;
	}

	/**
	 * @param item
	 * @return
	 * @throws Exception 
	 */
	protected String getTable(T item) throws Exception{

		String tableName=null;

		/**
		 * 
		 * Gets the table name from the bean
		 */
		Table tableAnnotation=item.getClass().getAnnotation(Table.class);

		if(tableAnnotation!=null && tableAnnotation.name()!="")
			tableName=tableAnnotation.name();

		if(tableName==null)
			throw new Exception("Invalid data bean. No table information found.");

		return tableName;

	}
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.IGeneralDao#getHqTableRowJson()
	 */
	public JsonArray getHqTableRowJson() throws Exception{
		
		final String sql="SELECT id,code,name FROM hq_"+getTable()+" WHERE id NOT IN (SELECT "+getTable()+".id FROM "+getTable()+"  WHERE "+getTable()+".is_deleted =0) AND code NOT IN (SELECT "+getTable()+".code FROM "+getTable()+") AND (is_deleted=0 OR is_deleted IS NULL)";

		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.IGeneralDao#getHqTableRowJson()
	 */
	public JsonArray getUpdatedHqTableRowJson() throws Exception{
		
		final String sql="SELECT hq_"+getTable()+".id,hq_"+getTable()+".code,hq_"+getTable()+".name,hq_"+getTable()+".is_deleted FROM hq_"+getTable()+" inner join "+getTable()+" on "+getTable()+".id=hq_"+getTable()+".id where hq_"+getTable()+".last_sync_at != "+getTable()+".last_sync_at";

		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.IGeneralDao#getHqTableRowJson()
	 */
	public List<T> getHqTableRowListToImport() throws Exception{
		
		final String sql="SELECT * FROM hq_"+getTable()+" WHERE id NOT IN (select id from "+getTable()+") AND is_deleted=0";

		return buildItemList(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.IGeneralDao#getHqTableRowJson()
	 */
	public List<T> getUpdatedHqTableRowListToImport() throws Exception{
		
		final String sql="SELECT hq_"+getTable()+".* FROM hq_"+getTable()+" inner join "+getTable()+" on "+getTable()+".id=hq_"+getTable()+".id where hq_"+getTable()+".last_sync_at != "+getTable()+".last_sync_at";

		return buildItemList(sql);
	}
	

	
   
	
	
	/**
	 * Gets the new object for the model class used in this controller.
	 * Should be overridden in the extended master class
	 * @return
	 */
	public abstract T getNewModelInstance();

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.IGeneralDao#getDataTableList(com.indocosmo.mrp.web.masters.datatables.DataTableCriterias, java.util.List)
	 */
	@Override
	public JsonArray getDataTableList(DataTableCriterias datatableParameters, List<String> tableFields) throws Exception {
	        
		String sql;
		
		if (datatableParameters.getSearch().get(SearchCriterias.value).isEmpty())  
		{
			sql = "SELECT  *  FROM "+getTable()+" WHERE (is_system=0 OR is_system IS NULL) AND (is_deleted=0 OR is_deleted IS NULL) limit "+datatableParameters.getLength()+" OFFSET "+datatableParameters.getStart()+"";
		}else{
			sql = " SELECT  *  FROM "+getTable()+" as rb where rb."+tableFields.get(0)+" like '%"+datatableParameters.getSearch().get(SearchCriterias.value)+"%' AND (is_system=0 OR is_system IS NULL) AND (is_deleted=0 OR is_deleted IS NULL)";
			
		}
		
		return getTableRowsAsJson(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.IGeneralDao#dataTableTotalRecord()
	 */
	@Override
	public Integer dataTableTotalRecord() {

		Integer countResults = 0 ;
		String sqlQuery="Select count(id) AS row_count from "+getTable()+" WHERE (is_system=0 OR is_system IS NULL) AND (is_deleted=0 OR is_deleted IS NULL)";

		try {
			countResults = excuteSQLForRowCount(sqlQuery);
		} catch (Exception e) {

			e.printStackTrace();
		}
		

		return countResults;
	}

	/**
	 * @param item
	 * @return
	 * @throws Exception 
	 */
	public Integer isCodeExist(String code) throws Exception {
		
		final String sql= "SELECT COUNT(code) AS  row_count FROM "+getTable()+" WHERE code='"+code+"' AND (is_system=0 OR is_system IS NULL) AND (is_deleted=0 OR is_deleted IS NULL) LIMIT 1";

		return excuteSQLForRowCount(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.IGeneralDao#getHqTableRowJson()
	 */
	public JsonArray getMastersRowJson() throws Exception{
		
		final String sql="SELECT id,code,name from "+ getTable() + " WHERE (is_system=0 OR is_system IS NULL) AND (is_deleted=0 OR is_deleted IS NULL)";
		
		return getTableRowsAsJson(sql);
	}
	
	/**
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public Double getItemLastCostPrice(Integer itemId) throws Exception{
		
		Double lastCcostPrice = 0.00;
		if(itemId != null){
			final String sql="SELECT stock_in_dtl.id,stock_in_dtl.stock_item_id,stock_in_dtl.stock_item_code,stock_in_dtl.unit_price AS costprice,stock_in_hdr.received_date FROM mrp_stock_in_dtl stock_in_dtl "
									+"INNER JOIN mrp_stock_in_hdr stock_in_hdr ON stock_in_dtl.stock_in_hdr_id = stock_in_hdr.id WHERE	stock_item_id = "+itemId+" AND stock_in_hdr.received_date = (SELECT MAX(mrp_stock_in_hdr.received_date) "
									+"FROM mrp_stock_in_hdr stock_in_hdr INNER JOIN mrp_stock_in_dtl stock_in_dtl ON stock_in_dtl.stock_in_hdr_id = stock_in_hdr.id WHERE stock_item_id = "+itemId+") ORDER BY stock_in_dtl.id DESC LIMIT 1";
			try{
				final CachedRowSet srs=executeSQLForRowset(sql);
				if(srs!=null){

					if(srs.next()){

						lastCcostPrice= srs.getDouble("costprice");
					}

				}

			}catch (Exception e){

				throw e;
			}

		}
 	
		return lastCcostPrice;
	}
	
	/**
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public Double getItemAverageCostPrice(Integer itemId) throws Exception{
		
		Double avgCcostPrice = 0.00;
		if(itemId != null){
			final String sql="SELECT MONTH (stock_in_hdr.received_date),YEAR (stock_in_hdr.received_date),AVG(stock_in_dtl.unit_price) AS costprice "
								+ "FROM mrp_stock_in_dtl stock_in_dtl  INNER JOIN mrp_stock_in_hdr stock_in_hdr ON stock_in_dtl.stock_in_hdr_id = stock_in_hdr.id WHERE "
								+ "stock_in_dtl.stock_item_id = "+itemId+" GROUP BY MONTH (stock_in_hdr.received_date),YEAR (stock_in_hdr.received_date) "
								+ "ORDER BY YEAR (stock_in_hdr.received_date) DESC LIMIT 1";
			try{
				final CachedRowSet srs=executeSQLForRowset(sql);
				if(srs!=null){

					if(srs.next()){

						avgCcostPrice= srs.getDouble("costprice");
					}

				}

			}catch (Exception e){

				throw e;
			}

		}
 	
		return avgCcostPrice;
	}
	
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.IGeneralDao#getCustomId(java.lang.Integer)
	 */
	@Override
	public Object getCustomId(Integer counter) {
	
		return counter;
	}
}
