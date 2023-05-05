package com.indocosmo.mrp.web.masters.supplier.dao;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.supplier.model.Supplier;

/**
 * @author jo
 *
 */
public class SupplierDao extends GeneralDao<Supplier> implements ISupplierDao {

	/**
	 * @param context
	 */
	public SupplierDao(ApplicationContext context) {
		super(context);

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getNewModelInstance()
	 */
	@Override
	public Supplier getNewModelInstance() {

		return new Supplier();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.BaseDao#getTable()
	 */
	@Override
	public String getTable() {

		return "mrp_supplier";
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.masters.supplier.dao.ISupplierDao#getIsSystemSupplier()
	 */
	@Override
	public Supplier getIsSystemSupplier() throws Exception {

		final String sql = "SELECT * "
							+ "FROM " 
							+  getTable()+" "
							+ "WHERE "
							+ "is_system=1 AND (IFNULL(is_deleted,0) = 0)";
		
		CachedRowSet resultSet;
		Supplier supplier = new Supplier();
		try {
			resultSet = dbHelper.executeSQLForRowset(sql);
			if (resultSet.next()) {
				supplier.setId(resultSet.getInt("id"));
				supplier.setCode(resultSet.getString("code"));
				supplier.setName(resultSet.getString("name"));
			}

		} catch (Exception e) {

			throw e;
		}

		return supplier;

	}

	/**
	 * @return
	 * @throws Exception
	 */
	public JsonArray getsupplierdataList() throws Exception {

		final String sql = "SELECT * FROM supplier  WHERE is_system=0 AND is_deleted=0";

		return getTableRowsAsJson(sql);
	}


	

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getMastersRowJson()
	 */
	@Override
	public JsonArray getMastersRowJson() throws Exception {
		
		final String sql = "SELECT id,code,name "
							+ "FROM " 
							+  getTable()+" "
							+ "WHERE "
							+ "(IFNULL(is_deleted,0) = 0) AND is_active=1 ";

		return getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.mrp.web.core.base.dao.GeneralDao#getTableRowsAsJson()
	 */

	@Override
	public JsonArray getTableRowsAsJson() throws Exception {
		
		final String sql = "SELECT * "
							+ "FROM "
							+  getTable()+" "
							+ "WHERE (IFNULL(is_system,0) = 0) AND  (IFNULL(is_deleted,0) = 0)";
		
		return super.getTableRowsAsJson(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.mrp.web.core.base.dao.GeneralDao#getList(java.lang.String)
	 */
	@Override
	public List<Supplier> getList(String where) throws Exception {
		
		String SQL = "SELECT * FROM " + getTable() + " WHERE id=" + where;

		return buildItemList(SQL);

	}

}
