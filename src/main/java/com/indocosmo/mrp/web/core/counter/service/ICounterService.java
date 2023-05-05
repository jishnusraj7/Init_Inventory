package com.indocosmo.mrp.web.core.counter.service;

import com.indocosmo.mrp.web.core.base.service.IBaseService;
import com.indocosmo.mrp.web.core.counter.dao.CounterDao;
import com.indocosmo.mrp.web.core.counter.model.Counter;

public interface ICounterService extends IBaseService<Counter, CounterDao> {

	/**
	 * @param module
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Integer getNextCounter(String module, String key)throws Exception;
}
