package com.indocosmo.mrp.web.masters.supplier.dao;

import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.supplier.model.Supplier;


public interface ISupplierDao extends IGeneralDao<Supplier> {

	public Supplier getIsSystemSupplier() throws Exception;


}
