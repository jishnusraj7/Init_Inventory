package com.indocosmo.mrp.web.masters.creditcard.controller;

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
import com.indocosmo.mrp.web.masters.creditcard.dao.CreditCardDao;
import com.indocosmo.mrp.web.masters.creditcard.model.CreditCard;
import com.indocosmo.mrp.web.masters.creditcard.service.CreditCardService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping("/creditcard")
public class CreditCardController extends MasterBaseController<CreditCard, CreditCardDao, CreditCardService> {

	@Override
	public CreditCardService getService() {
		
		return new CreditCardService(getCurrentContext());
	}

	
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response,HttpSession session,Model model) throws Exception{

		final CounterService counterService = new CounterService(getCurrentContext()); 

		String CounterWithPrefix = counterService.getNextCounterwithPrefix("credit_card", "credit_card");

		response.getWriter().print(CounterWithPrefix);
	}
	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute CreditCard creditCard,HttpSession session,Model model)
			throws Exception {

		final CurrentUserPermissionService userPermissionService=new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_CRD");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("paymentclass",true);
		model.addAttribute("Payment",true);
		return "/creditcard/list";
	}
	
	
	@RequestMapping(value="/saveCreditCard", method=RequestMethod.POST) 
	public void saveSuppier(@RequestBody String Quetable,HttpSession session,HttpServletResponse response,HttpServletRequest request) throws Exception
	{
		final CreditCardService creditCardService=new CreditCardService(getCurrentContext());
		final CreditCardDao creditCardDao=new CreditCardDao(getCurrentContext());
		final SyncQueueService SyncQueueService=new SyncQueueService(getCurrentContext());

		JsonParser parser1 = new JsonParser();
		final String DB_TXN_POINT = "creditCardService";
		Gson gson = new Gson();
		CreditCard crd=null;
		SyncQueue syncQueue=null;
		creditCardService.beginTrans(DB_TXN_POINT);
		
		try
		{
			crd = super.saveData(request);
			
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable=gson.toJson(jsonObject.get("Quetable"));
			 JsonObject QutableData  = (JsonObject) parser1.parse(Qutable);
			
		
			if(crd.getId().toString()!="")	
			{
				syncQueue=new SyncQueue();
				String tableName=creditCardDao.getTable();
				String bathId=SyncQueueService.fungetSynQueId(tableName,crd.getId().toString());
				if(bathId!="")
				{
					syncQueue.setId(Integer.parseInt(bathId));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(crd.getId());
				
				SyncQueueService.save(syncQueue);
				
				
			}
			
			
			
			
			
			response.getWriter().print("1");
			
			creditCardService.endTrans(DB_TXN_POINT);	
		}
		catch (Exception e) {
			response.getWriter().print("0");
			creditCardService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveCreditCard"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
		
		
		
	}
	
	
	@Override
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final SyncQueueService SyncQueueService=new SyncQueueService(getCurrentContext());
		final CreditCardDao creditCardDao=new CreditCardDao(getCurrentContext());
		final CreditCardService creditCardService=new CreditCardService(getCurrentContext());
		final String DB_TXN_POINT = "creditCardService";
		final String id = request.getParameter("id");
		
		creditCardService.beginTrans(DB_TXN_POINT);
		try {
			Integer is_deleted = 0;
			Integer is_sync=0;
			is_deleted = creditCardService.delete(id);
			if (is_deleted == 0 ) {
				response.getWriter().print("0");
			}
			else
			{
				is_sync=SyncQueueService.updateSyncQueue(id,creditCardDao.getTable());
				response.getWriter().print("1");
			}
			creditCardService.endTrans(DB_TXN_POINT);
		
	}
		 catch (Exception e) {	
			 creditCardService.rollbackTrans(DB_TXN_POINT);
				logger.error("Method: delete in"+this+Throwables.getStackTraceAsString(e));
				throw new CustomException();
			}
	
	}
	
	
	@RequestMapping(value = "/editData")
	public void getModalData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int Id=Integer.parseInt(request.getParameter("id"));
		JsonObject jsonResponse = new JsonObject();
		
		final CreditCardService creditCardService=new CreditCardService(getCurrentContext());
		JsonArray editDet=creditCardService.getEditDetails(Id);
		jsonResponse.add("editDet",editDet.get(0));
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());	
	}
	
	

	
}
