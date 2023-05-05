
package com.indocosmo.mrp.web.masters.itemslist.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.masters.itemmaster.dao.ItemMasterDao;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;
import com.indocosmo.mrp.web.masters.itemmaster.service.ItemMasterService;
import com.indocosmo.mrp.web.masters.itemslist.model.ItemsList;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping(value = "/itemslist")
public class ItemsListController extends ViewController<ItemMaster,ItemMasterDao,ItemMasterService> {
	public static final Logger logger=Logger.getLogger(ItemsListController.class);
	
	@Override
	public ItemMasterService getService() {

		return new ItemMasterService(getCurrentContext());
	}

	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute ItemsList itemList, HttpSession session, Model model) throws Exception {
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_ITM");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("itemmasterlistclass",true);
		model.addAttribute("Products",true);
		
		return "/itemslist/list";
	}
/*
	@RequestMapping(value = "getItemList")
	public void getTableRowsAsJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final ItemMasterService itemMst = new ItemMasterService(getCurrentContext());

		JsonArray data = itemMst.getJsonArray();
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(data.toString());
	}*/

	
	@RequestMapping(value = "updateIsActiveAndSync")
	public @ResponseBody void updateActive(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		try {
			saveActive(request);
			saveSynch(request);
			response.getWriter().print("1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.getWriter().print("0");
			e.printStackTrace();
			logger.error("Method : updateActive " +this+" "+ Throwables.getStackTraceAsString(e));
		}

	}

	public void saveActive(HttpServletRequest request) throws Exception {
		String activeList[] = request.getParameterValues("activeData");
		String inactiveList[]=request.getParameterValues("inactiveData");
		JsonArray activeId = getJsonFromString(activeList[0]);
		JsonArray inactiveId=getJsonFromString(inactiveList[0]);
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		boolean success = itemMasterService.updateIsActive(activeId,inactiveId);
		//System.out.println(success);

	}

	public void saveSynch(HttpServletRequest request) throws Exception {
		String syncList[] = request.getParameterValues("syncData");
		String nonSyncList[]=request.getParameterValues("nonSyncData");
		JsonArray syncId = getJsonFromString(syncList[0]);
		JsonArray nonSyncId=getJsonFromString(nonSyncList[0]);
		
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		boolean success = itemMasterService.updateIsSynch(syncId,nonSyncId);
		//System.out.println(success);
	}

	public JsonArray getJsonFromString(String s) {
		JsonParser parser = new JsonParser();
		JsonElement selectedElement = parser.parse(s);
		JsonArray selected = selectedElement.getAsJsonArray();
		return selected;
	}
	
	

}
