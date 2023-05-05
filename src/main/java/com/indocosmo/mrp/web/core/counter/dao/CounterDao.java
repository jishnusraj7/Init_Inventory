package com.indocosmo.mrp.web.core.counter.dao;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.BaseDao;
import com.indocosmo.mrp.web.core.counter.model.Counter;

/**
 * @author jojesh-13.2
 *
 */
public class CounterDao extends BaseDao<Counter> implements ICounterDao{
	
	public final static int COUNTER_CONST=1000000;
	private final static String PRIFIX_CON_SMBL="_";

	/**
	 * @param context
	 */
	public CounterDao(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.base.dao.BaseDao#getTable()
	 */
	@Override
	protected String getTable() {

		return "mrp_counter";
	}
	
	/**
	 * @param module
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public Integer getCounter(String module, String key) throws Exception{
		
		Integer nextCounter=null;
		
		try{
			final String sql="SELECT key_value FROM mrp_counter WHERE module_name='"+module+"' AND key_name='"+key+"'";
			CachedRowSet rs= getRowSet(sql);
			if(rs!=null && rs.next()){
				
				nextCounter=rs.getInt("key_value") + 1;
				updateCounter(module, key, nextCounter);
				nextCounter += getContext().getCompanyID() * COUNTER_CONST;   
					         
			} 
		}
		catch (Exception e) {
			throw e;
		}
		return nextCounter;
	}
	          
	/**
	 * @param module
	 * @param key
	 * @param key_value
	 * @return
	 * @throws Exception
	 */
	private Integer updateCounter(String module, String key, Integer value) throws Exception{
		
		int res;
		
		try{
			
			beginTrans();
		
			final String sql="UPDATE mrp_counter set key_value=" + value + " WHERE module_name='"+module+"' AND key_name='"+key+"'";
			res=executeSQL(sql);
			endTrans();
			
		}catch(Exception e){
			
			throw e;
		}

		return res;
		
	}
	
	/**
	 * @param module
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public String getNextCounterwithPrefix(String module, String key) throws Exception {
		Integer nextCounter=null;
		String counterwithPrefix = null;
		final String sql="SELECT prefix, key_value FROM mrp_counter WHERE module_name='"+module+"' AND key_name='"+key+"'";
		
		CachedRowSet rs= getRowSet(sql);
		
		if(rs!=null && rs.next()){
			
			nextCounter=rs.getInt("key_value") + 1;
			counterwithPrefix = rs.getString("prefix");
		}
		
		counterwithPrefix += PRIFIX_CON_SMBL + nextCounter.toString();
		
		return counterwithPrefix;
	}
	
	
	
public Integer getCounterFor(String module, String key) throws Exception{
		
		Integer nextCounter=null;
	
		
		
			final String sql="SELECT (key_value) as key_value FROM counter WHERE module='"+module+"' AND key_name='"+key+"'";
		
		
		CachedRowSet rs= getRowSet(sql);
		
		if(rs!=null && rs.next()){
			
			nextCounter=rs.getInt("key_value") + 1;
			updateCounterFor(module,key,nextCounter);
				         
		}
		
		nextCounter += getContext().getCompanyID()*COUNTER_CONST;              
		
		return nextCounter;
	}
	          
	/**
	 * @param module
	 * @param key
	 * @param key_value
	 * @return
	 * @throws Exception
	 */
	private Integer updateCounterFor(String module, String key, Integer value) throws Exception{
		
		int res;
		
		try{
			
			beginTrans();
			
			
			final String sql="UPDATE counter set key_value=" + value + " WHERE module='"+module+"' AND key_name='"+key+"'";
			
			res=executeSQL(sql);
			endTrans();
			
		}catch(Exception e){
			
			throw e;
		}

		return res;
		
	}

	
	
}
