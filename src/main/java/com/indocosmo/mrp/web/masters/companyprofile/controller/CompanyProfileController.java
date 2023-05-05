package com.indocosmo.mrp.web.masters.companyprofile.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.companyprofile.dao.CompanyProfileDao;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.companyprofile.service.CompanyProfileService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/companyprofile")
public class CompanyProfileController extends ViewController<CompanyProfile, CompanyProfileDao, CompanyProfileService> {
	
	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public CompanyProfileService getService() {
	
		return new CompanyProfileService(getCurrentContext());
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute CompanyProfile companyprofile , HttpSession session , Model model)
			throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_COMP_PROF");
		model.addAttribute("permission", permission);
		
		model.addAttribute("Settings",true);
		
		model.addAttribute("General",true);
		model.addAttribute("Companyprofileclass",true);
		
		return "/companyprofile/list";
	}
}
