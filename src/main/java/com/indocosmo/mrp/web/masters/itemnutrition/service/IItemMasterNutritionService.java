package com.indocosmo.mrp.web.masters.itemnutrition.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.itemnutrition.dao.ItemMasterNutritionDao;
import com.indocosmo.mrp.web.masters.itemnutrition.model.ItemMasterNutrition;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;

public interface IItemMasterNutritionService extends IGeneralService<ItemMasterNutrition, ItemMasterNutritionDao> {
	public JsonArray getMastersRowJson() throws Exception;

	public JsonArray getMastersRowJsonUOM() throws Exception;

	public JsonArray GetIngredientDisplayList(int dishid) throws Exception;
}
