package com.indocosmo.mrp.web.report.bomratecomparison.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.report.bomratecomparison.dao.BomRateComparisonDao;
import com.indocosmo.mrp.web.report.bomratecomparison.model.BomRateComparison;
import com.indocosmo.mrp.web.report.bomratecomparison.service.BomRateComparisonService;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.stock.war.service.StockWarServiceImpl;

@Controller
@RequestMapping(value = "/bomratecomparison")
public class BomRateComparisonController extends ViewController<BomRateComparison, BomRateComparisonDao, BomRateComparisonService> {
	
	public static final Logger logger = Logger.getLogger(BomRateComparisonController.class);
	
	@Override
	public BomRateComparisonService getService() {
	
		return new BomRateComparisonService(getCurrentContext());
	}
	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute BomRateComparison prod , HttpSession session , Model model) throws Exception {
		model.addAttribute("Report",true);
		
		return "/report/production/list";
	}
	
	@RequestMapping(value = "/BomRateComparisonReport")
	public ModelAndView getBomRateComparison(@ModelAttribute BomRateComparison bomRate , HttpServletRequest request ,
			Model model , HttpServletResponse response , HttpSession session) throws Exception 
	{
		String itemcategory = request.getParameter("itemcategory");
		String[] stockitem = request.getParameterValues("stockitem");
		String stockitemList = String.join(",", stockitem);
		
		bomRate.setItemcategory(itemcategory);
		bomRate.setStockitem(stockitemList);
		final StockWarServiceImpl stockWarService = new StockWarServiceImpl(getCurrentContext());
		final BomRateComparisonDao bomRateDao = new BomRateComparisonDao(getCurrentContext());
		
		//JsonArray bomRateData = null;
		/*JsonArray bomRateList = new JsonArray();
		bomRateList.add(bomRateDao.getBomRateData(bomRate, session));*/
		
		//JsonArray bomRateData = null;
				JsonArray array = new JsonArray();
				array.add(bomRateDao.getBomRateData(bomRate, session));
				
				JsonArray bomRateList = new JsonArray();
				JsonArray bomRateListTemp = new JsonArray();
				
				for (int i = 0; i < array.size(); i++) {
					JsonArray ratedataArray = (JsonArray) array.get(i);
					for (int j = 0; j < ratedataArray.size(); j++) {
						JsonObject ratedata = (JsonObject) ratedataArray.get(j);
						Double currWarPrice = stockWarService.getcurrentWARPrice(ratedata.get("id").getAsInt());
						Double purchaseRate = stockWarService.getPurchaseRate(ratedata.get("id").getAsInt());
						if(currWarPrice.equals(0.00)){
							currWarPrice=ratedata.get("bom_rate").getAsDouble();
						}
						Double checkValue=0.00;
						if(purchaseRate == 0.00){
							 checkValue = currWarPrice - ratedata.get("purchase_rate").getAsDouble();
						}else{
							 checkValue = currWarPrice - purchaseRate;
						}
						
						
						Double diffValue = Math.abs(checkValue);
						ratedata.addProperty("new_bom_rate", currWarPrice.toString());
						ratedata.addProperty("new_diff_value", diffValue.toString());
						ratedata.addProperty("new_purchase_rate", purchaseRate.toString());
						bomRateListTemp.add(ratedata);
					}
					
					bomRateList.add(bomRateListTemp);
					
				} 
				System.out.println("Bom Array List=====================>"+bomRateList.toString());
		
		try {
			
			CompanyProfile company = (CompanyProfile) session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
			
			Report report = new Report();
			report.setReportName("BOM RATE COMPARISON REPORT");
			report.setCurrUserName(user.getName());
			report.setReportType(1);
			report.setDateFormat(dateFormat);
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			/*report.setCompanyName("Central Hotel");
			report.setCompanyAddress("Thrissur, Kerala, India");*/
			model.addAttribute("reportName", report);
			model.addAttribute("bomrate", bomRate);
			
		}
		catch (Exception e) {
			
			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
			
		}

		if(bomRate.getPdfExcel().equals("pdf")){
			return new ModelAndView("bomRateComparisonView", "bomRateComparisonView", bomRateList);
		}
			
		else{
			return new ModelAndView("bomRateComparisonExcelView", "bomRateComparisonExcelView", bomRateList);
		}
		
	}
	
}
