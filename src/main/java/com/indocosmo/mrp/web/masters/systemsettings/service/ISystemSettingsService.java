package com.indocosmo.mrp.web.masters.systemsettings.service;

import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.systemsettings.dao.SystemSettingsDao;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;



public interface ISystemSettingsService extends IGeneralService<SystemSettings,SystemSettingsDao>{

	public SystemSettings getSystemData() throws Exception;

}
