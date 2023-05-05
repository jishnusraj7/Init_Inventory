package com.indocosmo.mrp.web.masters.itemnutritionlabel.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.itemnutritionlabel.model.ItemMasterNutritionLabel;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;

public interface IItemMasterNutritionLabelDao extends IGeneralDao<ItemMasterNutritionLabel> {
	public JsonArray GetDishList() throws Exception;
	public JsonArray ShowNutritionLabel(int dishid) throws Exception;
	public Float GetDishWeightById(int dishid) throws Exception;
}
