package com.indocosmo.mrp.web.masters.itemcategory.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.ReflectionUtil;
import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.Hq;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.MasterBaseDao;
import com.indocosmo.mrp.web.core.counter.dao.CounterDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.itemcategory.model.ItemCategory;


public class ItemCategoryDao extends MasterBaseDao<ItemCategory> implements IItemCategoryDao {
	private CounterDao counterDao;

	public ItemCategoryDao(ApplicationContext context) {
	
		super(context);
		counterDao=new CounterDao(getContext());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.MRP.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public ItemCategory getNewModelInstance() {

		return new ItemCategory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.MRP.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "mrp_item_category";
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTableRowsAsJsonForDataTable(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.util.List)
	 */
	@Override
	public JsonArray getTableRowsAsJsonForDataTable(String columns,String wherePart,String searchCriteria,String sortPart,int limitRows,int offset,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{

		if(wherePart==null || wherePart==""){

			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0)";
		}
		else{

			wherePart="WHERE "
					+ "(IFNULL(itm.is_deleted,0) = 0) and "
					+ " (itm.code like '%"+searchCriteria+"%' "
					+ "OR itm.name LIKE '%"+searchCriteria+"%' "
					+ "OR itm.description LIKE '%"+searchCriteria+"%' "
					+ "OR itmp.`name` LIKE '%"+searchCriteria+"%')";
		}
		String sql="";
		if(context.getCurrentHttpSession().getAttribute("COMPANY_ID").equals(Hq.HQ.getHqId()))
		{
		
		 sql = "SELECT "
		     		 + "itm.id,itm.`code`,itm.`name`,itm.description,itm.parent_id,itm.created_by,itm.created_at,"
		     		 + "itmp.`name` AS itemcategory_name,IFNULL(sync_queue.id ,'') AS quetableId "
		     		 + "FROM "
		     		 +  getTable()+" itm "
		     		 + "LEFT JOIN sync_queue  ON (itm.id=sync_queue.record_id AND sync_queue.table_name='"+getTable()+"') "
		     		 + "LEFT JOIN "+getTable()+" itmp ON itm.parent_id = itmp.id "
		     		 +  wherePart+" "
		     		 +  sortPart+" "
		     		 + "LIMIT "+limitRows+" "
		     		 + "OFFSET "+offset+"";
		}
		else
		{
			 sql = "SELECT "
		     		 + "itm.id,itm.`code`,itm.`name`,itm.description,itm.parent_id,itm.created_by,itm.created_at,"
		     		 + "itmp.`name` AS itemcategory_name "
		     		 + "FROM "
		     		 +  getTable()+" itm "
		     		
		     		 + "LEFT JOIN "+getTable()+" itmp ON itm.parent_id = itmp.id "
		     		 +  wherePart+" "
		     		 +  sortPart+" "
		     		 + "LIMIT "+limitRows+" "
		     		 + "OFFSET "+offset+"";
		}

		return getTableRowsAsJson(sql);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTotalCountForDataTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public CachedRowSet getTotalCountForDataTable(String wherePart,String searchCriteria,String adnlFilterPart,List<DataTableColumns> columnList) throws Exception{
		
		if(wherePart==null || wherePart==""){
		
			wherePart="WHERE (IFNULL(itm.is_deleted,0) = 0)";
		}
		else{
		
			wherePart="WHERE "
					   + "(IFNULL(itm.is_deleted,0) = 0) "
					   + "AND (itm.code LIKE '%"+searchCriteria+"%' "
					   + "OR itm.name LIKE '%"+searchCriteria+"%' "
					   + "OR itm.description LIKE '%"+searchCriteria+"%' "
					   + "OR itmp.`name` LIKE '%"+searchCriteria+"%')";
		}
		
		String sqlCount="SELECT count(itm.id) as row_count from "+getTable()+" itm LEFT JOIN "+getTable()+" itmp ON itm.parent_id = itmp.id "+wherePart+"";
		CachedRowSet totalRecs= executeSQLForRowset(sqlCount);		
		return totalRecs;
	}
	
	@Override
	public JsonArray getTableRowsAsJson() throws Exception {

		final String sql="SELECT * FROM " + getTable() + " WHERE  (is_deleted=0 OR is_deleted IS NULL)";

		return getTableRowsAsJson(sql);
	}
	
	
	@Override
	protected void setValuesForInsertPS(PreparedStatement ps , ItemCategory item) throws Exception {
	

	     Integer version=(Integer) context.getCurrentHttpSession().getAttribute("version");
	    
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
							int value=-1;
							if(version==1)
							{
							 value=counterDao.getCounterFor(module, key);
							}
							else
							{
							 value=counterDao.getCounter(module, key);
							}
					
							
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
					

						ps.setObject(index, fieledValue);

					index++;
				}
			}
		}
	
	}
	


}
