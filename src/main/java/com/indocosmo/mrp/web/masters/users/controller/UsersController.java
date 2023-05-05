package com.indocosmo.mrp.web.masters.users.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.config.AppConfig;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.CombineMode;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.subheaderMenus;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.company.service.CompanyService;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.companyprofile.service.CompanyProfileService;
import com.indocosmo.mrp.web.masters.department.model.Department;
import com.indocosmo.mrp.web.masters.department.service.DepartmentService;
import com.indocosmo.mrp.web.masters.supplier.model.Supplier;
import com.indocosmo.mrp.web.masters.supplier.service.SupplierService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.systemsettings.service.SystemSettingsService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.service.SysdefPermissionService;
import com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.model.UserGroupPermission;
import com.indocosmo.mrp.web.masters.usergroup.usergrouppermission.service.UserGroupPermissionService;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.model.UserPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.masters.users.dao.UsersDao;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.masters.users.service.UsersService;
import com.indocosmo.mrp.web.masters.users.shopuser.dao.ShopUserDao;
import com.indocosmo.mrp.web.masters.users.shopuser.model.ShopUser;
import com.indocosmo.mrp.web.masters.users.shopuser.service.ShopUserService;

@Controller
@RequestMapping("/")
public class UsersController extends ViewController<Users, UsersDao, UsersService> {
	public static final Logger logger=Logger.getLogger(UsersController.class);

	@Autowired
	AppConfig appConf;

	@Override
	public UsersService getService() {
		return new UsersService(getCurrentContext());
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */


	/**
	 * @param Users
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute Users Users,HttpSession session,Model model) throws Exception {
	final CurrentUserPermissionService userPermisssionService=new CurrentUserPermissionService();
		SysdefPermission permission = userPermisssionService.getCurrentUserPermission(session, "SET_USR");
		model.addAttribute("permission", permission);
		model.addAttribute("Users",true);
		model.addAttribute("usersclass",true);
		model.addAttribute("Settings",true);
		return "/users/list";
	}

	/**
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/")
	public String login(HttpSession session,Model model) throws Exception {
		SessionManager.clearCurrentSession(getCurrentContext());
		final CompanyService companyService=new CompanyService(getNewContext());
		List<Company> hqCompany = companyService.getList();
		
		model.addAttribute("hqCompany", hqCompany);
		session.setAttribute(SessionManager.SESSION_COMPANY_LIST_TAG, hqCompany);
		final CompanyProfileService companyprofileService=new CompanyProfileService(getNewContext());
		List<CompanyProfile> CompanyProfile = companyprofileService.getList();
		model.addAttribute("hqCompany", hqCompany);
		session.setAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS, CompanyProfile.get(0));
	    model.addAttribute("version", appConf.getsystemSettings().getImplementationMode().getImplementationModeId());
				
		return "login/login";
	   
	}
	@RequestMapping(value = "/redirectToProd")
	public String redirectToProduction(HttpSession session)
	{
		String redirectStr="redirect:purchaserequesthdr/list";
		Users user=(Users)session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
		HashMap<String,SysdefPermission> permissions=(HashMap<String, SysdefPermission>)session.getAttribute(SessionManager.SESSION_USERPERMISSION);
		/*if(userGroupPermissionList.size() !=0)
		{*/
		int flg=0;
		Iterator it = permissions.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();        
	       
	        SysdefPermission val=(SysdefPermission)pair.getValue(); 
	  
	         if(val.getSystemGroup().equals("MAIN") && 
	        		 val.getDescription().equals(subheaderMenus.MAIN_STR.getSubheaderMenusName()))
	         {

	        	 if(val.getCanView() != null ){
	        		 if(val.getCanView() ==true )
	        		 {

	        			 redirectStr="redirect:purchaserequesthdr/list";
	        			 if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId()!=0)
	        			 {
	        				 if(appConf.getsystemSettings().getCombineModePurchase().getCombineModeId()==CombineMode.COMBINE.getCombineModeId())
	        				 {
	        					 redirectStr="redirect:stockin/list";

	        				 }
	        				 else
	        				 {
	        					 redirectStr="redirect:purchaseorderhdr/list";

	        				 }
	        			 }
	        			 flg=1;
	        			 break;
	        		 }
	        	 }

	         }else continue;
	        
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	  
	   
