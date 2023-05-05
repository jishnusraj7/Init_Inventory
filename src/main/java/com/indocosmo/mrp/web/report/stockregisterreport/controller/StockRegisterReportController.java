package com.indocosmo.mrp.web.report.stockregisterreport.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stockregisterreport.dao.StockRegisterReportDao;
import com.indocosmo.mrp.web.report.stockregisterreport.model.StockRegisterReport;
import com.indocosmo.mrp.web.report.stockregisterreport.service.StockRegisterReportService;
import com.indocosmo.mrp.web.stock.stockdisposal.controller.StockDisposalController;

@Controller
@RequestMapping(value="/stockregisterreport")
public class StockRegisterReportController extends ViewController<StockRegisterReport,StockRegisterReportDao,StockRegisterReportService>{
	public static final Logger logger=Logger.getLogger(StockDisposalController.class);


	@Override
	public StockRegisterReportService getService() {

		return new StockRegisterReportService(getCurrentContext());
	}

	@RequestMapping(value = "stockregisterReport")
	public String getitemstock(@ModelAttribute StockRegisterReport stockregisterReport)
			throws Exception {

		return "report/form";
	}

	/*public String getwhereValues(StockRegisterReport stockregisterReport) throws Exception {

		final StockRegisterReportService stockregisterReportService = new StockRegisterReportService(getCurrentContext());
		
		java.util.List<StockRegisterReport> stocklist=null;
		Integer option = stockregisterReport.getOption();
		String start = "";
		String end = "";
		
		
		Integer dept = stockregisterReport.getDepartment_id();
		String itmctgry = stockregisterReport.getItem_category_id();
		
		String stockids = "";
		Integer stock_item_id;
		String stockitemIds = "";
		if(stockregisterReport.getStock_item_id()==0){
	 stock_item_id = 0;
				
		}else{
			stock_item_id = stockregisterReport.getStock_item_id();
		}
		
		
		if(stock_item_id == null && stock_item_id == 0){
			java.util.List<ItemMaster> category = stockregisterReportService.getItembyCategory(itmctgry);

		if(category.size() !=0){
			for(int i=0; i<category.size(); i++){
              if (category.get(i).getId() != null) {
            	  stockids += ((stockids.isEmpty()) ? "" : ",")	+ category.get(i).getId();
              }
			}
		}}String itemid = "";
		if(stock_item_id != null && stock_item_id != 0){
			itemid = stock_item_id.toString();
		}else{
			itemid = "";
		}
		Integer trans_type = stockregisterReport.getTrans_type();
		String getAnd = "";
		String getAnddept = "";
		String getAndTrnstype = "";
		String andCondition = "";
		String where = "";
		
		if(dept != 0){
			
			getAnddept = " AND  department_id = '"+dept+"'";
		}else{
		
			getAnddept = "";
		}
		
        if(stockids != ""){
        	if(itemid != ""){
        		getAnd = " AND  stock_item_id IN ("+stockids+","+itemid+")";
        	}else{
        		getAnd = " AND  stock_item_id IN ("+stockids+")";
        	}
		}
        
        if(stockids == ""  ){
        	if(itemid == ""){
        		getAnd = "";
        	}else{
        		getAnd = " AND  stock_item_id IN ("+itemid+")";
        	}
		}
        if(trans_type != null && trans_type != 0 ){
        	getAndTrnstype = " AND  trans_type = '"+trans_type+"'";
        	
		}
        
        andCondition = getAnddept+getAndTrnstype+getAnd;
        start = stockregisterReport.getStartdate();
    	end = stockregisterReport.getEnddate();
        if(option == 1){
        	
        	where = "where txn_date BETWEEN '"+start+"' AND '"+end+"' " +andCondition+"  GROUP BY stock_item_name,txn_date ORDER BY stock_item_name,txn_date";
        }
        if(option == 0){
        	//startdate = stockregisterReport.getStartmonth();
        	//enddate = stockregisterReport.getEndmonth();
        	//where = "where MONTH(txn_date) BETWEEN '"+start+"' AND '"+end+"' " +andCondition+"  GROUP BY stock_item_name ORDER BY stock_item_name,MONTH(txn_date)";
        	where=stockregisterReport.getEnddate();
        }
		
		return where;
	}*/

	/*@RequestMapping("isreport")
	public void getReportList(@ModelAttribute StockRegisterReport stkregrpt,HttpServletRequest request,HttpServletResponse response) throws Exception{

		final StockRegisterReportService stockregisterReportService = new StockRegisterReportService(getCurrentContext());
		String where = getwhereValues(stkregrpt);
		String startdate = stkregrpt.getStartdate();
		Boolean isDataAvailable = stockregisterReportService.getreportDetails(where);

		if(isDataAvailable == true){
			response.getWriter().print("1");
		}else{
			response.getWriter().print("0");
		}
	}*/

