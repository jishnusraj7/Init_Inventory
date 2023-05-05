package com.indocosmo.mrp.web.masters.itemnutritionlabel.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.sql.rowset.CachedRowSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.utils.core.ReflectionUtil;
import com.indocosmo.mrp.utils.core.dbutils.DBUtil;
import com.indocosmo.mrp.utils.core.persistence.annotation.Counter;
import com.indocosmo.mrp.utils.core.persistence.annotation.Id;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.Hq;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.ImplementationMode;
import com.indocosmo.mrp.utils.core.persistence.enums.GenerationType;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.core.counter.dao.CounterDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.department.dao.DepartmentDao;
import com.indocosmo.mrp.web.masters.itemnutrition.dao.IItemMasterNutritionDao;
import com.indocosmo.mrp.web.masters.itemnutritionlabel.model.ItemMasterNutritionLabel;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * @author jo
 *
 */
public class ItemMasterNutritionLabelDao extends GeneralDao<ItemMasterNutritionLabel> implements IItemMasterNutritionLabelDao {

	private CounterDao counterDao;
	private ItemMasterNutritionLabelDao itemMasterNutritionDao;


	public ItemMasterNutritionLabelDao(ApplicationContext context) {
		super(context);
		counterDao = new CounterDao(getContext());
	}

	@Override
	public ItemMasterNutritionLabel getNewModelInstance() {
		// TODO Auto-generated method stub

		return null;
	}

	
	@Override
	public JsonArray GetDishList() throws Exception {

		final String sql = "SELECT id,name FROM stock_items WHERE dish_weight > 0";

		return getTableRowsAsJson(sql);
	}
	
	@Override
	public JsonArray ShowNutritionLabel(int dishid) throws Exception {

		final String sql = " SELECT (@row_number:=@row_number + 1) AS SlNo "
				+ ", NL.NutrientID "
				+ ", NL.NutrientParam "
				+ ", NLB.NutrientValue "
				+ ", NL.NutrientUnit "
				+ " FROM nutri_nutrientslabel NLB "
				+ " INNER JOIN nutri_nutrientlist NL ON NL.NutrientID = NLB.NutrientID "
				+ " WHERE NLB.IngredientID = "+ dishid +" "
				+ " AND NL.NutrientParam "
				+ " IN ('Calorie','Carbohydrates', 'Fat', 'Protien' ,'Fibre' ,'Iron','Calcium','Phosphorus','VitaminABetaCarotene','VitaminARetinol','VitaminB12','VitaminC' )"
				+ " ;";

		return getTableRowsAsJson(sql);
	}

	@Override
	protected String getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	public Float GetDishWeightById(int dishid) {
		
		final String sql = "SELECT dish_weight FROM stock_items WHERE id ="+ dishid +"";

		Float Dish_Weight = (float) 0;
		
		JsonArray DishWeight;
		try {
			DishWeight = getTableRowsAsJson(sql);
			
			for (JsonElement jsonElementNVL : DishWeight) {
				JsonObject jsonObjectNVL = jsonElementNVL.getAsJsonObject();
				Dish_Weight = jsonObjectNVL.get("dish_weight").getAsFloat();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Dish_Weight;
	}


}
