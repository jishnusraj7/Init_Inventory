package com.indocosmo.mrp.web.masters.saleitemcombocontents.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.model.SaleItemComboContents;

public interface ISaleItemComboContentsDao extends IGeneralDao<SaleItemComboContents>{
	public JsonArray getComboContentData(Integer itemid) throws Exception ;
}
