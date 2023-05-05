package com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.mrp.web.core.base.controller.ViewController;
import com.indocosmo.mrp.web.core.exception.CustomException;
import com.indocosmo.mrp.web.masters.combocontents.service.ComboContentsService;
import com.indocosmo.mrp.web.masters.itemcategory.service.ItemCategoryService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.dao.ItemMasterBomDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.model.ItemMasterBom;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterbom.service.ItemMasterBomService;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.dao.ItemProdCostDao;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.model.ItemProdCost;
import com.indocosmo.mrp.web.masters.itemmaster.itemmasterprodcost.service.ItemProdCostService;
import com.indocosmo.mrp.web.masters.itemmaster.model.ItemMaster;
import com.indocosmo.mrp.web.masters.itemmaster.service.ItemMasterService;
import com.indocosmo.mrp.web.masters.tax.service.TaxService;
import com.indocosmo.mrp.web.masters.usergroup.sysdefpermission.model.SysdefPermission;
import com.indocosmo.mrp.web.masters.usergroup.userpermission.service.CurrentUserPermissionService;
import com.indocosmo.mrp.web.stock.war.service.StockWarServiceImpl;

@Controller
@RequestMapping(value = "/bom")
public class ItemMasterBomController extends ViewController<ItemMasterBom, ItemMasterBomDao, ItemMasterBomService> {
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "bomList")
	public void getBatchlist(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		final ItemMasterBomService itemmasterbomService = new ItemMasterBomService(getCurrentContext());
		List<ItemMasterBom> bomlist = itemmasterbomService.getList();
		String json = new Gson().toJson(bomlist);
		
		response.getWriter().print(json);
		
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see
	 * com.indocosmo.mrp.web.core.base.controller.BaseController#getService() */
	@Override
	public ItemMasterBomService getService() {
	
		return new ItemMasterBomService(getCurrentContext());
	}
	
	
	@RequestMapping(value = "/list")
	public String getList(@ModelAttribute ItemMasterBom itemMasterBom , HttpSession session , Model model) throws Exception {
	
		final CurrentUserPermissionService userPermissionService = new CurrentUserPermissionService();
		SysdefPermission permission = userPermissionService.getCurrentUserPermission(session, "SET_BOM");
		final ComboContentsService combocontentservice=new ComboContentsService(getCurrentContext());
		
		//List<ComboContents> combocontent=combocontentservice.getList();
		
		model.addAttribute("permission", permission);
		model.addAttribute("Settings",true);
		model.addAttribute("Products",true);
		model.addAttribute("itemmasterBom",true);
	//	model.addAttribute("combocontent",combocontent);
		
		return "/itembom/list";
	}
	
	
	@RequestMapping(value = "formJsonData")
	public void getFormJsonData(HttpServletRequest request , HttpServletResponse response , Model model)
			throws Exception {
	
		final ItemMasterBomService itemMasterBomService = new ItemMasterBomService(getCurrentContext());
		final ItemMasterService itemMasterService=new ItemMasterService(getCurrentContext());
		final ItemCategoryService itemCategoryService =new ItemCategoryService(getCurrentContext());
		final TaxService taxService=new TaxService(getCurrentContext());
		
		JsonArray itmList = itemMasterBomService.getItemList();
		JsonArray bomItems = itemMasterBomService.getItemMastersRowJson();
		JsonArray itemCategory = itemCategoryService.getDropdownArray();
		JsonArray tax = taxService.getTaxListForBom();
		JsonArray basebomList = itemMasterBomService.getbasebomList();
		
		JsonObject jsonResponse = new JsonObject();
		
		jsonResponse.add("itemCategory", itemCategory);
		jsonResponse.add("tax", tax);
		jsonResponse.add("itmList", itmList);
		jsonResponse.add("bomItems", bomItems);
		jsonResponse.add("basebomList", basebomList);
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonResponse.toString());
	}
	
	
	@RequestMapping(value = "/getDataToEdit")
	public void getDataToEdit(HttpServletRequest request , HttpServletResponse response) throws Exception {
	
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		int isBase = Integer.parseInt(request.getParameter("isBase"));
		JsonObject jsonResponse = new JsonObject();
		final ItemMasterBomService itemBomService = new ItemMasterBomService(getCurrentContext());
		final ItemProdCostService prodCostService=new ItemProdCostService(getCurrentContext());
		final StockWarServiceImpl stockWarService = new StockWarServiceImpl(getCurrentContext());
		
		Integer deptId=stockWarService.getDeptId(String.valueOf(request.getParameter("deptName")));
		
		
		
		
		// final SyncQueueService syncQueueService=new
		// SyncQueueService(getCurrentContext());
		try {
			
			JsonArray bomTemp = itemBomService.getBomJsonArray(itemId,isBase);
			JsonArray prodcost=prodCostService.getProdCostArray(itemId);
			JsonArray bom = new JsonArray();
			System.out.println("bom=====================Before"+bomTemp.toString());			
		
				for (int j = 0; j < bomTemp.size(); j++) {
					JsonObject ratedata = (JsonObject) bomTemp.get(j);
					
					Double warBomPrice=stockWarService.getcurrentWARPrice(ratedata.get("bom_item_id").getAsInt(), deptId);			
					
					//Double currWarPrice = stockWarService.getcurrentWARPrice(ratedata.get("id").getAsInt());
					if(!(warBomPrice.equals(0.00))){
						
						System.out.println("War Rate not 0==========");	
						
						//ratedata.remove("unit_price");
						//ratedata.addProperty("unit_price", warBomPrice);
					}
					
					bom.add(ratedata);
				}
				
				
				
			
			System.out.println("bom=====================After"+bom.toString());
						
			
			jsonResponse.add("bom", bom);
			jsonResponse.add("prd_costdata",prodcost);
			response.setContentType("application/Json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonResponse.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Method: getDataToEdit in" + this + Throwables.getStackTraceAsString(e));
			
		}
	}
	
	
	@RequestMapping(value = "/saveBom", method = RequestMethod.POST)
	public void save(@RequestBody String itemData , HttpSession session , HttpServletResponse response ,
			HttpServletRequest request) throws Exception {

		Integer version=(Integer) session.getAttribute("version");

		final ItemMasterService itemMasterService=new ItemMasterService(getCurrentContext());
		final ItemMasterBomService itemMasterBomService=new ItemMasterBomService(getCurrentContext());
		final ItemProdCostService prodCostService=new ItemProdCostService(getCurrentContext());
		final ItemProdCostDao itemProdCostDao=new ItemProdCostDao(getCurrentContext());
		final String DB_TXN_POINT = "itemMasterBomService";
		ItemMasterBom bom = null;
		ItemProdCost itmProdCost=null;
		Boolean prodCostFlag=true;
		ArrayList<ItemMasterBom> bomArrayList = new ArrayList<ItemMasterBom>();
		ObjectMapper mapper = new ObjectMapper();

		itemMasterBomService.beginTrans(DB_TXN_POINT);
		JsonParser parser1 = new JsonParser();
		Gson gson = new Gson();
		JsonObject obj=new JsonObject();
		JsonObject itemDatadet = parser1.parse(itemData).getAsJsonObject();
		String bomList = gson.toJson(itemDatadet.get("bom"));
		JsonArray bomListData = (JsonArray) parser1.parse(bomList);
		String costCalcList=gson.toJson(itemDatadet.get("prodCostList"));
		JsonArray prodCostData = (JsonArray) parser1.parse(costCalcList);
		ArrayList<ItemProdCost> prodCostArrayList=new ArrayList<ItemProdCost>();
		JsonObject itemDataObject=itemDatadet.getAsJsonObject("stock_item_data");
		try
		{
			//	itemMasterService.save(item);
			double stock_item_qty = itemDataObject.get("stock_item_qty").getAsDouble();
			Integer stock_item_id = itemDataObject.get("stock_item_id").getAsInt();
			if (bomListData.size() != 0) {
				for (int i = 0; i < bomListData.size(); i++) {
					bom = new ItemMasterBom();
					JsonObject json = (JsonObject) bomListData.get(i);
					if (bomListData.size() == 1 
							&& json.get("bom_item_id").getAsString().length() == 0
							&& json.get("qty").getAsString().length() == 0) {
						break;
					}
					/*if (bomListData.size() == 1 && json.get("id").getAsString().length() == 0
							&& json.get("bom_item_id").getAsString().length() == 0
							&& json.get("qty").getAsString().length() == 0) {
						break;
					}*/

					bom.setStockItemId(stock_item_id);

					if(itemDataObject.get("base_item_id") !=null && itemDataObject.get("base_item_id").getAsString().length() != 0){
						bom.setBaseItemId(itemDataObject.get("base_item_id").getAsInt());

					}

					//if(bom.getStockItemId()!=itemDataObject.get("stock_item_id").getAsInt()){

					if(json.get("id") != null && (json.get("id").getAsString()).length() != 0 && bom.getBaseItemId() ==null ) {
						bom.setId(json.get("id").getAsInt());
					}
					//}

					/*if(itemDataObject.get("base_bom_itemId") !=null && itemDataObject.get("base_bom_itemId").getAsString().length() != 0){
						bom.setStockBomItemId(itemDataObject.get("base_bom_itemId").getAsInt());

					}*/


					bom.setBomItemId((json.get("bom_item_id").getAsInt()));
					bom.setQty(json.get("qty").getAsDouble());
					//bom.setCost_price(json.get("unit_price").getAsDouble());
					bom.setCost_price(json.get("last_unit_price").getAsDouble());
					bom.setStock_item_qty(stock_item_qty);
					bomArrayList.add(bom);
				}
				itemMasterBomService.save(bomArrayList);
				itemMasterBomService.updateCostprice(bomArrayList, version);

				
				JsonElement price = itemDatadet.get("amount_after_tax");
				String unitprice = price.toString();

				itemMasterBomService.updateStockItemUnitPrice(stock_item_id, unitprice);

			}



			ItemMaster item =new ItemMaster();
			item.setId(itemDataObject.get("stock_item_id").getAsInt());
			//item.setStdPurchaseQty(itemDataObject.get("stock_item_qty").getAsDouble());
			item.setIs_semi_finished((itemDataObject.get("is_semi_finished").getAsBoolean()==true)?1:0);
			item.setIs_finished((itemDataObject.get("is_finished").getAsBoolean()==true)?1:0);
			item.setSales_margin(itemDataObject.get("sales_margin") != null && itemDataObject.get("sales_margin").getAsString().length()!=0?itemDataObject.get("sales_margin").getAsInt():0);
			item.setIs_sales_margin_percent((itemDataObject.get("is_sales_margin_percent").getAsBoolean()==true)?1:0);
			//item.setTax_percentage(itemDataObject.get("tax_percentage")!=null && itemDataObject.get("tax_percentage").getAsString().length()!=0?itemDataObject.get("tax_percentage").getAsDouble():0.0);
			item.setTaxCalculationMethod((itemDataObject.get("tax_calculation_method").getAsBoolean()==true)?1:0);


			itemMasterBomService.updateMrpStockItem(item,version);
			itemMasterBomService.updateQtyManufactured(stock_item_id , stock_item_qty, version);



			if(prodCostData.size() != 0)
			{

				for (int i = 0; i < prodCostData.size(); i++) {
					itmProdCost = new ItemProdCost();
					JsonObject json = (JsonObject) prodCostData.get(i);
					if (prodCostData.size() == 1 && json.get("id").getAsString().length() == 0
							&& json.get("prod_cost_id").getAsString().length() == 0
							/*&& json.get("rate").getAsString().length() == 0*/) {
						prodCostFlag = false;
						break;
					}

					itmProdCost.setStockItemId(bom.getStockItemId());
					if (json.get("id") != null && (json.get("id").getAsString()).length() != 0) {
						itmProdCost.setId(json.get("id").getAsInt());
					}
					if(json.get("isPercentage").getAsBoolean() == true)
					{
						itmProdCost.setIs_percentage(1);
					}else
					{
						itmProdCost.setIs_percentage(0);
					}
					itmProdCost.setCostcalc_param_id(json.get("prod_cost_id").getAsInt());
					itmProdCost.setRate(json.get("rate").getAsDouble());
					itmProdCost.setStock_item_qty(bom.getStock_item_qty());

					prodCostArrayList.add(itmProdCost);
				}

				if (prodCostFlag == true) {
					prodCostService.save(prodCostArrayList);
					JsonArray prodCostArray1=itemProdCostDao.getProdCostArray(bom.getStockItemId());
					obj.add("proddata1", prodCostArray1);

				}


			}

			if(prodCostFlag == false)
			{
				prodCostService.deleteData(bom.getStockItemId());

			}


			response.getWriter().print("1");

			itemMasterService.endTrans(DB_TXN_POINT);


		}
		catch (Exception e) {

			response.getWriter().print("0");
			itemMasterBomService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: saveBom" + this + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}



	}
	
	
	@Override
	public void delete(HttpServletRequest request,HttpServletResponse response) throws Exception
	{

		final ItemMasterService itemMasterService=new ItemMasterService(getCurrentContext());
		final ItemMasterBomService itemMasterBomService=new ItemMasterBomService(getCurrentContext());
		final ItemProdCostService prodCostService=new ItemProdCostService(getCurrentContext());


		final String id = request.getParameter("id");
		final String DB_TXN_POINT = "itemMasterBomService";
		itemMasterBomService.beginTrans(DB_TXN_POINT);
		String where="stock_item_id="+id;
		try {

			Integer is_deleted = 0;
			is_deleted = itemMasterBomService.delete(where);
			prodCostService.deleteData(Integer.parseInt(id));

			if (is_deleted == 0) {
				response.getWriter().print("0");
			}else{

			//productionService.upDateProductionOrderData(Integer.parseInt(id));
				response.getWriter().print(1);
				}

			itemMasterBomService.endTrans(DB_TXN_POINT);
		} catch (Exception e) {
			response.getWriter().print(0);
			itemMasterBomService.rollbackTrans(DB_TXN_POINT);
			e.printStackTrace();
			logger.error("Method: delete in"+this+Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}


	}


	
	
}
