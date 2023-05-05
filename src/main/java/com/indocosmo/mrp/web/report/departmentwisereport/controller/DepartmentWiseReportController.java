package com.indocosmo.mrp.web.report.departmentwisereport.controller;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * 
 * @gana 110320
 */
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.purchaseordersType;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockInStatus;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;
import com.indocosmo.mrp.web.report.departmentwisereport.dao.DepartmentWiseReportDaoImp;
import com.indocosmo.mrp.web.report.departmentwisereport.model.DepartmentWiseReport;
import com.indocosmo.mrp.web.report.departmentwisereport.service.DepartmentWiseReportServiceImp;
import com.indocosmo.mrp.web.report.stockregisterreport.service.StockRegisterReportService;
import com.indocosmo.mrp.web.stock.stockdisposal.controller.StockDisposalController;
/*
 * @gana 31220
 */
@Controller
@RequestMapping(value="/departmentwisereport")
public class DepartmentWiseReportController extends ViewController<DepartmentWiseReport, DepartmentWiseReportDaoImp, DepartmentWiseReportServiceImp>{

	@Override
	public DepartmentWiseReportServiceImp getService() {
		return new DepartmentWiseReportServiceImp(getCurrentContext());
	}

	@RequestMapping(value = "departmentwisereport")
	public String getitemstock(@ModelAttribute DepartmentWiseReport departmentWise, Model model) throws Exception {
		model.addAttribute("Report", true);
		return "report/form";
	}
	
	//to get pdf
	@RequestMapping(value="/getDepartmentWiseReport")
	public ModelAndView printDepartmentWiseReport(@ModelAttribute DepartmentWiseReport departmentWiseReport,Model model,
			HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {

		Integer reportType=Integer.parseInt(request.getParameter("option"));
		final DepartmentWiseReportServiceImp departmentWiseService=new DepartmentWiseReportServiceImp(getCurrentContext());
		final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
		final String dateFormat = currentDateFormat.getSystemDateFormat();
		final SystemSettings systemSettings = (SystemSettings) session
				.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);

		Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);

		CompanyProfile company = (CompanyProfile) session
				.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);

		departmentWiseReport.setDepartmentWiseReport(departmentWiseService.getDepartmentWiseReport(departmentWiseReport));

		Report report = new Report();
		report.setReportName(departmentWiseReport.getDepartmentName().toUpperCase()+" STOCK REGISTER"+" " +"ON"+" "+departmentWiseReport.getStockDate());
		report.setReportType(1);
		report.setCurrUserName(user.getName());
		report.setDecimalPlace(systemSettings.getDecimal_places());
		report.setCompanyName(company.getCompany_name());
		report.setCompanyAddress(company.getAddress());
		report.setDateFormat(dateFormat);
		model.addAttribute("reportName", report);
		if(reportType==1) {			
			return new ModelAndView("departmentWiseStockExcelView", "listInvoice", departmentWiseReport);
		}else {
			return new ModelAndView("departmentWiseReport", "listInvoice", departmentWiseReport);
		}
	}


}
