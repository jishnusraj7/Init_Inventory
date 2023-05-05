package com.indocosmo.mrp.utils.core.dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;

public interface IDBHelper {

	/**
	 * The function that SQL
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public int executeSQL(String sql) throws Exception;
	
	/**
	 * execute and returns the result set as SQL rowset
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public CachedRowSet executeSQLForRowset(String sql)  throws Exception;
	
	/**
	 * Execute the sqls in a batch
	 * @param sqlArray
	 * @return
	 * @throws Exception
	 */
	public int[] executeSQLBatch(String[] sqlArray) throws Exception;
	
	/**
	 * 
	 */
	public void beginTrans() throws Exception;
	
	/**
	 * @throws Exception
	 */
	public void endTrans() throws Exception;
	
	/**
	 * @throws Exception
	 */
	public void rollbackTrans() throws Exception;
	
	/**
	 * 
	 */
	public void beginTrans(String transactionPoint) throws Exception;
	
	/**
	 * @throws Exception
	 */
	public void endTrans(String transactionPoint) throws Exception;
	
	/**
	 * @throws Exception
	 */
	public void rollbackTrans(String transactionPoint) throws Exception;
	
	/**
	 * @return
	 */
	public Connection getConnection() throws Exception; 
	
	
	/**
	 * @param sql
	 */
	public PreparedStatement buildPreparedStatement(String sql) throws Exception;
}
