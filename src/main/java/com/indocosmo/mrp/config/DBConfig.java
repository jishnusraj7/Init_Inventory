package com.indocosmo.mrp.config;

import java.util.HashMap;

import com.indocosmo.mrp.config.bean.DBSettings;
import com.indocosmo.mrp.utils.core.dbutils.DBHelper;
import com.indocosmo.mrp.utils.core.dbutils.MySQLDBHelper;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;

public class DBConfig {

	private DBSettings dbSettings;
	private static DBConfig instance;
	private static HashMap<Integer, DBHelper> dbHelpers;
	
	/**
	 * 
	 */
	private DBConfig(){
		
		if(dbHelpers==null)
			dbHelpers=new HashMap<Integer, DBHelper>();
		
	}

	/**
	 * @param shopID
	 * @return
	 * @throws Exception
	 */
	public DBHelper getDBHelper(ApplicationContext context) {
		
		DBHelper dbHelper=null;
		
		dbHelper=dbHelpers.get(context.getCompanyID());
				
		if( dbHelper==null || !dbHelper.isActive()){
			
			dbHelper=MySQLDBHelper.getInstance(context);
			dbHelpers.put(context.getCompanyID(), dbHelper);
		}
		
		return dbHelper;
	}


	/**
	 * @return
	 */
	public static DBConfig getInstance() {
	
		if(instance==null)
			instance=new DBConfig();
		
		return instance;
	}

	/**
	 * @return
	 */
	public DBSettings getDBSettings() {
		return dbSettings;
	}

	/**
	 * @param dbSettings
	 */
	public void setDBSettings(DBSettings dbSettings) {
		
		this.dbSettings = dbSettings;
	}
	
	
}
