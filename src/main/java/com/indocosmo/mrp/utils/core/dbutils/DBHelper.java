package com.indocosmo.mrp.utils.core.dbutils;

import com.indocosmo.mrp.config.DBConfig;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;

public abstract class DBHelper implements IDBHelper {
	
//	@Autowired
	protected DBConfig dbConfig;
	protected String txnStartPoint;
	private ApplicationContext context;
	
	/**
	 * @return
	 */
	public ApplicationContext getContext() {
		return context;
	}
	/**
	 * @param jdbcURL
	 */
	public DBHelper(ApplicationContext context) {
		
		this.context=context;
		dbConfig=DBConfig.getInstance();
	}

	/**
	 * @return the txnStartPoint
	 */
	protected String getTxnStartPoint() {
		return txnStartPoint;
	}

	/**
	 * @param txnStartPoint the txnStartPoint to set
	 */
	protected void setTxnStartPoint(String txnStartPoint) {
		this.txnStartPoint = txnStartPoint;
	}
	
	/**
	 * @return
	 */
	protected boolean hasTransactionPoint(){
		
		return (txnStartPoint!=null && !txnStartPoint.trim().isEmpty());
	}
	
	/**
	 * @param txnPoint
	 * @return
	 */
	protected boolean isTransactionPoint(String txnPoint){
		
		return (txnStartPoint!=null && txnStartPoint.equals(txnPoint));
	}

	/**
	 * @return
	 */
	public boolean isActive() {
		
		boolean isActive=false;
		try {
			
			isActive= getConnection().isValid(2) ;

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return isActive;
	}

}
