package com.indocosmo.mrp.web.masters.salesdepartment.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.indocosmo.mrp.web.core.base.controller.MasterBaseController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.salesdepartment.dao.SalesDepartmentDao;
import com.indocosmo.mrp.web.masters.salesdepartment.model.SalesDepartment;
import com.indocosmo.mrp.web.masters.salesdepartment.service.SalesDepartmentService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/salesdepartment")
public class SalesDepartmentController  extends MasterBaseController<SalesDepartment, SalesDepartmentDao, SalesDepartmentService>{

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public SalesDepartmentService getService() {

		return new SalesDepartmentService(getCurrentContext());
	}


	/**
	 * @param response
	 * @param session
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response,HttpSession session,Model model) throws Exception{

		final CounterService counterService = new CounterService(getCurrentContext()); 

		String CounterWithPrefix = counterService.getNextCounterwithPrefix("sales_departments", "sales_departments");

		response.getWriter().print(CounterWithPrefix);
	}


	/**
	 * @param SalesDepartment
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute SalesDepartment SalesDepartment,HttpSession session,Model model)
			throws Exception {

		final CurrentUserPermissionService userPermissionService=new CurrentUserPermissionService();

		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_AREA");

		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);

		model.addAttribute("settings",true);
		
		model.addAttribute("salesdepartclass",true);
		
		return "/salesdepartment/list";
	}


	/**
	 * @param Quetable
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/saveSaleDep", method=RequestMethod.POST) 
	public void saveSuppier(@RequestBody String Quetable,HttpSession session,HttpServletResponse response,HttpServletRequest request) throws Exception
	{
		final SalesDepartmentService salesDepartmentService=new SalesDepartmentService(getCurrentContext());
		final SalesDepartmentDao salesDepartmentDao=new SalesDepartmentDao(getCurrentContext());
		final SyncQueueService SyncQueueService=new SyncQueueService(getCurrentContext());

		JsonParser parser1 = new JsonParser();

		final String DB_TXN_POINT = "salesDepartmentService";

		Gson gson = new Gson();
		SalesDepartment salesDepartment=null;
		SyncQueue syncQueue=null;
		salesDepartmentService.beginTrans(DB_TXN_POINT);

		try
		{
			salesDepartment = super.saveData(request);

			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable=gson.toJson(jsonObject.get("Quetable"));
			JsonObject QutableData  = (JsonObject) parser1.parse(Qutable);

			if(salesDepartment.getId().toString()!=""){

				syncQueue=new SyncQueue();

				String tableName=salesDepartmentDao.getTable();
				String bathId=SyncQueueService.fungetSynQueId(tableName,salesDepartment.getId().toString());
				if(bathId!=""){

					syncQueue.setId(Integer.parseInt(bathId));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(salesDepartment.getId());

				SyncQueueService.save(syncQueue);

			}
			response.getWriter().print("1");

			salesDepartmentService.endTrans(DB_TXN_POINT);	

		}
		catch (Exception e) {

			response.getWriter().print("0");
			salesDepartmentService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveSaleDep"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		final SyncQueueService SyncQueueService=new SyncQueueService(getCurrentContext());
		final SalesDepartmentService salesDepartmentService=new SalesDepartmentService(getCurrentContext());
		final SalesDepartmentDao salesDepartmentDao=new SalesDepartmentDao(getCurrentContext());
		
		final String DB_TXN_POINT = "salesDepartmentService";
		
		final String id = request.getParameter("id");

		salesDepartmentService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			Integer is_sync=0;
			is_deleted = salesDepartmentService.delete(id);
			
			if (is_deleted == 0 ) {
			
				response.getWriter().print("0");
			}
			else
			{
				is_sync=SyncQueueService.updateSyncQueue(id,salesDepartmentDao.getTable());
				response.getWriter().print("1");
			}

			salesDepartmentService.endTrans(DB_TXN_POINT);

		}
		catch (Exception e) {	
			
			salesDepartmentService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}


	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/editData")
	public void getModalData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int Id=Integer.parseInt(request.getParameter("id"));
		JsonObject jsonResponse = new JsonObject();

		final SalesDepartmentService salesDepartmentService=new SalesDepartmentService(getCurrentContext());
		JsonArray editDet=salesDepartmentService.getEditDetails(Id);
		jsonResponse.add("editDet",editDet.get(0));

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());	
	}



}
