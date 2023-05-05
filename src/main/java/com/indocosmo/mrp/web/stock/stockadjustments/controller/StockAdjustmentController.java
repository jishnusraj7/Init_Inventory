package com.indocosmo.mrp.web.stock.stockadjustments.controller;

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
import com.indocosmo.mrp.web.report.currentstock.service.ItemStockService;
import com.indocosmo.mrp.web.stock.stockadjustments.dao.StockAdjustmentDao;
import com.indocosmo.mrp.web.stock.stockadjustments.model.StockAdjustment;
import com.indocosmo.mrp.web.stock.stockadjustments.service.StockAdjustmentService;
import com.indocosmo.mrp.web.stock.stockadjustments.stockadjustmentdetail.model.StockAdjustmentDetail;
import com.indocosmo.mrp.web.stock.stockadjustments.stockadjustmentdetail.service.StockAdjustmentDetailService;
import com.indocosmo.mrp.web.stock.stockdisposal.controller.StockDisposalController;
import com.indocosmo.mrp.web.stock.stockdisposal.model.StockDisposal;
import com.indocosmo.mrp.web.stock.stockdisposal.stockdisposaldetail.service.StockDisposalDetailService;
import com.indocosmo.mrp.web.stock.stockin.dao.StockInDao;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.service.StockRegisterDetailService;
import com.indocosmo.mrp.web.stock.stockregister.model.StockRegister;
import com.indocosmo.mrp.web.stock.stockregister.service.StockRegisterService;

