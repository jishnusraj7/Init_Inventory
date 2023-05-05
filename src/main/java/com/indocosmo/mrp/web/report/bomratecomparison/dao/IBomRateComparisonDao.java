package  com.indocosmo.mrp.web.report.bomratecomparison.dao;



import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.report.bomratecomparison.model.BomRateComparison;



public interface IBomRateComparisonDao extends IGeneralDao<BomRateComparison>{
	
	public JsonArray getBomRateData(BomRateComparison bomrate,HttpSession session) throws Exception;	
	//public JsonArray getProductionMonthData(ProdBomReportModel prod,HttpSession session) throws Exception;	
	
}
