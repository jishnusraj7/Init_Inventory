package com.indocosmo.mrp.web.masters.combocontents.ComboContentSubstitution.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.combocontents.ComboContentSubstitution.model.ComboContentsSubstituion;

public interface IComboContentsSubstituionDao extends IGeneralDao<ComboContentsSubstituion>{
	public JsonArray getComboContentList(String combocontent) throws Exception;

}
