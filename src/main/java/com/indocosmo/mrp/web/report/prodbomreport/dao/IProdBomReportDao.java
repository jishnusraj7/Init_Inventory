package  com.indocosmo.mrp.web.report.prodbomreport.dao;



import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.indocosmo.mrp.web.core.base.dao.IGeneralDao;
import com.indocosmo.mrp.web.report.prodbomreport.model.ProdBomReportModel;



public interface IProdBomReportDao extends IGeneralDao<ProdBomReportModel>{
	
	public JsonArray getProductionData(ProdBomReportModel prod,HttpSession session) throws Exception;	
	public JsonArray getProductionMonthData(ProdBomReportModel prod,HttpSession session) throws Exception;	
	
}
