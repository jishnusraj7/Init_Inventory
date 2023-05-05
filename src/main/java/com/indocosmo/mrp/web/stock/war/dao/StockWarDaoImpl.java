package com.indocosmo.mrp.web.stock.war.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.rowset.CachedRowSet;

import org.springframework.stereotype.Repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockOutStatus;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.dao.GeneralDao;
import com.indocosmo.mrp.web.masters.datatables.DataTableColumns;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias.ColumnCriterias;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias.OrderCriterias;
import com.indocosmo.mrp.web.masters.datatables.DataTableCriterias.SearchCriterias;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.model.StockInDetail;
import com.indocosmo.mrp.web.stock.stockout.model.StockOut;
import com.indocosmo.mrp.web.stock.war.model.StockWarModel;
//@Repository
public class StockWarDaoImpl extends GeneralDao<StockWarModel> implements StockWarDao {

	public StockWarDaoImpl(ApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public StockWarModel getNewModelInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getTable() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Double getLatestCostPrice(String columnName,Integer stockId,Integer depId) throws Exception {
		final String sql = "SELECT dtl."+columnName+ " FROM mrp_stock_register_dtl dtl "+ 
				"JOIN mrp_stock_register_hdr hdr  ON dtl.stock_register_hdr_id=hdr.id WHERE dtl.in_qty>0.00 and dtl.department_id="+depId+
				" AND dtl.stock_item_id="+stockId+" AND  dtl.cost_price>0.00 ORDER BY hdr.created_at DESC LIMIT 1";
		Double costPrice = 0.00;
		CachedRowSet resultSet;
		beginTrans();
		try {
			System.out.println("sql================>"+sql);
			resultSet = dbHelper.executeSQLForRowset(sql);
			if (resultSet.next()) {
				costPrice=resultSet.getDouble(columnName);
				System.out.println("cost_price================>"+resultSet.getDouble(columnName));
			}
			//costPrice = (double) executeSQLForRowset(sql);
			System.out.println("costPrice================>"+costPrice);
			endTrans();
		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}

		return costPrice;
	}
	@Override
	public Double getcurrentStockPrice(Integer stockId) throws Exception  {
		final String sql = "SELECT unit_price FROM stock_items WHERE id ="+stockId;
		Double currentStockPrice = 0.00;
		CachedRowSet resultSet;
		beginTrans();
		try {
			System.out.println("sql================>"+sql);
			resultSet = dbHelper.executeSQLForRowset(sql);
			if (resultSet.next()) {
				currentStockPrice=resultSet.getDouble("unit_price");
				System.out.println("unit_price================>"+resultSet.getDouble("unit_price"));
			}
			System.out.println("costPrice================>"+currentStockPrice);
			endTrans();
		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}

		return currentStockPrice;
		}
	
	@Override
	public Double getcurrentWARPrice(Integer stockId) throws Exception  {
		final String sql = "SELECT war_rate FROM mrp_dept_stock_item WHERE stock_id ="+stockId+" and dept_id=2 ";
		Double currentStockPrice = 0.00;
		CachedRowSet resultSet;
		beginTrans();
		try {
			//System.out.println("sql================>"+sql);
			resultSet = dbHelper.executeSQLForRowset(sql);
			if (resultSet.next()) {
				currentStockPrice=resultSet.getDouble("war_rate");
				//System.out.println("war_rate================>"+resultSet.getDouble("war_rate"));
			}
			System.out.println("war_rate================>"+currentStockPrice);
			endTrans();
		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}

		return currentStockPrice;
		}
	
	@Override
	public Double getPurchaseRate(Integer stockId) throws Exception  {
		final String sql = "SELECT unit_price FROM mrp_stock_in_dtl dtl INNER JOIN mrp_stock_in_hdr hdr ON dtl.stock_in_hdr_id = hdr.id WHERE dtl.stock_item_id="+stockId+" AND department_id=2 ORDER BY last_sync_at DESC LIMIT 1 ";
		Double purchaseRate = 0.00;
		CachedRowSet resultSet;
		beginTrans();
		try {
			//System.out.println("sql================>"+sql);
			resultSet = dbHelper.executeSQLForRowset(sql);
			if (resultSet.next()) {
				purchaseRate=resultSet.getDouble("unit_price");
				//System.out.println("war_rate================>"+resultSet.getDouble("war_rate"));
			}
			System.out.println("purchase rate================>"+purchaseRate);
			endTrans();
		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}

		return purchaseRate;
		}

	public  List<StockWarModel> getWarDetails(Integer stockId, Integer departmentId) throws Exception {
		
		final String sql = "SELECT * FROM mrp_dept_stock_item WHERE dept_id="+departmentId+" AND stock_id="+stockId+"  ORDER BY created_date";
		
		List<StockWarModel> stockWarModels=new ArrayList<StockWarModel>();
		CachedRowSet resultSet;
		beginTrans();
		try {
			System.out.println("sql================>"+sql);
			resultSet = dbHelper.executeSQLForRowset(sql);
			if (resultSet.next()) {
				StockWarModel stockWarModel = new StockWarModel();
				stockWarModel.setDeptStockId(resultSet.getInt("dept_stock_item_id"));
				stockWarModel.setDeptId(resultSet.getInt("dept_id"));
				stockWarModel.setStockId(resultSet.getInt("stock_id"));
				stockWarModel.setPreviousRate(resultSet.getDouble("previous_rate"));
				stockWarModel.setPreviousQty(resultSet.getDouble("previous_qty"));
				stockWarModel.setInRate(resultSet.getDouble("in_rate"));
				stockWarModel.setInQty(resultSet.getDouble("in_qty"));
				stockWarModel.setWarRate(resultSet.getDouble("war_rate"));
				stockWarModel.setCreatedDate(String.valueOf(resultSet.getDate("created_date")));
			//	stockWarModel.setUpdatedDdate(String.valueOf(resultSet.getDate("updated_date"))!=null?String.valueOf(resultSet.getDate("updated_date")):"");	
				//stockWarModel.setUpdatedDdate(String.valueOf(resultSet.getDate("updated_date")));
				stockWarModel.setIsDeleted(resultSet.getInt("is_deleted"));
				stockWarModels.add(stockWarModel);
			}
			
			endTrans();
		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}
		
		return stockWarModels;
	}

	@Override
	public Double getLatestCostPriceStock(String columnName, Integer stockId) throws Exception {
		
		String sql="SELECT unit_price FROM stock_items WHERE id="+stockId+";";
		// TODO Auto-generated method stub
				Double costPrice = 0.00;
				CachedRowSet resultSet;
				beginTrans();
				try {
					System.out.println("sql================>"+sql);
					resultSet = dbHelper.executeSQLForRowset(sql);
					if (resultSet.next()) {
						costPrice=resultSet.getDouble(columnName);
						System.out.println("cost_price================>"+resultSet.getDouble(columnName));
					}
					//costPrice = (double) executeSQLForRowset(sql);
					System.out.println("costPrice================>"+costPrice);
					endTrans();
				} catch (Exception e) {

					rollbackTrans();
					throw e;
				}

				return costPrice;
		
		
	}
		
	@Override
	public Integer getDeptId(String deptName) throws Exception{
		
		String sql="SELECT id FROM mrp_department WHERE name='"+deptName+"';";
		// TODO Auto-generated method stub
		Integer deptId = 0;
				CachedRowSet resultSet;
				beginTrans();
				try {
					System.out.println("sql================>"+sql);
					resultSet = dbHelper.executeSQLForRowset(sql);
					if (resultSet.next()) {
						deptId=resultSet.getInt("id");
						System.out.println("deptId================>"+resultSet.getInt("id"));
					}
					//costPrice = (double) executeSQLForRowset(sql);
					System.out.println("deptId================>"+deptId);
					endTrans();
				} catch (Exception e) {

					rollbackTrans();
					throw e;
				}

				return deptId;
		
		
	}
	
	@Override
	public Double getcurrentWARPrice(Integer stockId, Integer deptId) throws Exception  {
		final String sql = "SELECT war_rate FROM mrp_dept_stock_item WHERE stock_id ="+stockId+" and dept_id="+deptId+";";
		Double currentStockPrice = 0.00;
		CachedRowSet resultSet;
		beginTrans();
		try {
			//System.out.println("sql================>"+sql);
			resultSet = dbHelper.executeSQLForRowset(sql);
			if (resultSet.next()) {
				currentStockPrice=resultSet.getDouble("war_rate");
				//System.out.println("war_rate================>"+resultSet.getDouble("war_rate"));
			}
			System.out.println("war_rate================>"+currentStockPrice);
			endTrans();
		} catch (Exception e) {

			rollbackTrans();
			throw e;
		}

		return currentStockPrice;
		}
	/*public Double getWarRateNew(Double currentStockPrice, Double reqPrice)throws Exception  {
		// TODO Auto-generated method stub
		return null;
	}*/
}
