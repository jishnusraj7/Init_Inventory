package com.indocosmo.mrp.web.stock.stockdisposal.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indocosmo.mrp.utils.core.ReflectionUtil;
import com.indocosmo.mrp.utils.core.WebUtil;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.transactionType;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.department.service.DepartmentService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.model.ItemMasterBatch;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbatch.service.ItemMasterBatchService;
import com.indocosmo.mrp.web.masters.itemmaster.service.ItemMasterService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.report.currentstock.service.ItemStockService;
import com.indocosmo.mrp.web.stock.stockdisposal.dao.StockDisposalDao;
import com.indocosmo.mrp.web.stock.stockdisposal.model.StockDisposal;
import com.indocosmo.mrp.web.stock.stockdisposal.service.StockDisposalService;
import com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.model.StockDisposalDetail;
import com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.service.StockDisposalDetailService;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.service.StockRegisterDetailService;
import com.indocosmo.mrp.web.stock.stockregister.model.StockRegister;
import com.indocosmo.mrp.web.stock.stockregister.service.StockRegisterService;

@Controller
@RequestMapping("/stockdisposal")
public class StockDisposalController extends ViewController<StockDisposal, StockDisposalDao, StockDisposalService> {
	
	public static final Logger logger = Logger.getLogger(StockDisposalController.class);
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public StockDisposalService getService() {
	
		return new StockDisposalService(getCurrentContext());
	}
	
