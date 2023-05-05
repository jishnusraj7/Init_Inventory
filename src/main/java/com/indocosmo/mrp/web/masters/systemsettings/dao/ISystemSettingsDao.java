package com.indocosmo.mrp.web.masters.systemsettings.dao;

import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;




public interface ISystemSettingsDao extends IGeneralDao<SystemSettings>{

	 public SystemSettings getSystemData() throws Exception;

}
