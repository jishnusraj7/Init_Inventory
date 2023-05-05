package com.indocosmo.mrp.web.core.counter.dao;

import com.indocosmo.mrp.web.core.base.dao.IBaseDao;
import com.indocosmo.mrp.web.core.counter.model.Counter;

public interface ICounterDao extends IBaseDao<Counter>{
	
	/**
	 * @param module
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Integer getCounter(String module, String key)throws Exception;
	
}
