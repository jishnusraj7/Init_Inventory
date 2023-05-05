package com.indocosmo.mrp.web.stock.war.dao;

import java.util.ArrayList;
import java.util.List;

import com.indocosmo.mrp.web.stock.war.model.StockWarModel;

public interface StockWarDao {
	
	public Double getLatestCostPrice(String columnName,Integer stockId,Integer depId) throws Exception ;
	public Double getcurrentStockPrice(Integer stockId) throws Exception;
	public List<StockWarModel> getWarDetails(Integer stockId, Integer departmentId) throws Exception;
	//public Double getWarRateNew(Double currentStockPrice, Double reqPrice)throws Exception;
	public Double getLatestCostPriceStock(String columnName, Integer stockId) throws Exception;
	public Double getcurrentWARPrice(Integer stockId) throws Exception ;
	public Integer getDeptId(String deptName) throws Exception;
	public Double getcurrentWARPrice(Integer stockId, Integer deptId) throws Exception;
	public Double getPurchaseRate(Integer stockId) throws Exception;
}
