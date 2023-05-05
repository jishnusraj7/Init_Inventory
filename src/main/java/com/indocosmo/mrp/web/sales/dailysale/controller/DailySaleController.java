package com.indocosmo.mrp.web.sales.dailysale.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.MasterBaseController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.sales.dailysale.dao.DailySaleDao;
import com.indocosmo.mrp.web.sales.dailysale.model.DailySale;
import com.indocosmo.mrp.web.sales.dailysale.service.DailySaleService;



@Controller
@RequestMapping("/dailysale")

public class DailySaleController extends MasterBaseController<DailySale,DailySaleDao,DailySaleService>{


	

	@Override
	public DailySaleService getService() {
		
		return new DailySaleService(getCurrentContext());
	}
	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute DailySale stockdisposal,HttpSession session,Model model)
			throws Exception {
		
		final CounterService counterService=new CounterService(getCurrentContext());
		final CurrentUserPermissionService userPermissionService=new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "STR_DISPOSE");
		model.addAttribute("permission", permission);
		String CounterWithPrefix=counterService.getNextCounterwithPrefix("stock_adjust_hdr", "stock_adjust_hdr");
		model.addAttribute("REFNO", CounterWithPrefix);
		model.addAttribute("Report",true);
		

		return "/dailysale/list";
	}
}
