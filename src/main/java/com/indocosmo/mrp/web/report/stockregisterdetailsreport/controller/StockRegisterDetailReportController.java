package com.indocosmo.mrp.web.report.stockregisterdetailsreport.controller;

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
import com.indocosmo.mrp.web.report.stockregisterdetailsreport.dao.StockRegisterDetailReportDao;
import com.indocosmo.mrp.web.report.stockregisterdetailsreport.model.StockRegisterDetailReport;
import com.indocosmo.mrp.web.report.stockregisterdetailsreport.service.StockRegisterDetailReportService;
import com.indocosmo.mrp.web.report.stockregisterreport.model.StockRegisterReport;
import com.indocosmo.mrp.web.stock.stockdisposal.controller.StockDisposalController;

@Controller
@RequestMapping(value="/stockregisterdetailsreport")
public class StockRegisterDetailReportController extends ViewController<StockRegisterDetailReport,StockRegisterDetailReportDao, StockRegisterDetailReportService>{

	public static final Logger logger=Logger.getLogger(StockRegisterDetailReportController.class);

	@Override
	public StockRegisterDetailReportService getService() {
		// TODO Auto-generated method stub
		return new StockRegisterDetailReportService(getCurrentContext());
	}

	@RequestMapping(value="/stockregisterdetailsreport")
	public String getitemstock(@ModelAttribute StockRegisterDetailReport stockregisterDetailsReport)
			throws Exception {

		return "report/form";
	}

	@RequestMapping(value="/Stock Register Detail")
	public ModelAndView  PrintInvoice(@ModelAttribute StockRegisterDetailReport stockRegisterDetailsReport,Model model,
			HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {

		try {

			final StockRegisterDetailReportService stockRegisterDetailReportService=new StockRegisterDetailReportService(getCurrentContext());
			CompanyProfile company = (CompanyProfile)session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			Users user= (Users)session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			final SystemSettings systemSettings = (SystemSettings) session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat(); 

			//Integer option = stockRegisterDetailsReport.getOption();

			Report report=new Report();
			//report.setDocType(option);

		/*	if(stockRegisterDetailsReport.getOption()==1){
				report.setReportName("STOCK REGISTER DETAIL REPORT");
			}
			else{
				report.setReportName("STOCK REGISTER MONTHLY REPORT");

			}*/
			
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);
			report.setReportType(1);
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			model.addAttribute("reportName", report);
		    stockRegisterDetailsReport.setStockRegisterDetailsReport(stockRegisterDetailReportService.getReportResult(stockRegisterDetailsReport));
		    Date startDate1 = formatter.parse(stockRegisterDetailsReport.getStartdate());
			Date endDate=formatter.parse(stockRegisterDetailsReport.getEnddate());
			stockRegisterDetailsReport.setStartdate(currentDateFormat.getDateWithSystemFormat(startDate1));
			stockRegisterDetailsReport.setEnddate(currentDateFormat.getDateWithSystemFormat(endDate));
			for (StockRegisterDetailReport stockRegisterDetail : stockRegisterDetailsReport.getStockRegisterDetailsReport()) 
			{
				Date txnDate = formatter.parse(stockRegisterDetail.getTxnDate());
				stockRegisterDetail.setTxnDate(currentDateFormat.getDateWithSystemFormat(txnDate));

			}
			
			return new ModelAndView("stockDetailView", "listInvoice",stockRegisterDetailsReport);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("Method: report in"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();	
		}
		
	}
}
