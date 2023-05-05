package com.indocosmo.mrp.web.masters.itemnutritionlabel.service;

import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.service.IGeneralService;
import com.indocosmo.mrp.web.masters.itemnutritionlabel.dao.ItemMasterNutritionLabelDao;
import com.indocosmo.mrp.web.masters.itemnutritionlabel.model.ItemMasterNutritionLabel;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;

public interface IItemMasterNutritionLabelService extends IGeneralService<ItemMasterNutritionLabel, ItemMasterNutritionLabelDao> {
	public JsonArray GetDishList() throws Exception;
	public JsonArray ShowNutritionLabel(int dishid) throws Exception;
	public Float GetDishWeightById(int dishid) throws Exception;
}
