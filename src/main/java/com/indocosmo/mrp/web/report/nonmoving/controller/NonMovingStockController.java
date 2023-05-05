package com.indocosmo.mrp.web.report.nonmoving.controller;

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
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.purchaseordersType;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockInStatus;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.report.currentstock.model.ItemStock;
import com.indocosmo.mrp.web.report.nonmoving.dao.NonMovingStockDao;
import com.indocosmo.mrp.web.report.nonmoving.model.NonMovingStock;
import com.indocosmo.mrp.web.report.nonmoving.service.NonMovingStockService;
import com.indocosmo.mrp.web.stock.stockdisposal.controller.StockDisposalController;

@Controller
@RequestMapping(value = "/nonmovingitem")
public class NonMovingStockController extends ViewController<NonMovingStock,NonMovingStockDao,NonMovingStockService>{
	public static final Logger logger=Logger.getLogger(StockDisposalController.class);
	@Override
	public NonMovingStockService getService() {

		return new NonMovingStockService(getCurrentContext());
	}
	
	@RequestMapping(value = "currentstock")
	public String getitemstock(@ModelAttribute ItemStock itemstock,Model model)
			throws Exception {
		model.addAttribute("poStatus", purchaseordersType.values());
		model.addAttribute("stockinStatus", stockInStatus.values());

		model.addAttribute("Report",true);
		
		return "report/form";
	}

	@RequestMapping(value="/NonMoving Report")
	 public ModelAndView PrintCurrentstock(@ModelAttribute NonMovingStock itemstock,Model model,
			 HttpSession session,HttpServletResponse response) throws Exception 
	{	
		try {

			final NonMovingStockService itemstockService = new NonMovingStockService(getCurrentContext());
			Users user= (Users)session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
			final SystemSettings systemSettings = (SystemSettings) session.getAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG);
			CompanyProfile company = (CompanyProfile)session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
			final GettingCurrentDateTime currentDateFormat = new GettingCurrentDateTime(getCurrentContext());
			final String dateFormat = currentDateFormat.getSystemDateFormat(); 
			itemstock.setItemstock(itemstockService.getList(itemstock));

			Report report=new Report();
			report.setReportName("NON MOVING STOCK REPORT");
			report.setReportType(1);
			report.setCurrUserName(user.getName());
			report.setDecimalPlace(systemSettings.getDecimal_places());
			report.setCompanyName(company.getCompany_name());
			report.setCompanyAddress(company.getAddress());
			report.setDateFormat(dateFormat);
			model.addAttribute("reportName", report);

			if(itemstock.getPdfExcel().equals("pdf"))
				return new ModelAndView("nonMovingStockView", "listcurrentStock",itemstock);
			else
				return new ModelAndView("nonMovingStockExcelView", "listcurrentStock",itemstock);
		} 
		catch (Exception e) {

			logger.error("Method: report in"+ this +Throwables.getStackTraceAsString(e));
			throw new CustomException();	

		}	
	}
}
