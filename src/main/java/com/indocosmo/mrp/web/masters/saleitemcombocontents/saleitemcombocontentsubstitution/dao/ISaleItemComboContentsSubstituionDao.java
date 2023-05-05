package com.indocosmo.mrp.web.masters.saleitemcombocontents.saleitemcombocontentsubstitution.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.saleitemcombocontents.saleitemcombocontentsubstitution.model.SaleItemComboContentsSubstituion;

public interface ISaleItemComboContentsSubstituionDao extends IGeneralDao<SaleItemComboContentsSubstituion>{
	public JsonArray getComboContentList(String combocontent) throws Exception;

}