	/**
	 * @param stockdisposal
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute StockDisposal stockdisposal , HttpSession session , Model model)
			throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "STR_DISPOSE");
		model.addAttribute("permission", permission);
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("stock_disposal_hdr", "stock_disposal_hdr");
		model.addAttribute("REFNO", CounterWithPrefix);
		model.addAttribute("Store",true);
		model.addAttribute("stockdispclass",true);
		model.addAttribute("Stock",true);
		
		return "/stockdisposal/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCounterPrefix")
	public void getCounterWothPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("stock_disposal_hdr", "stock_disposal_hdr");
		
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param invoiceItems
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveStockDisposal", method = RequestMethod.POST)
	public void save(@RequestBody String invoiceItems , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final StockDisposalService stockDisposalService = new StockDisposalService(getCurrentContext());
		final StockRegisterDetailService stockRegisterDetailService = new StockRegisterDetailService(
				getCurrentContext());
		final ItemMasterBatchService itemMasterBatchService = new ItemMasterBatchService(getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		final StockDisposalDetailService stockDisposalDetailService = new StockDisposalDetailService(
				getCurrentContext());
		Users users=(Users) session.getAttribute("user");
		Integer sourc_cmpny_id=(Integer) session.getAttribute("COMPANY_ID");

		final String DB_TXN_POINT = "stockDisposalService";
		StockDisposal item = null;
		StockRegister stockReg = new StockRegister();
		stockDisposalService.beginTrans(DB_TXN_POINT);
		Double cur_stock_in_batch = 0.00;
		Double cur_stock_out_regDtl = 0.00;
		
		try {
			item = super.saveData(request);
			
			final String stockregId = request.getParameter("stockreg_id");
			
			if (stockregId != "" && stockregId != null) {
				
				stockReg.setId(Integer.parseInt(stockregId));
				stockReg.setCreated_at(item.getDisposalDate());
				stockReg.setCreated_by(users.getId());
			}
			stockReg.setExtRefNo(item.getRefNo());
			stockReg.setExtRefId(item.getId());
			stockReg.setDepartmentId(item.getDepartmentId());
			stockReg.setTxnDate(item.getDisposalDate());
			stockReg.setTransType(transactionType.DISPOSAL.gettransactionTypeId());
			stockReg.setSource_shop_id(sourc_cmpny_id);
			stockReg.setDestination_shop_id(sourc_cmpny_id);
			stockReg = stockRegisterService.saveStockRegData(stockReg);
			
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(invoiceItems);
			final ArrayList invoiceItemsList = (ArrayList) json.get("invoiceItems");
			
			ArrayList<StockDisposalDetail> stockDisposalItemsList = new ArrayList<StockDisposalDetail>();
			ArrayList<StockRegisterDetail> stockRegisterItemList = new ArrayList<StockRegisterDetail>();
			final ArrayList<ItemMasterBatch> stockItembatchList = new ArrayList<ItemMasterBatch>();
			
			for (int i = 0; i < invoiceItemsList.size(); i++) {
				
				JSONObject obj = (JSONObject) invoiceItemsList.get(i);
				
				final StockDisposalDetail stockDisposalItem = new StockDisposalDetail();
				final StockRegisterDetail stockRegisterItem = new StockRegisterDetail();
				final ItemMasterBatch itemMasterBatch = new ItemMasterBatch();
				final Double damagedqty = Double.parseDouble(obj.get("damaged_qty").toString());
				if (obj.get("stock_disposal_id").toString() != ""
						&& obj.get("stock_disposal_id").toString().length() != 0) {
					
					stockDisposalItem.setId(Integer.parseInt(obj.get("stock_disposal_id").toString()));
				}
				
				stockDisposalItem.setStockDisposalHdrId(item.getId());
				stockDisposalItem.setStockItemId(Integer.parseInt(obj.get("stock_item_id").toString()));
				stockDisposalItem.setStockItemCode(obj.get("stock_item_code").toString());
				stockDisposalItem.setStockItemName(obj.get("stock_item_name").toString());
				stockDisposalItem.setDisposeQty(Double.parseDouble(obj.get("damaged_qty").toString()));
				stockDisposalItem.setCostPrice(Double.parseDouble(obj.get("rate").toString()));
				stockDisposalItem.setReason(Integer.parseInt(obj.get("reason_type").toString()));
				stockDisposalItemsList.add(stockDisposalItem);
				
				stockRegisterItem.setCostPrice(Double.parseDouble(obj.get("rate").toString()));
				stockRegisterItem.setStockItemCode(obj.get("stock_item_code").toString());
				stockRegisterItem.setStockItemId(Integer.parseInt(obj.get("stock_item_id").toString()));
				stockRegisterItem.setStockItemName(obj.get("stock_item_name").toString());
				stockRegisterItem.setStockRegHdrid(stockReg.getId());
				stockRegisterItem.setOutQty(Double.parseDouble(obj.get("damaged_qty").toString()));
				stockRegisterItem.setDepartment_id(stockReg.getDepartmentId());
				
				stockRegisterItem.setApproval_qty(Double.parseDouble(obj.get("damaged_qty").toString()));
				stockRegisterItem.setApproval_status(1);
				
				if (obj.get("stockRegDtl_id") != null && obj.get("stockRegDtl_id").toString().length() != 0) {
					
					stockRegisterItem.setId(Integer.parseInt(obj.get("stockRegDtl_id").toString()));
					cur_stock_out_regDtl = stockRegisterDetailService.getOutQtyInRegDtl(stockRegisterItem);
				}
				
				stockRegisterItemList.add(stockRegisterItem);
				
				if (obj.get("stock_item_batch_id") != null && obj.get("stock_item_batch_id").toString().length() != 0) {
					
					itemMasterBatch.setId(Integer.parseInt(obj.get("stock_item_batch_id").toString()));
					cur_stock_in_batch = itemMasterBatchService.getCurrentStockInBatch(itemMasterBatch);
				}
				
				itemMasterBatch.setStockItemId(Integer.parseInt(obj.get("stock_item_id").toString()));
				itemMasterBatch.setStock((cur_stock_in_batch + cur_stock_out_regDtl) - damagedqty);
				itemMasterBatch.setCostPrice(Double.parseDouble(obj.get("rate").toString()));
				
				stockItembatchList.add(itemMasterBatch);
			}
			
			stockDisposalItemsList = (ArrayList<StockDisposalDetail>) stockDisposalDetailService
					.saveStkDsplDtl(stockDisposalItemsList);
			item.setStockDisposalDetails(stockDisposalItemsList);
			
			for (int i = 0; i < stockDisposalItemsList.size(); i++) {
				
				stockRegisterItemList.get(i).setExtRefDtlId(stockDisposalItemsList.get(i).getId());
			}
			
			stockRegisterDetailService.save(stockRegisterItemList);
			itemMasterBatchService.save(stockItembatchList);
			
			stockDisposalService.endTrans(DB_TXN_POINT);
			
			response.getWriter().print("1");
		}
		catch (Exception e) {
			
			stockDisposalService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: save in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
			
		}
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.ViewController#delete(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) */
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		Double cur_stock_in_batch = 0.00;
		
