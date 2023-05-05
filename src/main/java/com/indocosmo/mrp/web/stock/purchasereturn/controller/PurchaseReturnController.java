package com.indocosmo.mrp.web.stock.purchasereturn.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.CombineMode;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.transactionType;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.base.currentdatetime.GettingCurrentDateTime;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.companyprofile.model.CompanyProfile;
import com.indocosmo.mrp.web.masters.department.model.Department;
import com.indocosmo.mrp.web.masters.department.service.DepartmentService;
import com.indocosmo.mrp.web.masters.itemmaster.service.ItemMasterService;
import com.indocosmo.mrp.web.masters.supplier.service.SupplierService;
import com.indocosmo.mrp.web.masters.systemsettings.model.SystemSettings;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.report.common.model.Report;
import com.indocosmo.mrp.web.stock.purchasereturn.dao.PurchaseReturnDao;
import com.indocosmo.mrp.web.stock.purchasereturn.model.PurchaseReturn;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.model.PurchaseReturnDetail;
import com.indocosmo.mrp.web.stock.purchasereturn.purchasereturndetails.service.PurchaseReturnDetailService;
import com.indocosmo.mrp.web.stock.purchasereturn.service.PurchaseReturnService;
import com.indocosmo.mrp.web.stock.stockin.controller.StockInController;
import com.indocosmo.mrp.web.stock.stockin.model.StockIn;
import com.indocosmo.mrp.web.stock.stockin.stockindetail.model.StockInDetail;
import com.indocosmo.mrp.web.stock.stockout.model.StockOut;
import com.indocosmo.mrp.web.stock.stockout.service.StockOutService;
import com.indocosmo.mrp.web.stock.stockout.stockoutdetail.service.StockOutDetailService;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.service.StockRegisterDetailService;
import com.indocosmo.mrp.web.stock.stockregister.model.StockRegister;
import com.indocosmo.mrp.web.stock.stockregister.service.StockRegisterService;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/purchasereturn")
public class PurchaseReturnController extends ViewController<PurchaseReturn, PurchaseReturnDao, PurchaseReturnService>{

	public static final Logger logger = Logger.getLogger(PurchaseReturnController.class);

