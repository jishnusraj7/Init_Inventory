package com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

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

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.dao.TableImageDao;
import com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.model.TableImage;
import com.indocosmo.mrp.web.masters.tablelocation.servingtableimages.service.TableImageService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping(value="/tableimages")
public class TableImageController extends ViewController<TableImage,TableImageDao,TableImageService>{
	public static final Logger logger=Logger.getLogger(TableImageController.class);

	/* (non-Javadoc)
	 * @see com.indocosmo.mrp.web.core.base.controller.BaseController#getService()
	 */
	@Override
	public TableImageService getService() {

		return new TableImageService(getCurrentContext());
	}

	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute ItemMaster itemMaster,HttpSession session,Model model)
			throws Exception {
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_ITM");
		model.addAttribute("permission", permission);

		return "/tablelocation/list";
	} 


	
	@RequestMapping(value = "/getTableImages")
	public void getDataToEditChoice(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
	}
	
	
	@RequestMapping(value = "/getDataQue")
	public void getDataQue(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
	}
	
	
	
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response,Model model) throws Exception{

		final CounterService counterService = new CounterService(getCurrentContext()); 

		String CounterWithPrefix = counterService.getNextCounterwithPrefix("serving_table_location", "serving_table_location");

		response.getWriter().print(CounterWithPrefix);
	}
	
	
	
	
	

	@RequestMapping(value = "/savetblImages")
	
	protected @ResponseBody void saveItem(MultipartHttpServletRequest request,
			@RequestParam(value = "imgFile", required = false) MultipartFile file1,
			@RequestParam(value = "data", required = false) String completeData,HttpServletResponse reponse) throws Exception {
		
		Gson gson = new Gson();
		JsonParser parser1 = new JsonParser();
		final String DB_TXN_POINT = "tblImageService";
		
		final TableImageService tblImageService=new TableImageService(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final TableImageDao tableImageDao=new TableImageDao(getCurrentContext());
		SyncQueue syncQueue=null;
		
		JsonObject jobject = parser1.parse(completeData).getAsJsonObject();
		
		String rootPath = System.getProperty("catalina.home");
		String absolutePath = "/mrp_uploads/serving_table_images/";
		String rootDirectory = rootPath  + "/webapps/" + absolutePath;
		
		File directory = new File(rootDirectory);
		
		if (! directory.exists()){
	        directory.mkdirs();
	       
	    }

		
		try
		{
			
			//TableImage tblLoc=mapper1.readValue(gson.toJson(jobject.get("tableImgData")), TableImage.class);
			TableImage tblLoc=new TableImage();
			String uniqueID=UUID.randomUUID().toString();
			JsonObject jsonObject = parser1.parse(gson.toJson(jobject.get("Quetable"))).getAsJsonObject();
			String Qutable=jsonObject.toString();
		    JsonObject QutableData  = (JsonObject) parser1.parse(Qutable);
		    
			tblImageService.beginTrans(DB_TXN_POINT);
			if (file1 != null) {
				BufferedImage image = ImageIO.read(file1.getInputStream());
				int width = image.getWidth();
				int height = image.getHeight(); 
				file1.transferTo(new File(rootDirectory + uniqueID + "_img.jpg"));
				tblLoc.setHeight(height);
				tblLoc.setWidth(width);
				tblLoc.setImage(absolutePath +uniqueID + "_img.jpg");
			}
			tblLoc=tblImageService.saveItem(tblLoc);
			 if(tblLoc.getId().toString()!="")	
				{
					syncQueue=new SyncQueue();
					String tableName=tableImageDao.getTable();
					String bathId=SyncQueueService.fungetSynQueId(tableName,tblLoc.getId().toString());
					if(bathId!="")
					{
						syncQueue.setId(Integer.parseInt(bathId));
					}
					syncQueue.setShopId(QutableData.get("shopId").getAsInt());
					syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
					syncQueue.setOrigin(QutableData.get("origin").getAsString());
					
					syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
					
					syncQueue.setTableName(tableName);
					syncQueue.setRecordId(tblLoc.getId());
					
					SyncQueueService.save(syncQueue);
					
					
				}
			
			
			JsonObject obj=new JsonObject();
		    String json = gson.toJson(tblLoc);
			obj.addProperty("newImg",json);
			reponse.getWriter().print(obj);
			tblImageService.endTrans(DB_TXN_POINT);
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			tblImageService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method:  saveItem in"+this+Throwables.getStackTraceAsString(e));
		}
		
		
	}


	
	
	
	@Override
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		final TableImageService tableImageService = new TableImageService(getCurrentContext());
		final String DB_TXN_POINT = "tableImageService";
		final String id = request.getParameter("id");

		tableImageService.beginTrans(DB_TXN_POINT);
		try {

			Integer is_deleted = 0;
			is_deleted = tableImageService.delete(id);

			if (is_deleted == 0) {

				response.getWriter().print("0");
			} else {
				
				response.getWriter().print("1");
			}

			tableImageService.endTrans(DB_TXN_POINT);
		} catch (Exception e) {

			tableImageService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this
					+ Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}

	
	
	
	
	

}
