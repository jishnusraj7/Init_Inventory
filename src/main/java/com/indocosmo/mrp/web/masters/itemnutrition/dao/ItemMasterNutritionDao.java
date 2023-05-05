package com.indocosmo.mrp.web.masters.itemnutrition.dao;

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
import com.indocosmo.mrp.web.masters.itemnutrition.model.ItemMasterNutrition;
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
public class ItemMasterNutritionDao extends GeneralDao<ItemMasterNutrition> implements IItemMasterNutritionDao {

	private CounterDao counterDao;
	private ItemMasterNutritionDao itemMasterNutritionDao;

	Connection conn_nutri = null;
	Statement stmt = null;
	ResultSet rs = null;

	public ItemMasterNutritionDao(ApplicationContext context) {
		super(context);
		counterDao = new CounterDao(getContext());
	}

	@Override
	public ItemMasterNutrition getNewModelInstance() {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	protected String getTable() {
		return "Dish";
	}

	public Connection ExecuteConnection() {
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Connect to database 1
			//conn_nutri = DriverManager.getConnection("jdbc:mysql://192.168.1.27:3306/jishnu_nutricare", "jishnu_hotel",	"posella123456^.");
			//conn_nutri = DriverManager.getConnection("jdbc:mysql://192.168.1.27:3306/jishnu_nutricare?user=jishnu_hotel&password=posella123456^.&tinyInt1isBit=false&useOldAliasMetadataBehavior=true&allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8");
			// Use the connections as needed...
			
			String url = "jdbc:mysql://192.168.1.27:3306/jishnu_nutricare";
            String user = "jishnu_hotel";
            String password = "posella123456^.";
            
            conn_nutri = DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close the connections when finished
			/*
			 * try { if (conn_nutri != null) { conn_nutri.close(); }
			 * 
			 * } catch (SQLException e) { e.printStackTrace(); }
			 */
		}
		return conn_nutri;
	}

	@Override
	public JsonArray getMastersRowJson() throws Exception {

		//final String sql = "SELECT DishID,DishName FROM " + getTable();

		Connection conn = ExecuteConnection();

		stmt = conn.createStatement();

		// Execute the query and get the result set
		//rs = stmt.executeQuery(sql);
		rs = stmt.executeQuery("SELECT DishID,DishName FROM dish");

		// Create a JSON factory and generator
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		ArrayNode json = mapper.createArrayNode();

		// Iterate over the result set and add each row to the JSON array
		while (rs.next()) {
			// Create an object node for the current row
			ObjectNode row = mapper.createObjectNode();

			// Add each column value to the object node
			row.put("DishId", rs.getInt("DishId"));
			row.put("DishName", rs.getString("DishName"));

			// Add the object node to the JSON array
			json.add(row);
		}
		
		String jsonString = mapper.writeValueAsString(json);
		
		JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(jsonString).getAsJsonArray();
        
		
		
		return jsonArray;
	}

	@Override
	public JsonArray getMastersRowJsonUOM() throws Exception {

		//final String sql = "SELECT DishID,DishName FROM " + getTable();

		Connection conn = ExecuteConnection();

		stmt = conn.createStatement();

		// Execute the query and get the result set
		//rs = stmt.executeQuery(sql);
		rs = stmt.executeQuery("SELECT UnitID,UnitName FROM nsysunit");

		// Create a JSON factory and generator
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		ArrayNode json = mapper.createArrayNode();

		// Iterate over the result set and add each row to the JSON array
		while (rs.next()) {
			// Create an object node for the current row
			ObjectNode row = mapper.createObjectNode();

			// Add each column value to the object node
			row.put("UnitID", rs.getInt("UnitID"));
			row.put("UnitName", rs.getString("UnitName"));

			// Add the object node to the JSON array
			json.add(row);
		}
		
		String jsonString = mapper.writeValueAsString(json);
		
		JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(jsonString).getAsJsonArray();
        
		
		
		return jsonArray;
	}
	
