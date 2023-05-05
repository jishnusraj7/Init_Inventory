package com.indocosmo.mrp.web.masters.prodcost.service;



import java.util.List;

import com.indocosmo.mrp.web.core.base.service.IMasterBaseService;
import com.indocosmo.mrp.web.masters.prodcost.dao.ProductionCostDao;
import com.indocosmo.mrp.web.masters.prodcost.model.ProductionCost;


public interface IProductionCostService extends IMasterBaseService<ProductionCost,ProductionCostDao>{
	
	
	public List<ProductionCost> getProdCostData() throws Exception;
	public List<ProductionCost> getExcelData() throws Exception;
	
}
