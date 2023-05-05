package com.indocosmo.mrp.web.production.planning.planningdetail.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.production.planning.planningdetail.dao.PlanningDetailDao;
import com.indocosmo.mrp.web.production.planning.planningdetail.model.PlanningDetail;
import com.indocosmo.mrp.web.production.planning.planningdetail.service.PlanningDetailService;



@Controller
@RequestMapping("/planningdetail")

public class PlanningDetailController extends ViewController<PlanningDetail,PlanningDetailDao,PlanningDetailService>{


	private PlanningDetailService iuomservice;
	
	@Override
	public PlanningDetailService getService() {
		
		return new PlanningDetailService(getCurrentContext());
	}


	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute PlanningDetail uom ,HttpSession session,Model model)
			throws Exception {

		 final  CurrentUserPermissionService userPermissionService=new CurrentUserPermissionService(); 
			SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "STR_PORD");
			model.addAttribute("permission", permission);
		return "/planning/list";
	}
}
