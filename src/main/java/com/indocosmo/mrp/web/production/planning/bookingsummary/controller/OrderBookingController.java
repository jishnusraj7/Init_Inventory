package com.indocosmo.mrp.web.production.planning.bookingsummary.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.production.planning.bookingsummary.dao.OrderBookingDao;
import com.indocosmo.mrp.web.production.planning.bookingsummary.model.OrderBoonkingSummary;
import com.indocosmo.mrp.web.production.planning.bookingsummary.service.OrderBookingService;
import com.indocosmo.mrp.web.production.planning.planningdetail.dao.PlanningDetailDao;
import com.indocosmo.mrp.web.production.planning.planningdetail.model.PlanningDetail;
import com.indocosmo.mrp.web.production.planning.planningdetail.service.PlanningDetailService;



@Controller
@RequestMapping("/orderbookingsummary")

public class OrderBookingController extends ViewController<OrderBoonkingSummary,OrderBookingDao,OrderBookingService>{


	private OrderBookingService iuomservice;
	
	@Override
	public OrderBookingService getService() {
		
		return new OrderBookingService(getCurrentContext());
	}


	
	/*@RequestMapping(value = "/list")
	public String getList(@ModelAttribute PlanningDetail uom ,HttpSession session,Model model)
			throws Exception {

		 final  CurrentUserPermissionService userPermissionService=new CurrentUserPermissionService(); 
			SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "STR_PORD");
			model.addAttribute("permission", permission);
		return "/planning/list";
	}*/
}
