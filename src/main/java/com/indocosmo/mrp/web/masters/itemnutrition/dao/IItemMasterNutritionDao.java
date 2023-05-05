package com.indocosmo.mrp.web.masters.itemnutrition.dao;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.masters.itemnutrition.model.ItemMasterNutrition;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;

public interface IItemMasterNutritionDao extends IGeneralDao<ItemMasterNutrition> {
	public JsonArray getMastersRowJson() throws Exception;

	public JsonArray getMastersRowJsonUOM() throws Exception;

	public JsonArray GetIngredientDisplayList(int dishid) throws Exception;
}