@Controller
@RequestMapping("/stockadjustment")
public class StockAdjustmentController extends
		ViewController<StockAdjustment, StockAdjustmentDao, StockAdjustmentService> {
	
	public static final Logger logger = Logger.getLogger(StockDisposalController.class);
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public StockAdjustmentService getService() {
	
		return new StockAdjustmentService(getCurrentContext());
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
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("stock_adjust_hdr", "stock_adjust_hdr");
		model.addAttribute("REFNO", CounterWithPrefix);
		model.addAttribute("Store",true);
		model.addAttribute("stockadjstclass",true);
		model.addAttribute("Stock",true);
		return "/stockadjustments/list";
	}
	
	/**
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCounterPrefix")
	public void getCounterPrefix(HttpServletResponse response , Model model) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("stock_adjust_hdr", "stock_adjust_hdr");
		response.getWriter().print(CounterWithPrefix);
	}
	
	/**
	 * @param invoiceItems
	 * @param session
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveStockAdjustment", method = RequestMethod.POST)
	public void save(@RequestBody String invoiceItems , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {
	
		final StockAdjustmentService stockAdjustmentService = new StockAdjustmentService(getCurrentContext());
		final StockRegisterDetailService stockRegisterDetailService = new StockRegisterDetailService(
				getCurrentContext());
		final ItemMasterBatchService itemMasterBatchService = new ItemMasterBatchService(getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		final StockAdjustmentDetailService stockAdjustmentDetailService = new StockAdjustmentDetailService(
				getCurrentContext());
		Integer sourc_cmpny_id=(Integer) session.getAttribute("COMPANY_ID");

		final String DB_TXN_POINT = "stockAdjustmentService";
		
		StockAdjustment item = null;
		StockRegister stockReg = new StockRegister();
		
		stockAdjustmentService.beginTrans(DB_TXN_POINT);
		
		Double cur_stock_in_batch = 0.00;
		
		try {
			item = super.saveData(request);
			item.setApprovalStatus(Integer.parseInt(request.getParameter("approvalStatus")));
			stockReg.setExtRefNo(item.getId().toString());
			stockReg.setExtRefId(item.getId());
			stockReg.setDepartmentId(item.getDepartmentId());
			//stockReg.setTxnDate(item.getAdjust_date());
			stockReg.setTxnDate(item.getApproval_date());
			stockReg.setTransType(transactionType.ADJUSTMENT.gettransactionTypeId());
			stockReg.setSource_shop_id(sourc_cmpny_id);
			stockReg.setDestination_shop_id(sourc_cmpny_id);
			if (item.getApprovalStatus() == 1) {
				
				stockReg = stockRegisterService.saveStockRegData(stockReg);
			}
			
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(invoiceItems);
			final ArrayList invoiceItemsList = (ArrayList) json.get("invoiceItems");
			
			ArrayList<StockAdjustmentDetail> stockAdjustmentItemsList = new ArrayList<StockAdjustmentDetail>();
			ArrayList<StockRegisterDetail> stockRegisterItemList = new ArrayList<StockRegisterDetail>();
			final ArrayList<ItemMasterBatch> stockItembatchList = new ArrayList<ItemMasterBatch>();
			
			for (int i = 0; i < invoiceItemsList.size(); i++) {
				JSONObject obj = (JSONObject) invoiceItemsList.get(i);
				
				final StockAdjustmentDetail stockAdjustmentItem = new StockAdjustmentDetail();
				final StockRegisterDetail stockRegisterItem = new StockRegisterDetail();
				final ItemMasterBatch itemMasterBatch = new ItemMasterBatch();
				
				stockAdjustmentItem.setId((Integer.parseInt(obj.get("id").toString()) != 0 && obj.get("id").toString()
						.length() != 0) ? Integer.parseInt(obj.get("id").toString()) : null);
				stockAdjustmentItem.setStock_adjust_hdr_id(item.getId());
				stockAdjustmentItem.setStock_item_id(Integer.parseInt(obj.get("stock_item_id").toString()));
				stockAdjustmentItem.setStock_item_code(obj.get("stock_item_code").toString());
				stockAdjustmentItem.setStock_item_name(obj.get("stock_item_name").toString());
				stockAdjustmentItem.setSystem_qty(Double.parseDouble(obj.get("currentStock").toString()));
				stockAdjustmentItem.setActual_qty(Double.parseDouble(obj.get("actual_qty").toString()));
				stockAdjustmentItem.setDiff_qty(Double.parseDouble(obj.get("diff_qty").toString()));
				stockAdjustmentItem.setAdjust_qty(Double.parseDouble(obj.get("diff_qty").toString()));
				stockAdjustmentItem.setCost_price(Double.parseDouble(obj.get("rate").toString()));
				stockAdjustmentItemsList.add(stockAdjustmentItem);
				
				stockRegisterItem.setCostPrice(Double.parseDouble(obj.get("rate").toString()));
				stockRegisterItem.setStockItemCode(obj.get("stock_item_code").toString());
				stockRegisterItem.setStockItemId(Integer.parseInt(obj.get("stock_item_id").toString()));
				stockRegisterItem.setStockItemName(obj.get("stock_item_name").toString());
				stockRegisterItem.setStockRegHdrid(stockReg.getId());
				stockRegisterItem.setExtRefDtlId(item.getId());
				stockRegisterItem.setDepartment_id(stockReg.getDepartmentId());
				
				if (obj.get("stock_item_batch_id") != null && obj.get("stock_item_batch_id").toString().length() != 0) {
					itemMasterBatch.setId(Integer.parseInt(obj.get("stock_item_batch_id").toString()));
					cur_stock_in_batch = itemMasterBatchService.getCurrentStockInBatch(itemMasterBatch);
					
				}
				
				if (stockAdjustmentItem.getSystem_qty() > stockAdjustmentItem.getActual_qty()) {
					
					stockRegisterItem.setOutQty(stockAdjustmentItem.getDiff_qty());
					stockRegisterItem.setInQty(0.0);
					itemMasterBatch.setStock(cur_stock_in_batch - stockAdjustmentItem.getDiff_qty());
				}
				else {
					
					stockRegisterItem.setInQty(stockAdjustmentItem.getDiff_qty());
					stockRegisterItem.setOutQty(0.0);
					itemMasterBatch.setStock(cur_stock_in_batch + stockAdjustmentItem.getDiff_qty());
				}
				stockRegisterItem.setApproval_qty(stockAdjustmentItem.getDiff_qty());
				stockRegisterItem.setApproval_status(1);
				stockRegisterItemList.add(stockRegisterItem);
				
				itemMasterBatch.setStockItemId(Integer.parseInt(obj.get("stock_item_id").toString()));
				itemMasterBatch.setCostPrice(Double.parseDouble(obj.get("rate").toString()));
				
				stockItembatchList.add(itemMasterBatch);
			}
			
			stockAdjustmentDetailService.save(stockAdjustmentItemsList);
			
			if (item.getApprovalStatus() == 1) {
				
				stockRegisterDetailService.save(stockRegisterItemList);
				itemMasterBatchService.save(stockItembatchList);
			}
			
			stockAdjustmentService.endTrans(DB_TXN_POINT);
			response.getWriter().print("1");
		}
		catch (Exception e) {
			
			stockAdjustmentService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: save in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}
	
	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "getstockregdtlid")
	public void getstockregdtlid(HttpServletRequest request , ItemMasterBatch item , HttpServletResponse response ,
			Model model) throws Exception {
	
		final StockInDao stockInService = new StockInDao(getCurrentContext());
		
		JsonArray stockRegDtlid = stockInService.getStockregdtlData(item.getId());
		JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.add("stockRegDtlid", stockRegDtlid);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		Double cur_stock_in_batch = 0.00;
		
		final StockAdjustmentService stockAdjustmentService = new StockAdjustmentService(getCurrentContext());
		final StockRegisterDetailService stockRegisterDetailService = new StockRegisterDetailService(
				getCurrentContext());
		final ItemMasterBatchService itemMasterBatchService = new ItemMasterBatchService(getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		final StockAdjustmentDetailService stockAdjustmentDetailService = new StockAdjustmentDetailService(
				getCurrentContext());
		
		final String id = request.getParameter("id");
		final String[] batch = request.getParameterValues("batch");
		final String stockreg_id = request.getParameter("stockreg_id");
		final String ext_ref_no = request.getParameter("ext_ref_no");
		
		final String DB_TXN_POINT = "stockDisposalService";
		stockAdjustmentService.beginTrans(DB_TXN_POINT);
		
		try {
			
			Integer is_deleted = 0;
			is_deleted = stockAdjustmentService.delete(id);
			
			if (is_deleted == 0) {
				
				response.getWriter().print("0");
			}
			else {
				
				StockAdjustmentDetail stockAdjustmentDtl = new StockAdjustmentDetail();
				stockAdjustmentDtl.setStock_adjust_hdr_id(Integer.parseInt(id));
				stockAdjustmentDetailService.delete(stockAdjustmentDtl);
				
				StockRegister stockReg = new StockRegister();
				stockReg.setExtRefNo(ext_ref_no);
				stockReg.setExtRefId(Integer.parseInt(id));
				
				if (stockreg_id != "") {
					
					stockRegisterService.delete(stockReg);
				}
				
				StockRegisterDetail stockRegDet = new StockRegisterDetail();
				
				if (stockreg_id != "") {
					
					stockRegDet.setStockRegHdrid(Integer.parseInt(stockreg_id));
					
					stockRegisterDetailService.delete(stockRegDet);
				}
				
				final ArrayList<ItemMasterBatch> itembatchList = new ArrayList<ItemMasterBatch>();
				
				for (int i = 0; i < batch.length; i++) {
					
					ItemMasterBatch itemMasterBatch = new ItemMasterBatch();
					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(batch[i]);
					itemMasterBatch.setId(Integer.valueOf(json.get("stock_item_batch_id").toString()));
					cur_stock_in_batch = itemMasterBatchService.getCurrentStockInBatch(itemMasterBatch);
					itemMasterBatch.setStockItemId(Integer.valueOf(json.get("stock_item_id").toString()));
					itemMasterBatch.setCostPrice(Double.valueOf(json.get("cost_price").toString()));
					itemMasterBatch.setStock(cur_stock_in_batch + Double.valueOf(json.get("adjust_qty").toString()));
					itembatchList.add(itemMasterBatch);
				}
				
				itemMasterBatchService.upadteItemMasterBatch(itembatchList);
				
				response.getWriter().print("1");
			}
			
			stockAdjustmentService.endTrans(DB_TXN_POINT);
			
		}
		catch (Exception e) {
			
			stockAdjustmentService.rollbackTrans(DB_TXN_POINT);
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
		
		JsonArray stockItmData = itemMasterService.getMastersRowJson();
		JsonArray depData = departmentService.getMastersRowJson();
		
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("stockItmData", stockItmData);
		jsonResponse.add("depData", depData);
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
	protected StockAdjustment setModelItem(HttpServletRequest request , StockAdjustment item) throws Exception {
	
		final CounterService counterService = new CounterService(getCurrentContext());
		
		for (Field field : ReflectionUtil.getAllFileds(item.getClass())) {
			
			Column annotationColumn = (Column) field.getDeclaredAnnotation(Column.class);
			
			if (annotationColumn != null) {
				
				final String fieldName = annotationColumn.name();
				String valueString = request.getParameter(fieldName);
				
				if (fieldName.equals("ref_no")) {
					
					if (request.getParameter("id") == null) {
						
						valueString = counterService.getNextCounterwithPrefix("stock_adjust_hdr", "stock_adjust_hdr");
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
	 * @param stockAdjst
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getStockAdjstDtl")
	public void getStockInDtlData(@ModelAttribute StockAdjustment stockAdjst , HttpServletResponse response)
			throws Exception {
	
		final StockAdjustmentService stockAdjstService = new StockAdjustmentService(getCurrentContext());
		JsonArray stkAdjstDtl = stockAdjstService.getStockAdjstDtlData(stockAdjst);
		JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.add("stkAdjstDtl", stkAdjstDtl);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
}