	public JsonArray GetIngredientDisplayList(int dishid) throws Exception {

		//final String sql = "SELECT DishID,DishName FROM " + getTable();

		Connection conn = ExecuteConnection();

		stmt = conn.createStatement();

		// Execute the query and get the result set
		
		rs = stmt.executeQuery("SELECT DISTINCT "
				+ "		i.IngredientName,"
				+ "		d.DishID,"
				+ "		di.IngredientID ,"
				+ "		di.Quantity, "
				+ "		i.ScrappageRate, "
				+ "		i.WeightChangeRate, "
				+ "		d.DishWeight, "
				+ "		d.StandardWeight, "
				+ "		d.StandardWeight1, "
				+ "		d.StandardWeight2, "
				+ "		d.Calorie, "
				+ "		d.Protien, "
				+ "		d.CarboHydrates, "
				+ "		d.FAT, "
				+ "		d.Fibre, "
				+ "		d.Iron, "
				+ "		d.Calcium, "
				+ "		d.Phosphorus, "
				+ "		d.VitaminARetinol, "
				+ "		d.VitaminABetaCarotene, "
				+ "		d.VitaminB12, "
				+ "		d.VitaminC, "
				+ "		su.StandardUnitName,"
				+ "		nsu.ServeUnitName, "
				+ "		d.ServeCount,"
				+ "		d.ServeCount1,"
				+ "		d.ServeCount2"
				+ "		 "
				+ "  FROM dish d "
				+ "  INNER JOIN dishingredient di ON di.DishID = d.DishID"
				+ "  INNER JOIN ingredient i ON i.IngredientID = di.IngredientID"
				+ "  LEFT JOIN standardunit su ON di.StandardUnitID = su.StandardUnitID"
				+ "  LEFT JOIN nsysserveunit nsu ON d.ServeUnit = nsu.ServeUnitID"
			    + "  WHERE d.DishID = " + dishid );

		// Create a JSON factory and generator
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		ArrayNode json = mapper.createArrayNode();

		// Iterate over the result set and add each row to the JSON array
		while (rs.next()) {
			// Create an object node for the current row
			ObjectNode row = mapper.createObjectNode();

			// Add each column value to the object node
			row.put("DishID", rs.getInt("DishID"));
			
			row.put("IngredientID", rs.getInt("IngredientID"));
			row.put("IngredientName", rs.getString("IngredientName"));
			row.put("Quantity", rs.getDouble("Quantity"));
			
			row.put("ScrappageRate", rs.getDouble("ScrappageRate"));
			row.put("WeightChangeRate", rs.getDouble("WeightChangeRate"));
			row.put("DishWeight", rs.getDouble("DishWeight"));
			row.put("StandardWeight", rs.getDouble("StandardWeight"));
			row.put("StandardWeight1", rs.getDouble("StandardWeight1"));
			row.put("StandardWeight2", rs.getDouble("StandardWeight2"));
			
			row.put("Calorie", rs.getDouble("Calorie"));
			row.put("Protien", rs.getDouble("Protien"));
			row.put("CarboHydrates", rs.getDouble("CarboHydrates"));
			row.put("FAT", rs.getDouble("FAT"));
			row.put("Fibre", rs.getDouble("Fibre"));
			row.put("Iron", rs.getDouble("Iron"));
			row.put("Calcium", rs.getDouble("Calcium"));
			row.put("Phosphorus", rs.getDouble("Phosphorus"));
			row.put("VitaminARetinol", rs.getDouble("VitaminARetinol"));
			row.put("VitaminABetaCarotene", rs.getDouble("VitaminABetaCarotene"));
			row.put("VitaminB12", rs.getDouble("VitaminB12"));
			row.put("VitaminC", rs.getDouble("VitaminC"));
			row.put("StandardUnitName", rs.getString("StandardUnitName"));
			row.put("ServeUnitName", rs.getString("ServeUnitName"));
			
			row.put("ServeCount", rs.getDouble("ServeCount"));
			row.put("ServeCount1", rs.getDouble("ServeCount1"));
			row.put("ServeCount2", rs.getDouble("ServeCount2"));

			// Add the object node to the JSON array
			json.add(row);
		}
		
		String jsonString = mapper.writeValueAsString(json);
		
		JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(jsonString).getAsJsonArray();
        
		
		
		return jsonArray;
	}
	
	
}
