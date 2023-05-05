package com.indocosmo.mrp.web.stock.war.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.indocosmo.mrp.utils.war.WarUtils;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.service.GeneralService;
import com.indocosmo.mrp.web.stock.stockin.dao.StockInDao;
import com.indocosmo.mrp.web.stock.war.dao.StockWarDao;
import com.indocosmo.mrp.web.stock.war.dao.StockWarDaoImpl;
import com.indocosmo.mrp.web.stock.war.model.StockWarModel;
//@Component 
//@Service
public class StockWarServiceImpl  extends GeneralService<StockWarModel,StockWarDaoImpl> implements StockWarService{
	
	private StockWarDaoImpl stockWarDao ;
	public StockWarServiceImpl(ApplicationContext context) {
		super(context);
		stockWarDao=new StockWarDaoImpl(getContext());
	}
	
	@Autowired
	private WarUtils warUtils;
	
	
	
	public void saveWARSaleItem (Double inQty,Double inQtyRate, Double previousQty,Double priviousQtyRate) throws Exception{
	
		try {
			//warUtils.getWarRate(inQty, inQtyRate, previousQty, priviousQtyRate);
			StockWarModel stockWarModel=null;
			stockWarModel.setWarRate(warUtils.getWarRate(inQty, inQtyRate, previousQty, priviousQtyRate));
		//	stockWarDao.save(stockWarModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Double getLatestCostPrice(String columnName,Integer stockId,Integer deptId) throws Exception{

		System.out.println("stockId=============>"+stockId);
		System.out.println("deptId=============>"+deptId);
		return stockWarDao.getLatestCostPrice(columnName,stockId,deptId);
		
	}
	
public StockWarModel saveStockItem(StockWarModel stockWarModel) throws Exception {
		
	//stockWarDao.save(stockWarModel);		
		return stockWarModel;
	}

@Override
public StockWarDaoImpl getDao() {
	// TODO Auto-generated method stub
	return stockWarDao;
}

@Override
public Double getcurrentStockPrice(Integer stockId) throws Exception  {
	// TODO Auto-generated method stub
	return stockWarDao.getcurrentStockPrice(stockId);
}

@Override
public Double getcurrentWARPrice(Integer stockId) throws Exception  {
	// TODO Auto-generated method stub
	return stockWarDao.getcurrentWARPrice(stockId);
}
@Override
public Double getPurchaseRate(Integer stockId) throws Exception  {
	// TODO Auto-generated method stub
	return stockWarDao.getPurchaseRate(stockId);
}



@Override
public Double getcurrentWARPrice(Integer stockId, Integer deptId) throws Exception  {
	// TODO Auto-generated method stub
	return stockWarDao.getcurrentWARPrice(stockId,deptId);
}


public Double getWarRateNew(Double currentStockPrice, Double reqPrice) throws Exception {
	// TODO Auto-generated method stub
	//return stockWarDao.getWarRateNew(currentStockPrice,reqPrice);
	return warUtils.getWarRateNew(currentStockPrice,reqPrice);
}

public Double getWarRateExisting(Double inQty, Double inRate, Double previousQty, Double previousRate) throws Exception {
	
	return warUtils.getWarRate(inQty, inRate, previousQty, previousRate);
}

public List<StockWarModel> getWarDetails(Integer stockId, Integer departmentId) throws Exception {
	
	List<StockWarModel> stockWarModel=new ArrayList<StockWarModel>();

	stockWarModel= stockWarDao.getWarDetails(stockId,departmentId);
		return stockWarModel;
	
	
	
}

public Double getLatestCostPriceStock(String columnName, Integer stockId) throws Exception {
	
	System.out.println("srock table service stockId=============>"+stockId);

	return stockWarDao.getLatestCostPriceStock(columnName,stockId);
}

@Override
public Integer getDeptId(String deptName) throws Exception{
	
	return stockWarDao.getDeptId(deptName);
	
}

}
