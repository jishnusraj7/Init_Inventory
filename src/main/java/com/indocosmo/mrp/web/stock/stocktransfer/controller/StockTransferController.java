package com.indocosmo.mrp.web.stock.stocktransfer.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.CombineMode;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.productionOrderStatusType;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.transactionType;
import com.indocosmo.mrp.web.company.model.Company;
import com.indocosmo.mrp.web.company.service.CompanyService;
import com.indocosmo.mrp.web.core.base.application.ApplicationContext;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.department.service.DepartmentService;
import com.indocosmo.mrp.web.masters.itemmaster.service.ItemMasterService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.production.planning.bookingsummary.model.OrderBoonkingSummary;
import com.indocosmo.mrp.web.production.planning.bookingsummary.service.OrderBookingService;
import com.indocosmo.mrp.web.production.planning.dao.PlanningDao;
import com.indocosmo.mrp.web.production.production.dao.ProductionDao;
import com.indocosmo.mrp.web.stock.purchaseorder.purchaseorderhdr.model.PO_hdr;
import com.indocosmo.mrp.web.stock.stockout.controller.StockOutController;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.service.StockRegisterDetailService;
import com.indocosmo.mrp.web.stock.stockregister.model.StockRegister;
import com.indocosmo.mrp.web.stock.stockregister.service.StockRegisterService;
import com.indocosmo.mrp.web.stock.stocktransfer.dao.StockTransferDao;
import com.indocosmo.mrp.web.stock.stocktransfer.model.StockTransfer;
import com.indocosmo.mrp.web.stock.stocktransfer.service.StockTransferService;
import com.indocosmo.mrp.web.stock.stocktransfer.stocktransferdetail.model.StockTransDetail;
import com.indocosmo.mrp.web.stock.stocktransfer.stocktransferdetail.service.StockTransDetailService;

@Controller
@RequestMapping("/StockTranfer")
public class StockTransferController extends ViewController<StockTransfer, StockTransferDao, StockTransferService>{

	public static final Logger logger = Logger.getLogger(StockOutController.class);

