package com.indocosmo.mrp.web.masters.itemnutrition.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.department.dao.DepartmentDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemnutrition.dao.ItemMasterNutritionDao;
import com.indocosmo.mrp.web.masters.itemnutrition.model.ItemMasterNutrition;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;



public class ItemMasterNutritionService extends GeneralService<ItemMasterNutrition, ItemMasterNutritionDao> implements
		IItemMasterNutritionService {
	
	private ItemMasterNutritionDao NutritionDao;
	
	public ItemMasterNutritionService(ApplicationContext context) {
		super(context);
		NutritionDao = new ItemMasterNutritionDao(getContext());
	}

	@Override
	public ItemMasterNutritionDao getDao() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public JsonArray getMastersRowJson() throws Exception {
		return NutritionDao.getMastersRowJson();
	}
	
	public JsonArray getMastersRowJsonUOM() throws Exception {
		return NutritionDao.getMastersRowJsonUOM();
	}
	
	public JsonArray GetIngredientDisplayList(int dishid) throws Exception {
		return NutritionDao.GetIngredientDisplayList(dishid);
	}

}
