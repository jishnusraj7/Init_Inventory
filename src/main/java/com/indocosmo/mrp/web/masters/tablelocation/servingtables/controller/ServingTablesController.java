package com.indocosmo.mrp.web.masters.tablelocation.servingtables.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.company.service.CompanyService;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.tablelocation.model.TableLocation;
import com.indocosmo.mrp.web.masters.tablelocation.service.TableLocationService;
import com.indocosmo.mrp.web.masters.tablelocation.servingtables.dao.ServingTablesDao;
import com.indocosmo.mrp.web.masters.tablelocation.servingtables.model.ServingTables;
import com.indocosmo.mrp.web.masters.tablelocation.servingtables.service.ServingTablesService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping(value="/servingtables")
public class ServingTablesController extends ViewController<ServingTables,ServingTablesDao,ServingTablesService>{
	public static final Logger logger=Logger.getLogger(ServingTablesController.class);

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public ServingTablesService getService() {

		return new ServingTablesService(getCurrentContext());
	}

	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute ItemMaster itemMaster,HttpSession session,Model model)
			throws Exception {
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_ITM");
		model.addAttribute("permission", permission);

		return "/servingtables/list";
	} 

	
	
	@RequestMapping(value = "/getImagesbyTableLoc")
	public void getImagesbyTableLoc(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
       JsonObject jsonResponse = new JsonObject();
       Integer tableLocId=Integer.parseInt(request.getParameter("tableLocId"));
		
		final ServingTablesService tblSrvice=new ServingTablesService(getCurrentContext());
		JsonArray servingTabls=tblSrvice.getTableImages(tableLocId);
		
		
		
		jsonResponse.add("servingTabls", servingTabls);	
		
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());	
		
	}
	
	
	@RequestMapping(value = "/getDataQue")
	public void getDataQue(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		
	}
	
	
	
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response,Model model) throws Exception{

		final CounterService counterService = new CounterService(getCurrentContext()); 

		String CounterWithPrefix = counterService.getNextCounterwithPrefix("serving_tables", "serving_tables");

		response.getWriter().print(CounterWithPrefix);
	}
	
	
	
	
	

	@RequestMapping(value = "/savetblLocation")
	protected @ResponseBody String saveItem(MultipartHttpServletRequest request,
			@RequestParam(value = "data", required = true) String completeData) throws Exception {
		String status="success";
		Gson gson = new Gson();
		JsonParser parser1 = new JsonParser();
		ObjectMapper mapper1 = new ObjectMapper();
		final String DB_TXN_POINT = "tblLocationService";
		
		final TableLocationService tblLocationService=new TableLocationService(getCurrentContext());
		
		JsonObject jobject = parser1.parse(completeData).getAsJsonObject();
		try
		{
			
			TableLocation tblLoc=mapper1.readValue(gson.toJson(jobject.get("itemData")), TableLocation.class);
			tblLocationService.beginTrans(DB_TXN_POINT);
			tblLoc=tblLocationService.saveItem(tblLoc);
			tblLocationService.endTrans(DB_TXN_POINT);
		}
		catch(Exception e)
		{
			status="error";
			e.printStackTrace();
			tblLocationService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method:  saveShop in"+this+Throwables.getStackTraceAsString(e));
		}
		status="status:"+status;
		return gson.toJson(status);	
	}

	
	@RequestMapping(value="saveServngTable")
	protected @ResponseBody void saveServingTable(MultipartHttpServletRequest request,
			@RequestParam(value = "data", required = true) String completeData,HttpServletResponse response) throws Exception {
		
		Gson gson = new Gson();
		JsonParser parser1 = new JsonParser();
		ObjectMapper mapper1 = new ObjectMapper();
		SyncQueue syncQueue=null;
		final String DB_TXN_POINT = "servingTableService";
		
		final ServingTablesService servingTableService=new ServingTablesService(getCurrentContext());
		final ServingTablesDao servingTablesDao=new ServingTablesDao(getCurrentContext());
		final SyncQueueService SyncQueueService =new SyncQueueService(getCurrentContext());
		
		JsonObject jobject = parser1.parse(completeData).getAsJsonObject();
		try
		{
			
			ServingTables servTbl=mapper1.readValue(gson.toJson(jobject.get("tblData")), ServingTables.class);
			JsonObject jsonObject = parser1.parse(gson.toJson(jobject.get("Quetable"))).getAsJsonObject();
			String Qutable=jsonObject.toString();
		    JsonObject QutableData  = (JsonObject) parser1.parse(Qutable);
			
			servingTableService.beginTrans(DB_TXN_POINT);
			servTbl=servingTableService.saveItem(servTbl);
			if(servTbl.getId().toString()!="")	
			{
				syncQueue=new SyncQueue();
				String tableName=servingTablesDao.getTable();
				String bathId=SyncQueueService.fungetSynQueId(tableName,servTbl.getId().toString());
				if(bathId!="")
				{
					syncQueue.setId(Integer.parseInt(bathId));
					syncQueue.setCrudAction("U");
				}else{
					syncQueue.setCrudAction("C");
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(servTbl.getId());
				
				SyncQueueService.save(syncQueue);
				
				
			}
			
			
			servingTableService.endTrans(DB_TXN_POINT);
			JsonObject obj=new JsonObject();
			 Gson gson1 = new Gson();
		     String json = gson.toJson(servTbl);
		     System.out.println(json);
			obj.addProperty("tablDta",json);
			response.getWriter().print(obj);
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			servingTableService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method:  saveShop in"+this+Throwables.getStackTraceAsString(e));
		}
		
		
	}
	
	//Update row position and column position of tables:
	@RequestMapping(value="/saveServingtableList",method=RequestMethod.POST)
	public void save(@RequestBody String tblItems,HttpSession session,HttpServletResponse response,HttpServletRequest request) throws Exception
	{
		final String DB_TXN_POINT = "servingTableService";
		ServingTables tbls=null;
		Integer department_id;
		
		final ServingTablesService servingTableService=new ServingTablesService(getCurrentContext());
		final ServingTablesDao servingTablesDao=new ServingTablesDao(getCurrentContext());
		final SyncQueueService SyncQueueService =new SyncQueueService(getCurrentContext());
		final CompanyService companyService=new CompanyService(getNewContext());
		
		SyncQueue syncQueue=null;
		
		
		servingTableService.beginTrans(DB_TXN_POINT);
		ObjectMapper mapper =new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try
		{
			
			//tbls=mapper.readValue(tblItems, ServingTables.class);
		     
			JsonParser parser = new JsonParser();
			JsonArray tblDtlList = (JsonArray) parser.parse(tblItems);
			String tableName=servingTablesDao.getTable();
			Integer companyID=getCurrentContext().getUser().getCompanyId();
			Integer syncNow=0;
			String origin=companyService.getCompanyByID(companyID).getName();
			
			
			final ArrayList<ServingTables> servTblList = new ArrayList<ServingTables>();
			
			
			for (int i = 0; i < tblDtlList.size(); i++) {
				final ServingTables tblItem = new ServingTables();
				syncQueue=new SyncQueue();
				
				 JsonObject json = (JsonObject) tblDtlList.get(i);
					 
				        tblItem.setId(json.get("id").getAsInt());
				        String bathId=SyncQueueService.fungetSynQueId(tableName,json.get("id").getAsString());
						if(bathId!="")
						{
							syncQueue.setId(Integer.parseInt(bathId));
							syncQueue.setCrudAction("U");
						}else
						{
							syncQueue.setCrudAction("C");
						}
				        tblItem.setCode(json.get("code").getAsString());
				        tblItem.setName(json.get("name").getAsString());
				        tblItem.setCovers(json.get("covers").getAsLong());
				        tblItem.setRow_position(json.get("row_position").getAsInt());
				        tblItem.setColumn_position(json.get("column_position").getAsInt());
				        tblItem.setServing_table_location_id(json.get("serving_table_location_id").getAsInt());
				        tblItem.setLayout_image(json.get("layout_image").getAsInt());
				        
				        servTblList.add(tblItem);
						
				        syncQueue.setShopId(companyID);
						syncQueue.setOrigin(origin);
						
						syncQueue.setSyncStatus(syncNow);
						
						syncQueue.setTableName(tableName);
						syncQueue.setRecordId(json.get("id").getAsInt());
						
						SyncQueueService.save(syncQueue);
			}
			servingTableService.save(servTblList);
			
			servingTableService.endTrans(DB_TXN_POINT);
			/*JsonObject obj=new JsonObject();
			obj.addProperty("prodId",dailyProd.getId());*/
			
			response.getWriter().print("1");
			
			
			
			
		}catch(Exception e)
		{
			response.getWriter().print("0");
			servingTableService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method:  save in"+this+Throwables.getStackTraceAsString(e));
		}
	}
	
	
	
	@Override
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		


		final SyncQueueService syncQueueService = new SyncQueueService(
				getCurrentContext());
		final ServingTablesDao supplierDao = new ServingTablesDao(getCurrentContext());
		final ServingTablesService supplierService = new ServingTablesService(getCurrentContext());
		final String DB_TXN_POINT = "supplierService";
		final String id = request.getParameter("id");

		supplierService.beginTrans(DB_TXN_POINT);
		try {

			Integer is_sync = 0;
			Integer is_deleted = 0;
			is_deleted = supplierService.deleteServTable(id);

			if (is_deleted == 0) {

				response.getWriter().print("0");
			} else {
				is_sync = syncQueueService.updateSyncQueue(id,supplierDao.getTable());
				JsonObject obj=new JsonObject();
				obj.addProperty("deletdId",id);
				response.getWriter().print(obj);
				
			}

			supplierService.endTrans(DB_TXN_POINT);
		} catch (Exception e) {

			supplierService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this
					+ Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	
	}	
	

	
	
	
	
	

}