	    if(flg==0)
		   {
			   redirectStr="redirect:planning/list";
		   }
		if((appConf.getsystemSettings().getImplementationMode().getImplementationModeId()==0 && user.getCmpnyType() != 0)
				|| (appConf.getsystemSettings().getImplementationMode().getImplementationModeId()!=0 && user.getCompanyId() != 0))
		{
			return redirectStr;

		}
		else
		{
			return "redirect:supplier/list";

		}
		/*}else
		{
			return "redirect:supplier/list";
		}*/
	}

	    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
	    public ModelAndView redirectToSales(HttpSession session) throws Exception {
	    	 String tokenId="";
			
			final UsersService usersService=new UsersService(getCurrentContext());
			Users user=(Users)session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
			Company currentCompany=(Company)session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_TAG);
			
			Instant instant = Instant.now();
			Instant inst=instant.plus(Duration.ofHours(0).plusMinutes(2));
			Date dt=Date.from(inst);

			String uniqueID=UUID.randomUUID().toString();
			tokenId=uniqueID.substring(0, 15);

			
			
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//Date date = new Date();
			String exprtime=dateFormat.format(dt); 
			
			
			
			
		usersService.updateToken(tokenId,exprtime,user.getId());
			
	    	
	            return new ModelAndView("redirect:" + appConf.getsystemSettings().getSalesUrl()+"?shopId="+currentCompany.getId()+"&tokenId="+tokenId);

	    }
	/**
	 * @param user
	 * @param session
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "login")
	public String logincheck(@ModelAttribute Users user, HttpSession session,
			HttpServletResponse response,Model model) throws Exception {

//		CompanyService companyService=new CompanyService(getNewContext());
	final UserPermission userPermission = new UserPermission();
		
		@SuppressWarnings("unchecked")
		List<Company> hqCompany =(List<Company>) session.getAttribute(SessionManager.SESSION_COMPANY_LIST_TAG);
		model.addAttribute("hqCompany", hqCompany);
		final CompanyService companyService=new CompanyService(getNewContext());
		Company currentCompany = new Company();
		
		if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId()==0)
		{
		if(user.getCmpnyType() != 0){
			final Integer companyId=user.getCompanyId();	
			currentCompany=companyService.getCompanyByID(companyId) ;
		}else{
			currentCompany=companyService.getCompanyByID(0) ;
		}
		
		}
		
		else
		{
			final Integer companyId=user.getCompanyId();	
			currentCompany=companyService.getCompanyByID(companyId) ;
		}
		final ApplicationContext currentCompanyContext=getNewContext(currentCompany);
		
	
		final UsersService usersService=new UsersService(currentCompanyContext);
		user = usersService.login(user);

		if (user.getId() == null) {

			 model.addAttribute("version", appConf.getsystemSettings().getImplementationMode().getImplementationModeId());
			model.addAttribute("err_msg", user.getErr_msg());
			

			return "login/login";
		} else {

			//If Business Type is 1 Redirect to Intermediate Page To Choose Sales Or Production
		/*	if(currentCompany.getBusinessType() == 1)
			{
				System.out.println("redirect to intermediate");
				return "redirect:login/intermediate";
			}else
			{
				System.out.println("redirect to Sales");
				return "redirect:purchaseorderhdr/list";
			}
 */
			if(currentCompany.getBusinessType() == 1)
			{
			final Integer userGroupId=user.getUserGroupId();
			final String cond = "user_group_id="+userGroupId;
			final String cond1=appConf.getsystemSettings().getImplementationMode().getColumnName()+"=1";
			SysdefPermissionService sysdefPermissionService=new SysdefPermissionService(currentCompanyContext);
			//List<SysdefPermission> sysdefPermssionList=sysdefPermissionService.getList();
			List<SysdefPermission> sysdefPermssionList=sysdefPermissionService.getList(cond1);
			UserGroupPermissionService userGroupPermissionservice=new UserGroupPermissionService(currentCompanyContext);
			List<UserGroupPermission> userGroupPermissionList=userGroupPermissionservice.getList(cond);
		
			HashMap<String,SysdefPermission> permissions=new HashMap<String, SysdefPermission>();
			for(SysdefPermission systemPermission : sysdefPermssionList){

				for(UserGroupPermission userGroupPermission : userGroupPermissionList){
					if(systemPermission.getId() == userGroupPermission.getSysdefPermissionId()){
						systemPermission.setCanDelete(userGroupPermission.getCanDelete());
						systemPermission.setCanEdit(userGroupPermission.getCanEdit());
						systemPermission.setCanExecute(userGroupPermission.getCanExecute());
						systemPermission.setCanView(userGroupPermission.getCanView());
						systemPermission.setCanExport(userGroupPermission.getCanExport());
						systemPermission.setCanAdd(userGroupPermission.getCanAdd());
					}
				}

				if(userGroupPermissionList.size() > 0){
					systemPermission.setUserGroupId(userGroupPermissionList.get(0).getUserGroupId());
				}else{
					systemPermission.setUserGroupId(0);
				}
				permissions.put(systemPermission.getCode(), systemPermission);
			}

			
			//session.setAttribute("userPermissions", permissions);
			
			session.setAttribute(SessionManager.SESSION_USERPERMISSION, permissions);
			//session.removeAttribute("err_msg");
			session.setAttribute(SessionManager.SESSION_CURRENT_USER_TAG, user);
			Department department=null;
			Supplier suppler = null;
			SystemSettings systemSettings = null;
			final CompanyProfileService companyprofileService=new CompanyProfileService(currentCompanyContext);
			try {
			SupplierService supplierService=new SupplierService(currentCompanyContext);
			suppler = supplierService.getIsSystemSupplier();
			DepartmentService departmentService=new DepartmentService(currentCompanyContext);
			department = departmentService.getIsSystemDepartment();
			
			
			SystemSettingsService systemSettingService=new SystemSettingsService(currentCompanyContext);
				systemSettings = systemSettingService.getSystemData();
				
				
				List<CompanyProfile> CompanyProfile = companyprofileService.getList();
				session.setAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS, CompanyProfile.get(0));
			} catch (Exception e) {

				e.printStackTrace();
				logger.error("Method : logincheck " + this+" " +Throwables.getStackTraceAsString(e));
			}
			
            session.setAttribute(SessionManager.SESSION_CURRENT_COMPANY_TAG, currentCompany);
            session.setAttribute(SessionManager.SESSION_CURRENT_COMPANY_ID, currentCompany.getId());
           session.setAttribute(SessionManager.SESSION_CURRENT_SUPPLIER_TAG,suppler);
           session.setAttribute(SessionManager.SESSION_CURRENT_DEPARTMENT_TAG,department);
           session.setAttribute(SessionManager.SESSION_CURRENT_SYSTEMSETTINGS_TAG,systemSettings);
			session.setAttribute(SessionManager.SESSION_CURRENT_COMPANY_TYPE, user.getCmpnyType());
			session.setAttribute(SessionManager.SESSION_VERSION,appConf.getsystemSettings().getImplementationMode().getImplementationModeId());
            session.setAttribute(SessionManager.SESSION_COMBINEMODE,appConf.getsystemSettings().getCombineModePurchase().getCombineModeId() );
          session.setAttribute(SessionManager.SESSION_DAILYPRODVIEW,appConf.getsystemSettings().getDailyproductionView().getDailyproductionViewId() );

            
				//System.out.println("redirect to intermediate");
				return "login/intermediate";
			}
			else
			{
				//System.out.println("redirect to Sales");
				
				String tokenId="";
				
				Instant instant = Instant.now();
				Instant inst=instant.plus(Duration.ofHours(0).plusMinutes(2));
				Date dt=Date.from(inst);

				
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String exprtime=dateFormat.format(dt); 
				
				String uniqueID=UUID.randomUUID().toString();
				tokenId=uniqueID.substring(0, 15);
				
				
			     usersService.updateToken(tokenId,exprtime,user.getId());
				return "redirect:" + appConf.getsystemSettings().getSalesUrl()+"?shopId="+currentCompany.getId()+"&tokenId="+tokenId;
				
			}
			/*String redirectStr="redirect:purchaserequesthdr/list";
			if(userGroupPermissionList.size() !=0)
			{
			int flg=0;
			Iterator it = permissions.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();        
		       
		        SysdefPermission val=(SysdefPermission)pair.getValue(); 
		        
		      
		  
		         if(val.getSystemGroup().equals("MAIN") && 
		        		 val.getDescription().equals(subheaderMenus.MAIN_STR.getSubheaderMenusName()))
			        {
		        	 
			        	if(val.getCanView() ==true )
			        	{
			        		
			        		redirectStr="redirect:purchaserequesthdr/list";
			        		if(appConf.getsystemSettings().getImplementationMode().getImplementationModeId()!=0)
			        		{
			        			redirectStr="redirect:purchaseorderhdr/list";
			        		}
			        		flg=1;
			        		break;
			        	}
			        	
			     
			        }else continue;
		        
		        it.remove(); // avoids a ConcurrentModificationException
		    }
		  
		   
		    if(flg==0)
			   {
				   redirectStr="redirect:planning/list";
			   }
			if((appConf.getsystemSettings().getImplementationMode().getImplementationModeId()==0 && user.getCmpnyType() != 0)
					|| (appConf.getsystemSettings().getImplementationMode().getImplementationModeId()!=0 && user.getCompanyId() != 0))
			{
				return redirectStr;

			}
			else
			{
				return "redirect:supplier/list";
	
			}
			}else
			{
				return "redirect:supplier/list";
			}*/
			
		}

	}

	/**
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout")
	public String logout(@ModelAttribute Users user, HttpServletRequest request,HttpServletResponse response,Model model,HttpSession session)
			throws Exception {
	 
		SessionManager.clearCurrentSession(getCurrentContext());
		return "redirect:/";

	}
	
	

	@RequestMapping(value = "/editData")
	public void getModalData(HttpServletRequest request, HttpServletResponse response,HttpSession session ) throws Exception{
		int Id=Integer.parseInt(request.getParameter("id"));
		JsonObject jsonResponse = new JsonObject();
		
		final  UsersService usersService=new UsersService(getCurrentContext());
		final ShopUserService shopUserService=new ShopUserService(getCurrentContext());
		JsonArray editDet=usersService.getEditDetails(Id);
		CompanyProfile company = (CompanyProfile)session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);

		if(company.getCompany_id()==0){
		JsonArray shopData=shopUserService.getEditDetails(Id);
		jsonResponse.add("shopData",shopData);
		}
		jsonResponse.add("editDet",editDet.get(0));
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());	
	}

	@RequestMapping(value = "/checkpass")
	public void checkPassword(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int Id=Integer.parseInt(request.getParameter("id"));
		JsonObject jsonResponse = new JsonObject();
		
		final  UsersService usersService=new UsersService(getCurrentContext());
		JsonArray checkPassword=usersService.checkPassword(Id);
		jsonResponse.add("checkPassword",checkPassword.get(0));
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());	
	}
	
	@RequestMapping(value = "/updatepassword")
	public void updatepassword(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer status=0;
		int Id=Integer.parseInt(request.getParameter("id"));
		String pass=request.getParameter("new_password");
		
		final  UsersService usersService=new UsersService(getCurrentContext());
		status=usersService.updatepassword(Id,pass);
		response.getWriter().print(status);
		
	}
	

	
	/*@RequestMapping(value="/saveUser", method=RequestMethod.POST) 
	public void saveUser(@RequestBody String Quetable,HttpSession session,HttpServletResponse response,HttpServletRequest request) throws Exception
	{
		final UsersService usersService=new UsersService(getCurrentContext());
		final UsersDao usersDao=new UsersDao(getCurrentContext());
		final SyncQueueService SyncQueueService=new SyncQueueService(getCurrentContext());

		JsonParser parser1 = new JsonParser();
		final String DB_TXN_POINT = "usersService";
		Gson gson = new Gson();
		Users user=null;
		SyncQueue syncQueue=null;
		usersService.beginTrans(DB_TXN_POINT);
		CompanyProfile company = (CompanyProfile)session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);

		try
		{
			
			
			user = super.saveData(request);
			
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable=gson.toJson(jsonObject.get("Quetable"));
			 JsonObject QutableData  = (JsonObject) parser1.parse(Qutable);
			
			System.out.println(QutableData);
			if(user.getId().toString()!="")	
			{
				syncQueue=new SyncQueue();
				String tableName=usersDao.getTable();
				String Syn_Id=SyncQueueService.fungetSynQueId(tableName,user.getId().toString());
				if(Syn_Id!="")
				{
					syncQueue.setId(Integer.parseInt(Syn_Id));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(user.getId());
				
				SyncQueueService.save(syncQueue);
				
				
			}
			if(company.getCompany_id()==0)
			{
				
			}
			
			
			
			
			response.getWriter().print("1");
			
			usersService.endTrans(DB_TXN_POINT);	
		}
		catch (Exception e) {
			response.getWriter().print("0");
			usersService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveItmCat"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
		
		
		
	}*/


	
	
	@RequestMapping(value="/saveUser", method=RequestMethod.POST) 
	public void saveUser(@RequestBody String completeData,HttpSession session,HttpServletResponse response,HttpServletRequest request) throws Exception
	{
		final UsersService usersService=new UsersService(getCurrentContext());
		final UsersDao usersDao=new UsersDao(getCurrentContext());
		final SyncQueueService SyncQueueService=new SyncQueueService(getCurrentContext());
		final ShopUserService shopUserService=new ShopUserService(getCurrentContext());
		final ShopUserDao shopUserDao=new ShopUserDao(getCurrentContext());

		JsonParser parser1 = new JsonParser();
		final String DB_TXN_POINT = "usersService";
		Gson gson = new Gson();
		Users user=null;
		SyncQueue syncQueue=null;
		ShopUser shopuser=null;
		CompanyProfile company = (CompanyProfile)session.getAttribute(SessionManager.SESSION_CURRENT_COMPANY_DETAILS);
		final ArrayList<SyncQueue> SyncQueueArrayList = new ArrayList<SyncQueue>();
		final ArrayList<ShopUser> shopUserArrayList= new ArrayList<ShopUser>();	
		usersService.beginTrans(DB_TXN_POINT);
		
		try
		{
			user = super.saveData(request);
			
			
			JsonObject jsonObject = parser1.parse(completeData).getAsJsonObject();
			String Qutable=gson.toJson(jsonObject.get("Quetable"));
			 JsonObject QutableData  = (JsonObject) parser1.parse(Qutable);
			
			System.out.println(QutableData);
			if(user.getId().toString()!="")	
			{
				syncQueue=new SyncQueue();
				String tableName=usersDao.getTable();
				String Syn_Id=SyncQueueService.fungetSynQueId(tableName,user.getId().toString());
				if(Syn_Id!="")
				{
					syncQueue.setId(Integer.parseInt(Syn_Id));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(user.getId());
				
				SyncQueueService.save(syncQueue);
				
				
			}
 if(company.getCompany_id()==0)
	{
				
			
			
		String shopData=gson.toJson(jsonObject.get("shopData"));
			
			
			JsonArray shopDataList = (JsonArray) parser1.parse(shopData);
			String shpIds="";
			
     if(request.getParameter("id")!=null)
     {
			
			for(int i=0;i<shopDataList.size();i++)
			{
				shpIds+=(shpIds.isEmpty() ? " ":",")+shopDataList.get(i).getAsInt();
			}
			System.out.println(shpIds);
			
			shopUserService.DeleteShops(shpIds,user.getId());
     }		
			
		if(shopDataList.size()!=0)
		{
			for(int i=0;i<shopDataList.size();i++)
			{
				Integer shop_id=shopDataList.get(i).getAsInt();
				 shopuser=new ShopUser();
				 
			
				 
				 
				 String shpuser_id=shopUserService.getShopUserId(user.getId().toString(),shop_id.toString());
				 
				 if(shpuser_id!="")
				 {
					 shopuser.setId(Integer.parseInt(shpuser_id)); 
				 }
				 
				 shopuser.setShop_id(shop_id);
				 shopuser.setUser_id(user.getId());
				 shopuser.setUser_group_id(user.getUserGroupId());
				 shopUserArrayList.add(shopuser);
				
				
			}
			shopUserService.save(shopUserArrayList);
			
			for(int i=0;i<shopUserArrayList.size();i++)
			{
				
				
			
				

				syncQueue=new SyncQueue();
				String tableName=shopUserDao.getTable();
				String synId=SyncQueueService.fungetSynQueId(tableName,shopUserArrayList.get(i).getId().toString());
				if(synId!="")
				{
					 syncQueue.setId(Integer.parseInt(synId));
				}
				
				
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
			     syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
			     syncQueue.setOrigin(QutableData.get("origin").getAsString());
			  
			     syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
			     syncQueue.setTableName(shopUserDao.getTable());
			     syncQueue.setRecordId(shopUserArrayList.get(i).getId());
			     SyncQueueArrayList.add(syncQueue);
			}
			
			SyncQueueService.save(SyncQueueArrayList);
			
			
			
		}	
			
			
	}		
			
			
			
			
			response.getWriter().print("1");
			
			usersService.endTrans(DB_TXN_POINT);	
		}
		catch (Exception e) {
			response.getWriter().print("0");
			usersService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveItmCat"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
		
		
		
	}
	
	
	@Override
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final UsersService usersService=new UsersService(getCurrentContext());
		final SyncQueueService SyncQueueService=new SyncQueueService(getCurrentContext());
		final UsersDao usersDao=new UsersDao(getCurrentContext());
        final ShopUserService shopUserService=new ShopUserService(getCurrentContext());
        ApplicationContext context = usersDao.getContext();
		final String DB_TXN_POINT = "usersService";
		final String id = request.getParameter("id");
		usersService.beginTrans(DB_TXN_POINT);
		try {
			Integer is_deleted = 0;
			Integer is_sync=0;
			is_deleted = usersService.delete(id);
			if (is_deleted == 0 ) {
				response.getWriter().print("0");
			}
			else
			{
				is_sync=SyncQueueService.updateSyncQueue(id,usersDao.getTable());
				if (context.getCompanyInfo().getId() == 0) {
				shopUserService.deleteSyncQue(Integer.parseInt(id));
				is_deleted = shopUserService.delete(id);
				}
				response.getWriter().print("1");
			}
			usersService.endTrans(DB_TXN_POINT);
		
	}
		 catch (Exception e) {	
			 usersService.rollbackTrans(DB_TXN_POINT);
				logger.error("Method: delete in"+this+Throwables.getStackTraceAsString(e));
				throw new CustomException();
			}

	}
	
	
	
	
}
