package com.indocosmo.mrp.web.core.base.dao;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.model.MasterModelBase;


/**
 * Base class for all master DAO
 * @author jojesh-13.2
 *
 * @param <T>
 */
public abstract class MasterBaseDao <T extends MasterModelBase> extends GeneralDao<T> implements IMasterBaseDao <T> {

	public MasterBaseDao(ApplicationContext context) {
		super(context);

	}
		
	
}