	@Override
	public StockTransferService getService() {

		return new StockTransferService(getCurrentContext());
	}

	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute PO_hdr PO_hdr , HttpSession session , Model model) throws Exception {

		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		final CounterService counterService = new CounterService(getCurrentContext());

		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "STR_TRANS");

		//String counterWithPrefix = counterService.getNextCounterwithPrefix("stock_transfer_hdr", "stock_transfer_hdr");

		model.addAttribute("permission", permission);
	//	model.addAttribute("PONO", counterWithPrefix);
		//model.addAttribute("Store",true);
		model.addAttribute("Production",true);
		//model.addAttribute("Stock",true);
		model.addAttribute("stocktranferclass",true);

		return "/stocktransfer/list";
	}

	@RequestMapping(value = "getCounterPrefix")
	public void getCounterPrefix(HttpServletResponse response , Model model) throws Exception {

		final CounterService counterService = new CounterService(getCurrentContext());
		String counterWithPrefix = counterService.getNextCounterwithPrefix("stock_transfer_hdr", "stock_transfer_hdr");
		System.out.println(counterWithPrefix);
		response.getWriter().print(counterWithPrefix);

	}

	@RequestMapping(value = "formJsonData")
	public void getFormJsonData(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {

		final ItemMasterService itemMasterService = new ItemMasterService(getCurrentContext());
		final DepartmentService departmentService = new DepartmentService(getCurrentContext());
		final PlanningDao planningDao = new PlanningDao(getCurrentContext());
		final ProductionDao productionDao=new ProductionDao(getCurrentContext());

		JsonArray shopsData = planningDao.getCustomerDataAsJson();
		JsonArray customerData = productionDao.getCustomerIdsAsJson();
		JsonArray sourcedepData = departmentService.getMastersRowJson();
		JsonArray stockItmData = itemMasterService.getTransferItems();
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("sourcedepData", sourcedepData);
		jsonResponse.add("departments", sourcedepData);
		jsonResponse.add("stockItmData", stockItmData);
		jsonResponse.add("shopsData", shopsData);
		jsonResponse.add("customerData", customerData);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

	@RequestMapping(value = "getOrderstoTransfer")
	public void getOrderstoTransfer(HttpServletRequest request , HttpServletResponse response) throws Exception {

		JsonArray orderList;
		String customer_id = request.getParameter("customerID");
		String delivery_date = request.getParameter("delevery_date");

		String where_part = "WHERE orderHdr.closing_date = '"+ delivery_date +"' "
				+ "AND orderHdr.is_ar_customer != 1";
		if(customer_id != "" && customer_id != null)
			where_part += " AND shop_code = '"+ customer_id +"'";

		final StockTransferService stockTransferService = new StockTransferService(getCurrentContext());
		orderList = stockTransferService.getOrderstoTransfer(where_part);

		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("totalItemList", orderList);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}

	@RequestMapping(value = "getOrderDtlData")
	public void getOrderDtlData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JsonArray orderDtlList;
		String order_id = request.getParameter("order_id");

		final StockTransferService stockTransferService = new StockTransferService(getCurrentContext());
		orderDtlList = stockTransferService.getOrderDtlData(order_id);

		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("orderDtlList", orderDtlList);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "destdepartment")
	public void getDestdept( HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {



		JsonArray destdepData = new JsonArray();

		if((request.getParameter("dest_cmpny_id")).toString()!= ""){
			Integer cmpany_id=Integer.parseInt(request.getParameter("dest_cmpny_id"));
			final CompanyService companyService = new CompanyService(getNewContext());
			Company currentCompany = new Company();
			currentCompany = companyService.getCompanyByID(cmpany_id);
			final ApplicationContext companyContext = getNewContext(currentCompany);
			final DepartmentService departmentService = new DepartmentService(companyContext);
			destdepData= departmentService.getMastersRowJson();
		}else{
			final DepartmentService departmentService = new DepartmentService(getCurrentContext());
			destdepData= departmentService.getMastersRowJson();
		}


		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("destdepData", destdepData);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());



	}



	@RequestMapping(value = "currentdestdepartment")
	public void getCurrentDestdept(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {



		final DepartmentService departmentService = new DepartmentService(getCurrentContext());

		JsonArray destdepData = departmentService.getMastersRowJson();

		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("destdepData", destdepData);

		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());

	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveStockTransfer", method = RequestMethod.POST)
	public void save(@RequestBody String stockTransferDtl , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {


		final int DEF_DEP_ID = 1;
		final String DEF_DEP_CODE = "DEFDEPT";
		final String DEF_DEP_NAME = "SHOP";
		final String DB_TXN_POINT = "stockTransferService";
		String departmentId=null;
		final StockTransDetailService stockTransDetailService = new StockTransDetailService(getCurrentContext());
		final StockTransferService stockTransferService = new StockTransferService(getCurrentContext());
		final CounterService counterService = new CounterService(getCurrentContext());
		Integer cmbnmode=(Integer) session.getAttribute("combineMode");

		StockTransfer stockTransfer = null;

		stockTransferService.beginTrans(DB_TXN_POINT);

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		// issue found in stock tranfer section. 
		// can't save the data and the duplication of TRN number 
		// duplication is occured due to the increment of counter in edit save section. no need to increment the
		// TRN number while editing . Change the code 
		// Modified Date: 31-12-2020 , Modified By: Ayana Dharman 
		
		try {
			//String counterWithPrefix = counterService.getNextCounterwithPrefix("stock_transfer_hdr", "stock_transfer_hdr");
			
			stockTransfer = mapper.readValue(stockTransferDtl, StockTransfer.class);
		//	stockTransfer.setStock_transfer_no(counterWithPrefix);
			if (stockTransfer.getId() == null)
			{
//				if(cmbnmode==CombineMode.SEPARATE.getCombineModeId())
//				{
					stockTransfer.setStock_transfer_no(counterService.getNextCounterwithPrefix("stock_transfer_hdr", "stock_transfer_hdr"));
//					stockTransfer.setStock_transfer_no(counterService.getNextCounterwithPrefix("stock_in_hdr", "stock_in_hdr"));
//				}
			}
			
			
			stockTransfer.setReq_status(0);
			
			
	//		stockTransfer.setStock_transfer_no(counterService.getNextCounterwithPrefix("stock_transfer_hdr", "stock_transfer_hdr"));
			
			List<Company> hqCompany =  (List<Company>) session.getAttribute("hqCompanyData");
			for(Company comp:hqCompany)
			{
				if(stockTransfer.getDest_company_id()!=null){
					if(stockTransfer.getDest_company_id().equals(comp.getId()))	
					{
						stockTransfer.setDest_company_code(comp.getCode());
						stockTransfer.setDest_company_name(comp.getName());
						stockTransfer.setDest_department_id(DEF_DEP_ID);
						stockTransfer.setDest_department_code(DEF_DEP_CODE);
						stockTransfer.setDest_department_name(DEF_DEP_NAME);
						break;
					}

				}
				else {

					JsonParser jsonParser=new JsonParser();
					JsonObject departmentData=jsonParser.parse(stockTransferDtl).getAsJsonObject();
					departmentId=departmentData.get("dest_department_id").getAsString();
					String department_data=stockTransferService.getDepartmentData(departmentId);
					stockTransfer.setDest_department_name(department_data);
				}
			}

			stockTransfer = stockTransferService.saveStockItem(stockTransfer);
			String stockTransferdtl = stockTransfer.getStockDetailLists();

			JsonParser parser = new JsonParser();
			JsonObject jsonObject=new JsonObject();
			JsonArray stockTransferDtlList = (JsonArray) parser.parse(stockTransferdtl);
			
            
       /*     JsonElement jsonElement=new JsonParser().parse(stockTransferdtl);
            JsonObject jsonObject=jsonElement.getAsJsonObject();
            JsonArray stockTransferDtlList=jsonObject.getAsJsonArray(stockTransferdtl);*/
            
			final ArrayList<StockTransDetail> stockTransferItemsList = new ArrayList<StockTransDetail>();



			for (int i = 0; i < stockTransferDtlList.size(); i++) {

				final StockTransDetail stockTransferItem = new StockTransDetail();
				JsonObject json = (JsonObject) stockTransferDtlList.get(i);


				if (json.get("id").getAsString() != null && json.get("id").getAsString().length() > 0)
					stockTransferItem.setId(Integer.parseInt(json.get("id").getAsString()));

				stockTransferItem.setStock_transfer_hdr_id(stockTransfer.getId());
				stockTransferItem.setStock_item_id(json.get("stock_item_id").getAsInt());
				stockTransferItem.setStock_item_code(json.get("stock_item_code").getAsString());
				stockTransferItem.setStock_item_name(json.get("stock_item_name").getAsString());


				stockTransferItem.setUomCode(json.get("uomcode").getAsString());
				stockTransferItem.setBaseUomCode(json.get("base_uom_code").getAsString());
				stockTransferItem.setCompoundUnit((json.get("compound_unit").getAsString() != null && json.get("compound_unit")
						.getAsString().length() > 0) ? json.get("compound_unit").getAsDouble() : 0.00);
				
				stockTransferItem.setRequest_qty((json.get("request_qty").getAsString() != null && json
						.get("request_qty").getAsString().length() > 0) ? json.get("request_qty").getAsDouble()
								: 0.00);
				stockTransferItem.setIssued_qty((json.get("issued_qty").getAsString() != null && json
						.get("issued_qty").getAsString().length() > 0) ? json.get("issued_qty").getAsDouble()
								: 0.00);
				stockTransferItem.setCost_price((json.get("cost_price").getAsString() != null && json.get("cost_price")
						.getAsString().length() > 0) ? json.get("cost_price").getAsDouble() : 0.00);
				stockTransferItem.setAmount((json.get("amount").getAsString() != null && json.get("amount").getAsString()
						.length() > 0) ? json.get("amount").getAsDouble() : 0.00);



				stockTransferItem.setTax_pc((json.get("tax_pc").getAsString() != null && json.get("tax_pc").getAsString()
						.length() > 0) ? json.get("tax_pc").getAsDouble() : 0);


				stockTransferItemsList.add(stockTransferItem);

			}

			stockTransDetailService.save(stockTransferItemsList);
			//new PlanningDao(getCurrentContext()).updateOrderStatus(stockTransfer.getOrder_no(), productionOrderStatusType.TRNASFER_INITIATED.getProductionOrderStatusId());
			stockTransferService.endTrans(DB_TXN_POINT);
			response.getWriter().print("1");
		}
		catch (Exception e) {

			response.getWriter().print("0");
			stockTransferService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: save" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}
	}

	@RequestMapping(value = "getStockTransDtl")
	public void getStockTransDtlData(@ModelAttribute StockTransfer stockTrans , HttpServletResponse response)
			throws Exception {


		final StockTransferService stockTransferService = new StockTransferService(getCurrentContext());
		JsonArray stkTrsnDtl = stockTransferService.getStockTransDtlData(stockTrans);
		JsonObject jsonResponse = new JsonObject();

		jsonResponse.add("stkTrsnDtl", stkTrsnDtl);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}

	@RequestMapping(value = "/finalize")
	public void finalize(@RequestBody String stockInDtl , HttpServletRequest request , StockTransfer stockTransfer ,
			HttpServletResponse response , HttpSession session) throws Exception {

		final StockTransferService stockTransferService = new StockTransferService(getCurrentContext());
		final StockRegisterDetailService stockRegDetailService = new StockRegisterDetailService(getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		final OrderBookingService orderBookingService = new OrderBookingService(getCurrentContext());
		final PlanningDao planningDao = new PlanningDao(getCurrentContext());	

		final String DB_TXN_POINT = "stockInRegService";
		stockRegisterService.beginTrans(DB_TXN_POINT);

		Integer source_cmpny_id = (Integer) session.getAttribute("COMPANY_ID");	
		StockRegister sourceStockRegisterHdr = new StockRegister();
		StockRegister destinationStockRegisterHdr = new StockRegister();

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			stockTransfer = mapper.readValue(stockInDtl, StockTransfer.class);			
			Integer destination_company_id = stockTransfer.getDest_company_id();	
			Integer  destinationDeptId=stockTransfer.getDest_department_id();
			String destinationDeptName=stockTransfer.getDest_department_name();

			if(destination_company_id == 0)
				destination_company_id = source_cmpny_id;

			final CompanyService companyService = new CompanyService(getNewContext());
			Company currentCompany = new Company();
			currentCompany = companyService.getCompanyByID(destination_company_id);
			final ApplicationContext companyContext = getNewContext(currentCompany);

			final StockRegisterService destinationStockRegisterService = new StockRegisterService(companyContext);
			final StockRegisterDetailService destinationStockRegisterDtlService = new StockRegisterDetailService(companyContext);

			String stockTransferdtl = stockTransfer.getStockDetailLists();

			JsonParser parser = new JsonParser();
			JsonArray stockTransDtlList = (JsonArray) parser.parse(stockTransferdtl);

			stockTransferService.upDateStatus(stockTransfer);

			sourceStockRegisterHdr.setExtRefNo(stockTransfer.getStock_transfer_no());
			sourceStockRegisterHdr.setTxnDate(stockTransfer.getStock_transfer_date());
			sourceStockRegisterHdr.setTransType(transactionType.STOCKTRANSFER.gettransactionTypeId());
			sourceStockRegisterHdr.setExtRefId(stockTransfer.getId());
			sourceStockRegisterHdr.setSource_shop_id(source_cmpny_id);
			sourceStockRegisterHdr.setDestination_shop_id(stockTransfer.getDest_company_id());			
			sourceStockRegisterHdr = stockRegisterService.saveStockRegData(sourceStockRegisterHdr);				

			if(destination_company_id != 0){ 

				destinationStockRegisterHdr.setExtRefNo(stockTransfer.getStock_transfer_no());
				destinationStockRegisterHdr.setTxnDate(stockTransfer.getStock_transfer_date());
				destinationStockRegisterHdr.setTransType(transactionType.STOCKTRANSFER.gettransactionTypeId());
				destinationStockRegisterHdr.setExtRefId(stockTransfer.getId());
				destinationStockRegisterHdr.setSource_shop_id(source_cmpny_id);
				destinationStockRegisterHdr.setDestination_shop_id(stockTransfer.getDest_company_id());
				destinationStockRegisterHdr = destinationStockRegisterService.saveStockRegData(destinationStockRegisterHdr);
			}

			final ArrayList<StockRegisterDetail> sourceStockRegisterDtlList = new ArrayList<StockRegisterDetail>();
			final ArrayList<StockRegisterDetail> destinationStockRegisterDtlList = new ArrayList<StockRegisterDetail>();
			final ArrayList<OrderBoonkingSummary> orderBookingSummaryList = new ArrayList<OrderBoonkingSummary>();

			String order_no = ((JsonObject) stockTransDtlList.get(0)).get("order_no").getAsString();
			for (int i = 0; i < stockTransDtlList.size(); i++) {

				JsonObject json = (JsonObject) stockTransDtlList.get(i);
				final StockRegisterDetail sourceStockRegisterDtl = new StockRegisterDetail();
				final StockRegisterDetail destinationStockRegisterDtl = new StockRegisterDetail();

				sourceStockRegisterDtl.setDepartment_id(stockTransfer.getSource_department_id());
				sourceStockRegisterDtl.setStockRegHdrid(sourceStockRegisterHdr.getId());
				sourceStockRegisterDtl.setStockItemId(Integer.valueOf(json.get("stock_item_id").getAsString()));
				sourceStockRegisterDtl.setExtRefDtlId(Integer.valueOf(json.get("id").getAsString()));
				sourceStockRegisterDtl.setStockItemCode(json.get("stock_item_code").getAsString());
				sourceStockRegisterDtl.setStockItemName(json.get("stock_item_name").getAsString());
				sourceStockRegisterDtl.setOutQty(Double.valueOf(json.get("issued_qty").getAsString()));
				sourceStockRegisterDtl.setCostPrice((json.get("cost_price").getAsString() != null && json.get("cost_price")
						.getAsString().length() != 0) ? Double.valueOf(json.get("cost_price").getAsString()) : 0.00);
				sourceStockRegisterDtl.setApproval_qty(Double.valueOf(json.get("issued_qty").getAsString()));
				sourceStockRegisterDtl.setApproval_status(1);
				sourceStockRegisterDtlList.add(sourceStockRegisterDtl);

				if(destination_company_id != 0 && destinationDeptName.equals("SHOP")){

					//destinationStockRegisterDtl.setDepartment_id(stockTransfer.getDest_department_id());
					destinationStockRegisterDtl.setStockRegHdrid(destinationStockRegisterHdr.getId());
					destinationStockRegisterDtl.setStockItemId(Integer.valueOf(json.get("stock_item_id").getAsString()));
					destinationStockRegisterDtl.setExtRefDtlId(Integer.valueOf(json.get("id").getAsString()));
					destinationStockRegisterDtl.setStockItemCode(json.get("stock_item_code").getAsString());
					destinationStockRegisterDtl.setStockItemName(json.get("stock_item_name").getAsString());
					destinationStockRegisterDtl.setInQty(Double.valueOf(json.get("issued_qty").getAsString()));
					destinationStockRegisterDtl.setApproval_qty(Double.valueOf(json.get("issued_qty").getAsString()));
					destinationStockRegisterDtl.setApproval_status(0);
					destinationStockRegisterDtl.setCostPrice((json.get("cost_price").getAsString() != null && json.get("cost_price")
							.getAsString().length() != 0) ? Double.valueOf(json.get("cost_price").getAsString()) : 0.00);
					destinationStockRegisterDtlList.add(destinationStockRegisterDtl);

				}else {
					
					destinationStockRegisterDtl.setDepartment_id(stockTransfer.getDest_department_id());
					destinationStockRegisterDtl.setStockRegHdrid(destinationStockRegisterHdr.getId());
					destinationStockRegisterDtl.setStockItemId(Integer.valueOf(json.get("stock_item_id").getAsString()));
					destinationStockRegisterDtl.setExtRefDtlId(Integer.valueOf(json.get("id").getAsString()));
					destinationStockRegisterDtl.setStockItemCode(json.get("stock_item_code").getAsString());
					destinationStockRegisterDtl.setStockItemName(json.get("stock_item_name").getAsString());
					destinationStockRegisterDtl.setInQty(Double.valueOf(json.get("issued_qty").getAsString()));
					destinationStockRegisterDtl.setApproval_qty(Double.valueOf(json.get("issued_qty").getAsString()));
					destinationStockRegisterDtl.setApproval_status(0);
					destinationStockRegisterDtl.setCostPrice((json.get("cost_price").getAsString() != null && json.get("cost_price")
							.getAsString().length() != 0) ? Double.valueOf(json.get("cost_price").getAsString()) : 0.00);
					destinationStockRegisterDtlList.add(destinationStockRegisterDtl);
				}
				//Save to order_booking summary
				if(order_no != null && !order_no.equals(""))
				{
					final OrderBoonkingSummary orderSummary = new OrderBoonkingSummary();
					if(stockTransfer.getDest_company_code() == null)
						orderSummary.setShop_id(source_cmpny_id);
					else
						orderSummary.setShop_id(stockTransfer.getDest_company_id());
					orderSummary.setStock_item_id(json.get("stock_item_id").getAsInt());
					orderSummary.setTrans_date(stockTransfer.getStock_transfer_date());		
					orderSummary.setOrder_qty(0.00);
					orderSummary.setIssued_qty((json.get("issued_qty").getAsString() != null && json.get("issued_qty")
							.getAsString().length() != 0) ? json.get("issued_qty").getAsDouble() : 0.00);				
					orderSummary.setCreated_by(stockTransfer.getReq_by());
					orderSummary.setCreated_at(new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(new Date()));
					orderSummary.setExt_ref_id(json.get("order_no").getAsString());
					orderSummary.setExt_ref_no(stockTransfer.getStock_transfer_no());
					orderSummary.setTrans_type(2);
					orderSummary.setOrder_date(json.get("order_date").getAsString());
					orderBookingSummaryList.add(orderSummary);
				}				
			}

			stockRegDetailService.save(sourceStockRegisterDtlList);			
			if(destination_company_id != 0){
				destinationStockRegisterDtlService.save(destinationStockRegisterDtlList);//internal

			}
			if(order_no != null && !order_no.equals("")){

				orderBookingService.save(orderBookingSummaryList);
				//change status of order if there is no pending items
				JsonArray pendingItems = orderBookingService.getPendingItemsByOrderNo((
						(JsonObject) stockTransDtlList.get(0)).get("order_no").getAsString());

				if(pendingItems.size() == 0)
					planningDao.updateOrderStatus(((JsonObject) stockTransDtlList.get(0)).get("order_no").getAsString(), 
							productionOrderStatusType.DELIVERED.getProductionOrderStatusId());
			}
			response.getWriter().print("1");			
			stockRegisterService.endTrans(DB_TXN_POINT);			
		}
		catch (Exception e) {
			e.printStackTrace();			
			stockRegisterService.rollbackTrans(DB_TXN_POINT);
		}
	}	

	@Override
	public void delete(HttpServletRequest request , HttpServletResponse response) throws Exception {


		final StockTransferService stockTransferService = new StockTransferService(getCurrentContext());
		//final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		final StockTransDetailService stockTransDetailService = new StockTransDetailService(getCurrentContext());

		final String id = request.getParameter("id");
		final String order_no = request.getParameter("order_no");

		final String DB_TXN_POINT = "stockTransferService";
		stockTransferService.beginTrans(DB_TXN_POINT);

		try {

			Integer is_deleted = 0;
			is_deleted = stockTransferService.delete(id);

			if (is_deleted == 0) {

				response.getWriter().print("0");
			}
			else {

				StockTransDetail stockTransDetail = new StockTransDetail();
				stockTransDetail.setStock_transfer_hdr_id(Integer.parseInt(id));
				stockTransDetailService.delete(stockTransDetail);
				if(order_no != null)
					new PlanningDao(getCurrentContext()).updateOrderStatus(order_no, productionOrderStatusType.ON_PRODUCTION.getProductionOrderStatusId());
				response.getWriter().print("1");
			}


			stockTransferService.endTrans(DB_TXN_POINT);

		}
		catch (Exception e) {

			stockTransferService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: delete in " + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

	}

	@RequestMapping(value = "getTransferHdrData")
	public void getTransferHdrData(HttpServletRequest request , HttpServletResponse response) throws Exception{

		String transfer_date = request.getParameter("transfer_date");
		final StockTransferService stockTransferService = new StockTransferService(getCurrentContext());
		JsonArray transferHdrData = stockTransferService.getStockTransferHdr(transfer_date);

		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("transferHdrData", transferHdrData);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}


}
