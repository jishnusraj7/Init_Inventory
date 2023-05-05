package  com.indocosmo.mrp.web.report.profitsummary.dao;



import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.report.prodbomreport.model.ProdBomReportModel;
import com.indocosmo.mrp.web.report.profitsummary.model.ProfitSummaryModel;



public interface IProfitSummaryDao extends IGeneralDao<ProfitSummaryModel>{
	
	public JsonArray getProductionData(ProfitSummaryModel prod,HttpSession session) throws Exception;	
	
	
}
