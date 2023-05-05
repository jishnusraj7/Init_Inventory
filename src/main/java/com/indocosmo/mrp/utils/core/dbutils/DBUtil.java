package com.indocosmo.mrp.utils.core.dbutils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.sql.rowset.CachedRowSet;

import org.sqlite.Function;

import com.indocosmo.mrp.utils.core.ReflectionUtil;

/**
 * DAtabase Utility functions
 * @author jojesh-13.2
 *
 */
public class DBUtil {

	/**
	 * No need to create the object of this class. All functions are static
	 */
	private DBUtil() {}


	/**
	 * Set the values of model object from the given rowset
	 * @param rs
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public static <T> T setModelFromSRS(CachedRowSet rs, T modelItem) throws Exception{

		try{
		for(Field field : ReflectionUtil.getAllFileds(modelItem.getClass())){

			/**
			 * Column annotation
			 * Gets the column from bean
			 */
			Column annotationColumn = (Column)field.getDeclaredAnnotation(Column.class);

			if(annotationColumn!=null){

				final String fieldName=annotationColumn.name();
				
				Object value=rs.getObject(fieldName);
				if(value!=null){
				
					if (field.getType()==Double.class)
						value=rs.getDouble(fieldName);
					else if (field.getType()==Integer.class)
						value=rs.getInt(fieldName);
					else if (field.getType()==Boolean.class)
						value=rs.getBoolean(fieldName);
					else if (field.getType()==Float.class)
						value=rs.getFloat(fieldName);
					else if (field.getType()==String.class)
						value=rs.getString(fieldName);
					else if (field.getType()==Timestamp.class)
						value=rs.getTimestamp(fieldName);
					else 
						value=rs.getObject(fieldName);
				}
				
				field.setAccessible(true);
				field.set(modelItem,value);
			}

		}
		
		}catch (Exception e){
			
			e.printStackTrace();
			throw e;
		}

		return modelItem;
	}
	
	/**
	 * Prepare the value for crud statement.
	 * @return
	 */
	public static Object modelValueToDBFieldValue(Field field, Object value) {
		
		
		return modelValueToDBFieldValue(field,value, true);
	}

	/**
	 * @param field
	 * @param value
	 * @param wrapStrings
	 * @return
	 */
	public static Object modelValueToDBFieldValue(Field field, Object value, boolean wrapStrings) {

		String crdValue=null;

		if(value!=null ){

			final String stringValue=value.toString();
			if(!stringValue.trim().equals("")){

				if (field.getType()==Double.class)
					crdValue=stringValue;
				else if (field.getType()==Integer.class)
					crdValue=stringValue;
				else if (field.getType()==Boolean.class)
					crdValue=((Boolean.parseBoolean(stringValue)?"1":"0"));
				else if (field.getType()==Float.class)
					crdValue=stringValue;
				else 
					crdValue=((wrapStrings)?"'"+stringValue+"'":stringValue);
			}
		}

		return crdValue;
	}
	
	
	
	/**
	 * Add mysql equivalent functions to SQLite
	 * @param con
	 * @throws Exception
	 */
	public static void initSqLiteDatabase(Connection con ) throws Exception{

		/**
		 * Porting mysql concat function to sqlite
		 */
		

		Function.create(con, "concat", new Function(){
			/* (non-Javadoc)
			 * @see org.sqlite.Function#xFunc()
			 */
			@Override
			protected void xFunc() throws SQLException {

				String resultString="";

				if (args() < 2) 
					throw new SQLException("concat(expr1,expr1,...): Invalid argument count." + args());

				for(int index=0; index<args(); index++)
					resultString+=value_text(index);

				result(resultString);
			}
		});

	}


}
