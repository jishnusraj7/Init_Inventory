package com.indocosmo.mrp.utils.core.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.mrp.config.DBConfig;
import com.indocosmo.mrp.config.bean.DBSettings;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.sun.rowset.CachedRowSetImpl;

public class MySQLDBHelper extends DBHelper {
	
	private Connection connection;
	
	/**
	 * @param context
	 * @return
	 */
	public static DBHelper getInstance(ApplicationContext context) {
		
		return new MySQLDBHelper(context);
	}

	/**
	 * @param context
	 */
	public MySQLDBHelper(ApplicationContext context) {
		super(context);
		
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.utils.dbutils.IDBHelper#executeSQL(java.lang.String)
	 */
	public int executeSQL(String sql) throws Exception {
		
		int cnt=-1;
		
		try{

			Statement statement=getConnection().createStatement();
//			conection.setAutoCommit(false);
			cnt=statement.executeUpdate(sql);
//			conection.commit();

		} catch (Exception e){

			throw e;
		
		}
		
		return cnt;
	}

	@SuppressWarnings("restriction")
	@Override
	public CachedRowSet executeSQLForRowset(String sql) throws Exception {
		
		CachedRowSet crs=null;
		
		Statement statement=getConnection().createStatement();
		
		System.out.println("inserted query============>"+sql);
		
		final ResultSet rs =statement.executeQuery(sql);
		
		if(rs!=null){

			crs=new CachedRowSetImpl();
			crs.populate(rs);
			rs.close();
		}
		statement.close();
		
		return crs;	
	}

	@Override
	public int[] executeSQLBatch(String[] sqlArray) throws Exception {

		final Statement statement=getConnection().createStatement();
//		conection.setAutoCommit(false);
		
		for(String sql:sqlArray)
			statement.addBatch(sql);
		
		final int cnt[]=statement.executeBatch();
//		conection.commit();
		
		return cnt;
	}

	@Override
	public void beginTrans() throws Exception {
		
		if(hasTransactionPoint()) return;
		
		if(getConnection()!=null)
			getConnection().setAutoCommit(false);
	}

	@Override
	public void endTrans() throws Exception {
		
		if(hasTransactionPoint()) return;
		
		if(getConnection()!=null && !getConnection().getAutoCommit()){
			getConnection().commit();
			getConnection().setAutoCommit(true);
		}
		

	}

	@Override
	public void rollbackTrans() throws Exception {

		if(hasTransactionPoint()) return;
		
		if(getConnection()!=null && !getConnection().getAutoCommit()){
			getConnection().rollback();
			getConnection().setAutoCommit(true);
		}
	}

	@Override
	public void beginTrans(String transactionPoint) throws Exception {
		
		if(hasTransactionPoint()) return;
		
		if(getConnection()!=null)
			getConnection().setAutoCommit(false);
		
		setTxnStartPoint(transactionPoint);
	}

	@Override
	public void endTrans(String transactionPoint) throws Exception {
		

		if(!isTransactionPoint(transactionPoint)) return;
		
		if(getConnection()!=null){
			getConnection().commit();
			getConnection().setAutoCommit(true);
		}
		
		setTxnStartPoint(null);

	}

	@Override
	public void rollbackTrans(String transactionPoint) throws Exception {
		
		if(!isTransactionPoint(transactionPoint)) return;
		
		if(getConnection()!=null && !getConnection().getAutoCommit()){
			getConnection().rollback();
			getConnection().setAutoCommit(true);
		}
		
		setTxnStartPoint(null);
	}

	@Override
	public Connection getConnection() throws Exception {
		
		final DBSettings dbSettings=DBConfig.getInstance().getDBSettings();

		Class.forName(dbSettings.getDriverClassName());
		
		final Company shopInfo=getContext().getCompanyInfo();
		
		if(connection==null || connection.isClosed() || !connection.isValid(2)){
			
			if(shopInfo == null){
				connection=DriverManager.getConnection(dbSettings.getDbConnectionString());
			}else{
				connection=DriverManager.getConnection(shopInfo.getConnectionString());
			}
		}
		return connection;
	}

	@Override
	public PreparedStatement buildPreparedStatement(String sql)
			throws Exception {
		
		PreparedStatement st=null;
		
		if(getConnection()!=null)
			st=getConnection().prepareStatement(sql);
		
		return st;
	}

	

}