		final StockDisposalService stockDisposalService = new StockDisposalService(getCurrentContext());
		final StockRegisterDetailService stockRegisterDetailService = new StockRegisterDetailService(
				getCurrentContext());
		final ItemMasterBatchService itemMasterBatchService = new ItemMasterBatchService(getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		final StockDisposalDetailService stockDisposalDetailService = new StockDisposalDetailService(
				getCurrentContext());
		
		final String id = request.getParameter("id");
		final String[] batch = request.getParameterValues("batch");
		final String stockreg_id = request.getParameter("stockreg_id");
		final String ext_ref_no = request.getParameter("ext_ref_no");
		final String DB_TXN_POINT = "stockDisposalService";
		
		stockDisposalService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			is_deleted = stockDisposalService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				
				StockDisposalDetail stockDisposalDtl = new StockDisposalDetail();
				stockDisposalDtl.setStockDisposalHdrId(Integer.parseInt(id));
				stockDisposalDetailService.delete(stockDisposalDtl);
				
				StockRegister stockReg = new StockRegister();
				
				stockReg.setExtRefId(Integer.parseInt(id));
				stockReg.setExtRefNo(ext_ref_no);
				stockRegisterService.delete(stockReg);
				
				StockRegisterDetail stockRegDet = new StockRegisterDetail();
				
				stockRegDet.setStockRegHdrid(Integer.parseInt(stockreg_id));
				stockRegisterDetailService.delete(stockRegDet);
				
				final ArrayList<ItemMasterBatch> itembatchList = new ArrayList<ItemMasterBatch>();
				
				for (int i = 0; i < batch.length; i++) {
					
					ItemMasterBatch itemMasterBatch = new ItemMasterBatch();
					
					JSONParser parser = new JSONParser();
					
					JSONObject json = (JSONObject) parser.parse(batch[i]);
					itemMasterBatch.setId(Integer.valueOf(json.get("stock_item_batch_id").toString()));
					cur_stock_in_batch = itemMasterBatchService.getCurrentStockInBatch(itemMasterBatch);
					itemMasterBatch.setStockItemId(Integer.valueOf(json.get("stock_item_id").toString()));
					itemMasterBatch.setCostPrice(Double.valueOf(json.get("cost_price").toString()));
					itemMasterBatch.setStock(cur_stock_in_batch + Double.valueOf(json.get("damaged_qty").toString()));
					itembatchList.add(itemMasterBatch);
				}
				
				itemMasterBatchService.upadteItemMasterBatch(itembatchList);
				
				response.getWriter().print("1");
			}
			
			stockDisposalService.endTrans(DB_TXN_POINT);
			
		}
		catch (Exception e) {
			
			stockDisposalService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
		
	}
	
	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "formJsonData")
	public void getFormJsonData(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {
	
		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		
		JsonArray depData = departmentService.getMastersRowJson();
		JsonArray stockItmData = itemMasterService.getMastersRowJson();
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("depData", depData);
		jsonResponse.add("stockItmData", stockItmData);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
		
	}
	
	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "stockRegJsonData")
	public void getStockregJsonData(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {
	
		final StockDisposalDetailService stockDisposalDetailService = new StockDisposalDetailService(
				getCurrentContext());
		final StockRegisterDetailService stockRegisterDetailService = new StockRegisterDetailService(
				getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		final ItemMasterBatchService itemMasterBatchService = new ItemMasterBatchService(getCurrentContext());
		final ItemStockService itemstock = new ItemStockService(getCurrentContext());
		
		JsonArray stockDispDtlData = stockDisposalDetailService.getJsonArray();
		JsonArray stockRegDtlData = stockRegisterDetailService.getJsonArray();
		JsonArray stockRegData = stockRegisterService.getJsonArray();
		JsonArray stockItmBatchData = itemMasterBatchService.getJsonArray();
		JsonArray stockItmBatchData1 = itemstock.getJsonArray();
		JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.add("stockDispDtlData", stockDispDtlData);
		jsonResponse.add("stockRegDtlData", stockRegDtlData);
		jsonResponse.add("stockRegData", stockRegData);
		jsonResponse.add("stockItmBatchData", stockItmBatchData);
		jsonResponse.add("stockItmBatchData1", stockItmBatchData1);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
		
	}
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.ViewController#setModelItem
	 * (javax.servlet.http.HttpServletRequest,
	 * com.indocosmo.mrp.web.core.base.model.GeneralModelBase) */
	@Override
	protected StockDisposal setModelItem(HttpServletRequest request , StockDisposal item) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		for (Field field : ReflectionUtil.getAllFileds(item.getClass())) {
			
			Column annotationColumn = (Column) field.getDeclaredAnnotation(Column.class);
			
			if (annotationColumn != null) {
				
				final String fieldName = annotationColumn.name();
				String valueString = request.getParameter(fieldName);
				
				if (fieldName.equals("ref_no")) {
					if (request.getParameter("id") == null) {
						valueString = counterService.getNextCounterwithPrefix("stock_disposal_hdr",
								"stock_disposal_hdr");
					}
				}
				
				if (valueString != null) {
					
					Object value = WebUtil.pageFieldToModelValue(field, valueString);
					
					if (value != null) {
						
						field.setAccessible(true);
						field.set(item, value);
					}
				}
				
			}
			
		}
		
		return item;
	}
	
	/**
	 * @param stockDisp
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getStockDispDtl")
	public void getStockInDtlData(@ModelAttribute StockDisposal stockDisp , HttpServletResponse response)
			throws Exception {
	
		final StockDisposalService stockDispService = new StockDisposalService(getCurrentContext());
		JsonArray stkDispDtl = stockDispService.getStockDispDtlData(stockDisp);
		JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.add("stkDispDtl", stkDispDtl);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
}
