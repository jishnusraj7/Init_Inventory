package com.indocosmo.mrp.web.core.base.currentdatetime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.mrp.config.DBConfig;
import com.indocosmo.mrp.utils.core.dbutils.DBHelper;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;


/**
 * @author anjutv
 *
 */
public class GettingCurrentDateTime {
	
	protected  DBHelper dbHelper;
	
	private ApplicationContext context;
	
	
	/**
	 * @return
	 */
	public ApplicationContext getContext() {
		return context;
	}

	/**
	 * @param context
	 */
	public GettingCurrentDateTime(ApplicationContext context){
		this.context=context;
		dbHelper=DBConfig.getInstance().getDBHelper(context) ;
	}
	
	
    /**
     * @return
     */
    public String getCurrentDate() {
    	
    	DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date dateobj = new Date();
        String date = df.format(dateobj);
    	
		return date;
	}
    
    /**
     * @return
     */
    public String getCurrentTime() {
    	
    	DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date dateobj = new Date();
        String time = df.format(dateobj);
    	
		return time;
	}
    
   
    /**
     * @param format
     * @return
     */
    public String getCurrentDateTime(String format) {
    	
    	DateFormat df = new SimpleDateFormat(format);
        Date dateobj = new Date();
        String dateTime = df.format(dateobj);
    	
		return dateTime;
	}
    
    /**
     * @return
     */
    public String getCurrentDateTime() {
    	
    	DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dateobj = new Date();
        String dateTime = df.format(dateobj);
    	
		return dateTime;
	}
    
    
    /**
     * @return
     * @throws Exception
     */
    public String getSystemDateFormat() throws Exception {
    	
    	String systemDateFormat = "yyyy/MM/dd";
    	
			final String sql="SELECT CASE(system_params.date_format) "
								+"WHEN 0 THEN CONCAT('dd',system_params.date_separator,'MM',system_params.date_separator,'yyyy') "
								+"WHEN 1 THEN CONCAT('MM',system_params.date_separator,'dd',system_params.date_separator,'yyyy') "
								+"WHEN 2 THEN CONCAT('yyyy',system_params.date_separator,'MM',system_params.date_separator,'dd') "
								+"ELSE '' END AS dateformat FROM system_params";
			try{
				final CachedRowSet srs=dbHelper.executeSQLForRowset(sql);
				if(srs!=null){

					if(srs.next()){

						systemDateFormat= srs.getString("dateformat");
					}
				}

			}catch (Exception e){

				throw e;
			}
    	
		return systemDateFormat;
	}
    
	 /**
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public String getDateWithSystemFormat(Date date) throws Exception {
	    	
	    	final String systemDateFormat = getSystemDateFormat();
	    	DateFormat formatter = new SimpleDateFormat(systemDateFormat);
	    	final String dateWithSystemFormat = formatter.format(date);
	    	
				
			return dateWithSystemFormat;
		}
}
