package com.indocosmo.mrp.web.masters.prodcost.dao;

import java.util.List;

import com.indocosmo.mrp.web.core.base.dao.IMasterBaseDao;
import com.indocosmo.mrp.web.masters.prodcost.model.ProductionCost;


public interface IProductionCostDao extends IMasterBaseDao<ProductionCost>{

	public List<ProductionCost> getProdCostData() throws Exception;
	public List<ProductionCost> getExcelData() throws Exception;
}
