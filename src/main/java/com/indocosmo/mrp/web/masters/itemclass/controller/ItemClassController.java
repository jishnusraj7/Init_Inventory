package com.indocosmo.mrp.web.masters.itemclass.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.MasterBaseController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.department.model.Department;
import com.indocosmo.mrp.web.masters.itemclass.dao.ItemClassDao;
import com.indocosmo.mrp.web.masters.itemclass.model.ItemClass;
import com.indocosmo.mrp.web.masters.itemclass.service.ItemClassService;
import com.indocosmo.mrp.web.masters.salesdepartment.service.SalesDepartmentService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/itemclass")
public class ItemClassController extends MasterBaseController<ItemClass, ItemClassDao, ItemClassService> {
	
	public static final Logger logger = Logger.getLogger(ItemClassController.class);
	
	@Override
	public ItemClassService getService() {
	
		return new ItemClassService(getCurrentContext());
	}
	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute Department department , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_ITMC");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("Products",true);
		model.addAttribute("itemclass",true);
		
		return "/itemclass/list";
	}
	
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("item_classes", "item_classes");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	@RequestMapping(value = "/saveItem")
	protected @ResponseBody String saveItem(MultipartHttpServletRequest request ,
			@RequestParam(value = "imgFile", required = false) MultipartFile file1 ,
			@RequestParam(value = "data", required = false) String completeData , HttpServletResponse response)
			throws Exception {
	
		String status = "success";
		Gson gson = new Gson();
		JsonParser parser1 = new JsonParser();
		ObjectMapper mapper = new ObjectMapper();
		final ItemClassService itemClassService = new ItemClassService(getCurrentContext());
		
		JsonObject jobject = parser1.parse(completeData).getAsJsonObject();
		
		String rootPath = System.getProperty("catalina.home");
		String absolutePath = "/mrp_uploads/item_class_images/";
		String rootDirectory = rootPath + "/webapps/" + absolutePath;
		
       
		File directory = new File(rootDirectory);
		if (! directory.exists()){
	        directory.mkdirs();
	       
	    }
		
		// ItemMaster item =
		// mapper.readValue(gson.toJson(jobject.get("itemData")),
		// ItemMaster.class);
		
		final String DB_TXN_POINT = "itemClassService";
		
		try {
			// ObjectMapper mapper = new ObjectMapper();
			ItemClass item = mapper.readValue(gson.toJson(jobject.get("itemData")), ItemClass.class);
			String code = item.getCode();
			if (file1 != null) {
				//file1.transferTo(new File(rootDirectory + code + "_img1.jpg"));
				//item.setItemThumb(absolutePath + code + "_img1.jpg");
				
				BufferedImage image1 = ImageIO.read(file1.getInputStream());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write( image1, "jpg", baos );
				baos.flush();
				byte[] imageInByte = baos.toByteArray();
				String base64 = Base64.getEncoder().encodeToString(imageInByte);
				item.setItemThumb(base64);
				
			}
			
			itemClassService.beginTrans(DB_TXN_POINT);
			itemClassService.save(item);
			
			itemClassService.endTrans(DB_TXN_POINT);
			// response.getWriter().print("1");
			
		}
		catch (Exception e) {
			status = "error";
			e.printStackTrace();
			itemClassService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: save in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
		status = "status:" + status;
		return gson.toJson(status);
	}
	
	@RequestMapping(value = "superclassList")
	public void getSuperClassListAsJson(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		JsonObject jsonResponse = new JsonObject();
		try {
			JsonArray superClassList = getService().getSuperClassList();
			
			jsonResponse.add("superClassList", superClassList);
			
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : DataAsJson " + this + "  " + Throwables.getStackTraceAsString(e));
			// throw new CustomException();
		}
		
	}
	
	@RequestMapping(value = "ItemClassReport")
	public ModelAndView generateExcel(@ModelAttribute ItemClass itemClass , HttpServletRequest request ,
			HttpServletResponse response) throws Exception {
	
		final ItemClassService itemClassService = new ItemClassService(getCurrentContext());
		
		try {
			itemClass.setItemClassExcelData(itemClassService.getItemClassData());
		}
		catch (Exception e) {
			throw e;
		}
		return new ModelAndView("itemClassView", "invoiceData", itemClass);
	}
	
	@RequestMapping("depList")
	public void getDepList(HttpServletRequest request , HttpServletResponse response , Model model) throws Exception {
	
		final SalesDepartmentService departmentService = new SalesDepartmentService(getCurrentContext());
		
		JsonArray data = departmentService.getMastersRowJson();
		JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.add("data", data);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
}
