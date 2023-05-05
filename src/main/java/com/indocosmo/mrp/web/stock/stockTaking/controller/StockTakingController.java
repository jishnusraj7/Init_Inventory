package com.indocosmo.mrp.web.stock.stockTaking.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.utils.core.SessionManager;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.enumDepartmentType;
import com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.transactionType;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.counter.service.CounterService;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.department.model.Department;
import com.indocosmo.mrp.web.masters.department.service.DepartmentService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.masters.users.model.Users;
import com.indocosmo.mrp.web.stock.stockTaking.dao.StockTakingDao;
import com.indocosmo.mrp.web.stock.stockTaking.modal.StockTaking;
import com.indocosmo.mrp.web.stock.stockTaking.service.StockTakingService;
import com.indocosmo.mrp.web.stock.stockadjustments.model.StockAdjustment;
import com.indocosmo.mrp.web.stock.stockadjustments.service.StockAdjustmentService;
import com.indocosmo.mrp.web.stock.stockadjustments.stockadjustmentdetail.model.StockAdjustmentDetail;
import com.indocosmo.mrp.web.stock.stockadjustments.stockadjustmentdetail.service.StockAdjustmentDetailService;
import com.indocosmo.mrp.web.stock.stockdisposal.controller.StockDisposalController;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.model.StockRegisterDetail;
import com.indocosmo.mrp.web.stock.stockregister.StockregisterDetail.service.StockRegisterDetailService;
import com.indocosmo.mrp.web.stock.stockregister.model.StockRegister;
import com.indocosmo.mrp.web.stock.stockregister.service.StockRegisterService;

/**
 * @author Gana
 *
 */
@Controller
@RequestMapping("stocktaking")
public class StockTakingController extends ViewController<StockTaking,StockTakingDao,StockTakingService>{

	public static final Logger logger = Logger.getLogger(StockTakingController.class);

	@Override
	public StockTakingService getService() {
		// TODO Auto-generated method stub
		return new StockTakingService(getCurrentContext());
	}


	@RequestMapping(value="/list")
	public String getList(@ModelAttribute StockTaking stockTaking,HttpSession session,Model model) {

		final CurrentUserPermissionService userPermission=new CurrentUserPermissionService();
		SysdefPermission sysPermission=userPermission.getCurrentUserPermission(session,"PRD_STKTAKING");
		model.addAttribute("permission",sysPermission);
		model.addAttribute("Production",true);
		model.addAttribute("stocktakingclass",true);
		return "stocktaking/list";


	}
	
	@RequestMapping(value = "/stockTaking", method = RequestMethod.GET)
	public void getStockItems(Integer department_id, Integer item_category_id, String selDate, HttpServletRequest request,
			HttpServletResponse response, HttpSession seesion, Model model) throws Exception {

		JsonArray stockItems = new StockTakingService(getCurrentContext()).getTableJsonArray(selDate, department_id, item_category_id);

		JsonObject stockDetails=new JsonObject();
		stockDetails.add("stockItems", stockItems);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(stockDetails.toString());
	}
	
	@RequestMapping(value="/department",method=RequestMethod.GET)
	public void getDepartment(HttpServletRequest request,HttpServletResponse response) throws Exception {

		JsonArray departments=new DepartmentService(getCurrentContext()).getMastersRowJson();

		JsonObject deptDetails=new JsonObject();
		deptDetails.add("departments", departments);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(deptDetails.toString());
	}
	
	/*
	 * 
	 *  Done by anandu on 22-01-2020
	 *   
	 */	
	@RequestMapping(value = "/item_category", method = RequestMethod.GET)
	public void getStockItems(HttpServletRequest request,
			HttpServletResponse response, HttpSession seesion, Model model) throws Exception {

		JsonArray itemCategory = new StockTakingService(getCurrentContext()).getgetItemCtegoryJsonArray();

		JsonObject itemCategories = new JsonObject();
		itemCategories.add("itemCategory", itemCategory);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(itemCategories.toString());
	}

