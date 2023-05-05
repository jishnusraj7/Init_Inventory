package com.indocosmo.mrp.web.masters.systemsettings.service;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.systemsettings.dao.SystemSettingsDao;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;

/**
 * @author jo
 *
 */
public class SystemSettingsService extends GeneralService<SystemSettings, SystemSettingsDao> implements
		ISystemSettingsService {
	
	private SystemSettingsDao systemsettingsDao;
	
	/**
	 * @param context
	 */
	public SystemSettingsService(ApplicationContext context) {
	
		super(context);
		systemsettingsDao = new SystemSettingsDao(getContext());
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.systemsettings.service.ISystemSettingsService
	 * #getSystemData() */
	@Override
	public SystemSettings getSystemData() throws Exception {
	
		return systemsettingsDao.getSystemData();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.service.IBaseService#getDao() */
	@Override
	public SystemSettingsDao getDao() {
	
		// TODO Auto-generated method stub
		return systemsettingsDao;
	}
	
	/**
	 * @param systemSettings
	 * @return
	 * @throws Exception
	 */
	public SystemSettings saveItem(SystemSettings systemSettings) throws Exception {
	
		systemsettingsDao.save(systemSettings);
		
		return systemSettings;
	}
	
}
