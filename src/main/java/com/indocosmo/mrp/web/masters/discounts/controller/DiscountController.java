package com.indocosmo.mrp.web.masters.discounts.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.discounts.dao.DiscountDao;
import com.indocosmo.mrp.web.masters.discounts.model.Discount;
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.dao.SaleItemDiscountDao;
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.model.SaleItemDiscount;
import com.indocosmo.mrp.web.masters.discounts.saleitemdiscounts.service.SaleItemDiscountService;
import com.indocosmo.mrp.web.masters.discounts.service.DiscountService;
import com.indocosmo.mrp.web.masters.syncqueue.model.SyncQueue;
import com.indocosmo.mrp.web.masters.syncqueue.service.SyncQueueService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;

@Controller
@RequestMapping(value = "/discounts")
public class DiscountController extends ViewController<Discount, DiscountDao, DiscountService> {
	
	public static final Logger logger = Logger.getLogger(DiscountController.class);
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public DiscountService getService() {
	
		return new DiscountService(getCurrentContext());
	}
	
	/**
	 * @param discount
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute Discount discount , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_DISC");
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("discountsclass",true);
		model.addAttribute("Promotions",true);
		
		return "/discounts/list";
		
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("discounts", "discounts");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param Quetable
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveDiscount", method = RequestMethod.POST)
	public void saveDiscount(@RequestBody String Quetable , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final DiscountService discountService = new DiscountService(getCurrentContext());
		final DiscountDao discountDao = new DiscountDao(getCurrentContext());
		final SaleItemDiscountService saleItemDiscountService = new SaleItemDiscountService(getCurrentContext());
		final SaleItemDiscountDao saleItemDiscountDao = new SaleItemDiscountDao(getCurrentContext());
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		
		JsonParser parser1 = new JsonParser();
		final String DB_TXN_POINT = "discountService";
		Gson gson = new Gson();
		Discount discount = null;
		SyncQueue syncQueue = null;
		SaleItemDiscount saleItmDiscount = null;
		discountService.beginTrans(DB_TXN_POINT);
		
		try {
			discount = super.saveData(request);
			
			JsonObject jsonObject = parser1.parse(Quetable).getAsJsonObject();
			String Qutable = gson.toJson(jsonObject.get("Quetable"));
			String saleItemDiscount = gson.toJson(jsonObject.get("SaleItemDiscountData"));
			JsonObject QutableData = (JsonObject) parser1.parse(Qutable);
			JsonArray saleItemDiscountData = (JsonArray) parser1.parse(saleItemDiscount);
			
			if (discount.getId().toString() != "") {
				
				syncQueue = new SyncQueue();
				String tableName = discountDao.getTable();
				String bathId = SyncQueueService.fungetSynQueId(tableName, discount.getId().toString());
				if (bathId != "") {
					syncQueue.setId(Integer.parseInt(bathId));
				}
				syncQueue.setShopId(QutableData.get("shopId").getAsInt());
				syncQueue.setCrudAction(QutableData.get("curdAction").getAsString());
				syncQueue.setOrigin(QutableData.get("origin").getAsString());
				syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
				
				syncQueue.setTableName(tableName);
				syncQueue.setRecordId(discount.getId());
				
				SyncQueueService.save(syncQueue);
				
			}
			String saleItemIds = "";
			if (request.getParameter("id") != null) {
				
				for (int i = 0; i < saleItemDiscountData.size(); i++) {
					saleItemIds += (saleItemIds.isEmpty() ? " " : ",")
							+ saleItemDiscountData.get(i).getAsJsonObject().get("sale_item_id").getAsInt();
				}
				saleItemDiscountService.deleteSaleDiscounts(saleItemIds, discount.getId());
			}
			
			if (saleItemDiscountData.size() != 0) {
				String tableName = saleItemDiscountDao.getTable();
				for (int i = 0; i < saleItemDiscountData.size(); i++) {
					saleItmDiscount = new SaleItemDiscount();
					Double price = saleItemDiscountData.get(i).getAsJsonObject().get("price").getAsDouble();
					Integer sale_item_id = saleItemDiscountData.get(i).getAsJsonObject().get("sale_item_id").getAsInt();
					Integer saleItmDiscountId = saleItemDiscountService.IsSaleItemExist(discount.getId(), sale_item_id);
					if (saleItmDiscountId != null) {
						saleItmDiscount.setId(saleItmDiscountId);
					}
					saleItmDiscount.setDiscount_id(discount.getId());
					saleItmDiscount.setPrice(price);
					saleItmDiscount.setSale_item_id(sale_item_id);
					saleItemDiscountService.save(saleItmDiscount);
					if (saleItmDiscount.getId().toString() != "") {
						syncQueue = new SyncQueue();
						String bathId = SyncQueueService.fungetSynQueId(tableName, saleItmDiscount.getId().toString());
						if (bathId != "") {
							syncQueue.setId(Integer.parseInt(bathId));
							syncQueue.setCrudAction("U");
						}
						else {
							syncQueue.setCrudAction("C");
						}
						syncQueue.setShopId(QutableData.get("shopId").getAsInt());
						
						syncQueue.setOrigin(QutableData.get("origin").getAsString());
						
						syncQueue.setSyncStatus(QutableData.get("syncNow").getAsInt());
						
						syncQueue.setTableName(tableName);
						syncQueue.setRecordId(saleItmDiscount.getId());
						
						SyncQueueService.save(syncQueue);
					}
					
				}
			}
			
			response.getWriter().print("1");
			
			discountService.endTrans(DB_TXN_POINT);
		}
		catch (Exception e) {
			
			response.getWriter().print("0");
			discountService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveDiscount" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final SyncQueueService SyncQueueService = new SyncQueueService(getCurrentContext());
		final DiscountDao discountDao = new DiscountDao(getCurrentContext());
		final DiscountService discountService = new DiscountService(getCurrentContext());
		final SaleItemDiscountService saleItemService = new SaleItemDiscountService(getCurrentContext());
		final String DB_TXN_POINT = "discountService";
		final String id = request.getParameter("id");
		discountService.beginTrans(DB_TXN_POINT);
		try {
			Integer is_deleted = 0;
			Integer is_sync = 0;
			is_deleted = discountService.delete(id);
			if (is_deleted == 0) {
				response.getWriter().print("0");
			}
			else {
				is_sync = SyncQueueService.updateSyncQueue(id, discountDao.getTable());
				saleItemService.delete(id);
				saleItemService.deleteSyncQue(Integer.parseInt(id));
				response.getWriter().print("1");
			}
			discountService.endTrans(DB_TXN_POINT);
			
		}
		catch (Exception e) {
			discountService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getSaleItemData")
	public void getSaleItemList(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final DiscountService discountService = new DiscountService(getCurrentContext());
		
		JsonArray json = discountService.getSaleItemJsonArray();
		
		response.getWriter().print(json);
	}
	

	
}