	@RequestMapping(value="/updateStockItem",method=RequestMethod.POST)
	public void updateStockData(@RequestBody String stockData,HttpServletRequest request,HttpServletResponse response,Model model,HttpSession session) throws Exception {

		final String DB_TXN_POINT = "stockAdjustmentService";
		double currentStock = 0;
		double actualStock = 0;
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(stockData).getAsJsonObject();
		JsonArray stockArray = jsonObject.get("stock_data").getAsJsonArray();
		String production_date = jsonObject.get("prod_date").getAsString();
		String dept_id = jsonObject.get("dept_id").getAsString();

		Users user = (Users) session.getAttribute(SessionManager.SESSION_CURRENT_USER_TAG);
		/*Department department = (Department) session.getAttribute(SessionManager.SESSION_CURRENT_DEPARTMENT_PRD);
		int dest_department_id = enumDepartmentType.DEP_PRODUCTION.getenumDepartmentTypeId();*/
		Integer sourc_cmpny_id=(Integer) session.getAttribute("COMPANY_ID");		

		final CounterService counterService = new CounterService(getCurrentContext());
		final StockAdjustmentService stockAdjustService = new StockAdjustmentService(getCurrentContext());
		final StockAdjustmentDetailService stockAdjustDtlService = new StockAdjustmentDetailService(getCurrentContext());
		final StockRegisterService stockRegisterService = new StockRegisterService(getCurrentContext());
		final StockRegisterDetailService stockRegisterDtlService = new StockRegisterDetailService(getCurrentContext());
		ArrayList<StockAdjustmentDetail> stockAdjustItemsList = new ArrayList<StockAdjustmentDetail>();
		ArrayList<StockRegisterDetail> stockRegisterItemList = new ArrayList<StockRegisterDetail>();

	//	StockTaking stockTaking = new StockTaking();
		StockAdjustment stockAdjustment = new StockAdjustment();
		StockRegister stockRegisterHdr = new StockRegister();
	//	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		stockAdjustService.beginTrans(DB_TXN_POINT);
		try {
			stockAdjustment.setDepartmentId(Integer.valueOf(dept_id));
			stockAdjustment.setAdjust_date(production_date);
			stockAdjustment.setAdjust_by(user.getName());
			stockAdjustment.setRef_no(counterService.getNextCounterwithPrefix("stock_adjust_hdr", "stock_adjust_hdr"));
			stockAdjustService.save(stockAdjustment);

			stockRegisterHdr.setExtRefNo(stockAdjustment.getRef_no());
			stockRegisterHdr.setExtRefId(stockAdjustment.getId());
			stockRegisterHdr.setDepartmentId(Integer.valueOf(dept_id));
			stockRegisterHdr.setTxnDate(production_date);
			stockRegisterHdr.setTransType(transactionType.ADJUSTMENT.gettransactionTypeId());
			stockRegisterHdr.setSource_shop_id(sourc_cmpny_id);
			stockRegisterHdr.setDestination_shop_id(sourc_cmpny_id);

			stockRegisterService.saveStockRegData(stockRegisterHdr);

			for(int stockItemCount=0;stockItemCount<stockArray.size();stockItemCount++) {

				JsonObject jsonstockArray =(JsonObject) stockArray.get(stockItemCount);


				currentStock=Double.valueOf(jsonstockArray.get("current_stock").getAsString());

				if(jsonstockArray.get("actual_stock").getAsString().isEmpty()) {


				}else {
					actualStock= Double.valueOf(jsonstockArray.get("actual_stock").getAsString());
					double variance=actualStock-currentStock;

					StockAdjustmentDetail stockAdjustDetail = new StockAdjustmentDetail();
					stockAdjustDetail.setStock_adjust_hdr_id(stockAdjustment.getId());
					stockAdjustDetail.setStock_item_id(Integer.parseInt((jsonstockArray.get("stock_item_id")).getAsString()));
					stockAdjustDetail.setStock_item_code(jsonstockArray.get("code").getAsString());
					stockAdjustDetail.setStock_item_name(jsonstockArray.get("name").getAsString());
					stockAdjustDetail.setBatch_no(jsonstockArray.get("batch_no").getAsString());
					stockAdjustDetail.setExpiry_date(jsonstockArray.get("expiry_date").getAsString());
					stockAdjustDetail.setSystem_qty(currentStock);
					stockAdjustDetail.setActual_qty(actualStock);
					stockAdjustDetail.setDiff_qty(variance);
					stockAdjustDetail.setAdjust_qty(variance);
					stockAdjustDetail.setCost_price(0.00);
					stockAdjustItemsList.add(stockAdjustDetail);

					StockRegisterDetail stockRegisterDetail = new StockRegisterDetail();

					stockRegisterDetail.setStockRegHdrid(stockRegisterHdr.getId());
					stockRegisterDetail.setDepartment_id(Integer.valueOf(dept_id));
					stockRegisterDetail.setStockItemId(Integer.parseInt((jsonstockArray.get("stock_item_id")).getAsString()));
					stockRegisterDetail.setStockItemCode(jsonstockArray.get("code").getAsString());
					stockRegisterDetail.setStockItemName(jsonstockArray.get("name").getAsString());
					stockRegisterDetail.setBatch_no(jsonstockArray.get("batch_no").getAsString());
					stockRegisterDetail.setExpiry_date(jsonstockArray.get("expiry_date").getAsString());

					if (stockAdjustDetail.getSystem_qty() > stockAdjustDetail.getActual_qty()) {

						stockRegisterDetail.setOutQty(Math.abs(stockAdjustDetail.getDiff_qty()));
						stockRegisterDetail.setInQty(0.0);


					}else {

						stockRegisterDetail.setInQty(stockAdjustDetail.getDiff_qty());
						stockRegisterDetail.setOutQty(0.0);
					}

					stockRegisterDetail.setApproval_qty(Math.abs(stockAdjustDetail.getDiff_qty()));
					stockRegisterDetail.setApproval_status(1);
					stockRegisterItemList.add(stockRegisterDetail);

					stockAdjustDtlService.save(stockAdjustItemsList);
					for (int i = 0; i < stockAdjustItemsList.size(); i++) {

						stockRegisterItemList.get(i).setExtRefDtlId(stockAdjustItemsList.get(i).getId());
					}
					stockRegisterDtlService.save(stockRegisterItemList);
				}
			}

			stockAdjustService.endTrans(DB_TXN_POINT);
			response.getWriter().print("1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			stockAdjustService.rollbackTrans(DB_TXN_POINT);
			logger.error("Method: save in" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

	}
}
