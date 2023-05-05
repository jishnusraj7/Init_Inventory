package com.indocosmo.mrp.web.stock.war.service;

import java.util.ArrayList;
import java.util.List;

import com.indocosmo.mrp.web.stock.war.model.StockWarModel;

public interface StockWarService {
	public void saveWARSaleItem (Double inQty,Double inQtyRate, Double previousQty,Double priviousQtyRate) throws Exception;
	public Double getcurrentStockPrice(Integer stockId) throws Exception;
	public Double getWarRateNew(Double currentStockPrice, Double reqPrice) throws Exception;
	public Double getWarRateExisting(Double inQty, Double inRate, Double previousQty, Double previousRate) throws Exception;
	public List<StockWarModel> getWarDetails(Integer stockId, Integer departmentId) throws Exception ;
	//public Double getLatestCostPrice(Integer stockId,Integer deptId) throws Exception;
	public Double getcurrentWARPrice(Integer stockId) throws Exception  ;
	public Integer getDeptId(String DeptName) throws Exception;
	public Double getcurrentWARPrice(Integer stockId, Integer deptId) throws Exception;
	public Double getPurchaseRate(Integer stockId) throws Exception;

}