	@RequestMapping(value="/Stock Register")
	public ModelAndView PrintInvoice(@ModelAttribute StockRegisterReport stockregisterReport,Model model,
			HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception 
	{	
		try{
			final StockRegisterReportService stockregisterReportService = new StockRegisterReportService(getCurrentContext());

			CompanyProfile company = (CompanyProfile)session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			Users user= (Users)session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			final SystemSettings systemSettings = (SystemSettings) session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat(); 

			//String startdate = stockregisterReport.getStartdate();
			Integer option = stockregisterReport.getOption();
			//String where = getwhereValues(stockregisterReport);

			Report report=new Report();
			report.setDocType(option);
			
			if(stockregisterReport.getOption()==1){
				report.setReportName("STOCK REGISTER REPORT");
			}
			else{
				report.setReportName("STOCK REGISTER MONTHLY REPORT");

			}
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);
			report.setReportType(1);
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			model.addAttribute("reportName", report);

			stockregisterReport.setStockRegister(stockregisterReportService.getReportResult(stockregisterReport));
			if(stockregisterReport.getOption()==1)
			{
				Date startDate1 = formatter.parse(stockregisterReport.getStartdate());
				Date endDate=formatter.parse(stockregisterReport.getEnddate());
				stockregisterReport.setStartdate(currentDateFormat.getDateWithSystemFormat(startDate1));
				stockregisterReport.setEnddate(currentDateFormat.getDateWithSystemFormat(endDate));
				for (StockRegisterReport stkreg : stockregisterReport.getStockRegister()) 
				{
					Date txnDate = formatter.parse(stkreg.getTxnDate());
					stkreg.setTxnDate(currentDateFormat.getDateWithSystemFormat(txnDate));

				}
			}
			return new ModelAndView("stockView", "listInvoice",stockregisterReport);
		} catch (Exception e) {

			logger.error("Method: report in"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();	

		}		
	}
	
	@RequestMapping(value="/Stock Register Excel")
	public ModelAndView excelInvoice(@ModelAttribute StockRegisterReport stockregisterReport,Model model,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {	
      try{
		final StockRegisterReportService stockregisterReportService = new StockRegisterReportService(getCurrentContext());
		
		CompanyProfile company = (CompanyProfile)session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
		Users user= (Users)session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
	
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		final SystemSettings systemSettings = (SystemSettings) session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
		
		final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
		final String dateFormat = currentDateFormat.getSystemDateFormat(); 
		
		String startdate = stockregisterReport.getStartdate();
		
		Integer option = stockregisterReport.getOption();
		
		
		//String where = getwhereValues(stockregisterReport);
		
		Report report=new Report();
		
		report.setDocType(option);
		
		if(stockregisterReport.getOption()==1){
		report.setReportName("STOCK REGISTER REPORT");
		}
		else{
			report.setReportName("STOCK REGISTER MONTHLY REPORT");
				
		}
		report.setDecimalPlace(systemSettings.getDecimal_places());
		report.setCurrency(systemSettings.getCurrencySymbol());
		report.setDateFormat(dateFormat);
		model.addAttribute("reportName", report);
		
	    report.setReportType(1);
	    report.setCurrUserName(user.getName());
		report.setCompanyName(company.getCompany_name());
		report.setCompanyAddress(company.getAddress());
		model.addAttribute("reportName", report);
	
		
		
		
		stockregisterReport.setStockRegister(stockregisterReportService.getReportResult(stockregisterReport));
	
		
		if(stockregisterReport.getOption()==1){
			Date startDate1 = formatter.parse(stockregisterReport.getStartdate());
			Date endDate=formatter.parse(stockregisterReport.getEnddate());
			stockregisterReport.setStartdate(currentDateFormat.getDateWithSystemFormat(startDate1));
			stockregisterReport.setEnddate(currentDateFormat.getDateWithSystemFormat(endDate));
		for (StockRegisterReport stkreg : stockregisterReport.getStockRegister()) {
		Date txnDate = formatter.parse(stkreg.getTxnDate());
			stkreg.setTxnDate(currentDateFormat.getDateWithSystemFormat(txnDate));
		
		}}
		
		return new ModelAndView("stockViewExcel", "listInvoice",stockregisterReport);
	} catch (Exception e) {
		
		logger.error("Method: report in"+this+Throwables.getStackTraceAsString(e));
		throw new CustomException();	

	}		

	}
	
}