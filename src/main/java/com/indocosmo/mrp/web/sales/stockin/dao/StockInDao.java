package com.indocosmo.mrp.web.sales.stockin.dao;

import java.text.SimpleDateFormat;
import java.util.Date;



import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.counter.dao.CounterDao;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.sales.stockin.model.StockIn;


public class StockInDao extends GeneralDao<StockIn> implements IStockInDao {
	private CounterDao counterDao;

	public StockInDao(ApplicationContext context) {

		super(context);
		counterDao=new CounterDao(getContext());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public StockIn getNewModelInstance() {

		return new StockIn();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "stock_in_hdr";
	}

	public int saveStockIn() throws Exception{
		
		// TODO Auto-generated method stub
		Integer stock_in_hdr_id=counterDao.getCounterFor("stockin", "stockin");
		
		Users user =(Users) context.getCurrentHttpSession().getAttribute("user");
		
		long millis=System.currentTimeMillis();  
		java.sql.Date sqlDate=new java.sql.Date(millis);  
		
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		  Date date = new Date();  
		   // System.out.println(formatter.format(date));  
		
		final String sql="insert INTO stock_in_hdr(id,date,type,created_by,created_at,is_deleted) "
				+ "values("+stock_in_hdr_id+",'"+sqlDate+"',1,"+user.getId()+",'"+formatter.format(date)+"',0)";
		
		beginTrans();
		try{
		executeSQL(sql);
		endTrans();
		}
		catch (Exception e){

			rollbackTrans();
			throw e;
		}
		
		
		return stock_in_hdr_id;
	}
}
