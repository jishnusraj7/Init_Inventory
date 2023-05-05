package com.indocosmo.mrp.web.masters.combocontents.dao;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IMasterBaseDao;
import com.indocosmo.mrp.web.masters.combocontents.model.ComboContents;

public interface IComboContentsDao extends IMasterBaseDao<ComboContents>{
	public JsonArray getComboContentData(Integer itemid) throws Exception ;
}
