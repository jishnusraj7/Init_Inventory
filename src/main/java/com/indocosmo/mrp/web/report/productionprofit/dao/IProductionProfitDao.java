package  com.indocosmo.mrp.web.report.productionprofit.dao;



import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.report.prodbomreport.model.ProdBomReportModel;
import com.indocosmo.mrp.web.report.productionprofit.model.ProductionProfit;
import com.indocosmo.mrp.web.report.profitsummary.model.ProfitSummaryModel;



public interface IProductionProfitDao extends IGeneralDao<ProductionProfit>{
	
	public JsonArray getProductionData(ProductionProfit prod,HttpSession session) throws Exception;	
	
	
}