	@Override
	public PurchaseReturnService getService() {

		return new PurchaseReturnService(getCurrentContext());
	}

	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute PurchaseReturn purchaseReturn , HttpSession session , Model model) throws Exception {

		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();

		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session,"STR_PROD_RETURN");		
		model.addAttribute("permission", permission);
		model.addAttribute("Store",true);
		model.addAttribute("stockReturnclass",true);
		model.addAttribute("Stock",true);
		return "/purchasereturn/list";

	}

	@RequestMapping(value = "/formJsonData")
	public void getFormJsonData(HttpServletRequest request , HttpServletResponse response) throws Exception{

		final SupplierService supplierservice = new SupplierService(getCurrentContext());
		final ItemMasterService itemmasterservice = new ItemMasterService(getCurrentContext());
		final DepartmentService departmentService = new DepartmentService(getCurrentContext());

		JsonArray depData = departmentService.getMastersRowJson();
		JsonArray supplier_data = supplierservice.getMastersRowJson();
		JsonArray items_data = itemmasterservice.getMastersRowJson();

		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("depData", depData);
		jsonResponse.add("supplier_data", supplier_data);
		jsonResponse.add("items_data", items_data);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "/getCounterPrefix")
	public void getCounterPrefix(HttpServletResponse response , Model model) throws Exception {

		final CounterService counterService = new CounterService(getCurrentContext());		
		String CounterWithPrefix = counterService.getNextCounterwithPrefix("stock_return_hdr", "stock_return_hdr");
		response.getWriter().print(CounterWithPrefix);
	}

	@SuppressWarnings({ "rawtypes", "null" })
	@RequestMapping(value = "/savereturn")
	public void savePurchaseReturn(@RequestBody String returnDtl , HttpSession session, HttpServletRequest request ,
			HttpServletResponse response) throws Exception{

		PurchaseReturn purchaseReturnHdr = null;
		int RETURN_STATUS_APPROVAL = 1;
		int RETURN_STATUS_SAVE=0;
		final String DB_TXN_POINT = "purchaseReturnService";		
		PurchaseReturnService purchaseReturnService = new PurchaseReturnService(getCurrentContext());
		PurchaseReturnDetailService purchaseReturnDetailService = new PurchaseReturnDetailService(getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		final StockRegisterDetailService stockRegisterDetailService = new StockRegisterDetailService(getCurrentContext());
		final CounterService counterService = new CounterService(getCurrentContext());

		Integer source_shop_id = (Integer) session.getAttribute("COMPANY_ID");
		Department department = (Department) session.getAttribute("department");
		Integer cmbnmode=(Integer) session.getAttribute("combineMode");
		purchaseReturnService.beginTrans(DB_TXN_POINT);
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try{
			purchaseReturnHdr = mapper.readValue(returnDtl, PurchaseReturn.class);
			String counterWithPrefix = counterService.getNextCounterwithPrefix("stock_return_hdr", "stock_return_hdr");
			if(purchaseReturnHdr.getReturn_status()==RETURN_STATUS_SAVE) {
				if (purchaseReturnHdr.getId() == null)
				{
					if(cmbnmode==CombineMode.SEPARATE.getCombineModeId())
					{
						purchaseReturnHdr.setReturn_no(counterService.getNextCounterwithPrefix("stock_return_hdr", "stock_return_hdr"));
					}
				}
				purchaseReturnHdr.setReturn_no(counterWithPrefix);
			}
			purchaseReturnHdr = purchaseReturnService.saveReturnStock(purchaseReturnHdr);
			String returnData=purchaseReturnHdr.getReturnDetailLists();	
			JsonParser parser = new JsonParser();
			JsonArray returnDtlItemsList = (JsonArray) parser.parse(returnData);

			final ArrayList<PurchaseReturnDetail> returnItemsList = new ArrayList<PurchaseReturnDetail>();

			ArrayList<PurchaseReturnDetail> purchaseReturnDetailList = new ArrayList<PurchaseReturnDetail>();
			ArrayList<StockRegisterDetail> stockRegisterDetailList =  new ArrayList<StockRegisterDetail>();


			for(int i=0; i < returnDtlItemsList.size(); i++){

				JsonObject jsonObject = (JsonObject) returnDtlItemsList.get(i);
				PurchaseReturnDetail purchaseReturnDetail = new PurchaseReturnDetail();
				purchaseReturnDetail.setStock_return_hdr_id(purchaseReturnHdr.getId());
				purchaseReturnDetail.setStock_item_id((jsonObject.get("stock_item_id").getAsInt()));
				purchaseReturnDetail.setStock_item_code(jsonObject.get("stock_item_code").getAsString());
				purchaseReturnDetail.setStock_item_name(jsonObject.get("stock_item_name").getAsString());
				purchaseReturnDetail.setReturn_qty(jsonObject.get("return_qty").getAsDouble());
				purchaseReturnDetail.setUomcode(jsonObject.get("uomcode").getAsString());
				purchaseReturnDetail.setUomcode(jsonObject.get("uomcode").getAsString());
				purchaseReturnDetail.setBase_uom_code(jsonObject.get("base_uom_code").getAsString());
				purchaseReturnDetail.setCompound_unit(jsonObject.get("compound_unit").getAsDouble());
				purchaseReturnDetail.setIssued_qty(jsonObject.get("unit_qty").getAsDouble());
				purchaseReturnDetailList.add(purchaseReturnDetail);	
			}
			purchaseReturnDetailService.save(purchaseReturnDetailList);

			if(purchaseReturnHdr.getReturn_status() == RETURN_STATUS_APPROVAL){

				StockRegister stockRegister = new StockRegister();
				stockRegister.setExtRefNo(purchaseReturnHdr.getReturn_no());
				stockRegister.setExtRefId(purchaseReturnHdr.getId());
				stockRegister.setTxnDate(purchaseReturnHdr.getReturn_date());
				stockRegister.setTransType(transactionType.RETURN.gettransactionTypeId());				
				stockRegister.setSource_shop_id(source_shop_id);
				stockRegister.setDestination_shop_id(source_shop_id);
				stockRegister = stockRegisterService.saveStockRegData(stockRegister);

				for (int i = 0; i < returnDtlItemsList.size(); i++) {
					JsonObject jsonObject = (JsonObject) returnDtlItemsList.get(i);
					StockRegisterDetail stockRegisterDetail = new StockRegisterDetail();
					stockRegisterDetail.setStockRegHdrid(stockRegister.getId());
					stockRegisterDetail.setExtRefDtlId(purchaseReturnDetailList.get(i).getId());
					stockRegisterDetail.setStockItemId(jsonObject.get("stock_item_id").getAsInt());
					stockRegisterDetail.setDepartment_id(department.getId());
					stockRegisterDetail.setStockItemCode(jsonObject.get("stock_item_code").getAsString());
					stockRegisterDetail.setStockItemName(jsonObject.get("stock_item_name").getAsString());
					stockRegisterDetail.setInQty(0.00);
					stockRegisterDetail.setOutQty(jsonObject.get("return_qty").getAsDouble());
					stockRegisterDetail.setApproval_qty(jsonObject.get("return_qty").getAsDouble());
					stockRegisterDetail.setApproval_status(1);				
					stockRegisterDetail.setCostPrice(0.00);
					stockRegisterDetailList.add(stockRegisterDetail);
				}
				stockRegisterDetailService.save(stockRegisterDetailList);
			}
			purchaseReturnService.endTrans(DB_TXN_POINT);
			response.getWriter().print("1");
		}catch(Exception e){
			purchaseReturnService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: save in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}

	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {

		String stock_return_hdr_id = request.getParameter("stock_return_hdr_id");
		final String DB_TXN_POINT = "PurchaseReturnService";
		final PurchaseReturnService purchaseReturnService = new PurchaseReturnService(getCurrentContext());
		final PurchaseReturnDetailService purchaseReturnDetailService = new PurchaseReturnDetailService(getCurrentContext());
		Integer is_hdr_deleted = 0;
		Integer is_dtl_deleted = 0;

		purchaseReturnService.beginTrans(DB_TXN_POINT);		
		try {	
			is_hdr_deleted = purchaseReturnService.delete(stock_return_hdr_id);			
			if (is_hdr_deleted == 0)				
				response.getWriter().print("0");			
			else {
				is_dtl_deleted = purchaseReturnDetailService.delete(stock_return_hdr_id);
				response.getWriter().print(is_dtl_deleted == 0 ? "0" : "1");
			}
			purchaseReturnService.endTrans(DB_TXN_POINT);
		}catch(Exception e){
			purchaseReturnService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: delete in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}


	@RequestMapping(value = "/getReturnDetailData")
	public void getPurchaseReturnDetailData(HttpServletRequest request , HttpServletResponse response) throws Exception{

		String stock_return_hdr_id = request.getParameter("stock_return_hdr_id");
		PurchaseReturnDetailService purchaseReturnDetailService = new PurchaseReturnDetailService(getCurrentContext());
		JsonArray ReturnDtlData = purchaseReturnDetailService.getPurchaseReturnDetailData(Integer.parseInt(stock_return_hdr_id));

		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("ReturnDtlData", ReturnDtlData);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	/*@gana  30032020*/
	@RequestMapping(value="/checkReturnStock")
	public void checkReturnStock(@RequestBody String stockData,HttpServletRequest request,HttpServletResponse response) throws Exception {

		PurchaseReturnService purchaseService=new PurchaseReturnService(getCurrentContext());
		JsonParser jsonParser = new JsonParser();
		JsonObject returnJson = jsonParser.parse(stockData).getAsJsonObject();
		JsonElement jsonStock = returnJson.get("stock");
		Type listType = new TypeToken<List<String>>() {}.getType();
		List<String> stockList = new Gson().fromJson(jsonStock, listType);
		String supplier = returnJson.get("supplier").getAsString();
		List<PurchaseReturnDetail> checkStatus=purchaseService.getStockReturn(stockList,supplier);
		JsonObject responseObj=new JsonObject();
		Gson gson = new Gson();
		JsonElement elementResponse = gson.toJsonTree(checkStatus, new TypeToken<List<PurchaseReturnDetail>>() {}.getType());
		JsonArray responseArray = elementResponse.getAsJsonArray();
		if(checkStatus.size()==0) {
			response.getWriter().print("0");
		}else {

			responseObj.add("stockData",responseArray);
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(responseObj.toString());
		}		
	}

}
