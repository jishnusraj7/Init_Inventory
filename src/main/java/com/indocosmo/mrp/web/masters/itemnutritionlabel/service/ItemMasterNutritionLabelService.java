package com.indocosmo.mrp.web.masters.itemnutritionlabel.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.masters.department.dao.DepartmentDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemnutritionlabel.dao.ItemMasterNutritionLabelDao;
import com.indocosmo.mrp.web.masters.itemnutritionlabel.model.ItemMasterNutritionLabel;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;



public class ItemMasterNutritionLabelService extends GeneralService<ItemMasterNutritionLabel, ItemMasterNutritionLabelDao> implements
		IItemMasterNutritionLabelService {
	
	private ItemMasterNutritionLabelDao NutritionDao;
	
	public ItemMasterNutritionLabelService(ApplicationContext context) {
		super(context);
		NutritionDao = new ItemMasterNutritionLabelDao(getContext());
	}
	
	@Override
	public JsonArray GetDishList() throws Exception {
		// TODO Auto-generated method stub
		return NutritionDao.GetDishList();
	}
	
	@Override
	public JsonArray ShowNutritionLabel(int dishid) throws Exception {
		// TODO Auto-generated method stub
		return NutritionDao.ShowNutritionLabel(dishid);
	}

	@Override
	public ItemMasterNutritionLabelDao getDao() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Float GetDishWeightById(int dishid) throws Exception {
		// TODO Auto-generated method stub
		return NutritionDao.GetDishWeightById(dishid);
	}
}
