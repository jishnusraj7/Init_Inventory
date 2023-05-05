package com.indocosmo.mrp.web.masters.systemsettings.dao;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.currencydetails.dao.CurrencyDetailsDao;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;

public class SystemSettingsDao extends GeneralDao<SystemSettings> implements ISystemSettingsDao {
	
	private CurrencyDetailsDao currencyDetailsDao;
	
	public SystemSettingsDao(ApplicationContext context) {
	
		super(context);
		currencyDetailsDao = new CurrencyDetailsDao(getContext());
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance() */
	@Override
	public SystemSettings getNewModelInstance() {
	
		return new SystemSettings();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable() */
	@Override
	public String getTable() {
	
		return "system_params";
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.masters.systemsettings.dao.ISystemSettingsDao#
	 * getSystemData() */
	@Override
	public SystemSettings getSystemData() throws Exception {
	
		final String sql = "SELECT " + "* " + "FROM " + getTable() + " " + "WHERE "
				+ "(IFNULL(is_deleted,0) = 0)";
		
		CachedRowSet resultSet;
		SystemSettings settings = new SystemSettings();
		
		try {
			resultSet = dbHelper.executeSQLForRowset(sql);
			if (resultSet.next()) {
				
				settings.setId(resultSet.getInt("id"));
				settings.setDate_format(resultSet.getString("date_format"));
				settings.setDate_separator(resultSet.getString("date_separator"));
				settings.setCurrencySymbol(currencyDetailsDao.getCurrentCurrency());
				settings.setWeek_start(resultSet.getInt("week_start"));
				settings.setDecimal_places(resultSet.getInt("decimal_places"));
				settings.setTime_format(resultSet.getString("time_format"));
			}
		}
		catch (SQLException e) {
			
			throw e;
		}
		
		return settings;
		
	}
	
}
