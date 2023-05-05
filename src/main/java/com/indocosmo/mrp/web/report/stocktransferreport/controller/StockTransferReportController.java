package com.indocosmo.mrp.web.report.stocktransferreport.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.stocktransferreport.dao.StockTransferReportDao;
import com.indocosmo.mrp.web.report.stocktransferreport.model.StockTransferReportModel;
import com.indocosmo.mrp.web.report.stocktransferreport.service.StockTransferReportService;

@Controller
@RequestMapping(value = "/stocktransferreport")
public class StockTransferReportController extends ViewController<StockTransferReportModel, StockTransferReportDao, StockTransferReportService>{

	@Override
	public StockTransferReportService getService() {
	
		return new StockTransferReportService(getCurrentContext());
	}
	
	@RequestMapping(value = "/stocktransferReport")
	public ModelAndView PrintInvoice(@ModelAttribute StockTransferReportModel stocktransfer , HttpServletRequest request ,
			Model model , HttpServletResponse response , HttpSession session) throws Exception 
	{
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String transferType = request.getParameter("transferType");
		String itemId= request.getParameter("stockitem");
		//Integer companyid = Integer.parseInt(request.getParameter("companyId"));
				

		stocktransfer.setStartdate(startdate);
		stocktransfer.setEnddate(enddate);
		//stocktransfer.setCompanyid(companyid);

		final StockTransferReportDao stktrsfrdao = new StockTransferReportDao(getCurrentContext());
		JsonArray itemData = null;
		itemData = stktrsfrdao.getItemData(startdate, enddate,transferType,itemId);//option[0], session);
		try {

			CompanyProfile company = (CompanyProfile) session
					.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final SystemSettings systemSettings = (SystemSettings) session
					.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat();
			Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

			Report report = new Report();
			report.setReportName("STOCK TRANSFER REPORT SUMMARY");
			report.setCurrUserName(user.getName());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			report.setReportType(1);
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCurrency(systemSettings.getCurrencySymbol());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);
			model.addAttribute("stocktransfer", stocktransfer);

		}
		catch (Exception e) {

			logger.error("Method: report in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		if(stocktransfer.getPdfExcel().equals("pdf"))
			return new ModelAndView("stocktransferSummaryView", "stocktransferSummaryView", itemData);
		else
			return new ModelAndView("StockTransferExcelReportView", "stocktransferSummaryView", itemData);

	}
}
