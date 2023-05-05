package com.indocosmo.mrp.web.core.counter.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.BaseService;
import com.indocosmo.mrp.web.core.counter.dao.CounterDao;
import com.indocosmo.mrp.web.core.counter.model.Counter;

public class CounterService extends BaseService<Counter, CounterDao> implements ICounterService{

	private CounterDao counterDao;
	
	public CounterService(ApplicationContext context) {
		super(context);
		
		counterDao=new CounterDao(getContext());
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.posellaweb.web.common.counter.service.ICounterService#getNextCounter(java.lang.String, java.lang.String)
	 */
	@Override
	public Integer getNextCounter(String module, String key) throws Exception {

		Integer counter=counterDao.getCounter(module, key);

		return counter;
	}
	
	@Override
	public CounterDao getDao() {
		// TODO Auto-generated method stub
		return counterDao;
	}

	/**
	 * @param module
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getNextCounterwithPrefix(String module, String key) throws Exception {
		
		return counterDao.getNextCounterwithPrefix(module, key);
	}
	
}
